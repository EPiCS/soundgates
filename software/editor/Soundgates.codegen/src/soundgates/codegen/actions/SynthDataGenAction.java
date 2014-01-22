package soundgates.codegen.actions;

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
import soundgates.codegen.CodeGenHelper;
import soundgates.codegen.synthesizer.SynthDataGen;
import soundgates.diagram.XMLexport.Tester;

public class SynthDataGenAction implements IObjectActionDelegate{
	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
//		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {		
		
		IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		Iterator<?> files = structuredSelection.iterator();
		while (files.hasNext()) {
			IFile file = (IFile) files.next();
			SynthDataGen dataGen = new SynthDataGen();
			
			try {
				String projectPath = file.getProject().getLocation().toPortableString();
				
				Patch patch = CodeGenHelper.getPatch(file.getFullPath().toPortableString());
						
				Tester tester = new Tester();				
				if(tester.testPatch(patch) == false)
					return;				
				
				// export TGF file and project as zip				
				dataGen.generateSynthData(patch, file, projectPath);			
				
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
	

}
