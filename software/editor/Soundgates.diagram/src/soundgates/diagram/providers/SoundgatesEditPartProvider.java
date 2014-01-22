package soundgates.diagram.providers;

import org.eclipse.gmf.tooling.runtime.providers.DefaultEditPartProvider;

import soundgates.diagram.edit.parts.PatchEditPart;
import soundgates.diagram.edit.parts.SoundgatesEditPartFactory;
import soundgates.diagram.part.SoundgatesVisualIDRegistry;

/**
 * @generated
 */
public class SoundgatesEditPartProvider extends DefaultEditPartProvider {

	/**
	 * @generated
	 */
	public SoundgatesEditPartProvider() {
		super(new SoundgatesEditPartFactory(),
				SoundgatesVisualIDRegistry.TYPED_INSTANCE,
				PatchEditPart.MODEL_ID);
	}

}
