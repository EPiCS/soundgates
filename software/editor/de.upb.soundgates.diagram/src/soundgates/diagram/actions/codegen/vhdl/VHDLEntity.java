package soundgates.diagram.actions.codegen.vhdl;

import java.util.ArrayList;

import soundgates.diagram.actions.codegen.vhdl.decl.VHDLComponentDeclaration;
import soundgates.diagram.actions.codegen.vhdl.decl.VHDLSignalDeclaration;
import soundgates.diagram.actions.codegen.vhdl.decl.VHDLVariableDeclaration;
import soundgates.diagram.actions.codegen.vhdl.statements.VHDLComponentInstantiationStatement;
import soundgates.diagram.actions.codegen.vhdl.statements.VHDLProcessStatement;
import soundgates.diagram.actions.codegen.vhdl.statements.VHDLSignalAssignmentStatement;

public class VHDLEntity extends VHDLElement {
	
	static private String templateEntitynamePlaceHolder    = "%%HWT_ENTITY_PLACEHOLDER%%";
	static private String templateComponentDeclPlaceHolder = "%%HWT_COMPONENT_DECL_PLACEHOLDER%%";
	static private String templateComponentInstPlaceHolder = "%%HWT_ARCHITECTURE_COMPONENT_INSTATIATION%%";
	static private String templateSignalDeclPlaceholder    = "%%HWT_SIGNAL_DECLARATION%%";
	static private String templateParameterSampleCount     = "%%TEMPLATE_PARAM_SAMPLE_COUNT%%";
	static private String templateArchitectureVariableDecl = "%%HWT_ARCHITECTURE_VARIABLE_DECLARATION%%"; 
	static private String templateArchitectureBegin		   = "%%HWT_ARCHITECTURE_BEGIN%%";
	
	
	static private int	  samplecount  = 64;
	
	private ArrayList<VHDLElement>   vhdlelements;
	
	VHDLStringRepresentation	   	 template;
	
	private String 				   	 name;
	
	public VHDLEntity(String name){
		this.name = name;
				
		vhdlelements  = new ArrayList<VHDLElement>();
		
	}	
	
	public VHDLEntity(String name, VHDLStringRepresentation template){
		
		this(name);
		
		this.template = template;
	}

		
	@Override
	public VHDLStringRepresentation getStringRepresentation() {
		
		int[] offsetIndicies;
		

		/* Set template parameters   					*/
		offsetIndicies = template.getStringOffset(templateParameterSampleCount);
		{
			String line = template.get(offsetIndicies[0]);
			line = line.replace(templateParameterSampleCount, Integer.toString(samplecount));
			template.set(offsetIndicies[0], line);
		}

		/* Set hardware thread name 					*/
		offsetIndicies = template.getStringOffset(templateEntitynamePlaceHolder);
		
		for(int offset : offsetIndicies)
		{
			String line = template.get(offset);
			
			line = line.replace(templateEntitynamePlaceHolder, this.name);
			template.set(offset, line);
		}
				
		for(VHDLElement element : getElements()){
			
			if(element instanceof VHDLSignalDeclaration){
				
				offsetIndicies = template.getStringOffset(templateSignalDeclPlaceholder);
								
				for(int offset : offsetIndicies)
				{
					template.add(offset, element.getStringRepresentation().pack().indent(4).toString());
				}
			}
			
			if(element instanceof VHDLVariableDeclaration){
				
				offsetIndicies = template.getStringOffset(templateArchitectureVariableDecl);
											
				for(int offset : offsetIndicies)
				{
					template.add(offset, element.getStringRepresentation().pack().indent(4).toString());
				}
				
			}
			
			if(element instanceof VHDLComponentDeclaration){
				
				offsetIndicies = template.getStringOffset(templateComponentDeclPlaceHolder);
				
				for(int offset : offsetIndicies)
				{
					template.add(offset, element.getStringRepresentation().pack().indent(4).toString());					
				}
			}
			
			if(element instanceof VHDLComponentInstantiationStatement){
				
				offsetIndicies = template.getStringOffset(templateComponentInstPlaceHolder);
				
				for(int offset : offsetIndicies)
				{					
					template.add(offset, element.getStringRepresentation().pack().indent(4).toString());				
				}
			}
			
			if(element instanceof VHDLSignalAssignmentStatement){
				
				offsetIndicies = template.getStringOffset(templateArchitectureBegin);
				
				for(int offset : offsetIndicies)
				{					
					template.add(offset, element.getStringRepresentation().pack().indent(4).toString());				
				}
			}
			
			if(element instanceof VHDLProcessStatement){
				
				offsetIndicies = template.getStringOffset(templateArchitectureBegin);
				
				for(int offset : offsetIndicies)
				{					
					template.add(offset, element.getStringRepresentation().pack().indent(4).toString());				
				}
			}
			
		}
		
		/* Remove placeholder */
		
		
		return template;
	}
		
	@Override
	public ArrayList<VHDLElement> getElements() {
		return vhdlelements;
	}
}
