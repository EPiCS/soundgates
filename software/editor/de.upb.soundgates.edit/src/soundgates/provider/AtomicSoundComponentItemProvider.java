/**
 */
package soundgates.provider;


import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;

import soundgates.AtomicSoundComponent;
import soundgates.SoundgatesFactory;
import soundgates.SoundgatesPackage;

/**
 * This is the item provider adapter for a {@link soundgates.AtomicSoundComponent} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class AtomicSoundComponentItemProvider
	extends SoundComponentItemProvider
	implements
		IEditingDomainItemProvider,
		IStructuredItemContentProvider,
		ITreeItemContentProvider,
		IItemLabelProvider,
		IItemPropertySource {
	/**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AtomicSoundComponentItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @author gwue
	 */
	@Override
	public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		// Reset the itemProperty descriptors, else they are loaded only once upon starting the edtior for each class
		// Since instances of AtomicSoundComponents have different sets of properties, each component basically needs it's own provider
		// As of yet I don't think this is possible, therefore we reset the itemPropertyDescriptor and generate a new one everytime for the selected object
		// A generic PropertyEntry descriptor might be able to handle this
		itemPropertyDescriptors = null;
		if (itemPropertyDescriptors == null) {
			super.getPropertyDescriptors(object);

			addTypePropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}	
	
	/**
	 * This adds a property descriptor for the Type feature.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void addTypePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_AtomicSoundComponent_type_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_AtomicSoundComponent_type_feature", "_UI_AtomicSoundComponent_type"),
				 SoundgatesPackage.Literals.ATOMIC_SOUND_COMPONENT__TYPE,
				 false,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

	/**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null) {
			super.getChildrenFeatures(object);
			childrenFeatures.add(SoundgatesPackage.Literals.ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES);
			childrenFeatures.add(SoundgatesPackage.Literals.ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES);
			childrenFeatures.add(SoundgatesPackage.Literals.ATOMIC_SOUND_COMPONENT__STRING_PROPERTIES);
			childrenFeatures.add(SoundgatesPackage.Literals.ATOMIC_SOUND_COMPONENT__USER_STRING_PROPERTIES);
		}
		return childrenFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

	/**
	 * This returns AtomicSoundComponent.gif.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object getImage(Object object) {
		return overlayImage(object, getResourceLocator().getImage("full/obj16/AtomicSoundComponent"));
	}

	/**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getText(Object object) {
		String label = ((AtomicSoundComponent)object).getName();
		return label == null || label.length() == 0 ?
			getString("_UI_AtomicSoundComponent_type") :
			getString("_UI_AtomicSoundComponent_type") + " " + label;
	}

	/**
	 * This handles model notifications by calling {@link #updateChildren} to update any cached
	 * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(AtomicSoundComponent.class)) {
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__TYPE:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES:
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES:
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__STRING_PROPERTIES:
			case SoundgatesPackage.ATOMIC_SOUND_COMPONENT__USER_STRING_PROPERTIES:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

	/**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(SoundgatesPackage.Literals.ATOMIC_SOUND_COMPONENT__FLOAT_PROPERTIES,
				 SoundgatesFactory.eINSTANCE.create(SoundgatesPackage.Literals.ESTRING_TO_EFLOAT_OBJECT)));

		newChildDescriptors.add
			(createChildParameter
				(SoundgatesPackage.Literals.ATOMIC_SOUND_COMPONENT__INTEGER_PROPERTIES,
				 SoundgatesFactory.eINSTANCE.create(SoundgatesPackage.Literals.ESTRING_TO_EINTEGER_OBJECT)));

		newChildDescriptors.add
			(createChildParameter
				(SoundgatesPackage.Literals.ATOMIC_SOUND_COMPONENT__STRING_PROPERTIES,
				 SoundgatesFactory.eINSTANCE.create(SoundgatesPackage.Literals.ESTRING_TO_ESTRING)));

		newChildDescriptors.add
			(createChildParameter
				(SoundgatesPackage.Literals.ATOMIC_SOUND_COMPONENT__USER_STRING_PROPERTIES,
				 SoundgatesFactory.eINSTANCE.create(SoundgatesPackage.Literals.ESTRING_TO_ESTRING)));
	}



}
