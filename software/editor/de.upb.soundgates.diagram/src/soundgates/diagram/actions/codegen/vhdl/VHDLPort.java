package soundgates.diagram.actions.codegen.vhdl;

import java.util.ArrayList;

import soundgates.Direction;
import soundgates.VHDLPortDescriptor;

public class VHDLPort extends VHDLElement implements IVHDLLabeledElement {
	
	public enum Direction {		
		IN,
		OUT,
		INOUT
	}
	
	private String		 name;
	private VHDLDatatype datatype;
	private	Direction	 direction;
	
	public VHDLPort(String name, Direction direction, VHDLDatatype datatype){
		this.name 	   = name;
		this.direction = direction;
		this.datatype  = datatype;		
	}
	
	public VHDLPort(VHDLPortDescriptor port){
		
		
		this.name    			= port.getVhdlName(); 
		
		
		if(!port.isVector()){
			this.datatype = new VHDLDatatype(port.getDatatype());
			
		}else{
			this.datatype = new VHDLDatatype(port.getDatatype(), new VHDLRange(port.getRange().getLeftValue(), port.getRange().getRightValue()));
		}
		
		if(port.getDir() == soundgates.Direction.IN){
			
			this.direction = VHDLPort.Direction.IN;			
	
		}else if(port.getDir() == soundgates.Direction.OUT){
			
			this.direction = VHDLPort.Direction.OUT;		
		}else {
			
			throw new RuntimeException("Unknown port direction: " + port.getDir().toString());
		}	
	}
	
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		VHDLStringRepresentation representaion = new VHDLStringRepresentation();
			
		representaion.add(getLabel());
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
	
	@Override
	public String getLabel() {
		return this.name;
	}

}
