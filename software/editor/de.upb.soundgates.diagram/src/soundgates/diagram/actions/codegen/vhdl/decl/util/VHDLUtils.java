package soundgates.diagram.actions.codegen.vhdl.decl.util;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.statushandlers.StatusManager;

import soundgates.AtomicSoundComponent;
import soundgates.SoundComponent;
import soundgates.diagram.actions.codegen.vhdl.IVHDLLabeledElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLElement;
import soundgates.diagram.actions.codegen.vhdl.statements.VHDLProcessStatement;
import soundgates.diagram.part.SoundgatesDiagramEditorPlugin;
import soundgates.diagram.soundcomponents.AtomicSoundComponentXMLHandler;

public class VHDLUtils {
	
	@SuppressWarnings("unchecked")
	public static <E> E getElementByLabel(List<VHDLElement> elements, String name){
		
		for(VHDLElement element : elements){
			
			if(element instanceof IVHDLLabeledElement){
				
				IVHDLLabeledElement namedelement = (IVHDLLabeledElement) element;
				
				if(namedelement.getLabel().equals(name)){
					
					try{
					
						return (E)namedelement;
					
					}catch(ClassCastException e){
						
						StatusManager.getManager().handle(new Status(IStatus.ERROR, SoundgatesDiagramEditorPlugin.ID, "Bad cast.", e));
					}
				}
			}
		}
		return null;
	}
	
	public static String getImplName(SoundComponent component){
		AtomicSoundComponent atomic = null;
		
		try{
			atomic = (AtomicSoundComponent) component;		
		
		}catch(ClassCastException e){
			StatusManager.getManager().handle(new Status(IStatus.ERROR, SoundgatesDiagramEditorPlugin.ID, 
												"Could not get implementation name of atomic soundcomponent, because the argument is not an atomic sound component.", e), 
												StatusManager.SHOW);
			
			return "???";
		}
		
		return atomic.getStringProperties().get(AtomicSoundComponentXMLHandler.DEVICE_PREFIX_IMPLNAME);
		
		
	}
	
	public static <E> E createFromString(String template){
		
		
		
		return null;
	}
	
}
