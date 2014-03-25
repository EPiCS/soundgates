package soundgates.diagram.actions.codegen.vhdl.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import soundgates.diagram.actions.codegen.vhdl.VHDLElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLStringRepresentation;

public class VHDLProcedureCallStatement extends VHDLElement {
	
	private String 		 procedure;
	private List<String> arguments;
		
	public VHDLProcedureCallStatement(String procedure, List<String> arguments){
	
		this.procedure = procedure;
		this.arguments = arguments;
		
	}
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		VHDLStringRepresentation rep = new VHDLStringRepresentation();
		
		rep.add(this.procedure + "(");
		ListIterator<String> iter = arguments.listIterator();
		
		while(iter.hasNext()){
			String argument = iter.next();
			rep.add(VHDLStringRepresentation.getIndentString(4) + argument);
			rep.addLine((iter.hasNext() ? "," : ""));			
		}
		rep.addLine(");");
		
		return rep;
	}

	@Override
	public ArrayList<VHDLElement> getElements() {
		// TODO Auto-generated method stub
		return null;
	}

}
