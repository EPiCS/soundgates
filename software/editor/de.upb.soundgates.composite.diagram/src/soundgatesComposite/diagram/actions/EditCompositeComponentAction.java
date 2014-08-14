package soundgatesComposite.diagram.actions;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import soundgatesComposite.diagram.XMLexport.CompositeSoundComponentImporter;
import soundgatesComposite.diagram.messageDialogs.MessageDialogs;
import soundgatesComposite.diagram.soundcomponents.AtomicSoundComponentLibrary;
import soundgatesComposite.diagram.soundcomponents.CompositeSoundComponentLibrary;



public class EditCompositeComponentAction implements IObjectActionDelegate{

	@Override
	public void run(IAction action) {
		IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		Iterator<?> files = structuredSelection.iterator();
		while (files.hasNext()) {			
			 Object selectedObject = files.next(); 
			 if (selectedObject instanceof IResource){
					IResource xmlFile = (IResource) selectedObject;	
					
					String newFileName = xmlFile.getName().replace(".xml", ".sgcd");
					
					IProject iProject = xmlFile.getProject();		
					
					
					if(iProject.getFile(newFileName).exists()){
						if (!MessageDialogs.replaceExistingEMFFile(newFileName))
							return;
					}
					
					if(!iProject.getFolder("soundcomponents").exists())
					{
						MessageDialogs.soundcomponentsFolderMissing(iProject.getName());
						return;
					}
					
					AtomicSoundComponentLibrary.setXMLFolder(iProject.getFolder("soundcomponents"));
					CompositeSoundComponentLibrary.setXMLFolder(iProject.getFolder("soundcomponents"));				
					
					String newFilePath = iProject.getLocation() + "/" + xmlFile.getName().replace(".xml", ".sgcd");
					
					try {
						File file = new File(iProject.getLocation() + "/soundcomponents/" + xmlFile.getName());
						DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
						Document doc = dBuilder.parse(file);
											
						CompositeSoundComponentImporter.createWorkbenchWithCompositeSoundComponentFromXML(newFilePath,doc);		
						iProject.refreshLocal(1, null);			
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
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
