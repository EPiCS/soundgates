package soundgates.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import soundgates.diagram.edit.commands.AtomicSoundComponent2CreateCommand;
import soundgates.diagram.edit.commands.CompositeSoundComponent2CreateCommand;
import soundgates.diagram.providers.SoundgatesElementTypes;

/**
 * @generated
 */
public class CompositeSoundComponentComponentCompartment2ItemSemanticEditPolicy
		extends SoundgatesBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public CompositeSoundComponentComponentCompartment2ItemSemanticEditPolicy() {
		super(SoundgatesElementTypes.CompositeSoundComponent_3003);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (SoundgatesElementTypes.AtomicSoundComponent_3002 == req
				.getElementType()) {
			return getGEFWrapper(new AtomicSoundComponent2CreateCommand(req));
		}
		if (SoundgatesElementTypes.CompositeSoundComponent_3003 == req
				.getElementType()) {
			return getGEFWrapper(new CompositeSoundComponent2CreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
