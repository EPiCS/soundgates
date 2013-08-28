package soundgates.provider.custom;

import java.util.Arrays;

import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

public class FloatPropertiesPropertyDescriptor extends PropertyDescriptor {

	public FloatPropertiesPropertyDescriptor(Object object,
			IItemPropertyDescriptor itemPropertyDescriptor) {
		super(object, itemPropertyDescriptor);
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		return new ExtendedDialogCellEditor(parent, getLabelProvider()) {

			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				// TODO Implement stuff here
				ElementListSelectionDialog dialog = new ElementListSelectionDialog(
						PlatformUI.getWorkbench().getDisplay().getActiveShell(),
						getLabelProvider());
				String [] test = {"elem1","elem2"};
				dialog.setElements(test);
				dialog.setTitle("setTitle");
				dialog.setMessage("SetMessage");
				
				int result = dialog.open();
				labelProvider.dispose();
				
				return result == Window.OK ? Arrays.asList(dialog.getResult()) : null;
			}
		};
	}

}