package soundgates.codegen.synthesizer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.util.EcoreUtil;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Connection;
import soundgates.Delegation;
import soundgates.Element;
import soundgates.Link;
import soundgates.Patch;
import soundgates.Port;
import soundgates.SoundComponent;
import soundgates.SoundgatesFactory;
import soundgates.codegen.CodeGenHelper;
import soundgates.diagram.soundcomponents.AtomicSoundComponentXMLHandler;

public class SynthDataGen {
	private HashMap<SoundComponent, Integer> uniqueIds;
	private HashMap<SoundComponent, String> implTypes;
	private int uniqueCounter = 0;
	private int hwSlot = 0;
	private CodeGenHelper codeGenHelper;
	
	public void generateSynthData(Patch patch, IFile file){
		SynthData synthData = new SynthData();
		codeGenHelper = new CodeGenHelper();
		uniqueIds = new HashMap<SoundComponent, Integer>();
		implTypes = new HashMap<SoundComponent, String>();

		boolean compositesUnrolled = false;		
		while(!compositesUnrolled){
			LinkedList<CompositeSoundComponent> componentsToRemove = getComponentsToRemove(patch);
			if(componentsToRemove.size()>0){
				patch = unrollCompositeSoundComponents(patch, componentsToRemove);
			}
			else{
				compositesUnrolled=true;
			}
		}
		
		for (Element element : patch.getElements()) {
			if(element instanceof AtomicSoundComponent)
				synthData.addSynthData(handleAtomicSoundComponent((AtomicSoundComponent) element));
		} 
		for (Element element : patch.getElements()) {
			if (element instanceof Link)
				synthData.addSynthData(handleLink((Link) element));
		} 
		writeToFile(synthData.getRepresentation(), file);	
	}
	
	private void writeToFile(String text, IFile file){
		
		try {
			IFile newFile = file.getProject().getFile(file.getName().replace(".soundgates", ".tgf"));
			
			InputStream code = new ByteArrayInputStream(text.getBytes());
			
			if(newFile.exists())
				newFile.delete(true, null);
			
			newFile.create(code, IResource.FORCE, null);
			newFile.getParent().refreshLocal(1, null);
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public SynthData handleAtomicSoundComponent(AtomicSoundComponent atomicSoundComponent){
		
		SynthData data = new SynthData();
		
		if (!uniqueIds.containsKey(atomicSoundComponent)){
			uniqueIds.put(atomicSoundComponent, uniqueCounter++);
		}		
					
		String type = atomicSoundComponent.getType();
		String implName = atomicSoundComponent.getStringProperties().
				get(AtomicSoundComponentXMLHandler.DEVICE_PREFIX_IMPLNAME);
		String implType = getBestImplementationType(atomicSoundComponent);		

		if(atomicSoundComponent.getType().equals("IO")){
			
			/*  Format: 
			 *   
			 *  input/sw/'/BeatLevel \"f\" [0:800]'
			 * 
			 */
			
			String oscAddress = atomicSoundComponent.getName();
			String oscDataType = atomicSoundComponent.getPorts().get(0).getDataType().getName();				
			String range = "[" + atomicSoundComponent.getFloatProperties().get("MinValue") +
					       ":" + atomicSoundComponent.getFloatProperties().get("MaxValue") +
					       "]";
			
			data.addIOComponent(uniqueIds.get(atomicSoundComponent), type, implName, implType, "", hwSlot, oscAddress, oscDataType, range);
		}			
		else{
			
			StringBuffer values = new StringBuffer();	
			int floatPropertiesSize = atomicSoundComponent.getFloatProperties().size();
			for(int i=0; i<floatPropertiesSize; i++){
				values.append(atomicSoundComponent.getFloatProperties().get(i).getValue().toString());
				if(i!=floatPropertiesSize-1)
					values.append(",");
			}
			String value=values.toString();
			
			data.addComponent(uniqueIds.get(atomicSoundComponent), type, implName, implType, value, hwSlot);
		}
		
		implTypes.put(atomicSoundComponent, implType);
		if(implType.equals("hw"))
			hwSlot++;
			
		return data;
	}
	
	public SynthData handleLink(Link link){
		SynthData data = new SynthData();
		
		SoundComponent sourceComponent = link.getSource().getComponent();
		SoundComponent targetComponent = link.getTarget().getComponent();
		
		int source = uniqueIds.get(sourceComponent);
		int sink = uniqueIds.get(targetComponent);

		HashMap<Port,Integer> sourcePortMappings =  codeGenHelper.parsePortMappings((AtomicSoundComponent) sourceComponent, 
				AtomicSoundComponentXMLHandler.DEVICE_PREFIX_PORT_MAPPINGS + implTypes.get(sourceComponent) );
		
		int sourcePort = sourcePortMappings.get(link.getSource());
		
		HashMap<Port,Integer> targetPortMappings =  codeGenHelper.parsePortMappings((AtomicSoundComponent) targetComponent, 
				AtomicSoundComponentXMLHandler.DEVICE_PREFIX_PORT_MAPPINGS + implTypes.get(targetComponent) );
		
		int sinkPort = targetPortMappings.get(link.getTarget());
		
		data.addLink(source, sink, sourcePort, sinkPort);
	
		return data;
	}	
	
	private String getBestImplementationType(AtomicSoundComponent atomicSoundComponent){
//		//TODO: actual implementation
//		double r = Math.random();
//		if (r<=0.5)
//			return "sw";
//		else 
//			return "hw";
		
		return "sw";
	}
	
	private LinkedList<CompositeSoundComponent> getComponentsToRemove(Patch patch){
		LinkedList<CompositeSoundComponent> componentsToRemove = new LinkedList<CompositeSoundComponent>();
		
		for(Element element : patch.getElements()){			
			if(element instanceof CompositeSoundComponent){
				CompositeSoundComponent compositeSoundComponent = (CompositeSoundComponent) element;
				componentsToRemove.add(compositeSoundComponent);
			}
		}
		return componentsToRemove;
	}
	
	private Patch unrollCompositeSoundComponents(Patch patch, LinkedList<CompositeSoundComponent> componentsToRemove){
		
		for(CompositeSoundComponent compositeSoundComponent : componentsToRemove){

			LinkedList<Connection> connectionsToRemove = new LinkedList<Connection>();
			HashMap<SoundComponent,SoundComponent> soundComponentCopies = new HashMap<SoundComponent,SoundComponent>();		
			
			// add embedded components from the composite component to the patch
			for(SoundComponent soundComponent : compositeSoundComponent.getEmbeddedComponents()){				
				
				SoundComponent soundComponentCopy = null;
				
				if(soundComponent instanceof AtomicSoundComponent){
					soundComponentCopy = EcoreUtil.copy(soundComponent);
					
					((AtomicSoundComponent) soundComponentCopy).setType
						(((AtomicSoundComponent) soundComponent).getType());
				}
				else if(soundComponent instanceof CompositeSoundComponent){
					soundComponentCopy =  
							copyCompositeSoundComponent((CompositeSoundComponent) soundComponent);
				}
				
				soundComponentCopies.put(soundComponent, soundComponentCopy);				
				patch.getElements().add(soundComponentCopy);
			}

			// add links from the composite component to the patch
			for(Link link : compositeSoundComponent.getLinks()){
				
				Link newLink = getLinkCopy(link, soundComponentCopies);
				
				patch.getElements().add(newLink);
			}
			
			// create links for delegations
			for(Delegation delegation : compositeSoundComponent.getDelegations()){
				
				//if incoming delegation
				if(delegation.getSource().getComponent()==compositeSoundComponent){
					
					//find component copy and the corresponding port
					SoundComponent soundComponentCopy = soundComponentCopies.get(delegation.getTarget().getComponent());
					Port portCopy = getPortCopy(soundComponentCopy, delegation.getTarget());
					
					Port newSourcePort = delegation.getSource().getIncomingConnection().getSource();
					Link newLink = SoundgatesFactory.eINSTANCE.createLink();
					newLink.setSource(newSourcePort);
					newLink.setTarget(portCopy);					
					patch.getElements().add(newLink);
					
					//mark the old link to delete
					connectionsToRemove.add(delegation.getSource().getIncomingConnection());
				}
				
				//if outgoing delegation
				else if(delegation.getTarget().getComponent()==compositeSoundComponent){
					for(Connection connection : delegation.getTarget().getOutgoingConnection()){
						
						//find component copy and the corresponding port
						SoundComponent soundComponentCopy = soundComponentCopies.get(delegation.getSource().getComponent());
						Port portCopy = getPortCopy(soundComponentCopy, delegation.getSource());
						
						Port newTargetPort = connection.getTarget();
						Link newLink = SoundgatesFactory.eINSTANCE.createLink();
						newLink.setSource(portCopy);
						newLink.setTarget(newTargetPort);						
						patch.getElements().add(newLink);

						//mark the old link to delete
						connectionsToRemove.add(connection);
					}				
				}
			}
			
			// remove composite components and their links
			EcoreUtil.remove(compositeSoundComponent);
			for(Connection connection : connectionsToRemove){
				EcoreUtil.remove(connection);
			}
			
		}		
		
		return patch;
	}
	
	public Port getPortCopy(SoundComponent soundComponentCopy, Port oldPort){
		for (Port newPort : soundComponentCopy.getPorts()){
			if(oldPort.getName().equals(newPort.getName()))
				return newPort;
		}
		return null;
	}
	
	public CompositeSoundComponent copyCompositeSoundComponent(CompositeSoundComponent originalCompositeSoundComponent){
		CompositeSoundComponent newCompositeSoundComponent = SoundgatesFactory.eINSTANCE.createCompositeSoundComponent();
		newCompositeSoundComponent.setName(originalCompositeSoundComponent.getName());
		
		//copy ports
		HashMap<Port,Port> portCopies = new HashMap<Port,Port>();
		
		for(Port originPort : originalCompositeSoundComponent.getPorts()){
			Port newPort = EcoreUtil.copy(originPort);
			newCompositeSoundComponent.getPorts().add(newPort);
			portCopies.put(originPort, newPort);
		}
		
		//copy embeddedElements		
		HashMap<SoundComponent,SoundComponent> soundComponentCopies = new HashMap<SoundComponent,SoundComponent>();	
		
		for(SoundComponent embOriginalSoundComponent : originalCompositeSoundComponent.getEmbeddedComponents()){
			if(embOriginalSoundComponent instanceof AtomicSoundComponent){
				AtomicSoundComponent newEmbAtomicSoundComponent = (AtomicSoundComponent) EcoreUtil.copy(embOriginalSoundComponent);
				
				((AtomicSoundComponent) newEmbAtomicSoundComponent).setType
					(((AtomicSoundComponent) embOriginalSoundComponent).getType());
				
				newCompositeSoundComponent.getEmbeddedComponents().add(newEmbAtomicSoundComponent);
				soundComponentCopies.put(embOriginalSoundComponent,newEmbAtomicSoundComponent);
			}
			else if(embOriginalSoundComponent instanceof CompositeSoundComponent){
				CompositeSoundComponent embNewCompositeSoundComponent = 
						copyCompositeSoundComponent((CompositeSoundComponent) embOriginalSoundComponent);
				
				newCompositeSoundComponent.getEmbeddedComponents().add(embNewCompositeSoundComponent);
				soundComponentCopies.put(embOriginalSoundComponent,embNewCompositeSoundComponent);
			}
		}
		
		//copy links
		for(Link originalLink : originalCompositeSoundComponent.getLinks()){
			newCompositeSoundComponent.getLinks().add(getLinkCopy(originalLink, soundComponentCopies));
		}
		
		//copy delegations
		for(Delegation originalDelegation : originalCompositeSoundComponent.getDelegations()){
			Delegation newDelegation = SoundgatesFactory.eINSTANCE.createDelegation();
			if(originalDelegation.getSource().getComponent()==originalCompositeSoundComponent){
				newDelegation.setSource(portCopies.get(originalDelegation.getSource()));
								
				SoundComponent targetSoundComponentCopy = soundComponentCopies.get(originalDelegation.getTarget().getComponent());
				Port targetPortCopy = getPortCopy(targetSoundComponentCopy, originalDelegation.getTarget());
				
				newDelegation.setTarget(targetPortCopy);
			}
			else{
				newDelegation.setTarget(portCopies.get(originalDelegation.getTarget()));
				
				SoundComponent sourceSoundComponentCopy = soundComponentCopies.get(originalDelegation.getSource().getComponent());
				Port sourcePortCopy = getPortCopy(sourceSoundComponentCopy, originalDelegation.getSource());
				
				newDelegation.setSource(sourcePortCopy);
			}
			newCompositeSoundComponent.getDelegations().add(newDelegation);
		}
		
		return newCompositeSoundComponent;
	}
	
	public Link getLinkCopy(Link link, HashMap<SoundComponent,SoundComponent> soundComponentCopies){
		Link newLink = SoundgatesFactory.eINSTANCE.createLink();
		
		//find component copies and the corresponding ports
		SoundComponent sourceSoundComponentCopy = soundComponentCopies.get(link.getSource().getComponent());
		Port sourcePortCopy = getPortCopy(sourceSoundComponentCopy, link.getSource());
		
		SoundComponent targetSoundComponentCopy = soundComponentCopies.get(link.getTarget().getComponent());
		Port targetPortCopy = getPortCopy(targetSoundComponentCopy, link.getTarget());
		
		newLink.setSource(sourcePortCopy);
		newLink.setTarget(targetPortCopy);
		
		return newLink;
	}
	
}