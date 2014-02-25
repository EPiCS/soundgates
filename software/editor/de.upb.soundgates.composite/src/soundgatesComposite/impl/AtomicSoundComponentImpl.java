/**
 */
package soundgatesComposite.impl;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import soundgatesComposite.AtomicSoundComponent;
import soundgatesComposite.SoundgatesCompositePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Atomic Sound Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link soundgatesComposite.impl.AtomicSoundComponentImpl#getFloatProperties <em>Float Properties</em>}</li>
 *   <li>{@link soundgatesComposite.impl.AtomicSoundComponentImpl#getIntegerProperties <em>Integer Properties</em>}</li>
 *   <li>{@link soundgatesComposite.impl.AtomicSoundComponentImpl#getStringProperties <em>String Properties</em>}</li>
 *   <li>{@link soundgatesComposite.impl.AtomicSoundComponentImpl#getUserStringProperties <em>User String Properties</em>}</li>
 *   <li>{@link soundgatesComposite.impl.AtomicSoundComponentImpl#getType <em>Type</em>}</li>
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
	 * The cached value of the '{@link #getStringProperties() <em>String Properties</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStringProperties()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> stringProperties;

	/**
	 * The cached value of the '{@link #getUserStringProperties() <em>User String Properties</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserStringProperties()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> userStringProperties;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

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
		return SoundgatesCompositePackage.Literals.ATOMIC_SOUND_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, Float> getFloatProperties() {
		if (floatProperties == null) {
			floatProperties = new EcoreEMap<String,Float>(SoundgatesCompositePackage.Literals.ESTRING_TO_EFLOAT_OBJECT, EStringToEFloatObjectImpl.class, this, SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES);
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
			integerProperties = new EcoreEMap<String,Integer>(SoundgatesCompositePackage.Literals.ESTRING_TO_EINTEGER_OBJECT, EStringToEIntegerObjectImpl.class, this, SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES);
		}
		return integerProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getStringProperties() {
		if (stringProperties == null) {
			stringProperties = new EcoreEMap<String,String>(SoundgatesCompositePackage.Literals.ESTRING_TO_ESTRING, EStringToEStringImpl.class, this, SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__STRING_PROPERTIES);
		}
		return stringProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getUserStringProperties() {
		if (userStringProperties == null) {
			userStringProperties = new EcoreEMap<String,String>(SoundgatesCompositePackage.Literals.ESTRING_TO_ESTRING, EStringToEStringImpl.class, this, SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__USER_STRING_PROPERTIES);
		}
		return userStringProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES:
				return ((InternalEList<?>)getFloatProperties()).basicRemove(otherEnd, msgs);
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES:
				return ((InternalEList<?>)getIntegerProperties()).basicRemove(otherEnd, msgs);
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__STRING_PROPERTIES:
				return ((InternalEList<?>)getStringProperties()).basicRemove(otherEnd, msgs);
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__USER_STRING_PROPERTIES:
				return ((InternalEList<?>)getUserStringProperties()).basicRemove(otherEnd, msgs);
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
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES:
				if (coreType) return getFloatProperties();
				else return getFloatProperties().map();
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES:
				if (coreType) return getIntegerProperties();
				else return getIntegerProperties().map();
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__STRING_PROPERTIES:
				if (coreType) return getStringProperties();
				else return getStringProperties().map();
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__USER_STRING_PROPERTIES:
				if (coreType) return getUserStringProperties();
				else return getUserStringProperties().map();
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__TYPE:
				return getType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES:
				((EStructuralFeature.Setting)getFloatProperties()).set(newValue);
				return;
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES:
				((EStructuralFeature.Setting)getIntegerProperties()).set(newValue);
				return;
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__TYPE:
				setType((String)newValue);
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
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES:
				getFloatProperties().clear();
				return;
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES:
				getIntegerProperties().clear();
				return;
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__STRING_PROPERTIES:
				getStringProperties().clear();
				return;
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__USER_STRING_PROPERTIES:
				getUserStringProperties().clear();
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
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES:
				return floatProperties != null && !floatProperties.isEmpty();
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES:
				return integerProperties != null && !integerProperties.isEmpty();
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__STRING_PROPERTIES:
				return stringProperties != null && !stringProperties.isEmpty();
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__USER_STRING_PROPERTIES:
				return userStringProperties != null && !userStringProperties.isEmpty();
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} //AtomicSoundComponentImpl
