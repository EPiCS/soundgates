package soundgates.diagram.actions.codegen.vhdl.statements;

import java.util.ArrayList;

import soundgates.diagram.actions.codegen.vhdl.VHDLElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLStringRepresentation;

public class VHDLCaseStatement extends VHDLElement {
	
	
	private ArrayList<VHDLCaseStatementFragment> choises;
	private String expression;
	
	
	public VHDLCaseStatement(String expression){
		
		choises 		= new ArrayList<VHDLCaseStatementFragment>();
		this.expression = expression;
	}
	
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		VHDLStringRepresentation rep = new VHDLStringRepresentation();
		
		rep.addLine("case " + this.expression + " is" );
		
		for(VHDLCaseStatementFragment choise : choises){
			
			rep.addLine(VHDLStringRepresentation.getIndentString(4) + "when " + choise.getLabel() + " =>");
			
			rep.add(choise.getStringRepresentation().pack().indent(4));			
		}
		
		rep.addLine("end case;");
		
		return rep;
	}

	@Override
	public ArrayList<VHDLElement> getElements() {
		
		return null;
	}
}
