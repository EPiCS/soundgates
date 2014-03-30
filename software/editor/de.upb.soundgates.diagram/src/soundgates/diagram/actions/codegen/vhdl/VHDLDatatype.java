package soundgates.diagram.actions.codegen.vhdl;

import java.util.ArrayList;

public class VHDLDatatype extends VHDLElement {
	
	private ArrayList<VHDLElement>	elements;
	
	private String 		name;
	public VHDLDatatype(String name){
		this.name = name;
		elements  = new ArrayList<VHDLElement>();
	}
	
	public VHDLDatatype(String name, VHDLRange range){
		this(name);
		
		this.elements.add(range);
	}
	
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		VHDLStringRepresentation representation =  new VHDLStringRepresentation();
		
		representation.add(name);
		
		if(elements.size() > 0){
			for(VHDLElement range : elements){				
				representation.add(range.getStringRepresentation());
			}
		}
		
		return representation;
	}

	@Override
	public ArrayList<VHDLElement> getElements() {
		return elements;
	}

}
