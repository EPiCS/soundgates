package soundgates.diagram.XMLexport;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.runtime.CoreException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Delegation;
import soundgates.Link;
import soundgates.Port;
import soundgates.SoundComponent;
import soundgates.diagram.messageDialogs.MessageDialogs;
import soundgates.diagram.soundcomponents.AtomicSoundComponentLibrary;

public class CompositeSoundComponentExporter extends Exporter {

	public static void exportToXML(CompositeSoundComponent compositeSoundComponentToExport) {
	
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			HashMap<SoundComponent,Integer> embeddedComponentsHashMap = new HashMap<SoundComponent, Integer>();
			int componentCounter = 0;
			
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("CompositeSoundComponent");
			rootElement.setAttribute("Name", compositeSoundComponentToExport.getName());
			doc.appendChild(rootElement);

			// port elements
			Element portsElement = doc.createElement("Ports");
			for(Port port : compositeSoundComponentToExport.getPorts()){
				Element portElement = doc.createElement("Port");
				//attributes of the port				
				portElement.setAttribute("DataType", port.getDataType().toString());
				portElement.setAttribute("Direction", port.getDirection().toString());
				portElement.setAttribute("Name", port.getName());
				portsElement.appendChild(portElement);
			}
			rootElement.appendChild(portsElement);
			
			// embedded components elements
			Element embeddedComponentsElement = doc.createElement("EmbeddedSoundComponents");
			
			Tester patchTester = new Tester();
			
			for(SoundComponent embComponent : compositeSoundComponentToExport.getEmbeddedComponents()){
					
				if(embComponent instanceof AtomicSoundComponent){					
					embeddedComponentsElement.appendChild(
							getAtomicSoundComponentElement(
									doc, 
									(AtomicSoundComponent) embComponent, 
									"EmbeddedAtomicSoundComponent", 
									componentCounter)
							);
				}
				else if(embComponent instanceof CompositeSoundComponent){

					embeddedComponentsElement.appendChild(
							getCompositeSoundComponentElement(
									doc, 
									(CompositeSoundComponent) embComponent, 
									"EmbeddedCompositeSoundComponent", 
									componentCounter)
							);
				 	
					if(patchTester.shouldWriteFileForCompositeSoundComponent(embComponent.getName())){						
						exportToXML((CompositeSoundComponent) embComponent);	
					}
				}
				
				embeddedComponentsHashMap.put(embComponent, componentCounter);
				componentCounter++;
				
			}
			rootElement.appendChild(embeddedComponentsElement);
			
			// links			
			Element linksElement = doc.createElement("Links");			
			for(Link link : compositeSoundComponentToExport.getLinks()){
				linksElement.appendChild(getLinkElement(doc, link, embeddedComponentsHashMap));
			}			
			rootElement.appendChild(linksElement);
			
			// delegations
			Element delegationsElement = doc.createElement("Delegations");
			for(Delegation delegation : compositeSoundComponentToExport.getDelegations()){
				Element delegationElement = doc.createElement("Delegation");
				
				if (delegation.getSource().getComponent()==compositeSoundComponentToExport){					
					delegationElement.setAttribute("TargetPort", delegation.getTarget().getName());
					delegationElement.setAttribute("TargetComponent", Integer.toString(embeddedComponentsHashMap.get(delegation.getTarget().getComponent())));
					delegationElement.setAttribute("SourcePort", delegation.getSource().getName());
					delegationElement.setAttribute("SourceComponent", "this");					
				}
				else{					
					delegationElement.setAttribute("TargetPort", delegation.getTarget().getName());	
					delegationElement.setAttribute("TargetComponent", "this");
					delegationElement.setAttribute("SourcePort", delegation.getSource().getName());
					delegationElement.setAttribute("SourceComponent", Integer.toString(embeddedComponentsHashMap.get(delegation.getSource().getComponent())));								
				}
				delegationsElement.appendChild(delegationElement);
			}
			rootElement.appendChild(delegationsElement);
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
			String filePath = AtomicSoundComponentLibrary.getXMLFolder().getRawLocation().toString()+"/"+compositeSoundComponentToExport.getName()+".xml";
			StreamResult result = new StreamResult(new File(filePath));
			
			transformer.transform(source, result);
			
			MessageDialogs.compositeSoundComponentWasExported(compositeSoundComponentToExport.getName());	
			
			AtomicSoundComponentLibrary.getXMLFolder().refreshLocal(1, null);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
}
