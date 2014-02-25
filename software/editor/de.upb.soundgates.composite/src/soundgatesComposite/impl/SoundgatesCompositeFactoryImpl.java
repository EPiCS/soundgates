/**
 */
package soundgatesComposite.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import soundgatesComposite.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SoundgatesCompositeFactoryImpl extends EFactoryImpl implements SoundgatesCompositeFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SoundgatesCompositeFactory init() {
		try {
			SoundgatesCompositeFactory theSoundgatesCompositeFactory = (SoundgatesCompositeFactory)EPackage.Registry.INSTANCE.getEFactory(SoundgatesCompositePackage.eNS_URI);
			if (theSoundgatesCompositeFactory != null) {
				return theSoundgatesCompositeFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SoundgatesCompositeFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SoundgatesCompositeFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SoundgatesCompositePackage.WORKBENCH: return createWorkbench();
			case SoundgatesCompositePackage.PORT: return createPort();
			case SoundgatesCompositePackage.COMPOSITE_SOUND_COMPONENT: return createCompositeSoundComponent();
			case SoundgatesCompositePackage.ATOMIC_SOUND_COMPONENT: return createAtomicSoundComponent();
			case SoundgatesCompositePackage.DELEGATION: return createDelegation();
			case SoundgatesCompositePackage.LINK: return createLink();
			case SoundgatesCompositePackage.ESTRING_TO_EFLOAT_OBJECT: return (EObject)createEStringToEFloatObject();
			case SoundgatesCompositePackage.ESTRING_TO_EINTEGER_OBJECT: return (EObject)createEStringToEIntegerObject();
			case SoundgatesCompositePackage.ESTRING_TO_EBOOLEAN_OBJECT: return (EObject)createEStringToEBooleanObject();
			case SoundgatesCompositePackage.ESTRING_TO_ESTRING: return (EObject)createEStringToEString();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case SoundgatesCompositePackage.DIRECTION:
				return createDirectionFromString(eDataType, initialValue);
			case SoundgatesCompositePackage.DATA_TYPE:
				return createDataTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case SoundgatesCompositePackage.DIRECTION:
				return convertDirectionToString(eDataType, instanceValue);
			case SoundgatesCompositePackage.DATA_TYPE:
				return convertDataTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Workbench createWorkbench() {
		WorkbenchImpl workbench = new WorkbenchImpl();
		return workbench;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Port createPort() {
		PortImpl port = new PortImpl();
		return port;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeSoundComponent createCompositeSoundComponent() {
		CompositeSoundComponentImpl compositeSoundComponent = new CompositeSoundComponentImpl();
		return compositeSoundComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AtomicSoundComponent createAtomicSoundComponent() {
		AtomicSoundComponentImpl atomicSoundComponent = new AtomicSoundComponentImpl();
		return atomicSoundComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Delegation createDelegation() {
		DelegationImpl delegation = new DelegationImpl();
		return delegation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Link createLink() {
		LinkImpl link = new LinkImpl();
		return link;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, Float> createEStringToEFloatObject() {
		EStringToEFloatObjectImpl eStringToEFloatObject = new EStringToEFloatObjectImpl();
		return eStringToEFloatObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, Integer> createEStringToEIntegerObject() {
		EStringToEIntegerObjectImpl eStringToEIntegerObject = new EStringToEIntegerObjectImpl();
		return eStringToEIntegerObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, Boolean> createEStringToEBooleanObject() {
		EStringToEBooleanObjectImpl eStringToEBooleanObject = new EStringToEBooleanObjectImpl();
		return eStringToEBooleanObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, String> createEStringToEString() {
		EStringToEStringImpl eStringToEString = new EStringToEStringImpl();
		return eStringToEString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Direction createDirectionFromString(EDataType eDataType, String initialValue) {
		Direction result = Direction.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDirectionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataType createDataTypeFromString(EDataType eDataType, String initialValue) {
		DataType result = DataType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertDataTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SoundgatesCompositePackage getSoundgatesCompositePackage() {
		return (SoundgatesCompositePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SoundgatesCompositePackage getPackage() {
		return SoundgatesCompositePackage.eINSTANCE;
	}

} //SoundgatesCompositeFactoryImpl
