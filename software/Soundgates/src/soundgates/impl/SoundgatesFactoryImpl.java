/**
 */
package soundgates.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import soundgates.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SoundgatesFactoryImpl extends EFactoryImpl implements SoundgatesFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SoundgatesFactory init() {
		try {
			SoundgatesFactory theSoundgatesFactory = (SoundgatesFactory)EPackage.Registry.INSTANCE.getEFactory(SoundgatesPackage.eNS_URI);
			if (theSoundgatesFactory != null) {
				return theSoundgatesFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SoundgatesFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SoundgatesFactoryImpl() {
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
			case SoundgatesPackage.PATCH: return createPatch();
			case SoundgatesPackage.LINK: return createLink();
			case SoundgatesPackage.PORT: return createPort();
			case SoundgatesPackage.COMPOSITE_SOUND_COMPONENT: return createCompositeSoundComponent();
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT: return createAtomicSoundComponent();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Patch createPatch() {
		PatchImpl patch = new PatchImpl();
		return patch;
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
	public SoundgatesPackage getSoundgatesPackage() {
		return (SoundgatesPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SoundgatesPackage getPackage() {
		return SoundgatesPackage.eINSTANCE;
	}

} //SoundgatesFactoryImpl
