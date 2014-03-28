package soundgates.diagram.actions.codegen.vhdl.decl;

import java.util.ArrayList;

import soundgates.diagram.actions.codegen.vhdl.IVHDLLabeledElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLDatatype;
import soundgates.diagram.actions.codegen.vhdl.VHDLElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLStringRepresentation;

public class VHDLVariableDeclaration extends VHDLElement implements IVHDLLabeledElement {
	
	private String 		 name;
	private VHDLDatatype datatype;
	private boolean 	 isShared;
	
	
	public VHDLVariableDeclaration(String name, boolean isShared, VHDLDatatype datatype){
		
		this.isShared = isShared;
		this.datatype = datatype;
		this.name     = name;
	}
	
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		VHDLStringRepresentation rep = new VHDLStringRepresentation();
		
		if(isShared){
			rep.add("shared ");
		}
		
		rep.add("variable " + getLabel() + " : ");
		rep.add(this.datatype.getStringRepresentation());
		rep.addLine(";");
				
		return rep.pack();
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
