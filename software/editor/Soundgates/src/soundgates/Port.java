/**
 */
package soundgates;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Port</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Ports
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link soundgates.Port#getOutgoingConnection <em>Outgoing Connection</em>}</li>
 *   <li>{@link soundgates.Port#getIncomingConnection <em>Incoming Connection</em>}</li>
 *   <li>{@link soundgates.Port#getComponent <em>Component</em>}</li>
 *   <li>{@link soundgates.Port#getDirection <em>Direction</em>}</li>
 *   <li>{@link soundgates.Port#getDataType <em>Data Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see soundgates.SoundgatesPackage#getPort()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='UnconnectedPort'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL UnconnectedPort='if (self.direction=Direction::IN) then (not (self.incomingConnection=null)) else (self.outgoingConnection->size()>0) endif'"
 * @generated
 */
public interface Port extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Outgoing Connection</b></em>' reference list.
	 * The list contents are of type {@link soundgates.Connection}.
	 * It is bidirectional and its opposite is '{@link soundgates.Connection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing Connection</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Connection</em>' reference list.
	 * @see soundgates.SoundgatesPackage#getPort_OutgoingConnection()
	 * @see soundgates.Connection#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<Connection> getOutgoingConnection();

	/**
	 * Returns the value of the '<em><b>Incoming Connection</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link soundgates.Connection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming Connection</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Connection</em>' reference.
	 * @see #setIncomingConnection(Connection)
	 * @see soundgates.SoundgatesPackage#getPort_IncomingConnection()
	 * @see soundgates.Connection#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	Connection getIncomingConnection();

	/**
	 * Sets the value of the '{@link soundgates.Port#getIncomingConnection <em>Incoming Connection</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Incoming Connection</em>' reference.
	 * @see #getIncomingConnection()
	 * @generated
	 */
	void setIncomingConnection(Connection value);

	/**
	 * Returns the value of the '<em><b>Component</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link soundgates.SoundComponent#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' container reference.
	 * @see #setComponent(SoundComponent)
	 * @see soundgates.SoundgatesPackage#getPort_Component()
	 * @see soundgates.SoundComponent#getPorts
	 * @model opposite="ports" required="true" transient="false"
	 * @generated
	 */
	SoundComponent getComponent();

	/**
	 * Sets the value of the '{@link soundgates.Port#getComponent <em>Component</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' container reference.
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(SoundComponent value);

	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link soundgates.Direction}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see soundgates.Direction
	 * @see #setDirection(Direction)
	 * @see soundgates.SoundgatesPackage#getPort_Direction()
	 * @model required="true"
	 * @generated
	 */
	Direction getDirection();

	/**
	 * Sets the value of the '{@link soundgates.Port#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see soundgates.Direction
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(Direction value);

	/**
	 * Returns the value of the '<em><b>Data Type</b></em>' attribute.
	 * The literals are from the enumeration {@link soundgates.DataType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Type</em>' attribute.
	 * @see soundgates.DataType
	 * @see #setDataType(DataType)
	 * @see soundgates.SoundgatesPackage#getPort_DataType()
	 * @model required="true"
	 * @generated
	 */
	DataType getDataType();

	/**
	 * Sets the value of the '{@link soundgates.Port#getDataType <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Type</em>' attribute.
	 * @see soundgates.DataType
	 * @see #getDataType()
	 * @generated
	 */
	void setDataType(DataType value);

} // Port
