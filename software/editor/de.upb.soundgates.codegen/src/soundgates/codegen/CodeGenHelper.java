package soundgates.codegen;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmf.runtime.notation.Diagram;

import soundgates.AtomicSoundComponent;
import soundgates.Patch;
import soundgates.Port;
import soundgates.SoundgatesPackage;

public class CodeGenHelper {
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
	
	public static Diagram getDiagram(String path){
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
	    Diagram diagram = (Diagram) resource.getContents().get(1);
	    return diagram;		
	}
	
	public static HashMap<Port, Integer> parsePortMappings(AtomicSoundComponent atomicSoundComponent, String prefix){
		HashMap<Port,Integer> resultingMapping = new HashMap<Port, Integer>();
		// get port mapping
		if (atomicSoundComponent.getStringProperties().containsKey(prefix)){
		
			//example: PortName="Value"|PortNumber="0"||...
			String portMapping = atomicSoundComponent.getStringProperties().get(prefix);
			
			// parse ports
			String[] entries = portMapping.split("\\|\\|");
			
			for (String entry : entries) {
				if (entry.contains("|")) {
					String[] subEntry = entry.split("\\|");
					String[] portNameEntry = subEntry[0].split("=");
					String portName = portNameEntry[1].replace("\"", "");
					String[] portNumberEntry = subEntry[1].split("=");
					Integer portNumber = Integer.parseInt(portNumberEntry[1].replace("\"", ""));

					// search for the port
					try {
						Port port = portSearch(atomicSoundComponent, portName);
						resultingMapping.put(port, portNumber);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return resultingMapping;
	}
	
	public static Port portSearch(AtomicSoundComponent atomicSoundComponent, String portName) throws Exception{
		for (Port port : atomicSoundComponent.getPorts()){
			if (port.getName().equals(portName)){
				return port;
			}
		}
		throw new Exception("Port not found in component");
	}
	
}
