/**
 */
package soundgates;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
	 * The meta object id for the '{@link soundgates.impl.LinkImpl <em>Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.LinkImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getLink()
	 * @generated
	 */
	int LINK = 2;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__SOURCE = ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__TARGET = ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parent Component</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK__PARENT_COMPONENT = ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LINK_FEATURE_COUNT = ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link soundgates.impl.NamedElementImpl <em>Named Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.NamedElementImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getNamedElement()
	 * @generated
	 */
	int NAMED_ELEMENT = 5;

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
	int SOUND_COMPONENT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_COMPONENT__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Input Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_COMPONENT__INPUT_PORTS = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Output Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_COMPONENT__OUTPUT_PORTS = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parent Component</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_COMPONENT__PARENT_COMPONENT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Sound Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOUND_COMPONENT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link soundgates.impl.PortImpl <em>Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.PortImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getPort()
	 * @generated
	 */
	int PORT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__NAME = NAMED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Outgoing Link</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__OUTGOING_LINK = NAMED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Incoming Link</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__INCOMING_LINK = NAMED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Receiving Component</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__RECEIVING_COMPONENT = NAMED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Sending Component</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT__SENDING_COMPONENT = NAMED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PORT_FEATURE_COUNT = NAMED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link soundgates.impl.CompositeSoundComponentImpl <em>Composite Sound Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.CompositeSoundComponentImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getCompositeSoundComponent()
	 * @generated
	 */
	int COMPOSITE_SOUND_COMPONENT = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SOUND_COMPONENT__NAME = SOUND_COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Input Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SOUND_COMPONENT__INPUT_PORTS = SOUND_COMPONENT__INPUT_PORTS;

	/**
	 * The feature id for the '<em><b>Output Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SOUND_COMPONENT__OUTPUT_PORTS = SOUND_COMPONENT__OUTPUT_PORTS;

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
	 * The number of structural features of the '<em>Composite Sound Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_SOUND_COMPONENT_FEATURE_COUNT = SOUND_COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link soundgates.impl.AtomicSoundComponentImpl <em>Atomic Sound Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see soundgates.impl.AtomicSoundComponentImpl
	 * @see soundgates.impl.SoundgatesPackageImpl#getAtomicSoundComponent()
	 * @generated
	 */
	int ATOMIC_SOUND_COMPONENT = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT__NAME = SOUND_COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Input Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT__INPUT_PORTS = SOUND_COMPONENT__INPUT_PORTS;

	/**
	 * The feature id for the '<em><b>Output Ports</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT__OUTPUT_PORTS = SOUND_COMPONENT__OUTPUT_PORTS;

	/**
	 * The feature id for the '<em><b>Parent Component</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT__PARENT_COMPONENT = SOUND_COMPONENT__PARENT_COMPONENT;

	/**
	 * The number of structural features of the '<em>Atomic Sound Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ATOMIC_SOUND_COMPONENT_FEATURE_COUNT = SOUND_COMPONENT_FEATURE_COUNT + 0;


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
	 * Returns the meta object for class '{@link soundgates.Link <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Link</em>'.
	 * @see soundgates.Link
	 * @generated
	 */
	EClass getLink();

	/**
	 * Returns the meta object for the reference '{@link soundgates.Link#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see soundgates.Link#getSource()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Source();

	/**
	 * Returns the meta object for the reference '{@link soundgates.Link#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see soundgates.Link#getTarget()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_Target();

	/**
	 * Returns the meta object for the container reference '{@link soundgates.Link#getParentComponent <em>Parent Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent Component</em>'.
	 * @see soundgates.Link#getParentComponent()
	 * @see #getLink()
	 * @generated
	 */
	EReference getLink_ParentComponent();

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
	 * Returns the meta object for the containment reference list '{@link soundgates.SoundComponent#getInputPorts <em>Input Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Input Ports</em>'.
	 * @see soundgates.SoundComponent#getInputPorts()
	 * @see #getSoundComponent()
	 * @generated
	 */
	EReference getSoundComponent_InputPorts();

	/**
	 * Returns the meta object for the containment reference list '{@link soundgates.SoundComponent#getOutputPorts <em>Output Ports</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Output Ports</em>'.
	 * @see soundgates.SoundComponent#getOutputPorts()
	 * @see #getSoundComponent()
	 * @generated
	 */
	EReference getSoundComponent_OutputPorts();

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
	 * Returns the meta object for the reference '{@link soundgates.Port#getOutgoingLink <em>Outgoing Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Outgoing Link</em>'.
	 * @see soundgates.Port#getOutgoingLink()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_OutgoingLink();

	/**
	 * Returns the meta object for the reference '{@link soundgates.Port#getIncomingLink <em>Incoming Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Incoming Link</em>'.
	 * @see soundgates.Port#getIncomingLink()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_IncomingLink();

	/**
	 * Returns the meta object for the container reference '{@link soundgates.Port#getReceivingComponent <em>Receiving Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Receiving Component</em>'.
	 * @see soundgates.Port#getReceivingComponent()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_ReceivingComponent();

	/**
	 * Returns the meta object for the container reference '{@link soundgates.Port#getSendingComponent <em>Sending Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Sending Component</em>'.
	 * @see soundgates.Port#getSendingComponent()
	 * @see #getPort()
	 * @generated
	 */
	EReference getPort_SendingComponent();

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
	 * Returns the meta object for class '{@link soundgates.AtomicSoundComponent <em>Atomic Sound Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Atomic Sound Component</em>'.
	 * @see soundgates.AtomicSoundComponent
	 * @generated
	 */
	EClass getAtomicSoundComponent();

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
		 * The meta object literal for the '{@link soundgates.impl.LinkImpl <em>Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.impl.LinkImpl
		 * @see soundgates.impl.SoundgatesPackageImpl#getLink()
		 * @generated
		 */
		EClass LINK = eINSTANCE.getLink();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__SOURCE = eINSTANCE.getLink_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__TARGET = eINSTANCE.getLink_Target();

		/**
		 * The meta object literal for the '<em><b>Parent Component</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINK__PARENT_COMPONENT = eINSTANCE.getLink_ParentComponent();

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
		 * The meta object literal for the '<em><b>Input Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOUND_COMPONENT__INPUT_PORTS = eINSTANCE.getSoundComponent_InputPorts();

		/**
		 * The meta object literal for the '<em><b>Output Ports</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOUND_COMPONENT__OUTPUT_PORTS = eINSTANCE.getSoundComponent_OutputPorts();

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
		 * The meta object literal for the '<em><b>Outgoing Link</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__OUTGOING_LINK = eINSTANCE.getPort_OutgoingLink();

		/**
		 * The meta object literal for the '<em><b>Incoming Link</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__INCOMING_LINK = eINSTANCE.getPort_IncomingLink();

		/**
		 * The meta object literal for the '<em><b>Receiving Component</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__RECEIVING_COMPONENT = eINSTANCE.getPort_ReceivingComponent();

		/**
		 * The meta object literal for the '<em><b>Sending Component</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PORT__SENDING_COMPONENT = eINSTANCE.getPort_SendingComponent();

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
		 * The meta object literal for the '{@link soundgates.impl.AtomicSoundComponentImpl <em>Atomic Sound Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see soundgates.impl.AtomicSoundComponentImpl
		 * @see soundgates.impl.SoundgatesPackageImpl#getAtomicSoundComponent()
		 * @generated
		 */
		EClass ATOMIC_SOUND_COMPONENT = eINSTANCE.getAtomicSoundComponent();

	}

} //SoundgatesPackage
