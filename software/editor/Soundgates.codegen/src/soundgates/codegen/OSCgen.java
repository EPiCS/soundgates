package soundgates.codegen;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.jface.dialogs.MessageDialog;

import soundgates.AtomicSoundComponent;
import soundgates.DataType;
import soundgates.Element;
import soundgates.Patch;
import soundgates.SoundgatesPackage;

public class OSCgen {

	private final String oscFolderName = "OSCcode";

	private final String getter = "char** getInteractiveComponents(){ return interactiveComponents; } \n";
	private final String arrayDecl = "char* interactiveComponents[] =";
	private LinkedList<AtomicSoundComponent> ioComponents;
	private LinkedList<String> ioComponentNames;
	
	IFolder oscFolder = null;
	
	public OSCgen() {
		ioComponents = new LinkedList<AtomicSoundComponent>();
		ioComponentNames = new LinkedList<String>();
	}
	
	public Patch getPatch(String path){
	    SoundgatesPackage.eINSTANCE.eClass();
	    
	    // Register the XMI resource factory for the .xmi extension

	    Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	    Map<String, Object> m = reg.getExtensionToFactoryMap();
	    m.put("xmi", new XMIResourceFactoryImpl());

	    // Obtain a new resource set
	    ResourceSet resSet = new ResourceSetImpl();

	    // Get the resource
	    Resource resource = resSet.getResource(URI.createURI(path), true);
	    // Get the first model element and cast it to the right type, in my
	    // example everything is hierarchical included in this first node
	    Patch patch = (Patch) resource.getContents().get(0);
	    return patch;		
	}
	
	public String getOSCArrayForPatch(Patch patch) throws IOException, CoreException {
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("{");
		
		for(AtomicSoundComponent atomicSoundComponent : ioComponents){
			// "/frequenz \"f\" "
			
			// get the data type over the port which is connected to the port of the i/o component
			DataType dataType = atomicSoundComponent.getPorts().get(0).getOutgoingConnection().get(0).getTarget().getDataType();
			
			if(dataType==DataType.FLOAT)
				stringBuffer.append("\"" + atomicSoundComponent.getName() + " \\\"f\\\"\","  );
			else if(dataType==DataType.INTEGER)
				stringBuffer.append("\"" + atomicSoundComponent.getName() + " \\\"i\\\"\","  );
			else if(dataType==DataType.BOOLEAN){
				stringBuffer.append("\"" + atomicSoundComponent.getName() + " \\\"T\\\"\","  );
				stringBuffer.append("\"" + atomicSoundComponent.getName() + " \\\"F\\\"\","  );
			}
		}
		
		// delete the last comma
		stringBuffer.deleteCharAt(stringBuffer.length()-1);
		
		stringBuffer.append("}; \n");
		
		return stringBuffer.toString();
	}	

	public void generate(IFile file) throws CoreException, IOException {
		Patch patch = getPatch(file.getFullPath().toPortableString());
		
		if(!test(patch)){
			return;
		}

		String text = arrayDecl + getOSCArrayForPatch(patch) + getter;
				
		IProject project = file.getProject();
		oscFolder = project.getFolder(oscFolderName);
		if (oscFolder.exists())
			oscFolder.delete(true, null);
			oscFolder.create(IResource.NONE, true, null);
		
		IFile newFile = oscFolder.getFile("osc.c");
		newFile.create(new ByteArrayInputStream(text.getBytes()), IFile.FORCE, null);
	}
	
	public boolean test(Patch patch) throws IOException, CoreException {
		
		// test uniqueness of names
		for(Element element : patch.getElements()){
			if (element instanceof AtomicSoundComponent){
				AtomicSoundComponent atomicSoundComponent = (AtomicSoundComponent) element;
				if(atomicSoundComponent.getType().equals("IO")){
					ioComponents.add(atomicSoundComponent);
					
					// test uniqueness of names
					if(listContainsString(ioComponentNames, atomicSoundComponent.getName())){
						MessageDialog.openWarning(null, "The names of I/O-components are not unique", "The names of I/O-components must be unique.");
						return false;
					}
					else{
						ioComponentNames.add(atomicSoundComponent.getName());
					}
				}
			}
		}
		
		return true;
	}
	
	private boolean listContainsString(LinkedList<String> list, String string){
		for(String s : list)
			if (s.equals(string))
				return true;
		return false;
	}
}
