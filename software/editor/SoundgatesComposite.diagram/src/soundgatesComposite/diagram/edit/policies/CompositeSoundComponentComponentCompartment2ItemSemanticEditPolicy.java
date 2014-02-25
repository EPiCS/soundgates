package soundgatesComposite.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

/**
 * @generated
 */
public class CompositeSoundComponentComponentCompartment2ItemSemanticEditPolicy
		extends
		soundgatesComposite.diagram.edit.policies.SoundgatesCompositeBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public CompositeSoundComponentComponentCompartment2ItemSemanticEditPolicy() {
		super(
				soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.CompositeSoundComponent_3006);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.AtomicSoundComponent_3005 == req
				.getElementType()) {
			return getGEFWrapper(new soundgatesComposite.diagram.edit.commands.AtomicSoundComponentCreateCommand(
					req));
		}
		if (soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.CompositeSoundComponent_3006 == req
				.getElementType()) {
			return getGEFWrapper(new soundgatesComposite.diagram.edit.commands.CompositeSoundComponent2CreateCommand(
					req));
		}
		return super.getCreateCommand(req);
	}

}
