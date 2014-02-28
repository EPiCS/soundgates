package soundgates.diagram.actions;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import soundgates.diagram.XMLexport.CompositeSoundComponentExporter;
import soundgates.diagram.edit.parts.AtomicSoundComponentEditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponentEditPart;
import soundgates.diagram.edit.parts.LinkEditPart;
import soundgatesComposite.diagram.XMLexport.CompositeSoundComponentImporter;
import soundgates.diagram.soundcomponents.AtomicSoundComponentLibrary;



public class CreateCompositeSoundComponentAction implements IObjectActionDelegate{

	@Override
	public void run(IAction action) {
		IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		Iterator<?> files = structuredSelection.iterator();
		
		LinkedList<EditPart> selectedEditParts = new LinkedList<>();
		
		while (files.hasNext()) {			
			 Object selectedObject = files.next(); 
			 if (selectedObject instanceof AtomicSoundComponentEditPart ||
					 selectedObject instanceof CompositeSoundComponentEditPart ||
					 selectedObject instanceof LinkEditPart ){
				 
				selectedEditParts.add((EditPart) selectedObject);		
				
			 }
		}		
		
		try {
			soundgatesComposite.diagram.soundcomponents.AtomicSoundComponentLibrary.
				setXMLFolder(AtomicSoundComponentLibrary.getXMLFolder());			
			
			
			IProject project = AtomicSoundComponentLibrary.getXMLFolder().getProject();
			IPath location = project.getLocation();
			String filePath = findNewFileName(location.toString());
			
			CompositeSoundComponentImporter.
				createWorkbenchWithCompositeSoundComponentFromXML(						
						filePath,						
						CompositeSoundComponentExporter.
						getCompositeSoundComponentXMLDocumentFromEditParts(selectedEditParts));
			
			project.refreshLocal(1, null);
			
		} catch (IOException e) {

		} catch (CoreException e) {
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
	
	public String findNewFileName(String location){		
		String filePath;
		File test;
		for(int i=1; i<=100; i++){
			filePath = location+"/new" + i + ".sgcd";
			test = new File(filePath);
			if(!test.exists())
				return filePath;
		}
		return "";
	}
}
