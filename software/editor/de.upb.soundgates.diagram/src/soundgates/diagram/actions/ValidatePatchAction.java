package soundgates.diagram.actions;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import soundgates.Patch;
import soundgates.diagram.XMLexport.PatchExporter;
import soundgates.diagram.XMLexport.Tester;
import soundgates.diagram.messageDialogs.MessageDialogs;



public class ValidatePatchAction implements IObjectActionDelegate{

	@Override
	public void run(IAction action) {		
		
		IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		Iterator<?> files = structuredSelection.iterator();
		while (files.hasNext()) {
			IFile file = (IFile) files.next();		

			String projectPath = file.getProject().getLocation().toPortableString();
			
			Patch patch = PatchExporter.getPatch(file.getFullPath().toPortableString());
			Tester tester = new Tester();
			tester.setProjectPath(projectPath);
			
			if(tester.testPatch(patch)) 
				MessageDialogs.patchValidationTrue();
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
