package soundgates.diagram.edit.parts;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;

import soundgates.DataType;
import soundgates.impl.LinkImpl;

public abstract class ConnectionAbstractEditPart extends ConnectionNodeEditPart 
	implements ITreeBranchEditPart{
	
	Adapter adapter;

	public ConnectionAbstractEditPart(View view) {
		super(view);
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
			getLinkImpl().eAdapters().add(adapter);
			refreshGraphic();
		}
		catch(Exception e){
			
		}		
	}

	public void refreshGraphic() {
		try{
			LinkImpl linkImpl = getLinkImpl();
			
			if(linkImpl.getSource().getDataType()==DataType.SOUND){
				setForegroundColor(new Color(null, 0, 0, 0));
			}
			else{
				setForegroundColor(new Color(null, 63, 72, 204));
			}

		}
		catch(Exception e){
			
		}

	}

	public LinkImpl getLinkImpl() {
		if (((org.eclipse.gmf.runtime.notation.Connector) getModel()).getElement() instanceof LinkImpl)
			return (LinkImpl) ((org.eclipse.gmf.runtime.notation.Connector) getModel()).getElement();
		else 
			return null;
	}
	
	@Override
	public void deactivate() {
		super.deactivate();
		try{
			getLinkImpl().eAdapters().remove(adapter);
			refreshGraphic();
		}
		catch(Exception e){
			
		}
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		super.refresh();
		refreshGraphic();
	}
}
