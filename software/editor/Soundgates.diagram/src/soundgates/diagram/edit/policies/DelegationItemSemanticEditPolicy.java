package soundgates.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;

import soundgates.diagram.providers.SoundgatesElementTypes;

/**
 * @generated
 */
public class DelegationItemSemanticEditPolicy extends
		SoundgatesBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public DelegationItemSemanticEditPolicy() {
		super(SoundgatesElementTypes.Delegation_4003);
	}

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return getGEFWrapper(new DestroyElementCommand(req));
	}

}
