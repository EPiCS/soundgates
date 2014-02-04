package soundgates.diagram.edit.parts;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.PlatformUI;

import soundgates.Direction;
import soundgates.impl.AtomicSoundComponentImpl;
import soundgates.provider.custom.CustomPropertyInputDialog;

public abstract class AtomicSoundComponentAbstractEditPart extends AbstractBorderedShapeEditPart {

	private Adapter adapter;
	
	protected int[] inputPortsXPositions; 
	protected int[] outputPortsXPositions;
	protected int componentWidth;
	
	protected int currentInputPort=0;
	protected int currentOutputPort=0;
	
	public AtomicSoundComponentAbstractEditPart(View view) {
		super(view);
		
		ComponentLayouter componentLayouter = new ComponentLayouter(getAtomicSoundComponentImpl());
		inputPortsXPositions = componentLayouter.getInputPortsXPositions();
		outputPortsXPositions = componentLayouter.getOutputPortsXPositions();
		componentWidth = componentLayouter.getComponentWidth();
	}

	public AtomicSoundComponentImpl getAtomicSoundComponentImpl() {
		if (((org.eclipse.gmf.runtime.notation.Shape) getModel()).getElement() instanceof AtomicSoundComponentImpl)
			return (AtomicSoundComponentImpl) ((org.eclipse.gmf.runtime.notation.Shape) getModel()).getElement();
		else 
			return null;
	}

	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof PortEditPart) {
			
			SoundgatesBorderItemLocator locator;
			
			if (((PortEditPart) childEditPart).getPortImpl().getDirection()==Direction.IN){
				locator = new SoundgatesBorderItemLocator(getMainFigure(),PositionConstants.NORTH, inputPortsXPositions[currentInputPort], ((PortEditPart) childEditPart).getPortImpl().getName());
				currentInputPort++;
			}
			else{
				locator = new SoundgatesBorderItemLocator(getMainFigure(),PositionConstants.SOUTH, outputPortsXPositions[currentOutputPort],  ((PortEditPart) childEditPart).getPortImpl().getName());
				currentOutputPort++;
			}
			getBorderedFigure().getBorderItemContainer().add(((PortEditPart) childEditPart).getFigure(),locator);

			return true;
		}
		return false;
	}
	
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = 
				new DefaultSizeNodeFigure(componentWidth, ComponentLayouter.componentHeight);
		return result;
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
