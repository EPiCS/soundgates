/**
 */
package soundgates.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import soundgates.CompositeSoundComponent;
import soundgates.Link;
import soundgates.SoundComponent;
import soundgates.SoundgatesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composite Sound Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link soundgates.impl.CompositeSoundComponentImpl#getEmbeddedComponents <em>Embedded Components</em>}</li>
 *   <li>{@link soundgates.impl.CompositeSoundComponentImpl#getLinks <em>Links</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CompositeSoundComponentImpl extends SoundComponentImpl implements CompositeSoundComponent {
	/**
	 * The cached value of the '{@link #getEmbeddedComponents() <em>Embedded Components</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmbeddedComponents()
	 * @generated
	 * @ordered
	 */
	protected EList<SoundComponent> embeddedComponents;

	/**
	 * The cached value of the '{@link #getLinks() <em>Links</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLinks()
	 * @generated
	 * @ordered
	 */
	protected EList<Link> links;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompositeSoundComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SoundgatesPackage.Literals.COMPOSITE_SOUND_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SoundComponent> getEmbeddedComponents() {
		if (embeddedComponents == null) {
			embeddedComponents = new EObjectContainmentWithInverseEList<SoundComponent>(SoundComponent.class, this, SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__EMBEDDED_COMPONENTS, SoundgatesPackage.SOUND_COMPONENT__PARENT_COMPONENT);
		}
		return embeddedComponents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Link> getLinks() {
		if (links == null) {
			links = new EObjectContainmentWithInverseEList<Link>(Link.class, this, SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__LINKS, SoundgatesPackage.LINK__PARENT_COMPONENT);
		}
		return links;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__EMBEDDED_COMPONENTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getEmbeddedComponents()).basicAdd(otherEnd, msgs);
			case SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__LINKS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getLinks()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__EMBEDDED_COMPONENTS:
				return ((InternalEList<?>)getEmbeddedComponents()).basicRemove(otherEnd, msgs);
			case SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__LINKS:
				return ((InternalEList<?>)getLinks()).basicRemove(otherEnd, msgs);
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
			case SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__EMBEDDED_COMPONENTS:
				return getEmbeddedComponents();
			case SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__LINKS:
				return getLinks();
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
			case SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__EMBEDDED_COMPONENTS:
				getEmbeddedComponents().clear();
				getEmbeddedComponents().addAll((Collection<? extends SoundComponent>)newValue);
				return;
			case SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__LINKS:
				getLinks().clear();
				getLinks().addAll((Collection<? extends Link>)newValue);
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
			case SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__EMBEDDED_COMPONENTS:
				getEmbeddedComponents().clear();
				return;
			case SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__LINKS:
				getLinks().clear();
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
			case SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__EMBEDDED_COMPONENTS:
				return embeddedComponents != null && !embeddedComponents.isEmpty();
			case SoundgatesPackage.COMPOSITE_SOUND_COMPONENT__LINKS:
				return links != null && !links.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //CompositeSoundComponentImpl
