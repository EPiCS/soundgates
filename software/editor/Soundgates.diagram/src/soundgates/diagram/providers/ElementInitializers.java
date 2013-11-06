package soundgates.diagram.providers;

import soundgates.diagram.part.SoundgatesDiagramEditorPlugin;

/**
 * @generated
 */
public class ElementInitializers {

	protected ElementInitializers() {
		// use #getInstance to access cached instance
	}

	/**
	 * @generated
	 */
	public static ElementInitializers getInstance() {
		ElementInitializers cached = SoundgatesDiagramEditorPlugin
				.getInstance().getElementInitializers();
		if (cached == null) {
			SoundgatesDiagramEditorPlugin.getInstance().setElementInitializers(
					cached = new ElementInitializers());
		}
		return cached;
	}
}
