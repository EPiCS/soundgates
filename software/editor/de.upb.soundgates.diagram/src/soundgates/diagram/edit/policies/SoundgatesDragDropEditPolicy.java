package soundgates.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy;

public class SoundgatesDragDropEditPolicy extends DiagramDragDropEditPolicy{
	@Override
	protected Command getDropCommand(ChangeBoundsRequest request) {	
		return null;
	}
}