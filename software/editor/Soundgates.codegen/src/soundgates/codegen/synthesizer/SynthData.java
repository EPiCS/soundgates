package soundgates.codegen.synthesizer;

import java.util.LinkedList;
import java.util.List;

public class SynthData {
	List<ComponentData> components = new LinkedList<ComponentData>();
	List<LinkData> links = new LinkedList<LinkData>();
	public void addComponent(int id, String type, String implName, String implType,  Object value, int hwSlot){
		components.add(new ComponentData(id, type, implName, implType, value, hwSlot));
	}
	
	public void addIOComponent(int id, String type, String implName, String implType, Object value, int hwSlot, String oscAddress, String oscDataType, String range){
		components.add(new IOComponentData(id, type, implName, implType, value, hwSlot, oscAddress, oscDataType, range));
	}
	
	public void addLink(int source, int sink, int sourcePort, int sinkPort){
		links.add(new LinkData(source, sink, sourcePort, sinkPort));
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
		
		result.append("#nodes \n");
		for(Representable component : components){
			result.append(component.getRepresentation() +"\n");
		}
		
		result.append("#edges \n");
		for (Representable link : links) {
			result.append(link.getRepresentation() +"\n");
		}
		
		return result.toString();
	}
}
