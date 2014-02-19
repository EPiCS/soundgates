package soundgates.diagram.actions;

import java.util.Iterator;

import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import soundgates.CompositeSoundComponent;
import soundgates.diagram.XMLexport.CompositeSoundComponentExporter;
import soundgates.diagram.XMLexport.Tester;
import soundgates.diagram.edit.parts.CompositeSoundComponentAbstractEditPart;
import soundgates.diagram.soundcomponents.AtomicSoundComponentLibrary;



public class ExportCompositeSoundComponentAction implements IObjectActionDelegate{

	@Override
	public void run(IAction action) {
		IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		Iterator<?> files = structuredSelection.iterator();
		while (files.hasNext()) {			
			 Object selectedObject = files.next(); 
			 if (selectedObject instanceof CompositeSoundComponentAbstractEditPart){
				 CompositeSoundComponentAbstractEditPart editPart = (CompositeSoundComponentAbstractEditPart) selectedObject;				 
				CompositeSoundComponent compositeSoundComponent =  (CompositeSoundComponent) ((Shape) editPart.getModel()).getElement();
				 
			 	Tester patchTester = new Tester();
			 	patchTester.setProjectPath(AtomicSoundComponentLibrary.getProjectPath());
			 	boolean accepted = patchTester.testCompositeSoundComponent(compositeSoundComponent, true);
			 	if(!accepted) return;
			 	
				if(patchTester.shouldWriteFileForCompositeSoundComponent(compositeSoundComponent.getName())){
					CompositeSoundComponentExporter.exportToXML(compositeSoundComponent);	
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
