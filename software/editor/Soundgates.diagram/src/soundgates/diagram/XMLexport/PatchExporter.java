package soundgates.diagram.XMLexport;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Link;
import soundgates.Patch;
import soundgates.SoundComponent;
import soundgates.SoundgatesPackage;

public class PatchExporter extends Exporter{

	public static void exportToXML(String location, Patch patchToExport, String patchName) {
	
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			HashMap<SoundComponent,Integer> componentsHashMap = new HashMap<SoundComponent, Integer>();
			LinkedList<Link> links = new LinkedList<>();
			int componentCounter = 0;
			
			// root element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("Patch");
			doc.appendChild(rootElement);
			
			// elements
			Element elements = doc.createElement("Elements");
			
			for(soundgates.Element element : patchToExport.getElements()){
				
				// atomic components
				if(element instanceof AtomicSoundComponent){					
					elements.appendChild(
							getAtomicSoundComponentElement(doc, (AtomicSoundComponent)element, "AtomicSoundComponent", componentCounter)
							);					
					componentsHashMap.put((SoundComponent) element, componentCounter);
					componentCounter++;
				}
				// composite components
				else if(element instanceof CompositeSoundComponent){							
					
					elements.appendChild(
							getCompositeSoundComponentElement(doc, (CompositeSoundComponent)element, "CompositeSoundComponent", componentCounter)
							);						
					componentsHashMap.put((SoundComponent) element, componentCounter);
					componentCounter++;					
				}
				
				// collect links
				else if(element instanceof Link){
					links.add((Link) element);
				}				
			}		
			
			// links
			for(Link link : links){
				elements.appendChild(getLinkElement(doc, link, componentsHashMap));
			}	
			
			rootElement.appendChild(elements);
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
			String filePath = location +"/"+patchName+".xml";
			StreamResult result = new StreamResult(new File(filePath));
			
			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
	
	public static Patch getPatch(String path){
	    SoundgatesPackage.eINSTANCE.eClass();
	    
	    // Register the XMI resource factory for the .xmi extension

	    Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	    Map<String, Object> m = reg.getExtensionToFactoryMap();
	    m.put("xmi", new XMIResourceFactoryImpl());

	    // Obtain a new resource set
	    ResourceSet resSet = new ResourceSetImpl();

	    // Get the resource
	    Resource resource = resSet.getResource(URI.createURI(path), true);
	    // Get the first model element and cast it to the right type, in my
	    // example everything is hierarchical included in this first node
	    Patch patch = (Patch) resource.getContents().get(0);
	    return patch;		
	}

}
