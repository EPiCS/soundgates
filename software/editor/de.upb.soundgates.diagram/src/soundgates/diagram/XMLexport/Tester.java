package soundgates.diagram.XMLexport;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

import org.eclipse.emf.common.util.EList;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Direction;
import soundgates.Link;
import soundgates.Patch;
import soundgates.Port;
import soundgates.SoundComponent;
import soundgates.diagram.messageDialogs.MessageDialogs;
import soundgates.diagram.soundcomponents.AtomicSoundComponentLibrary;
import soundgates.diagram.soundcomponents.CompositeSoundComponentLibrary;

public class Tester {
	
	private String projectPath;
	private LinkedList<String> ioComponentNames = new LinkedList<>();
	private boolean patchContainsSoundOutput = false;
	private LinkedList<CompositeSoundComponent> compositeSoundComponents =
			new LinkedList<CompositeSoundComponent>();


	public boolean testAtomicSoundComponent(AtomicSoundComponent atomicSoundComponent){

		// test names of IO components
		if (atomicSoundComponent.getType().equals(AtomicSoundComponentLibrary.IOComponentType)){
			for(String existingName : ioComponentNames){
				if(existingName.equals(atomicSoundComponent.getName())){
						MessageDialogs.ioComponentsMustHaveUniqueNames();
						return false;
				}
			}			
			
			if(atomicSoundComponent.getFloatProperties().get("MinValue") >
			  	atomicSoundComponent.getFloatProperties().get("MaxValue")){
				MessageDialogs.minValueGreaterThanMaxValue(atomicSoundComponent.getName());
				return false;
			}
				
			ioComponentNames.add(atomicSoundComponent.getName());			
		}		
		
		else if (atomicSoundComponent.getType().equals(AtomicSoundComponentLibrary.wavePlayerComponentType)){
			// test file references
			String relativeFileName = atomicSoundComponent.getUserStringProperties().get("FileName");
			String filePath = projectPath+"/" +AtomicSoundComponentLibrary.samplesFolderName + "/"+relativeFileName;
			File testFile = new File(filePath);
			if(!testFile.exists()){
				MessageDialogs.fileNotFound(filePath);
				return false;
			}
		}
		
		else if (atomicSoundComponent.getType().equals(AtomicSoundComponentLibrary.soundOutputType)){
			if(!patchContainsSoundOutput)
				patchContainsSoundOutput = true;
			else {
				MessageDialogs.patchContainsMoreThanOneSoundOutput();
				return false;
			}
		}

		return true;
	}
	
	public boolean testCompositeSoundComponentPort(Port port, CompositeSoundComponent parentComponent, boolean testCurrentComponent){
		
		if(port.getName()==null || "".equals(port.getName())){
			MessageDialogs.notAllPortsHaveAName(parentComponent.getName());
			return false;
		}
		
		return true;
	}
		
	public boolean testPatch(Patch patch){
		
		LinkedList<Link> links = new LinkedList<Link>();				
				
		for(soundgates.Element element : patch.getElements()){			
			
			// atomic components
			if(element instanceof AtomicSoundComponent){				
				if( testAtomicSoundComponent((AtomicSoundComponent) element) == false)
					return false;
			}
			
			// links
			else if(element instanceof Link){
				if (testLink(patch, (Link) element) == false)
					return false;
				links.add((Link) element);
			}				
		}
		
		// test containers of links
		boolean modelChanged = false;
		for(Link link : links){
			if(!(link.getSource().getComponent().eContainer()==patch)){
				patch.getElements().remove(link);
				setCorrectContainerForLink(link, compositeSoundComponents);
				modelChanged = true;
			}
		}
		
		if(modelChanged){
			try {
				patch.eResource().save(Collections.EMPTY_MAP);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
				
		return true;
	}
	
	

	// this method changes the model
	private void setCorrectContainerForLink(Link link,
			LinkedList<CompositeSoundComponent> compositeSoundComponents){
		
		CompositeSoundComponent compositeSoundComponentContainer = 
				link.getSource().getComponent().getParentComponent();
		
		for(CompositeSoundComponent compositeSoundComponent	: compositeSoundComponents){
			if(compositeSoundComponent==compositeSoundComponentContainer){				
				compositeSoundComponent.getLinks().add(link);
			}
		}
	}

	public boolean testLink(Object parent, Link link){
		// test directions
		
		String parentString;
		if(parent instanceof Patch) parentString = "the patch";
		else parentString = ((CompositeSoundComponent)parent).getName();
		
		if(link.getSource().getDirection()==Direction.IN)
		{			
			MessageDialogs.inPortAsSource(parentString, link.getSource().getName());			
			return false;
		}
		if(link.getTarget().getDirection()==Direction.OUT)
		{		
			MessageDialogs.outPortAsTarget(parentString, link.getSource().getName());			
			return false;
		}
		
		if(link.getSource().getDataType()!=link.getTarget().getDataType()){
			MessageDialogs.soundToControlConnection(parentString, link.getSource().getName(), link.getTarget().getName());
			return false;
		}
		
		return true;
	}

	
	public boolean shouldWriteFileForCompositeSoundComponent(String name){
		// if the composite sound component is in the folder
		if (CompositeSoundComponentLibrary.componentsFolderContaintsFile(name+".xml"))
		{
			// the user is asked if he want to replace the file
			boolean replace = MessageDialogs.replaceExistingCompositeSoundComponentDialog(name);			
			return replace;
		}
		else return true;
	}
	
	public boolean testEmbeddedSoundComponents(String name, EList<SoundComponent> components){
		if(components.size()==0){
			MessageDialogs.compositeSoundComponentHasNoEmbeddedComponents(name);
			return false;
		}
		for(SoundComponent soundComponent : components){
			if(soundComponent instanceof AtomicSoundComponent){
				if(((AtomicSoundComponent) soundComponent).getType().equals(AtomicSoundComponentLibrary.IOComponentType) )
				{
					MessageDialogs.compositeSoundComponentContaintsIOBlock(name);
					return false;
				}
				else if(((AtomicSoundComponent) soundComponent).getType().equals(AtomicSoundComponentLibrary.soundOutputType))
				{
					MessageDialogs.compositeSoundComponentContaintsSoundOutputBlock(name);
					return false;
				}
			}
		}
			
		return true;
	}
	
	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}
	
}
