package soundgates.provider.custom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.ui.celleditor.ExtendedDialogCellEditor;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.emf.edit.ui.provider.PropertyDescriptor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
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
	/**
	 * This method is responsible for returning the value text for the Float Properties on the Properties Sheet
	 */
	public ILabelProvider getLabelProvider() {
		final IItemLabelProvider itemLabelProvider = itemPropertyDescriptor
				.getLabelProvider(object);
		final AtomicSoundComponent ats = (AtomicSoundComponent) object;
		return new LabelProvider() {
			@Override
			public String getText(Object object) {
				List<String> keys = new ArrayList<String>();
				keys.addAll(ats.getFloatProperties().keySet());
				Collections.sort(keys);
				Iterator<String> it = keys.iterator();
				String result = "";
				while (it.hasNext()) {
					String s = it.next();
					Float value = ats.getFloatProperties().get(s);
					result = result + s + ": " + value + "; ";
				}

				return result;
				// return itemLabelProvider.getText(object);
			}

			@Override
			public Image getImage(Object object) {
				return ExtendedImageRegistry.getInstance().getImage(
						itemLabelProvider.getImage(object));
			}
		};
	}

	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		return new ExtendedDialogCellEditor(parent, getLabelProvider()) {

			@Override
			protected Label getDefaultLabel() {
				return super.getDefaultLabel();
			}

			@Override
			protected void updateContents(Object object) {
				super.updateContents(object);
			}

			@Override
			protected Object openDialogBox(Control cellEditorWindow) {
				Dialog dialog = new FloatPropertyInputDialog(PlatformUI
						.getWorkbench().getDisplay().getActiveShell(),
						"dialogTitle", "dialogMessage", "initialValue", null,
						object);
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

class FloatPropertyInputDialog extends Dialog {
	// TODO so bauen, dass für jede Property eine Zeile existiert
	// TODO Funktioniert im Diagramm Editor noch nicht korrekt (ändert nicht alle Werte)
	// TODO Änderungen werden nicht registriert (kein speichern möglich)
	// Funktioniert aber wenn man noch was anderes ändert

	Object atomicSoundComponentObject;
	Map<String, Text> inputTexts;

	public FloatPropertyInputDialog(Shell parentShell, String dialogTitle,
			String dialogMessage, String initialValue,
			IInputValidator validator, Object object) {
		super(parentShell);
		this.atomicSoundComponentObject = object;
		this.inputTexts = new HashMap<String, Text>();

	}

	@Override
	/**
	 * Builds the component's input dialog
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		Set<String> keys = getAtomicSoundComponent().getFloatProperties()
				.keySet();
		List<String> keysSorted = new ArrayList<String>();
		keysSorted.addAll(keys);
		Collections.sort(keysSorted);
		Iterator<String> it = keysSorted.iterator();
		while (it.hasNext()) {
			String desc = it.next();
			Label label = new Label(composite, SWT.WRAP);
			label.setText(desc);
			Text text = new Text(composite, SWT.SINGLE | SWT.BORDER);
			text.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
					| GridData.HORIZONTAL_ALIGN_FILL));
			text.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					// TODO validate
				}
			});
			inputTexts.put(desc, text);
		}

		applyDialogFont(composite);
		return composite;
	}

	@Override
	protected void okPressed() {
		try {
			Set<String> keys = getAtomicSoundComponent().getFloatProperties()
					.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String desc = it.next();
				System.out.println(desc);
				Float value = Float.parseFloat(inputTexts.get(desc).getText());
				getAtomicSoundComponent().getFloatProperties().put(desc, value); //TODO hier wird eine exception im Diagram Editor geworfen
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.okPressed();
	}

	private AtomicSoundComponent getAtomicSoundComponent() {
		return (AtomicSoundComponent) atomicSoundComponentObject;
	}

}