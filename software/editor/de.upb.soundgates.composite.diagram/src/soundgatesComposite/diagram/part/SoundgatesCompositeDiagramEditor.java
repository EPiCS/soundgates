package soundgatesComposite.diagram.part;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gmf.runtime.common.ui.services.marker.MarkerNavigationService;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.internal.actions.PromptingDeleteAction;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;

import soundgatesComposite.CompositeSoundComponent;
import soundgatesComposite.Connection;
import soundgatesComposite.diagram.edit.commands.EmptyCommand;
import soundgatesComposite.diagram.edit.parts.AtomicSoundComponentEditPart;
import soundgatesComposite.diagram.edit.parts.CompositeSoundComponentEditPart;
import soundgatesComposite.diagram.edit.parts.ConnectionAbstractEditPart;
import soundgatesComposite.diagram.soundcomponents.AtomicSoundComponentLibrary;
import soundgatesComposite.diagram.soundcomponents.CompositeSoundComponentLibrary;

/**
 * @generated
 */
public class SoundgatesCompositeDiagramEditor extends DiagramDocumentEditor
		implements IGotoMarker {

	/**
	 * @generated
	 */
	public static final String ID = "soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final String CONTEXT_ID = "soundgatesComposite.diagram.ui.diagramContext"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public SoundgatesCompositeDiagramEditor() {
		super(true);
	}

	/**
	 * @generated
	 */
	protected String getContextID() {
		return CONTEXT_ID;
	}

	/**
	 * @generated NOT
	 */
	protected PaletteRoot createPaletteRoot(PaletteRoot existingPaletteRoot) {

		// Find out in which project the diagram resides 
		IDiagramDocument dd = this.getDiagramDocument();
		EList l = dd.getEditingDomain().getResourceSet().getResources();
		Resource g = (Resource) l.get(0);

		// Construct a Resource Locator from this URI
		IResource resource = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(new Path(g.getURI().toPlatformString(false)));
		IProject project = resource.getProject();
		IFolder folder = project.getFolder("soundcomponents");

		// Tell the Component Library about the new path
		AtomicSoundComponentLibrary.setXMLFolder(folder);
		CompositeSoundComponentLibrary.setXMLFolder(folder);

		PaletteRoot root = super.createPaletteRoot(existingPaletteRoot);
		new SoundgatesCompositePaletteFactory().fillPalette(root);
		return root;
	}

	@Override
	protected KeyHandler getKeyHandler() {
		// TODO Auto-generated method stub
		KeyHandler keyHandler =  super.getKeyHandler();
		
        ActionRegistry registry = getActionRegistry();
        IAction action;

        action = new SoundgatesPromptingDeleteAction(this);
        action.setText(DiagramUIMessages.DiagramEditor_Delete_from_Diagram);
        registry.registerAction(action);
        getSelectionActions().add(action.getId());
        
        keyHandler.remove(KeyStroke.getPressed(SWT.DEL, 127, 0));
        keyHandler.remove(KeyStroke.getPressed(SWT.DEL, 8, 0));
        
        keyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
                getActionRegistry().getAction(ActionFactory.DELETE.getId()));
        keyHandler.put(KeyStroke.getPressed(SWT.BS, 8, 0),
                getActionRegistry().getAction(ActionFactory.DELETE.getId()));
        
        return keyHandler;
	}
	
	class SoundgatesPromptingDeleteAction extends PromptingDeleteAction {

		public SoundgatesPromptingDeleteAction(IWorkbenchPart part) {
			super(part);
		}
		
		@Override
		public Command createCommand(List objects) {
			if(listContainsEditPartsNotToRemove(objects))
				return new EmptyCommand();
			else
				return super.createCommand(objects);
		}
		
		public boolean listContainsEditPartsNotToRemove(List objects){
			for(Object object : objects){
				if(	object instanceof EditPart)	
				{	
					if(object instanceof AtomicSoundComponentEditPart || 
							object instanceof CompositeSoundComponentEditPart){
						
						EditPart parent = ((EditPart)object).getParent();
						try{
							if(parent.getParent().getParent().getParent() instanceof CompositeSoundComponentEditPart)
								if(!objects.contains(parent.getParent()))
									return true;
						}
						catch(Exception e){
							
						}
					}
					
					else if(object instanceof ConnectionAbstractEditPart){
						Connection connection = ((ConnectionAbstractEditPart) object).getConnection();
						if(connection.eContainer().eContainer() instanceof CompositeSoundComponent)
							return true;
					}
				}
			}
			return false;
		}
		
		

	}
	/**
	 * @generated
	 */
	protected PreferencesHint getPreferencesHint() {
		return soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
	}

	/**
	 * @generated
	 */
	public String getContributorId() {
		return soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin.ID;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class type) {
		if (type == IShowInTargetList.class) {
			return new IShowInTargetList() {
				public String[] getShowInTargetIds() {
					return new String[] { ProjectExplorer.VIEW_ID };
				}
			};
		}
		return super.getAdapter(type);
	}

	/**
	 * @generated
	 */
	protected IDocumentProvider getDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput
				|| input instanceof URIEditorInput) {
			return soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin
					.getInstance().getDocumentProvider();
		}
		return super.getDocumentProvider(input);
	}

	/**
	 * @generated
	 */
	public TransactionalEditingDomain getEditingDomain() {
		IDocument document = getEditorInput() != null ? getDocumentProvider()
				.getDocument(getEditorInput()) : null;
		if (document instanceof IDiagramDocument) {
			return ((IDiagramDocument) document).getEditingDomain();
		}
		return super.getEditingDomain();
	}

	/**
	 * @generated
	 */
	protected void setDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput
				|| input instanceof URIEditorInput) {
			setDocumentProvider(soundgatesComposite.diagram.part.SoundgatesCompositeDiagramEditorPlugin
					.getInstance().getDocumentProvider());
		} else {
			super.setDocumentProvider(input);
		}
	}

	/**
	 * @generated
	 */
	public void gotoMarker(IMarker marker) {
		MarkerNavigationService.getInstance().gotoMarker(this, marker);
	}

	/**
	 * @generated
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * @generated
	 */
	public void doSaveAs() {
		performSaveAs(new NullProgressMonitor());
	}

	/**
	 * @generated
	 */
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		Shell shell = getSite().getShell();
		IEditorInput input = getEditorInput();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		IFile original = input instanceof IFileEditorInput ? ((IFileEditorInput) input)
				.getFile() : null;
		if (original != null) {
			dialog.setOriginalFile(original);
		}
		dialog.create();
		IDocumentProvider provider = getDocumentProvider();
		if (provider == null) {
			// editor has been programmatically closed while the dialog was open
			return;
		}
		if (provider.isDeleted(input) && original != null) {
			String message = NLS
					.bind(soundgatesComposite.diagram.part.Messages.SoundgatesCompositeDiagramEditor_SavingDeletedFile,
							original.getName());
			dialog.setErrorMessage(null);
			dialog.setMessage(message, IMessageProvider.WARNING);
		}
		if (dialog.open() == Window.CANCEL) {
			if (progressMonitor != null) {
				progressMonitor.setCanceled(true);
			}
			return;
		}
		IPath filePath = dialog.getResult();
		if (filePath == null) {
			if (progressMonitor != null) {
				progressMonitor.setCanceled(true);
			}
			return;
		}
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = workspaceRoot.getFile(filePath);
		final IEditorInput newInput = new FileEditorInput(file);
		// Check if the editor is already open
		IEditorMatchingStrategy matchingStrategy = getEditorDescriptor()
				.getEditorMatchingStrategy();
		IEditorReference[] editorRefs = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		for (int i = 0; i < editorRefs.length; i++) {
			if (matchingStrategy.matches(editorRefs[i], newInput)) {
				MessageDialog
						.openWarning(
								shell,
								soundgatesComposite.diagram.part.Messages.SoundgatesCompositeDiagramEditor_SaveAsErrorTitle,
								soundgatesComposite.diagram.part.Messages.SoundgatesCompositeDiagramEditor_SaveAsErrorMessage);
				return;
			}
		}
		boolean success = false;
		try {
			provider.aboutToChange(newInput);
			getDocumentProvider(newInput).saveDocument(progressMonitor,
					newInput,
					getDocumentProvider().getDocument(getEditorInput()), true);
			success = true;
		} catch (CoreException x) {
			IStatus status = x.getStatus();
			if (status == null || status.getSeverity() != IStatus.CANCEL) {
				ErrorDialog
						.openError(
								shell,
								soundgatesComposite.diagram.part.Messages.SoundgatesCompositeDiagramEditor_SaveErrorTitle,
								soundgatesComposite.diagram.part.Messages.SoundgatesCompositeDiagramEditor_SaveErrorMessage,
								x.getStatus());
			}
		} finally {
			provider.changed(newInput);
			if (success) {
				setInput(newInput);
			}
		}
		if (progressMonitor != null) {
			progressMonitor.setCanceled(!success);
		}
	}

	/**
	 * @generated
	 */
	public ShowInContext getShowInContext() {
		return new ShowInContext(getEditorInput(), getNavigatorSelection());
	}

	/**
	 * @generated
	 */
	private ISelection getNavigatorSelection() {
		IDiagramDocument document = getDiagramDocument();
		if (document == null) {
			return StructuredSelection.EMPTY;
		}
		Diagram diagram = document.getDiagram();
		if (diagram == null || diagram.eResource() == null) {
			return StructuredSelection.EMPTY;
		}
		IFile file = WorkspaceSynchronizer.getFile(diagram.eResource());
		if (file != null) {
			soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorItem item = new soundgatesComposite.diagram.navigator.SoundgatesCompositeNavigatorItem(
					diagram, file, false);
			return new StructuredSelection(item);
		}
		return StructuredSelection.EMPTY;
	}

	/**
	 * @generated
	 */
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		soundgatesComposite.diagram.part.DiagramEditorContextMenuProvider provider = new soundgatesComposite.diagram.part.DiagramEditorContextMenuProvider(
				this, getDiagramGraphicalViewer());
		getDiagramGraphicalViewer().setContextMenu(provider);
		getSite().registerContextMenu(ActionIds.DIAGRAM_EDITOR_CONTEXT_MENU,
				provider, getDiagramGraphicalViewer());
	}

}
