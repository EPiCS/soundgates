/**
 */
package soundgatesComposite.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import soundgatesComposite.CompositeSoundComponent;
import soundgatesComposite.Port;
import soundgatesComposite.SoundComponent;
import soundgatesComposite.SoundgatesCompositePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sound Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link soundgatesComposite.impl.SoundComponentImpl#getPorts <em>Ports</em>}</li>
 *   <li>{@link soundgatesComposite.impl.SoundComponentImpl#getParentComponent <em>Parent Component</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class SoundComponentImpl extends NamedElementImpl implements SoundComponent {
	/**
	 * The cached value of the '{@link #getPorts() <em>Ports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPorts()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> ports;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SoundComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SoundgatesCompositePackage.Literals.SOUND_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getPorts() {
		if (ports == null) {
			ports = new EObjectContainmentWithInverseEList<Port>(Port.class, this, SoundgatesCompositePackage.SOUND_COMPONENT__PORTS, SoundgatesCompositePackage.PORT__COMPONENT);
		}
		return ports;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeSoundComponent getParentComponent() {
		if (eContainerFeatureID() != SoundgatesCompositePackage.SOUND_COMPONENT__PARENT_COMPONENT) return null;
		return (CompositeSoundComponent)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentComponent(CompositeSoundComponent newParentComponent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParentComponent, SoundgatesCompositePackage.SOUND_COMPONENT__PARENT_COMPONENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentComponent(CompositeSoundComponent newParentComponent) {
		if (newParentComponent != eInternalContainer() || (eContainerFeatureID() != SoundgatesCompositePackage.SOUND_COMPONENT__PARENT_COMPONENT && newParentComponent != null)) {
			if (EcoreUtil.isAncestor(this, newParentComponent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentComponent != null)
				msgs = ((InternalEObject)newParentComponent).eInverseAdd(this, SoundgatesCompositePackage.COMPOSITE_SOUND_COMPONENT__EMBEDDED_COMPONENTS, CompositeSoundComponent.class, msgs);
			msgs = basicSetParentComponent(newParentComponent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SoundgatesCompositePackage.SOUND_COMPONENT__PARENT_COMPONENT, newParentComponent, newParentComponent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SoundgatesCompositePackage.SOUND_COMPONENT__PORTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPorts()).basicAdd(otherEnd, msgs);
			case SoundgatesCompositePackage.SOUND_COMPONENT__PARENT_COMPONENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParentComponent((CompositeSoundComponent)otherEnd, msgs);
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
			case SoundgatesCompositePackage.SOUND_COMPONENT__PORTS:
				return ((InternalEList<?>)getPorts()).basicRemove(otherEnd, msgs);
			case SoundgatesCompositePackage.SOUND_COMPONENT__PARENT_COMPONENT:
				return basicSetParentComponent(null, msgs);
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
			case SoundgatesCompositePackage.SOUND_COMPONENT__PARENT_COMPONENT:
				return eInternalContainer().eInverseRemove(this, SoundgatesCompositePackage.COMPOSITE_SOUND_COMPONENT__EMBEDDED_COMPONENTS, CompositeSoundComponent.class, msgs);
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
			case SoundgatesCompositePackage.SOUND_COMPONENT__PORTS:
				return getPorts();
			case SoundgatesCompositePackage.SOUND_COMPONENT__PARENT_COMPONENT:
				return getParentComponent();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SoundgatesCompositePackage.SOUND_COMPONENT__PORTS:
				getPorts().clear();
				getPorts().addAll((Collection<? extends Port>)newValue);
				return;
			case SoundgatesCompositePackage.SOUND_COMPONENT__PARENT_COMPONENT:
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
			case SoundgatesCompositePackage.SOUND_COMPONENT__PORTS:
				getPorts().clear();
				return;
			case SoundgatesCompositePackage.SOUND_COMPONENT__PARENT_COMPONENT:
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
			case SoundgatesCompositePackage.SOUND_COMPONENT__PORTS:
				return ports != null && !ports.isEmpty();
			case SoundgatesCompositePackage.SOUND_COMPONENT__PARENT_COMPONENT:
				return getParentComponent() != null;
		}
		return super.eIsSet(featureID);
	}

} //SoundComponentImpl
