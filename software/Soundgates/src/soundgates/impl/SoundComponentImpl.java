/**
 */
package soundgates.impl;

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

import soundgates.CompositeSoundComponent;
import soundgates.Port;
import soundgates.SoundComponent;
import soundgates.SoundgatesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sound Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link soundgates.impl.SoundComponentImpl#getInputPorts <em>Input Ports</em>}</li>
 *   <li>{@link soundgates.impl.SoundComponentImpl#getOutputPorts <em>Output Ports</em>}</li>
 *   <li>{@link soundgates.impl.SoundComponentImpl#getParentComponent <em>Parent Component</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class SoundComponentImpl extends NamedElementImpl implements SoundComponent {
	/**
	 * The cached value of the '{@link #getInputPorts() <em>Input Ports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputPorts()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> inputPorts;

	/**
	 * The cached value of the '{@link #getOutputPorts() <em>Output Ports</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputPorts()
	 * @generated
	 * @ordered
	 */
	protected EList<Port> outputPorts;

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
		return SoundgatesPackage.Literals.SOUND_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getInputPorts() {
		if (inputPorts == null) {
			inputPorts = new EObjectContainmentWithInverseEList<Port>(Port.class, this, SoundgatesPackage.SOUND_COMPONENT__INPUT_PORTS, SoundgatesPackage.PORT__RECEIVING_COMPONENT);
		}
		return inputPorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Port> getOutputPorts() {
		if (outputPorts == null) {
			outputPorts = new EObjectContainmentWithInverseEList<Port>(Port.class, this, SoundgatesPackage.SOUND_COMPONENT__OUTPUT_PORTS, SoundgatesPackage.PORT__SENDING_COMPONENT);
		}
		return outputPorts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeSoundComponent getParentComponent() {
		if (eContainerFeatureID() != SoundgatesPackage.SOUND_COMPONENT__PARENT_COMPONENT) return null;
		return (CompositeSoundComponent)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParentComponent(CompositeSoundComponent newParentComponent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParentComponent, SoundgatesPackage.SOUND_COMPONENT__PARENT_COMPONENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentComponent(CompositeSoundComponent newParentComponent) {
		if (newParentComponent != eInternalContainer() || (eContainerFeatureID() != SoundgatesPackage.SOUND_COMPONENT__PARENT_COMPONENT && newParentComponent != null)) {
			if (EcoreUtil.isAncestor(this, newParentComponent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentComponent != null)
				msgs = ((InternalEObject)newParentComponent).eInverseAdd(this, SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__EMBEDDED_COMPONENTS, CompositeSoundComponent.class, msgs);
			msgs = basicSetParentComponent(newParentComponent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SoundgatesPackage.SOUND_COMPONENT__PARENT_COMPONENT, newParentComponent, newParentComponent));
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
			case SoundgatesPackage.SOUND_COMPONENT__INPUT_PORTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInputPorts()).basicAdd(otherEnd, msgs);
			case SoundgatesPackage.SOUND_COMPONENT__OUTPUT_PORTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutputPorts()).basicAdd(otherEnd, msgs);
			case SoundgatesPackage.SOUND_COMPONENT__PARENT_COMPONENT:
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
			case SoundgatesPackage.SOUND_COMPONENT__INPUT_PORTS:
				return ((InternalEList<?>)getInputPorts()).basicRemove(otherEnd, msgs);
			case SoundgatesPackage.SOUND_COMPONENT__OUTPUT_PORTS:
				return ((InternalEList<?>)getOutputPorts()).basicRemove(otherEnd, msgs);
			case SoundgatesPackage.SOUND_COMPONENT__PARENT_COMPONENT:
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
			case SoundgatesPackage.SOUND_COMPONENT__PARENT_COMPONENT:
				return eInternalContainer().eInverseRemove(this, SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__EMBEDDED_COMPONENTS, CompositeSoundComponent.class, msgs);
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
			case SoundgatesPackage.SOUND_COMPONENT__INPUT_PORTS:
				return getInputPorts();
			case SoundgatesPackage.SOUND_COMPONENT__OUTPUT_PORTS:
				return getOutputPorts();
			case SoundgatesPackage.SOUND_COMPONENT__PARENT_COMPONENT:
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
			case SoundgatesPackage.SOUND_COMPONENT__INPUT_PORTS:
				getInputPorts().clear();
				getInputPorts().addAll((Collection<? extends Port>)newValue);
				return;
			case SoundgatesPackage.SOUND_COMPONENT__OUTPUT_PORTS:
				getOutputPorts().clear();
				getOutputPorts().addAll((Collection<? extends Port>)newValue);
				return;
			case SoundgatesPackage.SOUND_COMPONENT__PARENT_COMPONENT:
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
			case SoundgatesPackage.SOUND_COMPONENT__INPUT_PORTS:
				getInputPorts().clear();
				return;
			case SoundgatesPackage.SOUND_COMPONENT__OUTPUT_PORTS:
				getOutputPorts().clear();
				return;
			case SoundgatesPackage.SOUND_COMPONENT__PARENT_COMPONENT:
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
			case SoundgatesPackage.SOUND_COMPONENT__INPUT_PORTS:
				return inputPorts != null && !inputPorts.isEmpty();
			case SoundgatesPackage.SOUND_COMPONENT__OUTPUT_PORTS:
				return outputPorts != null && !outputPorts.isEmpty();
			case SoundgatesPackage.SOUND_COMPONENT__PARENT_COMPONENT:
				return getParentComponent() != null;
		}
		return super.eIsSet(featureID);
	}

} //SoundComponentImpl
