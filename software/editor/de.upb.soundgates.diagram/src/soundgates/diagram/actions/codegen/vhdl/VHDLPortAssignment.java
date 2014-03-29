package soundgates.diagram.actions.codegen.vhdl;

import java.util.ArrayList;

import soundgates.diagram.actions.codegen.vhdl.decl.VHDLSignalDeclaration;

public class VHDLPortAssignment extends VHDLElement {
		
	private VHDLPort 				port;
	private VHDLSignalDeclaration	signaldecl;
	
	
	public VHDLPortAssignment(VHDLPort port, VHDLSignalDeclaration signal){
		
		this.port 		= port;
		this.signaldecl = signal;
		
	}
	
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		VHDLStringRepresentation rep = new VHDLStringRepresentation();
		
		rep.add(port.getLabel());
		rep.add(" => ");
		rep.add(signaldecl.getName());
		
		return rep;
	}

	@Override
	public ArrayList<VHDLElement> getElements() {
		// TODO Auto-generated method stub
		return null;
	}

}
