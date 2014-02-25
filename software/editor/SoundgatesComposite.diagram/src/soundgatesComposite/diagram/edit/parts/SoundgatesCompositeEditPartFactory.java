package soundgatesComposite.diagram.edit.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.directedit.locator.CellEditorLocatorAccess;

/**
 * @generated
 */
public class SoundgatesCompositeEditPartFactory implements EditPartFactory {

	/**
	 * @generated
	 */
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;
			switch (soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getVisualID(view)) {

			case soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.VISUAL_ID:
				return new soundgatesComposite.diagram.edit.parts.WorkbenchEditPart(
						view);

			case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentEditPart.VISUAL_ID:
				return new soundgatesComposite.diagram.edit.parts.CompositeSoundComponentEditPart(
						view);

			case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentNameEditPart.VISUAL_ID:
				return new soundgatesComposite.diagram.edit.parts.CompositeSoundComponentNameEditPart(
						view);

			case soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID:
				return new soundgatesComposite.diagram.edit.parts.PortEditPart(
						view);

			case soundgatesComposite.diagram.edit.parts.PortNameEditPart.VISUAL_ID:
				return new soundgatesComposite.diagram.edit.parts.PortNameEditPart(
						view);

			case soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID:
				return new soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart(
						view);

			case soundgatesComposite.diagram.edit.parts.AtomicSoundComponentNameEditPart.VISUAL_ID:
				return new soundgatesComposite.diagram.edit.parts.AtomicSoundComponentNameEditPart(
						view);

			case soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID:
				return new soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart(
						view);

			case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentName2EditPart.VISUAL_ID:
				return new soundgatesComposite.diagram.edit.parts.CompositeSoundComponentName2EditPart(
						view);

			case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentComponentCompartmentEditPart.VISUAL_ID:
				return new soundgatesComposite.diagram.edit.parts.CompositeSoundComponentComponentCompartmentEditPart(
						view);

			case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentComponentCompartment2EditPart.VISUAL_ID:
				return new soundgatesComposite.diagram.edit.parts.CompositeSoundComponentComponentCompartment2EditPart(
						view);

			case soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID:
				return new soundgatesComposite.diagram.edit.parts.LinkEditPart(
						view);

			case soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID:
				return new soundgatesComposite.diagram.edit.parts.DelegationEditPart(
						view);

			}
		}
		return createUnrecognizedEditPart(context, model);
	}

	/**
	 * @generated
	 */
	private EditPart createUnrecognizedEditPart(EditPart context, Object model) {
		// Handle creation of unrecognized child node EditParts here
		return null;
	}

	/**
	 * @generated
	 */
	public static CellEditorLocator getTextCellEditorLocator(
			ITextAwareEditPart source) {
		return CellEditorLocatorAccess.INSTANCE
				.getTextCellEditorLocator(source);
	}

}
