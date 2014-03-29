package soundgates.diagram.actions.codegen.vhdl;

import java.util.ArrayList;

public abstract class VHDLElement {
		
	public abstract VHDLStringRepresentation getStringRepresentation();
	
	public void addElement(VHDLElement elem){
		
		getElements().add(elem);		
	}
	
	public void removeChild(VHDLElement elem){
		getElements().remove(elem);
	}
	
	public void removeChild(int index){
		getElements().remove(index);
	}
	
	public abstract ArrayList<VHDLElement> getElements();
	
	public boolean hasChildren(){
		return (getElements() != null);
	}
}
