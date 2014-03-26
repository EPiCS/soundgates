package soundgates.provider.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import soundgates.AtomicSoundComponent;

public class CustomPropertyInputDialog extends Dialog {

	Object atomicSoundComponentObject;
	Map<String, Text> inputTexts;
	Map<String, List<Button>> optionButtons;
	Map<String, String> errorMessages = new HashMap<String, String>();

	public CustomPropertyInputDialog(Shell parentShell, String dialogTitle,
			String dialogMessage, String initialValue,
			IInputValidator validator, Object object) {
		super(parentShell);
		this.atomicSoundComponentObject = object;
		this.inputTexts = new HashMap<String, Text>();
		this.optionButtons = new HashMap<String, List<Button>>();
	}

	@Override
	/**
	 * Builds the component's input dialog
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		List<String> keysSorted = new ArrayList<String>();
		keysSorted.addAll(getAtomicSoundComponent().getIntegerProperties()
				.keySet());
		keysSorted.addAll(getAtomicSoundComponent().getFloatProperties()
				.keySet());
		keysSorted.addAll(getAtomicSoundComponent().getUserStringProperties()
				.keySet());

//		Collections.sort(keysSorted);
		Iterator<String> it = keysSorted.iterator();
		
		while (it.hasNext()) {
			final String key = it.next();
			String desc = key;
			final boolean f = getAtomicSoundComponent().getFloatProperties()
					.containsKey(key);
			final boolean i = getAtomicSoundComponent().getIntegerProperties()
					.containsKey(key);
			final boolean s = getAtomicSoundComponent().getUserStringProperties()
					.containsKey(key);

			Label label = new Label(composite, SWT.WRAP);
			
			if (s) {
				
				// has the property options?				
				if(getAtomicSoundComponent().getStringProperties().containsKey(key+"Options")){
					
					String possibleOptionsString = getAtomicSoundComponent().getStringProperties().get(key+"Options");	
					String[] possibleOptions = possibleOptionsString.split("\\|");						
					
					Composite implRadioGroup = (Composite) super.createDialogArea(composite);
					implRadioGroup.setLayout(new RowLayout());					
										
					List<Button> buttons = new LinkedList<Button>();
					for(String option : possibleOptions){
						Button optionButton = new Button(implRadioGroup, SWT.RADIO);
						optionButton.setText(option);						
						if (getAtomicSoundComponent().getUserStringProperties().get(key).equals(option)) {
							optionButton.setSelection(true);
						}
						buttons.add(optionButton);
					}
					optionButtons.put(key, buttons);
				}
				else //the user can type the value of the property
				{
					Text text = new Text(composite, SWT.SINGLE | SWT.BORDER);

					text.setText(getAtomicSoundComponent().getUserStringProperties()
							.get(key).toString());
					desc = desc + " (String)";
					text.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
							| GridData.HORIZONTAL_ALIGN_FILL));

					inputTexts.put(key, text);
				}
			}
			if(f) {
				Text text = new Text(composite, SWT.SINGLE | SWT.BORDER);

					text.setText(getAtomicSoundComponent().getFloatProperties()
							.get(key).toString());
					desc = desc + " (Float)";
					text.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
							| GridData.HORIZONTAL_ALIGN_FILL));
					text.addModifyListener(new ModifyListener() {
						public void modifyText(ModifyEvent e) {
							validateInput(key, i, f);

						}
					});
					inputTexts.put(key, text);
			}			
			
			label.setText(desc);
		}

		applyDialogFont(composite);
		return composite;
	}

	@Override
	protected void okPressed() {
		
		ChangeCommand changeCommand = new ChangeCommand(getAtomicSoundComponent()){

			@Override
			protected void doExecute() {
				// TODO Auto-generated method stub
				
			
		
			List<String> keys = new ArrayList<String>();
			keys.addAll(getAtomicSoundComponent().getIntegerProperties()
					.keySet());
			keys.addAll(getAtomicSoundComponent().getFloatProperties().
					keySet());
			keys.addAll(getAtomicSoundComponent().getUserStringProperties().
					keySet());
			Iterator<String> it = keys.iterator();
				
			while (it.hasNext()) {			
					
			
					// TODO hier wird eine exception im Diagram Editor
					// geworfen.
					// Funktioniert durch Abfangen der Exception so, ist
					// aber
					// ein dreckicker Hack. Man sollte hier ein GMF Command
					// absetzen
					String desc = it.next();
					
						if (getAtomicSoundComponent().getFloatProperties()
								.containsKey(desc)) {
							Float value = Float.parseFloat(inputTexts.get(desc)
									.getText());
							getAtomicSoundComponent().getFloatProperties().put(
									desc, value);
						}
						
						if (getAtomicSoundComponent().getUserStringProperties()
								.containsKey(desc)) {
							String value = "";
							if(inputTexts.containsKey(desc))
								value = inputTexts.get(desc).getText();
							else if(optionButtons.containsKey(desc)){
								List<Button> buttons = optionButtons.get(desc);
								for(Button tmpButton : buttons){
									if(tmpButton.getSelection()){
										value = tmpButton.getText();
										break;
									}
								}
							}
							getAtomicSoundComponent().getUserStringProperties().put(
									desc, value);
						}				
				}
				
			}	
			
		};

		EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(getAtomicSoundComponent());
		editingDomain.getCommandStack().execute(changeCommand);
		
		super.okPressed();
	}

	protected void validateInput(String key, boolean i, boolean f) {
		String input = inputTexts.get(key).getText();
		try {
			if (i) {
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