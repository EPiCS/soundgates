package soundgates.diagram.actions.codegen.vhdl;

import java.util.ArrayList;

public class VHDLPort extends VHDLElement {
	
	public enum Direction {		
		IN,
		OUT,
		INOUT
	}
	
	private String		 name;
	private VHDLDatatype datatype;
	private	Direction	 direction;
	
	public VHDLPort(String name, Direction direction, VHDLDatatype datatype){
		this.direction = direction;
		this.name 	   = name;
		this.datatype  = datatype;
		
	}
	
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		VHDLStringRepresentation representaion = new VHDLStringRepresentation();
			
		representaion.add(this.name);
		representaion.add("    : ");
		representaion.add(direction.toString());
		representaion.add("      ");
		representaion.add(datatype.getStringRepresentation());
				
		return representaion;
	}

	@Override
	public ArrayList<VHDLElement> getElements() {
		// TODO Auto-generated method stub
		return null;
	}

}
