package soundgates.diagram.actions;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import soundgates.diagram.XMLexport.PatchImporter;
import soundgates.diagram.soundcomponents.AtomicSoundComponentLibrary;
import soundgates.diagram.soundcomponents.CompositeSoundComponentLibrary;



public class ImportPatchAction implements IObjectActionDelegate{

	@Override
	public void run(IAction action) {
		IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		Iterator<?> files = structuredSelection.iterator();
		while (files.hasNext()) {			
			 Object selectedObject = files.next(); 
			 if (selectedObject instanceof IResource){
					PatchImporter importer = new PatchImporter();
					IResource xmlFile = (IResource) selectedObject;	
					
					IProject iProject = xmlFile.getProject();		
					
					AtomicSoundComponentLibrary.setXMLFolder(iProject.getFolder("soundcomponents"));
					CompositeSoundComponentLibrary.setXMLFolder(iProject.getFolder("soundcomponents"));				
					
					String newFileName = iProject.getLocation() + "/" + xmlFile.getName().replace(".xml", "") + ".soundgates";
					
					try {
						importer.createPatchFromXML(newFileName, xmlFile.getLocation().toPortableString());					
						iProject.refreshLocal(1, null);
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
