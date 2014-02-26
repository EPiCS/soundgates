package soundgates.diagram.edit.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

import soundgates.AtomicSoundComponent;
import soundgates.DataType;
import soundgates.Direction;
import soundgates.Port;
import soundgates.diagram.edit.commands.LinkCreateCommand;
import soundgates.diagram.edit.commands.LinkReorientCommand;
import soundgates.diagram.edit.parts.LinkEditPart;
import soundgates.diagram.providers.SoundgatesElementTypes;
import soundgates.impl.PatchImpl;

/**
 * @generated
 */
public class PortItemSemanticEditPolicy extends
		SoundgatesBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public PortItemSemanticEditPolicy() {
		super(SoundgatesElementTypes.Port_3001);
	}

	/**
	 * @generated NOT
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
			return new EmptyCommand();		
	}

	class EmptyCommand extends Command{
		@Override
		public boolean canExecute() {
			return false;
		}
	}
	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		Command command = req.getTarget() == null ? getStartCreateRelationshipCommand(req)
				: getCompleteCreateRelationshipCommand(req);
		return command != null ? command : super
				.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getStartCreateRelationshipCommand(
			CreateRelationshipRequest req) {
		if (SoundgatesElementTypes.Link_4001 == req.getElementType()) {
			return getGEFWrapper(new LinkCreateCommand(req, req.getSource(),
					req.getTarget()));
		}
		return null;
	}

	/**
	 * @generated NOT
	 */
	protected Command getCompleteCreateRelationshipCommand(
			CreateRelationshipRequest req) {

		boolean patch;
		boolean linkAllowed;
		boolean delegationAllowed;

		//             port          component    container
		patch = (req.getContainer().eContainer().eContainer() instanceof PatchImpl);

		EObject sourceContainer = req.getSource().eContainer().eContainer();
		EObject targetContainer = req.getTarget().eContainer().eContainer();
		linkAllowed = (sourceContainer == targetContainer)
				&& ((Port) req.getTarget()).getDirection() == Direction.IN
				&& ((Port) req.getTarget()).getIncomingConnection() == null
				&& (!(((Port) req.getSource()).getDataType() == DataType.SOUND && ((Port) req
						.getTarget()).getDataType() == DataType.CONTROL));

		if (sourceContainer instanceof PatchImpl) {
			delegationAllowed = sourceContainer.eContents().contains(
					targetContainer);
		} else if (targetContainer instanceof PatchImpl) {
			delegationAllowed = targetContainer.eContents().contains(
					sourceContainer);
		} else {
			delegationAllowed = (sourceContainer.eContents().contains(
					targetContainer) || targetContainer.eContents().contains(
					sourceContainer));
		}

		delegationAllowed = delegationAllowed
				&& ((Port) req.getTarget()).getDirection() == ((Port) req
						.getSource()).getDirection()
				&& ((Port) req.getTarget()).getIncomingConnection() == null
				&& (!(((Port) req.getSource()).getDataType() == DataType.SOUND && ((Port) req
						.getTarget()).getDataType() == DataType.CONTROL));

		if (req.getTarget().eContainer() instanceof AtomicSoundComponent) {
			delegationAllowed = delegationAllowed
					&& ((Port) req.getTarget()).getDirection() == Direction.IN;
		}

		if (SoundgatesElementTypes.Link_4001 == req.getElementType() && patch
				&& linkAllowed) {
			return getGEFWrapper(new LinkCreateCommand(req, req.getSource(),
					req.getTarget()));
		}
		return null;
	}

	/**
	 * Returns command to reorient EClass based link. New link target or source
	 * should be the domain model element associated with this node.
	 * 
	 * @generated
	 */
	protected Command getReorientRelationshipCommand(
			ReorientRelationshipRequest req) {
		switch (getVisualID(req)) {
		case LinkEditPart.VISUAL_ID:
			return getGEFWrapper(new LinkReorientCommand(req));
		}
		return super.getReorientRelationshipCommand(req);
	}

}
