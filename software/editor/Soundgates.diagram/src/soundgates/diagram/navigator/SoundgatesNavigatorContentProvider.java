package soundgates.diagram.navigator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonContentProvider;

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
import soundgates.diagram.part.Messages;
import soundgates.diagram.part.SoundgatesVisualIDRegistry;

/**
 * @generated
 */
public class SoundgatesNavigatorContentProvider implements
		ICommonContentProvider {

	/**
	 * @generated
	 */
	private static final Object[] EMPTY_ARRAY = new Object[0];

	/**
	 * @generated
	 */
	private Viewer myViewer;

	/**
	 * @generated
	 */
	private AdapterFactoryEditingDomain myEditingDomain;

	/**
	 * @generated
	 */
	private WorkspaceSynchronizer myWorkspaceSynchronizer;

	/**
	 * @generated
	 */
	private Runnable myViewerRefreshRunnable;

	/**
	 * @generated
	 */
	@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
	public SoundgatesNavigatorContentProvider() {
		TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE
				.createEditingDomain();
		myEditingDomain = (AdapterFactoryEditingDomain) editingDomain;
		myEditingDomain.setResourceToReadOnlyMap(new HashMap() {
			public Object get(Object key) {
				if (!containsKey(key)) {
					put(key, Boolean.TRUE);
				}
				return super.get(key);
			}
		});
		myViewerRefreshRunnable = new Runnable() {
			public void run() {
				if (myViewer != null) {
					myViewer.refresh();
				}
			}
		};
		myWorkspaceSynchronizer = new WorkspaceSynchronizer(editingDomain,
				new WorkspaceSynchronizer.Delegate() {
					public void dispose() {
					}

					public boolean handleResourceChanged(final Resource resource) {
						unloadAllResources();
						asyncRefresh();
						return true;
					}

					public boolean handleResourceDeleted(Resource resource) {
						unloadAllResources();
						asyncRefresh();
						return true;
					}

					public boolean handleResourceMoved(Resource resource,
							final URI newURI) {
						unloadAllResources();
						asyncRefresh();
						return true;
					}
				});
	}

	/**
	 * @generated
	 */
	public void dispose() {
		myWorkspaceSynchronizer.dispose();
		myWorkspaceSynchronizer = null;
		myViewerRefreshRunnable = null;
		myViewer = null;
		unloadAllResources();
		((TransactionalEditingDomain) myEditingDomain).dispose();
		myEditingDomain = null;
	}

	/**
	 * @generated
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		myViewer = viewer;
	}

	/**
	 * @generated
	 */
	void unloadAllResources() {
		for (Resource nextResource : myEditingDomain.getResourceSet()
				.getResources()) {
			nextResource.unload();
		}
	}

	/**
	 * @generated
	 */
	void asyncRefresh() {
		if (myViewer != null && !myViewer.getControl().isDisposed()) {
			myViewer.getControl().getDisplay()
					.asyncExec(myViewerRefreshRunnable);
		}
	}

	/**
	 * @generated
	 */
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	/**
	 * @generated
	 */
	public void restoreState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	public void saveState(IMemento aMemento) {
	}

	/**
	 * @generated
	 */
	public void init(ICommonContentExtensionSite aConfig) {
	}

	/**
	 * @generated
	 */
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof IFile) {
			IFile file = (IFile) parentElement;
			URI fileURI = URI.createPlatformResourceURI(file.getFullPath()
					.toString(), true);
			Resource resource = myEditingDomain.getResourceSet().getResource(
					fileURI, true);
			ArrayList<SoundgatesNavigatorItem> result = new ArrayList<SoundgatesNavigatorItem>();
			ArrayList<View> topViews = new ArrayList<View>(resource
					.getContents().size());
			for (EObject o : resource.getContents()) {
				if (o instanceof View) {
					topViews.add((View) o);
				}
			}
			result.addAll(createNavigatorItems(
					selectViewsByType(topViews, PatchEditPart.MODEL_ID), file,
					false));
			return result.toArray();
		}

		if (parentElement instanceof SoundgatesNavigatorGroup) {
			SoundgatesNavigatorGroup group = (SoundgatesNavigatorGroup) parentElement;
			return group.getChildren();
		}

		if (parentElement instanceof SoundgatesNavigatorItem) {
			SoundgatesNavigatorItem navigatorItem = (SoundgatesNavigatorItem) parentElement;
			if (navigatorItem.isLeaf() || !isOwnView(navigatorItem.getView())) {
				return EMPTY_ARRAY;
			}
			return getViewChildren(navigatorItem.getView(), parentElement);
		}

		return EMPTY_ARRAY;
	}

	/**
	 * @generated
	 */
	private Object[] getViewChildren(View view, Object parentElement) {
		switch (SoundgatesVisualIDRegistry.getVisualID(view)) {

		case PatchEditPart.VISUAL_ID: {
			LinkedList<SoundgatesAbstractNavigatorItem> result = new LinkedList<SoundgatesAbstractNavigatorItem>();
			Diagram sv = (Diagram) view;
			SoundgatesNavigatorGroup links = new SoundgatesNavigatorGroup(
					Messages.NavigatorGroupName_Patch_1000_links,
					"icons/linksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getChildrenByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry
							.getType(AtomicSoundComponentEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			connectedViews = getChildrenByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry
							.getType(CompositeSoundComponentEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			connectedViews = getDiagramLinksByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(LinkEditPart.VISUAL_ID));
			links.addChildren(createNavigatorItems(connectedViews, links, false));
			connectedViews = getDiagramLinksByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(Link2EditPart.VISUAL_ID));
			links.addChildren(createNavigatorItems(connectedViews, links, false));
			connectedViews = getDiagramLinksByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry
							.getType(DelegationEditPart.VISUAL_ID));
			links.addChildren(createNavigatorItems(connectedViews, links, false));
			if (!links.isEmpty()) {
				result.add(links);
			}
			return result.toArray();
		}

		case AtomicSoundComponentEditPart.VISUAL_ID: {
			LinkedList<SoundgatesAbstractNavigatorItem> result = new LinkedList<SoundgatesAbstractNavigatorItem>();
			Node sv = (Node) view;
			Collection<View> connectedViews;
			connectedViews = getChildrenByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(PortEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			return result.toArray();
		}

		case CompositeSoundComponentEditPart.VISUAL_ID: {
			LinkedList<SoundgatesAbstractNavigatorItem> result = new LinkedList<SoundgatesAbstractNavigatorItem>();
			Node sv = (Node) view;
			Collection<View> connectedViews;
			connectedViews = getChildrenByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(PortEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			connectedViews = getChildrenByType(
					Collections.singleton(sv),
					SoundgatesVisualIDRegistry
							.getType(CompositeSoundComponentComponentCompartmentEditPart.VISUAL_ID));
			connectedViews = getChildrenByType(connectedViews,
					SoundgatesVisualIDRegistry
							.getType(AtomicSoundComponent2EditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			connectedViews = getChildrenByType(
					Collections.singleton(sv),
					SoundgatesVisualIDRegistry
							.getType(CompositeSoundComponentComponentCompartmentEditPart.VISUAL_ID));
			connectedViews = getChildrenByType(
					connectedViews,
					SoundgatesVisualIDRegistry
							.getType(CompositeSoundComponent2EditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			return result.toArray();
		}

		case PortEditPart.VISUAL_ID: {
			LinkedList<SoundgatesAbstractNavigatorItem> result = new LinkedList<SoundgatesAbstractNavigatorItem>();
			Node sv = (Node) view;
			SoundgatesNavigatorGroup incominglinks = new SoundgatesNavigatorGroup(
					Messages.NavigatorGroupName_Port_3001_incominglinks,
					"icons/incomingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			SoundgatesNavigatorGroup outgoinglinks = new SoundgatesNavigatorGroup(
					Messages.NavigatorGroupName_Port_3001_outgoinglinks,
					"icons/outgoingLinksNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getIncomingLinksByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(LinkEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(LinkEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(Link2EditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(Link2EditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			connectedViews = getIncomingLinksByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry
							.getType(DelegationEditPart.VISUAL_ID));
			incominglinks.addChildren(createNavigatorItems(connectedViews,
					incominglinks, true));
			connectedViews = getOutgoingLinksByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry
							.getType(DelegationEditPart.VISUAL_ID));
			outgoinglinks.addChildren(createNavigatorItems(connectedViews,
					outgoinglinks, true));
			if (!incominglinks.isEmpty()) {
				result.add(incominglinks);
			}
			if (!outgoinglinks.isEmpty()) {
				result.add(outgoinglinks);
			}
			return result.toArray();
		}

		case AtomicSoundComponent2EditPart.VISUAL_ID: {
			LinkedList<SoundgatesAbstractNavigatorItem> result = new LinkedList<SoundgatesAbstractNavigatorItem>();
			Node sv = (Node) view;
			Collection<View> connectedViews;
			connectedViews = getChildrenByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(PortEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			return result.toArray();
		}

		case CompositeSoundComponent2EditPart.VISUAL_ID: {
			LinkedList<SoundgatesAbstractNavigatorItem> result = new LinkedList<SoundgatesAbstractNavigatorItem>();
			Node sv = (Node) view;
			Collection<View> connectedViews;
			connectedViews = getChildrenByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(PortEditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			connectedViews = getChildrenByType(
					Collections.singleton(sv),
					SoundgatesVisualIDRegistry
							.getType(CompositeSoundComponentComponentCompartment2EditPart.VISUAL_ID));
			connectedViews = getChildrenByType(connectedViews,
					SoundgatesVisualIDRegistry
							.getType(AtomicSoundComponent2EditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			connectedViews = getChildrenByType(
					Collections.singleton(sv),
					SoundgatesVisualIDRegistry
							.getType(CompositeSoundComponentComponentCompartment2EditPart.VISUAL_ID));
			connectedViews = getChildrenByType(
					connectedViews,
					SoundgatesVisualIDRegistry
							.getType(CompositeSoundComponent2EditPart.VISUAL_ID));
			result.addAll(createNavigatorItems(connectedViews, parentElement,
					false));
			return result.toArray();
		}

		case LinkEditPart.VISUAL_ID: {
			LinkedList<SoundgatesAbstractNavigatorItem> result = new LinkedList<SoundgatesAbstractNavigatorItem>();
			Edge sv = (Edge) view;
			SoundgatesNavigatorGroup target = new SoundgatesNavigatorGroup(
					Messages.NavigatorGroupName_Link_4001_target,
					"icons/linkTargetNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			SoundgatesNavigatorGroup source = new SoundgatesNavigatorGroup(
					Messages.NavigatorGroupName_Link_4001_source,
					"icons/linkSourceNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getLinksTargetByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(PortEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(PortEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			if (!target.isEmpty()) {
				result.add(target);
			}
			if (!source.isEmpty()) {
				result.add(source);
			}
			return result.toArray();
		}

		case Link2EditPart.VISUAL_ID: {
			LinkedList<SoundgatesAbstractNavigatorItem> result = new LinkedList<SoundgatesAbstractNavigatorItem>();
			Edge sv = (Edge) view;
			SoundgatesNavigatorGroup target = new SoundgatesNavigatorGroup(
					Messages.NavigatorGroupName_Link_4002_target,
					"icons/linkTargetNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			SoundgatesNavigatorGroup source = new SoundgatesNavigatorGroup(
					Messages.NavigatorGroupName_Link_4002_source,
					"icons/linkSourceNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getLinksTargetByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(PortEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(PortEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			if (!target.isEmpty()) {
				result.add(target);
			}
			if (!source.isEmpty()) {
				result.add(source);
			}
			return result.toArray();
		}

		case DelegationEditPart.VISUAL_ID: {
			LinkedList<SoundgatesAbstractNavigatorItem> result = new LinkedList<SoundgatesAbstractNavigatorItem>();
			Edge sv = (Edge) view;
			SoundgatesNavigatorGroup target = new SoundgatesNavigatorGroup(
					Messages.NavigatorGroupName_Delegation_4003_target,
					"icons/linkTargetNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			SoundgatesNavigatorGroup source = new SoundgatesNavigatorGroup(
					Messages.NavigatorGroupName_Delegation_4003_source,
					"icons/linkSourceNavigatorGroup.gif", parentElement); //$NON-NLS-1$
			Collection<View> connectedViews;
			connectedViews = getLinksTargetByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(PortEditPart.VISUAL_ID));
			target.addChildren(createNavigatorItems(connectedViews, target,
					true));
			connectedViews = getLinksSourceByType(Collections.singleton(sv),
					SoundgatesVisualIDRegistry.getType(PortEditPart.VISUAL_ID));
			source.addChildren(createNavigatorItems(connectedViews, source,
					true));
			if (!target.isEmpty()) {
				result.add(target);
			}
			if (!source.isEmpty()) {
				result.add(source);
			}
			return result.toArray();
		}
		}
		return EMPTY_ARRAY;
	}

	/**
	 * @generated
	 */
	private Collection<View> getLinksSourceByType(Collection<Edge> edges,
			String type) {
		LinkedList<View> result = new LinkedList<View>();
		for (Edge nextEdge : edges) {
			View nextEdgeSource = nextEdge.getSource();
			if (type.equals(nextEdgeSource.getType())
					&& isOwnView(nextEdgeSource)) {
				result.add(nextEdgeSource);
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection<View> getLinksTargetByType(Collection<Edge> edges,
			String type) {
		LinkedList<View> result = new LinkedList<View>();
		for (Edge nextEdge : edges) {
			View nextEdgeTarget = nextEdge.getTarget();
			if (type.equals(nextEdgeTarget.getType())
					&& isOwnView(nextEdgeTarget)) {
				result.add(nextEdgeTarget);
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection<View> getOutgoingLinksByType(
			Collection<? extends View> nodes, String type) {
		LinkedList<View> result = new LinkedList<View>();
		for (View nextNode : nodes) {
			result.addAll(selectViewsByType(nextNode.getSourceEdges(), type));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection<View> getIncomingLinksByType(
			Collection<? extends View> nodes, String type) {
		LinkedList<View> result = new LinkedList<View>();
		for (View nextNode : nodes) {
			result.addAll(selectViewsByType(nextNode.getTargetEdges(), type));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection<View> getChildrenByType(
			Collection<? extends View> nodes, String type) {
		LinkedList<View> result = new LinkedList<View>();
		for (View nextNode : nodes) {
			result.addAll(selectViewsByType(nextNode.getChildren(), type));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private Collection<View> getDiagramLinksByType(
			Collection<Diagram> diagrams, String type) {
		ArrayList<View> result = new ArrayList<View>();
		for (Diagram nextDiagram : diagrams) {
			result.addAll(selectViewsByType(nextDiagram.getEdges(), type));
		}
		return result;
	}

	// TODO refactor as static method
	/**
	 * @generated
	 */
	private Collection<View> selectViewsByType(Collection<View> views,
			String type) {
		ArrayList<View> result = new ArrayList<View>();
		for (View nextView : views) {
			if (type.equals(nextView.getType()) && isOwnView(nextView)) {
				result.add(nextView);
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private boolean isOwnView(View view) {
		return PatchEditPart.MODEL_ID.equals(SoundgatesVisualIDRegistry
				.getModelID(view));
	}

	/**
	 * @generated
	 */
	private Collection<SoundgatesNavigatorItem> createNavigatorItems(
			Collection<View> views, Object parent, boolean isLeafs) {
		ArrayList<SoundgatesNavigatorItem> result = new ArrayList<SoundgatesNavigatorItem>(
				views.size());
		for (View nextView : views) {
			result.add(new SoundgatesNavigatorItem(nextView, parent, isLeafs));
		}
		return result;
	}

	/**
	 * @generated
	 */
	public Object getParent(Object element) {
		if (element instanceof SoundgatesAbstractNavigatorItem) {
			SoundgatesAbstractNavigatorItem abstractNavigatorItem = (SoundgatesAbstractNavigatorItem) element;
			return abstractNavigatorItem.getParent();
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean hasChildren(Object element) {
		return element instanceof IFile || getChildren(element).length > 0;
	}

}
