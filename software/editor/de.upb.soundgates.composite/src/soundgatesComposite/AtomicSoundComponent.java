/**
 */
package soundgatesComposite;

import org.eclipse.emf.common.util.EMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Atomic Sound Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Atomic sound-component
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link soundgatesComposite.AtomicSoundComponent#getFloatProperties <em>Float Properties</em>}</li>
 *   <li>{@link soundgatesComposite.AtomicSoundComponent#getIntegerProperties <em>Integer Properties</em>}</li>
 *   <li>{@link soundgatesComposite.AtomicSoundComponent#getStringProperties <em>String Properties</em>}</li>
 *   <li>{@link soundgatesComposite.AtomicSoundComponent#getUserStringProperties <em>User String Properties</em>}</li>
 *   <li>{@link soundgatesComposite.AtomicSoundComponent#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see soundgatesComposite.SoundgatesCompositePackage#getAtomicSoundComponent()
 * @model
 * @generated
 */
public interface AtomicSoundComponent extends SoundComponent {
	/**
	 * Returns the value of the '<em><b>Float Properties</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.Float},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Float Properties</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Float Properties</em>' map.
	 * @see soundgatesComposite.SoundgatesCompositePackage#getAtomicSoundComponent_FloatProperties()
	 * @model mapType="soundgatesComposite.EStringToEFloatObject<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EFloatObject>"
	 * @generated
	 */
	EMap<String, Float> getFloatProperties();

	/**
	 * Returns the value of the '<em><b>Integer Properties</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.Integer},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Integer Properties</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Integer Properties</em>' map.
	 * @see soundgatesComposite.SoundgatesCompositePackage#getAtomicSoundComponent_IntegerProperties()
	 * @model mapType="soundgatesComposite.EStringToEIntegerObject<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EIntegerObject>"
	 * @generated
	 */
	EMap<String, Integer> getIntegerProperties();

	/**
	 * Returns the value of the '<em><b>String Properties</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>String Properties</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>String Properties</em>' map.
	 * @see soundgatesComposite.SoundgatesCompositePackage#getAtomicSoundComponent_StringProperties()
	 * @model mapType="soundgatesComposite.EStringToEString<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	EMap<String, String> getStringProperties();

	/**
	 * Returns the value of the '<em><b>User String Properties</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User String Properties</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User String Properties</em>' map.
	 * @see soundgatesComposite.SoundgatesCompositePackage#getAtomicSoundComponent_UserStringProperties()
	 * @model mapType="soundgatesComposite.EStringToEString<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	EMap<String, String> getUserStringProperties();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see soundgatesComposite.SoundgatesCompositePackage#getAtomicSoundComponent_Type()
	 * @model required="true" changeable="false"
	 * @generated
	 */
	String getType();
	
	void setType(String type);

} // AtomicSoundComponent
