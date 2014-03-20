package soundgates.diagram.actions.codegen.vhdl.statements;

import java.util.ArrayList;

import soundgates.diagram.actions.codegen.vhdl.VHDLElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLStringRepresentation;

public class VHDLSequentialStatement extends VHDLElement {
	
	private String 					statement;
	private ArrayList<VHDLElement>	elements;
	public VHDLSequentialStatement(String statement){
		
		this.statement = statement;
		this.elements  = new ArrayList<VHDLElement>();
		
	}
	
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		VHDLStringRepresentation rep = new VHDLStringRepresentation();
		
		rep.addLine(this.statement);
		for(VHDLElement nestedstatement : getElements()){
			
			rep.add(nestedstatement.getStringRepresentation().indent(4));
		}		
		return rep;
	}

	@Override
	public ArrayList<VHDLElement> getElements() {
		
		return elements;
	}

}
