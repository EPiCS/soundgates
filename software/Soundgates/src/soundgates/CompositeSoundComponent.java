/**
 */
package soundgates;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composite Sound Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Composite sound-component
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link soundgates.CompositeSoundComponent#getEmbeddedComponents <em>Embedded Components</em>}</li>
 *   <li>{@link soundgates.CompositeSoundComponent#getLinks <em>Links</em>}</li>
 * </ul>
 * </p>
 *
 * @see soundgates.SoundgatesPackage#getCompositeSoundComponent()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='RightLinksOnPorts'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL RightLinksOnPorts='(self.inputPorts->forAll(if (not (outgoingLink=null)) then if (outgoingLink.parentComponent=self) then true else false endif else true endif)) and \r\n(self.outputPorts->forAll(if (not (incomingLink=null)) then if (incomingLink.parentComponent=self) then true else false endif else true endif))'"
 * @generated
 */
public interface CompositeSoundComponent extends SoundComponent {
	/**
	 * Returns the value of the '<em><b>Embedded Components</b></em>' containment reference list.
	 * The list contents are of type {@link soundgates.SoundComponent}.
	 * It is bidirectional and its opposite is '{@link soundgates.SoundComponent#getParentComponent <em>Parent Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Embedded Components</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Embedded Components</em>' containment reference list.
	 * @see soundgates.SoundgatesPackage#getCompositeSoundComponent_EmbeddedComponents()
	 * @see soundgates.SoundComponent#getParentComponent
	 * @model opposite="parentComponent" containment="true"
	 * @generated
	 */
	EList<SoundComponent> getEmbeddedComponents();

	/**
	 * Returns the value of the '<em><b>Links</b></em>' containment reference list.
	 * The list contents are of type {@link soundgates.Link}.
	 * It is bidirectional and its opposite is '{@link soundgates.Link#getParentComponent <em>Parent Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Links</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Links</em>' containment reference list.
	 * @see soundgates.SoundgatesPackage#getCompositeSoundComponent_Links()
	 * @see soundgates.Link#getParentComponent
	 * @model opposite="parentComponent" containment="true"
	 * @generated
	 */
	EList<Link> getLinks();

} // CompositeSoundComponent
