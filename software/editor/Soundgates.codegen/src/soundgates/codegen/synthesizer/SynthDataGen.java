package soundgates.codegen.synthesizer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Connection;
import soundgates.Delegation;
import soundgates.Element;
import soundgates.Link;
import soundgates.Patch;
import soundgates.Port;
import soundgates.SoundComponent;
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
		for (Element element : patch.getElements()) {
			synthData.addSynthData(handleElement(element));
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
	
	public SynthData handleElement(Element element){
		if(element instanceof AtomicSoundComponent)
			return handleAtomicSoundComponent((AtomicSoundComponent) element);
		else if (element instanceof CompositeSoundComponent)
			return handleCompositeSoundComponent((CompositeSoundComponent) element);
		else if (element instanceof Link)
			return handleLink((Link) element);
		else 
			return null;
	}		
	
	public SynthData handleAtomicSoundComponent(AtomicSoundComponent atomicSoundComponent){
		
		SynthData data = new SynthData();
		
		if (!uniqueIds.containsKey(atomicSoundComponent)){
			uniqueIds.put(atomicSoundComponent, uniqueCounter++);
		}
		
		String implementationType = getBestImplementationType(atomicSoundComponent);			
		String type = atomicSoundComponent.getType();
		
		Object value = "";			
		if(atomicSoundComponent.getFloatProperties().size()==1)
			value = atomicSoundComponent.getFloatProperties().get(0).getValue();
		

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
			
			data.addIOComponent(uniqueIds.get(atomicSoundComponent), implementationType, type, value, hwSlot, oscAddress, oscDataType, range);
		}			
		else{
			data.addComponent(uniqueIds.get(atomicSoundComponent), implementationType, type, value, hwSlot);
		}
		
		implTypes.put(atomicSoundComponent, implementationType);
		if(implementationType.equals("hw"))
			hwSlot++;
			
		return data;
	}
	
	public SynthData handleLink(Link link){
		SynthData data = new SynthData();
		
		SoundComponent sourceComponent = link.getSource().getComponent();
		SoundComponent targetComponent = link.getTarget().getComponent();
		
		int source = uniqueIds.get(sourceComponent);
		int sink = uniqueIds.get(targetComponent);
		int sourcePort = 0;
		int sinkPort = 0;
		
		HashMap<Port,Integer> sourcePortMappings =  codeGenHelper.parsePortMappings((AtomicSoundComponent) sourceComponent, 
				AtomicSoundComponentXMLHandler.DEVICE_PREFIX_PORT_MAPPINGS + implTypes.get(sourceComponent) );
		
		sourcePort = sourcePortMappings.get(link.getSource());
		
		HashMap<Port,Integer> targetPortMappings =  codeGenHelper.parsePortMappings((AtomicSoundComponent) targetComponent, 
				AtomicSoundComponentXMLHandler.DEVICE_PREFIX_PORT_MAPPINGS + implTypes.get(targetComponent) );
		
		sinkPort = targetPortMappings.get(link.getTarget());
		
		data.addLink(source, sink, sourcePort, sinkPort);
	
		return data;
	}
	
	public SynthData handleCompositeSoundComponent(CompositeSoundComponent composite){
		SynthData data = new SynthData();
		for (SoundComponent component : composite.getEmbeddedComponents()) {
			data.addSynthData(handleElement(component));
		}
		for (Delegation delegation: composite.getDelegations()){
			// find outgoing port of source atomic component
			Port sourcePort = delegation.getSource();
			Port targetPort = delegation.getTarget();
			if (sourcePort.getComponent() instanceof CompositeSoundComponent){
				sourcePort = sourcePort.getIncomingConnection().getSource();
			}
			if (targetPort.getComponent() instanceof CompositeSoundComponent){
				for (Connection connection : targetPort.getOutgoingConnection()){
					ComponentData source = null;
					ComponentData sink = null;
					int sourcePortNumber = 0;
					int sinkPortNumber = 0;
					//FIXME: add as parameters the target of the different outgoing edges and the source port
//					data.addLink(source, sink, sourcePortNumber, sinkPortNumber);
				}
			} else {
				ComponentData source = null;
				ComponentData sink = null;
				int sourcePortNumber = 0;
				int sinkPortNumber = 0;
				//FIXME: add as parameters source and target
//				data.addLink(source, sink, sourcePortNumber, sinkPortNumber);
			}
		}
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
	

}