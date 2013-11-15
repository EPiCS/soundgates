package soundgates.diagram.soundcomponents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.eclipse.core.resources.IFolder;

import soundgates.CompositeSoundComponent;
import soundgates.Port;
import soundgates.SoundgatesFactory;


public class CompositeSoundComponentLibrary {

	private static CompositeSoundComponentLibrary instance;
	private static TreeMap<String, CompositeSoundComponent> components;
	private static IFolder xmlfolder;

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

	public void addComponent(CompositeSoundComponent component) {
		components.put(component.getName(), component);
	}

	public static CompositeSoundComponentLibrary createFromXML(String libraryPath) {
		// TODO check for compliance with XSD
		instance = new CompositeSoundComponentLibrary();
		CompositeSoundComponentXMLHandler.readFromXML(instance, libraryPath);
		return instance;
	}

	public CompositeSoundComponent createCompositeSoundComponentInstance(String name) {
		CompositeSoundComponent blueprint = components.get(name);

		CompositeSoundComponent copy = SoundgatesFactory.eINSTANCE.createCompositeSoundComponent();
		copy.setName(blueprint.getName());
		Iterator<Port> pi = blueprint.getPorts().iterator();
		while (pi.hasNext()) {
			Port portCopy = SoundgatesFactory.eINSTANCE.createPort();
			Port portBlueprint = pi.next();
			portCopy.setDataType(portBlueprint.getDataType());
			portCopy.setDirection(portBlueprint.getDirection());
			portCopy.setName(portBlueprint.getName());
			copy.getPorts().add(portCopy);
		}

		return copy;
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
}
