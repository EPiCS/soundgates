package soundgates.diagram.soundcomponents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.common.util.EMap;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Port;
import soundgates.SoundgatesFactory;

//TODO needs to check whether there are duplicate keys in the boolean/float/integer properties
public class CompositeSoundComponentLibrary {

	private static CompositeSoundComponentLibrary instance;
	private TreeMap<String, CompositeSoundComponent> components;
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
		this.components.put(component.getName(), component);
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

//		// addAll somehow removes the properties from the blueprint
//		// copy.getBooleanProperties().addAll(blueprint.getBooleanProperties());
//		// copy.getIntegerProperties().addAll(blueprint.getIntegerProperties());
//		// copy.getFloatProperties().addAll(blueprint.getFloatProperties());
//		EMap<String, Float> floatProps = blueprint.getFloatProperties();
//		EMap<String, Boolean> boolProps = blueprint.getBooleanProperties();
//		EMap<String, Integer> intProps = blueprint.getIntegerProperties();
//		EMap<String, String> stringProps = blueprint.getStringProperties();
//		Iterator<String> it;
//
//		it = floatProps.keySet().iterator();
//		while (it.hasNext()) {
//			String key = it.next();
//			// make sure to create a copy of the property value
//			Float value = new Float(floatProps.get(key));
//			copy.getFloatProperties().put(key, value);
//		}
//		it = boolProps.keySet().iterator();
//		while (it.hasNext()) {
//			String key = it.next();
//			Boolean value = new Boolean(boolProps.get(key));
//			copy.getBooleanProperties().put(key, value);
//		}
//		it = intProps.keySet().iterator();
//		while (it.hasNext()) {
//			String key = it.next();
//			Integer value = new Integer(intProps.get(key));
//			copy.getIntegerProperties().put(key, value);
//		}
//		
//		it = stringProps.keySet().iterator();
//		while (it.hasNext()) {
//			String key = it.next();
//			String value = new String(stringProps.get(key));
//			copy.getStringProperties().put(key, value);
//		}

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
}
