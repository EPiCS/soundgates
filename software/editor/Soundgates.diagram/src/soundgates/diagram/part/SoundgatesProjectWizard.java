package soundgates.diagram.part;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class SoundgatesProjectWizard extends Wizard implements INewWizard {

	private SoundgatesProjectWizardPage page;
	
	public SoundgatesProjectWizard() {
		setWindowTitle("Soundgates Project Wizard");
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	@Override
	public void addPages() {
		page = new SoundgatesProjectWizardPage("Soundgates Project");
		page.setTitle("Create new Soundgates Project");
		page.setDescription("Create new Soundgates Project");
		addPage(page);
	}
	
	@Override
	public boolean performFinish() {
		IRunnableWithProgress op = new WorkspaceModifyOperation(null) {
			
			
			//TODO: Default Komponenten im soundcomponents ordner abspeichern
			@Override
			protected void execute(IProgressMonitor monitor) throws CoreException, InvocationTargetException,
					InterruptedException {
				IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
				IProject project = workspaceRoot.getProject(page.getProjectName());
				project.create(monitor);
				project.open(monitor);
				IFolder xmlfolder = project.getFolder("soundcomponents");
				xmlfolder.create(true, true, monitor);
			}
		};
		try {
			getContainer().run(false, true, op);
		}
		catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			return false;
		}
		
		return true;
	}
	
}
