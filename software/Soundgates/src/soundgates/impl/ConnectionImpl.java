/**
 */
package soundgates.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import soundgates.CompositeSoundComponent;
import soundgates.Connection;
import soundgates.Port;
import soundgates.SoundgatesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link soundgates.impl.ConnectionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link soundgates.impl.ConnectionImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link soundgates.impl.ConnectionImpl#getParentComponent <em>Parent Component</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ConnectionImpl extends EObjectImpl implements Connection {
	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected Port source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Port target;

	/**
	 * The cached value of the '{@link #getParentComponent() <em>Parent Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentComponent()
	 * @generated
	 * @ordered
	 */
	protected CompositeSoundComponent parentComponent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConnectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SoundgatesPackage.Literals.CONNECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Port getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject)source;
			source = (Port)eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SoundgatesPackage.CONNECTION__SOURCE, oldSource, source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Port basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(Port newSource, NotificationChain msgs) {
		Port oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SoundgatesPackage.CONNECTION__SOURCE, oldSource, newSource);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(Port newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject)source).eInverseRemove(this, SoundgatesPackage.PORT__OUTGOING_CONNECTION, Port.class, msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, SoundgatesPackage.PORT__OUTGOING_CONNECTION, Port.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SoundgatesPackage.CONNECTION__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Port getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (Port)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SoundgatesPackage.CONNECTION__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Port basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(Port newTarget, NotificationChain msgs) {
		Port oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, SoundgatesPackage.CONNECTION__TARGET, oldTarget, newTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(Port newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, SoundgatesPackage.PORT__INCOMING_CONNECTION, Port.class, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, SoundgatesPackage.PORT__INCOMING_CONNECTION, Port.class, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SoundgatesPackage.CONNECTION__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeSoundComponent getParentComponent() {
		if (parentComponent != null && parentComponent.eIsProxy()) {
			InternalEObject oldParentComponent = (InternalEObject)parentComponent;
			parentComponent = (CompositeSoundComponent)eResolveProxy(oldParentComponent);
			if (parentComponent != oldParentComponent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SoundgatesPackage.CONNECTION__PARENT_COMPONENT, oldParentComponent, parentComponent));
			}
		}
		return parentComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeSoundComponent basicGetParentComponent() {
		return parentComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentComponent(CompositeSoundComponent newParentComponent) {
		CompositeSoundComponent oldParentComponent = parentComponent;
		parentComponent = newParentComponent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SoundgatesPackage.CONNECTION__PARENT_COMPONENT, oldParentComponent, parentComponent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SoundgatesPackage.CONNECTION__SOURCE:
				if (source != null)
					msgs = ((InternalEObject)source).eInverseRemove(this, SoundgatesPackage.PORT__OUTGOING_CONNECTION, Port.class, msgs);
				return basicSetSource((Port)otherEnd, msgs);
			case SoundgatesPackage.CONNECTION__TARGET:
				if (target != null)
					msgs = ((InternalEObject)target).eInverseRemove(this, SoundgatesPackage.PORT__INCOMING_CONNECTION, Port.class, msgs);
				return basicSetTarget((Port)otherEnd, msgs);
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
			case SoundgatesPackage.CONNECTION__SOURCE:
				return basicSetSource(null, msgs);
			case SoundgatesPackage.CONNECTION__TARGET:
				return basicSetTarget(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SoundgatesPackage.CONNECTION__SOURCE:
				if (resolve) return getSource();
				return basicGetSource();
			case SoundgatesPackage.CONNECTION__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case SoundgatesPackage.CONNECTION__PARENT_COMPONENT:
				if (resolve) return getParentComponent();
				return basicGetParentComponent();
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
			case SoundgatesPackage.CONNECTION__SOURCE:
				setSource((Port)newValue);
				return;
			case SoundgatesPackage.CONNECTION__TARGET:
				setTarget((Port)newValue);
				return;
			case SoundgatesPackage.CONNECTION__PARENT_COMPONENT:
				setParentComponent((CompositeSoundComponent)newValue);
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
			case SoundgatesPackage.CONNECTION__SOURCE:
				setSource((Port)null);
				return;
			case SoundgatesPackage.CONNECTION__TARGET:
				setTarget((Port)null);
				return;
			case SoundgatesPackage.CONNECTION__PARENT_COMPONENT:
				setParentComponent((CompositeSoundComponent)null);
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
			case SoundgatesPackage.CONNECTION__SOURCE:
				return source != null;
			case SoundgatesPackage.CONNECTION__TARGET:
				return target != null;
			case SoundgatesPackage.CONNECTION__PARENT_COMPONENT:
				return parentComponent != null;
		}
		return super.eIsSet(featureID);
	}

} //ConnectionImpl
