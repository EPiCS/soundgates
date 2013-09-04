package soundgates.provider.custom;

import java.util.Arrays;

import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import soundgates.AtomicSoundComponent;

public class FloatPropertiesPropertyDescriptor extends PropertyDescriptor {

	public FloatPropertiesPropertyDescriptor(Object object,
			IItemPropertyDescriptor itemPropertyDescriptor) {
		super(object, itemPropertyDescriptor);
	}

	@Override
	public String getDisplayName() {
		return super.getDisplayName();
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		boolean x = false;
		if (x) {
			return super.createPropertyEditor(parent);
			// if (genericFeature instanceof EStructuralFeature)
		}

		
		
		return new ExtendedDialogCellEditor(parent, getLabelProvider()) {

			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				InputDialog dialog = new FloatPropertyInputDialog(PlatformUI.getWorkbench()
						.getDisplay().getActiveShell(), "dialogTitle",
						"dialogMessage", "initialValue", null, object);
				int result = dialog.open();
				return result == Window.OK ? dialog.getReturnCode() : null;
			}
			// @Override
			// protected Object openDialogBox(Control cellEditorWindow) {
			// // TODO Implement stuff here
			// ElementListSelectionDialog dialog = new
			// ElementListSelectionDialog(
			// PlatformUI.getWorkbench().getDisplay().getActiveShell(),
			// getLabelProvider());
			// String [] test = {"elem1","elem2"};
			// dialog.setElements(test);
			// dialog.setTitle("setTitle");
			// dialog.setMessage("SetMessage");
			//
			// int result = dialog.open();
			// labelProvider.dispose();
			//
			// return result == Window.OK ? Arrays.asList(dialog.getResult()) :
			// null;
			// }

		};
	}
}

class FloatPropertyInputDialog extends InputDialog {
	//TODO so bauen, dass für jede Property eine Zeile existiert
	//TODO Label im Property Tab zeigt Blödsinn an
	//TODO Wird aktuell nur im Tree Editor nicht im Diagramm Editor benutzt

	Object object;
	
	public FloatPropertyInputDialog(Shell parentShell, String dialogTitle,
			String dialogMessage, String initialValue, IInputValidator validator, Object object) {
		super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
		this.object = object;

	}
	
	@Override
	protected void okPressed() {
		System.out.println("Try to set \"SomeValue\" to " + this.getValue());
		try {
			AtomicSoundComponent ats = (AtomicSoundComponent)object;
			ats.getFloatProperties().put("SomeValue", Float.parseFloat(this.getValue())); //Verdammt, das funktioniert!
			
		}
		catch (Exception e) {
			
		}
		super.okPressed();
	}
	
}