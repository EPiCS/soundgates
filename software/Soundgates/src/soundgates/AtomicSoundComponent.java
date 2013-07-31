/**
 */
package soundgates;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Atomic Sound Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Atomic sound-component
 * <!-- end-model-doc -->
 *
 *
 * @see soundgates.SoundgatesPackage#getAtomicSoundComponent()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='RightLinksOnPorts NumPortsTest'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL RightLinksOnPorts='(self.inputPorts->forAll( not (incomingLink=null) and outgoingLink=null)) and (self.outputPorts->forAll( not (outgoingLink=null) and incomingLink=null))' NumPortsTest='self.inputPorts->size()=1'"
 * @generated
 */
public interface AtomicSoundComponent extends SoundComponent {
} // AtomicSoundComponent
