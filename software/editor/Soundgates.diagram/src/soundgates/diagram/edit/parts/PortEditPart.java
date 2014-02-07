package soundgates.diagram.edit.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.BorderedBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.BorderItemSelectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemLocator;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.graphics.Color;

import soundgates.Direction;
import soundgates.Port;
import soundgates.diagram.edit.policies.PortItemSemanticEditPolicy;
import soundgates.diagram.edit.policies.SoundgatesConnectionHandleEditPolicy;
import soundgates.diagram.part.SoundgatesVisualIDRegistry;
import soundgates.diagram.providers.SoundgatesElementTypes;

/**
 * @generated
 */
public class PortEditPart extends BorderedBorderItemEditPart {

	private Adapter adapter;
	
	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3001;

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated
	 */
	public PortEditPart(View view) {
		super(view);
	}

	/**
	 * @generated NOT
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE,
				getPrimaryDragEditPolicy());
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new PortItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
		installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE, 
				new SoundgatesConnectionHandleEditPolicy(getPort()));
		
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {
		org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy lep = new org.eclipse.gmf.runtime.diagram.ui.editpolicies.LayoutEditPolicy() {

			protected EditPolicy createChildEditPolicy(EditPart child) {
				View childView = (View) child.getModel();
				switch (SoundgatesVisualIDRegistry.getVisualID(childView)) {
				case PortNameEditPart.VISUAL_ID:
					return new BorderItemSelectionEditPolicy() {

						protected List createSelectionHandles() {
							MoveHandle mh = new MoveHandle(
									(GraphicalEditPart) getHost());
							mh.setBorder(null);
							return Collections.singletonList(mh);
						}
					};
				}
				EditPolicy result = child
						.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
				if (result == null) {
					result = new NonResizableEditPolicy();
				}
				return result;
			}

			protected Command getMoveChildrenCommand(Request request) {
				return null;
			}

			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}
		};
		return lep;
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		return primaryShape = new PortFigure();
	}

	/**
	 * @generated
	 */
	public PortFigure getPrimaryShape() {
		return (PortFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected void addBorderItem(IFigure borderItemContainer,
			IBorderItemEditPart borderItemEditPart) {
		if (borderItemEditPart instanceof PortNameEditPart) {
//			BorderItemLocator locator = new BorderItemLocator(getMainFigure(),PositionConstants.SOUTH);
//			locator.setBorderItemOffset(new Dimension(-20, -20));
			
			BorderItemLocator locator;
			if(getPort().getDirection()==Direction.IN){
				locator = new BorderItemLocator(getMainFigure(),PositionConstants.NORTH);
				locator.setBorderItemOffset(new Dimension(-20, 20));
			}
			else{
				locator = new BorderItemLocator(getMainFigure(),PositionConstants.SOUTH);
				locator.setBorderItemOffset(new Dimension(-20, -20));
			}
			
			
			borderItemContainer.add(borderItemEditPart.getFigure(), locator);
		} else {
			super.addBorderItem(borderItemContainer, borderItemEditPart);
		}
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(10, 10);

		//FIXME: workaround for #154536
		result.getBounds().setSize(result.getPreferredSize());
		return result;
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated NOT
	 */
	protected NodeFigure createMainFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
		figure.add(shape);
		figure.setToolTip(new Label(createToolTip()));
		contentPane = setupContentPane(shape);
		return figure;
	}
	
	public String createToolTip(){
		Port port = getPort();
		return "Name: "+port.getName()+"\nDirection: "+port.getDirection()+"\nType: "+port.getDataType();
	}
	
	/**
	 * Default implementation treats passed figure as content pane.
	 * Respects layout one may have set for generated figure.
	 * @param nodeShape instance of generated figure class
	 * @generated
	 */
	protected IFigure setupContentPane(IFigure nodeShape) {
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	protected void setForegroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setForegroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	protected void setBackgroundColor(Color color) {
		if (primaryShape != null) {
			primaryShape.setBackgroundColor(color);
		}
	}

	/**
	 * @generated
	 */
	protected void setLineWidth(int width) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineWidth(width);
		}
	}

	/**
	 * @generated
	 */
	protected void setLineType(int style) {
		if (primaryShape instanceof Shape) {
			((Shape) primaryShape).setLineStyle(style);
		}
	}

	/**
	 * @generated
	 */
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(SoundgatesVisualIDRegistry
				.getType(PortNameEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMARelTypesOnSource() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(3);
		types.add(SoundgatesElementTypes.Link_4001);
		types.add(SoundgatesElementTypes.Link_4002);
		types.add(SoundgatesElementTypes.Delegation_4003);
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMARelTypesOnSourceAndTarget(
			IGraphicalEditPart targetEditPart) {
		LinkedList<IElementType> types = new LinkedList<IElementType>();
		if (targetEditPart instanceof soundgates.diagram.edit.parts.PortEditPart) {
			types.add(SoundgatesElementTypes.Link_4001);
		}
		if (targetEditPart instanceof soundgates.diagram.edit.parts.PortEditPart) {
			types.add(SoundgatesElementTypes.Link_4002);
		}
		if (targetEditPart instanceof soundgates.diagram.edit.parts.PortEditPart) {
			types.add(SoundgatesElementTypes.Delegation_4003);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMATypesForTarget(IElementType relationshipType) {
		LinkedList<IElementType> types = new LinkedList<IElementType>();
		if (relationshipType == SoundgatesElementTypes.Link_4001) {
			types.add(SoundgatesElementTypes.Port_3001);
		} else if (relationshipType == SoundgatesElementTypes.Link_4002) {
			types.add(SoundgatesElementTypes.Port_3001);
		} else if (relationshipType == SoundgatesElementTypes.Delegation_4003) {
			types.add(SoundgatesElementTypes.Port_3001);
		}
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMARelTypesOnTarget() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(3);
		types.add(SoundgatesElementTypes.Link_4001);
		types.add(SoundgatesElementTypes.Link_4002);
		types.add(SoundgatesElementTypes.Delegation_4003);
		return types;
	}

	/**
	 * @generated
	 */
	public List<IElementType> getMATypesForSource(IElementType relationshipType) {
		LinkedList<IElementType> types = new LinkedList<IElementType>();
		if (relationshipType == SoundgatesElementTypes.Link_4001) {
			types.add(SoundgatesElementTypes.Port_3001);
		} else if (relationshipType == SoundgatesElementTypes.Link_4002) {
			types.add(SoundgatesElementTypes.Port_3001);
		} else if (relationshipType == SoundgatesElementTypes.Delegation_4003) {
			types.add(SoundgatesElementTypes.Port_3001);
		}
		return types;
	}

	/**
	 * @generated NOT
	 */
	public class PortFigure extends RectangleFigure {

		/**
		 * @generated
		 */
		public PortFigure() {
			this.setFill(false);
		}
		
		@Override
		public IFigure getToolTip() {			
			return new Label(createToolTip());
		}

	}
	
	@Override
	public void notifyChanged(Notification notification) {
		// TODO Auto-generated method stub
		super.notifyChanged(notification);
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
			getPort().eAdapters().add(adapter);
			refreshGraphic();
		}
		catch(Exception e){
			
		}
	}

	public void refreshGraphic() {
		try{
			Port port = getPort();
			
			refreshConnections();
			
			if ((port.getDirection() == Direction.OUT && getPort().getOutgoingConnection().size() == 0)
					|| (port.getDirection() == Direction.IN && getPort().getIncomingConnection() == null)) {
				setForegroundColor(new Color(null, 255, 0, 0));
				getPortNameEditPart().getFigure().setVisible(true);
			} else{
				setForegroundColor(new Color(null, 0, 0, 0));
				getPortNameEditPart().getFigure().setVisible(false);
			}

		}
		catch(Exception e){
			
		}

	}
	
	public void refreshConnections(){
		for(Object editPart : getSourceConnections()){
			if(editPart instanceof ConnectionAbstractEditPart){
				((ConnectionAbstractEditPart) editPart).refreshGraphic();
			}
		}
		for(Object editPart : getTargetConnections()){
			if(editPart instanceof ConnectionAbstractEditPart){
				((ConnectionAbstractEditPart) editPart).refreshGraphic();
			}
		}

	}

	public Port getPort() {
		if (((org.eclipse.gmf.runtime.notation.Shape) getModel()).getElement() instanceof Port)
			return (Port) ((org.eclipse.gmf.runtime.notation.Shape) getModel()).getElement();
		else 
			return null;
	}
	
	public PortNameEditPart getPortNameEditPart(){
		for(Object editPart : getChildren()){
			if ( editPart instanceof PortNameEditPart){
				return (PortNameEditPart) editPart;
			}
		}
		return null;
	}
	
	@Override
	public void deactivate() {
		super.deactivate();
		try{
			getPort().eAdapters().remove(adapter);
			refreshGraphic();
		}
		catch(Exception e){
			
		}
	}
	
	@Override
	public void refresh() {
		super.refresh();
		refreshGraphic();		
	}
}
