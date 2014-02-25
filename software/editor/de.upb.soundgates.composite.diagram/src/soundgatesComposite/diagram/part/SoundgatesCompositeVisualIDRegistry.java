package soundgatesComposite.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.structure.DiagramStructure;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented
 * by a domain model object.
 * 
 * @generated
 */
public class SoundgatesCompositeVisualIDRegistry {

	/**
	 * @generated
	 */
	private static final String DEBUG_KEY = "SoundgatesComposite.diagram/debug/visualID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static int getVisualID(View view) {
		if (view instanceof Diagram) {
			if (soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.MODEL_ID
					.equals(view.getType())) {
				return soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		return soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
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
				soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin
						.getInstance().logError(
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
		if (soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
				.getWorkbench().isSuperTypeOf(domainElement.eClass())
				&& isDiagram((soundgatesComposite.Workbench) domainElement)) {
			return soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.VISUAL_ID;
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
		String containerModelID = soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
				.getModelID(containerView);
		if (!soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.MODEL_ID
				.equals(containerModelID)) {
			return -1;
		}
		int containerVisualID;
		if (soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.MODEL_ID
				.equals(containerModelID)) {
			containerVisualID = soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		switch (containerVisualID) {
		case soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.VISUAL_ID:
			if (soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
					.getCompositeSoundComponent().isSuperTypeOf(
							domainElement.eClass())) {
				return soundgatesComposite.diagram.edit.parts.CompositeSoundComponentEditPart.VISUAL_ID;
			}
			break;
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentEditPart.VISUAL_ID:
			if (soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
					.getPort().isSuperTypeOf(domainElement.eClass())) {
				return soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID;
			}
			break;
		case soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID:
			if (soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
					.getPort().isSuperTypeOf(domainElement.eClass())) {
				return soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID;
			}
			break;
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID:
			if (soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
					.getPort().isSuperTypeOf(domainElement.eClass())) {
				return soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID;
			}
			break;
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentComponentCompartmentEditPart.VISUAL_ID:
			if (soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
					.getAtomicSoundComponent().isSuperTypeOf(
							domainElement.eClass())) {
				return soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID;
			}
			if (soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
					.getCompositeSoundComponent().isSuperTypeOf(
							domainElement.eClass())) {
				return soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID;
			}
			break;
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentComponentCompartment2EditPart.VISUAL_ID:
			if (soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
					.getAtomicSoundComponent().isSuperTypeOf(
							domainElement.eClass())) {
				return soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID;
			}
			if (soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
					.getCompositeSoundComponent().isSuperTypeOf(
							domainElement.eClass())) {
				return soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID;
			}
			break;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static boolean canCreateNode(View containerView, int nodeVisualID) {
		String containerModelID = soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
				.getModelID(containerView);
		if (!soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.MODEL_ID
				.equals(containerModelID)) {
			return false;
		}
		int containerVisualID;
		if (soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.MODEL_ID
				.equals(containerModelID)) {
			containerVisualID = soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		switch (containerVisualID) {
		case soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.VISUAL_ID:
			if (soundgatesComposite.diagram.edit.parts.CompositeSoundComponentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentEditPart.VISUAL_ID:
			if (soundgatesComposite.diagram.edit.parts.CompositeSoundComponentNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (soundgatesComposite.diagram.edit.parts.CompositeSoundComponentComponentCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID:
			if (soundgatesComposite.diagram.edit.parts.PortNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID:
			if (soundgatesComposite.diagram.edit.parts.AtomicSoundComponentNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID:
			if (soundgatesComposite.diagram.edit.parts.CompositeSoundComponentName2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (soundgatesComposite.diagram.edit.parts.CompositeSoundComponentComponentCompartment2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentComponentCompartmentEditPart.VISUAL_ID:
			if (soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentComponentCompartment2EditPart.VISUAL_ID:
			if (soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID == nodeVisualID) {
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
		if (soundgatesComposite.SoundgatesCompositePackage.eINSTANCE.getLink()
				.isSuperTypeOf(domainElement.eClass())
				&& isLink_4003((soundgatesComposite.Link) domainElement)) {
			return soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID;
		}
		if (soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
				.getDelegation().isSuperTypeOf(domainElement.eClass())
				&& isDelegation_4004((soundgatesComposite.Delegation) domainElement)) {
			return soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 * 
	 * @generated
	 */
	private static boolean isDiagram(soundgatesComposite.Workbench element) {
		return true;
	}

	/**
	 * @generated
	 */
	private static boolean isLink_4003(soundgatesComposite.Link domainElement) {
		return (domainElement.eContainingFeature().getName().equals("links"));
	}

	/**
	 * @generated
	 */
	private static boolean isDelegation_4004(
			soundgatesComposite.Delegation domainElement) {
	return domainElement.eContainingFeature().getName()
				.equals("delegations");	
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
		switch (visualID) {
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentComponentCompartmentEditPart.VISUAL_ID:
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentComponentCompartment2EditPart.VISUAL_ID:
			return true;
		default:
			break;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static boolean isSemanticLeafVisualID(int visualID) {
		switch (visualID) {
		case soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.VISUAL_ID:
			return false;
		case soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID:
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
			return soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getVisualID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public String getModelID(View view) {
			return soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getModelID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public int getNodeVisualID(View containerView, EObject domainElement) {
			return soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getNodeVisualID(containerView, domainElement);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean checkNodeVisualID(View containerView,
				EObject domainElement, int candidate) {
			return soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.checkNodeVisualID(containerView, domainElement, candidate);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isCompartmentVisualID(int visualID) {
			return soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.isCompartmentVisualID(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isSemanticLeafVisualID(int visualID) {
			return soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.isSemanticLeafVisualID(visualID);
		}
	};

}
