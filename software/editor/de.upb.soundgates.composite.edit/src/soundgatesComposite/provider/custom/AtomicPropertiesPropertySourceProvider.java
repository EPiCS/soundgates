package soundgatesComposite.provider.custom;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;

/**
 * This class has something to with building a custom properties view for the
 * different AtomicSoundComponent properties (float, bool, int properties) TODO
 * was genau macht das hier?
 * 
 * @author gwue
 * 
 */
public class AtomicPropertiesPropertySourceProvider implements
		IPropertySourceProvider {
	private AdapterFactory adapterFactory;

	public AtomicPropertiesPropertySourceProvider(AdapterFactory adapterFactor) {
		this.adapterFactory = adapterFactor;
	}

	@Override
	public IPropertySource getPropertySource(Object object) {
		if (object instanceof IPropertySource) {
			return (IPropertySource) object;
		} else {
			IItemPropertySource itemPropertySource = (IItemPropertySource) (object instanceof EObject
					&& ((EObject) object).eClass() == null ? null
					: adapterFactory.adapt(object, IItemPropertySource.class));
			return itemPropertySource != null ? createPropertySource(object,
					itemPropertySource) : null;
		}
	}

	protected IPropertySource createPropertySource(Object object,
			IItemPropertySource itemPropertySource) {
		// returns the custom property source
		return new AtomicPropertiesPropertySource(object, itemPropertySource);
	}
}
