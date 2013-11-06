package soundgates.diagram.providers;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.tooling.runtime.providers.DiagramElementTypeImages;
import org.eclipse.gmf.tooling.runtime.providers.DiagramElementTypes;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import soundgates.SoundgatesPackage;
import soundgates.diagram.edit.parts.AtomicSoundComponent2EditPart;
import soundgates.diagram.edit.parts.AtomicSoundComponentEditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponent2EditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponentEditPart;
import soundgates.diagram.edit.parts.DelegationEditPart;
import soundgates.diagram.edit.parts.Link2EditPart;
import soundgates.diagram.edit.parts.LinkEditPart;
import soundgates.diagram.edit.parts.PatchEditPart;
import soundgates.diagram.edit.parts.PortEditPart;
import soundgates.diagram.part.SoundgatesDiagramEditorPlugin;

/**
 * @generated
 */
public class SoundgatesElementTypes {

	/**
	 * @generated
	 */
	private SoundgatesElementTypes() {
	}

	/**
	 * @generated
	 */
	private static Map<IElementType, ENamedElement> elements;

	/**
	 * @generated
	 */
	private static DiagramElementTypeImages elementTypeImages = new DiagramElementTypeImages(
			SoundgatesDiagramEditorPlugin.getInstance()
					.getItemProvidersAdapterFactory());

	/**
	 * @generated
	 */
	private static Set<IElementType> KNOWN_ELEMENT_TYPES;

	/**
	 * @generated
	 */
	public static final IElementType Patch_1000 = getElementType("Soundgates.diagram.Patch_1000"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType AtomicSoundComponent_2001 = getElementType("Soundgates.diagram.AtomicSoundComponent_2001"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType CompositeSoundComponent_2002 = getElementType("Soundgates.diagram.CompositeSoundComponent_2002"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Port_3001 = getElementType("Soundgates.diagram.Port_3001"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType AtomicSoundComponent_3002 = getElementType("Soundgates.diagram.AtomicSoundComponent_3002"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType CompositeSoundComponent_3003 = getElementType("Soundgates.diagram.CompositeSoundComponent_3003"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Link_4001 = getElementType("Soundgates.diagram.Link_4001"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Link_4002 = getElementType("Soundgates.diagram.Link_4002"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final IElementType Delegation_4003 = getElementType("Soundgates.diagram.Delegation_4003"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(ENamedElement element) {
		return elementTypeImages.getImageDescriptor(element);
	}

	/**
	 * @generated
	 */
	public static Image getImage(ENamedElement element) {
		return elementTypeImages.getImage(element);
	}

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(IAdaptable hint) {
		return getImageDescriptor(getElement(hint));
	}

	/**
	 * @generated
	 */
	public static Image getImage(IAdaptable hint) {
		return getImage(getElement(hint));
	}

	/**
	 * Returns 'type' of the ecore object associated with the hint.
	 * 
	 * @generated
	 */
	public static ENamedElement getElement(IAdaptable hint) {
		Object type = hint.getAdapter(IElementType.class);
		if (elements == null) {
			elements = new IdentityHashMap<IElementType, ENamedElement>();

			elements.put(Patch_1000, SoundgatesPackage.eINSTANCE.getPatch());

			elements.put(AtomicSoundComponent_2001,
					SoundgatesPackage.eINSTANCE.getAtomicSoundComponent());

			elements.put(CompositeSoundComponent_2002,
					SoundgatesPackage.eINSTANCE.getCompositeSoundComponent());

			elements.put(Port_3001, SoundgatesPackage.eINSTANCE.getPort());

			elements.put(AtomicSoundComponent_3002,
					SoundgatesPackage.eINSTANCE.getAtomicSoundComponent());

			elements.put(CompositeSoundComponent_3003,
					SoundgatesPackage.eINSTANCE.getCompositeSoundComponent());

			elements.put(Link_4001, SoundgatesPackage.eINSTANCE.getLink());

			elements.put(Link_4002, SoundgatesPackage.eINSTANCE.getLink());

			elements.put(Delegation_4003,
					SoundgatesPackage.eINSTANCE.getDelegation());
		}
		return (ENamedElement) elements.get(type);
	}

	/**
	 * @generated
	 */
	private static IElementType getElementType(String id) {
		return ElementTypeRegistry.getInstance().getType(id);
	}

	/**
	 * @generated
	 */
	public static boolean isKnownElementType(IElementType elementType) {
		if (KNOWN_ELEMENT_TYPES == null) {
			KNOWN_ELEMENT_TYPES = new HashSet<IElementType>();
			KNOWN_ELEMENT_TYPES.add(Patch_1000);
			KNOWN_ELEMENT_TYPES.add(AtomicSoundComponent_2001);
			KNOWN_ELEMENT_TYPES.add(CompositeSoundComponent_2002);
			KNOWN_ELEMENT_TYPES.add(Port_3001);
			KNOWN_ELEMENT_TYPES.add(AtomicSoundComponent_3002);
			KNOWN_ELEMENT_TYPES.add(CompositeSoundComponent_3003);
			KNOWN_ELEMENT_TYPES.add(Link_4001);
			KNOWN_ELEMENT_TYPES.add(Link_4002);
			KNOWN_ELEMENT_TYPES.add(Delegation_4003);
		}
		return KNOWN_ELEMENT_TYPES.contains(elementType);
	}

	/**
	 * @generated
	 */
	public static IElementType getElementType(int visualID) {
		switch (visualID) {
		case PatchEditPart.VISUAL_ID:
			return Patch_1000;
		case AtomicSoundComponentEditPart.VISUAL_ID:
			return AtomicSoundComponent_2001;
		case CompositeSoundComponentEditPart.VISUAL_ID:
			return CompositeSoundComponent_2002;
		case PortEditPart.VISUAL_ID:
			return Port_3001;
		case AtomicSoundComponent2EditPart.VISUAL_ID:
			return AtomicSoundComponent_3002;
		case CompositeSoundComponent2EditPart.VISUAL_ID:
			return CompositeSoundComponent_3003;
		case LinkEditPart.VISUAL_ID:
			return Link_4001;
		case Link2EditPart.VISUAL_ID:
			return Link_4002;
		case DelegationEditPart.VISUAL_ID:
			return Delegation_4003;
		}
		return null;
	}

	/**
	 * @generated
	 */
	public static final DiagramElementTypes TYPED_INSTANCE = new DiagramElementTypes(
			elementTypeImages) {

		/**
		 * @generated
		 */
		@Override
		public boolean isKnownElementType(IElementType elementType) {
			return soundgates.diagram.providers.SoundgatesElementTypes
					.isKnownElementType(elementType);
		}

		/**
		 * @generated
		 */
		@Override
		public IElementType getElementTypeForVisualId(int visualID) {
			return soundgates.diagram.providers.SoundgatesElementTypes
					.getElementType(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public ENamedElement getDefiningNamedElement(
				IAdaptable elementTypeAdapter) {
			return soundgates.diagram.providers.SoundgatesElementTypes
					.getElement(elementTypeAdapter);
		}
	};

}
