package soundgatesComposite.diagram.edit.policies;

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

import soundgatesComposite.CompositeSoundComponent;
import soundgatesComposite.diagram.edit.commands.EmptyCommand;

/**
 * @generated
 */
public class CompositeSoundComponent2ItemSemanticEditPolicy
		extends
		soundgatesComposite.diagram.edit.policies.SoundgatesCompositeBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public CompositeSoundComponent2ItemSemanticEditPolicy() {
		super(
				soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.CompositeSoundComponent_3006);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Port_3004 == req
				.getElementType()) {
			return getGEFWrapper(new soundgatesComposite.diagram.edit.commands.PortCreateCommand(
					req));
		}
		return super.getCreateCommand(req);
	}

	/**
	 * @generated NOT
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		View view = (View) getHost().getModel();
		
		if(view.getElement().eContainer() instanceof CompositeSoundComponent && 
				view.getElement().eContainer().eContainer() instanceof CompositeSoundComponent)
			return new EmptyCommand();
		else{
			CompositeTransactionalCommand cmd = new CompositeTransactionalCommand(
					getEditingDomain(), null);
			cmd.setTransactionNestingEnabled(false);
			EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
			if (annotation == null) {
				// there are indirectly referenced children, need extra commands: false
				addDestroyChildNodesCommand(cmd);
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
	private void addDestroyChildNodesCommand(ICompositeCommand cmd) {
		View view = (View) getHost().getModel();
		for (Iterator<?> nit = view.getChildren().iterator(); nit.hasNext();) {
			Node node = (Node) nit.next();
			switch (soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getVisualID(node)) {
			case soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID:
				for (Iterator<?> it = node.getTargetEdges().iterator(); it
						.hasNext();) {
					Edge incomingLink = (Edge) it.next();
					if (soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
							.getVisualID(incomingLink) == soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID) {
						DestroyElementRequest r = new DestroyElementRequest(
								incomingLink.getElement(), false);
						cmd.add(new DestroyElementCommand(r));
						cmd.add(new DeleteCommand(getEditingDomain(),
								incomingLink));
						continue;
					}
					if (soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
							.getVisualID(incomingLink) == soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID) {
						DestroyElementRequest r = new DestroyElementRequest(
								incomingLink.getElement(), false);
						cmd.add(new DestroyElementCommand(r));
						cmd.add(new DeleteCommand(getEditingDomain(),
								incomingLink));
						continue;
					}
				}
				for (Iterator<?> it = node.getSourceEdges().iterator(); it
						.hasNext();) {
					Edge outgoingLink = (Edge) it.next();
					if (soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
							.getVisualID(outgoingLink) == soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID) {
						DestroyElementRequest r = new DestroyElementRequest(
								outgoingLink.getElement(), false);
						cmd.add(new DestroyElementCommand(r));
						cmd.add(new DeleteCommand(getEditingDomain(),
								outgoingLink));
						continue;
					}
					if (soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
							.getVisualID(outgoingLink) == soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID) {
						DestroyElementRequest r = new DestroyElementRequest(
								outgoingLink.getElement(), false);
						cmd.add(new DestroyElementCommand(r));
						cmd.add(new DeleteCommand(getEditingDomain(),
								outgoingLink));
						continue;
					}
				}
				cmd.add(new DestroyElementCommand(new DestroyElementRequest(
						getEditingDomain(), node.getElement(), false))); // directlyOwned: true
				// don't need explicit deletion of node as parent's view deletion would clean child views as well 
				// cmd.add(new org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand(getEditingDomain(), node));
				break;
			case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentComponentCompartment2EditPart.VISUAL_ID:
				for (Iterator<?> cit = node.getChildren().iterator(); cit
						.hasNext();) {
					Node cnode = (Node) cit.next();
					switch (soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
							.getVisualID(cnode)) {
					case soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID:
						cmd.add(new DestroyElementCommand(
								new DestroyElementRequest(getEditingDomain(),
										cnode.getElement(), false))); // directlyOwned: true
						// don't need explicit deletion of cnode as parent's view deletion would clean child views as well 
						// cmd.add(new org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand(getEditingDomain(), cnode));
						break;
					case soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID:
						cmd.add(new DestroyElementCommand(
								new DestroyElementRequest(getEditingDomain(),
										cnode.getElement(), false))); // directlyOwned: true
						// don't need explicit deletion of cnode as parent's view deletion would clean child views as well 
						// cmd.add(new org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand(getEditingDomain(), cnode));
						break;
					}
				}
				break;
			}
		}
	}

}
