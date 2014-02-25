package soundgatesComposite.diagram.soundcomponents;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.common.util.EList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import soundgatesComposite.AtomicSoundComponent;
import soundgatesComposite.CompositeSoundComponent;
import soundgatesComposite.Delegation;
import soundgatesComposite.Link;
import soundgatesComposite.Port;
import soundgatesComposite.SoundComponent;
import soundgatesComposite.SoundgatesCompositeFactory;
import soundgatesComposite.diagram.messageDialogs.MessageDialogs;


/**
 * This class provides a single static method to parse an XML file into a SoundComponentLibrary
 * @author gwue
 *
 */
public class CompositeSoundComponentXMLHandler {

	// private AtomicSoundComponentLibrary library;

	public static void readFromXML(CompositeSoundComponentLibrary library, String libraryPath) {
		try {

			File directory = new File(libraryPath);
			String[] files = directory.list();
			for (int fileIndex = 0; fileIndex < files.length; fileIndex++) {
				File file = new File(libraryPath + files[fileIndex]);

				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = dBuilder.parse(file);

				NodeList nList = doc.getElementsByTagName("CompositeSoundComponent");

				for (int i = 0; i < nList.getLength(); i++) {
					Node nNode = nList.item(i);
					Element element = (Element) nNode;
					try {
						CompositeSoundComponent compositeSoundComponent = SoundgatesCompositeFactory.eINSTANCE.createCompositeSoundComponent();						
						compositeSoundComponent.setName(element.getAttribute("Name"));			

						NodeList portList = element.getElementsByTagName("Port");						
						XMLHandler.addPortsToSoundComponentFromPortList(compositeSoundComponent, portList);					
						
						HashMap<Integer,SoundComponent> embeddedSoundComponentMapping = new HashMap<Integer,SoundComponent>();
						
						NodeList atomicComponentList = element.getElementsByTagName("EmbeddedAtomicSoundComponent");
						for (int atomComp = 0; atomComp < atomicComponentList.getLength(); atomComp++) {							
							Element atomCompElement = (Element) atomicComponentList.item(atomComp);		
							
							AtomicSoundComponent atomicSoundComponent = null;
							try{
								atomicSoundComponent = AtomicSoundComponentLibrary.getInstance().
										createAtomicSoundComponentInstance(atomCompElement.getAttribute("Type"));
							}
							catch(Exception e){
								MessageDialogs.atomicComponentInCompositeComponentMissing(
										atomCompElement.getAttribute("Type"),
										element.getAttribute("Name"));											   
								return;
							}
														
							atomicSoundComponent.setName(atomCompElement.getAttribute("Name"));
//							atomicSoundComponent.setType(atomCompElement.getAttribute("Type"));	
							
							//walkaround
							atomicSoundComponent.getStringProperties().put("Type", atomCompElement.getAttribute("Type"));							
							
							Element propertiesNode = (Element) atomCompElement.getElementsByTagName("Properties").item(0);							
							XMLHandler.addPropertiesToSoundComponentFromPropertiesElement(atomicSoundComponent, propertiesNode, "Value");
							
							compositeSoundComponent.getEmbeddedComponents().add(atomicSoundComponent);	
							embeddedSoundComponentMapping.put(
									Integer.parseInt(atomCompElement.getAttribute("Id")), atomicSoundComponent);

						}
						
						NodeList compositeComponentList = element.getElementsByTagName("EmbeddedCompositeSoundComponent");
						for (int compositeComp = 0; compositeComp < compositeComponentList.getLength(); compositeComp++) {							
							Element compositeCompElement = (Element) compositeComponentList.item(compositeComp);										
							
							CompositeSoundComponent embeddedCompositeSoundComponent = null;
							try{
								embeddedCompositeSoundComponent = CompositeSoundComponentLibrary.getInstance().
										createCompositeSoundComponentInstance(compositeCompElement.getAttribute("Name"));
							}
							catch(Exception e){
								MessageDialogs.compositeComponentInCompositeComponentMissing(
										compositeCompElement.getAttribute("Name"),
										element.getAttribute("Name"));
								return;
							}
							
							embeddedCompositeSoundComponent.setName(compositeCompElement.getAttribute("Name"));		
							
							compositeSoundComponent.getEmbeddedComponents().add(embeddedCompositeSoundComponent);
							embeddedSoundComponentMapping.put(
									Integer.parseInt(compositeCompElement.getAttribute("Id")), embeddedCompositeSoundComponent);
						}
						
						NodeList linkList = element.getElementsByTagName("Link");
						for (int linkIndex = 0; linkIndex < linkList.getLength(); linkIndex++) {							
							Element linkElement = (Element) linkList.item(linkIndex);	
							
							Link link = SoundgatesCompositeFactory.eINSTANCE.createLink();							
							SoundComponent sourceComponent = embeddedSoundComponentMapping.get(
									Integer.parseInt(linkElement.getAttribute("SourceComponent")));
							SoundComponent targetComponent = embeddedSoundComponentMapping.get(
									Integer.parseInt(linkElement.getAttribute("TargetComponent")));		
							Port sourcePort = findPort(sourceComponent.getPorts(), linkElement.getAttribute("SourcePort"));
							Port targetPort = findPort(targetComponent.getPorts(), linkElement.getAttribute("TargetPort"));
							link.setSource(sourcePort);
							link.setTarget(targetPort);
							compositeSoundComponent.getLinks().add(link);
						}
						
						NodeList delegationList = element.getElementsByTagName("Delegation");
						for (int delegationIndex = 0; delegationIndex < delegationList.getLength(); delegationIndex++) {							
							Element delegationElement = (Element) delegationList.item(delegationIndex);	
							
							Delegation delegation = SoundgatesCompositeFactory.eINSTANCE.createDelegation();		
							
							String sourceID = delegationElement.getAttribute("SourceComponent");
							String targetID = delegationElement.getAttribute("TargetComponent");
							
							SoundComponent sourceComponent;
							if(sourceID.equals("this")) sourceComponent = compositeSoundComponent;
							else sourceComponent = embeddedSoundComponentMapping.get(Integer.parseInt(sourceID));
									
							SoundComponent targetComponent;
							if(targetID.equals("this")) targetComponent = compositeSoundComponent;
							else targetComponent = embeddedSoundComponentMapping.get(Integer.parseInt(targetID));							
	
							Port sourcePort = findPort(sourceComponent.getPorts(), delegationElement.getAttribute("SourcePort"));
							Port targetPort = findPort(targetComponent.getPorts(), delegationElement.getAttribute("TargetPort"));
							delegation.setSource(sourcePort);
							delegation.setTarget(targetPort);
							compositeSoundComponent.getDelegations().add(delegation);
						}

						library.addComponent(compositeSoundComponent);
						
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
	
	public static Port findPort(EList<Port> ports, String portName){
		for(Port port : ports)
			if (port.getName().equals(portName))
				return port;
		return null;
	}

}
