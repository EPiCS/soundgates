package soundgates.diagram.actions.codegen.vhdl;

import java.util.ArrayList;

public class VHDLRange extends VHDLElement {
	
	public enum Direction{
		
		TO,
		DOWNTO,
		DONTCARE
	}
	
	private Direction direction;
	
	private int 	left;
	private int 	right;
	
	private String 	lhsName;
	private String 	rhsName;
	
	private VHDLRange(){
		
		this.left 	 = Integer.MIN_VALUE;
		this.right	 = Integer.MIN_VALUE;
		
		this.lhsName = "";
		this.rhsName = "";
	}
	
	public VHDLRange(int left, int right){
		
		this();
		
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
	
	public VHDLRange(int left, String rhsName, Direction direction){
		
		this();
		
		this.left  		= left;
		
		this.rhsName 	= rhsName;
		this.direction 	= direction;
		
	}
	
	public VHDLRange(String lhsName, String rhsName, Direction direction){
		
		this();
		
		this.lhsName 	= lhsName;
		this.rhsName 	= rhsName;
		this.direction 	= direction;
	}
	
	
	
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		 VHDLStringRepresentation rep = new VHDLStringRepresentation();
		 
		 /* If both range values are numbers */
		 if(left > - 1 && right > -1){
			 
			 rep.add("(" + Integer.toString(left) + " " + this.direction.toString() + " " + Integer.toString(right) + ")");
		 
	     /* Left set and right is string */
		 }else if(left > - 1 && right < 0 && lhsName.isEmpty() &&!rhsName.isEmpty()){
			 
			 rep.add("(" + Integer.toString(left) + " " + this.direction.toString() + " " + this.rhsName + ")");
			 
	     /* Right is set and left is String */
		 }else if(left < 0 && right > -1 && !lhsName.isEmpty() && rhsName.isEmpty()){
			 
			 rep.add("(" + lhsName + " " + this.direction.toString() + " " + Integer.toString(right) + ")");
			 
		 }else if(left > -1 && right > -1 && !lhsName.isEmpty() && !rhsName.isEmpty()){
			 
			 rep.add("(" + lhsName + " " + this.direction.toString() + " " + rhsName + ")");
		 }
		 
		 
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
