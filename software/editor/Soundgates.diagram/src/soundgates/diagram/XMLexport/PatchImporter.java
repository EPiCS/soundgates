package soundgates.diagram.XMLexport;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Link;
import soundgates.Patch;
import soundgates.Port;
import soundgates.SoundComponent;
import soundgates.SoundgatesFactory;
import soundgates.diagram.messageDialogs.MessageDialogs;
import soundgates.diagram.soundcomponents.AtomicSoundComponentLibrary;
import soundgates.diagram.soundcomponents.CompositeSoundComponentLibrary;
import soundgates.diagram.soundcomponents.XMLHandler;
import soundgates.impl.SoundgatesFactoryImpl;


public class PatchImporter{
	
	public Patch getPatchXML(String filePath) {
		try {

				File file = new File(filePath);

				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = dBuilder.parse(file);
				
				Element patchElement = (Element) doc.getElementsByTagName("Patch").item(0);
				Patch patch = SoundgatesFactory.eINSTANCE.createPatch();
				
				Element elements = ((Element) patchElement.getElementsByTagName("Elements").item(0));
				
				NodeList atomicSoundComponents  = elements.getElementsByTagName("AtomicSoundComponent");
				NodeList compositeSoundComponents  = elements.getElementsByTagName("CompositeSoundComponent");
				NodeList links = elements.getElementsByTagName("Link");
				
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
					
					patch.getElements().add(atomicSoundComponent);					
				}
				
				for (int i = 0; i < compositeSoundComponents.getLength(); i++) {					
					Element compositeElement = (Element) compositeSoundComponents.item(i);
					
					CompositeSoundComponent compositeSoundComponent = null;
					try{
						compositeSoundComponent = CompositeSoundComponentLibrary.getInstance().
								createCompositeSoundComponentInstance(compositeElement.getAttribute("Name"));
					}
					catch(Exception e){
						MessageDialogs.compositeComponentMissing(compositeElement.getAttribute("Name"));
						return null;
					}
					
					soundComponentsMapping.put(Integer.parseInt(compositeElement.getAttribute("Id")), compositeSoundComponent);
					
					compositeSoundComponent.setName(compositeElement.getAttribute("Name"));
					
					patch.getElements().add(compositeSoundComponent);					
				}
				
				for (int i = 0; i < links.getLength(); i++) {					
					Element linkElement = (Element) links.item(i);
					
					Link link = SoundgatesFactory.eINSTANCE.createLink();
					
					SoundComponent sourceComponent = soundComponentsMapping.get(
							Integer.parseInt(linkElement.getAttribute("SourceComponent")));
					SoundComponent targetComponent = soundComponentsMapping.get(
							Integer.parseInt(linkElement.getAttribute("TargetComponent")));
					
					Port sourcePort = findPort(sourceComponent.getPorts(), linkElement.getAttribute("SourcePort"));
					Port targetPort = findPort(targetComponent.getPorts(), linkElement.getAttribute("TargetPort"));
					link.setSource(sourcePort);
					link.setTarget(targetPort);				
					
					patch.getElements().add(link);					
				}
				return patch;
		}	 
			catch (NullPointerException e) {
				//TODO Differenziertere Exceptions
				System.out.println("Malformed XML Document!");
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
	
	public void createPatchFromXML(String newFilePath, String patchFile, String newFileName) throws IOException {  
		  Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		  Map<String, Object> m = reg.getExtensionToFactoryMap();
		  m.put(".soundgates", new SoundgatesFactoryImpl());

		  Patch patch = getPatchXML(patchFile);
		  
		  if(patch!=null){
			  ResourceSet resSet = new ResourceSetImpl();
			  Resource resource = resSet.createResource(URI.createFileURI(newFilePath));
			  resource.getContents().add(patch);
			  resource.save(Collections.EMPTY_MAP);
			  
			  MessageDialogs.patchtWasImported(newFileName);
		  }
	}
	
}		
