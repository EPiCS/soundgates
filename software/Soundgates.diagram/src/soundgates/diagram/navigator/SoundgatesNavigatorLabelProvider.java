package soundgates.diagram.navigator;

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

import soundgates.diagram.edit.parts.AtomicSoundComponentEditPart;
import soundgates.diagram.edit.parts.AtomicSoundComponentNameEditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponentEditPart;
import soundgates.diagram.edit.parts.CompositeSoundComponentNameEditPart;
import soundgates.diagram.edit.parts.LinkEditPart;
import soundgates.diagram.edit.parts.PatchEditPart;
import soundgates.diagram.edit.parts.PortEditPart;
import soundgates.diagram.edit.parts.PortNameEditPart;
import soundgates.diagram.part.SoundgatesDiagramEditorPlugin;
import soundgates.diagram.part.SoundgatesVisualIDRegistry;
import soundgates.diagram.providers.SoundgatesElementTypes;
import soundgates.diagram.providers.SoundgatesParserProvider;

/**
 * @generated
 */
public class SoundgatesNavigatorLabelProvider extends LabelProvider implements
		ICommonLabelProvider, ITreePathLabelProvider {

	/**
	 * @generated
	 */
	static {
		SoundgatesDiagramEditorPlugin
				.getInstance()
				.getImageRegistry()
				.put("Navigator?UnknownElement", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
		SoundgatesDiagramEditorPlugin
				.getInstance()
				.getImageRegistry()
				.put("Navigator?ImageNotFound", ImageDescriptor.getMissingImageDescriptor()); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	public void updateLabel(ViewerLabel label, TreePath elementPath) {
		Object element = elementPath.getLastSegment();
		if (element instanceof SoundgatesNavigatorItem
				&& !isOwnView(((SoundgatesNavigatorItem) element).getView())) {
			return;
		}
		label.setText(getText(element));
		label.setImage(getImage(element));
	}

	/**
	 * @generated
	 */
	public Image getImage(Object element) {
		if (element instanceof SoundgatesNavigatorGroup) {
			SoundgatesNavigatorGroup group = (SoundgatesNavigatorGroup) element;
			return SoundgatesDiagramEditorPlugin.getInstance().getBundledImage(
					group.getIcon());
		}

		if (element instanceof SoundgatesNavigatorItem) {
			SoundgatesNavigatorItem navigatorItem = (SoundgatesNavigatorItem) element;
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
		switch (SoundgatesVisualIDRegistry.getVisualID(view)) {
		case PatchEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Diagram?soundgates?Patch", SoundgatesElementTypes.Patch_1000); //$NON-NLS-1$
		case AtomicSoundComponentEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?soundgates?AtomicSoundComponent", SoundgatesElementTypes.AtomicSoundComponent_2001); //$NON-NLS-1$
		case CompositeSoundComponentEditPart.VISUAL_ID:
			return getImage(
					"Navigator?TopLevelNode?soundgates?CompositeSoundComponent", SoundgatesElementTypes.CompositeSoundComponent_2002); //$NON-NLS-1$
		case PortEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Node?soundgates?Port", SoundgatesElementTypes.Port_3001); //$NON-NLS-1$
		case LinkEditPart.VISUAL_ID:
			return getImage(
					"Navigator?Link?soundgates?Link", SoundgatesElementTypes.Link_4001); //$NON-NLS-1$
		}
		return getImage("Navigator?UnknownElement", null); //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private Image getImage(String key, IElementType elementType) {
		ImageRegistry imageRegistry = SoundgatesDiagramEditorPlugin
				.getInstance().getImageRegistry();
		Image image = imageRegistry.get(key);
		if (image == null && elementType != null
				&& SoundgatesElementTypes.isKnownElementType(elementType)) {
			image = SoundgatesElementTypes.getImage(elementType);
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
		if (element instanceof SoundgatesNavigatorGroup) {
			SoundgatesNavigatorGroup group = (SoundgatesNavigatorGroup) element;
			return group.getGroupName();
		}

		if (element instanceof SoundgatesNavigatorItem) {
			SoundgatesNavigatorItem navigatorItem = (SoundgatesNavigatorItem) element;
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
		switch (SoundgatesVisualIDRegistry.getVisualID(view)) {
		case PatchEditPart.VISUAL_ID:
			return getPatch_1000Text(view);
		case AtomicSoundComponentEditPart.VISUAL_ID:
			return getAtomicSoundComponent_2001Text(view);
		case CompositeSoundComponentEditPart.VISUAL_ID:
			return getCompositeSoundComponent_2002Text(view);
		case PortEditPart.VISUAL_ID:
			return getPort_3001Text(view);
		case LinkEditPart.VISUAL_ID:
			return getLink_4001Text(view);
		}
		return getUnknownElementText(view);
	}

	/**
	 * @generated
	 */
	private String getPatch_1000Text(View view) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	private String getAtomicSoundComponent_2001Text(View view) {
		IParser parser = SoundgatesParserProvider.getParser(
				SoundgatesElementTypes.AtomicSoundComponent_2001, view
						.getElement() != null ? view.getElement() : view,
				SoundgatesVisualIDRegistry
						.getType(AtomicSoundComponentNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			SoundgatesDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5002); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getCompositeSoundComponent_2002Text(View view) {
		IParser parser = SoundgatesParserProvider
				.getParser(
						SoundgatesElementTypes.CompositeSoundComponent_2002,
						view.getElement() != null ? view.getElement() : view,
						SoundgatesVisualIDRegistry
								.getType(CompositeSoundComponentNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			SoundgatesDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5003); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getPort_3001Text(View view) {
		IParser parser = SoundgatesParserProvider.getParser(
				SoundgatesElementTypes.Port_3001,
				view.getElement() != null ? view.getElement() : view,
				SoundgatesVisualIDRegistry.getType(PortNameEditPart.VISUAL_ID));
		if (parser != null) {
			return parser.getPrintString(new EObjectAdapter(
					view.getElement() != null ? view.getElement() : view),
					ParserOptions.NONE.intValue());
		} else {
			SoundgatesDiagramEditorPlugin.getInstance().logError(
					"Parser was not found for label " + 5001); //$NON-NLS-1$
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * @generated
	 */
	private String getLink_4001Text(View view) {
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
		return PatchEditPart.MODEL_ID.equals(SoundgatesVisualIDRegistry
				.getModelID(view));
	}

}
