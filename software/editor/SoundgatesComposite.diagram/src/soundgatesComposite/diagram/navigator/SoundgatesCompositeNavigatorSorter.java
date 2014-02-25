package soundgatesComposite.diagram.navigator;

import org.eclipse.jface.viewers.ViewerSorter;

/**
 * @generated
 */
public class SoundgatesCompositeNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 7006;

	/**
	 * @generated
	 */
	public int category(Object element) {
		if (element instanceof soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorItem) {
			soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorItem item = (soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorItem) element;
			return soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}
