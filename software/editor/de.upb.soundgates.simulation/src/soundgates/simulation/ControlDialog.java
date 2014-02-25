package soundgates.simulation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;

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


		GridData wide = new GridData();
		wide.grabExcessHorizontalSpace = true;
		wide.horizontalAlignment = GridData.FILL;
		
		GridData narrow = new GridData();
		narrow.minimumWidth = 40;
		narrow.widthHint = 70;
		narrow.grabExcessHorizontalSpace = false;
		narrow.horizontalAlignment = GridData.FILL;
		
		if (componentsToControl != null) {
			for (final AtomicSoundComponent comp : componentsToControl) {
				Label lblCompName = new Label(container, SWT.NONE);
				lblCompName.setText(comp.getName());

				final Slider slider = new Slider(container, SWT.NONE);
				final Button pushButton = new Button(container, SWT.PUSH);
				final Button highButton = new Button(container, SWT.PUSH);
				final Button lowButton = new Button(container, SWT.PUSH);
				final Text freeValue = new Text(container, SWT.SINGLE);
				final Button freeButton = new Button(container, SWT.PUSH);
				final Label lastSent = new Label(container, SWT.SINGLE);
				
				final float maximum = comp.getFloatProperties().get("MaxValue");
				final float minimum = comp.getFloatProperties().get("MinValue");
				
				int max = (int)(Math.ceil(maximum * 10000));
				int min = (int)(Math.floor(minimum * 10000));
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
							sender.send(new OSCMessage(comp.getName(), new Object[]{value}));
							lastSent.setText("" + value);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				slider.setLayoutData(wide);
				
				lastSent.setLayoutData(narrow);

				
				pushButton.setLayoutData(narrow);
				pushButton.setText("Push");
				pushButton.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseUp(MouseEvent e) {
						try {
							sender.send(new OSCMessage(comp.getName(), new Object[]{minimum}));
							lastSent.setText("" + minimum);
						} catch (IOException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
					}
					
					@Override
					public void mouseDown(MouseEvent e) {
						try {
							sender.send(new OSCMessage(comp.getName(), new Object[]{maximum}));
							lastSent.setText("" + maximum);
						} catch (IOException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
						}
					}
					
					@Override
					public void mouseDoubleClick(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				
				highButton.setLayoutData(narrow);
				highButton.setText("High (" + comp.getFloatProperties().get("MaxValue") + ")");
				highButton.addListener(SWT.Selection, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						try {
							sender.send(new OSCMessage(comp.getName(), new Object[]{maximum}));
							lastSent.setText("" + maximum);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				
				lowButton.setLayoutData(narrow);
				lowButton.setText("Low (" + comp.getFloatProperties().get("MinValue") + ")");
				lowButton.addListener(SWT.Selection, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						try {
							sender.send(new OSCMessage(comp.getName(), new Object[]{minimum}));
							lastSent.setText("" + minimum);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				
				
				freeButton.setLayoutData(narrow);
				freeValue.setLayoutData(narrow);
				freeValue.addFocusListener(new FocusListener() {
					
					@Override
					public void focusLost(FocusEvent e) {
						try{
							Float.parseFloat(freeValue.getText());
						} catch (NumberFormatException ex) {
							freeValue.setText("NaN");
						}
					}
					
					@Override
					public void focusGained(FocusEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				
				freeButton.setLayoutData(narrow);
				freeButton.setText("Send");
				freeButton.addListener(SWT.Selection, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						try {
							float value = Float.parseFloat(freeValue.getText());
							sender.send(new OSCMessage(comp.getName(), new Object[]{value}));
							lastSent.setText("" + value);
						} catch (NumberFormatException e){
							freeValue.setText("NaN");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				
			}
		}
	}
	  
	  @Override
	  protected Control createDialogArea(Composite parent) {
	    Composite area = (Composite) super.createDialogArea(parent);
	    Composite container = new Composite(area, SWT.NONE);
	    container.setLayoutData(new GridData(GridData.FILL_BOTH));
	    GridLayout layout = new GridLayout(8, true);
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
