package soundgates.diagram.edit.parts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;

import soundgates.diagram.part.SoundgatesDiagramUpdater;
import soundgates.impl.PatchImpl;

public abstract class CompositeSoundComponentAbstractEditPart extends
			AbstractBorderedShapeEditPart{

	public CompositeSoundComponentAbstractEditPart(View view) {
		super(view);
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
	
}