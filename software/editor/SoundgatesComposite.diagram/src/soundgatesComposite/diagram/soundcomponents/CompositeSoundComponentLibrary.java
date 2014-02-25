package soundgatesComposite.diagram.soundcomponents;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.ecore.util.EcoreUtil;

import soundgatesComposite.AtomicSoundComponent;
import soundgatesComposite.CompositeSoundComponent;
import soundgatesComposite.SoundComponent;


public class CompositeSoundComponentLibrary{

	private static CompositeSoundComponentLibrary instance;
	private static TreeMap<String, CompositeSoundComponent> components;
	private static IFolder xmlfolder;
	private static HashMap<String,Integer> diagramToCounter = new HashMap<>();
	public static final String defaultName="CompositeComponent";

	public static CompositeSoundComponentLibrary getInstance() {
		if (instance == null) {
			instance = new CompositeSoundComponentLibrary();
			String path = xmlfolder.getRawLocation().toString()+"/";
			createFromXML(path);
		}
		return instance;
	}

	private CompositeSoundComponentLibrary() {
		components = new TreeMap<String, CompositeSoundComponent>();
	}

	public static String getNumberedName(String fileName){
		if(diagramToCounter.containsKey(fileName)){
			int number = diagramToCounter.get(fileName);
			diagramToCounter.put(fileName, number+1);
			return defaultName + Integer.toString(number);		
		}
		else{
			diagramToCounter.put(fileName, 2);
			return defaultName+"1";
		}
	}
	
	public void addComponent(CompositeSoundComponent component) {
		components.put(component.getName(), component);
	}

	public static CompositeSoundComponentLibrary createFromXML(String libraryPath) {
		// TODO check for compliance with XSD
		instance = new CompositeSoundComponentLibrary();
		CompositeSoundComponentXMLHandler.readFromXML(instance, libraryPath);
		return instance;
	}

	public CompositeSoundComponent createCompositeSoundComponentInstance(String compositeSoundComponentName) {
		CompositeSoundComponent blueprint = components.get(compositeSoundComponentName);

		CompositeSoundComponent compositeSoundComponentCopy = EcoreUtil.copy(blueprint);
			
		//walkaround
		for(SoundComponent soundComponent : compositeSoundComponentCopy.getEmbeddedComponents()){
			if(soundComponent instanceof AtomicSoundComponent){
				AtomicSoundComponent atomicSoundComponent = (AtomicSoundComponent) soundComponent;
				atomicSoundComponent.setType(atomicSoundComponent.getStringProperties().get("Type"));
				atomicSoundComponent.getStringProperties().removeKey("Type");
			}
		}

		return compositeSoundComponentCopy;
	}

	public List<String> getAvailableComponents() {
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
	
	public static boolean compositeSoundComponentIsInLibrary(String name){
		for(String libComponent : components.keySet()){
			if(libComponent.equals(name))
				return true;
		}
		return false;
	}
	
	public static boolean componentsFolderContaintsFile(String fileName){
		File folder = new File(xmlfolder.getRawLocation().toString());
		for(File file : folder.listFiles()){
			if(file.getName().equals(fileName))
				return true;
		}
		return false;
	}
}
