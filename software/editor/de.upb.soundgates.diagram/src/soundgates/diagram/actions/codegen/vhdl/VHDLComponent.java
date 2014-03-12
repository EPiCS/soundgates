package soundgates.diagram.actions.codegen.vhdl;

import java.util.ArrayList;

public class VHDLComponent extends VHDLElement {
	
	private ArrayList<VHDLElement> ports = null;
	
	private String name;
	
	public VHDLComponent(String name, ArrayList<VHDLElement> ports){
		this.name  = name;
		this.ports = ports;
	}
	
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		VHDLStringRepresentation componentrep = new VHDLStringRepresentation();
		VHDLStringRepresentation portrep = new VHDLStringRepresentation();;
		
		componentrep.addLine("component " + name + " is");
		componentrep.addLine("Port (");
		
		for(int i = 0; i < ports.size(); i++){
			
			VHDLPort port = (VHDLPort) ports.get(i);
			
			portrep.add(port.getStringRepresentation());
			
			if(i < ports.size() - 1){
				portrep.addLine(";");
			}
		}
		
		portrep = portrep.pack().indent(4);
		
		componentrep.addLine(portrep);
		
		componentrep.addLine(");");
		componentrep.addLine("end component " + name + ";");
		
		return componentrep;
	}

	@Override
	public ArrayList<VHDLElement> getElements() {
		return ports;
	}
}
