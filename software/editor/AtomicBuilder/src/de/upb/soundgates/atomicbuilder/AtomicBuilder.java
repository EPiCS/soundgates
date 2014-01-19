package de.upb.soundgates.atomicbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AtomicBuilder {

	static boolean folder = false;
	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("--folder")){
			folder = true;
		}
		if (args.length == 0){
			System.out.println("Usage:");
			System.out.println("\t -r <sourceFolder> <destinationFolder>");
			System.out.println("or");
			System.out.println("\t <pdFile> <templateFile> <destinationFile>");
		}
		
			if (folder){
				File sourceFolder = new File(args[1]);
				File destinationFolder = new File(args[2]);
				for (final File sub : sourceFolder.listFiles()){
					if (sub.isDirectory()){
						File pdFile = getFileFromFolder(sub, "_patch.pd");
						File xmlFile = getFileFromFolder(sub, "xml");
							try {
								generate(new File(destinationFolder, xmlFile.getName().replaceAll("(\\..*)$", "") + ".xml"), pdFile, xmlFile);
							} catch (ParserConfigurationException
									| SAXException | IOException
									| TransformerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}
			} else {
				File pdFile = new File(args[0]);
				File templateFile = new File(args[0]);
				File destinationFile = new File(args[1]);
				try {
					generate(destinationFile, pdFile, templateFile);
				} catch (ParserConfigurationException | SAXException
						| IOException | TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			

		
	}
	
	private static void generate(File destinationFile, File pdFile, File templateFile) throws ParserConfigurationException, SAXException, IOException, TransformerException{
		Document templateDoc = readTemplateFile(templateFile);
		PureDataInfo pureDataInfo = readPureDataCode(pdFile);
		insert(templateDoc, pureDataInfo);
		output(templateDoc, destinationFile);
	}
	
	private static File getFileFromFolder(File folder, final String suffix) {
		for (final File sub : folder.listFiles()) {
			if (sub.getName().endsWith(suffix)) return sub;
		}
		return null;
	}

	private static void output(Document templateDoc, File destination) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(templateDoc);
		StreamResult result = new StreamResult(destination);
		transformer.transform(source, result);
	}

	private static void insert(Document templateDoc, PureDataInfo pureDataInfo) {
		NodeList simulationNodes = templateDoc.getElementsByTagName("Simulation");
		for (int i = 0; i < simulationNodes.getLength(); i++){
			if (simulationNodes.item(i).getParentNode().getNodeName().equals("Code")){
				createPortMappings(templateDoc, simulationNodes.item(i), pureDataInfo.getInputDefinitions());
				createPortMappings(templateDoc, simulationNodes.item(i), pureDataInfo.getOutputDefinitions());
				createPropMappings(templateDoc, simulationNodes.item(i), pureDataInfo.getProperties());
				createCode(templateDoc, simulationNodes.item(i), pureDataInfo.getCode());
			}
		}
	}
	
	private static void createCode(Document doc, Node parent,
			String code){
		Element codeNode = doc.createElement("PD");
		parent.appendChild(codeNode);
		codeNode.appendChild(doc.createTextNode(code));
	}
	
	private static void createPropMappings(Document doc, Node parent,
			List<String> properties) {
		for (String property : properties){
			Element propMapping = doc.createElement("PropMapping");
			parent.appendChild(propMapping);
			propMapping.setAttribute("PropName", property);
			propMapping.setAttribute("Tag", "@" + property);
		}
	}

	private static void createPortMappings(Document doc, Node parent, List<String> ports){
		for (String port : ports){
			Element portMapping = doc.createElement("PortMapping");
			parent.appendChild(portMapping);
			portMapping.setAttribute("PortName", port);
			portMapping.setAttribute("PortNumber", "" + ports.indexOf(port));
		}
	}

	private static Document readTemplateFile(File templateFile) throws ParserConfigurationException, SAXException, IOException {

		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return dBuilder.parse(templateFile);
	}

	private static void executeReplacements(List<String> codeLines, PureDataInfo pdInfo){
		List<String> connects = new ArrayList<String>();
		List<String> onlyObjects = new ArrayList<String>();
		
		//find counted pd code lines
		for (String line : codeLines){
				onlyObjects.add(line);
		}
		
		//find connections
		for (String line : codeLines){
			if (line.contains("#X connect")){
				
				connects.add(line);

			}
		}

		//find messages with no outgoing links
		for(String connectLine : connects){
			String [] connect = connectLine.split(" ");
			int sourceObjectNumber = Integer.parseInt(connect[2]);
			int targetObjectNumber = Integer.parseInt(connect[4]);
			String targetObject = getObject(codeLines, targetObjectNumber);
			if (targetObject.contains("#X msg")){
				String [] message = targetObject.split(" ");
				
				//find only messages with no links to them
				boolean hasOutgoingLinks = false;
				for(String tmpConnectLine : connects){
					String [] tmpConnect = tmpConnectLine.split(" ");
					if (Integer.parseInt(tmpConnect[2]) == targetObjectNumber){
						hasOutgoingLinks = true;
						break;
					}
				}
				
				if (!hasOutgoingLinks){
					String selector = message [4];
					if (selector.equals("replace")){
						int atomToReplace = Integer.parseInt(message[5]);
						String replacement = "@" + message[6];
						
						String [] toManipulate = getObject(codeLines, sourceObjectNumber).split(" ");
						int offset = 3;
						//TODO: check if other offsets are needed, 3 is correct for messages, objects and comments
						toManipulate[atomToReplace + offset] = replacement;
						
						String manipulatedLine = "";
						for (String part : toManipulate)
							manipulatedLine += part + " ";
						pdInfo.addProperty(message[6]);
						setObject(codeLines, sourceObjectNumber, manipulatedLine);
					}
				}

			}
		}
	}
	
	private static void setObject(List<String> pdCodeLines, int objectId, String object){
		int counter = 0;
		int lineNumber = 0;
		for (String line : pdCodeLines){
			if (!line.contains("#N")){
				if (counter == objectId){
					pdCodeLines.set(lineNumber, object);
					return;
				}
				counter++;
			}
			lineNumber++;
		}
	}
	
	private static String getObject(List<String> pdCodeLines, int objectId){
		int counter = 0;
		for (String line : pdCodeLines){
			if (!line.contains("#N")){
				if (counter == objectId){
					return line;
				}
				counter++;
			}
		}
		return null;
	}
	
	private static PureDataInfo readPureDataCode(File pdFile) throws IOException {
		FileReader fileReader = new FileReader(pdFile);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String read = "";
		PureDataInfo result = new PureDataInfo();
		List<String> codeLines = new ArrayList<String>();
		while((read = bufferedReader.readLine()) != null){
			if (!read.contains(";")){
				read += bufferedReader.readLine();
			}
			read = read.split(";")[0];
			if (read.contains("#X text")){
				String [] splitLine = read.split(" ");
				if (splitLine.length >= 5 && splitLine[4].equals("inports")){
					for (int i = 5; i < splitLine.length; i++){
						result.addInputDefinition(splitLine[i]);
					}
				} else if (splitLine.length >= 5 && splitLine[4].equals("outports")){
					for (int i = 5; i < splitLine.length; i++){
						result.addOutputDefinition(splitLine[i]);
					}
				}
			}
			codeLines.add(read);
		}
		
		bufferedReader.close();
		
		StringBuilder resultingCode = new StringBuilder();
		executeReplacements(codeLines, result);
		for (String line : codeLines){
			resultingCode.append(line + ";" + System.lineSeparator());
		}
		result.setCode(resultingCode.toString());
		return result;
	}

}
