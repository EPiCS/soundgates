package soundgates.diagram.edit.policies;

import org.eclipse.gef.commands.Command;

import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;

import soundgates.diagram.edit.commands.EmptyCommand;
import soundgates.diagram.providers.SoundgatesElementTypes;

/**
 * @generated NOT
 */
public class CompositeSoundComponentComponentCompartmentItemSemanticEditPolicy
		extends SoundgatesBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public CompositeSoundComponentComponentCompartmentItemSemanticEditPolicy() {
		super(SoundgatesElementTypes.CompositeSoundComponent_2002);
	}

	/**
	 * @generated NOT
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return new EmptyCommand();
	}
}
