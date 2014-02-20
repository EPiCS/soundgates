package soundgates.simulation;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public class StartSimulationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI
				.getWorkbench().getActiveWorkbenchWindow()
				.getSelectionService().getSelection();
		@SuppressWarnings("rawtypes")
		Iterator it = structuredSelection.iterator();

		boolean fileFound = false;
		while (it.hasNext()) {
			Object next = it.next();
			IFile modelFile;
			if (next instanceof IFile
					&& ((modelFile = ((IFile) next)).getName().endsWith(".sgd"))) {
				ProcessStore.startSimulation((IFile) modelFile);
				fileFound = true;
				break;
			}
		}
		if(!fileFound){
			MessageBox message = new MessageBox(
					HandlerUtil.getActiveShell(event));
			message.setMessage("Please select a *.sgd file.");
			message.setText("Simulation");
			message.open();
		}
		return null;
	}

}
