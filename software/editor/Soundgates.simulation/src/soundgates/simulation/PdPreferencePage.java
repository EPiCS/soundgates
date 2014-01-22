package soundgates.simulation;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class PdPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	public static final String PD_EXECUTABLE = "PD_EXEC";
	public static final String PD_ARGUMENTS = "PD_PARAMS";
	public static final String PD_NOGUI = "PD_NOGUI";
	
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Soundgates simulation settings");
	}

	@Override
	protected void createFieldEditors() {
		addField(new FileFieldEditor(PD_EXECUTABLE, "Pure data executable", getFieldEditorParent()));
		addField(new StringFieldEditor(PD_ARGUMENTS, "Parameters (separated by %)", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PD_NOGUI, "Start PD without gui", getFieldEditorParent()));
	}

}
