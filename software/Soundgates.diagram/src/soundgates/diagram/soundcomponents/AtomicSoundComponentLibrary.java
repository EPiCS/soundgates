package soundgates.diagram.soundcomponents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.common.util.EMap;
import soundgates.AtomicSoundComponent;
import soundgates.DataType;
import soundgates.Direction;
import soundgates.Port;
import soundgates.SoundgatesFactory;

//TODO needs to check whether there are duplicate keys in the boolean/float/integer properties
public class AtomicSoundComponentLibrary {

	private static AtomicSoundComponentLibrary instance;
	private TreeMap<String, AtomicSoundComponent> components;
	private static IFolder xmlfolder;

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

	public void addComponent(AtomicSoundComponent component) {
		this.components.put(component.getType(), component);
	}

	public static AtomicSoundComponentLibrary createDummyLibrary() {
		instance = new AtomicSoundComponentLibrary();
		AtomicSoundComponent c = SoundgatesFactory.eINSTANCE.createAtomicSoundComponent();
		c.setName("Dummy 1");
		c.setType("d1");
		Port in = SoundgatesFactory.eINSTANCE.createPort();
		Port out = SoundgatesFactory.eINSTANCE.createPort();
		in.setName("DummyBoolIn");
		in.setDataType(DataType.BOOLEAN);
		in.setDirection(Direction.IN);
		out.setName("DummySoundOut");
		out.setDataType(DataType.SOUND);
		out.setDirection(Direction.OUT);
		c.getPorts().add(in);
		c.getPorts().add(out);

		c.getFloatProperties().put("SomeValue", 1.0f);
		c.getFloatProperties().put("AnotherValue", 3.14f);
		c.getIntegerProperties().put("IntProp", 33);
		c.getBooleanProperties().put("BoolProp", true);
		instance.components.put(c.getType(), c);

		AtomicSoundComponent c2 = SoundgatesFactory.eINSTANCE.createAtomicSoundComponent();
		c2.setName("Boolean dummy");
		c2.setType("BoolDummy");
		c2.getBooleanProperties().put("pommes", false);
		instance.components.put(c2.getType(), c2);

		return instance;
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

		if (blueprint == null) {
			throw new NoSuchAtomicSoundComponentException();
		}

		AtomicSoundComponent copy = SoundgatesFactory.eINSTANCE.createAtomicSoundComponent();
		if (blueprint.getName() != null && blueprint.getName().length() > 0) {
			copy.setName(blueprint.getName());
		} else {
			copy.setName(blueprint.getType());
		}
		copy.setType(blueprint.getType());
		Iterator<Port> pi = blueprint.getPorts().iterator();
		while (pi.hasNext()) {
			Port portCopy = SoundgatesFactory.eINSTANCE.createPort();
			Port portBlueprint = pi.next();
			portCopy.setDataType(portBlueprint.getDataType());
			portCopy.setDirection(portBlueprint.getDirection());
			portCopy.setName(portBlueprint.getName());
			copy.getPorts().add(portCopy);
		}

		// addAll somehow removes the properties from the blueprint
		// copy.getBooleanProperties().addAll(blueprint.getBooleanProperties());
		// copy.getIntegerProperties().addAll(blueprint.getIntegerProperties());
		// copy.getFloatProperties().addAll(blueprint.getFloatProperties());
		EMap<String, Float> floatProps = blueprint.getFloatProperties();
		EMap<String, Boolean> boolProps = blueprint.getBooleanProperties();
		EMap<String, Integer> intProps = blueprint.getIntegerProperties();
		Iterator<String> it;

		it = floatProps.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			// make sure to create a copy of the property value
			Float value = new Float(floatProps.get(key));
			copy.getFloatProperties().put(key, value);
		}
		it = boolProps.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Boolean value = new Boolean(boolProps.get(key));
			copy.getBooleanProperties().put(key, value);
		}
		it = intProps.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Integer value = new Integer(intProps.get(key));
			copy.getIntegerProperties().put(key, value);
		}

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
}

class NoSuchAtomicSoundComponentException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7653687437959450208L;
}
