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

import soundgates.AtomicSoundComponent;
import soundgates.Patch;
import soundgates.SoundgatesFactory;

/**
 * @generated
 */
public class AtomicSoundComponentCreateCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	public AtomicSoundComponentCreateCommand(CreateElementRequest req) {
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
		AtomicSoundComponent newElement = SoundgatesFactory.eINSTANCE
				.createAtomicSoundComponent();

		Patch owner = (Patch) getElementToEdit();
		owner.getElements().add(newElement);

		doConfigure(newElement, monitor, info);

		((CreateElementRequest) getRequest()).setNewElement(newElement);

		//MYTOOL TODO elemente aus XML lesen
		Object concreteType = this.getRequest().getParameter("concreteType");
		System.out.println(concreteType);
		if (concreteType != null) {
			try {
				int i = Integer.parseInt(concreteType.toString().split(" ")[1]);
				newElement.setName(concreteType.toString());
				for (int j = 0; j < i; j++) { 
					newElement.getPorts().add(SoundgatesFactory.eINSTANCE.createPort());
				}
			}
			catch(Exception e) {}
		}
		//---MYTOOL
		return CommandResult.newOKCommandResult(newElement);
	}

	/**
	 * @generated
	 */
	protected void doConfigure(AtomicSoundComponent newElement,
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
