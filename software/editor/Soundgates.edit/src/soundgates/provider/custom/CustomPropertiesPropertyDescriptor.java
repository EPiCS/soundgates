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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
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

public class CustomPropertiesPropertyDescriptor extends PropertyDescriptor {

	public CustomPropertiesPropertyDescriptor(Object object,
			IItemPropertyDescriptor itemPropertyDescriptor) {
		super(object, itemPropertyDescriptor);
	}

	@Override
	public String getDisplayName() {
		return super.getDisplayName();

	}

	@Override
	/**
	 * This method is responsible for returning the value text for the custom Properties on the Properties Sheet
	 * It aggregates boolean, integer and float properties into a single entry, so make sure there are no duplicate keys!
	 * Otherwise you might see unexpected behaviour.
	 */
	public ILabelProvider getLabelProvider() {
		final IItemLabelProvider itemLabelProvider = itemPropertyDescriptor
				.getLabelProvider(object);
		final AtomicSoundComponent ats = (AtomicSoundComponent) object;
		return new LabelProvider() {
			@Override
			public String getText(Object object) {
				List<String> keys = new ArrayList<String>();
				keys.addAll(ats.getBooleanProperties().keySet());
				keys.addAll(ats.getIntegerProperties().keySet());
				keys.addAll(ats.getFloatProperties().keySet());
				Collections.sort(keys);
				Iterator<String> it = keys.iterator();
				String result = "";
				while (it.hasNext()) {
					String s = it.next();
					if (ats.getFloatProperties().containsKey(s)) {
						Float value = ats.getFloatProperties().get(s);
						result = result + s + ": " + value + "; ";
					} else if (ats.getBooleanProperties().containsKey(s)) {
						Boolean value = ats.getBooleanProperties().get(s);
						result = result + s + ": " + value + "; ";
					} else if (ats.getIntegerProperties().containsKey(s)) {
						Integer value = ats.getIntegerProperties().get(s);
						result = result + s + ": " + value + "; ";
					}
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
				Dialog dialog = new CustomPropertyInputDialog(PlatformUI
						.getWorkbench().getDisplay().getActiveShell(),
						"dialogTitle", "dialogMessage", "initialValue", null,
						object);
				int result = dialog.open();
				return result == Window.OK ? dialog.getReturnCode() : null;
			}

		};
	}
}