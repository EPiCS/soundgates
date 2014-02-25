package soundgatesComposite.diagram.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ParserHintAdapter;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class SoundgatesCompositeParserProvider extends AbstractProvider
		implements IParserProvider {

	/**
	 * @generated
	 */
	private IParser compositeSoundComponentName_5008Parser;

	/**
	 * @generated
	 */
	private IParser getCompositeSoundComponentName_5008Parser() {
		if (compositeSoundComponentName_5008Parser == null) {
			EAttribute[] features = new EAttribute[] { soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
					.getNamedElement_Name() };
			soundgatesComposite.diagram.parsers.MessageFormatParser parser = new soundgatesComposite.diagram.parsers.MessageFormatParser(
					features);
			compositeSoundComponentName_5008Parser = parser;
		}
		return compositeSoundComponentName_5008Parser;
	}

	/**
	 * @generated
	 */
	private IParser portName_5005Parser;

	/**
	 * @generated
	 */
	private IParser getPortName_5005Parser() {
		if (portName_5005Parser == null) {
			EAttribute[] features = new EAttribute[] { soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
					.getNamedElement_Name() };
			soundgatesComposite.diagram.parsers.MessageFormatParser parser = new soundgatesComposite.diagram.parsers.MessageFormatParser(
					features);
			portName_5005Parser = parser;
		}
		return portName_5005Parser;
	}

	/**
	 * @generated
	 */
	private IParser atomicSoundComponentName_5006Parser;

	/**
	 * @generated
	 */
	private IParser getAtomicSoundComponentName_5006Parser() {
		if (atomicSoundComponentName_5006Parser == null) {
			EAttribute[] features = new EAttribute[] { soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
					.getNamedElement_Name() };
			soundgatesComposite.diagram.parsers.MessageFormatParser parser = new soundgatesComposite.diagram.parsers.MessageFormatParser(
					features);
			atomicSoundComponentName_5006Parser = parser;
		}
		return atomicSoundComponentName_5006Parser;
	}

	/**
	 * @generated
	 */
	private IParser compositeSoundComponentName_5007Parser;

	/**
	 * @generated
	 */
	private IParser getCompositeSoundComponentName_5007Parser() {
		if (compositeSoundComponentName_5007Parser == null) {
			EAttribute[] features = new EAttribute[] { soundgatesComposite.SoundgatesCompositePackage.eINSTANCE
					.getNamedElement_Name() };
			soundgatesComposite.diagram.parsers.MessageFormatParser parser = new soundgatesComposite.diagram.parsers.MessageFormatParser(
					features);
			compositeSoundComponentName_5007Parser = parser;
		}
		return compositeSoundComponentName_5007Parser;
	}

	/**
	 * @generated
	 */
	protected IParser getParser(int visualID) {
		switch (visualID) {
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentNameEditPart.VISUAL_ID:
			return getCompositeSoundComponentName_5008Parser();
		case soundgatesComposite.diagram.edit.parts.PortNameEditPart.VISUAL_ID:
			return getPortName_5005Parser();
		case soundgatesComposite.diagram.edit.parts.AtomicSoundComponentNameEditPart.VISUAL_ID:
			return getAtomicSoundComponentName_5006Parser();
		case soundgatesComposite.diagram.edit.parts.CompositeSoundComponentName2EditPart.VISUAL_ID:
			return getCompositeSoundComponentName_5007Parser();
		}
		return null;
	}

	/**
	 * Utility method that consults ParserService
	 * @generated
	 */
	public static IParser getParser(IElementType type, EObject object,
			String parserHint) {
		return ParserService.getInstance().getParser(
				new HintAdapter(type, object, parserHint));
	}

	/**
	 * @generated
	 */
	public IParser getParser(IAdaptable hint) {
		String vid = (String) hint.getAdapter(String.class);
		if (vid != null) {
			return getParser(soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getVisualID(vid));
		}
		View view = (View) hint.getAdapter(View.class);
		if (view != null) {
			return getParser(soundgatesComposite.diagram.part.SoundgatesCompositeVisualIDRegistry
					.getVisualID(view));
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean provides(IOperation operation) {
		if (operation instanceof GetParserOperation) {
			IAdaptable hint = ((GetParserOperation) operation).getHint();
			if (soundgatesComposite.diagram.providers.SoundgatesCompositeElementTypes
					.getElement(hint) == null) {
				return false;
			}
			return getParser(hint) != null;
		}
		return false;
	}

	/**
	 * @generated
	 */
	private static class HintAdapter extends ParserHintAdapter {

		/**
		 * @generated
		 */
		private final IElementType elementType;

		/**
		 * @generated
		 */
		public HintAdapter(IElementType type, EObject object, String parserHint) {
			super(object, parserHint);
			assert type != null;
			elementType = type;
		}

		/**
		 * @generated
		 */
		public Object getAdapter(Class adapter) {
			if (IElementType.class.equals(adapter)) {
				return elementType;
			}
			return super.getAdapter(adapter);
		}
	}

}
