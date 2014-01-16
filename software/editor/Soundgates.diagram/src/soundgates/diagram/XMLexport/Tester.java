package soundgates.diagram.XMLexport;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

import org.eclipse.emf.common.util.EList;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Delegation;
import soundgates.Direction;
import soundgates.Link;
import soundgates.Patch;
import soundgates.Port;
import soundgates.SoundComponent;
import soundgates.diagram.messageDialogs.MessageDialogs;
import soundgates.diagram.soundcomponents.CompositeSoundComponentLibrary;

public class Tester {
	
	Patch patchToTest;
	
	LinkedList<String> ioComponentNames = new LinkedList<>();
	LinkedList<AtomicSoundComponent> ioComponents = new LinkedList<>();
	LinkedList<CompositeSoundComponent> compositeSoundComponents =
			new LinkedList<CompositeSoundComponent>();
	
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
		
		// test containers of links
		boolean modelChanged = false;
		for(Link link : links){
			if(!(link.getSource().getComponent().eContainer()==compositeSoundComponent)){
				compositeSoundComponent.getLinks().remove(link);
				setCorrectContainerForLink(link, compositeSoundComponents);
				modelChanged = true;
			}
		}
		
		if(modelChanged){
			try {
				compositeSoundComponent.eResource().save(Collections.EMPTY_MAP);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// test delegations
		for(Delegation delegation : compositeSoundComponent.getDelegations()){
			if (testDelegation(compositeSoundComponent, delegation) == false)
				return false;
		}
		
		// test ports
		boolean outport = false;
		for(Port port : compositeSoundComponent.getPorts()){			
			if ( testCompositeSoundComponentPort(port, compositeSoundComponent, testCurrentComponent) == false )
				return false;
			
			if(port.getDirection()==Direction.OUT) outport = true;
		}
		if(!outport){
			MessageDialogs.compositeSoundComponentHasNoOutPort(compositeSoundComponent.getName());
			return false;
		}
		
		// test embedded components
		if ( testEmbeddedSoundComponents(
				compositeSoundComponent.getName(), 
				compositeSoundComponent.getEmbeddedComponents()) == false)
		return false;
			
		for(SoundComponent soundComponent : compositeSoundComponent.getEmbeddedComponents()){
			if(soundComponent instanceof CompositeSoundComponent){
				if (testCompositeSoundComponent((CompositeSoundComponent) soundComponent, false) == false)
					return false;
				else
					compositeSoundComponents.add((CompositeSoundComponent) soundComponent);
			}
			else if(soundComponent instanceof AtomicSoundComponent){
				if (testAtomicSoundComponent((AtomicSoundComponent) soundComponent) == false)
					return false;
			}
		}
		
		return true;
	}

	public boolean testAtomicSoundComponent(AtomicSoundComponent atomicSoundComponent){

		// test names of IO components
		if (atomicSoundComponent.getType().equals("IO")){
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
		
		// test ports
		for(Port port : atomicSoundComponent.getPorts()){			
			if ( testAtomicSoundComponentPort(port, atomicSoundComponent) == false )
				return false;
		}

		return true;
	}
	
	public boolean testCompositeSoundComponentPort(Port port, CompositeSoundComponent parentComponent, boolean testCurrentComponent){
		
		if(port.getName()==null || "".equals(port.getName())){
			MessageDialogs.notAllPortsHaveAName(parentComponent.getName());
			return false;
		}
		
		
			if(port.getDirection() == Direction.IN)
			{
				if (port.getOutgoingConnection().size()==0){
					MessageDialogs.portHasNoDelegation(parentComponent.getName(), port.getName());
					return false;
				}
				
				if(!testCurrentComponent){
					if (port.getIncomingConnection()==null){
						MessageDialogs.portHasNoIncomingConnection(parentComponent.getName(), port.getName());
						return false;
					}
				}
			}
			if(port.getDirection() == Direction.OUT)
			{
				if (port.getIncomingConnection()==null){
					MessageDialogs.portHasNoDelegation(parentComponent.getName(), port.getName());
					return false;
				}
				
				if(!testCurrentComponent){
					if(port.getOutgoingConnection().size()==0){
						MessageDialogs.portHasNoOutgoingConnection(parentComponent.getName(), port.getName());
						return false;
					}
				}
			}
		
		return true;
	}
	
	public boolean testAtomicSoundComponentPort(Port port, AtomicSoundComponent parentComponent){		
		if(port.getDirection() == Direction.IN)
		{
			if(port.getOutgoingConnection().size()>0){
				MessageDialogs.inPortHasOutgoingConnection(parentComponent.getName(), port.getName());
				return false;
			}
			if (port.getIncomingConnection()==null){
				MessageDialogs.portHasNoIncomingConnection(parentComponent.getName(), port.getName());
				return false;
			}
		}
		if(port.getDirection() == Direction.OUT)
		{
			if(port.getIncomingConnection() != null){
				MessageDialogs.outPortHasIncomingConnection(parentComponent.getName(), port.getName());
				return false;
			}
			if(port.getOutgoingConnection().size()==0){
				MessageDialogs.portHasNoOutgoingConnection(parentComponent.getName(), port.getName());
				return false;
			}
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
			
			// composite components
			else if(element instanceof CompositeSoundComponent){				
				if( testCompositeSoundComponent((CompositeSoundComponent) element, false) == false)
					return false;
				
				compositeSoundComponents.add((CompositeSoundComponent) element);
			}
			
			// links
			else if(element instanceof Link){
				if (testLink(patch, (Link) element) == false)
					return false;
				links.add((Link) element);
				// test daty type?
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
			
		if(link.getSource().getDirection()==Direction.IN)
		{
			String parentString;
			if(parent instanceof Patch) parentString = "the patch";
			else parentString = ((CompositeSoundComponent)parent).getName();
			
			MessageDialogs.inPortAsSource(parentString, link.getSource().getName());
			
			return false;
		}
		if(link.getTarget().getDirection()==Direction.OUT)
		{
			String parentString;
			if(parent instanceof Patch) parentString = "the patch";
			else parentString = ((CompositeSoundComponent)parent).getName();
			
			MessageDialogs.outPortAsTarget(parentString, link.getSource().getName());
			
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
				if(((AtomicSoundComponent) soundComponent).getType().equals("IO") )
				{
					MessageDialogs.compositeSoundComponentContaintsIOBlock(name);
					return false;
				}
				else if(((AtomicSoundComponent) soundComponent).getType().equals("SoundOutput"))
				{
					MessageDialogs.compositeSoundComponentContaintsSoundOutputBlock(name);
					return false;
				}
			}
		}
			
		return true;
	}
	
	
}
