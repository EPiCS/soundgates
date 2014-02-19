package soundgates.diagram.edit.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConnectionHandleEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.handles.ConnectionHandle;
import org.eclipse.gmf.runtime.diagram.ui.handles.ConnectionHandle.HandleDirection;

import soundgates.CompositeSoundComponent;
import soundgates.Direction;
import soundgates.Port;

public class SoundgatesConnectionHandleEditPolicy extends ConnectionHandleEditPolicy{
	
	Port port;
	
	public SoundgatesConnectionHandleEditPolicy(Port port){
		this.port = port;
	}
	
	/**
	 * Gets the two connection handle figures to be added to this shape if they
	 * support user gestures.
	 * @return a list of <code>ConnectionHandle</code> objects
	 */
	protected List getHandleFigures() {
		List list = new ArrayList(2);
		
		String tooltip;
		
		// composite
		if(port.getComponent() instanceof CompositeSoundComponent){				
			tooltip = buildTooltip(HandleDirection.OUTGOING);
			if (tooltip != null) {
				list.add(new ConnectionHandle((IGraphicalEditPart) getHost(),
					HandleDirection.OUTGOING, tooltip));
			}
			if(port.getIncomingConnection()==null){
				tooltip = buildTooltip(HandleDirection.INCOMING);
				if (tooltip != null) {
					list.add(new ConnectionHandle((IGraphicalEditPart) getHost(),
						HandleDirection.INCOMING, tooltip));
				}
			}
		}
		//atomic
		else{
			if(port.getDirection()==Direction.IN  && port.getIncomingConnection()==null){
				tooltip = buildTooltip(HandleDirection.INCOMING);
				if (tooltip != null) {
					list.add(new ConnectionHandle((IGraphicalEditPart) getHost(),
						HandleDirection.INCOMING, tooltip));
				}
			}
			else if(port.getDirection()==Direction.OUT){
				tooltip = buildTooltip(HandleDirection.OUTGOING);
				if (tooltip != null) {
					list.add(new ConnectionHandle((IGraphicalEditPart) getHost(),
						HandleDirection.OUTGOING, tooltip));
				}			
			}
		}

		return list;
	}
}
