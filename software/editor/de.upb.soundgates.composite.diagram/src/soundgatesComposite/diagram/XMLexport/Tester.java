package soundgatesComposite.diagram.XMLexport;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import org.eclipse.emf.common.util.EList;

import soundgatesComposite.AtomicSoundComponent;
import soundgatesComposite.CompositeSoundComponent;
import soundgatesComposite.DataType;
import soundgatesComposite.Delegation;
import soundgatesComposite.Direction;
import soundgatesComposite.Link;
import soundgatesComposite.Port;
import soundgatesComposite.SoundComponent;
import soundgatesComposite.diagram.messageDialogs.MessageDialogs;
import soundgatesComposite.diagram.soundcomponents.AtomicSoundComponentLibrary;
import soundgatesComposite.diagram.soundcomponents.CompositeSoundComponentLibrary;

public class Tester {
	
	private String projectPath;
	private HashMap<String,Boolean> ports = new HashMap<>();
	
	public boolean testCompositeSoundComponent(CompositeSoundComponent compositeSoundComponent, boolean testCurrentComponent){
		
		LinkedList<Link> links = new LinkedList<>();
		
		// test name
		if (compositeSoundComponent.getName()==null || "".equals(compositeSoundComponent.getName())){
			MessageDialogs.compositeSoundComponentHasNoName();
			return false;
		}
		
		// test links
		for(Link link : compositeSoundComponent.getLinks()){
			if(testLink(compositeSoundComponent, link) == false)
				return false;
			else
				links.add(link);
		}
		
		// test delegations
		for(Delegation delegation : compositeSoundComponent.getDelegations()){
			if (testDelegation(compositeSoundComponent, delegation) == false)
				return false;
		}
		
		// test ports
		for(Port port : compositeSoundComponent.getPorts()){		
			if ( testCompositeSoundComponentPort(port, compositeSoundComponent) == false )
				return false;
			else
				ports.put(port.getName(), true);
		}
		
		// test embedded components
		if ( testEmbeddedSoundComponents(
				compositeSoundComponent.getName(), 
				compositeSoundComponent.getEmbeddedComponents()) == false)
		return false;
			
		for(SoundComponent soundComponent : compositeSoundComponent.getEmbeddedComponents()){
			if(soundComponent instanceof AtomicSoundComponent){
				if (testAtomicSoundComponent((AtomicSoundComponent) soundComponent) == false)
					return false;
			}
		}
		
		return true;
	}

	public boolean testAtomicSoundComponent(AtomicSoundComponent atomicSoundComponent){	
		
		if (atomicSoundComponent.getType().equals(AtomicSoundComponentLibrary.wavePlayerComponentType)){
			// test file references
			String relativeFileName = atomicSoundComponent.getUserStringProperties().get("FileName");
			String filePath = projectPath+"/" +AtomicSoundComponentLibrary.samplesFolderName + "/"+relativeFileName;
			File testFile = new File(filePath);
			if(!testFile.exists()){
				MessageDialogs.fileNotFound(filePath);
				return false;
			}
		}

		return true;
	}
	
	public boolean testCompositeSoundComponentPort(Port port, CompositeSoundComponent parentComponent){
		
		if(port.getName()==null || "".equals(port.getName())){
			MessageDialogs.notAllPortsHaveAName(parentComponent.getName());
			return false;
		}
		if(ports.containsKey(port.getName())){
			MessageDialogs.portsMustHaveUniqueNames();
			return false;
		}
		return true;
	}

	public boolean testLink(Object parent, Link link){
		
		// test directions		
		String parentString;
		parentString = ((CompositeSoundComponent)parent).getName();
		
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
		
		//test types
		if(link.getSource().getDataType()!=link.getTarget().getDataType()){
			MessageDialogs.soundToControlConnection(parentString, link.getSource().getName(), link.getTarget().getName());
			return false;
		}
		
		return true;
	}

	public boolean testDelegation(CompositeSoundComponent parent, Delegation delegation){
		// test directions
			
		if(delegation.getSource().getDirection()!=delegation.getTarget().getDirection()){
			MessageDialogs.delegationError(
					parent.getName(),
					delegation.getSource().getName()+" ("+delegation.getSource().getDirection()+")",
					delegation.getTarget().getName()+" ("+delegation.getTarget().getDirection()+")"
				);
			return false;
		}		
		
		if(delegation.getSource().getDataType()==DataType.SOUND && delegation.getTarget().getDataType()==DataType.CONTROL){
			MessageDialogs.soundToControlConnection(parent.getName(), delegation.getSource().getName(), delegation.getTarget().getName());
			return false;
		}
		
		// special case
		if(parent.getPorts().contains(delegation.getTarget()) && 
				delegation.getTarget().getDirection()==Direction.IN)
		{
			MessageDialogs.delegation2Error(parent.getName(), delegation.getSource().getName(), delegation.getTarget().getName());
			return false;
		}
		// special case
		if(parent.getPorts().contains(delegation.getSource()) && 
				delegation.getTarget().getDirection()==Direction.OUT)
		{
			MessageDialogs.delegation2Error(parent.getName(), delegation.getSource().getName(), delegation.getTarget().getName());
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
