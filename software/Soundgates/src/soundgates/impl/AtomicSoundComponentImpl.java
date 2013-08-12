/**
 */
package soundgates.impl;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import soundgates.AtomicSoundComponent;
import soundgates.SoundgatesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Atomic Sound Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link soundgates.impl.AtomicSoundComponentImpl#getFloatProperties <em>Float Properties</em>}</li>
 *   <li>{@link soundgates.impl.AtomicSoundComponentImpl#getIntegerProperties <em>Integer Properties</em>}</li>
 *   <li>{@link soundgates.impl.AtomicSoundComponentImpl#getBooleanProperties <em>Boolean Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AtomicSoundComponentImpl extends SoundComponentImpl implements AtomicSoundComponent {
	/**
	 * The cached value of the '{@link #getFloatProperties() <em>Float Properties</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloatProperties()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, Float> floatProperties;

	/**
	 * The cached value of the '{@link #getIntegerProperties() <em>Integer Properties</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntegerProperties()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, Integer> integerProperties;

	/**
	 * The cached value of the '{@link #getBooleanProperties() <em>Boolean Properties</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBooleanProperties()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, Boolean> booleanProperties;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AtomicSoundComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SoundgatesPackage.Literals.ATOMIC_SOUND_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, Float> getFloatProperties() {
		if (floatProperties == null) {
			floatProperties = new EcoreEMap<String,Float>(SoundgatesPackage.Literals.ESTRING_TO_EFLOAT_OBJECT, EStringToEFloatObjectImpl.class, this, SoundgatesPackage.ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES);
		}
		return floatProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, Integer> getIntegerProperties() {
		if (integerProperties == null) {
			integerProperties = new EcoreEMap<String,Integer>(SoundgatesPackage.Literals.ESTRING_TO_EINTEGER_OBJECT, EStringToEIntegerObjectImpl.class, this, SoundgatesPackage.ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES);
		}
		return integerProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, Boolean> getBooleanProperties() {
		if (booleanProperties == null) {
			booleanProperties = new EcoreEMap<String,Boolean>(SoundgatesPackage.Literals.ESTRING_TO_EBOOLEAN_OBJECT, EStringToEBooleanObjectImpl.class, this, SoundgatesPackage.ATOMIC_SOUND_COMPONENT__BOOLEAN_PROPERTIES);
		}
		return booleanProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES:
				return ((InternalEList<?>)getFloatProperties()).basicRemove(otherEnd, msgs);
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES:
				return ((InternalEList<?>)getIntegerProperties()).basicRemove(otherEnd, msgs);
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__BOOLEAN_PROPERTIES:
				return ((InternalEList<?>)getBooleanProperties()).basicRemove(otherEnd, msgs);
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
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES:
				if (coreType) return getFloatProperties();
				else return getFloatProperties().map();
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES:
				if (coreType) return getIntegerProperties();
				else return getIntegerProperties().map();
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__BOOLEAN_PROPERTIES:
				if (coreType) return getBooleanProperties();
				else return getBooleanProperties().map();
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
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES:
				((EStructuralFeature.Setting)getFloatProperties()).set(newValue);
				return;
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES:
				((EStructuralFeature.Setting)getIntegerProperties()).set(newValue);
				return;
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__BOOLEAN_PROPERTIES:
				((EStructuralFeature.Setting)getBooleanProperties()).set(newValue);
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
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES:
				getFloatProperties().clear();
				return;
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES:
				getIntegerProperties().clear();
				return;
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__BOOLEAN_PROPERTIES:
				getBooleanProperties().clear();
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
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES:
				return floatProperties != null && !floatProperties.isEmpty();
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES:
				return integerProperties != null && !integerProperties.isEmpty();
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__BOOLEAN_PROPERTIES:
				return booleanProperties != null && !booleanProperties.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AtomicSoundComponentImpl
