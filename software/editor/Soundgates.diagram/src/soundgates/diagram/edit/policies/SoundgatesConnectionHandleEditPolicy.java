package soundgates.diagram.edit.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConnectionHandleEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.handles.ConnectionHandle;
import org.eclipse.gmf.runtime.diagram.ui.handles.ConnectionHandle.HandleDirection;

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
		
		
		if(port.getDirection()==Direction.IN){
			tooltip = buildTooltip(HandleDirection.INCOMING);
			if (tooltip != null) {
				list.add(new ConnectionHandle((IGraphicalEditPart) getHost(),
					HandleDirection.INCOMING, tooltip));
			}
		}else{	
			tooltip = buildTooltip(HandleDirection.OUTGOING);
			if (tooltip != null) {
				list.add(new ConnectionHandle((IGraphicalEditPart) getHost(),
					HandleDirection.OUTGOING, tooltip));
			}
		}

		return list;
	}
}
