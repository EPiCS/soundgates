package soundgatesComposite.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.View;

import soundgatesComposite.CompositeSoundComponent;
import soundgatesComposite.diagram.edit.commands.EmptyCommand;

/**
 * @generated
 */
public class LinkItemSemanticEditPolicy
		extends
		soundgatesComposite.diagram.edit.policies.SoundgatesCompositeBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public LinkItemSemanticEditPolicy() {
		super(
				soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Link_4003);
	}

	/**
	 * @generated NOT
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		View view = (View) getHost().getModel();
		if(view.getElement().eContainer() instanceof CompositeSoundComponent && 
				view.getElement().eContainer().eContainer() instanceof CompositeSoundComponent){
			return new EmptyCommand();
		}
		else{
			return getGEFWrapper(new DestroyElementCommand(req));
		}
	}

}
