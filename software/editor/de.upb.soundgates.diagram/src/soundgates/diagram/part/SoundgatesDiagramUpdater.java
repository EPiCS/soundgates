package soundgates.diagram.part;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.update.DiagramUpdater;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Delegation;
import soundgates.Element;
import soundgates.Link;
import soundgates.Patch;
import soundgates.Port;
import soundgates.SoundComponent;
import soundgates.SoundgatesPackage;
import soundgates.diagram.edit.parts.AtomicSoundComponent2EditPart;
import soundgates.diagram.edit.parts.AtomicSoundComponentEditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponent2EditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponentComponentCompartment2EditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponentComponentCompartmentEditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponentEditPart;
import soundgates.diagram.edit.parts.DelegationEditPart;
import soundgates.diagram.edit.parts.Link2EditPart;
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
		case AtomicSoundComponent2EditPart.VISUAL_ID:
			return getAtomicSoundComponent_3002SemanticChildren(view);
		case CompositeSoundComponent2EditPart.VISUAL_ID:
			return getCompositeSoundComponent_3003SemanticChildren(view);
		case CompositeSoundComponentComponentCompartmentEditPart.VISUAL_ID:
			return getCompositeSoundComponentComponentCompartment_7001SemanticChildren(view);
		case CompositeSoundComponentComponentCompartment2EditPart.VISUAL_ID:
			return getCompositeSoundComponentComponentCompartment_7002SemanticChildren(view);
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
	public static List<SoundgatesNodeDescriptor> getAtomicSoundComponent_3002SemanticChildren(
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
	public static List<SoundgatesNodeDescriptor> getCompositeSoundComponent_3003SemanticChildren(
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
	public static List<SoundgatesNodeDescriptor> getCompositeSoundComponentComponentCompartment_7001SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		CompositeSoundComponent modelElement = (CompositeSoundComponent) containerView
				.getElement();
		LinkedList<SoundgatesNodeDescriptor> result = new LinkedList<SoundgatesNodeDescriptor>();
		for (Iterator<?> it = modelElement.getEmbeddedComponents().iterator(); it
				.hasNext();) {
			SoundComponent childElement = (SoundComponent) it.next();
			int visualID = SoundgatesVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == AtomicSoundComponent2EditPart.VISUAL_ID) {
				result.add(new SoundgatesNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == CompositeSoundComponent2EditPart.VISUAL_ID) {
				result.add(new SoundgatesNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesNodeDescriptor> getCompositeSoundComponentComponentCompartment_7002SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		CompositeSoundComponent modelElement = (CompositeSoundComponent) containerView
				.getElement();
		LinkedList<SoundgatesNodeDescriptor> result = new LinkedList<SoundgatesNodeDescriptor>();
		for (Iterator<?> it = modelElement.getEmbeddedComponents().iterator(); it
				.hasNext();) {
			SoundComponent childElement = (SoundComponent) it.next();
			int visualID = SoundgatesVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == AtomicSoundComponent2EditPart.VISUAL_ID) {
				result.add(new SoundgatesNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == CompositeSoundComponent2EditPart.VISUAL_ID) {
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
		case AtomicSoundComponent2EditPart.VISUAL_ID:
			return getAtomicSoundComponent_3002ContainedLinks(view);
		case CompositeSoundComponent2EditPart.VISUAL_ID:
			return getCompositeSoundComponent_3003ContainedLinks(view);
		case LinkEditPart.VISUAL_ID:
			return getLink_4001ContainedLinks(view);
		case Link2EditPart.VISUAL_ID:
			return getLink_4002ContainedLinks(view);
		case DelegationEditPart.VISUAL_ID:
			return getDelegation_4003ContainedLinks(view);
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
		case AtomicSoundComponent2EditPart.VISUAL_ID:
			return getAtomicSoundComponent_3002IncomingLinks(view);
		case CompositeSoundComponent2EditPart.VISUAL_ID:
			return getCompositeSoundComponent_3003IncomingLinks(view);
		case LinkEditPart.VISUAL_ID:
			return getLink_4001IncomingLinks(view);
		case Link2EditPart.VISUAL_ID:
			return getLink_4002IncomingLinks(view);
		case DelegationEditPart.VISUAL_ID:
			return getDelegation_4003IncomingLinks(view);
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
		case AtomicSoundComponent2EditPart.VISUAL_ID:
			return getAtomicSoundComponent_3002OutgoingLinks(view);
		case CompositeSoundComponent2EditPart.VISUAL_ID:
			return getCompositeSoundComponent_3003OutgoingLinks(view);
		case LinkEditPart.VISUAL_ID:
			return getLink_4001OutgoingLinks(view);
		case Link2EditPart.VISUAL_ID:
			return getLink_4002OutgoingLinks(view);
		case DelegationEditPart.VISUAL_ID:
			return getDelegation_4003OutgoingLinks(view);
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
		CompositeSoundComponent modelElement = (CompositeSoundComponent) view
				.getElement();
		LinkedList<SoundgatesLinkDescriptor> result = new LinkedList<SoundgatesLinkDescriptor>();
		result.addAll(getContainedTypeModelFacetLinks_Link_4002(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Delegation_4003(modelElement));
		return result;
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
	public static List<SoundgatesLinkDescriptor> getAtomicSoundComponent_3002ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getCompositeSoundComponent_3003ContainedLinks(
			View view) {
		CompositeSoundComponent modelElement = (CompositeSoundComponent) view
				.getElement();
		LinkedList<SoundgatesLinkDescriptor> result = new LinkedList<SoundgatesLinkDescriptor>();
		result.addAll(getContainedTypeModelFacetLinks_Link_4002(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Delegation_4003(modelElement));
		return result;
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
	public static List<SoundgatesLinkDescriptor> getLink_4002ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getDelegation_4003ContainedLinks(
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
		result.addAll(getIncomingTypeModelFacetLinks_Link_4002(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_Delegation_4003(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getAtomicSoundComponent_3002IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getCompositeSoundComponent_3003IncomingLinks(
			View view) {
		return Collections.emptyList();
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
	public static List<SoundgatesLinkDescriptor> getLink_4002IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getDelegation_4003IncomingLinks(
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
		result.addAll(getOutgoingTypeModelFacetLinks_Link_4002(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Delegation_4003(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getAtomicSoundComponent_3002OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getCompositeSoundComponent_3003OutgoingLinks(
			View view) {
		return Collections.emptyList();
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
	public static List<SoundgatesLinkDescriptor> getLink_4002OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<SoundgatesLinkDescriptor> getDelegation_4003OutgoingLinks(
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
	private static Collection<SoundgatesLinkDescriptor> getContainedTypeModelFacetLinks_Link_4002(
			CompositeSoundComponent container) {
		LinkedList<SoundgatesLinkDescriptor> result = new LinkedList<SoundgatesLinkDescriptor>();
		for (Iterator<?> links = container.getLinks().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Link) {
				continue;
			}
			Link link = (Link) linkObject;
			if (Link2EditPart.VISUAL_ID != SoundgatesVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Port dst = link.getTarget();
			Port src = link.getSource();
			result.add(new SoundgatesLinkDescriptor(src, dst, link,
					SoundgatesElementTypes.Link_4002, Link2EditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<SoundgatesLinkDescriptor> getContainedTypeModelFacetLinks_Delegation_4003(
			CompositeSoundComponent container) {
		LinkedList<SoundgatesLinkDescriptor> result = new LinkedList<SoundgatesLinkDescriptor>();
		for (Iterator<?> links = container.getDelegations().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Delegation) {
				continue;
			}
			Delegation link = (Delegation) linkObject;
			if (DelegationEditPart.VISUAL_ID != SoundgatesVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Port dst = link.getTarget();
			Port src = link.getSource();
			result.add(new SoundgatesLinkDescriptor(src, dst, link,
					SoundgatesElementTypes.Delegation_4003,
					DelegationEditPart.VISUAL_ID));
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
	private static Collection<SoundgatesLinkDescriptor> getIncomingTypeModelFacetLinks_Link_4002(
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
			if (Link2EditPart.VISUAL_ID != SoundgatesVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Port src = link.getSource();
			result.add(new SoundgatesLinkDescriptor(src, target, link,
					SoundgatesElementTypes.Link_4002, Link2EditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<SoundgatesLinkDescriptor> getIncomingTypeModelFacetLinks_Delegation_4003(
			Port target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<SoundgatesLinkDescriptor> result = new LinkedList<SoundgatesLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences
				.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != SoundgatesPackage.eINSTANCE
					.getConnection_Target()
					|| false == setting.getEObject() instanceof Delegation) {
				continue;
			}
			Delegation link = (Delegation) setting.getEObject();
			if (DelegationEditPart.VISUAL_ID != SoundgatesVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Port src = link.getSource();
			result.add(new SoundgatesLinkDescriptor(src, target, link,
					SoundgatesElementTypes.Delegation_4003,
					DelegationEditPart.VISUAL_ID));
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
	private static Collection<SoundgatesLinkDescriptor> getOutgoingTypeModelFacetLinks_Link_4002(
			Port source) {
		CompositeSoundComponent container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof CompositeSoundComponent) {
				container = (CompositeSoundComponent) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<SoundgatesLinkDescriptor> result = new LinkedList<SoundgatesLinkDescriptor>();
		for (Iterator<?> links = container.getLinks().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Link) {
				continue;
			}
			Link link = (Link) linkObject;
			if (Link2EditPart.VISUAL_ID != SoundgatesVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Port dst = link.getTarget();
			Port src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new SoundgatesLinkDescriptor(src, dst, link,
					SoundgatesElementTypes.Link_4002, Link2EditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<SoundgatesLinkDescriptor> getOutgoingTypeModelFacetLinks_Delegation_4003(
			Port source) {
		CompositeSoundComponent container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof CompositeSoundComponent) {
				container = (CompositeSoundComponent) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<SoundgatesLinkDescriptor> result = new LinkedList<SoundgatesLinkDescriptor>();
		for (Iterator<?> links = container.getDelegations().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Delegation) {
				continue;
			}
			Delegation link = (Delegation) linkObject;
			if (DelegationEditPart.VISUAL_ID != SoundgatesVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Port dst = link.getTarget();
			Port src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new SoundgatesLinkDescriptor(src, dst, link,
					SoundgatesElementTypes.Delegation_4003,
					DelegationEditPart.VISUAL_ID));
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

	public static void refreshContainer(EObject container)
			throws ExecutionException {
		List<CanonicalEditPolicy> editPolicies = CanonicalEditPolicy
				.getRegisteredEditPolicies(container);
		for (Iterator<CanonicalEditPolicy> it = editPolicies.iterator(); it
				.hasNext();) {
			CanonicalEditPolicy nextEditPolicy = (CanonicalEditPolicy) it
					.next();
			nextEditPolicy.refresh();
		}
	}

}
