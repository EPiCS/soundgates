package soundgates.diagram.navigator;

import org.eclipse.jface.viewers.ViewerSorter;

import soundgates.diagram.part.SoundgatesVisualIDRegistry;

/**
 * @generated
 */
public class SoundgatesNavigatorSorter extends ViewerSorter {

	/**
	 * @generated
	 */
	private static final int GROUP_CATEGORY = 7004;

	/**
	 * @generated
	 */
	public int category(Object element) {
		if (element instanceof SoundgatesNavigatorItem) {
			SoundgatesNavigatorItem item = (SoundgatesNavigatorItem) element;
			return SoundgatesVisualIDRegistry.getVisualID(item.getView());
		}
		return GROUP_CATEGORY;
	}

}
