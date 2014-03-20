package soundgates.diagram.actions.codegen.vhdl.statements;

import java.util.ArrayList;
import java.util.ListIterator;

import soundgates.diagram.actions.codegen.vhdl.IVHDLLabeledElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLPortAssignment;
import soundgates.diagram.actions.codegen.vhdl.VHDLStringRepresentation;
import soundgates.diagram.actions.codegen.vhdl.decl.VHDLComponentDeclaration;

public class VHDLComponentInstantiationStatement extends VHDLElement implements IVHDLLabeledElement{
	
	
	private static String prefix      = "_INSTANCE";
	
	private ArrayList<VHDLElement> 		elements;
	private VHDLComponentDeclaration	decl;
	private String 						name;
	
	public VHDLComponentInstantiationStatement(String modelname, VHDLComponentDeclaration decl){
		this.decl 	= decl;
		this.name   = modelname;
		
		elements 	= new ArrayList<VHDLElement>();
		
	}	
	
	public VHDLComponentDeclaration getDeclaration(){
		
		return this.decl;
	}
	
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		
		VHDLStringRepresentation rep = new VHDLStringRepresentation();
		
		rep.addLine(getLabel() + " : " + decl.getLabel());
		rep.addLine("port map (");
		
		ListIterator<VHDLElement> iter = getElements().listIterator();
		
		while(iter.hasNext()){
			VHDLElement element = iter.next();
			
			if(element instanceof VHDLPortAssignment){				
				rep.add(element.getStringRepresentation().indent(4));
			}
			if(iter.hasNext()){
				rep.addLine(",");
			}
		}
		
		rep.addLine(");");
		
		return rep;
	}

	@Override
	public ArrayList<VHDLElement> getElements() {
		return this.elements;
	}
	

	@Override
	public String getLabel() {
		return getLabelName(this.name, decl.getLabel());
	}
		
	public static String getLabelName(String modelname, String type){
		
		return modelname.toUpperCase() + "_" + type.toUpperCase() + prefix;
	}
}
