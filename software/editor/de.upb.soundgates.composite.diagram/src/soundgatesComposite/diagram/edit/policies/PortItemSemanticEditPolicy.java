package soundgatesComposite.diagram.edit.policies;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

import soundgatesComposite.AtomicSoundComponent;
import soundgatesComposite.CompositeSoundComponent;
import soundgatesComposite.DataType;
import soundgatesComposite.Direction;
import soundgatesComposite.Port;
import soundgatesComposite.diagram.edit.commands.DelegationCreateCommand;
import soundgatesComposite.diagram.edit.commands.LinkCreateCommand;
import soundgatesComposite.diagram.edit.parts.DelegationEditPart;
import soundgatesComposite.diagram.edit.parts.LinkEditPart;
import soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry;
import soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes;
import soundgatesComposite.impl.WorkbenchImpl;

/**
 * @generated
 */
public class PortItemSemanticEditPolicy
		extends
		soundgatesComposite.diagram.edit.policies.SoundgatesCompositeBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public PortItemSemanticEditPolicy() {
		super(
				soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Port_3004);
	}

	class EmptyCommand extends Command{
		@Override
		public boolean canExecute() {
			return false;
		}
	}
	
	/**
	 * @generated NOT
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {

		View view = (View) getHost().getModel();
		
		if (view.getElement().eContainer() instanceof AtomicSoundComponent)
			return new EmptyCommand();
		else if(view.getElement().eContainer() instanceof CompositeSoundComponent && 
				view.getElement().eContainer().eContainer() instanceof CompositeSoundComponent)
			return new EmptyCommand();
		else {
			CompositeTransactionalCommand cmd = new CompositeTransactionalCommand(
					getEditingDomain(), null);
			cmd.setTransactionNestingEnabled(false);
			for (Iterator<?> it = view.getTargetEdges().iterator(); it
					.hasNext();) {
				Edge incomingLink = (Edge) it.next();
				
				if (SoundgatesCompositeVisualIDRegistry.getVisualID(incomingLink) == LinkEditPart.VISUAL_ID) {
					DestroyElementRequest r = new DestroyElementRequest(
							incomingLink.getElement(), false);
					cmd.add(new DestroyElementCommand(r));
					cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
					continue;
				}
				if (SoundgatesCompositeVisualIDRegistry.getVisualID(incomingLink) == DelegationEditPart.VISUAL_ID) {
					DestroyElementRequest r = new DestroyElementRequest(
							incomingLink.getElement(), false);
					cmd.add(new DestroyElementCommand(r));
					cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
					continue;
				}
			}
			for (Iterator<?> it = view.getSourceEdges().iterator(); it
					.hasNext();) {
				Edge outgoingLink = (Edge) it.next();
				
				if (SoundgatesCompositeVisualIDRegistry.getVisualID(outgoingLink) == LinkEditPart.VISUAL_ID) {
					DestroyElementRequest r = new DestroyElementRequest(
							outgoingLink.getElement(), false);
					cmd.add(new DestroyElementCommand(r));
					cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
					continue;
				}
				if (SoundgatesCompositeVisualIDRegistry.getVisualID(outgoingLink) == DelegationEditPart.VISUAL_ID) {
					DestroyElementRequest r = new DestroyElementRequest(
							outgoingLink.getElement(), false);
					cmd.add(new DestroyElementCommand(r));
					cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
					continue;
				}
			}
			EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
			if (annotation == null) {
				// there are indirectly referenced children, need extra commands: false
				addDestroyShortcutsCommand(cmd, view);
				// delete host element
				cmd.add(new DestroyElementCommand(req));
			} else {
				cmd.add(new DeleteCommand(getEditingDomain(), view));
			}
			return getGEFWrapper(cmd.reduce());
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
		if (soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Link_4003 == req
				.getElementType()) {
			return getGEFWrapper(new soundgatesComposite.diagram.edit.commands.LinkCreateCommand(
					req, req.getSource(), req.getTarget()));
		}
		if (soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Delegation_4004 == req
				.getElementType()) {
			return getGEFWrapper(new soundgatesComposite.diagram.edit.commands.DelegationCreateCommand(
					req, req.getSource(), req.getTarget()));
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
		patch = (req.getContainer().eContainer().eContainer() instanceof WorkbenchImpl);

		EObject sourceContainer = req.getSource().eContainer().eContainer();
		EObject targetContainer = req.getTarget().eContainer().eContainer();
		linkAllowed = (sourceContainer == targetContainer)
				&& ((Port) req.getTarget()).getDirection() == Direction.IN
				&& ((Port) req.getTarget()).getIncomingConnection() == null
				&& (!(((Port) req.getSource()).getDataType() == DataType.SOUND && ((Port) req
						.getTarget()).getDataType() == DataType.CONTROL));

		if (sourceContainer instanceof WorkbenchImpl) {
			delegationAllowed = sourceContainer.eContents().contains(
					targetContainer);
		} else if (targetContainer instanceof WorkbenchImpl) {
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
		if (SoundgatesCompositeElementTypes.Link_4003 == req.getElementType() && !patch
				&& linkAllowed) {
			return getGEFWrapper(new LinkCreateCommand(req, req.getSource(),
					req.getTarget()));
		}
		if (SoundgatesCompositeElementTypes.Delegation_4004 == req.getElementType()
				&& delegationAllowed) {
			return getGEFWrapper(new DelegationCreateCommand(req,
					req.getSource(), req.getTarget()));
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
		case soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID:
			return getGEFWrapper(new soundgatesComposite.diagram.edit.commands.LinkReorientCommand(
					req));
		case soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID:
			return getGEFWrapper(new soundgatesComposite.diagram.edit.commands.DelegationReorientCommand(
					req));
		}
		return super.getReorientRelationshipCommand(req);
	}

}
