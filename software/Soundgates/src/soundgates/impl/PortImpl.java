/**
 */
package soundgates.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import soundgates.Link;
import soundgates.Port;
import soundgates.SoundComponent;
import soundgates.SoundgatesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Port</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link soundgates.impl.PortImpl#getOutgoingLink <em>Outgoing Link</em>}</li>
 *   <li>{@link soundgates.impl.PortImpl#getIncomingLink <em>Incoming Link</em>}</li>
 *   <li>{@link soundgates.impl.PortImpl#getReceivingComponent <em>Receiving Component</em>}</li>
 *   <li>{@link soundgates.impl.PortImpl#getSendingComponent <em>Sending Component</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PortImpl extends NamedElementImpl implements Port {
	/**
	 * The cached value of the '{@link #getOutgoingLink() <em>Outgoing Link</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoingLink()
	 * @generated
	 * @ordered
	 */
	protected Link outgoingLink;

	/**
	 * The cached value of the '{@link #getIncomingLink() <em>Incoming Link</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncomingLink()
	 * @generated
	 * @ordered
	 */
	protected Link incomingLink;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PortImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SoundgatesPackage.Literals.PORT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Link getOutgoingLink() {
		if (outgoingLink != null && outgoingLink.eIsProxy()) {
			InternalEObject oldOutgoingLink = (InternalEObject)outgoingLink;
			outgoingLink = (Link)eResolveProxy(oldOutgoingLink);
			if (outgoingLink != oldOutgoingLink) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SoundgatesPackage.PORT__OUTGOING_LINK, oldOutgoingLink, outgoingLink));
			}
		}
		return outgoingLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Link basicGetOutgoingLink() {
		return outgoingLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOutgoingLink(Link newOutgoingLink, NotificationChain msgs) {
		Link oldOutgoingLink = outgoingLink;
		outgoingLink = newOutgoingLink;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SoundgatesPackage.PORT__OUTGOING_LINK, oldOutgoingLink, newOutgoingLink);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutgoingLink(Link newOutgoingLink) {
		if (newOutgoingLink != outgoingLink) {
			NotificationChain msgs = null;
			if (outgoingLink != null)
				msgs = ((InternalEObject)outgoingLink).eInverseRemove(this, SoundgatesPackage.LINK__SOURCE, Link.class, msgs);
			if (newOutgoingLink != null)
				msgs = ((InternalEObject)newOutgoingLink).eInverseAdd(this, SoundgatesPackage.LINK__SOURCE, Link.class, msgs);
			msgs = basicSetOutgoingLink(newOutgoingLink, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SoundgatesPackage.PORT__OUTGOING_LINK, newOutgoingLink, newOutgoingLink));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Link getIncomingLink() {
		if (incomingLink != null && incomingLink.eIsProxy()) {
			InternalEObject oldIncomingLink = (InternalEObject)incomingLink;
			incomingLink = (Link)eResolveProxy(oldIncomingLink);
			if (incomingLink != oldIncomingLink) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SoundgatesPackage.PORT__INCOMING_LINK, oldIncomingLink, incomingLink));
			}
		}
		return incomingLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Link basicGetIncomingLink() {
		return incomingLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIncomingLink(Link newIncomingLink, NotificationChain msgs) {
		Link oldIncomingLink = incomingLink;
		incomingLink = newIncomingLink;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SoundgatesPackage.PORT__INCOMING_LINK, oldIncomingLink, newIncomingLink);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIncomingLink(Link newIncomingLink) {
		if (newIncomingLink != incomingLink) {
			NotificationChain msgs = null;
			if (incomingLink != null)
				msgs = ((InternalEObject)incomingLink).eInverseRemove(this, SoundgatesPackage.LINK__TARGET, Link.class, msgs);
			if (newIncomingLink != null)
				msgs = ((InternalEObject)newIncomingLink).eInverseAdd(this, SoundgatesPackage.LINK__TARGET, Link.class, msgs);
			msgs = basicSetIncomingLink(newIncomingLink, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SoundgatesPackage.PORT__INCOMING_LINK, newIncomingLink, newIncomingLink));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SoundComponent getReceivingComponent() {
		if (eContainerFeatureID() != SoundgatesPackage.PORT__RECEIVING_COMPONENT) return null;
		return (SoundComponent)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReceivingComponent(SoundComponent newReceivingComponent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newReceivingComponent, SoundgatesPackage.PORT__RECEIVING_COMPONENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReceivingComponent(SoundComponent newReceivingComponent) {
		if (newReceivingComponent != eInternalContainer() || (eContainerFeatureID() != SoundgatesPackage.PORT__RECEIVING_COMPONENT && newReceivingComponent != null)) {
			if (EcoreUtil.isAncestor(this, newReceivingComponent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newReceivingComponent != null)
				msgs = ((InternalEObject)newReceivingComponent).eInverseAdd(this, SoundgatesPackage.SOUND_COMPONENT__INPUT_PORTS, SoundComponent.class, msgs);
			msgs = basicSetReceivingComponent(newReceivingComponent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SoundgatesPackage.PORT__RECEIVING_COMPONENT, newReceivingComponent, newReceivingComponent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SoundComponent getSendingComponent() {
		if (eContainerFeatureID() != SoundgatesPackage.PORT__SENDING_COMPONENT) return null;
		return (SoundComponent)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSendingComponent(SoundComponent newSendingComponent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSendingComponent, SoundgatesPackage.PORT__SENDING_COMPONENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSendingComponent(SoundComponent newSendingComponent) {
		if (newSendingComponent != eInternalContainer() || (eContainerFeatureID() != SoundgatesPackage.PORT__SENDING_COMPONENT && newSendingComponent != null)) {
			if (EcoreUtil.isAncestor(this, newSendingComponent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSendingComponent != null)
				msgs = ((InternalEObject)newSendingComponent).eInverseAdd(this, SoundgatesPackage.SOUND_COMPONENT__OUTPUT_PORTS, SoundComponent.class, msgs);
			msgs = basicSetSendingComponent(newSendingComponent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SoundgatesPackage.PORT__SENDING_COMPONENT, newSendingComponent, newSendingComponent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SoundgatesPackage.PORT__OUTGOING_LINK:
				if (outgoingLink != null)
					msgs = ((InternalEObject)outgoingLink).eInverseRemove(this, SoundgatesPackage.LINK__SOURCE, Link.class, msgs);
				return basicSetOutgoingLink((Link)otherEnd, msgs);
			case SoundgatesPackage.PORT__INCOMING_LINK:
				if (incomingLink != null)
					msgs = ((InternalEObject)incomingLink).eInverseRemove(this, SoundgatesPackage.LINK__TARGET, Link.class, msgs);
				return basicSetIncomingLink((Link)otherEnd, msgs);
			case SoundgatesPackage.PORT__RECEIVING_COMPONENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetReceivingComponent((SoundComponent)otherEnd, msgs);
			case SoundgatesPackage.PORT__SENDING_COMPONENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSendingComponent((SoundComponent)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SoundgatesPackage.PORT__OUTGOING_LINK:
				return basicSetOutgoingLink(null, msgs);
			case SoundgatesPackage.PORT__INCOMING_LINK:
				return basicSetIncomingLink(null, msgs);
			case SoundgatesPackage.PORT__RECEIVING_COMPONENT:
				return basicSetReceivingComponent(null, msgs);
			case SoundgatesPackage.PORT__SENDING_COMPONENT:
				return basicSetSendingComponent(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case SoundgatesPackage.PORT__RECEIVING_COMPONENT:
				return eInternalContainer().eInverseRemove(this, SoundgatesPackage.SOUND_COMPONENT__INPUT_PORTS, SoundComponent.class, msgs);
			case SoundgatesPackage.PORT__SENDING_COMPONENT:
				return eInternalContainer().eInverseRemove(this, SoundgatesPackage.SOUND_COMPONENT__OUTPUT_PORTS, SoundComponent.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SoundgatesPackage.PORT__OUTGOING_LINK:
				if (resolve) return getOutgoingLink();
				return basicGetOutgoingLink();
			case SoundgatesPackage.PORT__INCOMING_LINK:
				if (resolve) return getIncomingLink();
				return basicGetIncomingLink();
			case SoundgatesPackage.PORT__RECEIVING_COMPONENT:
				return getReceivingComponent();
			case SoundgatesPackage.PORT__SENDING_COMPONENT:
				return getSendingComponent();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SoundgatesPackage.PORT__OUTGOING_LINK:
				setOutgoingLink((Link)newValue);
				return;
			case SoundgatesPackage.PORT__INCOMING_LINK:
				setIncomingLink((Link)newValue);
				return;
			case SoundgatesPackage.PORT__RECEIVING_COMPONENT:
				setReceivingComponent((SoundComponent)newValue);
				return;
			case SoundgatesPackage.PORT__SENDING_COMPONENT:
				setSendingComponent((SoundComponent)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case SoundgatesPackage.PORT__OUTGOING_LINK:
				setOutgoingLink((Link)null);
				return;
			case SoundgatesPackage.PORT__INCOMING_LINK:
				setIncomingLink((Link)null);
				return;
			case SoundgatesPackage.PORT__RECEIVING_COMPONENT:
				setReceivingComponent((SoundComponent)null);
				return;
			case SoundgatesPackage.PORT__SENDING_COMPONENT:
				setSendingComponent((SoundComponent)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case SoundgatesPackage.PORT__OUTGOING_LINK:
				return outgoingLink != null;
			case SoundgatesPackage.PORT__INCOMING_LINK:
				return incomingLink != null;
			case SoundgatesPackage.PORT__RECEIVING_COMPONENT:
				return getReceivingComponent() != null;
			case SoundgatesPackage.PORT__SENDING_COMPONENT:
				return getSendingComponent() != null;
		}
		return super.eIsSet(featureID);
	}

} //PortImpl
