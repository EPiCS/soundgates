package soundgatesComposite.diagram.part;

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

/**
 * @generated
 */
public class SoundgatesCompositeDiagramUpdater {

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor> getSemanticChildren(
			View view) {
		switch (soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
				.getVisualID(view)) {
		case soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.VISUAL_ID:
			return getWorkbench_1000SemanticChildren(view);
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentEditPart.VISUAL_ID:
			return getCompositeSoundComponent_2002SemanticChildren(view);
		case soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID:
			return getAtomicSoundComponent_3005SemanticChildren(view);
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID:
			return getCompositeSoundComponent_3006SemanticChildren(view);
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentComponentCompartmentEditPart.VISUAL_ID:
			return getCompositeSoundComponentComponentCompartment_7003SemanticChildren(view);
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentComponentCompartment2EditPart.VISUAL_ID:
			return getCompositeSoundComponentComponentCompartment_7004SemanticChildren(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor> getWorkbench_1000SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		soundgatesComposite.Workbench modelElement = (soundgatesComposite.Workbench) view
				.getElement();
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor>();
		for (Iterator<?> it = modelElement.getCompositeSoundComponents()
				.iterator(); it.hasNext();) {
			soundgatesComposite.CompositeSoundComponent childElement = (soundgatesComposite.CompositeSoundComponent) it
					.next();
			int visualID = soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getNodeVisualID(view, childElement);
			if (visualID == soundgatesComposite.diagram.edit.parts.CompositeSoundComponentEditPart.VISUAL_ID) {
				result.add(new soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor(
						childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor> getCompositeSoundComponent_2002SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		soundgatesComposite.CompositeSoundComponent modelElement = (soundgatesComposite.CompositeSoundComponent) view
				.getElement();
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor>();
		for (Iterator<?> it = modelElement.getPorts().iterator(); it.hasNext();) {
			soundgatesComposite.Port childElement = (soundgatesComposite.Port) it
					.next();
			int visualID = soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getNodeVisualID(view, childElement);
			if (visualID == soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID) {
				result.add(new soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor(
						childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor> getAtomicSoundComponent_3005SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		soundgatesComposite.AtomicSoundComponent modelElement = (soundgatesComposite.AtomicSoundComponent) view
				.getElement();
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor>();
		for (Iterator<?> it = modelElement.getPorts().iterator(); it.hasNext();) {
			soundgatesComposite.Port childElement = (soundgatesComposite.Port) it
					.next();
			int visualID = soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getNodeVisualID(view, childElement);
			if (visualID == soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID) {
				result.add(new soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor(
						childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor> getCompositeSoundComponent_3006SemanticChildren(
			View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		soundgatesComposite.CompositeSoundComponent modelElement = (soundgatesComposite.CompositeSoundComponent) view
				.getElement();
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor>();
		for (Iterator<?> it = modelElement.getPorts().iterator(); it.hasNext();) {
			soundgatesComposite.Port childElement = (soundgatesComposite.Port) it
					.next();
			int visualID = soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getNodeVisualID(view, childElement);
			if (visualID == soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID) {
				result.add(new soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor(
						childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor> getCompositeSoundComponentComponentCompartment_7003SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		soundgatesComposite.CompositeSoundComponent modelElement = (soundgatesComposite.CompositeSoundComponent) containerView
				.getElement();
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor>();
		for (Iterator<?> it = modelElement.getEmbeddedComponents().iterator(); it
				.hasNext();) {
			soundgatesComposite.SoundComponent childElement = (soundgatesComposite.SoundComponent) it
					.next();
			int visualID = soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getNodeVisualID(view, childElement);
			if (visualID == soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID) {
				result.add(new soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor(
						childElement, visualID));
				continue;
			}
			if (visualID == soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID) {
				result.add(new soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor(
						childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor> getCompositeSoundComponentComponentCompartment_7004SemanticChildren(
			View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		soundgatesComposite.CompositeSoundComponent modelElement = (soundgatesComposite.CompositeSoundComponent) containerView
				.getElement();
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor>();
		for (Iterator<?> it = modelElement.getEmbeddedComponents().iterator(); it
				.hasNext();) {
			soundgatesComposite.SoundComponent childElement = (soundgatesComposite.SoundComponent) it
					.next();
			int visualID = soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getNodeVisualID(view, childElement);
			if (visualID == soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID) {
				result.add(new soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor(
						childElement, visualID));
				continue;
			}
			if (visualID == soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID) {
				result.add(new soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor(
						childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getContainedLinks(
			View view) {
		switch (soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
				.getVisualID(view)) {
		case soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.VISUAL_ID:
			return getWorkbench_1000ContainedLinks(view);
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentEditPart.VISUAL_ID:
			return getCompositeSoundComponent_2002ContainedLinks(view);
		case soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID:
			return getPort_3004ContainedLinks(view);
		case soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID:
			return getAtomicSoundComponent_3005ContainedLinks(view);
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID:
			return getCompositeSoundComponent_3006ContainedLinks(view);
		case soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID:
			return getLink_4003ContainedLinks(view);
		case soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID:
			return getDelegation_4004ContainedLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getIncomingLinks(
			View view) {
		switch (soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
				.getVisualID(view)) {
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentEditPart.VISUAL_ID:
			return getCompositeSoundComponent_2002IncomingLinks(view);
		case soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID:
			return getPort_3004IncomingLinks(view);
		case soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID:
			return getAtomicSoundComponent_3005IncomingLinks(view);
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID:
			return getCompositeSoundComponent_3006IncomingLinks(view);
		case soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID:
			return getLink_4003IncomingLinks(view);
		case soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID:
			return getDelegation_4004IncomingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getOutgoingLinks(
			View view) {
		switch (soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
				.getVisualID(view)) {
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentEditPart.VISUAL_ID:
			return getCompositeSoundComponent_2002OutgoingLinks(view);
		case soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID:
			return getPort_3004OutgoingLinks(view);
		case soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID:
			return getAtomicSoundComponent_3005OutgoingLinks(view);
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID:
			return getCompositeSoundComponent_3006OutgoingLinks(view);
		case soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID:
			return getLink_4003OutgoingLinks(view);
		case soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID:
			return getDelegation_4004OutgoingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getWorkbench_1000ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getCompositeSoundComponent_2002ContainedLinks(
			View view) {
		soundgatesComposite.CompositeSoundComponent modelElement = (soundgatesComposite.CompositeSoundComponent) view
				.getElement();
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor>();
		result.addAll(getContainedTypeModelFacetLinks_Link_4003(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Delegation_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getPort_3004ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getAtomicSoundComponent_3005ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getCompositeSoundComponent_3006ContainedLinks(
			View view) {
		soundgatesComposite.CompositeSoundComponent modelElement = (soundgatesComposite.CompositeSoundComponent) view
				.getElement();
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor>();
		result.addAll(getContainedTypeModelFacetLinks_Link_4003(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Delegation_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getLink_4003ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getDelegation_4004ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getCompositeSoundComponent_2002IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getPort_3004IncomingLinks(
			View view) {
		soundgatesComposite.Port modelElement = (soundgatesComposite.Port) view
				.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor>();
		result.addAll(getIncomingTypeModelFacetLinks_Link_4003(modelElement,
				crossReferences));
		result.addAll(getIncomingTypeModelFacetLinks_Delegation_4004(
				modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getAtomicSoundComponent_3005IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getCompositeSoundComponent_3006IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getLink_4003IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getDelegation_4004IncomingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getCompositeSoundComponent_2002OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getPort_3004OutgoingLinks(
			View view) {
		soundgatesComposite.Port modelElement = (soundgatesComposite.Port) view
				.getElement();
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor>();
		result.addAll(getOutgoingTypeModelFacetLinks_Link_4003(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Delegation_4004(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getAtomicSoundComponent_3005OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getCompositeSoundComponent_3006OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getLink_4003OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getDelegation_4004OutgoingLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	private static Collection<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getContainedTypeModelFacetLinks_Link_4003(
			soundgatesComposite.CompositeSoundComponent container) {
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor>();
		for (Iterator<?> links = container.getLinks().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof soundgatesComposite.Link) {
				continue;
			}
			soundgatesComposite.Link link = (soundgatesComposite.Link) linkObject;
			if (soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID != soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			soundgatesComposite.Port dst = link.getTarget();
			soundgatesComposite.Port src = link.getSource();
			result.add(new soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor(
					src,
					dst,
					link,
					soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Link_4003,
					soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getContainedTypeModelFacetLinks_Delegation_4004(
			soundgatesComposite.CompositeSoundComponent container) {
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor>();
		for (Iterator<?> links = container.getDelegations().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof soundgatesComposite.Delegation) {
				continue;
			}
			soundgatesComposite.Delegation link = (soundgatesComposite.Delegation) linkObject;
			if (soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID != soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			soundgatesComposite.Port dst = link.getTarget();
			soundgatesComposite.Port src = link.getSource();
			result.add(new soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor(
					src,
					dst,
					link,
					soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Delegation_4004,
					soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getIncomingTypeModelFacetLinks_Link_4003(
			soundgatesComposite.Port target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences
				.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
					.getConnection_Target()
					|| false == setting.getEObject() instanceof soundgatesComposite.Link) {
				continue;
			}
			soundgatesComposite.Link link = (soundgatesComposite.Link) setting
					.getEObject();
			if (soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID != soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			soundgatesComposite.Port src = link.getSource();
			result.add(new soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor(
					src,
					target,
					link,
					soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Link_4003,
					soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getIncomingTypeModelFacetLinks_Delegation_4004(
			soundgatesComposite.Port target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences
				.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
					.getConnection_Target()
					|| false == setting.getEObject() instanceof soundgatesComposite.Delegation) {
				continue;
			}
			soundgatesComposite.Delegation link = (soundgatesComposite.Delegation) setting
					.getEObject();
			if (soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID != soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			soundgatesComposite.Port src = link.getSource();
			result.add(new soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor(
					src,
					target,
					link,
					soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Delegation_4004,
					soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getOutgoingTypeModelFacetLinks_Link_4003(
			soundgatesComposite.Port source) {
		soundgatesComposite.CompositeSoundComponent container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof soundgatesComposite.CompositeSoundComponent) {
				container = (soundgatesComposite.CompositeSoundComponent) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor>();
		for (Iterator<?> links = container.getLinks().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof soundgatesComposite.Link) {
				continue;
			}
			soundgatesComposite.Link link = (soundgatesComposite.Link) linkObject;
			if (soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID != soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			soundgatesComposite.Port dst = link.getTarget();
			soundgatesComposite.Port src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor(
					src,
					dst,
					link,
					soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Link_4003,
					soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getOutgoingTypeModelFacetLinks_Delegation_4004(
			soundgatesComposite.Port source) {
		soundgatesComposite.CompositeSoundComponent container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof soundgatesComposite.CompositeSoundComponent) {
				container = (soundgatesComposite.CompositeSoundComponent) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> result = new LinkedList<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor>();
		for (Iterator<?> links = container.getDelegations().iterator(); links
				.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof soundgatesComposite.Delegation) {
				continue;
			}
			soundgatesComposite.Delegation link = (soundgatesComposite.Delegation) linkObject;
			if (soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID != soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			soundgatesComposite.Port dst = link.getTarget();
			soundgatesComposite.Port src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor(
					src,
					dst,
					link,
					soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Delegation_4004,
					soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID));
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
		public List<soundgatesComposite.diagram.part.SoundgatesCompositeNodeDescriptor> getSemanticChildren(
				View view) {
			return SoundgatesCompositeDiagramUpdater.getSemanticChildren(view);
		}

		/**
		 * @generated
		 */
		@Override
		public List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getContainedLinks(
				View view) {
			return SoundgatesCompositeDiagramUpdater.getContainedLinks(view);
		}

		/**
		 * @generated
		 */
		@Override
		public List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getIncomingLinks(
				View view) {
			return SoundgatesCompositeDiagramUpdater.getIncomingLinks(view);
		}

		/**
		 * @generated
		 */
		@Override
		public List<soundgatesComposite.diagram.part.SoundgatesCompositeLinkDescriptor> getOutgoingLinks(
				View view) {
			return SoundgatesCompositeDiagramUpdater.getOutgoingLinks(view);
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
