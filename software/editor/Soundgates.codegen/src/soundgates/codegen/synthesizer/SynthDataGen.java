package soundgates.codegen.synthesizer;

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
	public static SynthData generateSynthData(Patch patch){
		SynthData synthData = new SynthData();
		for (Element element : patch.getElements()) {
			handleElement(element);
		}
		return synthData;
	}
	
	public static SynthData handleElement(Element element){
		SynthData data = new SynthData();
		if (element instanceof CompositeSoundComponent){
			data.addSynthData(handleComposite((CompositeSoundComponent) element));
		} else if (element instanceof AtomicSoundComponent){
			data.addComponent();
		} else if (element instanceof Link){
			data.addLink();
		}
		return data;
	}
	
	public static SynthData handleComposite(CompositeSoundComponent composite){
		SynthData data = new SynthData();
		for (SoundComponent component : composite.getEmbeddedComponents()) {
			data.addSynthData(handleElement(component));
		}
		for (Delegation delegation: composite.getDelegations()){
			// find outgoing port of source atomic component
			Port source = delegation.getSource();
			Port target = delegation.getTarget();
			if (source.getComponent() instanceof CompositeSoundComponent){
				source = source.getIncomingConnection().getSource();
			}
			if (target.getComponent() instanceof CompositeSoundComponent){
				for (Connection connection : target.getOutgoingConnection()){
					//FIXME: add as parameters the target of the different outgoing edges and the source port
					data.addLink();
				}
			} else {
				//FIXME: add as parameters source and target
				data.addLink();
			}
		}
		return data;
	}
}