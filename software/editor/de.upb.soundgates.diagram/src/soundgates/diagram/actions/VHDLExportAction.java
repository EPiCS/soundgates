package soundgates.diagram.actions;

import java.util.Iterator;

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
import soundgates.diagram.edit.parts.CompositeSoundComponentEditPart;



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
				 
				 print(compositeSoundComponent);
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

	private void print(CompositeSoundComponent compositeSoundComponent){
		for (SoundComponent soundComponent : compositeSoundComponent.getEmbeddedComponents()){
			if(soundComponent instanceof AtomicSoundComponent){
				
				System.out.println("Atomic component: Name: "+soundComponent.getName()+" Type: "+((AtomicSoundComponent) soundComponent).getType());
				for(Port port : soundComponent.getPorts()){
					System.out.println("  Port: Name: "+port.getName()+" Direction: "+port.getDirection());
				}
				float v = ((AtomicSoundComponent) soundComponent).getFloatProperties().get("Value");
			}
			else if(soundComponent instanceof CompositeSoundComponent){
				System.out.println("Composite component: Name: "+soundComponent.getName());
			}
		}
	}
}
