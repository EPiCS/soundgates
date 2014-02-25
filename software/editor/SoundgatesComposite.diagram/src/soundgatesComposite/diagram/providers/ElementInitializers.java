package soundgatesComposite.diagram.providers;

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
		ElementInitializers cached = soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin
				.getInstance().getElementInitializers();
		if (cached == null) {
			soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin
					.getInstance().setElementInitializers(
							cached = new ElementInitializers());
		}
		return cached;
	}
}
