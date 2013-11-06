package soundgates.diagram.sheet;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.gmf.tooling.runtime.sheet.DefaultPropertySection;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

import soundgates.provider.custom.AtomicPropertiesPropertySource;

/**
 * @generated
 */
public class SoundgatesPropertySection extends DefaultPropertySection implements
		IPropertySourceProvider {

	/**
	 * Modify/unwrap selection.
	 * @generated
	 */
	@Override
	protected Object transformSelection(Object selected) {
		selected = /*super.*/transformSelectionToDomain(selected);
		return selected;
	}

	@Override
	/**
	 * used to override the property sheet
	 */
	public IPropertySource getPropertySource(Object object) {
		if (object instanceof IPropertySource) {
			return (IPropertySource) object;
		}
		AdapterFactory af = getAdapterFactory(object);
		if (af != null) {
			IItemPropertySource ips = (IItemPropertySource) af.adapt(object,
					IItemPropertySource.class);
			if (ips != null) {
				//Return an instance of our own PropertySource implementation here.
				return new AtomicPropertiesPropertySource(object, ips);
			}
		}
		if (object instanceof IAdaptable) {
			return (IPropertySource) ((IAdaptable) object)
					.getAdapter(IPropertySource.class);
		}
		return null;
	}

}
