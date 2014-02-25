/**
 */
package soundgatesComposite.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import soundgatesComposite.CompositeSoundComponent;
import soundgatesComposite.SoundgatesCompositePackage;
import soundgatesComposite.Workbench;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Workbench</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link soundgatesComposite.impl.WorkbenchImpl#getCompositeSoundComponents <em>Composite Sound Components</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WorkbenchImpl extends MinimalEObjectImpl.Container implements Workbench {
	/**
	 * The cached value of the '{@link #getCompositeSoundComponents() <em>Composite Sound Components</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompositeSoundComponents()
	 * @generated
	 * @ordered
	 */
	protected EList<CompositeSoundComponent> compositeSoundComponents;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WorkbenchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SoundgatesCompositePackage.Literals.WORKBENCH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CompositeSoundComponent> getCompositeSoundComponents() {
		if (compositeSoundComponents == null) {
			compositeSoundComponents = new EObjectContainmentEList<CompositeSoundComponent>(CompositeSoundComponent.class, this, SoundgatesCompositePackage.WORKBENCH__COMPOSITE_SOUND_COMPONENTS);
		}
		return compositeSoundComponents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SoundgatesCompositePackage.WORKBENCH__COMPOSITE_SOUND_COMPONENTS:
				return ((InternalEList<?>)getCompositeSoundComponents()).basicRemove(otherEnd, msgs);
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
			case SoundgatesCompositePackage.WORKBENCH__COMPOSITE_SOUND_COMPONENTS:
				return getCompositeSoundComponents();
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
			case SoundgatesCompositePackage.WORKBENCH__COMPOSITE_SOUND_COMPONENTS:
				getCompositeSoundComponents().clear();
				getCompositeSoundComponents().addAll((Collection<? extends CompositeSoundComponent>)newValue);
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
			case SoundgatesCompositePackage.WORKBENCH__COMPOSITE_SOUND_COMPONENTS:
				getCompositeSoundComponents().clear();
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
			case SoundgatesCompositePackage.WORKBENCH__COMPOSITE_SOUND_COMPONENTS:
				return compositeSoundComponents != null && !compositeSoundComponents.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //WorkbenchImpl
