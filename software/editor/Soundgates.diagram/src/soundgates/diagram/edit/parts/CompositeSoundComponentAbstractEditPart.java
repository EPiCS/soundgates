package soundgates.diagram.edit.parts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.swt.graphics.Color;

import soundgates.CompositeSoundComponent;
import soundgates.diagram.part.SoundgatesDiagramUpdater;
import soundgates.impl.PatchImpl;

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
			 EObject container = (PatchImpl) ((ShapeImpl)getModel()).getElement().eContainer();
			 SoundgatesDiagramUpdater.refreshContainer(container);
		 }
		 catch(Exception e){
			 
		 }
	}
	
	@Override
	public void activate() {
		super.activate();
		refreshContainer();
		setForegroundColor(new Color(null,0,0,0));
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