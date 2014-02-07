package soundgates.diagram.edit.parts;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.PlatformUI;

import soundgates.AtomicSoundComponent;
import soundgates.Direction;
import soundgates.provider.custom.CustomPropertyInputDialog;

public abstract class AtomicSoundComponentAbstractEditPart extends AbstractBorderedShapeEditPart {
	
	protected int[] inputPortsXPositions; 
	protected int[] outputPortsXPositions;
	protected int componentWidth;
	
	protected int currentInputPort=0;
	protected int currentOutputPort=0;
	
	public AtomicSoundComponentAbstractEditPart(View view) {
		super(view);
		
		ComponentLayouter componentLayouter = new ComponentLayouter(getAtomicSoundComponent());
		inputPortsXPositions = componentLayouter.getInputPortsXPositions();
		outputPortsXPositions = componentLayouter.getOutputPortsXPositions();
		componentWidth = componentLayouter.getComponentWidth();
	}

	public AtomicSoundComponent getAtomicSoundComponent() {
		if (((org.eclipse.gmf.runtime.notation.Node) getModel()).getElement() instanceof AtomicSoundComponent)
			return (AtomicSoundComponent) ((org.eclipse.gmf.runtime.notation.Node) getModel()).getElement();
		else 
			return null;
	}

	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof PortEditPart) {
			
			SoundgatesBorderItemLocator locator;
			
			if (((PortEditPart) childEditPart).getPort().getDirection()==Direction.IN){
				locator = new SoundgatesBorderItemLocator(getMainFigure(),PositionConstants.NORTH, inputPortsXPositions[currentInputPort], ((PortEditPart) childEditPart).getPort().getName());
				currentInputPort++;
			}
			else{
				locator = new SoundgatesBorderItemLocator(getMainFigure(),PositionConstants.SOUTH, outputPortsXPositions[currentOutputPort],  ((PortEditPart) childEditPart).getPort().getName());
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
		AtomicSoundComponent atomicSoundComponent = getAtomicSoundComponent();
		
		StringBuffer stringBuffer = new StringBuffer();
		for(String key : atomicSoundComponent.getStringProperties().keySet()){
			if(key.equals("implType")){
				String implType;
				if(atomicSoundComponent.getStringProperties().get(key).equals("hw"))
					implType = "Hardware ";
				else
					implType = "Software ";
				
				stringBuffer.append("Implementation"+": "+implType+"\n");
			}
		}
		for(String key : atomicSoundComponent.getFloatProperties().keySet()){
			stringBuffer.append(key+": "+atomicSoundComponent.getFloatProperties().get(key)+"\n");
		}
		for(String key : atomicSoundComponent.getUserStringProperties().keySet()){
			stringBuffer.append(key+": "+atomicSoundComponent.getUserStringProperties().get(key)+"\n");
		}
		
		return stringBuffer.toString();
	}
	
	@Override
	public void performRequest(Request req) {
	    if(req.getType() == RequestConstants.REQ_OPEN) {
			Dialog dialog = new CustomPropertyInputDialog(PlatformUI
					.getWorkbench().getDisplay().getActiveShell(),
					"dialogTitle", "dialogMessage", "initialValue", null,
					getAtomicSoundComponent());
			dialog.open();
	    }
	}
	
}
