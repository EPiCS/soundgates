package soundgates.diagram.actions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Direction;
import soundgates.Port;
import soundgates.SoundComponent;
import soundgates.VHDLXMLPort;
import soundgates.diagram.actions.codegen.vhdl.VHDLComponent;
import soundgates.diagram.actions.codegen.vhdl.VHDLDatatype;
import soundgates.diagram.actions.codegen.vhdl.VHDLElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLEntity;
import soundgates.diagram.actions.codegen.vhdl.VHDLPort;
import soundgates.diagram.actions.codegen.vhdl.VHDLRange;
import soundgates.diagram.actions.codegen.vhdl.VHDLStringRepresentation;
import soundgates.diagram.edit.parts.CompositeSoundComponentEditPart;
import soundgates.diagram.soundcomponents.AtomicSoundComponentLibrary;
import soundgates.diagram.soundcomponents.AtomicSoundComponentXMLHandler;



public class VHDLExportAction implements IObjectActionDelegate{
	
	private static String hwtTemplateFilename = "hwt_template.vhd";
	
	
	@Override
	public void run(IAction action) {
		IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		Iterator<?> files = structuredSelection.iterator();
		while (files.hasNext()) {			
			 Object selectedObject = files.next(); 
			 
			 if (selectedObject instanceof CompositeSoundComponentEditPart){
				 
				 CompositeSoundComponentEditPart compositeSoundComponentAbstractEditPart =
						 (CompositeSoundComponentEditPart) selectedObject;
				
				 CompositeSoundComponent compositeSoundComponent = compositeSoundComponentAbstractEditPart.getCompositeSoundComponent();			 
				 
				 saveTextToFile(getVHDLText(compositeSoundComponent),"NewVHDLFile.txt");
			 }
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {	
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {		
	}

	private String getVHDLText(CompositeSoundComponent compositeSoundComponent){
		
		
		StringBuffer 				stringBuffer = new StringBuffer();
		VHDLStringRepresentation	hwttemplate  = new VHDLStringRepresentation();
		
		VHDLEntity					hwtentity    = null;
		
		IFile templatefile = AtomicSoundComponentLibrary.getXMLFolder().getProject().getFile(hwtTemplateFilename);
		
		if(!templatefile.exists()){
			
			throw new RuntimeException("Hardware thread template file does not exist!");
			
		}else{
			/* Read template file */
			try {
				InputStream    in     = templatefile.getContents();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String line;
				while ((line = reader.readLine()) != null) { 
		            hwttemplate.addLine(line);
		        }
				
				in.close();
				
			} catch (CoreException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}			
		}
		
		/* Replace entity name */
		hwtentity = new VHDLEntity(compositeSoundComponent.getName(), hwttemplate);
		
		for (SoundComponent soundComponent : compositeSoundComponent.getEmbeddedComponents()){
			if(soundComponent instanceof AtomicSoundComponent){
				
				AtomicSoundComponent atomicSoundComponent =((AtomicSoundComponent) soundComponent);
				atomicSoundComponent.getStringProperties().get(AtomicSoundComponentXMLHandler.DEVICE_PREFIX_IMPLNAME);
								
				ArrayList<VHDLElement> vhdlports = new ArrayList<VHDLElement>();
				
				System.out.println("Atomic component: Name: "+soundComponent.getName()+" Type: "+atomicSoundComponent.getType()+"\n");
				String type = atomicSoundComponent.getType();
				
				for(Port modelport : soundComponent.getPorts()){
					System.out.println("  Port: Name: " + modelport.getName() + " Direction: " + modelport.getDirection());
					
					VHDLXMLPort xmlport = getVHDLPort(atomicSoundComponent, modelport);
					
					if(xmlport != null){
						System.out.println(" VHDLName: " + xmlport.getVhdlName() + "\n");
						
						VHDLPort 	 vhdlport     = null;
						VHDLDatatype vhdldatatype = null;
						
						if(!xmlport.isVector()){
							vhdldatatype  = new VHDLDatatype(xmlport.getDatatype());
							
						}else{
							vhdldatatype = new VHDLDatatype(xmlport.getDatatype(), 
																			new VHDLRange(	xmlport.getRange().getLeftValue(), 
																							xmlport.getRange().getRightValue()));
						}
						
						if(xmlport.getDir() == Direction.IN){
							
							vhdlport = new VHDLPort(xmlport.getVhdlName(), VHDLPort.Direction.IN, vhdldatatype);
							
						}else if(xmlport.getDir() == Direction.OUT){
							vhdlport = new VHDLPort(xmlport.getVhdlName(), VHDLPort.Direction.OUT, vhdldatatype);
						}else {
							
							throw new RuntimeException("Unknown port direction: " + xmlport.getDir().toString());
						}						
						 
						vhdlports.add(vhdlport);						
					}
					else{ 
						System.out.println("\n");
					}
				}
				
				VHDLComponent component = new VHDLComponent(soundComponent.getName(),vhdlports);
//				component.getStringRepresentation().add(0, "-- Generated for " + atomicSoundComponent.getName() + "\n");
				
				hwtentity.addComponentDecl(component);
				
				System.out.println(component.getStringRepresentation().toString());
			}
			else if(soundComponent instanceof CompositeSoundComponent){
				System.out.println("Composite component: Name: "+soundComponent.getName());
			}
		}
		return hwtentity.getStringRepresentation().toString();
	}
	
	private VHDLXMLPort getVHDLPort(AtomicSoundComponent atomicSoundComponent, Port modelPort){
		LinkedList<VHDLXMLPort> vhdlPorts = AtomicSoundComponentLibrary.componentTypeToVHDLPortsList.get(atomicSoundComponent.getType());
		for(VHDLXMLPort vhdlPort : vhdlPorts){
			if(vhdlPort.hasModelName())
				if(vhdlPort.getModelName().equals(modelPort.getName())){
					return vhdlPort;
				}
		}
		return null;
	}
	
	private Port getModelPort(AtomicSoundComponent atomicSoundComponent, VHDLXMLPort vhdlPort){		
		for(Port modelPort : atomicSoundComponent.getPorts()){
			if(vhdlPort.getModelName().equals(modelPort.getName())){
				return modelPort;
			}
		}
		return null;
	}
	
	private void saveTextToFile(String text, String fileName){	
		
		try {
			IFile newFile = AtomicSoundComponentLibrary.getXMLFolder().getProject().getFile(fileName);
			
			InputStream code = null;
			
			try {
			
				code = new ByteArrayInputStream(text.getBytes("UTF-8"));
			
			} catch (UnsupportedEncodingException e) {
			
				e.printStackTrace();
			}
			
			if(newFile.exists()){
				newFile.delete(true, null);
			}
			newFile.create(code, IResource.FORCE, null);
			
			newFile.getParent().refreshLocal(1, null);
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
