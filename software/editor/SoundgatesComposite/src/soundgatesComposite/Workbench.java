/**
 */
package soundgatesComposite;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Workbench</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link soundgatesComposite.Workbench#getCompositeSoundComponents <em>Composite Sound Components</em>}</li>
 * </ul>
 * </p>
 *
 * @see soundgatesComposite.SoundgatesCompositePackage#getWorkbench()
 * @model
 * @generated
 */
public interface Workbench extends EObject {
	/**
	 * Returns the value of the '<em><b>Composite Sound Components</b></em>' containment reference list.
	 * The list contents are of type {@link soundgatesComposite.CompositeSoundComponent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Composite Sound Components</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Composite Sound Components</em>' containment reference list.
	 * @see soundgatesComposite.SoundgatesCompositePackage#getWorkbench_CompositeSoundComponents()
	 * @model containment="true"
	 * @generated
	 */
	EList<CompositeSoundComponent> getCompositeSoundComponents();

} // Workbench
