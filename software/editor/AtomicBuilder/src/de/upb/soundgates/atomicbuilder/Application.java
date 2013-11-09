package de.upb.soundgates.atomicbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Application {

	public static void main(String[] args) {
		String templateFile = args[0];
		String pdFile = args[1];
		
		try {
			Document templateDoc = readTemplateFile(templateFile);
			PureDataInfo pureDataInfo = readPureDataCode(pdFile);
			insert(templateDoc, pureDataInfo);
			output(templateDoc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void output(Document templateDoc) throws TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(templateDoc);
		//StreamResult result = new StreamResult(new File("C:\\file.xml"));
		StreamResult result = new StreamResult(System.out);
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

	private static Document readTemplateFile(String path) throws ParserConfigurationException, SAXException, IOException {
		File file = new File(path);

		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		return dBuilder.parse(file);
	}

	private static PureDataInfo readPureDataCode(String path) throws IOException {
		FileReader fileReader = new FileReader(new File(path));
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuilder code = new StringBuilder();
		String read = "";
		boolean manipulateNextLine = false;
		int manipulationIndex = 0;
		String manipulationValue = "";
		PureDataInfo result = new PureDataInfo();
		while((read = bufferedReader.readLine()) != null){
			read = read.split(";")[0];
			if (manipulateNextLine){
				String [] splitLine = read.split(" ");
				splitLine[manipulationIndex] = "@" + manipulationValue;
				read = "";
				for (String single : splitLine){
					read += single + " "; 
				}
				manipulateNextLine = false;
			}
			if (read.contains("#X text")){
				String [] splitLine = read.split(" ");
				if (splitLine[4].equals("replace")){
					manipulateNextLine = true;
					manipulationIndex = Integer.parseInt(splitLine[5]) + 1;
					manipulationValue = splitLine[6];
					result.addProperty(manipulationValue);
				} else if (splitLine[4].equals("inports")){
					for (int i = 5; i < splitLine.length; i++){
						result.addInputDefinition(splitLine[i]);
					}
				} else if (splitLine[4].equals("outports")){
					for (int i = 5; i < splitLine.length; i++){
						result.addOutputDefinition(splitLine[i]);
					}
				}
			} else {
				code.append(read + ";" + System.lineSeparator());
			}
		}
		bufferedReader.close();
		result.setCode(code.toString());
		return result;
	}

}
