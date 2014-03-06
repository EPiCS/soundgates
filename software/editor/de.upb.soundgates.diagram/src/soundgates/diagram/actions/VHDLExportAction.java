package soundgates.diagram.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
import soundgates.Port;
import soundgates.SoundComponent;
import soundgates.VHDLPort;
import soundgates.diagram.edit.parts.CompositeSoundComponentEditPart;
import soundgates.diagram.soundcomponents.AtomicSoundComponentLibrary;



public class VHDLExportAction implements IObjectActionDelegate{

	@Override
	public void run(IAction action) {
		IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		Iterator<?> files = structuredSelection.iterator();
		while (files.hasNext()) {			
			 Object selectedObject = files.next(); 
			 if (selectedObject instanceof CompositeSoundComponentEditPart){
				 CompositeSoundComponentEditPart compositeSoundComponentAbstractEditPart =
						 (CompositeSoundComponentEditPart) selectedObject;
				
				 CompositeSoundComponent compositeSoundComponent =
						 compositeSoundComponentAbstractEditPart.getCompositeSoundComponent();			 
				 
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
		StringBuffer stringBuffer = new StringBuffer();
		for (SoundComponent soundComponent : compositeSoundComponent.getEmbeddedComponents()){
			if(soundComponent instanceof AtomicSoundComponent){
				
				AtomicSoundComponent atomicSoundComponent =((AtomicSoundComponent) soundComponent);
				
				stringBuffer.append("Atomic component: Name: "+soundComponent.getName()+" Type: "+atomicSoundComponent.getType()+"\n");
				for(Port port : soundComponent.getPorts()){
					stringBuffer.append("  Port: Name: "+port.getName()+" Direction: "+port.getDirection());
					if( getVHDLPortFromModelPort(atomicSoundComponent, port) !=null)
						stringBuffer.append(" VHDLName: "+getVHDLPortFromModelPort(atomicSoundComponent, port).getVhdlName()+"\n");
					else 
						stringBuffer.append("\n");
				}				
			}
			else if(soundComponent instanceof CompositeSoundComponent){
				System.out.println("Composite component: Name: "+soundComponent.getName());
			}
		}
		return stringBuffer.toString();
	}
	
	private VHDLPort getVHDLPortFromModelPort(AtomicSoundComponent atomicSoundComponent, Port modelPort){
		LinkedList<VHDLPort> vhdlPorts = AtomicSoundComponentLibrary.componentTypeToVHDLPortsList.get(atomicSoundComponent.getType());
		for(VHDLPort vhdlPort : vhdlPorts){
			if(vhdlPort.hasModelName())
				if(vhdlPort.getModelName().equals(modelPort.getName())){
					return vhdlPort;
				}
		}
		return null;
	}
	
	private Port getModelPortFromVHDLPort(AtomicSoundComponent atomicSoundComponent, VHDLPort vhdlPort){		
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
			
			InputStream code = new ByteArrayInputStream(text.getBytes());
			
			if(newFile.exists())
				newFile.delete(true, null);
			
			newFile.create(code, IResource.FORCE, null);
			
			newFile.getParent().refreshLocal(1, null);
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
