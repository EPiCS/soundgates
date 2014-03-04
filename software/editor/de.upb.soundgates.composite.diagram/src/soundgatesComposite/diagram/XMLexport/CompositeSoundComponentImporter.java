package soundgatesComposite.diagram.XMLexport;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import soundgatesComposite.AtomicSoundComponent;
import soundgatesComposite.CompositeSoundComponent;
import soundgatesComposite.Link;
import soundgatesComposite.Port;
import soundgatesComposite.SoundComponent;
import soundgatesComposite.SoundgatesCompositeFactory;
import soundgatesComposite.Workbench;
import soundgatesComposite.diagram.edit.parts.WorkbenchEditPart;
import soundgatesComposite.diagram.messageDialogs.MessageDialogs;
import soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin;
import soundgatesComposite.diagram.soundcomponents.AtomicSoundComponentLibrary;
import soundgatesComposite.diagram.soundcomponents.CompositeSoundComponentLibrary;
import soundgatesComposite.impl.SoundgatesCompositeFactoryImpl;


public class CompositeSoundComponentImporter{
	
	public static Workbench getWorkbenchFromXML(Document doc) {
		try {

				
			Workbench workbench = SoundgatesCompositeFactory.eINSTANCE.createWorkbench();
			
				Element rootElement = (Element) doc.getElementsByTagName("CompositeSoundComponent").item(0);
				
				CompositeSoundComponent compositeSoundComponent = SoundgatesCompositeFactory.eINSTANCE.createCompositeSoundComponent();
				compositeSoundComponent.setName("NewCompositeSoundComponent");
				workbench.getCompositeSoundComponents().add(compositeSoundComponent);
				
				Element embeddedSoundComponentsElement = ((Element) rootElement.getElementsByTagName("EmbeddedSoundComponents").item(0));
				
				NodeList atomicSoundComponents  = embeddedSoundComponentsElement.getElementsByTagName("EmbeddedAtomicSoundComponent");
				NodeList compositeSoundComponents  = embeddedSoundComponentsElement.getElementsByTagName("EmbeddedCompositeSoundComponent");
				
				Element linksElement = (Element) rootElement.getElementsByTagName("Links").item(0);
				
				NodeList links = linksElement.getElementsByTagName("Link");
				
				HashMap<Integer, SoundComponent> soundComponentsMapping = new HashMap<Integer, SoundComponent>();

				for (int i = 0; i < atomicSoundComponents.getLength(); i++) {					
					Element atomicElement = (Element) atomicSoundComponents.item(i);
						
					AtomicSoundComponent atomicSoundComponent = null;
					try{
						atomicSoundComponent = AtomicSoundComponentLibrary.getInstance().
								createAtomicSoundComponentInstance(atomicElement.getAttribute("Type"));
					}
					catch(Exception e){
						MessageDialogs.atomicComponentMissing(atomicElement.getAttribute("Type"));
						return null;
					}
					
					soundComponentsMapping.put(Integer.parseInt(atomicElement.getAttribute("Id")), atomicSoundComponent);
					
					atomicSoundComponent.setName(atomicElement.getAttribute("Name"));
					
					//walkaround
					atomicSoundComponent.getStringProperties().put("Type", atomicElement.getAttribute("Type"));							
					
					Element propertiesNode = (Element) atomicElement.getElementsByTagName("Properties").item(0);							
					XMLHandler.addPropertiesToSoundComponentFromPropertiesElement(atomicSoundComponent, propertiesNode, "Value");
					
					compositeSoundComponent.getEmbeddedComponents().add(atomicSoundComponent);					
				}
				
				for (int i = 0; i < compositeSoundComponents.getLength(); i++) {					
					Element compositeElement = (Element) compositeSoundComponents.item(i);
					
					CompositeSoundComponent embeddedCompositeSoundComponent = null;
					try{
						embeddedCompositeSoundComponent = CompositeSoundComponentLibrary.getInstance().
								createCompositeSoundComponentInstance(compositeElement.getAttribute("Name"));
					}
					catch(Exception e){
						MessageDialogs.compositeComponentMissing(compositeElement.getAttribute("Name"));
						return null;
					}
					
					soundComponentsMapping.put(Integer.parseInt(compositeElement.getAttribute("Id")), embeddedCompositeSoundComponent);
					
					embeddedCompositeSoundComponent.setName(compositeElement.getAttribute("Name"));
					
					compositeSoundComponent.getEmbeddedComponents().add(embeddedCompositeSoundComponent);					
				}
				
				for (int i = 0; i < links.getLength(); i++) {					
					Element linkElement = (Element) links.item(i);
					
					Link link = SoundgatesCompositeFactory.eINSTANCE.createLink();
					
					SoundComponent sourceComponent = soundComponentsMapping.get(
							Integer.parseInt(linkElement.getAttribute("SourceComponent")));
					SoundComponent targetComponent = soundComponentsMapping.get(
							Integer.parseInt(linkElement.getAttribute("TargetComponent")));
					
					Port sourcePort = findPort(sourceComponent.getPorts(), linkElement.getAttribute("SourcePort"));
					Port targetPort = findPort(targetComponent.getPorts(), linkElement.getAttribute("TargetPort"));
					link.setSource(sourcePort);
					link.setTarget(targetPort);				
					
					compositeSoundComponent.getLinks().add(link);
				}
				return workbench;
		}	 
			catch (NullPointerException e) {
				//TODO Differenziertere Exceptions
				System.out.println("Malformed XML Document!");
				e.printStackTrace();
			} 
		return null;
	}
	
	public static Port findPort(EList<Port> ports, String portName){
		for(Port port : ports)
			if (port.getName().equals(portName))
				return port;
		return null;
	}
	
	public static void createWorkbenchWithCompositeSoundComponentFromXML(String filePath, Document doc) throws IOException {  
		  Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		  Map<String, Object> m = reg.getExtensionToFactoryMap();
		  m.put(".sgcd", new SoundgatesCompositeFactoryImpl());

		  Workbench patch = getWorkbenchFromXML(doc);
		  
		  if(patch!=null){
			  ResourceSet resSet = new ResourceSetImpl();
			  Resource resource = resSet.createResource(URI.createFileURI(filePath));
			  resource.getContents().add(patch);
			  
			  Diagram diag = 
			  ViewService.createDiagram(
					  patch,
						WorkbenchEditPart.MODEL_ID,
						SoundgatesCompositeDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
			  
			  resource.getContents().add(diag);
			  
			  resource.save(Collections.EMPTY_MAP);

			  openDiagram(filePath);
		  }
	}
	
	private static void openDiagram(String filePath){
		try {
		  String[] segments = filePath.split("/");
		  String name = segments[segments.length-1];
		  IFile file = AtomicSoundComponentLibrary.getXMLFolder().getProject().getFile(name);
		  
		  IWorkbenchPage page =  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		  
		  IDE.openEditor(page, file, true);
		} catch(Exception e){
			 
		}
	}
	
}		
