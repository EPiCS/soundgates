package soundgatesComposite.provider.custom;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import soundgatesComposite.SoundgatesCompositePackage;

public class AtomicPropertiesPropertySource extends PropertySource {

	public AtomicPropertiesPropertySource(Object object,
			IItemPropertySource itemPropertySource) {
		super(object, itemPropertySource);
	}

	@Override
	protected IPropertyDescriptor createPropertyDescriptor(
			IItemPropertyDescriptor itemPropertyDescriptor) {
		SoundgatesCompositePackage pkg = SoundgatesCompositePackage.eINSTANCE;
		Object feature = itemPropertyDescriptor.getFeature(object);
		if (
				SoundgatesCompositePackage.Literals.ESTRING_TO_EFLOAT_OBJECT__VALUE
						.equals(feature)
				|| 
				SoundgatesCompositePackage.Literals.ESTRING_TO_EINTEGER_OBJECT__VALUE
						.equals(feature)
				|| 
				SoundgatesCompositePackage.Literals.ESTRING_TO_EBOOLEAN_OBJECT__VALUE
						.equals(feature) 
				||
				SoundgatesCompositePackage.Literals.ESTRING_TO_ESTRING__VALUE
						.equals(feature)){
			return new CustomPropertiesPropertyDescriptor(object,
					itemPropertyDescriptor);
		} else {
			return super.createPropertyDescriptor(itemPropertyDescriptor);
		}
	}

	@Override
	public Object getPropertyValue(Object id) {
		return super.getPropertyValue(id);
	}
}