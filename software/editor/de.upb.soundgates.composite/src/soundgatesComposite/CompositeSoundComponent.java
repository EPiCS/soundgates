/**
 */
package soundgatesComposite;

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
 *   <li>{@link soundgatesComposite.CompositeSoundComponent#getEmbeddedComponents <em>Embedded Components</em>}</li>
 *   <li>{@link soundgatesComposite.CompositeSoundComponent#getLinks <em>Links</em>}</li>
 *   <li>{@link soundgatesComposite.CompositeSoundComponent#getDelegations <em>Delegations</em>}</li>
 * </ul>
 * </p>
 *
 * @see soundgatesComposite.SoundgatesCompositePackage#getCompositeSoundComponent()
 * @model
 * @generated
 */
public interface CompositeSoundComponent extends SoundComponent {
	/**
	 * Returns the value of the '<em><b>Embedded Components</b></em>' containment reference list.
	 * The list contents are of type {@link soundgatesComposite.SoundComponent}.
	 * It is bidirectional and its opposite is '{@link soundgatesComposite.SoundComponent#getParentComponent <em>Parent Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Embedded Components</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Embedded Components</em>' containment reference list.
	 * @see soundgatesComposite.SoundgatesCompositePackage#getCompositeSoundComponent_EmbeddedComponents()
	 * @see soundgatesComposite.SoundComponent#getParentComponent
	 * @model opposite="parentComponent" containment="true"
	 * @generated
	 */
	EList<SoundComponent> getEmbeddedComponents();

	/**
	 * Returns the value of the '<em><b>Links</b></em>' containment reference list.
	 * The list contents are of type {@link soundgatesComposite.Link}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Links</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Links</em>' containment reference list.
	 * @see soundgatesComposite.SoundgatesCompositePackage#getCompositeSoundComponent_Links()
	 * @model containment="true"
	 * @generated
	 */
	EList<Link> getLinks();

	/**
	 * Returns the value of the '<em><b>Delegations</b></em>' containment reference list.
	 * The list contents are of type {@link soundgatesComposite.Delegation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Delegations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Delegations</em>' containment reference list.
	 * @see soundgatesComposite.SoundgatesCompositePackage#getCompositeSoundComponent_Delegations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Delegation> getDelegations();

} // CompositeSoundComponent
