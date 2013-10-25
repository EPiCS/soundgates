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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import soundgates.AtomicSoundComponent;
import soundgates.DataType;
import soundgates.Direction;
import soundgates.Port;
import soundgates.SoundgatesFactory;


/**
 * This class provides a single static method to parse an XML file into a SoundComponentLibrary
 * @author gwue
 *
 */
public class AtomicSoundComponentXMLHandler {
	

	public static String NODENAME_SIMULATION = "Simulation";
	public static String NODENAME_SIMULATION_PD = "PD";
	public static String NODENAME_SIMULATION_PROP_MAPPING = "PropMapping";
	public static String NODENAME_SIMULATION_PORT_MAPPING = "PortMapping";
	public static String CODEGEN_PREFIX = "codeGenProp_";
	public static String CODEGEN_PREFIX_PDCODE = CODEGEN_PREFIX + "_pdcode";
	public static String CODEGEN_PREFIX_VHDL = CODEGEN_PREFIX + "_vhdl";
	public static String CODEGEN_PREFIX_PROP_MAPPINGS = CODEGEN_PREFIX + "_propMappings";
	public static String CODEGEN_PREFIX_PORT_MAPPINGS = CODEGEN_PREFIX + "_portMappings";
	// private AtomicSoundComponentLibrary library;

	public static void readFromXML(AtomicSoundComponentLibrary library, String libraryPath) {
		try {
			// String path =
			// "D:\\Users\\gwue\\Documents\\GitHub\\pg-soundgates\\software\\AtomicComponents\\";
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
						soundComponent.setName(type);
						soundComponent.setType(type);

						NodeList portList = element.getElementsByTagName("Port");
						for (int port = 0; port < portList.getLength(); port++) {
							Port soundComponentPort = SoundgatesFactory.eINSTANCE.createPort();
							Element portElement = (Element) portList.item(port);
							String portDirection = portElement.getAttribute("Direction");
							String portType = portElement.getAttribute("DataType");
							String portName = portElement.getAttribute("Name");
							if (portDirection.toUpperCase().contentEquals("IN")) {
								soundComponentPort.setDirection(Direction.IN);
							} else if (portDirection.toUpperCase().contentEquals("OUT")) {
								soundComponentPort.setDirection(Direction.OUT);
							} else {
								throw new NullPointerException("Port Direction invalid! " + portDirection.toUpperCase());
							}

							if (portType.toUpperCase().contentEquals("INT")
									|| portType.toUpperCase().contentEquals("INTEGER")) {
								soundComponentPort.setDataType(DataType.INTEGER);
							} else if (portType.toUpperCase().contentEquals("FLOAT")) {
								soundComponentPort.setDataType(DataType.FLOAT);
							} else if (portType.toUpperCase().contentEquals("SOUND")) {
								soundComponentPort.setDataType(DataType.SOUND);
							} else if (portType.toUpperCase().contentEquals("BOOL")
									|| portType.toUpperCase().contentEquals("BOOLEAN")) {
								soundComponentPort.setDataType(DataType.BOOLEAN);
							} else {
								throw new NullPointerException("Port DataType invalid! " + portType.toUpperCase());
							}

							if (portName.length() > 0) {
								soundComponentPort.setName(portName);
							} else {
								throw new NullPointerException("Port Name not specified");
							}

							soundComponent.getPorts().add(soundComponentPort);
						}

						Element propertiesNode = (Element) element.getElementsByTagName("Properties").item(0);
						if (propertiesNode != null) {
							NodeList properties = propertiesNode.getElementsByTagName("FloatProperty");
							for (int property = 0; property < properties.getLength(); property++) {
								String propertyName = properties.item(property).getAttributes().getNamedItem("Name")
										.getTextContent();
								Float propertyValue = 0f;
								if (properties.item(property).getAttributes().getNamedItem("InitialValue") != null) {
									propertyValue = Float.parseFloat(properties.item(property).getAttributes()
											.getNamedItem("InitialValue").getNodeValue());
								}
								soundComponent.getFloatProperties().put(propertyName, propertyValue);
							}

							properties = propertiesNode.getElementsByTagName("IntProperty");
							for (int property = 0; property < properties.getLength(); property++) {
								String propertyName = properties.item(property).getAttributes().getNamedItem("Name")
										.getTextContent();
								Integer propertyValue = 0;
								if (properties.item(property).getAttributes().getNamedItem("InitialValue") != null) {
									propertyValue = Integer.parseInt(properties.item(property).getAttributes()
											.getNamedItem("InitialValue").getNodeValue());
								}
								soundComponent.getIntegerProperties().put(propertyName, propertyValue);
							}

							properties = propertiesNode.getElementsByTagName("BoolProperty");
							for (int property = 0; property < properties.getLength(); property++) {
								String propertyName = properties.item(property).getAttributes().getNamedItem("Name")
										.getTextContent();
								Boolean propertyValue = Boolean.FALSE;
								if (properties.item(property).getAttributes().getNamedItem("InitialValue") != null) {
									propertyValue = Boolean.parseBoolean(properties.item(property).getAttributes()
											.getNamedItem("InitialValue").getNodeValue());
								}
								soundComponent.getBooleanProperties().put(propertyName, propertyValue);
							}
						}
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
												code = currentSubNode.getTextContent();
											}
											if (pdNode.getNodeName().equals(NODENAME_SIMULATION_PORT_MAPPING)){
												portMappings.append(currentSubNode.getAttributes().getNamedItem("PortName"));
												portMappings.append("|");
												portMappings.append(currentSubNode.getAttributes().getNamedItem("PortNumber"));
												portMappings.append("||");
											}
											if (pdNode.getNodeName().equals(NODENAME_SIMULATION_PROP_MAPPING)){
												propMappings.append(currentSubNode.getAttributes().getNamedItem("PropName"));
												propMappings.append("|");
												propMappings.append(currentSubNode.getAttributes().getNamedItem("Tag"));
												propMappings.append("||");
											}
										} while ((pdNode = pdNode.getNextSibling()) != null);

										soundComponent.getStringProperties().put(CODEGEN_PREFIX_PDCODE , code);
										soundComponent.getStringProperties().put(CODEGEN_PREFIX_PORT_MAPPINGS , portMappings.toString());
										soundComponent.getStringProperties().put(CODEGEN_PREFIX_PROP_MAPPINGS , propMappings.toString());
									} else {
										String propertyValue = currentSubNode.getTextContent();
										soundComponent.getStringProperties().put(CODEGEN_PREFIX + propertyName, propertyValue);
									}
									
									
								}
							}
						}
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
