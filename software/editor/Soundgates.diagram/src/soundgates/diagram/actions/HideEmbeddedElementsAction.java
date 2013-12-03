package soundgates.diagram.actions;

import java.util.Iterator;

import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import soundgates.diagram.edit.parts.CompositeSoundComponentAbstractEditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponentComponentCompartment2EditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponentComponentCompartmentEditPart;



public class HideEmbeddedElementsAction implements IObjectActionDelegate{

	@Override
	public void run(IAction action) {
		IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		Iterator<?> files = structuredSelection.iterator();
		while (files.hasNext()) {			
			 Object selectedObject = files.next(); 
			 if (selectedObject instanceof CompositeSoundComponentAbstractEditPart){
				 CompositeSoundComponentAbstractEditPart compositeSoundComponentAbstractEditPart =
						 (CompositeSoundComponentAbstractEditPart) selectedObject;
				 
				 ResizableCompartmentFigure compartmentFigure = null;
				 
				 for(Object child : compositeSoundComponentAbstractEditPart.getChildren()){
					 if(child instanceof CompositeSoundComponentComponentCompartmentEditPart){
						 CompositeSoundComponentComponentCompartmentEditPart editPart = (CompositeSoundComponentComponentCompartmentEditPart) child;
						 compartmentFigure = editPart.getCompartmentFigure();
						 break;
					 }
					 else if(child instanceof CompositeSoundComponentComponentCompartment2EditPart){
						 CompositeSoundComponentComponentCompartment2EditPart editPart = (CompositeSoundComponentComponentCompartment2EditPart) child;
						 compartmentFigure = editPart.getCompartmentFigure();
						 break;
					 }
				 }
				 
				 if(compartmentFigure!=null){
					 if (compartmentFigure.isExpanded()) 
						 compartmentFigure.collapse();
					 else 
						 compartmentFigure.expand();
				 }
				
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

}
