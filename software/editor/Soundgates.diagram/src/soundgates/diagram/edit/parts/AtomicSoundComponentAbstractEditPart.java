package soundgates.diagram.edit.parts;

import org.eclipse.draw2d.Label;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.PlatformUI;

import soundgates.impl.AtomicSoundComponentImpl;
import soundgates.provider.custom.CustomPropertyInputDialog;

public abstract class AtomicSoundComponentAbstractEditPart extends AbstractBorderedShapeEditPart {

	private Adapter adapter;
	
	public AtomicSoundComponentAbstractEditPart(View view) {
		super(view);
	}

	public AtomicSoundComponentImpl getAtomicSoundComponentImpl() {
		if (((org.eclipse.gmf.runtime.notation.Shape) getModel()).getElement() instanceof AtomicSoundComponentImpl)
			return (AtomicSoundComponentImpl) ((org.eclipse.gmf.runtime.notation.Shape) getModel()).getElement();
		else 
			return null;
	}
	
	public String createToolTip(){
		AtomicSoundComponentImpl atomicSoundComponentImpl = getAtomicSoundComponentImpl();
		
		StringBuffer stringBuffer = new StringBuffer();
		for(String key : atomicSoundComponentImpl.getStringProperties().keySet()){
			if(key.equals("implType")){
				String implType;
				if(atomicSoundComponentImpl.getStringProperties().get(key).equals("hw"))
					implType = "Hardware ";
				else
					implType = "Software ";
				
				stringBuffer.append("Implementation"+": "+implType+"\n");
			}
		}
		for(String key : atomicSoundComponentImpl.getFloatProperties().keySet()){
			stringBuffer.append(key+": "+atomicSoundComponentImpl.getFloatProperties().get(key)+"\n");
		}
		for(String key : atomicSoundComponentImpl.getUserStringProperties().keySet()){
			stringBuffer.append(key+": "+atomicSoundComponentImpl.getUserStringProperties().get(key)+"\n");
		}
		
		return stringBuffer.toString();
	}
	
	@Override
	public void activate() {
		super.activate();
		adapter = new Adapter() {

			@Override
			public void setTarget(Notifier newTarget) {

			}

			@Override
			public void notifyChanged(Notification notification) {
				refreshGraphic();
			}

			@Override
			public boolean isAdapterForType(Object type) {
				return false;
			}

			@Override
			public Notifier getTarget() {
				return null;
			}
		};

		try{
			getAtomicSoundComponentImpl().eAdapters().add(adapter);
			refreshGraphic();
		}
		catch(Exception e){
			
		}
	}

	public void refreshGraphic() {
		try{
			figure.setToolTip(new Label(createToolTip()));
			
		}
		catch(Exception e){
			
		}

	}
	
	@Override
	public void deactivate() {
		super.deactivate();
		try{
			getAtomicSoundComponentImpl().eAdapters().remove(adapter);
			refreshGraphic();
		}
		catch(Exception e){
			
		}
	}

	
	@Override
	public void performRequest(Request req) {
	    if(req.getType() == RequestConstants.REQ_OPEN) {
			Dialog dialog = new CustomPropertyInputDialog(PlatformUI
					.getWorkbench().getDisplay().getActiveShell(),
					"dialogTitle", "dialogMessage", "initialValue", null,
					getAtomicSoundComponentImpl());
			dialog.open();
	    }
	}
	
}
