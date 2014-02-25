package soundgatesComposite.diagram.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @generated
 */
public class DiagramPreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * @generated
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = getPreferenceStore();
		soundgatesComposite.diagram.preferences.DiagramGeneralPreferencePage
				.initDefaults(store);
		soundgatesComposite.diagram.preferences.DiagramAppearancePreferencePage
				.initDefaults(store);
		soundgatesComposite.diagram.preferences.DiagramConnectionsPreferencePage
				.initDefaults(store);
		soundgatesComposite.diagram.preferences.DiagramPrintingPreferencePage
				.initDefaults(store);
		soundgatesComposite.diagram.preferences.DiagramRulersAndGridPreferencePage
				.initDefaults(store);

	}

	/**
	 * @generated
	 */
	protected IPreferenceStore getPreferenceStore() {
		return soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin
				.getInstance().getPreferenceStore();
	}
}
