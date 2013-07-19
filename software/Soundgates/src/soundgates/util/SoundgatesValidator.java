/**
 */
package soundgates.util;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

import soundgates.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see soundgates.SoundgatesPackage
 * @generated
 */
public class SoundgatesValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final SoundgatesValidator INSTANCE = new SoundgatesValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "soundgates";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SoundgatesValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return SoundgatesPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case SoundgatesPackage.PATCH:
				return validatePatch((Patch)value, diagnostics, context);
			case SoundgatesPackage.ELEMENT:
				return validateElement((Element)value, diagnostics, context);
			case SoundgatesPackage.LINK:
				return validateLink((Link)value, diagnostics, context);
			case SoundgatesPackage.SOUND_COMPONENT:
				return validateSoundComponent((SoundComponent)value, diagnostics, context);
			case SoundgatesPackage.PORT:
				return validatePort((Port)value, diagnostics, context);
			case SoundgatesPackage.NAMED_ELEMENT:
				return validateNamedElement((NamedElement)value, diagnostics, context);
			case SoundgatesPackage.COMPOSITE_SOUND_COMPONENT:
				return validateCompositeSoundComponent((CompositeSoundComponent)value, diagnostics, context);
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT:
				return validateAtomicSoundComponent((AtomicSoundComponent)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePatch(Patch patch, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(patch, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateElement(Element element, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(element, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLink(Link link, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(link, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSoundComponent(SoundComponent soundComponent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(soundComponent, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePort(Port port, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(port, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNamedElement(NamedElement namedElement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(namedElement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCompositeSoundComponent(CompositeSoundComponent compositeSoundComponent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(compositeSoundComponent, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(compositeSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(compositeSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(compositeSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(compositeSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(compositeSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(compositeSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(compositeSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(compositeSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validateCompositeSoundComponent_RightLinksOnPorts(compositeSoundComponent, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the RightLinksOnPorts constraint of '<em>Composite Sound Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String COMPOSITE_SOUND_COMPONENT__RIGHT_LINKS_ON_PORTS__EEXPRESSION = "(self.inputPorts->forAll(if (not (outgoingLink=null)) then if (outgoingLink.parentComponent=self) then true else false endif else true endif)) and \r\n" +
		"(self.outputPorts->forAll(if (not (incomingLink=null)) then if (incomingLink.parentComponent=self) then true else false endif else true endif))";

	/**
	 * Validates the RightLinksOnPorts constraint of '<em>Composite Sound Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCompositeSoundComponent_RightLinksOnPorts(CompositeSoundComponent compositeSoundComponent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(SoundgatesPackage.Literals.COMPOSITE_SOUND_COMPONENT,
				 compositeSoundComponent,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL",
				 "RightLinksOnPorts",
				 COMPOSITE_SOUND_COMPONENT__RIGHT_LINKS_ON_PORTS__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAtomicSoundComponent(AtomicSoundComponent atomicSoundComponent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment(atomicSoundComponent, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms(atomicSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms(atomicSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained(atomicSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired(atomicSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves(atomicSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID(atomicSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique(atomicSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique(atomicSoundComponent, diagnostics, context);
		if (result || diagnostics != null) result &= validateAtomicSoundComponent_RightLinksOnPorts(atomicSoundComponent, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the RightLinksOnPorts constraint of '<em>Atomic Sound Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ATOMIC_SOUND_COMPONENT__RIGHT_LINKS_ON_PORTS__EEXPRESSION = "(self.inputPorts->forAll( not (incomingLink=null) and outgoingLink=null)) and (self.outputPorts->forAll( not (outgoingLink=null) and incomingLink=null))";

	/**
	 * Validates the RightLinksOnPorts constraint of '<em>Atomic Sound Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAtomicSoundComponent_RightLinksOnPorts(AtomicSoundComponent atomicSoundComponent, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(SoundgatesPackage.Literals.ATOMIC_SOUND_COMPONENT,
				 atomicSoundComponent,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL",
				 "RightLinksOnPorts",
				 ATOMIC_SOUND_COMPONENT__RIGHT_LINKS_ON_PORTS__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //SoundgatesValidator
