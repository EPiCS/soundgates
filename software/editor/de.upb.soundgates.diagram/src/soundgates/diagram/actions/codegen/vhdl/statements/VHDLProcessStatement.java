package soundgates.diagram.actions.codegen.vhdl.statements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import soundgates.diagram.actions.codegen.vhdl.IVHDLLabeledElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLStringRepresentation;
import soundgates.diagram.actions.codegen.vhdl.decl.VHDLSignalDeclaration;

public class VHDLProcessStatement extends VHDLElement implements
		IVHDLLabeledElement {
	
	private List<VHDLSignalDeclaration> sensitivitylis;
	private ArrayList<VHDLElement>		elements;
	private String 						name;
	
	
	public VHDLProcessStatement(String name, List<VHDLSignalDeclaration> sensitivitylist){
		this.name 			= name;
		this.sensitivitylis = sensitivitylist;
		this.elements 		= new ArrayList<VHDLElement>();
	}
	
	@Override
	public String getLabel() {
		return this.name.toUpperCase() + "_PROC";
	}

	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		VHDLStringRepresentation rep = new VHDLStringRepresentation();
		
		rep.add(getLabel() + " : " + " process ");
		
		rep.add("(");	// Open sensitivity list
		
		Iterator<VHDLSignalDeclaration> iter = sensitivitylis.iterator();
		while(iter.hasNext()){
			VHDLSignalDeclaration element = iter.next();			
			rep.add(element.getName());			
			if(iter.hasNext()){
				rep.add(",");
			}
		}
		rep.addLine(")  is");	// Close sensitivity list
		
		rep.addLine("begin");	// begin
		
		for(VHDLElement sequentialstatement : getElements()){
			
			rep.add(sequentialstatement.getStringRepresentation().pack());
		}		
		
		rep.addLine("end process " + getLabel() + ";");	// end
		
		return rep;
	}

	@Override
	public ArrayList<VHDLElement> getElements() {
		return elements;
	}
	
}
