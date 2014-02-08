package soundgates.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;

import soundgates.CompositeSoundComponent;
import soundgates.SoundgatesFactory;
import soundgates.diagram.soundcomponents.CompositeSoundComponentLibrary;

/**
 * @generated
 */
public class CompositeSoundComponent2CreateCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	public CompositeSoundComponent2CreateCommand(CreateElementRequest req) {
		super(req.getLabel(), null, req);
	}

	/**
	 * FIXME: replace with setElementToEdit()
	 * @generated
	 */
	protected EObject getElementToEdit() {
		EObject container = ((CreateElementRequest) getRequest())
				.getContainer();
		if (container instanceof View) {
			container = ((View) container).getElement();
		}
		return container;
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		return true;

	}

	/**
	 * @generated NOT
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		CompositeSoundComponent newElement = null;

		//MYTOOL TODO elemente aus XML lesen
		Object name = this.getRequest().getParameter("name");
		if (name != null) {
			newElement = CompositeSoundComponentLibrary.getInstance()
					.createCompositeSoundComponentInstance((String) name);
		} else {
			newElement = SoundgatesFactory.eINSTANCE
					.createCompositeSoundComponent();
			newElement.setName(CompositeSoundComponentLibrary
					.getNumberedName(getElementToEdit().eResource().getURI()
							.lastSegment()));
		}
		//---MYTOOL

		CompositeSoundComponent owner = (CompositeSoundComponent) getElementToEdit();
		owner.getEmbeddedComponents().add(newElement);

		doConfigure(newElement, monitor, info);

		((CreateElementRequest) getRequest()).setNewElement(newElement);

		return CommandResult.newOKCommandResult(newElement);
	}

	/**
	 * @generated
	 */
	protected void doConfigure(CompositeSoundComponent newElement,
			IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		IElementType elementType = ((CreateElementRequest) getRequest())
				.getElementType();
		ConfigureRequest configureRequest = new ConfigureRequest(
				getEditingDomain(), newElement, elementType);
		configureRequest.setClientContext(((CreateElementRequest) getRequest())
				.getClientContext());
		configureRequest.addParameters(getRequest().getParameters());
		ICommand configureCommand = elementType
				.getEditCommand(configureRequest);
		if (configureCommand != null && configureCommand.canExecute()) {
			configureCommand.execute(monitor, info);
		}
	}

}
