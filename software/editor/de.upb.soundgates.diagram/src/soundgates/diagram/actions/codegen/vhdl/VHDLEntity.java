package soundgates.diagram.actions.codegen.vhdl;

import java.util.ArrayList;

public class VHDLEntity extends VHDLElement {
	
	static private String templateEntitynamePlaceHolder    = "%%HWT_ENTITY_PLACEHOLDER%%";
	static private String templateComponentDeclPlaceHolder = "%%HWT_COMPONENT_DECL_PLACEHOLDER%%";
	static private String templateComponentInstPlaceHolder = "%%HWT_COMPONENT_INSTATIOAN_PLACEHOLDER%%";
	
	private ArrayList<VHDLComponent> componentdecl;
	
	private ArrayList<VHDLElement>   vhdlelements;
	
	VHDLStringRepresentation	   	 template;
	
	private String 				   	 name;
	
	public VHDLEntity(String name){
		this.name = name;
		
		componentdecl = new ArrayList<VHDLComponent>();
		vhdlelements  = new ArrayList<VHDLElement>();
		
	}	
	
	public VHDLEntity(String name, VHDLStringRepresentation template){
		
		this(name);
		
		this.template = template;
	}
	
	public int getArchitectureOffset(){
		return 0;
	}
	
	public void addComponentDecl(VHDLComponent decl){
		
		this.componentdecl.add(decl);		
	}
	
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		
		/* Add hardware thread name */
		int[] entityNameIndicies = template.getStringOffset(templateEntitynamePlaceHolder);
		
		for(int i = 0; i < entityNameIndicies.length; i++){
			
			String line = template.get(entityNameIndicies[i]);
			
			line = line.replace(templateEntitynamePlaceHolder, this.name);
			template.set(entityNameIndicies[i], line);
		}
		
		/* Add component declarations */		
		int[] componentdeclIndicies = template.getStringOffset(templateComponentDeclPlaceHolder);
		
		for(int i = 0; i < componentdeclIndicies.length; i++){
			int offset = componentdeclIndicies[i];
			
			for(VHDLComponent decl : componentdecl){
				template.add(offset, decl.getStringRepresentation().pack().toString());
			}
		}		
		
		return template;
	}
		
	@Override
	public ArrayList<VHDLElement> getElements() {
		return vhdlelements;
	}
}
