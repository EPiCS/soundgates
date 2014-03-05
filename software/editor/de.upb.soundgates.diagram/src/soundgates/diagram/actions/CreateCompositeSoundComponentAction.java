package soundgates.diagram.actions;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
import org.w3c.dom.Document;

import soundgates.diagram.XMLexport.CompositeSoundComponentExporter;
import soundgates.diagram.edit.parts.AtomicSoundComponentEditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponentEditPart;
import soundgates.diagram.edit.parts.LinkEditPart;
import soundgates.diagram.part.SoundgatesDiagramEditor;
import soundgates.diagram.soundcomponents.AtomicSoundComponentLibrary;
import soundgatesComposite.diagram.XMLexport.CompositeSoundComponentImporter;



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
			soundgatesComposite.diagram.soundcomponents.CompositeSoundComponentLibrary.
				setXMLFolder(AtomicSoundComponentLibrary.getXMLFolder());	
			
			IProject project = AtomicSoundComponentLibrary.getXMLFolder().getProject();
			IPath location = project.getLocation();
			String filePath = findNewFileName(location.toString());
			
			String[] segments =	filePath.split("/");
			String componentNameWithExtension = segments[segments.length-1];
			String componentName = componentNameWithExtension.replace(".sgcd", "");			
			
			Document newCompositeSoundComponentXML = CompositeSoundComponentExporter.
									getCompositeSoundComponentXMLDocumentFromEditParts(selectedEditParts,componentName);
			
			//create workbench
			if(newCompositeSoundComponentXML!=null){
				CompositeSoundComponentImporter.
					createWorkbenchWithCompositeSoundComponentFromXML(						
							filePath,newCompositeSoundComponentXML);
				
				//save as xml
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(newCompositeSoundComponentXML);			
				
				String xmlFilePath = AtomicSoundComponentLibrary.getXMLFolder().getRawLocation().toString()+
						"/"+componentNameWithExtension.replace(".sgcd", ".xml");
				StreamResult result = new StreamResult(new File(xmlFilePath));
				
				transformer.transform(source, result);
				
				//refresh
				Object[] editors = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditors();
				for(Object editor : editors){
					if(editor instanceof SoundgatesDiagramEditor){
						((SoundgatesDiagramEditor) editor).updatePalette();
					}
				}
				
				project.refreshLocal(2, null);
			}
			
		} catch (IOException | TransformerException | CoreException e) {

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
			filePath = location+"/NewCompositeSoundComponent" + i + ".sgcd";
			test = new File(filePath);
			if(!test.exists())
				return filePath;
		}
		return "";
	}
}
