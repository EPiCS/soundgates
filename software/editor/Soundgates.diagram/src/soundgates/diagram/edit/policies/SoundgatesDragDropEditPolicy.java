package soundgates.diagram.edit.policies;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy;

import soundgates.diagram.edit.parts.CompositeSoundComponentComponentCompartment2EditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponentComponentCompartmentEditPart;

public class SoundgatesDragDropEditPolicy extends DiagramDragDropEditPolicy{
	@Override
	protected Command getDropCommand(ChangeBoundsRequest request) {	
		if(!canExecute(request.getEditParts())){
			return null;
		}
		else 
			return super.getDropCommand(request);
	}
	
	private boolean canExecute(List<Object> editParts){
		for(Object editPart : editParts){
			if(editPart instanceof EditPart){
				if(((EditPart) editPart).getParent() instanceof CompositeSoundComponentComponentCompartmentEditPart ||
						((EditPart) editPart).getParent() instanceof CompositeSoundComponentComponentCompartment2EditPart){
					return false;
				}
			}
		}
		return true;
	}
}