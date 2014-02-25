package soundgatesComposite.diagram.providers;

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

/**
 * @generated
 */
public class SoundgatesCompositeElementTypes {

	/**
	 * @generated
	 */
	private SoundgatesCompositeElementTypes() {
	}

	/**
	 * @generated
	 */
	private static Map<IElementType, ENamedElement> elements;

	/**
	 * @generated
	 */
	private static DiagramElementTypeImages elementTypeImages = new DiagramElementTypeImages(
			soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin
					.getInstance().getItemProvidersAdapterFactory());

	/**
	 * @generated
	 */
	private static Set<IElementType> KNOWN_ELEMENT_TYPES;

	/**
	 * @generated
	 */
	public static final IElementType Workbench_1000 = getElementType("SoundgatesComposite.diagram.Workbench_1000"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType CompositeSoundComponent_2002 = getElementType("SoundgatesComposite.diagram.CompositeSoundComponent_2002"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Port_3004 = getElementType("SoundgatesComposite.diagram.Port_3004"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType AtomicSoundComponent_3005 = getElementType("SoundgatesComposite.diagram.AtomicSoundComponent_3005"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType CompositeSoundComponent_3006 = getElementType("SoundgatesComposite.diagram.CompositeSoundComponent_3006"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Link_4003 = getElementType("SoundgatesComposite.diagram.Link_4003"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Delegation_4004 = getElementType("SoundgatesComposite.diagram.Delegation_4004"); //$NON-NLS-1$

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

			elements.put(Workbench_1000,
					soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
							.getWorkbench());

			elements.put(CompositeSoundComponent_2002,
					soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
							.getCompositeSoundComponent());

			elements.put(Port_3004,
					soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
							.getPort());

			elements.put(AtomicSoundComponent_3005,
					soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
							.getAtomicSoundComponent());

			elements.put(CompositeSoundComponent_3006,
					soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
							.getCompositeSoundComponent());

			elements.put(Link_4003,
					soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
							.getLink());

			elements.put(Delegation_4004,
					soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
							.getDelegation());
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
			KNOWN_ELEMENT_TYPES.add(Workbench_1000);
			KNOWN_ELEMENT_TYPES.add(CompositeSoundComponent_2002);
			KNOWN_ELEMENT_TYPES.add(Port_3004);
			KNOWN_ELEMENT_TYPES.add(AtomicSoundComponent_3005);
			KNOWN_ELEMENT_TYPES.add(CompositeSoundComponent_3006);
			KNOWN_ELEMENT_TYPES.add(Link_4003);
			KNOWN_ELEMENT_TYPES.add(Delegation_4004);
		}
		return KNOWN_ELEMENT_TYPES.contains(elementType);
	}

	/**
	 * @generated
	 */
	public static IElementType getElementType(int visualID) {
		switch (visualID) {
		case soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.VISUAL_ID:
			return Workbench_1000;
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentEditPart.VISUAL_ID:
			return CompositeSoundComponent_2002;
		case soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID:
			return Port_3004;
		case soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID:
			return AtomicSoundComponent_3005;
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID:
			return CompositeSoundComponent_3006;
		case soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID:
			return Link_4003;
		case soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID:
			return Delegation_4004;
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
			return soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes
					.isKnownElementType(elementType);
		}

		/**
		 * @generated
		 */
		@Override
		public IElementType getElementTypeForVisualId(int visualID) {
			return soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes
					.getElementType(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public ENamedElement getDefiningNamedElement(
				IAdaptable elementTypeAdapter) {
			return soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes
					.getElement(elementTypeAdapter);
		}
	};

}
