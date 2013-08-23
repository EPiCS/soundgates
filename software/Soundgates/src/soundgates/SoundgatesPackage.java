/**
 */
package soundgates;

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
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see soundgates.SoundgatesFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL'"
 * @generated
 */
public interface SoundgatesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "soundgates";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "soundgates";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "soundgates";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SoundgatesPackage eINSTANCE = soundgates.impl.SoundgatesPackageImpl.init();

	/**
	 * The meta object id for the '{@link soundgates.impl.PatchImpl <em>Patch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.PatchImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getPatch()
	 * @generated
	 */
	int PATCH = 0;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATCH__ELEMENTS = 0;

	/**
	 * The number of structural features of the '<em>Patch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATCH_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link soundgates.impl.ElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.ElementImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getElement()
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
	 * The meta object id for the '{@link soundgates.impl.NamedElementImpl <em>Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.NamedElementImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getNamedElement()
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
	 * The meta object id for the '{@link soundgates.impl.SoundComponentImpl <em>Sound Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.SoundComponentImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getSoundComponent()
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
	 * The meta object id for the '{@link soundgates.impl.PortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.PortImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getPort()
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
	 * The feature id for the '<em><b>Outgoing Connection</b></em>' reference.
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
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__KIND = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__DATA_TYPE = NAMED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link soundgates.impl.CompositeSoundComponentImpl <em>Composite Sound Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.CompositeSoundComponentImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getCompositeSoundComponent()
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
	 * The feature id for the '<em><b>Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SOUND_COMPONENT__CONNECTIONS = SOUND_COMPONENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Composite Sound Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SOUND_COMPONENT_FEATURE_COUNT = SOUND_COMPONENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link soundgates.impl.AtomicSoundComponentImpl <em>Atomic Sound Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.AtomicSoundComponentImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getAtomicSoundComponent()
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
	 * The feature id for the '<em><b>Boolean Properties</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT__BOOLEAN_PROPERTIES = SOUND_COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT__TYPE = SOUND_COMPONENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Atomic Sound Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT_FEATURE_COUNT = SOUND_COMPONENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link soundgates.impl.ConnectionImpl <em>Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.ConnectionImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getConnection()
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
	 * The feature id for the '<em><b>Parent Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__PARENT_COMPONENT = 2;

	/**
	 * The number of structural features of the '<em>Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link soundgates.impl.DelegationImpl <em>Delegation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.DelegationImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getDelegation()
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
	 * The feature id for the '<em><b>Parent Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATION__PARENT_COMPONENT = CONNECTION__PARENT_COMPONENT;

	/**
	 * The number of structural features of the '<em>Delegation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DELEGATION_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link soundgates.impl.LinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.LinkImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getLink()
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
	 * The feature id for the '<em><b>Parent Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__PARENT_COMPONENT = CONNECTION__PARENT_COMPONENT;

	/**
	 * The number of structural features of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_FEATURE_COUNT = CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link soundgates.impl.EStringToEFloatObjectImpl <em>EString To EFloat Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.EStringToEFloatObjectImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getEStringToEFloatObject()
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
	 * The meta object id for the '{@link soundgates.impl.EStringToEIntegerObjectImpl <em>EString To EInteger Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.EStringToEIntegerObjectImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getEStringToEIntegerObject()
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
	 * The meta object id for the '{@link soundgates.impl.EStringToEBooleanObjectImpl <em>EString To EBoolean Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.EStringToEBooleanObjectImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getEStringToEBooleanObject()
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
	 * The meta object id for the '{@link soundgates.Kind <em>Kind</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.Kind
	 * @see soundgates.impl.SoundgatesPackageImpl#getKind()
	 * @generated
	 */
	int KIND = 13;

	/**
	 * The meta object id for the '{@link soundgates.Direction <em>Direction</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.Direction
	 * @see soundgates.impl.SoundgatesPackageImpl#getDirection()
	 * @generated
	 */
	int DIRECTION = 14;

	/**
	 * The meta object id for the '{@link soundgates.DataType <em>Data Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.DataType
	 * @see soundgates.impl.SoundgatesPackageImpl#getDataType()
	 * @generated
	 */
	int DATA_TYPE = 15;


	/**
	 * Returns the meta object for class '{@link soundgates.Patch <em>Patch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Patch</em>'.
	 * @see soundgates.Patch
	 * @generated
	 */
	EClass getPatch();

	/**
	 * Returns the meta object for the containment reference list '{@link soundgates.Patch#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see soundgates.Patch#getElements()
	 * @see #getPatch()
	 * @generated
	 */
	EReference getPatch_Elements();

	/**
	 * Returns the meta object for class '{@link soundgates.Element <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see soundgates.Element
	 * @generated
	 */
	EClass getElement();

	/**
	 * Returns the meta object for class '{@link soundgates.SoundComponent <em>Sound Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Sound Component</em>'.
	 * @see soundgates.SoundComponent
	 * @generated
	 */
	EClass getSoundComponent();

	/**
	 * Returns the meta object for the containment reference list '{@link soundgates.SoundComponent#getPorts <em>Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ports</em>'.
	 * @see soundgates.SoundComponent#getPorts()
	 * @see #getSoundComponent()
	 * @generated
	 */
	EReference getSoundComponent_Ports();

	/**
	 * Returns the meta object for the container reference '{@link soundgates.SoundComponent#getParentComponent <em>Parent Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent Component</em>'.
	 * @see soundgates.SoundComponent#getParentComponent()
	 * @see #getSoundComponent()
	 * @generated
	 */
	EReference getSoundComponent_ParentComponent();

	/**
	 * Returns the meta object for class '{@link soundgates.Port <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Port</em>'.
	 * @see soundgates.Port
	 * @generated
	 */
	EClass getPort();

	/**
	 * Returns the meta object for the reference '{@link soundgates.Port#getOutgoingConnection <em>Outgoing Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Outgoing Connection</em>'.
	 * @see soundgates.Port#getOutgoingConnection()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_OutgoingConnection();

	/**
	 * Returns the meta object for the reference '{@link soundgates.Port#getIncomingConnection <em>Incoming Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Incoming Connection</em>'.
	 * @see soundgates.Port#getIncomingConnection()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_IncomingConnection();

	/**
	 * Returns the meta object for the container reference '{@link soundgates.Port#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Component</em>'.
	 * @see soundgates.Port#getComponent()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_Component();

	/**
	 * Returns the meta object for the attribute '{@link soundgates.Port#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see soundgates.Port#getDirection()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Direction();

	/**
	 * Returns the meta object for the attribute '{@link soundgates.Port#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see soundgates.Port#getKind()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_Kind();

	/**
	 * Returns the meta object for the attribute '{@link soundgates.Port#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Type</em>'.
	 * @see soundgates.Port#getDataType()
	 * @see #getPort()
	 * @generated
	 */
	EAttribute getPort_DataType();

	/**
	 * Returns the meta object for class '{@link soundgates.NamedElement <em>Named Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Named Element</em>'.
	 * @see soundgates.NamedElement
	 * @generated
	 */
	EClass getNamedElement();

	/**
	 * Returns the meta object for the attribute '{@link soundgates.NamedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see soundgates.NamedElement#getName()
	 * @see #getNamedElement()
	 * @generated
	 */
	EAttribute getNamedElement_Name();

	/**
	 * Returns the meta object for class '{@link soundgates.CompositeSoundComponent <em>Composite Sound Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composite Sound Component</em>'.
	 * @see soundgates.CompositeSoundComponent
	 * @generated
	 */
	EClass getCompositeSoundComponent();

	/**
	 * Returns the meta object for the containment reference list '{@link soundgates.CompositeSoundComponent#getEmbeddedComponents <em>Embedded Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Embedded Components</em>'.
	 * @see soundgates.CompositeSoundComponent#getEmbeddedComponents()
	 * @see #getCompositeSoundComponent()
	 * @generated
	 */
	EReference getCompositeSoundComponent_EmbeddedComponents();

	/**
	 * Returns the meta object for the containment reference list '{@link soundgates.CompositeSoundComponent#getLinks <em>Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Links</em>'.
	 * @see soundgates.CompositeSoundComponent#getLinks()
	 * @see #getCompositeSoundComponent()
	 * @generated
	 */
	EReference getCompositeSoundComponent_Links();

	/**
	 * Returns the meta object for the containment reference list '{@link soundgates.CompositeSoundComponent#getDelegations <em>Delegations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Delegations</em>'.
	 * @see soundgates.CompositeSoundComponent#getDelegations()
	 * @see #getCompositeSoundComponent()
	 * @generated
	 */
	EReference getCompositeSoundComponent_Delegations();

	/**
	 * Returns the meta object for the reference list '{@link soundgates.CompositeSoundComponent#getConnections <em>Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Connections</em>'.
	 * @see soundgates.CompositeSoundComponent#getConnections()
	 * @see #getCompositeSoundComponent()
	 * @generated
	 */
	EReference getCompositeSoundComponent_Connections();

	/**
	 * Returns the meta object for class '{@link soundgates.AtomicSoundComponent <em>Atomic Sound Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Atomic Sound Component</em>'.
	 * @see soundgates.AtomicSoundComponent
	 * @generated
	 */
	EClass getAtomicSoundComponent();

	/**
	 * Returns the meta object for the map '{@link soundgates.AtomicSoundComponent#getFloatProperties <em>Float Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Float Properties</em>'.
	 * @see soundgates.AtomicSoundComponent#getFloatProperties()
	 * @see #getAtomicSoundComponent()
	 * @generated
	 */
	EReference getAtomicSoundComponent_FloatProperties();

	/**
	 * Returns the meta object for the map '{@link soundgates.AtomicSoundComponent#getIntegerProperties <em>Integer Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Integer Properties</em>'.
	 * @see soundgates.AtomicSoundComponent#getIntegerProperties()
	 * @see #getAtomicSoundComponent()
	 * @generated
	 */
	EReference getAtomicSoundComponent_IntegerProperties();

	/**
	 * Returns the meta object for the map '{@link soundgates.AtomicSoundComponent#getBooleanProperties <em>Boolean Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Boolean Properties</em>'.
	 * @see soundgates.AtomicSoundComponent#getBooleanProperties()
	 * @see #getAtomicSoundComponent()
	 * @generated
	 */
	EReference getAtomicSoundComponent_BooleanProperties();

	/**
	 * Returns the meta object for the attribute '{@link soundgates.AtomicSoundComponent#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see soundgates.AtomicSoundComponent#getType()
	 * @see #getAtomicSoundComponent()
	 * @generated
	 */
	EAttribute getAtomicSoundComponent_Type();

	/**
	 * Returns the meta object for class '{@link soundgates.Connection <em>Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection</em>'.
	 * @see soundgates.Connection
	 * @generated
	 */
	EClass getConnection();

	/**
	 * Returns the meta object for the reference '{@link soundgates.Connection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see soundgates.Connection#getSource()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_Source();

	/**
	 * Returns the meta object for the reference '{@link soundgates.Connection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see soundgates.Connection#getTarget()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_Target();

	/**
	 * Returns the meta object for the reference '{@link soundgates.Connection#getParentComponent <em>Parent Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent Component</em>'.
	 * @see soundgates.Connection#getParentComponent()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_ParentComponent();

	/**
	 * Returns the meta object for class '{@link soundgates.Delegation <em>Delegation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Delegation</em>'.
	 * @see soundgates.Delegation
	 * @generated
	 */
	EClass getDelegation();

	/**
	 * Returns the meta object for class '{@link soundgates.Link <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link</em>'.
	 * @see soundgates.Link
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
	 * Returns the meta object for enum '{@link soundgates.Kind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Kind</em>'.
	 * @see soundgates.Kind
	 * @generated
	 */
	EEnum getKind();

	/**
	 * Returns the meta object for enum '{@link soundgates.Direction <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Direction</em>'.
	 * @see soundgates.Direction
	 * @generated
	 */
	EEnum getDirection();

	/**
	 * Returns the meta object for enum '{@link soundgates.DataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Data Type</em>'.
	 * @see soundgates.DataType
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
	SoundgatesFactory getSoundgatesFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link soundgates.impl.PatchImpl <em>Patch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.impl.PatchImpl
		 * @see soundgates.impl.SoundgatesPackageImpl#getPatch()
		 * @generated
		 */
		EClass PATCH = eINSTANCE.getPatch();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATCH__ELEMENTS = eINSTANCE.getPatch_Elements();

		/**
		 * The meta object literal for the '{@link soundgates.impl.ElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.impl.ElementImpl
		 * @see soundgates.impl.SoundgatesPackageImpl#getElement()
		 * @generated
		 */
		EClass ELEMENT = eINSTANCE.getElement();

		/**
		 * The meta object literal for the '{@link soundgates.impl.SoundComponentImpl <em>Sound Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.impl.SoundComponentImpl
		 * @see soundgates.impl.SoundgatesPackageImpl#getSoundComponent()
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
		 * The meta object literal for the '{@link soundgates.impl.PortImpl <em>Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.impl.PortImpl
		 * @see soundgates.impl.SoundgatesPackageImpl#getPort()
		 * @generated
		 */
		EClass PORT = eINSTANCE.getPort();

		/**
		 * The meta object literal for the '<em><b>Outgoing Connection</b></em>' reference feature.
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
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT__KIND = eINSTANCE.getPort_Kind();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PORT__DATA_TYPE = eINSTANCE.getPort_DataType();

		/**
		 * The meta object literal for the '{@link soundgates.impl.NamedElementImpl <em>Named Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.impl.NamedElementImpl
		 * @see soundgates.impl.SoundgatesPackageImpl#getNamedElement()
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
		 * The meta object literal for the '{@link soundgates.impl.CompositeSoundComponentImpl <em>Composite Sound Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.impl.CompositeSoundComponentImpl
		 * @see soundgates.impl.SoundgatesPackageImpl#getCompositeSoundComponent()
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
		 * The meta object literal for the '<em><b>Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITE_SOUND_COMPONENT__CONNECTIONS = eINSTANCE.getCompositeSoundComponent_Connections();

		/**
		 * The meta object literal for the '{@link soundgates.impl.AtomicSoundComponentImpl <em>Atomic Sound Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.impl.AtomicSoundComponentImpl
		 * @see soundgates.impl.SoundgatesPackageImpl#getAtomicSoundComponent()
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
		 * The meta object literal for the '<em><b>Boolean Properties</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ATOMIC_SOUND_COMPONENT__BOOLEAN_PROPERTIES = eINSTANCE.getAtomicSoundComponent_BooleanProperties();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ATOMIC_SOUND_COMPONENT__TYPE = eINSTANCE.getAtomicSoundComponent_Type();

		/**
		 * The meta object literal for the '{@link soundgates.impl.ConnectionImpl <em>Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.impl.ConnectionImpl
		 * @see soundgates.impl.SoundgatesPackageImpl#getConnection()
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
		 * The meta object literal for the '<em><b>Parent Component</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__PARENT_COMPONENT = eINSTANCE.getConnection_ParentComponent();

		/**
		 * The meta object literal for the '{@link soundgates.impl.DelegationImpl <em>Delegation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.impl.DelegationImpl
		 * @see soundgates.impl.SoundgatesPackageImpl#getDelegation()
		 * @generated
		 */
		EClass DELEGATION = eINSTANCE.getDelegation();

		/**
		 * The meta object literal for the '{@link soundgates.impl.LinkImpl <em>Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.impl.LinkImpl
		 * @see soundgates.impl.SoundgatesPackageImpl#getLink()
		 * @generated
		 */
		EClass LINK = eINSTANCE.getLink();

		/**
		 * The meta object literal for the '{@link soundgates.impl.EStringToEFloatObjectImpl <em>EString To EFloat Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.impl.EStringToEFloatObjectImpl
		 * @see soundgates.impl.SoundgatesPackageImpl#getEStringToEFloatObject()
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
		 * The meta object literal for the '{@link soundgates.impl.EStringToEIntegerObjectImpl <em>EString To EInteger Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.impl.EStringToEIntegerObjectImpl
		 * @see soundgates.impl.SoundgatesPackageImpl#getEStringToEIntegerObject()
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
		 * The meta object literal for the '{@link soundgates.impl.EStringToEBooleanObjectImpl <em>EString To EBoolean Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.impl.EStringToEBooleanObjectImpl
		 * @see soundgates.impl.SoundgatesPackageImpl#getEStringToEBooleanObject()
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
		 * The meta object literal for the '{@link soundgates.Kind <em>Kind</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.Kind
		 * @see soundgates.impl.SoundgatesPackageImpl#getKind()
		 * @generated
		 */
		EEnum KIND = eINSTANCE.getKind();

		/**
		 * The meta object literal for the '{@link soundgates.Direction <em>Direction</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.Direction
		 * @see soundgates.impl.SoundgatesPackageImpl#getDirection()
		 * @generated
		 */
		EEnum DIRECTION = eINSTANCE.getDirection();

		/**
		 * The meta object literal for the '{@link soundgates.DataType <em>Data Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.DataType
		 * @see soundgates.impl.SoundgatesPackageImpl#getDataType()
		 * @generated
		 */
		EEnum DATA_TYPE = eINSTANCE.getDataType();

	}

} //SoundgatesPackage
