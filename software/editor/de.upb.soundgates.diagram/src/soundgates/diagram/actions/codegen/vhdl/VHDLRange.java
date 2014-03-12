package soundgates.diagram.actions.codegen.vhdl;

import java.util.ArrayList;

public class VHDLRange extends VHDLElement {
	
	public enum Direction{
		
		TO,
		DOWNTO,
		DONTCARE
	}
	
	private Direction direction;
	
	public int left;
	public int right;
		
	public VHDLRange(int left, int right){
		
		this.left  = left;
		this.right = right;
		
		if(left > right){			
			this.direction = Direction.DOWNTO;
		}else if(left < right){
			this.direction = Direction.TO;
		}else{
			this.direction = Direction.DONTCARE;
		}
		
	}
	
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		 VHDLStringRepresentation rep = new VHDLStringRepresentation();
		 rep.add("(" + Integer.toString(left) + " " + this.direction.toString() + " " + Integer.toString(right) + ")");
		 return rep;
	}

	@Override
	public ArrayList<VHDLElement> getElements() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Direction getDirection(){
		return direction;
	}
}
