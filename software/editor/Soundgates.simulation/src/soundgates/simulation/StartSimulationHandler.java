package soundgates.simulation;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;

public class StartSimulationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
			Iterator it = structuredSelection.iterator();
			while (it.hasNext()){
				Object next = it.next();
				if (next instanceof IProject){
					IProject project = (IProject)next;
					IFile pdFile= project.getFolder("pdcode").getFile("patch.pd");
					if (ProcessStore.currentPureDataProcess == null) {
						ProcessBuilder pb = new ProcessBuilder(
								new String[] {
										"puredata",
										"-jack",
										"-nogui",
										"-send",
										"pd dsp 1",
										pdFile.getLocation().toFile()
												.getAbsolutePath() });
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
