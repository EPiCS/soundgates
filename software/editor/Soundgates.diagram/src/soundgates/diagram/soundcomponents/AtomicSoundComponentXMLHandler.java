package soundgates.diagram.soundcomponents;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import soundgates.AtomicSoundComponent;
import soundgates.SoundgatesFactory;


/**
 * This class provides a single static method to parse an XML file into a SoundComponentLibrary
 * @author gwue
 *
 */
public class AtomicSoundComponentXMLHandler{
	

	public static String NODENAME_SIMULATION = "Simulation";
	public static String NODENAME_SIMULATION_PD = "PD";
	public static String NODENAME_SIMULATION_PROP_MAPPING = "PropMapping";
	public static String NODENAME_SIMULATION_PORT_MAPPING = "PortMapping";
	public static String CODEGEN_PREFIX = "codeGenProp_";
	public static String CODEGEN_PREFIX_PDCODE = CODEGEN_PREFIX + "_pdcode";
	public static String CODEGEN_PREFIX_VHDL = CODEGEN_PREFIX + "_vhdl";
	public static String CODEGEN_PREFIX_PROP_MAPPINGS = CODEGEN_PREFIX + "_propMappings";
	public static String CODEGEN_PREFIX_PORT_MAPPINGS = CODEGEN_PREFIX + "_portMappings";
	
	public static String NODENAME_DEVICE = "Device";
	public static String NODENAME_DEVICE_IMPL = "Implementation";
	public static String NODENAME_DEVICE_PROP_MAPPING = "PropMapping";
	public static String NODENAME_DEVICE_PORT_MAPPING = "PortMapping";
	public static String DEVICE_PREFIX = "deviceProp_";
	public static String DEVICE_PREFIX_PROP_MAPPINGS = DEVICE_PREFIX + "_propMappings_";
	public static String DEVICE_PREFIX_PORT_MAPPINGS = DEVICE_PREFIX + "_portMappings_";
	public static String DEVICE_PREFIX_IMPLNAME = DEVICE_PREFIX + "_name";
	
	// private AtomicSoundComponentLibrary library;

	public static void readFromXML(AtomicSoundComponentLibrary library, String libraryPath) {
		try {

			File directory = new File(libraryPath);
			String[] files = directory.list();
			for (int fileIndex = 0; fileIndex < files.length; fileIndex++) {
				File file = new File(libraryPath + files[fileIndex]);

				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = dBuilder.parse(file);

				NodeList nList = doc.getElementsByTagName("AtomicSoundComponent");

				for (int i = 0; i < nList.getLength(); i++) {
					Node nNode = nList.item(i);
					Element element = (Element) nNode;
					try {
						AtomicSoundComponent soundComponent = SoundgatesFactory.eINSTANCE.createAtomicSoundComponent();
						String type = element.getElementsByTagName("Type").item(0).getTextContent();
						soundComponent.setType(type);	
						
						try{
							String category = element.getElementsByTagName("Category").item(0).getTextContent();
						if(category!=null)	
							AtomicSoundComponentLibrary.typeToCategory.put(type, category);
						else
							AtomicSoundComponentLibrary.typeToCategory.put(type, "General");
						}catch(Exception e){
							AtomicSoundComponentLibrary.typeToCategory.put(type, "General");
						}
						
						NodeList portList = element.getElementsByTagName("Port");						
						XMLHandler.addPortsToSoundComponentFromPortList(soundComponent, portList);

						Element propertiesNode = (Element) element.getElementsByTagName("Properties").item(0);
						XMLHandler.addPropertiesToSoundComponentFromPropertiesElement(soundComponent, propertiesNode, "InitialValue");						
						
						Element codeNode = (Element) element.getElementsByTagName("Code").item(0);
						if (codeNode != null){
							NodeList codeSubNodes = codeNode.getChildNodes();
							for (int j = 0; j < codeSubNodes.getLength(); j++){
								
								Node currentSubNode = codeSubNodes.item(j);
								if (currentSubNode.getNodeType() == Node.ELEMENT_NODE){
									String propertyName = currentSubNode.getNodeName();
									if (propertyName.equals(NODENAME_SIMULATION)){

										String code = "";
										StringBuilder propMappings = new StringBuilder();
										StringBuilder portMappings = new StringBuilder();
										Node pdNode = currentSubNode.getFirstChild();
										do {
											if (pdNode.getNodeName().equals(NODENAME_SIMULATION_PD)){
												code = pdNode.getTextContent();
											}
											if (pdNode.getNodeName().equals(NODENAME_SIMULATION_PORT_MAPPING)){
												portMappings.append(pdNode.getAttributes().getNamedItem("PortName"));
												portMappings.append("|");
												portMappings.append(pdNode.getAttributes().getNamedItem("PortNumber"));
												portMappings.append("||");
											}
											if (pdNode.getNodeName().equals(NODENAME_SIMULATION_PROP_MAPPING)){
												propMappings.append(pdNode.getAttributes().getNamedItem("PropName"));
												propMappings.append("|");
												propMappings.append(pdNode.getAttributes().getNamedItem("Tag"));
												propMappings.append("||");
											}
										} while ((pdNode = pdNode.getNextSibling()) != null);

										soundComponent.getStringProperties().put(CODEGEN_PREFIX_PDCODE , code);
										soundComponent.getStringProperties().put(CODEGEN_PREFIX_PORT_MAPPINGS , portMappings.toString());
										soundComponent.getStringProperties().put(CODEGEN_PREFIX_PROP_MAPPINGS , propMappings.toString());									
										
									} 
									
									if (propertyName.equals(NODENAME_DEVICE)){

										NodeList nodeList = currentSubNode.getChildNodes();		
										for(int k=0; k<nodeList.getLength(); k++) 
										{
											if(nodeList.item(k).getNodeName().equals(NODENAME_DEVICE_IMPL)){
//												StringBuilder propMappings = new StringBuilder();
												StringBuilder portMappings = new StringBuilder();
												Node implNode = nodeList.item(k);
												
												String implType = implNode.getAttributes().getNamedItem("type").getNodeValue();
												String implName = implNode.getAttributes().getNamedItem("name").getNodeValue();
												
												Node childNode = implNode.getFirstChild(); 
												do {
													if (childNode.getNodeName().equals(NODENAME_DEVICE_PORT_MAPPING)){
														portMappings.append(childNode.getAttributes().getNamedItem("PortName"));
														portMappings.append("|");
														portMappings.append(childNode.getAttributes().getNamedItem("PortNumber"));
														portMappings.append("||");
													}
//													if (childNode.getNodeName().equals(NODENAME_DEVICE_PROP_MAPPING)){
//														propMappings.append(childNode.getAttributes().getNamedItem("PropName"));
//														propMappings.append("|");
//														propMappings.append(childNode.getAttributes().getNamedItem("Tag"));
//														propMappings.append("||");
//													}
												} while ((childNode = childNode.getNextSibling()) != null);

												soundComponent.getStringProperties().put(DEVICE_PREFIX_PORT_MAPPINGS + implType, portMappings.toString());
												soundComponent.getStringProperties().put(DEVICE_PREFIX_IMPLNAME, implName);
//												soundComponent.getStringProperties().put(DEVICE_PREFIX_PROP_MAPPINGS + implType, propMappings.toString());
											
												// put "sw", "hw" or "sw/hw" for possibleImplType
												if(!soundComponent.getStringProperties().containsKey("possibleImplType"))
													soundComponent.getStringProperties().put("possibleImplType", implType);
												else
													soundComponent.getStringProperties().put("possibleImplType", 
															soundComponent.getStringProperties().get("possibleImplType")+"|"+implType);
											}
										}	

									}
									
									else {
										String propertyValue = currentSubNode.getTextContent();
										soundComponent.getStringProperties().put(CODEGEN_PREFIX + propertyName, propertyValue);
									}
									
									
								}
							}
						}
						
//						if(soundComponent.getStringProperties().get("possibleImplType").contains("hw"))
//							soundComponent.getStringProperties().put("implType", "hw");
//						else
							soundComponent.getStringProperties().put("implType", "sw");
						
						library.addComponent(soundComponent);
						
					} catch (NullPointerException e) {
						//TODO Differenziertere Exceptions
						System.out.println("Malformed XML Document!");
						e.printStackTrace();
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	

}
