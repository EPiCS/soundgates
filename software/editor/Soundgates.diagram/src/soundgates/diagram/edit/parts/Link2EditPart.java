package soundgates.diagram.edit.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.View;

import soundgates.diagram.edit.policies.Link2ItemSemanticEditPolicy;

/**
 * @generated
 */
public class Link2EditPart extends ConnectionAbstractEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 4002;

	/**
	 * @generated
	 */
	public Link2EditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
				new Link2ItemSemanticEditPolicy());
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */

	protected Connection createConnectionFigure() {
		return new CompositeSoundComponentLinksFigure();
	}

	/**
	 * @generated
	 */
	public CompositeSoundComponentLinksFigure getPrimaryShape() {
		return (CompositeSoundComponentLinksFigure) getFigure();
	}

	/**
	 * @generated
	 */
	public class CompositeSoundComponentLinksFigure extends
			PolylineConnectionEx {

		/**
		 * @generated
		 */
		public CompositeSoundComponentLinksFigure() {

			setTargetDecoration(createTargetDecoration());
		}

		/**
		 * @generated
		 */
		private RotatableDecoration createTargetDecoration() {
			PolylineDecoration df = new PolylineDecoration();
			return df;
		}

	}

}
