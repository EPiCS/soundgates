/**
 */
package soundgatesComposite;

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
 *   <li>{@link soundgatesComposite.Port#getOutgoingConnection <em>Outgoing Connection</em>}</li>
 *   <li>{@link soundgatesComposite.Port#getIncomingConnection <em>Incoming Connection</em>}</li>
 *   <li>{@link soundgatesComposite.Port#getComponent <em>Component</em>}</li>
 *   <li>{@link soundgatesComposite.Port#getDirection <em>Direction</em>}</li>
 *   <li>{@link soundgatesComposite.Port#getDataType <em>Data Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see soundgatesComposite.SoundgatesCompositePackage#getPort()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='UnconnectedPort'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL UnconnectedPort='if (self.direction=Direction::IN) then (not (self.incomingConnection=null)) else (self.outgoingConnection->size()>0) endif'"
 * @generated
 */
public interface Port extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Outgoing Connection</b></em>' reference list.
	 * The list contents are of type {@link soundgatesComposite.Connection}.
	 * It is bidirectional and its opposite is '{@link soundgatesComposite.Connection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing Connection</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Connection</em>' reference list.
	 * @see soundgatesComposite.SoundgatesCompositePackage#getPort_OutgoingConnection()
	 * @see soundgatesComposite.Connection#getSource
	 * @model opposite="source"
	 * @generated
	 */
	EList<Connection> getOutgoingConnection();

	/**
	 * Returns the value of the '<em><b>Incoming Connection</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link soundgatesComposite.Connection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming Connection</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Connection</em>' reference.
	 * @see #setIncomingConnection(Connection)
	 * @see soundgatesComposite.SoundgatesCompositePackage#getPort_IncomingConnection()
	 * @see soundgatesComposite.Connection#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	Connection getIncomingConnection();

	/**
	 * Sets the value of the '{@link soundgatesComposite.Port#getIncomingConnection <em>Incoming Connection</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Incoming Connection</em>' reference.
	 * @see #getIncomingConnection()
	 * @generated
	 */
	void setIncomingConnection(Connection value);

	/**
	 * Returns the value of the '<em><b>Component</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link soundgatesComposite.SoundComponent#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' container reference.
	 * @see #setComponent(SoundComponent)
	 * @see soundgatesComposite.SoundgatesCompositePackage#getPort_Component()
	 * @see soundgatesComposite.SoundComponent#getPorts
	 * @model opposite="ports" required="true" transient="false"
	 * @generated
	 */
	SoundComponent getComponent();

	/**
	 * Sets the value of the '{@link soundgatesComposite.Port#getComponent <em>Component</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' container reference.
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(SoundComponent value);

	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The literals are from the enumeration {@link soundgatesComposite.Direction}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see soundgatesComposite.Direction
	 * @see #setDirection(Direction)
	 * @see soundgatesComposite.SoundgatesCompositePackage#getPort_Direction()
	 * @model required="true"
	 * @generated
	 */
	Direction getDirection();

	/**
	 * Sets the value of the '{@link soundgatesComposite.Port#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see soundgatesComposite.Direction
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(Direction value);

	/**
	 * Returns the value of the '<em><b>Data Type</b></em>' attribute.
	 * The literals are from the enumeration {@link soundgatesComposite.DataType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Type</em>' attribute.
	 * @see soundgatesComposite.DataType
	 * @see #setDataType(DataType)
	 * @see soundgatesComposite.SoundgatesCompositePackage#getPort_DataType()
	 * @model required="true"
	 * @generated
	 */
	DataType getDataType();

	/**
	 * Sets the value of the '{@link soundgatesComposite.Port#getDataType <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Type</em>' attribute.
	 * @see soundgatesComposite.DataType
	 * @see #getDataType()
	 * @generated
	 */
	void setDataType(DataType value);

} // Port
