package soundgates.simulation;
import java.io.IOException;
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
import org.eclipse.ui.handlers.HandlerUtil;

import soundgates.codegen.CodeGenHelper;
import soundgates.codegen.simulation.Codegen;


public class StartSimulationHandler extends AbstractHandler {

	@SuppressWarnings("rawtypes")
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
			Iterator it = structuredSelection.iterator();
			
			while (it.hasNext()){
				Object next = it.next();
				IFile modelFile;
				if (next instanceof IFile && ((modelFile = ((IFile)next)).getName().endsWith(".soundgates"))){
					IProject project = ((IResource)next).getProject();
					
					Codegen codegen = new Codegen();
					
					try {
						codegen.generate(CodeGenHelper.getPatch(modelFile.getFullPath().toOSString()), project);
						modelFile.getParent().refreshLocal(1, null);
						ControlDialog dialog = new ControlDialog(HandlerUtil.getActiveShell(event));

						dialog.setIoComponents(codegen.getIoComponents());
						dialog.create();
						(ProcessStore.handShakeThread = new HandshakeThread(codegen.getIoComponents())).start();
						simulate(project);
						dialog.open();
						return null;
					} catch (Exception e) {				
						e.printStackTrace();
					}
					
					
				}
			}
			
		} catch (Exception err) {
			err.printStackTrace();
		}
		return null;
	}

	
	private void simulate(IProject project) throws IOException{
		String pdexecutable = Platform.getPreferencesService().getString(Activator.PLUGIN_ID, PdPreferencePage.PD_EXECUTABLE, "", null);
		
		String [] pdArguments = Platform.getPreferencesService().getString(Activator.PLUGIN_ID, PdPreferencePage.PD_ARGUMENTS, "", null).split("%");
		
		List<String> executionArguments = new LinkedList<String>();
		executionArguments.add(pdexecutable);
		
		if (pdArguments.length > 0){
			for (int i = 0; i < pdArguments.length; i++) {
				if (pdArguments[i].length() > 0) executionArguments.add(pdArguments[i]);
			}
		}
		if (Platform.getPreferencesService().getBoolean(Activator.PLUGIN_ID, PdPreferencePage.PD_NOGUI, false, null)){
			executionArguments.add("-nogui");
		}
		executionArguments.add("-send");
		executionArguments.add("pd dsp 1");
		executionArguments.add(project.getFolder("pdcode").getFile("patch.pd").getLocation().toOSString());

		if (ProcessStore.currentPureDataProcess == null) {
			ProcessBuilder pb = new ProcessBuilder(executionArguments.toArray(new String [] {}));
			ProcessStore.currentPureDataProcess = pb.start();
		}
	}

}
