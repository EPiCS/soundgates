package soundgates.diagram.edit.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemLocator;

public class SoundgatesBorderItemLocator extends BorderItemLocator{	
	
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
			x = parentFigureX + offset;
		} else if (side == PositionConstants.SOUTH) {
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

}
