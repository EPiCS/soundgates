package soundgates.diagram.actions.codegen.vhdl.statements;

import java.util.ArrayList;

import soundgates.diagram.actions.codegen.vhdl.VHDLElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLRange;
import soundgates.diagram.actions.codegen.vhdl.VHDLStringRepresentation;
import soundgates.diagram.actions.codegen.vhdl.decl.VHDLSignalDeclaration;

public class VHDLSignalAssignmentStatement extends VHDLElement {
	
	private VHDLSignalDeclaration lhs;
	private VHDLSignalDeclaration rhs;
	
	private VHDLRange rhs_range;
	
	public VHDLSignalAssignmentStatement(VHDLSignalDeclaration lhs, VHDLSignalDeclaration rhs){
		
		this.lhs  = lhs;
		this.rhs  = rhs;
		
		rhs_range = null;
	}
	
	public VHDLSignalAssignmentStatement(VHDLSignalDeclaration lhs, VHDLSignalDeclaration rhs, VHDLRange rhs_range){
		
		this(lhs, rhs);
		
		this.rhs_range 	= rhs_range;
		
	}
	
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		
		VHDLStringRepresentation rep = new VHDLStringRepresentation();
		
		rep.add(lhs.getName());
		rep.add("    <=    ");
		rep.add(rhs.getName());
		
		if(rhs_range != null){
			rep.add(rhs_range.getStringRepresentation());
		}
		rep.addLine(";");
		
		return rep;
	}

	@Override
	public ArrayList<VHDLElement> getElements() {

		return null;
	}

}
