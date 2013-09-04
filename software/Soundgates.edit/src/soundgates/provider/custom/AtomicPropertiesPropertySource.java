package soundgates.provider.custom;

import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import soundgates.SoundgatesPackage;

public class AtomicPropertiesPropertySource extends PropertySource {

	public AtomicPropertiesPropertySource(Object object,
			IItemPropertySource itemPropertySource) {
		super(object, itemPropertySource);
	}

	@Override
	protected IPropertyDescriptor createPropertyDescriptor(
			IItemPropertyDescriptor itemPropertyDescriptor) {
		SoundgatesPackage pkg = SoundgatesPackage.eINSTANCE;
		Object feature = itemPropertyDescriptor.getFeature(object);
		//TODO Int und Bool Properties hinzufügen
		if (			 SoundgatesPackage.Literals.ESTRING_TO_EFLOAT_OBJECT__VALUE.equals(feature)) {
			return new FloatPropertiesPropertyDescriptor(object,
					itemPropertyDescriptor);
		} else {
			return super.createPropertyDescriptor(itemPropertyDescriptor);
		}
	}
}