package soundgates.diagram.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;

import soundgates.diagram.providers.SoundgatesElementTypes;

/**
 * @generated
 */
public class Link2ItemSemanticEditPolicy extends
		SoundgatesBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public Link2ItemSemanticEditPolicy() {
		super(SoundgatesElementTypes.Link_4002);
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

}
