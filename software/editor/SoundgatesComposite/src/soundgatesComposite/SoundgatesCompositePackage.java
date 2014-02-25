/**
 */
package soundgatesComposite;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see soundgatesComposite.SoundgatesCompositeFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL'"
 * @generated
 */
public interface SoundgatesCompositePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "soundgatesComposite";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "soundgatesComposite";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "soundgatesComposite";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SoundgatesCompositePackage eINSTANCE = soundgatesComposite.impl.SoundgatesCompositePackageImpl.init();

	/**
	 * The meta object id for the '{@link soundgatesComposite.impl.WorkbenchImpl <em>Workbench</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.impl.WorkbenchImpl
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getWorkbench()
	 * @generated
	 */
	int WORKBENCH = 0;

	/**
	 * The feature id for the '<em><b>Composite Sound Components</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKBENCH__COMPOSITE_SOUND_COMPONENTS = 0;

	/**
	 * The number of structural features of the '<em>Workbench</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKBENCH_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Workbench</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WORKBENCH_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link soundgatesComposite.impl.ElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.impl.ElementImpl
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getElement()
	 * @generated
	 */
	int ELEMENT = 1;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link soundgatesComposite.impl.NamedElementImpl <em>Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.impl.NamedElementImpl
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Named Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NAMED_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link soundgatesComposite.impl.SoundComponentImpl <em>Sound Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.impl.SoundComponentImpl
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getSoundComponent()
	 * @generated
	 */
	int SOUND_COMPONENT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_COMPONENT__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_COMPONENT__PORTS = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parent Component</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_COMPONENT__PARENT_COMPONENT = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Sound Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_COMPONENT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Sound Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_COMPONENT_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link soundgatesComposite.impl.PortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.impl.PortImpl
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getPort()
	 * @generated
	 */
	int PORT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Outgoing Connection</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__OUTGOING_CONNECTION = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Incoming Connection</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__INCOMING_CONNECTION = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Component</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__COMPONENT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__DIRECTION = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__DATA_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_OPERATION_COUNT = NAMED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link soundgatesComposite.impl.CompositeSoundComponentImpl <em>Composite Sound Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.impl.CompositeSoundComponentImpl
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getCompositeSoundComponent()
	 * @generated
	 */
	int COMPOSITE_SOUND_COMPONENT = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SOUND_COMPONENT__NAME = SOUND_COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SOUND_COMPONENT__PORTS = SOUND_COMPONENT__PORTS;

	/**
	 * The feature id for the '<em><b>Parent Component</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SOUND_COMPONENT__PARENT_COMPONENT = SOUND_COMPONENT__PARENT_COMPONENT;

	/**
	 * The feature id for the '<em><b>Embedded Components</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SOUND_COMPONENT__EMBEDDED_COMPONENTS = SOUND_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SOUND_COMPONENT__LINKS = SOUND_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Delegations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SOUND_COMPONENT__DELEGATIONS = SOUND_COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Composite Sound Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SOUND_COMPONENT_FEATURE_COUNT = SOUND_COMPONENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Composite Sound Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SOUND_COMPONENT_OPERATION_COUNT = SOUND_COMPONENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link soundgatesComposite.impl.AtomicSoundComponentImpl <em>Atomic Sound Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.impl.AtomicSoundComponentImpl
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getAtomicSoundComponent()
	 * @generated
	 */
	int ATOMIC_SOUND_COMPONENT = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT__NAME = SOUND_COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT__PORTS = SOUND_COMPONENT__PORTS;

	/**
	 * The feature id for the '<em><b>Parent Component</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT__PARENT_COMPONENT = SOUND_COMPONENT__PARENT_COMPONENT;

	/**
	 * The feature id for the '<em><b>Float Properties</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES = SOUND_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Integer Properties</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES = SOUND_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>String Properties</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT__STRING_PROPERTIES = SOUND_COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>User String Properties</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT__USER_STRING_PROPERTIES = SOUND_COMPONENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT__TYPE = SOUND_COMPONENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Atomic Sound Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT_FEATURE_COUNT = SOUND_COMPONENT_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Atomic Sound Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT_OPERATION_COUNT = SOUND_COMPONENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link soundgatesComposite.impl.ConnectionImpl <em>Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.impl.ConnectionImpl
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getConnection()
	 * @generated
	 */
	int CONNECTION = 7;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__TARGET = 1;

	/**
	 * The number of structural features of the '<em>Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link soundgatesComposite.impl.DelegationImpl <em>Delegation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.impl.DelegationImpl
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getDelegation()
	 * @generated
	 */
	int DELEGATION = 8;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATION__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATION__TARGET = CONNECTION__TARGET;

	/**
	 * The number of structural features of the '<em>Delegation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATION_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Delegation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATION_OPERATION_COUNT = CONNECTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link soundgatesComposite.impl.LinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.impl.LinkImpl
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getLink()
	 * @generated
	 */
	int LINK = 9;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__SOURCE = CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__TARGET = CONNECTION__TARGET;

	/**
	 * The number of structural features of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_OPERATION_COUNT = CONNECTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link soundgatesComposite.impl.EStringToEFloatObjectImpl <em>EString To EFloat Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.impl.EStringToEFloatObjectImpl
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getEStringToEFloatObject()
	 * @generated
	 */
	int ESTRING_TO_EFLOAT_OBJECT = 10;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EFLOAT_OBJECT__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EFLOAT_OBJECT__VALUE = 1;

	/**
	 * The number of structural features of the '<em>EString To EFloat Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EFLOAT_OBJECT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>EString To EFloat Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EFLOAT_OBJECT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link soundgatesComposite.impl.EStringToEIntegerObjectImpl <em>EString To EInteger Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.impl.EStringToEIntegerObjectImpl
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getEStringToEIntegerObject()
	 * @generated
	 */
	int ESTRING_TO_EINTEGER_OBJECT = 11;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EINTEGER_OBJECT__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EINTEGER_OBJECT__VALUE = 1;

	/**
	 * The number of structural features of the '<em>EString To EInteger Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EINTEGER_OBJECT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>EString To EInteger Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EINTEGER_OBJECT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link soundgatesComposite.impl.EStringToEBooleanObjectImpl <em>EString To EBoolean Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.impl.EStringToEBooleanObjectImpl
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getEStringToEBooleanObject()
	 * @generated
	 */
	int ESTRING_TO_EBOOLEAN_OBJECT = 12;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EBOOLEAN_OBJECT__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EBOOLEAN_OBJECT__VALUE = 1;

	/**
	 * The number of structural features of the '<em>EString To EBoolean Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EBOOLEAN_OBJECT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>EString To EBoolean Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_EBOOLEAN_OBJECT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link soundgatesComposite.impl.EStringToEStringImpl <em>EString To EString</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.impl.EStringToEStringImpl
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getEStringToEString()
	 * @generated
	 */
	int ESTRING_TO_ESTRING = 13;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING__VALUE = 1;

	/**
	 * The number of structural features of the '<em>EString To EString</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>EString To EString</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link soundgatesComposite.Direction <em>Direction</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.Direction
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getDirection()
	 * @generated
	 */
	int DIRECTION = 14;

	/**
	 * The meta object id for the '{@link soundgatesComposite.DataType <em>Data Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgatesComposite.DataType
	 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getDataType()
	 * @generated
	 */
	int DATA_TYPE = 15;


	/**
	 * Returns the meta object for class '{@link soundgatesComposite.Workbench <em>Workbench</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Workbench</em>'.
	 * @see soundgatesComposite.Workbench
	 * @generated
	 */
	EClass getWorkbench();

	/**
	 * Returns the meta object for the containment reference list '{@link soundgatesComposite.Workbench#getCompositeSoundComponents <em>Composite Sound Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Composite Sound Components</em>'.
	 * @see soundgatesComposite.Workbench#getCompositeSoundComponents()
	 * @see #getWorkbench()
	 * @generated
	 */
	EReference getWorkbench_CompositeSoundComponents();

	/**
	 * Returns the meta object for class '{@link soundgatesComposite.Element <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see soundgatesComposite.Element
	 * @generated
	 */
	EClass getElement();

	/**
	 * Returns the meta object for class '{@link soundgatesComposite.SoundComponent <em>Sound Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sound Component</em>'.
	 * @see soundgatesComposite.SoundComponent
	 * @generated
	 */
	EClass getSoundComponent();

	/**
	 * Returns the meta object for the containment reference list '{@link soundgatesComposite.SoundComponent#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ports</em>'.
	 * @see soundgatesComposite.SoundComponent#getPorts()
	 * @see #getSoundComponent()
	 * @generated
	 */
	EReference getSoundComponent_Ports();

	/**
	 * Returns the meta object for the container reference '{@link soundgatesComposite.SoundComponent#getParentComponent <em>Parent Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent Component</em>'.
	 * @see soundgatesComposite.SoundComponent#getParentComponent()
	 * @see #getSoundComponent()
	 * @generated
	 */
	EReference getSoundComponent_ParentComponent();

	/**
	 * Returns the meta object for class '{@link soundgatesComposite.Port <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port</em>'.
	 * @see soundgatesComposite.Port
	 * @generated
	 */
	EClass getPort();

	/**
	 * Returns the meta object for the reference list '{@link soundgatesComposite.Port#getOutgoingConnection <em>Outgoing Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing Connection</em>'.
	 * @see soundgatesComposite.Port#getOutgoingConnection()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_OutgoingConnection();

	/**
	 * Returns the meta object for the reference '{@link soundgatesComposite.Port#getIncomingConnection <em>Incoming Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Incoming Connection</em>'.
	 * @see soundgatesComposite.Port#getIncomingConnection()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_IncomingConnection();

	/**
	 * Returns the meta object for the container reference '{@link soundgatesComposite.Port#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Component</em>'.
	 * @see soundgatesComposite.Port#getComponent()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_Component();

	/**
	 * Returns the meta object for the attribute '{@link soundgatesComposite.Port#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see soundgatesComposite.Port#getDirection()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Direction();

	/**
	 * Returns the meta object for the attribute '{@link soundgatesComposite.Port#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Type</em>'.
	 * @see soundgatesComposite.Port#getDataType()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_DataType();

	/**
	 * Returns the meta object for class '{@link soundgatesComposite.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see soundgatesComposite.NamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link soundgatesComposite.NamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see soundgatesComposite.NamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link soundgatesComposite.CompositeSoundComponent <em>Composite Sound Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composite Sound Component</em>'.
	 * @see soundgatesComposite.CompositeSoundComponent
	 * @generated
	 */
	EClass getCompositeSoundComponent();

	/**
	 * Returns the meta object for the containment reference list '{@link soundgatesComposite.CompositeSoundComponent#getEmbeddedComponents <em>Embedded Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Embedded Components</em>'.
	 * @see soundgatesComposite.CompositeSoundComponent#getEmbeddedComponents()
	 * @see #getCompositeSoundComponent()
	 * @generated
	 */
	EReference getCompositeSoundComponent_EmbeddedComponents();

	/**
	 * Returns the meta object for the containment reference list '{@link soundgatesComposite.CompositeSoundComponent#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Links</em>'.
	 * @see soundgatesComposite.CompositeSoundComponent#getLinks()
	 * @see #getCompositeSoundComponent()
	 * @generated
	 */
	EReference getCompositeSoundComponent_Links();

	/**
	 * Returns the meta object for the containment reference list '{@link soundgatesComposite.CompositeSoundComponent#getDelegations <em>Delegations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Delegations</em>'.
	 * @see soundgatesComposite.CompositeSoundComponent#getDelegations()
	 * @see #getCompositeSoundComponent()
	 * @generated
	 */
	EReference getCompositeSoundComponent_Delegations();

	/**
	 * Returns the meta object for class '{@link soundgatesComposite.AtomicSoundComponent <em>Atomic Sound Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Atomic Sound Component</em>'.
	 * @see soundgatesComposite.AtomicSoundComponent
	 * @generated
	 */
	EClass getAtomicSoundComponent();

	/**
	 * Returns the meta object for the map '{@link soundgatesComposite.AtomicSoundComponent#getFloatProperties <em>Float Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Float Properties</em>'.
	 * @see soundgatesComposite.AtomicSoundComponent#getFloatProperties()
	 * @see #getAtomicSoundComponent()
	 * @generated
	 */
	EReference getAtomicSoundComponent_FloatProperties();

	/**
	 * Returns the meta object for the map '{@link soundgatesComposite.AtomicSoundComponent#getIntegerProperties <em>Integer Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Integer Properties</em>'.
	 * @see soundgatesComposite.AtomicSoundComponent#getIntegerProperties()
	 * @see #getAtomicSoundComponent()
	 * @generated
	 */
	EReference getAtomicSoundComponent_IntegerProperties();

	/**
	 * Returns the meta object for the map '{@link soundgatesComposite.AtomicSoundComponent#getStringProperties <em>String Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>String Properties</em>'.
	 * @see soundgatesComposite.AtomicSoundComponent#getStringProperties()
	 * @see #getAtomicSoundComponent()
	 * @generated
	 */
	EReference getAtomicSoundComponent_StringProperties();

	/**
	 * Returns the meta object for the map '{@link soundgatesComposite.AtomicSoundComponent#getUserStringProperties <em>User String Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>User String Properties</em>'.
	 * @see soundgatesComposite.AtomicSoundComponent#getUserStringProperties()
	 * @see #getAtomicSoundComponent()
	 * @generated
	 */
	EReference getAtomicSoundComponent_UserStringProperties();

	/**
	 * Returns the meta object for the attribute '{@link soundgatesComposite.AtomicSoundComponent#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see soundgatesComposite.AtomicSoundComponent#getType()
	 * @see #getAtomicSoundComponent()
	 * @generated
	 */
	EAttribute getAtomicSoundComponent_Type();

	/**
	 * Returns the meta object for class '{@link soundgatesComposite.Connection <em>Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection</em>'.
	 * @see soundgatesComposite.Connection
	 * @generated
	 */
	EClass getConnection();

	/**
	 * Returns the meta object for the reference '{@link soundgatesComposite.Connection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see soundgatesComposite.Connection#getSource()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_Source();

	/**
	 * Returns the meta object for the reference '{@link soundgatesComposite.Connection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see soundgatesComposite.Connection#getTarget()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_Target();

	/**
	 * Returns the meta object for class '{@link soundgatesComposite.Delegation <em>Delegation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Delegation</em>'.
	 * @see soundgatesComposite.Delegation
	 * @generated
	 */
	EClass getDelegation();

	/**
	 * Returns the meta object for class '{@link soundgatesComposite.Link <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link</em>'.
	 * @see soundgatesComposite.Link
	 * @generated
	 */
	EClass getLink();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EString To EFloat Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EString To EFloat Object</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDataType="org.eclipse.emf.ecore.EFloatObject"
	 * @generated
	 */
	EClass getEStringToEFloatObject();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToEFloatObject()
	 * @generated
	 */
	EAttribute getEStringToEFloatObject_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToEFloatObject()
	 * @generated
	 */
	EAttribute getEStringToEFloatObject_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EString To EInteger Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EString To EInteger Object</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDataType="org.eclipse.emf.ecore.EIntegerObject"
	 * @generated
	 */
	EClass getEStringToEIntegerObject();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToEIntegerObject()
	 * @generated
	 */
	EAttribute getEStringToEIntegerObject_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToEIntegerObject()
	 * @generated
	 */
	EAttribute getEStringToEIntegerObject_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EString To EBoolean Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EString To EBoolean Object</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDataType="org.eclipse.emf.ecore.EBooleanObject"
	 * @generated
	 */
	EClass getEStringToEBooleanObject();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToEBooleanObject()
	 * @generated
	 */
	EAttribute getEStringToEBooleanObject_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToEBooleanObject()
	 * @generated
	 */
	EAttribute getEStringToEBooleanObject_Value();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EString To EString</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EString To EString</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDataType="org.eclipse.emf.ecore.EString"
	 * @generated
	 */
	EClass getEStringToEString();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToEString()
	 * @generated
	 */
	EAttribute getEStringToEString_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToEString()
	 * @generated
	 */
	EAttribute getEStringToEString_Value();

	/**
	 * Returns the meta object for enum '{@link soundgatesComposite.Direction <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Direction</em>'.
	 * @see soundgatesComposite.Direction
	 * @generated
	 */
	EEnum getDirection();

	/**
	 * Returns the meta object for enum '{@link soundgatesComposite.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Data Type</em>'.
	 * @see soundgatesComposite.DataType
	 * @generated
	 */
	EEnum getDataType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SoundgatesCompositeFactory getSoundgatesCompositeFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link soundgatesComposite.impl.WorkbenchImpl <em>Workbench</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.impl.WorkbenchImpl
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getWorkbench()
		 * @generated
		 */
		EClass WORKBENCH = eINSTANCE.getWorkbench();

		/**
		 * The meta object literal for the '<em><b>Composite Sound Components</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WORKBENCH__COMPOSITE_SOUND_COMPONENTS = eINSTANCE.getWorkbench_CompositeSoundComponents();

		/**
		 * The meta object literal for the '{@link soundgatesComposite.impl.ElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.impl.ElementImpl
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getElement()
		 * @generated
		 */
		EClass ELEMENT = eINSTANCE.getElement();

		/**
		 * The meta object literal for the '{@link soundgatesComposite.impl.SoundComponentImpl <em>Sound Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.impl.SoundComponentImpl
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getSoundComponent()
		 * @generated
		 */
		EClass SOUND_COMPONENT = eINSTANCE.getSoundComponent();

		/**
		 * The meta object literal for the '<em><b>Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOUND_COMPONENT__PORTS = eINSTANCE.getSoundComponent_Ports();

		/**
		 * The meta object literal for the '<em><b>Parent Component</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOUND_COMPONENT__PARENT_COMPONENT = eINSTANCE.getSoundComponent_ParentComponent();

		/**
		 * The meta object literal for the '{@link soundgatesComposite.impl.PortImpl <em>Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.impl.PortImpl
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getPort()
		 * @generated
		 */
		EClass PORT = eINSTANCE.getPort();

		/**
		 * The meta object literal for the '<em><b>Outgoing Connection</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__OUTGOING_CONNECTION = eINSTANCE.getPort_OutgoingConnection();

		/**
		 * The meta object literal for the '<em><b>Incoming Connection</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__INCOMING_CONNECTION = eINSTANCE.getPort_IncomingConnection();

		/**
		 * The meta object literal for the '<em><b>Component</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__COMPONENT = eINSTANCE.getPort_Component();

		/**
		 * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT__DIRECTION = eINSTANCE.getPort_Direction();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT__DATA_TYPE = eINSTANCE.getPort_DataType();

		/**
		 * The meta object literal for the '{@link soundgatesComposite.impl.NamedElementImpl <em>Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.impl.NamedElementImpl
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getNamedElement()
		 * @generated
		 */
		EClass NAMED_ELEMENT = eINSTANCE.getNamedElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NAMED_ELEMENT__NAME = eINSTANCE.getNamedElement_Name();

		/**
		 * The meta object literal for the '{@link soundgatesComposite.impl.CompositeSoundComponentImpl <em>Composite Sound Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.impl.CompositeSoundComponentImpl
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getCompositeSoundComponent()
		 * @generated
		 */
		EClass COMPOSITE_SOUND_COMPONENT = eINSTANCE.getCompositeSoundComponent();

		/**
		 * The meta object literal for the '<em><b>Embedded Components</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITE_SOUND_COMPONENT__EMBEDDED_COMPONENTS = eINSTANCE.getCompositeSoundComponent_EmbeddedComponents();

		/**
		 * The meta object literal for the '<em><b>Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITE_SOUND_COMPONENT__LINKS = eINSTANCE.getCompositeSoundComponent_Links();

		/**
		 * The meta object literal for the '<em><b>Delegations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITE_SOUND_COMPONENT__DELEGATIONS = eINSTANCE.getCompositeSoundComponent_Delegations();

		/**
		 * The meta object literal for the '{@link soundgatesComposite.impl.AtomicSoundComponentImpl <em>Atomic Sound Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.impl.AtomicSoundComponentImpl
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getAtomicSoundComponent()
		 * @generated
		 */
		EClass ATOMIC_SOUND_COMPONENT = eINSTANCE.getAtomicSoundComponent();

		/**
		 * The meta object literal for the '<em><b>Float Properties</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES = eINSTANCE.getAtomicSoundComponent_FloatProperties();

		/**
		 * The meta object literal for the '<em><b>Integer Properties</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES = eINSTANCE.getAtomicSoundComponent_IntegerProperties();

		/**
		 * The meta object literal for the '<em><b>String Properties</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATOMIC_SOUND_COMPONENT__STRING_PROPERTIES = eINSTANCE.getAtomicSoundComponent_StringProperties();

		/**
		 * The meta object literal for the '<em><b>User String Properties</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATOMIC_SOUND_COMPONENT__USER_STRING_PROPERTIES = eINSTANCE.getAtomicSoundComponent_UserStringProperties();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATOMIC_SOUND_COMPONENT__TYPE = eINSTANCE.getAtomicSoundComponent_Type();

		/**
		 * The meta object literal for the '{@link soundgatesComposite.impl.ConnectionImpl <em>Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.impl.ConnectionImpl
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getConnection()
		 * @generated
		 */
		EClass CONNECTION = eINSTANCE.getConnection();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__SOURCE = eINSTANCE.getConnection_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__TARGET = eINSTANCE.getConnection_Target();

		/**
		 * The meta object literal for the '{@link soundgatesComposite.impl.DelegationImpl <em>Delegation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.impl.DelegationImpl
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getDelegation()
		 * @generated
		 */
		EClass DELEGATION = eINSTANCE.getDelegation();

		/**
		 * The meta object literal for the '{@link soundgatesComposite.impl.LinkImpl <em>Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.impl.LinkImpl
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getLink()
		 * @generated
		 */
		EClass LINK = eINSTANCE.getLink();

		/**
		 * The meta object literal for the '{@link soundgatesComposite.impl.EStringToEFloatObjectImpl <em>EString To EFloat Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.impl.EStringToEFloatObjectImpl
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getEStringToEFloatObject()
		 * @generated
		 */
		EClass ESTRING_TO_EFLOAT_OBJECT = eINSTANCE.getEStringToEFloatObject();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_EFLOAT_OBJECT__KEY = eINSTANCE.getEStringToEFloatObject_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_EFLOAT_OBJECT__VALUE = eINSTANCE.getEStringToEFloatObject_Value();

		/**
		 * The meta object literal for the '{@link soundgatesComposite.impl.EStringToEIntegerObjectImpl <em>EString To EInteger Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.impl.EStringToEIntegerObjectImpl
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getEStringToEIntegerObject()
		 * @generated
		 */
		EClass ESTRING_TO_EINTEGER_OBJECT = eINSTANCE.getEStringToEIntegerObject();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_EINTEGER_OBJECT__KEY = eINSTANCE.getEStringToEIntegerObject_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_EINTEGER_OBJECT__VALUE = eINSTANCE.getEStringToEIntegerObject_Value();

		/**
		 * The meta object literal for the '{@link soundgatesComposite.impl.EStringToEBooleanObjectImpl <em>EString To EBoolean Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.impl.EStringToEBooleanObjectImpl
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getEStringToEBooleanObject()
		 * @generated
		 */
		EClass ESTRING_TO_EBOOLEAN_OBJECT = eINSTANCE.getEStringToEBooleanObject();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_EBOOLEAN_OBJECT__KEY = eINSTANCE.getEStringToEBooleanObject_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_EBOOLEAN_OBJECT__VALUE = eINSTANCE.getEStringToEBooleanObject_Value();

		/**
		 * The meta object literal for the '{@link soundgatesComposite.impl.EStringToEStringImpl <em>EString To EString</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.impl.EStringToEStringImpl
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getEStringToEString()
		 * @generated
		 */
		EClass ESTRING_TO_ESTRING = eINSTANCE.getEStringToEString();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_ESTRING__KEY = eINSTANCE.getEStringToEString_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_ESTRING__VALUE = eINSTANCE.getEStringToEString_Value();

		/**
		 * The meta object literal for the '{@link soundgatesComposite.Direction <em>Direction</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.Direction
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getDirection()
		 * @generated
		 */
		EEnum DIRECTION = eINSTANCE.getDirection();

		/**
		 * The meta object literal for the '{@link soundgatesComposite.DataType <em>Data Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgatesComposite.DataType
		 * @see soundgatesComposite.impl.SoundgatesCompositePackageImpl#getDataType()
		 * @generated
		 */
		EEnum DATA_TYPE = eINSTANCE.getDataType();

	}

} //SoundgatesCompositePackage
