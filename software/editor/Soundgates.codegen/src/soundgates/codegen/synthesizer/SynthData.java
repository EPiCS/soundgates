package soundgates.codegen.synthesizer;

import java.util.LinkedList;
import java.util.List;

public class SynthData {
	List<ComponentData> components = new LinkedList<ComponentData>();
	List<LinkData> links = new LinkedList<LinkData>();
	public void addComponent(){
		components.add(new ComponentData());
	}
	public void addLink(){
		links.add(new LinkData());
	}
	public List<ComponentData> getComponents(){
		return components;		
	}
	public List<LinkData> getLinks(){
		return links;
	}
	public void addSynthData(SynthData data){
		components.addAll(data.getComponents());
		links.addAll(data.getLinks());
	}
	public String getRepresentation(){
		StringBuilder result = new StringBuilder();
		for(Representable component : components){
			result.append(component.getRepresentation());
		}
		result.append("#\n");
		for (Representable link : links) {
			result.append(link.getRepresentation());
		}
		return result.toString();
	}
}
