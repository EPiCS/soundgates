package soundgates.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.structure.DiagramStructure;

import soundgates.Patch;
import soundgates.SoundgatesPackage;
import soundgates.diagram.edit.parts.AtomicSoundComponentEditPart;
import soundgates.diagram.edit.parts.AtomicSoundComponentNameEditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponentEditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponentNameEditPart;
import soundgates.diagram.edit.parts.LinkEditPart;
import soundgates.diagram.edit.parts.PatchEditPart;
import soundgates.diagram.edit.parts.PortEditPart;
import soundgates.diagram.edit.parts.PortNameEditPart;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented
 * by a domain model object.
 * 
 * @generated
 */
public class SoundgatesVisualIDRegistry {

	/**
	 * @generated
	 */
	private static final String DEBUG_KEY = "Soundgates.diagram/debug/visualID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static int getVisualID(View view) {
		if (view instanceof Diagram) {
			if (PatchEditPart.MODEL_ID.equals(view.getType())) {
				return PatchEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		return soundgates.diagram.part.SoundgatesVisualIDRegistry
				.getVisualID(view.getType());
	}

	/**
	 * @generated
	 */
	public static String getModelID(View view) {
		View diagram = view.getDiagram();
		while (view != diagram) {
			EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
			if (annotation != null) {
				return (String) annotation.getDetails().get("modelID"); //$NON-NLS-1$
			}
			view = (View) view.eContainer();
		}
		return diagram != null ? diagram.getType() : null;
	}

	/**
	 * @generated
	 */
	public static int getVisualID(String type) {
		try {
			return Integer.parseInt(type);
		} catch (NumberFormatException e) {
			if (Boolean.TRUE.toString().equalsIgnoreCase(
					Platform.getDebugOption(DEBUG_KEY))) {
				SoundgatesDiagramEditorPlugin.getInstance().logError(
						"Unable to parse view type as a visualID number: "
								+ type);
			}
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static String getType(int visualID) {
		return Integer.toString(visualID);
	}

	/**
	 * @generated
	 */
	public static int getDiagramVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		if (SoundgatesPackage.eINSTANCE.getPatch().isSuperTypeOf(
				domainElement.eClass())
				&& isDiagram((Patch) domainElement)) {
			return PatchEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static int getNodeVisualID(View containerView, EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		String containerModelID = soundgates.diagram.part.SoundgatesVisualIDRegistry
				.getModelID(containerView);
		if (!PatchEditPart.MODEL_ID.equals(containerModelID)) {
			return -1;
		}
		int containerVisualID;
		if (PatchEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = soundgates.diagram.part.SoundgatesVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = PatchEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		switch (containerVisualID) {
		case PatchEditPart.VISUAL_ID:
			if (SoundgatesPackage.eINSTANCE.getAtomicSoundComponent()
					.isSuperTypeOf(domainElement.eClass())) {
				return AtomicSoundComponentEditPart.VISUAL_ID;
			}
			if (SoundgatesPackage.eINSTANCE.getCompositeSoundComponent()
					.isSuperTypeOf(domainElement.eClass())) {
				return CompositeSoundComponentEditPart.VISUAL_ID;
			}
			break;
		case AtomicSoundComponentEditPart.VISUAL_ID:
			if (SoundgatesPackage.eINSTANCE.getPort().isSuperTypeOf(
					domainElement.eClass())) {
				return PortEditPart.VISUAL_ID;
			}
			break;
		case CompositeSoundComponentEditPart.VISUAL_ID:
			if (SoundgatesPackage.eINSTANCE.getPort().isSuperTypeOf(
					domainElement.eClass())) {
				return PortEditPart.VISUAL_ID;
			}
			break;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static boolean canCreateNode(View containerView, int nodeVisualID) {
		String containerModelID = soundgates.diagram.part.SoundgatesVisualIDRegistry
				.getModelID(containerView);
		if (!PatchEditPart.MODEL_ID.equals(containerModelID)) {
			return false;
		}
		int containerVisualID;
		if (PatchEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = soundgates.diagram.part.SoundgatesVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = PatchEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		switch (containerVisualID) {
		case PatchEditPart.VISUAL_ID:
			if (AtomicSoundComponentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (CompositeSoundComponentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case AtomicSoundComponentEditPart.VISUAL_ID:
			if (AtomicSoundComponentNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (PortEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case CompositeSoundComponentEditPart.VISUAL_ID:
			if (CompositeSoundComponentNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (PortEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case PortEditPart.VISUAL_ID:
			if (PortNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static int getLinkWithClassVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		if (SoundgatesPackage.eINSTANCE.getLink().isSuperTypeOf(
				domainElement.eClass())) {
			return LinkEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 * 
	 * @generated
	 */
	private static boolean isDiagram(Patch element) {
		return true;
	}

	/**
	 * @generated
	 */
	public static boolean checkNodeVisualID(View containerView,
			EObject domainElement, int candidate) {
		if (candidate == -1) {
			//unrecognized id is always bad
			return false;
		}
		int basic = getNodeVisualID(containerView, domainElement);
		return basic == candidate;
	}

	/**
	 * @generated
	 */
	public static boolean isCompartmentVisualID(int visualID) {
		return false;
	}

	/**
	 * @generated
	 */
	public static boolean isSemanticLeafVisualID(int visualID) {
		switch (visualID) {
		case PatchEditPart.VISUAL_ID:
			return false;
		case PortEditPart.VISUAL_ID:
			return true;
		default:
			break;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static final DiagramStructure TYPED_INSTANCE = new DiagramStructure() {
		/**
		 * @generated
		 */
		@Override
		public int getVisualID(View view) {
			return soundgates.diagram.part.SoundgatesVisualIDRegistry
					.getVisualID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public String getModelID(View view) {
			return soundgates.diagram.part.SoundgatesVisualIDRegistry
					.getModelID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public int getNodeVisualID(View containerView, EObject domainElement) {
			return soundgates.diagram.part.SoundgatesVisualIDRegistry
					.getNodeVisualID(containerView, domainElement);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean checkNodeVisualID(View containerView,
				EObject domainElement, int candidate) {
			return soundgates.diagram.part.SoundgatesVisualIDRegistry
					.checkNodeVisualID(containerView, domainElement, candidate);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isCompartmentVisualID(int visualID) {
			return soundgates.diagram.part.SoundgatesVisualIDRegistry
					.isCompartmentVisualID(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isSemanticLeafVisualID(int visualID) {
			return soundgates.diagram.part.SoundgatesVisualIDRegistry
					.isSemanticLeafVisualID(visualID);
		}
	};

}
