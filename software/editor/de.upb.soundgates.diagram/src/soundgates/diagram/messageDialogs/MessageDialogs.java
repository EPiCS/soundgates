package soundgates.diagram.messageDialogs;

import org.eclipse.jface.dialogs.MessageDialog;

public class MessageDialogs {

	
	// --DIALOGS--
	
	public static boolean replaceExistingCompositeSoundComponentDialog(String name){	
	
	   MessageDialog dialog = new MessageDialog(
			      null, "Composite sound component "+name+" already exists", 
			      null, "Do you want to replace the file "+name+".xml?",
			      MessageDialog.QUESTION,
			      new String[] {"Yes", "No"},
			      0); 
	   
	   int result =  dialog.open();
	   if(result==0) return true;
	   else return false;
	}
	
	public static boolean replaceExistingEMFFile(String name){	
		
		   MessageDialog dialog = new MessageDialog(
				      null, "File \""+name+"\" already exists", 
				      null, "Do you want to replace the file \""+name+"\"?",
				      MessageDialog.QUESTION,
				      new String[] {"Yes", "No"},
				      0); 
		   
		   int result =  dialog.open();
		   if(result==0) return true;
		   else return false;
		}
	
	// --INFO--
	
	public static void compositeSoundComponentWasExported(String name){		
		MessageDialog.openInformation(null, "Success", "The composite sound component \""+name+"\" was exported to file \""+name+".xml\"");
	}

	public static void patchtWasExported(String name){		
		MessageDialog.openInformation(null, "Success", "The patch was exported to file \""+name+"\"");	
	}

	public static void patchtWasImported(String name){		
		MessageDialog.openInformation(null, "Success", "The patch was imported to file \""+name+"\"");	
	}
	
	public static void compositeSoundComponentValidationTrue(String name) {		
		MessageDialog.openInformation(null, "Success", "Composite sound component \""+name+"\" is correct");		
	}

	public static void patchValidationTrue() {		
		MessageDialog.openInformation(null, "Success", "The patch is correct");		
	}
	
	// --WARNINGS--

	public static void compositeSoundComponentHasNoName(){
		MessageDialog.openWarning(null, "Composite sound component has no name", "Please enter a name for the composite sound component.");
	}
	
	public static void notAllPortsHaveAName(String parentCompName){
		MessageDialog.openWarning(null, "Not all ports of \""+parentCompName+"\" have a name", "Please enter a name for each port of the composite sound component \""+parentCompName+"\""); 
	}

	public static void portHasNoOutgoingConnection(String parent, String name) {		
		MessageDialog.openWarning(null, "Port \""+name+"\" of \""+parent+"\" has no outgoing link", "Please create an outgoing connection for the port \""+name+"\"");
	}

	public static void portHasNoIncomingConnection(String parent, String name) {
		MessageDialog.openWarning(null, "Port \""+name+"\" of \""+parent+"\"  has no incoming link", "Please create an incoming connection for the port \""+name+"\"");		
	}
	
	public static void portHasNoDelegation(String parent, String name) {
		MessageDialog.openWarning(null, "Port \""+name+"\" of \""+parent+"\"  has no delegation", "Please create a delegetion for the port \""+name+"\"");		
	}
		
	public static void inPortHasOutgoingConnection(String parent, String name) {
		MessageDialog.openWarning(null, "IN-port \""+name+"\" of \""+parent+"\"  has an outgoing link", "Please delete the outgoing connection from the port \""+name+"\"");		
	}
	
	public static void outPortHasIncomingConnection(String parent, String name) {
		MessageDialog.openWarning(null, "OUT-port \""+name+"\" of \""+parent+"\"  has an incoming link", "Please delete the incoming connection from the port "+name+"\"");		
	}
	
	public static void inPortAsSource(String parent, String port) {		
		MessageDialog.openWarning(null, "Link error", "A link in \""+parent+"\" has the IN-port \""+port+"\" as source.");		
	}
	
	public static void outPortAsTarget(String parent, String port) {		
		MessageDialog.openWarning(null, "Link error", "A link in \""+parent+"\" has the OUT-port \""+port+"\" as target.");		
	}
	
	public static void delegationError(String parent, String source, String target) {		
		MessageDialog.openWarning(null, "A delegation must connect two IN-ports or two OUT-ports", "The delegation in \""+parent+"\" connects the port \""+source+"\" with the port \""+target+"\"");		
	}
	
	public static void delegation2Error(String parent, String source, String target) {		
		MessageDialog.openWarning(null, "", "The delegation in \""+parent+"\" between the port \""+source+"\" and the port \""+target+"\" is incorrect" );		
	}
	
	public static void compositeSoundComponentHasNoOutPort(String name) {		
		MessageDialog.openWarning(null, "Composite sound component \""+name+"\" has no OUT-ports", "Please add at least one OUT-port to the composite sound component \""+name+"\"");			
	}
	
	public static void compositeSoundComponentHasNoEmbeddedComponents(String name) {		
		MessageDialog.openWarning(null, "Composite sound component \""+name+"\" has no embedded components", "Please add at least one embedded component to the composite sound component \""+name+"\"");		
	}	
	
	public static void compositeSoundComponentContaintsIOBlock(String name) {		
		MessageDialog.openWarning(null, "IO components in composite components are not allowed", "Please remove the IO component(s) from the composite sound component \""+name+"\"");		
	}
	
	public static void compositeSoundComponentContaintsSoundOutputBlock(String name) {		
		MessageDialog.openWarning(null, "SoundOutput blocks in composite components are not allowed", "Please remove the SoundOutput block(s) from the composite sound component \""+name+"\"");		
	}
	
	public static void newCompositeSoundComponentContaintsIOBlock() {		
		MessageDialog.openWarning(null, "IO components in composite components are not allowed", "Please unmark the IO component(s)");		
	}
	
	public static void newCompositeSoundComponentContaintsSoundOutputBlock() {		
		MessageDialog.openWarning(null, "SoundOutput blocks in composite components are not allowed", "Please unmark the SoundOutput block(s)");		
	}
	
	public static void ioComponentsMustHaveUniqueNames() {		
		MessageDialog.openWarning(null, "IO components must have unique names", "Please assign each IO component an unique name");		
	}
	
	public static void soundcomponentsFolderMissing(String projectName) {		
		MessageDialog.openWarning(null, "No folder \"soundcomponents\" in the project", "Please add the folder \"soundcomponents\" to the project \""+projectName+"\"");		
	}
	
	public static void atomicComponentMissing(String name) {		
		MessageDialog.openWarning(null, "Missing atomic sound component", "The atomic sound component \""+name+"\" was not found in the folder \"soundcomponents\"");		
	}
	
	public static void compositeComponentMissing(String name) {		
		MessageDialog.openWarning(null, "Missing composite sound component", "The composite sound component \""+name+"\" was not found in the folder \"soundcomponents\" or its embedded component(s) are missing");		
	}

	public static void atomicComponentInCompositeComponentMissing(String name, String parentName) {		
		MessageDialog.openWarning(null, "Missing atomic sound component", "The atomic sound component \""+name+"\" which is embedded in the composite sound component \""+parentName+"\" was not found in the folder \"soundcomponents\"");		
	}
	
	public static void compositeComponentInCompositeComponentMissing(String name, String parentName) {		
		MessageDialog.openWarning(null, "Missing composite sound component", "The composite sound component \""+name+"\" which is embedded in the composite sound component \""+parentName+"\" was not found in the folder \"soundcomponents\" or its embedded component(s) are missing");		
	}

	public static void minValueGreaterThanMaxValue(String name) {
		MessageDialog.openWarning(null, "The minimum value of the IO component "+name+" is greater than its maximum value.","Please set correct values for the IO component "+name);		
	}

	public static void fileNotFound(String filePath) {
		MessageDialog.openWarning(null, "File not found","File "+filePath+" not found");
	}

	public static void soundToControlConnection(String parent, String port1, String port2) {
		MessageDialog.openWarning(null, "Wrong connection","A connection in \""+parent+"\" connects the sound port \""+port1+"\" with the control port \""+port2+"\"");
	}

}
