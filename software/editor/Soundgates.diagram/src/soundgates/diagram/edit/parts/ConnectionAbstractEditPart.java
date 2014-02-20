package soundgates.diagram.edit.parts;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;

import soundgates.Connection;
import soundgates.DataType;

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
			getConnection().eAdapters().add(adapter);
			refreshGraphic();
		}
		catch(Exception e){
			
		}		
	}

	public void refreshGraphic() {
		try{
			Connection connection = getConnection();
			
			if(connection.getSource().getDataType()==DataType.SOUND){
				setForegroundColor(new Color(null, 0, 0, 0));
			}
			else{
				setForegroundColor(new Color(null, 63, 72, 204));
			}

		}
		catch(Exception e){
			
		}

	}

	public Connection getConnection() {
		if (((org.eclipse.gmf.runtime.notation.Connector) getModel()).getElement() instanceof Connection)
			return (Connection) ((org.eclipse.gmf.runtime.notation.Connector) getModel()).getElement();
		else 
			return null;
	}
	
	@Override
	public void deactivate() {
		super.deactivate();
		try{
			getConnection().eAdapters().remove(adapter);
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
