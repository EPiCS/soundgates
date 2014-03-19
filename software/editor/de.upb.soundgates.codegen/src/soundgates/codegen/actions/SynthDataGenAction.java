package soundgates.codegen.actions;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
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
				tester.setProjectPath(projectPath);
				if(tester.testPatch(patch) == false)
					return;				
				
				IFile newZipFile = file.getProject().getFile(file.getName().replace(".sgd", ".zip"));
				if(newZipFile.exists())
					{  MessageDialog dialog = new MessageDialog(
								      null, "File \""+newZipFile.getName()+"\" already exists", 
								      null, "Do you want to replace the file \""+newZipFile.getName()+"\"?",
								      MessageDialog.QUESTION,
								      new String[] {"Yes", "No"},
								      0); 
						   
						   int result =  dialog.open();
						   if(result==0){ 
							   // export TGF file and project as zip				
								dataGen.generateSynthData(patch, file, projectPath);
						   }
						   else return;
						
					}
				else{
					// export TGF file and project as zip				
					dataGen.generateSynthData(patch, file, projectPath);
				}
							
				
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
