package soundgates.diagram.actions.codegen.vhdl.statements;

import java.util.ArrayList;

import soundgates.diagram.actions.codegen.vhdl.IVHDLLabeledElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLStringRepresentation;

public class VHDLCaseStatementFragment extends VHDLElement implements IVHDLLabeledElement{
	
	
	private ArrayList<VHDLElement> sequentialstatements;
	
	private String choise;
	
	public VHDLCaseStatementFragment(String choise){
		
		this.choise = choise;
	}
	
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		VHDLStringRepresentation rep = new VHDLStringRepresentation();
		
		
		return rep;
	}

	@Override
	public ArrayList<VHDLElement> getElements() {

		return sequentialstatements;
	}

	@Override
	public String getLabel() {
		return choise;
	}

}
