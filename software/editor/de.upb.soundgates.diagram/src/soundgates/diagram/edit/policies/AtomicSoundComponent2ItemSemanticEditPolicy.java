package soundgates.diagram.edit.policies;

import java.util.Iterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.ICompositeCommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;

import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import soundgates.diagram.edit.commands.EmptyCommand;
import soundgates.diagram.edit.commands.PortCreateCommand;
import soundgates.diagram.edit.parts.DelegationEditPart;
import soundgates.diagram.edit.parts.Link2EditPart;
import soundgates.diagram.edit.parts.LinkEditPart;
import soundgates.diagram.edit.parts.PortEditPart;
import soundgates.diagram.part.SoundgatesVisualIDRegistry;
import soundgates.diagram.providers.SoundgatesElementTypes;

/**
 * @generated
 */
public class AtomicSoundComponent2ItemSemanticEditPolicy extends
		SoundgatesBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public AtomicSoundComponent2ItemSemanticEditPolicy() {
		super(SoundgatesElementTypes.AtomicSoundComponent_3002);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (SoundgatesElementTypes.Port_3001 == req.getElementType()) {
			return getGEFWrapper(new PortCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

	/**
	 * @generated NOT
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return new EmptyCommand();
	}

	/**
	 * @generated NOT
	 */
	private void addDestroyChildNodesCommand(ICompositeCommand cmd) {

	}

}
