package soundgates.provider.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import soundgates.AtomicSoundComponent;

public class CustomPropertyInputDialog extends Dialog {
	// TODO Funktioniert im Diagramm Editor noch nicht korrekt (schmeißt
	// exception)
	// TODO Änderungen werden in EMF nicht registriert (kein speichern möglich)
	// Funktioniert aber wenn man noch was anderes ändert

	Object atomicSoundComponentObject;
	Map<String, Text> inputTexts;
	Map<String, String> errorMessages = new HashMap<String,String>();

	public CustomPropertyInputDialog(Shell parentShell, String dialogTitle,
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

		List<String> keysSorted = new ArrayList<String>();
		keysSorted.addAll(getAtomicSoundComponent().getBooleanProperties()
				.keySet());
		keysSorted.addAll(getAtomicSoundComponent().getIntegerProperties()
				.keySet());
		keysSorted.addAll(getAtomicSoundComponent().getFloatProperties()
				.keySet());

		Collections.sort(keysSorted);
		Iterator<String> it = keysSorted.iterator();
		while (it.hasNext()) {
			final String key = it.next();
			String desc = key;
			final boolean f = getAtomicSoundComponent().getFloatProperties()
					.containsKey(key);
			final boolean b = getAtomicSoundComponent().getBooleanProperties()
					.containsKey(key);
			final boolean i = getAtomicSoundComponent().getIntegerProperties()
					.containsKey(key);

			Label label = new Label(composite, SWT.WRAP);
			Text text = new Text(composite, SWT.SINGLE | SWT.BORDER);

			if (b) {
				text.setText(getAtomicSoundComponent().getBooleanProperties()
						.get(key).toString());
				desc = desc + " (Bool)";
			} else if (i) {
				text.setText(getAtomicSoundComponent().getIntegerProperties()
						.get(key).toString());
				desc = desc + " (Int)";
			} else if (f) {
				text.setText(getAtomicSoundComponent().getFloatProperties()
						.get(key).toString());
				desc = desc + " (Float)";
			}
			label.setText(desc);

			text.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
					| GridData.HORIZONTAL_ALIGN_FILL));
			text.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent e) {
					validateInput(key, b, i, f);

				}
			});
			inputTexts.put(key, text);
		}

		applyDialogFont(composite);
		return composite;
	}

	@Override
	protected void okPressed() {
		try {
			List<String> keys = new ArrayList<String>();
			keys.addAll(getAtomicSoundComponent().getBooleanProperties()
					.keySet());
			keys.addAll(getAtomicSoundComponent().getIntegerProperties()
					.keySet());
			keys.addAll(getAtomicSoundComponent().getFloatProperties().keySet());
			Iterator<String> it = keys.iterator();

			while (it.hasNext()) {
				String desc = it.next();
				try {
					if (getAtomicSoundComponent().getBooleanProperties()
							.containsKey(desc)) {
						Boolean value = Boolean.parseBoolean(inputTexts.get(
								desc).getText());
						getAtomicSoundComponent().getBooleanProperties().put(
								desc, value);
						// TODO hier wird eine exception im Diagram Editor
						// geworfen.
						// Funktioniert durch Abfangen der Exception so, ist
						// aber
						// ein dreckicker Hack. Man sollte hier ein GMF Command
						// absetzen
					}
					if (getAtomicSoundComponent().getIntegerProperties()
							.containsKey(desc)) {
						Integer value = Integer.parseInt(inputTexts.get(desc)
								.getText());
						getAtomicSoundComponent().getIntegerProperties().put(
								desc, value);
					}
					if (getAtomicSoundComponent().getFloatProperties()
							.containsKey(desc)) {
						Float value = Float.parseFloat(inputTexts.get(desc)
								.getText());
						getAtomicSoundComponent().getFloatProperties().put(
								desc, value);
					}
				} catch (IllegalStateException e) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.okPressed();
	}

	protected void validateInput(String key, boolean b, boolean i, boolean f) {
		String input = inputTexts.get(key).getText();
		try {
			if (b) {
				Boolean.parseBoolean(input);
			} else if (i) {
				Integer.parseInt(input);
			} else if (f) {
				Float.parseFloat(input);
			}
			errorMessages.remove(key);
		} catch (NumberFormatException e) {
			errorMessages.put(key, "The value entered for " + key
					+ " is not valid");
		}
		Control button = this.getButton(IDialogConstants.OK_ID);
		if (button != null) {
			button.setEnabled(errorMessages.size() == 0);
		}

	}

	private AtomicSoundComponent getAtomicSoundComponent() {
		return (AtomicSoundComponent) atomicSoundComponentObject;
	}

}