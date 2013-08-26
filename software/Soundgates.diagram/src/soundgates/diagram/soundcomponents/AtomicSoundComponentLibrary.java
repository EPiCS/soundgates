package soundgates.diagram.soundcomponents;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import soundgates.AtomicSoundComponent;
import soundgates.DataType;
import soundgates.Direction;
import soundgates.Port;
import soundgates.SoundgatesFactory;

public class AtomicSoundComponentLibrary {

	private static AtomicSoundComponentLibrary instance;
	private TreeMap<String, AtomicSoundComponent> components;
	
	public static AtomicSoundComponentLibrary getInstance() {
		if (instance == null) {
			instance = new AtomicSoundComponentLibrary();
			//TODO weg
			createDummyLibrary();
		}
		return instance;
	}
	
	private AtomicSoundComponentLibrary() {
		components = new TreeMap<String, AtomicSoundComponent>();
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
		instance.components.put(c.getType(), c);
		
		return instance;
	}
	
	//TODO implement loading from XML
	public static AtomicSoundComponentLibrary createFromXML() {
		return null;
	}
	
	/**
	 * Creates a copy of a previously loaded AtomicSoundComponent
	 * @param type
	 * @return
	 * @throws NoSuchAtomicSoundComponentException
	 */
	public AtomicSoundComponent createAtomicSoundComponentInstance(String type) throws NoSuchAtomicSoundComponentException {
		AtomicSoundComponent blueprint = components.get(type); 
		
		if (blueprint == null) {
			throw new NoSuchAtomicSoundComponentException();
		}
		
		AtomicSoundComponent copy = SoundgatesFactory.eINSTANCE.createAtomicSoundComponent();
		copy.setName(blueprint.getName());
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
		copy.getBooleanProperties().addAll(blueprint.getBooleanProperties());
		copy.getIntegerProperties().addAll(blueprint.getIntegerProperties());
		copy.getFloatProperties().addAll(blueprint.getFloatProperties());

		return copy;
	}
	
	public List<String> getAvailableTypes() {
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(components.keySet());
		return list;
	}
}


class NoSuchAtomicSoundComponentException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7653687437959450208L;
}
