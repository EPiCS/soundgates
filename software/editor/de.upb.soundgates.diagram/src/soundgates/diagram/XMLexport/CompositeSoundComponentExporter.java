package soundgates.diagram.XMLexport;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Delegation;
import soundgates.Direction;
import soundgates.Link;
import soundgates.Port;
import soundgates.SoundComponent;
import soundgates.SoundgatesFactory;
import soundgates.diagram.edit.parts.AtomicSoundComponentEditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponentEditPart;
import soundgates.diagram.edit.parts.LinkEditPart;
import soundgates.diagram.messageDialogs.MessageDialogs;
import soundgatesComposite.diagram.soundcomponents.AtomicSoundComponentLibrary;

public class CompositeSoundComponentExporter extends Exporter {

	public static Document getDocForCompositeSoundComponent(CompositeSoundComponent compositeSoundComponentToExport){
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
			return doc;
		}
		catch(Exception e){
			return null;
		}
	}
	
	public static Document getCompositeSoundComponentXMLDocumentFromEditParts(List<EditPart> selectedEditParts){
		LinkedList<soundgates.Element> elements = getElementsFromEditParts(selectedEditParts);
		if(elements==null)
			return null;
		else
			return CompositeSoundComponentExporter.getDocForCompositeSoundComponent(
				createCompositeSoundComponentFromSelectedEditParts(elements));
	}
	
	private static CompositeSoundComponent createCompositeSoundComponentFromSelectedEditParts(LinkedList<soundgates.Element> elements){		
		
		HashMap<SoundComponent,SoundComponent> componentCopies = new HashMap<SoundComponent,SoundComponent>();
		
		CompositeSoundComponent compositeSoundComponent = SoundgatesFactory.eINSTANCE.createCompositeSoundComponent();
		compositeSoundComponent.setName("NewCompositeSoundComponent");
		LinkedList<Link> links = new LinkedList<>();
		
		for(soundgates.Element element : elements){
			if(element instanceof AtomicSoundComponent){
				AtomicSoundComponent oldAtomicSoundComponent = (AtomicSoundComponent) element;				
				AtomicSoundComponent newAtomicSoundComponent = EcoreUtil.copy(oldAtomicSoundComponent);
				newAtomicSoundComponent.setType(oldAtomicSoundComponent.getType());
				componentCopies.put(oldAtomicSoundComponent, newAtomicSoundComponent);
				compositeSoundComponent.getEmbeddedComponents().add(newAtomicSoundComponent);
			}
			else if(element instanceof CompositeSoundComponent){
				CompositeSoundComponent oldCompositeSoundComponent = (CompositeSoundComponent) element;
				CompositeSoundComponent newCompositeSoundComponent = EcoreUtil.copy(oldCompositeSoundComponent);
				componentCopies.put(oldCompositeSoundComponent, newCompositeSoundComponent);
				compositeSoundComponent.getEmbeddedComponents().add(newCompositeSoundComponent);
			}
			else if(element instanceof Link){
				links.add((Link) element);
			}
		}
		for(Link oldLink : links){
			Link newLink = SoundgatesFactory.eINSTANCE.createLink();
			
			SoundComponent newSourceSoundComponent = componentCopies.get(oldLink.getSource().getComponent());
			Port newSource = findPort(newSourceSoundComponent.getPorts(),oldLink.getSource().getName());
			
			SoundComponent newTargetSoundComponent = componentCopies.get(oldLink.getTarget().getComponent());
			Port newTarget = findPort(newTargetSoundComponent.getPorts(),oldLink.getTarget().getName());
			
			newLink.setSource(newSource);
			newLink.setTarget(newTarget);
			
			compositeSoundComponent.getLinks().add(newLink);
		}
		
		//create ports for the composite component and delegations
		int portCounter = 1;
		for(SoundComponent soundComponent : compositeSoundComponent.getEmbeddedComponents()){
			for(Port port : soundComponent.getPorts()){
				if(port.getDirection()==Direction.IN){
					if(port.getIncomingConnection()==null){
						
						Port newPort = SoundgatesFactory.eINSTANCE.createPort();
						newPort.setDirection(port.getDirection());
						newPort.setDataType(port.getDataType());
						newPort.setName("Port"+portCounter+"_"+port.getName());
						
						Delegation delegation = SoundgatesFactory.eINSTANCE.createDelegation();
						delegation.setSource(newPort);
						delegation.setTarget(port);
						
						compositeSoundComponent.getPorts().add(newPort);
						compositeSoundComponent.getDelegations().add(delegation);
					}
				}
				else if(port.getDirection()==Direction.OUT){
					if (port.getOutgoingConnection().size()==0){
						
						Port newPort = SoundgatesFactory.eINSTANCE.createPort();
						newPort.setDirection(port.getDirection());
						newPort.setDataType(port.getDataType());
						newPort.setName("Port"+portCounter+"_"+port.getName());
						
						Delegation delegation = SoundgatesFactory.eINSTANCE.createDelegation();
						delegation.setSource(port);
						delegation.setTarget(newPort);
						
						compositeSoundComponent.getPorts().add(newPort);
						compositeSoundComponent.getDelegations().add(delegation);
					}
				}
			}
		}
		
		return compositeSoundComponent;
	}
	
	private static Port findPort(EList<Port> ports, String portName){
		for(Port port : ports)
			if (port.getName().equals(portName))
				return port;
		return null;
	}
	
	private static LinkedList<soundgates.Element> getElementsFromEditParts(List<EditPart> selectedEditParts){
		
		LinkedList<soundgates.Element> elements = new LinkedList<>();
		
		for(EditPart editPart : selectedEditParts){
			if(editPart instanceof AtomicSoundComponentEditPart){
				elements.add(((AtomicSoundComponentEditPart) editPart).getAtomicSoundComponent());
				
				if(!checkAtomicSoundComponent(((AtomicSoundComponentEditPart) editPart).getAtomicSoundComponent()))
					return null;
			}
			else if(editPart instanceof CompositeSoundComponentEditPart){
				elements.add(((CompositeSoundComponentEditPart) editPart).getCompositeSoundComponent());
			}
		}
		
		for(EditPart editPart : selectedEditParts){
			if(editPart instanceof LinkEditPart){
				Link link = (Link)((LinkEditPart) editPart).getConnection();
				
				if(elements.contains(link.getSource().getComponent()) && 
						elements.contains(link.getTarget().getComponent())){
					
					elements.add(link);
				}
			}
		}
		
		return elements;
	}
	
	private static boolean checkAtomicSoundComponent(AtomicSoundComponent atomicSoundComponent){
		if(atomicSoundComponent.getType().equals(AtomicSoundComponentLibrary.soundOutputType)){
			MessageDialogs.newCompositeSoundComponentContaintsSoundOutputBlock();
			return false;
		}
		else if(atomicSoundComponent.getType().equals(AtomicSoundComponentLibrary.IOComponentType)){
			MessageDialogs.newCompositeSoundComponentContaintsIOBlock();
			return false;
		}
		else 
			return true;
	}
}
