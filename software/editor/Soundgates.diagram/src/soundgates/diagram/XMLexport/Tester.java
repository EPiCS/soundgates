package soundgates.diagram.XMLexport;

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
	
	public boolean testCompositeSoundComponent(CompositeSoundComponent compositeSoundComponent){
		
		// test name
		if (compositeSoundComponent.getName()==null || "".equals(compositeSoundComponent.getName())){
			MessageDialogs.compositeSoundComponentHasNoName();
			return false;
		}
		
		// test links
		for(Link link : compositeSoundComponent.getLinks()){
			if(testLink(compositeSoundComponent, link) == false)
				return false;
		}
		
		// test delegations
		for(Delegation delegation : compositeSoundComponent.getDelegations()){
			if (testDelegation(compositeSoundComponent, delegation) == false)
				return false;
		}
		
		// test ports
		boolean inport = false;
		boolean outport = false;
		for(Port port : compositeSoundComponent.getPorts()){			
			if ( testCompositeSoundComponentPort(port, compositeSoundComponent) == false )
				return false;
			
			if(port.getDirection()==Direction.IN) inport = true;
			if(port.getDirection()==Direction.OUT) outport = true;
		}
		if(!inport){
			MessageDialogs.compositeSoundComponentHasNoInPort(compositeSoundComponent.getName());
			return false;
		}
		if(!outport){
			MessageDialogs.compositeSoundComponentHasNoOutPort(compositeSoundComponent.getName());
			return false;
		}
		
		// test embedded components
//		if(compositeSoundComponent.getEmbeddedComponents().size()==0){
//			MessageDialogs.compositeSoundComponentHasNoEmbeddedComponents(compositeSoundComponent.getName());
//		}			
			
		for(SoundComponent soundComponent : compositeSoundComponent.getEmbeddedComponents()){
			if(soundComponent instanceof CompositeSoundComponent){
				if (testCompositeSoundComponent((CompositeSoundComponent) soundComponent) == false)
					return false;
			}
			else if(soundComponent instanceof AtomicSoundComponent){
				if (testAtomicSoundComponent((AtomicSoundComponent) soundComponent) == false)
					return false;
			}
		}
		
		return true;
	}

	public boolean testAtomicSoundComponent(AtomicSoundComponent atomicSoundComponent){

		// test ports
		for(Port port : atomicSoundComponent.getPorts()){			
			if ( testAtomicSoundComponentPort(port, atomicSoundComponent) == false )
				return false;
		}

		return true;
	}
	
	public boolean testCompositeSoundComponentPort(Port port, CompositeSoundComponent parentComponent){
		
		if(port.getName()==null || "".equals(port.getName())){
			MessageDialogs.notAllPortsHaveAName(parentComponent.getName());
			return false;
		}
		
		if(port.getDirection() == Direction.IN)
		{
			if (port.getIncomingConnection()==null){
				MessageDialogs.portHasNoIncomingConnection(parentComponent.getName(), port.getName());
				return false;
			}			
		}
		if(port.getDirection() == Direction.OUT)
		{
			if(port.getOutgoingConnection().size()==0){
				MessageDialogs.portHasNoOutgoingConnection(parentComponent.getName(), port.getName());
				return false;
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
		
		for(soundgates.Element element : patch.getElements()){			
			
			// atomic components
			if(element instanceof AtomicSoundComponent){					
				if( testAtomicSoundComponent((AtomicSoundComponent) element) == false)
					return false;
			}
			
			// composite components
			else if(element instanceof CompositeSoundComponent){				
				if( testCompositeSoundComponent((CompositeSoundComponent) element) == false)
					return false;
			}
			
			// links
			else if(element instanceof Link){
				if (testLink(patch, (Link) element) == false)
					return false;
				
				// test daty type?
			}				
		}
		return true;
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
}
