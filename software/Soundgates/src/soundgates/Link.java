/**
 */
package soundgates;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link soundgates.Link#getSource <em>Source</em>}</li>
 *   <li>{@link soundgates.Link#getTarget <em>Target</em>}</li>
 *   <li>{@link soundgates.Link#getParentComponent <em>Parent Component</em>}</li>
 * </ul>
 * </p>
 *
 * @see soundgates.SoundgatesPackage#getLink()
 * @model
 * @generated
 */
public interface Link extends Element {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link soundgates.Port#getOutgoingLink <em>Outgoing Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(Port)
	 * @see soundgates.SoundgatesPackage#getLink_Source()
	 * @see soundgates.Port#getOutgoingLink
	 * @model opposite="outgoingLink" required="true"
	 * @generated
	 */
	Port getSource();

	/**
	 * Sets the value of the '{@link soundgates.Link#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Port value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link soundgates.Port#getIncomingLink <em>Incoming Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Port)
	 * @see soundgates.SoundgatesPackage#getLink_Target()
	 * @see soundgates.Port#getIncomingLink
	 * @model opposite="incomingLink" required="true"
	 * @generated
	 */
	Port getTarget();

	/**
	 * Sets the value of the '{@link soundgates.Link#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Port value);

	/**
	 * Returns the value of the '<em><b>Parent Component</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link soundgates.CompositeSoundComponent#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Component</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Component</em>' container reference.
	 * @see #setParentComponent(CompositeSoundComponent)
	 * @see soundgates.SoundgatesPackage#getLink_ParentComponent()
	 * @see soundgates.CompositeSoundComponent#getLinks
	 * @model opposite="links" transient="false"
	 * @generated
	 */
	CompositeSoundComponent getParentComponent();

	/**
	 * Sets the value of the '{@link soundgates.Link#getParentComponent <em>Parent Component</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Component</em>' container reference.
	 * @see #getParentComponent()
	 * @generated
	 */
	void setParentComponent(CompositeSoundComponent value);

} // Link
