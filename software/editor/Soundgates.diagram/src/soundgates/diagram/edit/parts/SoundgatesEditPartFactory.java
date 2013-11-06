package soundgates.diagram.edit.parts;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.directedit.locator.CellEditorLocatorAccess;

import soundgates.diagram.part.SoundgatesVisualIDRegistry;

/**
 * @generated
 */
public class SoundgatesEditPartFactory implements EditPartFactory {

	/**
	 * @generated
	 */
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;
			switch (SoundgatesVisualIDRegistry.getVisualID(view)) {

			case PatchEditPart.VISUAL_ID:
				return new PatchEditPart(view);

			case AtomicSoundComponentEditPart.VISUAL_ID:
				return new AtomicSoundComponentEditPart(view);

			case AtomicSoundComponentNameEditPart.VISUAL_ID:
				return new AtomicSoundComponentNameEditPart(view);

			case CompositeSoundComponentEditPart.VISUAL_ID:
				return new CompositeSoundComponentEditPart(view);

			case CompositeSoundComponentNameEditPart.VISUAL_ID:
				return new CompositeSoundComponentNameEditPart(view);

			case PortEditPart.VISUAL_ID:
				return new PortEditPart(view);

			case PortNameEditPart.VISUAL_ID:
				return new PortNameEditPart(view);

			case AtomicSoundComponent2EditPart.VISUAL_ID:
				return new AtomicSoundComponent2EditPart(view);

			case AtomicSoundComponentName2EditPart.VISUAL_ID:
				return new AtomicSoundComponentName2EditPart(view);

			case CompositeSoundComponent2EditPart.VISUAL_ID:
				return new CompositeSoundComponent2EditPart(view);

			case CompositeSoundComponentName2EditPart.VISUAL_ID:
				return new CompositeSoundComponentName2EditPart(view);

			case CompositeSoundComponentComponentCompartmentEditPart.VISUAL_ID:
				return new CompositeSoundComponentComponentCompartmentEditPart(
						view);

			case CompositeSoundComponentComponentCompartment2EditPart.VISUAL_ID:
				return new CompositeSoundComponentComponentCompartment2EditPart(
						view);

			case LinkEditPart.VISUAL_ID:
				return new LinkEditPart(view);

			case Link2EditPart.VISUAL_ID:
				return new Link2EditPart(view);

			case DelegationEditPart.VISUAL_ID:
				return new DelegationEditPart(view);

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
