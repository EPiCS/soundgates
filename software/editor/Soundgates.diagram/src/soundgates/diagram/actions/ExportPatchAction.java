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

import soundgates.diagram.XMLexport.PatchExporter;



public class ExportPatchAction implements IObjectActionDelegate{

	@Override
	public void run(IAction action) {		
		
		IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		Iterator<?> files = structuredSelection.iterator();
		while (files.hasNext()) {
			IFile file = (IFile) files.next();		

			PatchExporter patchExporter = new PatchExporter();
						
			try {
				
				patchExporter.exportToXML(
						file.getParent().getLocation().toPortableString(), 				// folder						
						patchExporter.getPatch(file.getFullPath().toPortableString()),	// patch
						file.getName().replace(".soundgates",""));						// name
				
				file.getParent().refreshLocal(1, null);
			} catch (Exception e) {				
				e.printStackTrace();
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
