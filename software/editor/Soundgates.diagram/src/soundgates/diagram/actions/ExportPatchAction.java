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



public class ExportPatchAction implements IObjectActionDelegate{

	@Override
	public void run(IAction action) {		
		
		IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		Iterator<?> files = structuredSelection.iterator();
		while (files.hasNext()) {
			IFile file = (IFile) files.next();		

			PatchExporter patchExporter = new PatchExporter();
			Patch patch = patchExporter.getPatch(file.getFullPath().toPortableString());

			String newFileName = file.getName().replace(".soundgates",".xml");
			
			if(file.getProject().getFile(newFileName).exists()){
				if (!MessageDialogs.replaceExistingEMFFile(newFileName))
					return;
			}
			
			Tester tester = new Tester();
			
			if(tester.testPatch(patch) == false)
				return;
			
			try {
				
				patchExporter.exportToXML(
						file.getParent().getLocation().toPortableString(), 	// folder						
						patch,												// patch
						file.getName().replace(".soundgates",""));			// name
								
				MessageDialogs.patchtWasExported(file.getName().replace(".soundgates",".xml"));
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
