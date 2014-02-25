package soundgatesComposite.diagram.soundcomponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.ecore.util.EcoreUtil;

import soundgatesComposite.AtomicSoundComponent;

//TODO needs to check whether there are duplicate keys in the boolean/float/integer properties
public class AtomicSoundComponentLibrary{

	private static AtomicSoundComponentLibrary instance;
	private static HashMap<String,Integer> diagramAndTypeToCounter = new HashMap<>();
	private TreeMap<String, AtomicSoundComponent> components;
	private static IFolder xmlfolder;
	
	public static HashMap<String,String> typeToCategory = new HashMap<>();	
	
	public final static String samplesFolderName = "samples";
	public final static String wavePlayerComponentType = "WavePlayer";
	public final static String IOComponentType = "IO";
	public final static String soundOutputType = "SoundOutput"; 
	public final static String implementationTypeProperty = "Implementation";	

	public static AtomicSoundComponentLibrary getInstance() {
		if (instance == null) {
			instance = new AtomicSoundComponentLibrary();
			String path = xmlfolder.getRawLocation().toString()+"/";
			createFromXML(path);
		}
		return instance;
	}

	private AtomicSoundComponentLibrary() {
		components = new TreeMap<String, AtomicSoundComponent>();
	}

	public static String getNumberedName(String concreteType, String fileName){
		// compute key 
		// key = resource + type
		String name; 
		String key = fileName + concreteType;
		if(diagramAndTypeToCounter.containsKey(key)){
			int number = diagramAndTypeToCounter.get(key);
			
			if(concreteType.equals(IOComponentType))
				name = "/" + concreteType + Integer.toString(number);
			else
				name = concreteType + Integer.toString(number);
			
			diagramAndTypeToCounter.put(key, number+1);
		}
		else{
			if(concreteType.equals(IOComponentType))
				name = "/" + concreteType + Integer.toString(1);
			else
				name = concreteType + Integer.toString(1);
			diagramAndTypeToCounter.put(key, 2);
		}
		return name;
	}
	
	public void addComponent(AtomicSoundComponent component) {
		this.components.put(component.getType(), component);
	}

	public static AtomicSoundComponentLibrary createFromXML(String libraryPath) {
		// TODO check for compliance with XSD
		instance = new AtomicSoundComponentLibrary();
		AtomicSoundComponentXMLHandler.readFromXML(instance, libraryPath);
		return instance;
	}

	/**
	 * Creates a copy of a previously loaded AtomicSoundComponent
	 * 
	 * @param type
	 * @return
	 * @throws NoSuchAtomicSoundComponentException
	 */
	public AtomicSoundComponent createAtomicSoundComponentInstance(String type)
			throws NoSuchAtomicSoundComponentException {
		AtomicSoundComponent blueprint = components.get(type);

		AtomicSoundComponent copy = EcoreUtil.copy(blueprint);
		copy.setType(blueprint.getType());

		return copy;
	}

	public List<String> getAvailableTypes() {
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(components.keySet());
		return list;
	}

	/**
	 * Set's the xml library to a given folder
	 * This requires the library to be reconstructed, so the instance is dereferenced
	 * @param folder
	 */
	public static void setXMLFolder(IFolder folder) {
		instance = null;
		xmlfolder = folder;
	}
	
	public static IFolder getXMLFolder() {
		return xmlfolder;
	}
	
	public static String getProjectPath() {
		return xmlfolder.getProject().getLocation().toPortableString();
	}
}

class NoSuchAtomicSoundComponentException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7653687437959450208L;
}


