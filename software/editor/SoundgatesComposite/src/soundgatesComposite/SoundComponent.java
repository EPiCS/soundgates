/**
 */
package soundgatesComposite;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Sound Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Sound-component
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link soundgatesComposite.SoundComponent#getPorts <em>Ports</em>}</li>
 *   <li>{@link soundgatesComposite.SoundComponent#getParentComponent <em>Parent Component</em>}</li>
 * </ul>
 * </p>
 *
 * @see soundgatesComposite.SoundgatesCompositePackage#getSoundComponent()
 * @model abstract="true"
 * @generated
 */
public interface SoundComponent extends NamedElement, Element {
	/**
	 * Returns the value of the '<em><b>Ports</b></em>' containment reference list.
	 * The list contents are of type {@link soundgatesComposite.Port}.
	 * It is bidirectional and its opposite is '{@link soundgatesComposite.Port#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Ports
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Ports</em>' containment reference list.
	 * @see soundgatesComposite.SoundgatesCompositePackage#getSoundComponent_Ports()
	 * @see soundgatesComposite.Port#getComponent
	 * @model opposite="component" containment="true"
	 * @generated
	 */
	EList<Port> getPorts();

	/**
	 * Returns the value of the '<em><b>Parent Component</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link soundgatesComposite.CompositeSoundComponent#getEmbeddedComponents <em>Embedded Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Component</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Component</em>' container reference.
	 * @see #setParentComponent(CompositeSoundComponent)
	 * @see soundgatesComposite.SoundgatesCompositePackage#getSoundComponent_ParentComponent()
	 * @see soundgatesComposite.CompositeSoundComponent#getEmbeddedComponents
	 * @model opposite="embeddedComponents" transient="false"
	 * @generated
	 */
	CompositeSoundComponent getParentComponent();

	/**
	 * Sets the value of the '{@link soundgatesComposite.SoundComponent#getParentComponent <em>Parent Component</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Component</em>' container reference.
	 * @see #getParentComponent()
	 * @generated
	 */
	void setParentComponent(CompositeSoundComponent value);

} // SoundComponent
