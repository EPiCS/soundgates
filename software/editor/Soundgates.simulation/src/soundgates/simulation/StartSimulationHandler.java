package soundgates.simulation;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

public class StartSimulationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
			Iterator it = structuredSelection.iterator();
			while (it.hasNext()){
				Object next = it.next();
				if (next instanceof IResource){
					IProject project = ((IResource)next).getProject();
					String pdexecutable = Platform.getPreferencesService().getString(Activator.PLUGIN_ID, PdPreferencePage.PD_EXECUTABLE, "", null);
					
					String [] pdArguments = Platform.getPreferencesService().getString(Activator.PLUGIN_ID, PdPreferencePage.PD_ARGUMENTS, "", null).split("%");
					
					List<String> executionArguments = new LinkedList<String>();
					executionArguments.add(pdexecutable);
					
					if (pdArguments.length > 0){
						for (int i = 0; i < pdArguments.length; i++) {
							if (pdArguments[i].length() > 0) executionArguments.add(pdArguments[i]);
						}
					}
					
					executionArguments.add("-nogui");
					executionArguments.add("-send");
					executionArguments.add("pd dsp 1");
					executionArguments.add(project.getFolder("pdcode").getFile("patch.pd").getLocation().toOSString());

					if (ProcessStore.currentPureDataProcess == null) {
						ProcessBuilder pb = new ProcessBuilder(executionArguments.toArray(new String [] {}));
						ProcessStore.currentPureDataProcess = pb.start();
						return null;
					}
				}
			}
			
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}

}
