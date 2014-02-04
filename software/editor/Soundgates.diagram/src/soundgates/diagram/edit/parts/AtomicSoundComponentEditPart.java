package soundgates.diagram.edit.parts;

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IBorderItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.BorderItemSelectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemLocator;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.edit.policies.reparent.CreationEditPolicyWithCustomReparent;
import org.eclipse.swt.graphics.Color;

import soundgates.Direction;
import soundgates.Port;
import soundgates.diagram.edit.policies.AtomicSoundComponentCanonicalEditPolicy;
import soundgates.diagram.edit.policies.AtomicSoundComponentItemSemanticEditPolicy;
import soundgates.diagram.part.SoundgatesVisualIDRegistry;

/**
 * @generated
 */
public class AtomicSoundComponentEditPart extends AtomicSoundComponentAbstractEditPart{

	private static final int portSize = 10;
	private static final int distanceFromBorder = 10;
	private static final int distanceBetweenPorts = 90;
	
	private int maxPortsNumber;
	private int[] inputPortsXPositions; 
	private int[] outputPortsXPositions;
	private int currentInputPort=0;
	private int currentOutputPort=0;
	
	
	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 2001;

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
	public AtomicSoundComponentEditPart(View view) {
		super(view);
		computePortsXPositions();
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		installEditPolicy(EditPolicyRoles.CREATION_ROLE,
				new CreationEditPolicyWithCustomReparent(
						SoundgatesVisualIDRegistry.TYPED_INSTANCE));
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new AtomicSoundComponentItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
				new DragDropEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
				new AtomicSoundComponentCanonicalEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {

		FlowLayoutEditPolicy lep = new FlowLayoutEditPolicy() {

			protected EditPolicy createChildEditPolicy(EditPart child) {
				View childView = (View) child.getModel();
				switch (SoundgatesVisualIDRegistry.getVisualID(childView)) {
				case PortEditPart.VISUAL_ID:
					return new BorderItemSelectionEditPolicy();
				}
				return super.createChildEditPolicy(child);
			}

			protected Command createAddCommand(EditPart child, EditPart after) {
				return null;
			}

			protected Command createMoveChildCommand(EditPart child,
					EditPart after) {
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
		return primaryShape = new AtomicSoundComponentFigure();
	}

	/**
	 * @generated
	 */
	public AtomicSoundComponentFigure getPrimaryShape() {
		return (AtomicSoundComponentFigure) primaryShape;
	}

	/**
	 * @generated NOT
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof AtomicSoundComponentNameEditPart) {
			((AtomicSoundComponentNameEditPart) childEditPart)
					.setLabel(getPrimaryShape()
							.getFigureAtomicSoundComponentNameFigure());
			return true;
		}
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
	
//	public int computeXpos(){
//		
//	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {
//		if (childEditPart instanceof AtomicSoundComponentNameEditPart) {
//			return true;
//		}
//		if (childEditPart instanceof PortEditPart) {
//			getBorderedFigure().getBorderItemContainer().remove(
//					((PortEditPart) childEditPart).getFigure());
//			return true;
//		}
		return false;
	}

	/**
	 * @generated
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * @generated
	 */
	protected void removeChildVisual(EditPart childEditPart) {
//		if (removeFixedChild(childEditPart)) {
//			return;
//		}
//		super.removeChildVisual(childEditPart);
	}

	/**
	 * @generated
	 */
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {
		if (editPart instanceof IBorderItemEditPart) {
			return getBorderedFigure().getBorderItemContainer();
		}
		return getContentPane();
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(computeWidth(), 40);
		return result;
	}
	
	protected int computeWidth(){
		return 2*distanceFromBorder + maxPortsNumber*portSize + (maxPortsNumber-1)*distanceBetweenPorts; 
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

	/**
	 * Default implementation treats passed figure as content pane.
	 * Respects layout one may have set for generated figure.
	 * @param nodeShape instance of generated figure class
	 * @generated
	 */
	protected IFigure setupContentPane(IFigure nodeShape) {
		if (nodeShape.getLayoutManager() == null) {
			ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
			layout.setSpacing(5);
			nodeShape.setLayoutManager(layout);
		}
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
				.getType(AtomicSoundComponentNameEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	public class AtomicSoundComponentFigure extends RectangleFigure {

		/**
		 * @generated
		 */
		private WrappingLabel fFigureAtomicSoundComponentNameFigure;

		/**
		 * @generated
		 */
		public AtomicSoundComponentFigure() {

			FlowLayout layoutThis = new FlowLayout();
			layoutThis.setStretchMinorAxis(false);
			layoutThis.setMinorAlignment(FlowLayout.ALIGN_LEFTTOP);

			layoutThis.setMajorAlignment(FlowLayout.ALIGN_LEFTTOP);
			layoutThis.setMajorSpacing(5);
			layoutThis.setMinorSpacing(5);
			layoutThis.setHorizontal(true);

			this.setLayoutManager(layoutThis);

			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fFigureAtomicSoundComponentNameFigure = new WrappingLabel();

			fFigureAtomicSoundComponentNameFigure
					.setText("Atomic Sound Component");

			this.add(fFigureAtomicSoundComponentNameFigure);

		}

		/**
		 * @generated
		 */
		public WrappingLabel getFigureAtomicSoundComponentNameFigure() {
			return fFigureAtomicSoundComponentNameFigure;
		}

	}
	
	public void computePortsXPositions(){
		int inputPorts = 0;
		int outputPorts = 0;
		char sideWithMorePorts;
		for (Port port : getAtomicSoundComponentImpl().getPorts()){
			if(port.getDirection()==Direction.IN)
				inputPorts++;
			else 
				outputPorts++;
		}
		if(inputPorts>outputPorts){
			maxPortsNumber = inputPorts;
			sideWithMorePorts='i';
		}
		else if (inputPorts<outputPorts){
			maxPortsNumber = outputPorts;
			sideWithMorePorts='o';
		}
		else {
			maxPortsNumber = inputPorts;
			sideWithMorePorts = 'b';
		}
		
		inputPortsXPositions = new int[inputPorts];
		outputPortsXPositions = new int[outputPorts];
		
		int portWithDistance = portSize + distanceBetweenPorts;
		
		int tmpPos = distanceFromBorder;
		if(sideWithMorePorts=='i'){
			for(int i=0; i<inputPorts; i++){
				inputPortsXPositions[i]=tmpPos;
				tmpPos += portWithDistance;
			}
			
			int emptyCellsAtStart = (inputPorts-outputPorts)/2;
			tmpPos = distanceFromBorder + portWithDistance*emptyCellsAtStart;
			for(int i=0; i<outputPorts; i++){
				outputPortsXPositions[i]=tmpPos;
				tmpPos += portWithDistance;
			}
		}
		else if(sideWithMorePorts=='o'){
			for(int i=0; i<outputPorts; i++){
				outputPortsXPositions[i]=tmpPos;
				tmpPos += portWithDistance;
			}
			
			int emptyCellsAtStart = (outputPorts-inputPorts)/2;
			tmpPos = distanceFromBorder + portWithDistance*emptyCellsAtStart;
			for(int i=0; i<inputPorts; i++){
				inputPortsXPositions[i]=tmpPos;
				tmpPos += portWithDistance;
			}
		}else if(sideWithMorePorts=='b'){
			for(int i=0; i<outputPorts; i++){
				outputPortsXPositions[i]=tmpPos;
				inputPortsXPositions[i]=tmpPos;
				tmpPos += portWithDistance;
			}			
		}
	}
	
	class SoundgatesBorderItemLocator extends BorderItemLocator{
		
		private boolean secondStart = false;
		private int offset;
		
		String name;
		
		public SoundgatesBorderItemLocator(IFigure parentFigure,
				int preferredSide, int offset, String name) {
			super(parentFigure, preferredSide);
			this.offset=offset;
			this.name=name;
		}
		
		protected Point getPreferredLocation(int side, IFigure borderItem) {
			Rectangle bounds = getParentBorder();
			int parentFigureWidth = bounds.width;
			int parentFigureHeight = bounds.height;
			int parentFigureX = bounds.x;
			int parentFigureY = bounds.y;
			int x = parentFigureX;
			int y = parentFigureY;

			Dimension borderItemSize = getSize(borderItem);

			if (side == PositionConstants.WEST) {
				x = parentFigureX - borderItemSize.width
					+ getBorderItemOffset().width;
				y += parentFigureHeight / 2;
			} else if (side == PositionConstants.EAST) {
				x = parentFigureX + parentFigureWidth - getBorderItemOffset().width;
				y += parentFigureHeight / 2;
			} else if (side == PositionConstants.NORTH) {
				y = parentFigureY - borderItemSize.height;
//				x += parentFigureWidth/2;
				x = parentFigureX + offset;
			} else if (side == PositionConstants.SOUTH) {
//				x += parentFigureWidth/2;
				x = parentFigureX + offset;
				y = parentFigureY + parentFigureHeight;
					
			}
			return new Point(x, y);
		}
		
		@Override
		protected Point getPreferredLocation(IFigure borderItem) {
			getParentFigure().validate();
			if(getParentFigure().getBounds().x!=0 && getParentFigure().getBounds().y!=0){
				if(!secondStart){
					secondStart=true;
					setConstraint(new Rectangle(0,0,0,0));
					relocate(borderItem);
				}
			}
			Point p = super.getPreferredLocation(borderItem);
			return p;
		}

		@Override
		public void relocate(IFigure borderItem) {
		System.out.println(name);
			super.relocate(borderItem);
		}
		
	}
}
