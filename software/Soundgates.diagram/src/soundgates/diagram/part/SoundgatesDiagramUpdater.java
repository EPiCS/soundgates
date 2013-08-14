package soundgates.diagram.part;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.update.DiagramUpdater;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Element;
import soundgates.Link;
import soundgates.Patch;
import soundgates.Port;
import soundgates.SoundgatesPackage;
import soundgates.diagram.edit.parts.AtomicSoundComponentEditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponentEditPart;
import soundgates.diagram.edit.parts.LinkEditPart;
import soundgates.diagram.edit.parts.PatchEditPart;
import soundgates.diagram.edit.parts.PortEditPart;
import soundgates.diagram.providers.SoundgatesElementTypes;

/**
 * @generated
 */
public class SoundgatesDiagramUpdater {

	/**
	 * @generated
	 */
	public static List<SoundgatesNodeDescriptor> getSemanticChildren(View view) {
		switch (SoundgatesVisualIDRegistry.getVisualID(view)) {
		case PatchEditPart.VISUAL_ID:
			return getPatch_1000SemanticChildren(view);
		case AtomicSoundComponentEditPart.VISUAL_ID:
			return getAtomicSoundComponent_2001SemanticChildren(view);
		case CompositeSoundComponentEditPart.VISUAL_ID:
			return getCompositeSoundComponent_2002SemanticChildren(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesNodeDescriptor> getPatch_1000SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Patch modelElement = (Patch) view.getElement();
		LinkedList<SoundgatesNodeDescriptor> result = new LinkedList<SoundgatesNodeDescriptor>();
		for (Iterator<?> it = modelElement.getElements().iterator(); it
				.hasNext();) {
			Element childElement = (Element) it.next();
			int visualID = SoundgatesVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == AtomicSoundComponentEditPart.VISUAL_ID) {
				result.add(new SoundgatesNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == CompositeSoundComponentEditPart.VISUAL_ID) {
				result.add(new SoundgatesNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesNodeDescriptor> getAtomicSoundComponent_2001SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		AtomicSoundComponent modelElement = (AtomicSoundComponent) view
				.getElement();
		LinkedList<SoundgatesNodeDescriptor> result = new LinkedList<SoundgatesNodeDescriptor>();
		for (Iterator<?> it = modelElement.getPorts().iterator(); it.hasNext();) {
			Port childElement = (Port) it.next();
			int visualID = SoundgatesVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == PortEditPart.VISUAL_ID) {
				result.add(new SoundgatesNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesNodeDescriptor> getCompositeSoundComponent_2002SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		CompositeSoundComponent modelElement = (CompositeSoundComponent) view
				.getElement();
		LinkedList<SoundgatesNodeDescriptor> result = new LinkedList<SoundgatesNodeDescriptor>();
		for (Iterator<?> it = modelElement.getPorts().iterator(); it.hasNext();) {
			Port childElement = (Port) it.next();
			int visualID = SoundgatesVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == PortEditPart.VISUAL_ID) {
				result.add(new SoundgatesNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getContainedLinks(View view) {
		switch (SoundgatesVisualIDRegistry.getVisualID(view)) {
		case PatchEditPart.VISUAL_ID:
			return getPatch_1000ContainedLinks(view);
		case AtomicSoundComponentEditPart.VISUAL_ID:
			return getAtomicSoundComponent_2001ContainedLinks(view);
		case CompositeSoundComponentEditPart.VISUAL_ID:
			return getCompositeSoundComponent_2002ContainedLinks(view);
		case PortEditPart.VISUAL_ID:
			return getPort_3001ContainedLinks(view);
		case LinkEditPart.VISUAL_ID:
			return getLink_4001ContainedLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getIncomingLinks(View view) {
		switch (SoundgatesVisualIDRegistry.getVisualID(view)) {
		case AtomicSoundComponentEditPart.VISUAL_ID:
			return getAtomicSoundComponent_2001IncomingLinks(view);
		case CompositeSoundComponentEditPart.VISUAL_ID:
			return getCompositeSoundComponent_2002IncomingLinks(view);
		case PortEditPart.VISUAL_ID:
			return getPort_3001IncomingLinks(view);
		case LinkEditPart.VISUAL_ID:
			return getLink_4001IncomingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getOutgoingLinks(View view) {
		switch (SoundgatesVisualIDRegistry.getVisualID(view)) {
		case AtomicSoundComponentEditPart.VISUAL_ID:
			return getAtomicSoundComponent_2001OutgoingLinks(view);
		case CompositeSoundComponentEditPart.VISUAL_ID:
			return getCompositeSoundComponent_2002OutgoingLinks(view);
		case PortEditPart.VISUAL_ID:
			return getPort_3001OutgoingLinks(view);
		case LinkEditPart.VISUAL_ID:
			return getLink_4001OutgoingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getPatch_1000ContainedLinks(
			View view) {
		Patch modelElement = (Patch) view.getElement();
		LinkedList<SoundgatesLinkDescriptor> result = new LinkedList<SoundgatesLinkDescriptor>();
		result.addAll(getContainedTypeModelFacetLinks_Link_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getAtomicSoundComponent_2001ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getCompositeSoundComponent_2002ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getPort_3001ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getLink_4001ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getAtomicSoundComponent_2001IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getCompositeSoundComponent_2002IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getPort_3001IncomingLinks(
			View view) {
		Port modelElement = (Port) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<SoundgatesLinkDescriptor> result = new LinkedList<SoundgatesLinkDescriptor>();
		result.addAll(getIncomingTypeModelFacetLinks_Link_4001(modelElement,
				crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getLink_4001IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getAtomicSoundComponent_2001OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getCompositeSoundComponent_2002OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getPort_3001OutgoingLinks(
			View view) {
		Port modelElement = (Port) view.getElement();
		LinkedList<SoundgatesLinkDescriptor> result = new LinkedList<SoundgatesLinkDescriptor>();
		result.addAll(getOutgoingTypeModelFacetLinks_Link_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getLink_4001OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	private static Collection<SoundgatesLinkDescriptor> getContainedTypeModelFacetLinks_Link_4001(
			Patch container) {
		LinkedList<SoundgatesLinkDescriptor> result = new LinkedList<SoundgatesLinkDescriptor>();
		for (Iterator<?> links = container.getElements().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Link) {
				continue;
			}
			Link link = (Link) linkObject;
			if (LinkEditPart.VISUAL_ID != SoundgatesVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Port dst = link.getTarget();
			Port src = link.getSource();
			result.add(new SoundgatesLinkDescriptor(src, dst, link,
					SoundgatesElementTypes.Link_4001, LinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<SoundgatesLinkDescriptor> getIncomingTypeModelFacetLinks_Link_4001(
			Port target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<SoundgatesLinkDescriptor> result = new LinkedList<SoundgatesLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences
				.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != SoundgatesPackage.eINSTANCE
					.getConnection_Target()
					|| false == setting.getEObject() instanceof Link) {
				continue;
			}
			Link link = (Link) setting.getEObject();
			if (LinkEditPart.VISUAL_ID != SoundgatesVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Port src = link.getSource();
			result.add(new SoundgatesLinkDescriptor(src, target, link,
					SoundgatesElementTypes.Link_4001, LinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<SoundgatesLinkDescriptor> getOutgoingTypeModelFacetLinks_Link_4001(
			Port source) {
		Patch container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof Patch) {
				container = (Patch) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<SoundgatesLinkDescriptor> result = new LinkedList<SoundgatesLinkDescriptor>();
		for (Iterator<?> links = container.getElements().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Link) {
				continue;
			}
			Link link = (Link) linkObject;
			if (LinkEditPart.VISUAL_ID != SoundgatesVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Port dst = link.getTarget();
			Port src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new SoundgatesLinkDescriptor(src, dst, link,
					SoundgatesElementTypes.Link_4001, LinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static final DiagramUpdater TYPED_INSTANCE = new DiagramUpdater() {
		/**
		 * @generated
		 */
		@Override
		public List<SoundgatesNodeDescriptor> getSemanticChildren(View view) {
			return SoundgatesDiagramUpdater.getSemanticChildren(view);
		}

		/**
		 * @generated
		 */
		@Override
		public List<SoundgatesLinkDescriptor> getContainedLinks(View view) {
			return SoundgatesDiagramUpdater.getContainedLinks(view);
		}

		/**
		 * @generated
		 */
		@Override
		public List<SoundgatesLinkDescriptor> getIncomingLinks(View view) {
			return SoundgatesDiagramUpdater.getIncomingLinks(view);
		}

		/**
		 * @generated
		 */
		@Override
		public List<SoundgatesLinkDescriptor> getOutgoingLinks(View view) {
			return SoundgatesDiagramUpdater.getOutgoingLinks(view);
		}
	};

}
