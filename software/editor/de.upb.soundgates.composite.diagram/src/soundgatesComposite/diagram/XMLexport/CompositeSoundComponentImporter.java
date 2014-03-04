package soundgatesComposite.diagram.XMLexport;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import soundgatesComposite.CompositeSoundComponent;
import soundgatesComposite.SoundgatesCompositeFactory;
import soundgatesComposite.Workbench;
import soundgatesComposite.diagram.edit.parts.WorkbenchEditPart;
import soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin;
import soundgatesComposite.diagram.soundcomponents.AtomicSoundComponentLibrary;
import soundgatesComposite.diagram.soundcomponents.CompositeSoundComponentXMLHandler;
import soundgatesComposite.impl.SoundgatesCompositeFactoryImpl;


public class CompositeSoundComponentImporter{
	
	public static Workbench getWorkbenchFromXML(Document doc) {
		try {

				
			Workbench workbench = SoundgatesCompositeFactory.eINSTANCE.createWorkbench();
			
				Element rootElement = (Element) doc.getElementsByTagName("CompositeSoundComponent").item(0);
				
				CompositeSoundComponent compositeSoundComponent =  
						CompositeSoundComponentXMLHandler.createCompositeSoundComponentFromXMLElement(rootElement);
				
				workbench.getCompositeSoundComponents().add(compositeSoundComponent);
				
				return workbench;
		}	 
			catch (Exception e) {
				//TODO Differenziertere Exceptions
				System.out.println("Malformed XML Document!");
				e.printStackTrace();
			} 
		return null;
	}

	
	public static void createWorkbenchWithCompositeSoundComponentFromXML(String filePath, Document doc) throws IOException {  
		  Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		  Map<String, Object> m = reg.getExtensionToFactoryMap();
		  m.put(".sgcd", new SoundgatesCompositeFactoryImpl());

		  Workbench patch = getWorkbenchFromXML(doc);
		  
		  if(patch!=null){
			  ResourceSet resSet = new ResourceSetImpl();
			  Resource resource = resSet.createResource(URI.createFileURI(filePath));
			  resource.getContents().add(patch);
			  
			  Diagram diag = 
			  ViewService.createDiagram(
					  patch,
						WorkbenchEditPart.MODEL_ID,
						SoundgatesCompositeDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
			  
			  resource.getContents().add(diag);
			  
			  resource.save(Collections.EMPTY_MAP);

			  openDiagram(filePath);
		  }
	}
	
	private static void openDiagram(String filePath){
		try {
		  String[] segments = filePath.split("/");
		  String name = segments[segments.length-1];
		  IFile file = AtomicSoundComponentLibrary.getXMLFolder().getProject().getFile(name);
		  
		  IWorkbenchPage page =  PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		  
		  IDE.openEditor(page, file, true);
		} catch(Exception e){
			 
		}
	}
	
}		
