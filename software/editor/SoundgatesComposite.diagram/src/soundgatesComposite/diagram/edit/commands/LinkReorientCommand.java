package soundgatesComposite.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;

/**
 * @generated
 */
public class LinkReorientCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	private final int reorientDirection;

	/**
	 * @generated
	 */
	private final EObject oldEnd;

	/**
	 * @generated
	 */
	private final EObject newEnd;

	/**
	 * @generated
	 */
	public LinkReorientCommand(ReorientRelationshipRequest request) {
		super(request.getLabel(), request.getRelationship(), request);
		reorientDirection = request.getDirection();
		oldEnd = request.getOldRelationshipEnd();
		newEnd = request.getNewRelationshipEnd();
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		if (false == getElementToEdit() instanceof soundgatesComposite.Link) {
			return false;
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) {
			return canReorientSource();
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET) {
			return canReorientTarget();
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean canReorientSource() {
		if (!(oldEnd instanceof soundgatesComposite.Port && newEnd instanceof soundgatesComposite.Port)) {
			return false;
		}
		soundgatesComposite.Port target = getLink().getTarget();
		if (!(getLink().eContainer() instanceof soundgatesComposite.CompositeSoundComponent)) {
			return false;
		}
		soundgatesComposite.CompositeSoundComponent container = (soundgatesComposite.CompositeSoundComponent) getLink()
				.eContainer();
		return soundgatesComposite.diagram.edit.policies.SoundgatesCompositeBaseItemSemanticEditPolicy
				.getLinkConstraints().canExistLink_4003(container, getLink(),
						getNewSource(), target);
	}

	/**
	 * @generated
	 */
	protected boolean canReorientTarget() {
		if (!(oldEnd instanceof soundgatesComposite.Port && newEnd instanceof soundgatesComposite.Port)) {
			return false;
		}
		soundgatesComposite.Port source = getLink().getSource();
		if (!(getLink().eContainer() instanceof soundgatesComposite.CompositeSoundComponent)) {
			return false;
		}
		soundgatesComposite.CompositeSoundComponent container = (soundgatesComposite.CompositeSoundComponent) getLink()
				.eContainer();
		return soundgatesComposite.diagram.edit.policies.SoundgatesCompositeBaseItemSemanticEditPolicy
				.getLinkConstraints().canExistLink_4003(container, getLink(),
						source, getNewTarget());
	}

	/**
	 * @generated
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		if (!canExecute()) {
			throw new ExecutionException(
					"Invalid arguments in reorient link command"); //$NON-NLS-1$
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_SOURCE) {
			return reorientSource();
		}
		if (reorientDirection == ReorientRelationshipRequest.REORIENT_TARGET) {
			return reorientTarget();
		}
		throw new IllegalStateException();
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientSource() throws ExecutionException {
		getLink().setSource(getNewSource());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected CommandResult reorientTarget() throws ExecutionException {
		getLink().setTarget(getNewTarget());
		return CommandResult.newOKCommandResult(getLink());
	}

	/**
	 * @generated
	 */
	protected soundgatesComposite.Link getLink() {
		return (soundgatesComposite.Link) getElementToEdit();
	}

	/**
	 * @generated
	 */
	protected soundgatesComposite.Port getOldSource() {
		return (soundgatesComposite.Port) oldEnd;
	}

	/**
	 * @generated
	 */
	protected soundgatesComposite.Port getNewSource() {
		return (soundgatesComposite.Port) newEnd;
	}

	/**
	 * @generated
	 */
	protected soundgatesComposite.Port getOldTarget() {
		return (soundgatesComposite.Port) oldEnd;
	}

	/**
	 * @generated
	 */
	protected soundgatesComposite.Port getNewTarget() {
		return (soundgatesComposite.Port) newEnd;
	}
}
