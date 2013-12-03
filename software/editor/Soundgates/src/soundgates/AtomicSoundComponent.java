/**
 */
package soundgates;

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
 *   <li>{@link soundgates.AtomicSoundComponent#getFloatProperties <em>Float Properties</em>}</li>
 *   <li>{@link soundgates.AtomicSoundComponent#getIntegerProperties <em>Integer Properties</em>}</li>
 *   <li>{@link soundgates.AtomicSoundComponent#getBooleanProperties <em>Boolean Properties</em>}</li>
 *   <li>{@link soundgates.AtomicSoundComponent#getStringProperties <em>String Properties</em>}</li>
 *   <li>{@link soundgates.AtomicSoundComponent#getUserStringProperties <em>User String Properties</em>}</li>
 *   <li>{@link soundgates.AtomicSoundComponent#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see soundgates.SoundgatesPackage#getAtomicSoundComponent()
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
	 * @see soundgates.SoundgatesPackage#getAtomicSoundComponent_FloatProperties()
	 * @model mapType="soundgates.EStringToEFloatObject<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EFloatObject>"
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
	 * @see soundgates.SoundgatesPackage#getAtomicSoundComponent_IntegerProperties()
	 * @model mapType="soundgates.EStringToEIntegerObject<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EIntegerObject>"
	 * @generated
	 */
	EMap<String, Integer> getIntegerProperties();

	/**
	 * Returns the value of the '<em><b>Boolean Properties</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.Boolean},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Boolean Properties</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Boolean Properties</em>' map.
	 * @see soundgates.SoundgatesPackage#getAtomicSoundComponent_BooleanProperties()
	 * @model mapType="soundgates.EStringToEBooleanObject<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EBooleanObject>"
	 * @generated
	 */
	EMap<String, Boolean> getBooleanProperties();

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
	 * @see soundgates.SoundgatesPackage#getAtomicSoundComponent_StringProperties()
	 * @model mapType="soundgates.EStringToEString<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
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
	 * @see soundgates.SoundgatesPackage#getAtomicSoundComponent_UserStringProperties()
	 * @model mapType="soundgates.EStringToEString<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
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
	 * @see soundgates.SoundgatesPackage#getAtomicSoundComponent_Type()
	 * @model required="true" changeable="false"
	 * @generated
	 */
	String getType();
	
	/**
	 * Sets the value of the '<em><b>Type</b></em>' attribute
	 * The user should not be able to set this, it is only meant to be called from code
	 * @param type
	 */
	void setType(String type);

} // AtomicSoundComponent
