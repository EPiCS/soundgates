package soundgates.simulation;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gmf.runtime.notation.Diagram;

import soundgates.Patch;
import soundgates.codegen.CodeGenHelper;
import soundgates.codegen.simulation.Codegen;

public class ProcessStore {
	static public Process currentPureDataProcess = null;
	static public Codegen currentCodeGen = null;
	static public HandshakeThread handShakeThread = null;
	
	static public void killSimulation(){
		if (ProcessStore.currentPureDataProcess != null){
			ProcessStore.currentPureDataProcess.destroy();
			ProcessStore.currentPureDataProcess = null;
		}
		if (ProcessStore.handShakeThread != null){
			ProcessStore.handShakeThread.stopMe();
			ProcessStore.handShakeThread = null;
		}
		if (currentCodeGen != null){
			currentCodeGen = null;
		}
	}

	static public void startSimulation(IFile modelFile){
		killSimulation();
		currentCodeGen = new Codegen();
					try {
						IProject project = modelFile.getProject();
						Patch patch = CodeGenHelper.getPatch(modelFile.getFullPath().toOSString());
						Diagram diagram = CodeGenHelper.getDiagram(modelFile.getFullPath().toOSString());
						currentCodeGen.generate(patch, diagram, project);
						modelFile.getParent().refreshLocal(1, null);
						
						(ProcessStore.handShakeThread = new HandshakeThread(currentCodeGen.getIoComponents())).start();
						simulate(project);
					} catch (Exception e) {				
						e.printStackTrace();
					}
					
				
	}
	
	private static void simulate(IProject project) throws IOException{
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
