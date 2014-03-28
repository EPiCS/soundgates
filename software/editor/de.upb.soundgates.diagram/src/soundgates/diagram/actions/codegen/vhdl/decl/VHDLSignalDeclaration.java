package soundgates.diagram.actions.codegen.vhdl.decl;

import java.util.ArrayList;

import soundgates.diagram.actions.codegen.vhdl.VHDLDatatype;
import soundgates.diagram.actions.codegen.vhdl.VHDLElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLStringRepresentation;

public class VHDLSignalDeclaration extends VHDLElement {
	
	private String 	     name;
	private VHDLDatatype datatype;

	public VHDLSignalDeclaration(String name, VHDLDatatype datatype){
		this.name     = name;
		this.datatype = datatype;
	}
	
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		VHDLStringRepresentation rep = new VHDLStringRepresentation();
		
		rep.add("signal ");
		rep.add(name);
		rep.add(" : ");
		rep.add(datatype.getStringRepresentation());
		rep.addLine(";");
		
		return rep;
	}

	public String getName() {
		return name;
	}
	
	public VHDLDatatype getDatatype() {
		return datatype;
	}

	@Override
	public ArrayList<VHDLElement> getElements() {
		return null;
	}

}
