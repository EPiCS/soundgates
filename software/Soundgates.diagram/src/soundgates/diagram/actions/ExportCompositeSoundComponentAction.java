package soundgates.diagram.actions;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Delegation;
import soundgates.Link;
import soundgates.Port;
import soundgates.SoundComponent;
import soundgates.diagram.edit.parts.CompositeSoundComponentEditPart;
import soundgates.diagram.soundcomponents.AtomicSoundComponentLibrary;



public class ExportCompositeSoundComponentAction implements IObjectActionDelegate{

	@Override
	public void run(IAction action) {
		IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		Iterator<?> files = structuredSelection.iterator();
		while (files.hasNext()) {			
			 Object selectedObject = files.next(); 
			 if (selectedObject instanceof CompositeSoundComponentEditPart){
				 CompositeSoundComponentEditPart editPart = (CompositeSoundComponentEditPart) selectedObject;
				 CompositeSoundComponent compositeSoundComponent =  (CompositeSoundComponent) ((Shape) editPart.getModel()).getElement();
				 
				 exportToXML(compositeSoundComponent);
				 
			 }
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {	
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {		
	}

	public void exportToXML(CompositeSoundComponent compositeSoundComponentToExport) {
		if (compositeSoundComponentToExport.getName()==null || compositeSoundComponentToExport.getName().equals("")){
			MessageDialog.openWarning(null, "Composite sound component has no name", "Please enter a name for the composite sound component.");
			return;
		}
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			HashMap<SoundComponent,Integer> embeddedComponentsHashMap = new HashMap<SoundComponent, Integer>();
			int componentCounter = 0;
			
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("CompositeSoundComponent");
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
					AtomicSoundComponent embAtomicComponent = (AtomicSoundComponent) embComponent;
					Element embComponentElement = doc.createElement("EmbeddedAtomicSoundComponent");
					embComponentElement.setAttribute("Type", embAtomicComponent.getType());
					embComponentElement.setAttribute("Id", Integer.toString(componentCounter));
					
					//integer properties
					for(int i=0; i<embAtomicComponent.getIntegerProperties().size(); i++){						
						Element integerProperty = doc.createElement("IntegerProperty");						
						integerProperty.setAttribute("Name",embAtomicComponent.getIntegerProperties().get(i).getKey().toString());
						integerProperty.setAttribute("Value",embAtomicComponent.getIntegerProperties().get(i).getValue().toString());						
						embComponentElement.appendChild(integerProperty);
					}

					//float properties
					for(int i=0; i<embAtomicComponent.getFloatProperties().size(); i++){						
						Element floatProperty = doc.createElement("FloatProperty");
						floatProperty.setAttribute("Name", embAtomicComponent.getFloatProperties().get(i).getKey().toString());						
						floatProperty.setAttribute("Value", embAtomicComponent.getFloatProperties().get(i).getValue().toString());						
						embComponentElement.appendChild(floatProperty);
					}
					
					//boolean properties
					for(int i=0; i<embAtomicComponent.getBooleanProperties().size(); i++){						
						Element booleanProperty = doc.createElement("BooleanProperty");						
						booleanProperty.setAttribute("Name",embAtomicComponent.getBooleanProperties().get(i).getKey().toString());
						booleanProperty.setAttribute("Value",embAtomicComponent.getBooleanProperties().get(i).getValue().toString());						
						embComponentElement.appendChild(booleanProperty);
					}
					
					embeddedComponentsElement.appendChild(embComponentElement);
				}
				else if(embComponent instanceof CompositeSoundComponent){
					Element embComponentElement = doc.createElement("EmbeddedCompositeSoundComponent");
					embComponentElement.setAttribute("Name", embComponent.getName());
					embComponentElement.setAttribute("Id", Integer.toString(componentCounter));
					embeddedComponentsElement.appendChild(embComponentElement);
					
					//create xml for the embedded composite component
					exportToXML((CompositeSoundComponent) embComponent);
				}
				
				embeddedComponentsHashMap.put(embComponent, componentCounter);
				componentCounter++;
				
			}
			rootElement.appendChild(embeddedComponentsElement);
			
			// links
			Element linksElement = doc.createElement("Links");
			for(Link link : compositeSoundComponentToExport.getLinks()){
				Element linkElement = doc.createElement("Link");
				
				linkElement.setAttribute("TargetPort", link.getTarget().getName());
				linkElement.setAttribute("TargetComponent", Integer.toString(embeddedComponentsHashMap.get(link.getTarget().getComponent())));
				linkElement.setAttribute("SourcePort", link.getSource().getName());
				linkElement.setAttribute("SourceComponent", Integer.toString(embeddedComponentsHashMap.get(link.getSource().getComponent())));			
				
				linksElement.appendChild(linkElement);
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

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
