package soundgates.codegen.synthesizer;

import java.util.HashMap;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Connection;
import soundgates.Delegation;
import soundgates.Element;
import soundgates.Link;
import soundgates.Patch;
import soundgates.Port;
import soundgates.SoundComponent;

public class SynthDataGen {
	private HashMap<SoundComponent, Integer> uniqueIds;
	private int uniqueCounter = 0;
	public SynthData generateSynthData(Patch patch){
		SynthData synthData = new SynthData();
		uniqueIds = new HashMap<SoundComponent, Integer>();
		for (Element element : patch.getElements()) {
			handleElement(element);
		}
		return synthData;
	}
	
	public SynthData handleElement(Element element){
		SynthData data = new SynthData();
		if (element instanceof CompositeSoundComponent){
			data.addSynthData(handleComposite((CompositeSoundComponent) element));
		} else if (element instanceof AtomicSoundComponent){
			if (!uniqueIds.containsKey((SoundComponent)element)){
				uniqueIds.put((SoundComponent) element, uniqueCounter++);
			}
			String implementationType = getBestImplementationType((AtomicSoundComponent)element);
			String oscAddress = "";
			String oscDataType = "";
			int hwSlot = 0;
			Object value = "";
			String type = "";
			data.addComponent(uniqueIds.get(element), implementationType, type, value, hwSlot, oscAddress, oscDataType);
		} else if (element instanceof Link){
			int sourcePort = 0;
			int sinkPort = 0;
			ComponentData source = null;
			ComponentData sink = null;
			data.addLink(sourcePort, sinkPort, source, sink);
		}
		return data;
	}
	
	
	
	private String getBestImplementationType(AtomicSoundComponent atomicSoundComponent){
		//TODO: actual implementation
		return "hw";
	}
	
	public SynthData handleComposite(CompositeSoundComponent composite){
		SynthData data = new SynthData();
		for (SoundComponent component : composite.getEmbeddedComponents()) {
			data.addSynthData(handleElement(component));
		}
		for (Delegation delegation: composite.getDelegations()){
			// find outgoing port of source atomic component
			Port sourcePort = delegation.getSource();
			Port targetPort = delegation.getTarget();
			if (sourcePort.getComponent() instanceof CompositeSoundComponent){
				sourcePort = sourcePort.getIncomingConnection().getSource();
			}
			if (targetPort.getComponent() instanceof CompositeSoundComponent){
				for (Connection connection : targetPort.getOutgoingConnection()){
					ComponentData source = null;
					ComponentData sink = null;
					int sourcePortNumber = 0;
					int sinkPortNumber = 0;
					//FIXME: add as parameters the target of the different outgoing edges and the source port
					data.addLink(sourcePortNumber, sinkPortNumber, source, sink);
				}
			} else {
				ComponentData source = null;
				ComponentData sink = null;
				int sourcePortNumber = 0;
				int sinkPortNumber = 0;
				//FIXME: add as parameters source and target
				data.addLink(sourcePortNumber, sinkPortNumber, source, sink);
			}
		}
		return data;
	}
}