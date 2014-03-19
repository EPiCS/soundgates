package soundgatesComposite.diagram.edit.policies;

import java.util.Iterator;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.edit.policies.reparent.CreationEditPolicyWithCustomReparent;
import org.eclipse.gmf.tooling.runtime.structure.DiagramStructure;

public class SoundgatesCreationEditPolicyWithCustomReparent 
	extends CreationEditPolicyWithCustomReparent {

	public SoundgatesCreationEditPolicyWithCustomReparent(
			DiagramStructure diagramStructure) {
		super(diagramStructure);
	}
	
	@Override
	protected Command getCreateCommand(CreateViewRequest request) {
        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
                .getEditingDomain();
            CompositeTransactionalCommand cc = new CompositeTransactionalCommand(
                editingDomain, DiagramUIMessages.AddCommand_Label);
            
            Iterator descriptors = request.getViewDescriptors().iterator();
            getHost();
    		while (descriptors.hasNext()) {
    			CreateViewRequest.ViewDescriptor descriptor =
    				(CreateViewRequest.ViewDescriptor)descriptors.next();
    			
    			CreateCommand createCommand;
    			if(descriptor.getSemanticHint().equals("3004")){
	    			createCommand =
	    				new CreateCommand(editingDomain,
	    					descriptor, 
	    					(View)(getHost().getParent().getModel()));
    			}
    			else{
	    			createCommand =
		    				new CreateCommand(editingDomain,
		    					descriptor, 
		    					(View)(getHost().getModel()));
    			}

    			cc.compose(createCommand);
    		}
    	return new ICommandProxy(cc.reduce());
	}

}
