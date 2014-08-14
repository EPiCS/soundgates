package soundgates.diagram.actions.codegen.vhdl.decl;

import java.util.ArrayList;

import soundgates.diagram.actions.codegen.vhdl.IVHDLLabeledElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLPort;
import soundgates.diagram.actions.codegen.vhdl.VHDLStringRepresentation;

public class VHDLComponentDeclaration extends VHDLElement implements IVHDLLabeledElement {
	
	private ArrayList<VHDLElement> ports = null;
	

	private String name;
	
	public VHDLComponentDeclaration(String name, ArrayList<VHDLElement> ports){
		this.name  = name;
		this.ports = ports;
	}
	
	public String getLabel() {
		return name;
	}

	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		VHDLStringRepresentation componentrep 	= new VHDLStringRepresentation();
		VHDLStringRepresentation portrep 		= new VHDLStringRepresentation();;
		
		componentrep.addLine("component " + name + " is");
		componentrep.addLine("Port (");
		
		for(int i = 0; i < ports.size(); i++){
			
			VHDLPort port = (VHDLPort) ports.get(i);
			
			portrep.add(port.getStringRepresentation());
			
			if(i < ports.size() - 1){
				portrep.addLine(";");
			}
		}
		
		componentrep.add(portrep.pack().indent(4));
		
		componentrep.addLine(");");
		componentrep.addLine("end component " + name + ";");
		
		return componentrep;
	}

	@Override
	public ArrayList<VHDLElement> getElements() {
		return ports;
	}
}
