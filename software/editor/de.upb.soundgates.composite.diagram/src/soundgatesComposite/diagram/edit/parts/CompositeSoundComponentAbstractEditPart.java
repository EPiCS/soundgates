package soundgatesComposite.diagram.edit.parts;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;

import soundgatesComposite.diagram.edit.parts.ComponentLayouter;
import soundgatesComposite.Direction;
import soundgatesComposite.diagram.edit.parts.PortEditPart;
import soundgatesComposite.diagram.edit.parts.SoundgatesPortBorderItemLocator;
import soundgatesComposite.CompositeSoundComponent;
import soundgatesComposite.diagram.part.SoundgatesCompositeDiagramUpdater;
import soundgatesComposite.impl.WorkbenchImpl;

public abstract class CompositeSoundComponentAbstractEditPart extends
			AbstractBorderedShapeEditPart{

	protected int[] inputPortsXPositions; 
	protected int[] outputPortsXPositions;
	protected int componentWidth;
	
	protected int currentInputPort=0;
	protected int currentOutputPort=0;
	
	public CompositeSoundComponentAbstractEditPart(View view) {
		super(view);
		
		ComponentLayouter componentLayouter = new ComponentLayouter(getCompositeSoundComponent());
		inputPortsXPositions = componentLayouter.getInputPortsXPositions();
		outputPortsXPositions = componentLayouter.getOutputPortsXPositions();
		componentWidth = componentLayouter.getComponentWidth();
	}
	
	public void refreshContainer(){		 
		 try{
			 EObject container = getWorkbenchImpl();
			 SoundgatesCompositeDiagramUpdater.refreshContainer(container);
		 }
		 catch(Exception e){
			 
		 }
	}
	
	public WorkbenchImpl getWorkbenchImpl(){
		EObject container = ((View)getModel()).getElement().eContainer();
		while(! (container instanceof WorkbenchImpl) ){
			container = container.eContainer();
		}
		return (WorkbenchImpl) container;
	}
	
	@Override
	public void activate() {
		super.activate();
		refreshContainer();
		setForegroundColor(new Color(null,0,0,0));
	}
	
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = 
				new DefaultSizeNodeFigure(componentWidth, ComponentLayouter.componentHeight);
		return result;
	}
	
	public ResizableCompartmentFigure getCompartmentFigure(){
		ResizableCompartmentFigure compartmentFigure = null;
		 
		 for(Object child : getChildren()){
			 if(child instanceof CompositeSoundComponentComponentCompartmentEditPart){
				 CompositeSoundComponentComponentCompartmentEditPart editPart = (CompositeSoundComponentComponentCompartmentEditPart) child;
				 compartmentFigure = editPart.getCompartmentFigure();
				 break;
			 }
			 else if(child instanceof CompositeSoundComponentComponentCompartment2EditPart){
				 CompositeSoundComponentComponentCompartment2EditPart editPart = (CompositeSoundComponentComponentCompartment2EditPart) child;
				 compartmentFigure = editPart.getCompartmentFigure();
				 break;
			 }
		 }
		 return compartmentFigure;
	}
	
	
	public void expandOrCollapseCompartment(){
		ResizableCompartmentFigure compartmentFigure = getCompartmentFigure();
		
		 if(compartmentFigure!=null){
			 if (compartmentFigure.isExpanded()) 
				 collapseCompartment(compartmentFigure);
			 else{
				 expandCompartment(compartmentFigure);
				 refreshContainer();
			 }
		 }
	}
	
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof PortEditPart) {
			
			SoundgatesPortBorderItemLocator locator;
			
			if (((PortEditPart) childEditPart).getPort().getDirection()==Direction.IN){
				locator = new SoundgatesPortBorderItemLocator(getMainFigure(),PositionConstants.NORTH, inputPortsXPositions[currentInputPort], ((PortEditPart) childEditPart).getPort().getName());
				currentInputPort++;
			}
			else{
				locator = new SoundgatesPortBorderItemLocator(getMainFigure(),PositionConstants.SOUTH, outputPortsXPositions[currentOutputPort],  ((PortEditPart) childEditPart).getPort().getName());
				currentOutputPort++;
			}
			getBorderedFigure().getBorderItemContainer().add(((PortEditPart) childEditPart).getFigure(),locator);

			return true;
		}
		return false;
	}
	
	public void expandCompartment(ResizableCompartmentFigure compartmentFigure){
		compartmentFigure.expand();
	}
	
	public void collapseCompartment(ResizableCompartmentFigure compartmentFigure){
		compartmentFigure.collapse();
	}
	
	public CompositeSoundComponent getCompositeSoundComponent() {
		if (((org.eclipse.gmf.runtime.notation.Node) getModel()).getElement() instanceof CompositeSoundComponent)
			return (CompositeSoundComponent) ((org.eclipse.gmf.runtime.notation.Node) getModel()).getElement();
		else 
			return null;
	}
}