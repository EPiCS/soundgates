package soundgates.simulation;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.handlers.HandlerUtil;

public class ControlSimulationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (ProcessStore.currentCodeGen != null){
			ControlDialog dialog = new ControlDialog(HandlerUtil.getActiveShell(event));

			dialog.setIoComponents(ProcessStore.currentCodeGen.getIoComponents());
			dialog.create();

			dialog.open();
		} else {
			MessageBox message = new MessageBox(HandlerUtil.getActiveShell(event));
			message.setMessage("There seems to be no simulation running.");
			message.setText("No simulation running.");
			message.open();
		}

		return null;
	}

}
