package soundgates.simulation;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.AbstractHandler;

public class StopSimulationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (ProcessStore.currentPureDataProcess != null){
			ProcessStore.currentPureDataProcess.destroy();
			ProcessStore.currentPureDataProcess = null;
		}
		return null;
	}

}
