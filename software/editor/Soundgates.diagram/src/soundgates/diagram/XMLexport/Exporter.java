package soundgates.diagram.XMLexport;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import soundgates.AtomicSoundComponent;
import soundgates.Link;
import soundgates.SoundComponent;

public class Exporter {
	
	public Element getAtomicSoundComponentElement(Document doc, AtomicSoundComponent atomicSoundComponent, int componentCounter){	
		
		Element atomicSoundComponentElement = doc.createElement("AtomicSoundComponent");				
		atomicSoundComponentElement.setAttribute("Type", atomicSoundComponent.getType());
		atomicSoundComponentElement.setAttribute("Name", atomicSoundComponent.getName());
		atomicSoundComponentElement.setAttribute("Id", Integer.toString(componentCounter));					
		
		//integer properties
		for(int i=0; i<atomicSoundComponent.getIntegerProperties().size(); i++){						
			Element integerProperty = doc.createElement("IntProperty");						
			integerProperty.setAttribute("Name",atomicSoundComponent.getIntegerProperties().get(i).getKey().toString());
			integerProperty.setAttribute("Value",atomicSoundComponent.getIntegerProperties().get(i).getValue().toString());						
			atomicSoundComponentElement.appendChild(integerProperty);
		}

		//float properties
		for(int i=0; i<atomicSoundComponent.getFloatProperties().size(); i++){						
			Element floatProperty = doc.createElement("FloatProperty");
			floatProperty.setAttribute("Name", atomicSoundComponent.getFloatProperties().get(i).getKey().toString());						
			floatProperty.setAttribute("Value", atomicSoundComponent.getFloatProperties().get(i).getValue().toString());						
			atomicSoundComponentElement.appendChild(floatProperty);
		}
		
		//boolean properties
		for(int i=0; i<atomicSoundComponent.getBooleanProperties().size(); i++){						
			Element booleanProperty = doc.createElement("BoolProperty");						
			booleanProperty.setAttribute("Name",atomicSoundComponent.getBooleanProperties().get(i).getKey().toString());
			booleanProperty.setAttribute("Value",atomicSoundComponent.getBooleanProperties().get(i).getValue().toString());						
			atomicSoundComponentElement.appendChild(booleanProperty);
		}
		
		return atomicSoundComponentElement;
	}
		
	public Element getLinkElement(Document doc, Link link, HashMap<SoundComponent,Integer> componentsHashMap){
		Element linkElement = doc.createElement("Link");		
		linkElement.setAttribute("TargetPort", link.getTarget().getName());
		linkElement.setAttribute("TargetComponent", Integer.toString(componentsHashMap.get(link.getTarget().getComponent())));
		linkElement.setAttribute("SourcePort", link.getSource().getName());
		linkElement.setAttribute("SourceComponent", Integer.toString(componentsHashMap.get(link.getSource().getComponent())));	
		return linkElement;
	}
}
