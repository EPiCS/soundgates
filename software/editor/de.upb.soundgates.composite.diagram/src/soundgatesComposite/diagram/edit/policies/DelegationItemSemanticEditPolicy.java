package soundgatesComposite.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;

import soundgatesComposite.Workbench;
import soundgatesComposite.diagram.edit.commands.EmptyCommand;

/**
 * @generated
 */
public class DelegationItemSemanticEditPolicy
		extends
		soundgatesComposite.diagram.edit.policies.SoundgatesCompositeBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public DelegationItemSemanticEditPolicy() {
		super(
				soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Delegation_4004);
	}

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		if(req.getElementToDestroy().eContainer().eContainer() instanceof Workbench)
			return getGEFWrapper(new DestroyElementCommand(req));
		else 
			return new EmptyCommand();
	}

}
