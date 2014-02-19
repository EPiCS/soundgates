package soundgates.diagram.soundcomponents;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import soundgates.AtomicSoundComponent;
import soundgates.DataType;
import soundgates.Direction;
import soundgates.Port;
import soundgates.SoundComponent;
import soundgates.SoundgatesFactory;

public class XMLHandler {
	public static void addPortsToSoundComponentFromPortList(SoundComponent soundComponent, NodeList portList){		

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

			if (portType.toUpperCase().contentEquals("CONTROL")) {
				soundComponentPort.setDataType(DataType.CONTROL);
			} else if (portType.toUpperCase().contentEquals("SOUND")) {
				soundComponentPort.setDataType(DataType.SOUND);
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
	
	} 
	
	public static void addPropertiesToSoundComponentFromPropertiesElement(AtomicSoundComponent soundComponent, Element propertiesNode, String valueString){
		if (propertiesNode != null) {
			NodeList properties = propertiesNode.getElementsByTagName("FloatProperty");
			for (int property = 0; property < properties.getLength(); property++) {
				String propertyName = properties.item(property).getAttributes().getNamedItem("Name")
						.getTextContent();
				Float propertyValue = 0f;
				if (properties.item(property).getAttributes().getNamedItem(valueString) != null) {
					propertyValue = Float.parseFloat(properties.item(property).getAttributes()
							.getNamedItem(valueString).getNodeValue());
				}
				soundComponent.getFloatProperties().put(propertyName, propertyValue);
			}	
			
			properties = propertiesNode.getElementsByTagName("IntProperty");
			if(properties.getLength()==0)
				properties = propertiesNode.getElementsByTagName("IntegerProperty");
			
			for (int property = 0; property < properties.getLength(); property++) {
				String propertyName = properties.item(property).getAttributes().getNamedItem("Name")
						.getTextContent();
				Integer propertyValue = 0;
				if (properties.item(property).getAttributes().getNamedItem(valueString) != null) {
					propertyValue = Integer.parseInt(properties.item(property).getAttributes()
							.getNamedItem(valueString).getNodeValue());
				}
				soundComponent.getIntegerProperties().put(propertyName, propertyValue);
			}
			
			properties = propertiesNode.getElementsByTagName("UserStringProperty");
			for (int property = 0; property < properties.getLength(); property++) {
				String propertyName = properties.item(property).getAttributes().getNamedItem("Name")
						.getTextContent();
				
				if(properties.item(property).getAttributes().getNamedItem("Options")!=null){
					String optionsString = properties.item(property).getAttributes().getNamedItem("Options")
							.getTextContent();
					soundComponent.getStringProperties().put(propertyName+"Options", optionsString);
				}
				
				String propertyValue = "";
				if (properties.item(property).getAttributes().getNamedItem(valueString) != null) {
					propertyValue = properties.item(property).getAttributes()
							.getNamedItem(valueString).getNodeValue();
				}				
				soundComponent.getUserStringProperties().put(propertyName, propertyValue);
			}
			
			properties = propertiesNode.getElementsByTagName("StringProperty");
			for (int property = 0; property < properties.getLength(); property++) {
				String propertyName = properties.item(property).getAttributes().getNamedItem("Name")
						.getTextContent();
				String propertyValue = "";
				if (properties.item(property).getAttributes().getNamedItem(valueString) != null) {
					propertyValue = properties.item(property).getAttributes()
							.getNamedItem(valueString).getNodeValue();
				}
				soundComponent.getStringProperties().put(propertyName, propertyValue);
			}
		}
	}
}
