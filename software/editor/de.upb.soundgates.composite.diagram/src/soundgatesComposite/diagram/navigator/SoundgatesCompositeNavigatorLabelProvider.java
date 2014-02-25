package soundgatesComposite.diagram.navigator;

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ITreePathLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.ViewerLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

/**
 * @generated
 */
public class SoundgatesCompositeNavigatorLabelProvider extends LabelProvider
		implements ICommonLabelProvider, ITreePathLabelProvider {

	/**
	 * @generated
	 */
	static {
		soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin
				.getInstance()
				.getImageRegistry()
				.put("Navigator?UnknownElement", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
		soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin
				.getInstance()
				.getImageRegistry()
				.put("Navigator?ImageNotFound", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	public void updateLabel(ViewerLabel label, TreePath elementPath) {
		Object element = elementPath.getLastSegment();
		if (element instanceof soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorItem
				&& !isOwnView(((soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorItem) element)
						.getView())) {
			return;
		}
		label.setText(getText(element));
		label.setImage(getImage(element));
	}

	/**
	 * @generated
	 */
	public Image getImage(Object element) {
		if (element instanceof soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorGroup) {
			soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorGroup group = (soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorGroup) element;
			return soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin
					.getInstance().getBundledImage(group.getIcon());
		}

		if (element instanceof soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorItem) {
			soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorItem navigatorItem = (soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return super.getImage(element);
			}
			return getImage(navigatorItem.getView());
		}

		return super.getImage(element);
	}

	/**
	 * @generated
	 */
	public Image getImage(View view) {
		switch (soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
				.getVisualID(view)) {
		case soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Diagram?soundgatesComposite?Workbench", soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Workbench_1000); //$NON-NLS-1$
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?soundgatesComposite?CompositeSoundComponent", soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.CompositeSoundComponent_2002); //$NON-NLS-1$
		case soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?soundgatesComposite?Port", soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Port_3004); //$NON-NLS-1$
		case soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?soundgatesComposite?AtomicSoundComponent", soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.AtomicSoundComponent_3005); //$NON-NLS-1$
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?soundgatesComposite?CompositeSoundComponent", soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.CompositeSoundComponent_3006); //$NON-NLS-1$
		case soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Link?soundgatesComposite?Link", soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Link_4003); //$NON-NLS-1$
		case soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Link?soundgatesComposite?Delegation", soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Delegation_4004); //$NON-NLS-1$
		}
		return getImage("Navigator?UnknownElement", null); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private Image getImage(String key, IElementType elementType) {
		ImageRegistry imageRegistry = soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin
				.getInstance().getImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null
				&& elementType != null
				&& soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes
						.isKnownElementType(elementType)) {
			image = soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes
					.getImage(elementType);
			imageRegistry.put(key, image);
		}

		if (image == null) {
			image = imageRegistry.get("Navigator?ImageNotFound"); //$NON-NLS-1$
			imageRegistry.put(key, image);
		}
		return image;
	}

	/**
	 * @generated
	 */
	public String getText(Object element) {
		if (element instanceof soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorGroup) {
			soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorGroup group = (soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorGroup) element;
			return group.getGroupName();
		}

		if (element instanceof soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorItem) {
			soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorItem navigatorItem = (soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorItem) element;
			if (!isOwnView(navigatorItem.getView())) {
				return null;
			}
			return getText(navigatorItem.getView());
		}

		return super.getText(element);
	}

	/**
	 * @generated
	 */
	public String getText(View view) {
		if (view.getElement() != null && view.getElement().eIsProxy()) {
			return getUnresolvedDomainElementProxyText(view);
		}
		switch (soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
				.getVisualID(view)) {
		case soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.VISUAL_ID:
			return getWorkbench_1000Text(view);
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentEditPart.VISUAL_ID:
			return getCompositeSoundComponent_2002Text(view);
		case soundgatesComposite.diagram.edit.parts.PortEditPart.VISUAL_ID:
			return getPort_3004Text(view);
		case soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart.VISUAL_ID:
			return getAtomicSoundComponent_3005Text(view);
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponent2EditPart.VISUAL_ID:
			return getCompositeSoundComponent_3006Text(view);
		case soundgatesComposite.diagram.edit.parts.LinkEditPart.VISUAL_ID:
			return getLink_4003Text(view);
		case soundgatesComposite.diagram.edit.parts.DelegationEditPart.VISUAL_ID:
			return getDelegation_4004Text(view);
		}
		return getUnknownElementText(view);
	}

	/**
	 * @generated
	 */
	private String getWorkbench_1000Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getCompositeSoundComponent_2002Text(View view) {
		IParser parser = soundgatesComposite.diagram.providers.SoundgatesCompositeParserProvider
				.getParser(
						soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.CompositeSoundComponent_2002,
						view.getElement() != null ? view.getElement() : view,
						soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
								.getType(soundgatesComposite.diagram.edit.parts.CompositeSoundComponentNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin
					.getInstance().logError(
							"Parser was not found for label " + 5008); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getPort_3004Text(View view) {
		IParser parser = soundgatesComposite.diagram.providers.SoundgatesCompositeParserProvider
				.getParser(
						soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.Port_3004,
						view.getElement() != null ? view.getElement() : view,
						soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
								.getType(soundgatesComposite.diagram.edit.parts.PortNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin
					.getInstance().logError(
							"Parser was not found for label " + 5005); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getAtomicSoundComponent_3005Text(View view) {
		IParser parser = soundgatesComposite.diagram.providers.SoundgatesCompositeParserProvider
				.getParser(
						soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.AtomicSoundComponent_3005,
						view.getElement() != null ? view.getElement() : view,
						soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
								.getType(soundgatesComposite.diagram.edit.parts.AtomicSoundComponentNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin
					.getInstance().logError(
							"Parser was not found for label " + 5006); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getCompositeSoundComponent_3006Text(View view) {
		IParser parser = soundgatesComposite.diagram.providers.SoundgatesCompositeParserProvider
				.getParser(
						soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes.CompositeSoundComponent_3006,
						view.getElement() != null ? view.getElement() : view,
						soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
								.getType(soundgatesComposite.diagram.edit.parts.CompositeSoundComponentName2EditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin
					.getInstance().logError(
							"Parser was not found for label " + 5007); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getLink_4003Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getDelegation_4004Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getUnknownElementText(View view) {
		return "<UnknownElement Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$  //$NON-NLS-2$
	}

	/**
	 * @generated
	 */
	private String getUnresolvedDomainElementProxyText(View view) {
		return "<Unresolved domain element Visual_ID = " + view.getType() + ">"; //$NON-NLS-1$  //$NON-NLS-2$
	}

	/**
	 * @generated
	 */
	public void init(ICommonContentExtensionSite aConfig) {
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
	public String getDescription(Object anElement) {
		return null;
	}

	/**
	 * @generated
	 */
	private boolean isOwnView(View view) {
		return soundgatesComposite.diagram.edit.parts.WorkbenchEditPart.MODEL_ID
				.equals(soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
						.getModelID(view));
	}

}
