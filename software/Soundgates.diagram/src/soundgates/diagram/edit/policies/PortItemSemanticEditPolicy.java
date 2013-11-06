package soundgates.diagram.edit.policies;

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
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Direction;
import soundgates.Port;
import soundgates.diagram.edit.commands.DelegationCreateCommand;
import soundgates.diagram.edit.commands.DelegationReorientCommand;
import soundgates.diagram.edit.commands.Link2CreateCommand;
import soundgates.diagram.edit.commands.Link2ReorientCommand;
import soundgates.diagram.edit.commands.LinkCreateCommand;
import soundgates.diagram.edit.commands.LinkReorientCommand;
import soundgates.diagram.edit.parts.DelegationEditPart;
import soundgates.diagram.edit.parts.Link2EditPart;
import soundgates.diagram.edit.parts.LinkEditPart;
import soundgates.diagram.part.SoundgatesVisualIDRegistry;
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
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {		
		
		View view = (View) getHost().getModel();
		
		if (view.getElement().eContainer() instanceof AtomicSoundComponent)
			return null;
		else{
			CompositeTransactionalCommand cmd = new CompositeTransactionalCommand(
					getEditingDomain(), null);
			cmd.setTransactionNestingEnabled(false);
			for (Iterator<?> it = view.getTargetEdges().iterator(); it.hasNext();) {
				Edge incomingLink = (Edge) it.next();
				if (SoundgatesVisualIDRegistry.getVisualID(incomingLink) == LinkEditPart.VISUAL_ID) {
					DestroyElementRequest r = new DestroyElementRequest(
							incomingLink.getElement(), false);
					cmd.add(new DestroyElementCommand(r));
					cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
					continue;
				}
				if (SoundgatesVisualIDRegistry.getVisualID(incomingLink) == Link2EditPart.VISUAL_ID) {
					DestroyElementRequest r = new DestroyElementRequest(
							incomingLink.getElement(), false);
					cmd.add(new DestroyElementCommand(r));
					cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
					continue;
				}
				if (SoundgatesVisualIDRegistry.getVisualID(incomingLink) == DelegationEditPart.VISUAL_ID) {
					DestroyElementRequest r = new DestroyElementRequest(
							incomingLink.getElement(), false);
					cmd.add(new DestroyElementCommand(r));
					cmd.add(new DeleteCommand(getEditingDomain(), incomingLink));
					continue;
				}
			}
			for (Iterator<?> it = view.getSourceEdges().iterator(); it.hasNext();) {
				Edge outgoingLink = (Edge) it.next();
				if (SoundgatesVisualIDRegistry.getVisualID(outgoingLink) == LinkEditPart.VISUAL_ID) {
					DestroyElementRequest r = new DestroyElementRequest(
							outgoingLink.getElement(), false);
					cmd.add(new DestroyElementCommand(r));
					cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
					continue;
				}
				if (SoundgatesVisualIDRegistry.getVisualID(outgoingLink) == Link2EditPart.VISUAL_ID) {
					DestroyElementRequest r = new DestroyElementRequest(
							outgoingLink.getElement(), false);
					cmd.add(new DestroyElementCommand(r));
					cmd.add(new DeleteCommand(getEditingDomain(), outgoingLink));
					continue;
				}
				if (SoundgatesVisualIDRegistry.getVisualID(outgoingLink) == DelegationEditPart.VISUAL_ID) {
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
		if (SoundgatesElementTypes.Link_4001 == req.getElementType()) {
			return getGEFWrapper(new LinkCreateCommand(req, req.getSource(),
					req.getTarget()));
		}
		if (SoundgatesElementTypes.Link_4002 == req.getElementType()) {
			return getGEFWrapper(new Link2CreateCommand(req, req.getSource(),
					req.getTarget()));
		}
		if (SoundgatesElementTypes.Delegation_4003 == req.getElementType()) {
			return getGEFWrapper(new DelegationCreateCommand(req,
					req.getSource(), req.getTarget()));
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
		linkAllowed = (sourceContainer == targetContainer) && ((Port) req.getTarget()).getDirection()==Direction.IN && ((Port) req.getTarget()).getIncomingConnection()==null; 
		if(sourceContainer instanceof PatchImpl){
			delegationAllowed = sourceContainer.eContents().contains(targetContainer);
		}
		else if(targetContainer instanceof PatchImpl){
			delegationAllowed = targetContainer.eContents().contains(sourceContainer);
		}
		else {
			delegationAllowed = (sourceContainer.eContents().contains(targetContainer) || targetContainer.eContents().contains(sourceContainer));
		}
		
		if (req.getTarget().eContainer() instanceof CompositeSoundComponent){		
			delegationAllowed = delegationAllowed && ((Port) req.getTarget()).getDirection()==Direction.OUT && ((Port) req.getTarget()).getIncomingConnection()==null;
		}
		else if (req.getTarget().eContainer() instanceof AtomicSoundComponent){
			delegationAllowed = delegationAllowed && ((Port) req.getTarget()).getDirection()==Direction.IN && ((Port) req.getTarget()).getIncomingConnection()==null;
		}	
		
		if (SoundgatesElementTypes.Link_4001 == req.getElementType() && patch && linkAllowed) {
			return getGEFWrapper(new LinkCreateCommand(req, req.getSource(),
					req.getTarget()));
		}
		if (SoundgatesElementTypes.Link_4002 == req.getElementType() && !patch && linkAllowed) {
			return getGEFWrapper(new Link2CreateCommand(req, req.getSource(),
					req.getTarget()));
		}
		if (SoundgatesElementTypes.Delegation_4003 == req.getElementType() && delegationAllowed) {
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
		case LinkEditPart.VISUAL_ID:
			return getGEFWrapper(new LinkReorientCommand(req));
		case Link2EditPart.VISUAL_ID:
			return getGEFWrapper(new Link2ReorientCommand(req));
		case DelegationEditPart.VISUAL_ID:
			return getGEFWrapper(new DelegationReorientCommand(req));
		}
		return super.getReorientRelationshipCommand(req);
	}

}
