package soundgates.simulation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;

import soundgates.AtomicSoundComponent;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

public class ControlDialog extends TitleAreaDialog {

	  private List<AtomicSoundComponent> componentsToControl;
	  
	  public ControlDialog(Shell parentShell) {
	    super(parentShell);
	  }
	  OSCPortOut sender;
	  @Override
	  public void create() {
	    super.create();
	    setTitle("Soundgates simulation control");
	    setMessage("Control the used IO-components", IMessageProvider.INFORMATION);
	    try {
			sender = new OSCPortOut(InetAddress.getByName("localhost"),50050);
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

	  public void setIoComponents(List<AtomicSoundComponent> components){
		  componentsToControl = components;
	  }
	  
	private void createIoControl(Composite container) {
		if (componentsToControl != null) {
			for (final AtomicSoundComponent comp : componentsToControl) {
				Label lblCompName = new Label(container, SWT.NONE);
				lblCompName.setText(comp.getName());

				GridData dataComp = new GridData();
				dataComp.grabExcessHorizontalSpace = true;
				dataComp.horizontalAlignment = GridData.FILL;

				final Slider slider = new Slider(container, SWT.NONE);
				int max = (int)(Math.round(comp.getFloatProperties().get("MaxValue") * 10000));
				int min = (int)(Math.round(comp.getFloatProperties().get("MinValue") * 10000));
				float increment = (float)(max - min)/200;
				final int incr = increment <= 1 ? 1 : Math.round(increment);
				slider.setIncrement(incr);
				slider.setMaximum(max);
				slider.setMinimum(min);
				slider.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						slider.getSelection();
						try {
							float value = (float)slider.getSelection() /10000;
							sender.send(new OSCMessage("/" + comp.getName(), new Object[]{value}));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				slider.setLayoutData(dataComp);

			}
		}
	}
	  
	  @Override
	  protected Control createDialogArea(Composite parent) {
	    Composite area = (Composite) super.createDialogArea(parent);
	    Composite container = new Composite(area, SWT.NONE);
	    container.setLayoutData(new GridData(GridData.FILL_BOTH));
	    GridLayout layout = new GridLayout(2, true);
	    container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	    container.setLayout(layout);
	    createIoControl(container);
	    return area;
	  }

	  @Override
	  protected boolean isResizable() {
	    return true;
	  }

}
