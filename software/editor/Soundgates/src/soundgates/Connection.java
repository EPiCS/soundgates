/**
 */
package soundgates;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Connection
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link soundgates.Connection#getSource <em>Source</em>}</li>
 *   <li>{@link soundgates.Connection#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see soundgates.SoundgatesPackage#getConnection()
 * @model abstract="true"
 * @generated
 */
public interface Connection extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link soundgates.Port#getOutgoingConnection <em>Outgoing Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(Port)
	 * @see soundgates.SoundgatesPackage#getConnection_Source()
	 * @see soundgates.Port#getOutgoingConnection
	 * @model opposite="outgoingConnection" required="true"
	 * @generated
	 */
	Port getSource();

	/**
	 * Sets the value of the '{@link soundgates.Connection#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Port value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link soundgates.Port#getIncomingConnection <em>Incoming Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Port)
	 * @see soundgates.SoundgatesPackage#getConnection_Target()
	 * @see soundgates.Port#getIncomingConnection
	 * @model opposite="incomingConnection" required="true"
	 * @generated
	 */
	Port getTarget();

	/**
	 * Sets the value of the '{@link soundgates.Connection#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Port value);

} // Connection
