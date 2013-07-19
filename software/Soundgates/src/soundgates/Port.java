/**
 */
package soundgates;


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
 *   <li>{@link soundgates.Port#getOutgoingLink <em>Outgoing Link</em>}</li>
 *   <li>{@link soundgates.Port#getIncomingLink <em>Incoming Link</em>}</li>
 *   <li>{@link soundgates.Port#getReceivingComponent <em>Receiving Component</em>}</li>
 *   <li>{@link soundgates.Port#getSendingComponent <em>Sending Component</em>}</li>
 * </ul>
 * </p>
 *
 * @see soundgates.SoundgatesPackage#getPort()
 * @model
 * @generated
 */
public interface Port extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Outgoing Link</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link soundgates.Link#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outgoing Link</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Link</em>' reference.
	 * @see #setOutgoingLink(Link)
	 * @see soundgates.SoundgatesPackage#getPort_OutgoingLink()
	 * @see soundgates.Link#getSource
	 * @model opposite="source"
	 * @generated
	 */
	Link getOutgoingLink();

	/**
	 * Sets the value of the '{@link soundgates.Port#getOutgoingLink <em>Outgoing Link</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Outgoing Link</em>' reference.
	 * @see #getOutgoingLink()
	 * @generated
	 */
	void setOutgoingLink(Link value);

	/**
	 * Returns the value of the '<em><b>Incoming Link</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link soundgates.Link#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Incoming Link</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Link</em>' reference.
	 * @see #setIncomingLink(Link)
	 * @see soundgates.SoundgatesPackage#getPort_IncomingLink()
	 * @see soundgates.Link#getTarget
	 * @model opposite="target"
	 * @generated
	 */
	Link getIncomingLink();

	/**
	 * Sets the value of the '{@link soundgates.Port#getIncomingLink <em>Incoming Link</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Incoming Link</em>' reference.
	 * @see #getIncomingLink()
	 * @generated
	 */
	void setIncomingLink(Link value);

	/**
	 * Returns the value of the '<em><b>Receiving Component</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link soundgates.SoundComponent#getInputPorts <em>Input Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Receiving Component</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Receiving Component</em>' container reference.
	 * @see #setReceivingComponent(SoundComponent)
	 * @see soundgates.SoundgatesPackage#getPort_ReceivingComponent()
	 * @see soundgates.SoundComponent#getInputPorts
	 * @model opposite="inputPorts" transient="false"
	 * @generated
	 */
	SoundComponent getReceivingComponent();

	/**
	 * Sets the value of the '{@link soundgates.Port#getReceivingComponent <em>Receiving Component</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Receiving Component</em>' container reference.
	 * @see #getReceivingComponent()
	 * @generated
	 */
	void setReceivingComponent(SoundComponent value);

	/**
	 * Returns the value of the '<em><b>Sending Component</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link soundgates.SoundComponent#getOutputPorts <em>Output Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sending Component</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sending Component</em>' container reference.
	 * @see #setSendingComponent(SoundComponent)
	 * @see soundgates.SoundgatesPackage#getPort_SendingComponent()
	 * @see soundgates.SoundComponent#getOutputPorts
	 * @model opposite="outputPorts" transient="false"
	 * @generated
	 */
	SoundComponent getSendingComponent();

	/**
	 * Sets the value of the '{@link soundgates.Port#getSendingComponent <em>Sending Component</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sending Component</em>' container reference.
	 * @see #getSendingComponent()
	 * @generated
	 */
	void setSendingComponent(SoundComponent value);

} // Port
