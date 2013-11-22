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
	
			properties = propertiesNode.getElementsByTagName(valueString);
			for (int property = 0; property < properties.getLength(); property++) {
				String propertyName = properties.item(property).getAttributes().getNamedItem("Name")
						.getTextContent();
				Boolean propertyValue = Boolean.FALSE;
				if (properties.item(property).getAttributes().getNamedItem(valueString) != null) {
					propertyValue = Boolean.parseBoolean(properties.item(property).getAttributes()
							.getNamedItem(valueString).getNodeValue());
				}
				soundComponent.getBooleanProperties().put(propertyName, propertyValue);
			}
		}
	}
}
