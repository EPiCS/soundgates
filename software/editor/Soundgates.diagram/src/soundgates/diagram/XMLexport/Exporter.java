package soundgates.diagram.XMLexport;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Link;
import soundgates.SoundComponent;

public class Exporter {
	
	public static Element getAtomicSoundComponentElement(Document doc, AtomicSoundComponent atomicSoundComponent, String elementName, int componentCounter){	
		
		Element atomicSoundComponentElement = doc.createElement(elementName);				
		atomicSoundComponentElement.setAttribute("Type", atomicSoundComponent.getType());
		atomicSoundComponentElement.setAttribute("Name", atomicSoundComponent.getName());
		atomicSoundComponentElement.setAttribute("Id", Integer.toString(componentCounter));					
		
		Element properties = doc.createElement("Properties");		
		//integer properties
		for(int i=0; i<atomicSoundComponent.getIntegerProperties().size(); i++){						
			Element integerProperty = doc.createElement("IntProperty");						
			integerProperty.setAttribute("Name",atomicSoundComponent.getIntegerProperties().get(i).getKey().toString());
			integerProperty.setAttribute("Value",atomicSoundComponent.getIntegerProperties().get(i).getValue().toString());						
			properties.appendChild(integerProperty);
		}

		//float properties
		for(int i=0; i<atomicSoundComponent.getFloatProperties().size(); i++){						
			Element floatProperty = doc.createElement("FloatProperty");
			floatProperty.setAttribute("Name", atomicSoundComponent.getFloatProperties().get(i).getKey().toString());						
			floatProperty.setAttribute("Value", atomicSoundComponent.getFloatProperties().get(i).getValue().toString());						
			properties.appendChild(floatProperty);
		}
		
		//boolean properties
		for(int i=0; i<atomicSoundComponent.getBooleanProperties().size(); i++){						
			Element booleanProperty = doc.createElement("BoolProperty");						
			booleanProperty.setAttribute("Name",atomicSoundComponent.getBooleanProperties().get(i).getKey().toString());
			booleanProperty.setAttribute("Value",atomicSoundComponent.getBooleanProperties().get(i).getValue().toString());						
			properties.appendChild(booleanProperty);
		}
		
		//user string properties
		for(int i=0; i<atomicSoundComponent.getUserStringProperties().size(); i++){						
			Element userStringProperty = doc.createElement("UserStringProperty");						
			userStringProperty.setAttribute("Name",atomicSoundComponent.getUserStringProperties().get(i).getKey().toString());
			userStringProperty.setAttribute("Value",atomicSoundComponent.getUserStringProperties().get(i).getValue().toString());						
			properties.appendChild(userStringProperty);
		}
		
		//string property - impl type
		for(int i=0; i<atomicSoundComponent.getStringProperties().size(); i++){
			if(atomicSoundComponent.getStringProperties().get(i).getKey().equals("implType")){
				Element stringProperty = doc.createElement("StringProperty");						
				stringProperty.setAttribute("Name",atomicSoundComponent.getStringProperties().get(i).getKey().toString());
				stringProperty.setAttribute("Value",atomicSoundComponent.getStringProperties().get(i).getValue().toString());						
				properties.appendChild(stringProperty);
			}			
		}
		atomicSoundComponentElement.appendChild(properties);
		
		return atomicSoundComponentElement;
	}
	
	public static Element getCompositeSoundComponentElement(Document doc, CompositeSoundComponent compositeSoundComponent, String elementName, int componentCounter){	

		Element compositeSoundComponentElement = doc.createElement(elementName);		
		compositeSoundComponentElement.setAttribute("Name", compositeSoundComponent.getName());
		compositeSoundComponentElement.setAttribute("Id", Integer.toString(componentCounter));
		return compositeSoundComponentElement;
	}
	
	public static Element getLinkElement(Document doc, Link link, HashMap<SoundComponent,Integer> componentsHashMap){
		Element linkElement = doc.createElement("Link");
	
		linkElement.setAttribute("SourceComponent", Integer.toString(componentsHashMap.get(link.getSource().getComponent())));
		linkElement.setAttribute("SourcePort", link.getSource().getName());
		linkElement.setAttribute("TargetComponent", Integer.toString(componentsHashMap.get(link.getTarget().getComponent())));
		linkElement.setAttribute("TargetPort", link.getTarget().getName());		
		
		return linkElement;
	}
}
