package soundgates.diagram.actions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;

import soundgates.AtomicSoundComponent;
import soundgates.CompositeSoundComponent;
import soundgates.Connection;
import soundgates.DataType;
import soundgates.Direction;
import soundgates.Link;
import soundgates.Port;
import soundgates.SoundComponent;
import soundgates.VHDLPortDescriptor;
import soundgates.diagram.actions.codegen.vhdl.VHDLDatatype;
import soundgates.diagram.actions.codegen.vhdl.VHDLElement;
import soundgates.diagram.actions.codegen.vhdl.VHDLEntity;
import soundgates.diagram.actions.codegen.vhdl.VHDLPort;
import soundgates.diagram.actions.codegen.vhdl.VHDLPortAssignment;
import soundgates.diagram.actions.codegen.vhdl.VHDLRange;
import soundgates.diagram.actions.codegen.vhdl.VHDLStandardDataType;
import soundgates.diagram.actions.codegen.vhdl.VHDLStringRepresentation;
import soundgates.diagram.actions.codegen.vhdl.decl.VHDLComponentDeclaration;
import soundgates.diagram.actions.codegen.vhdl.decl.VHDLSignalDeclaration;
import soundgates.diagram.actions.codegen.vhdl.decl.VHDLVariableDeclaration;
import soundgates.diagram.actions.codegen.vhdl.decl.util.VHDLUtils;
import soundgates.diagram.actions.codegen.vhdl.statements.VHDLComponentInstantiationStatement;
import soundgates.diagram.actions.codegen.vhdl.statements.VHDLProcessStatement;
import soundgates.diagram.actions.codegen.vhdl.statements.VHDLSequentialStatement;
import soundgates.diagram.actions.codegen.vhdl.statements.VHDLSignalAssignmentStatement;
import soundgates.diagram.edit.parts.CompositeSoundComponentEditPart;
import soundgates.diagram.part.SoundgatesDiagramEditorPlugin;
import soundgates.diagram.soundcomponents.AtomicSoundComponentLibrary;
import soundgates.diagram.soundcomponents.AtomicSoundComponentXMLHandler;



public class VHDLExportAction implements IObjectActionDelegate{
	
	private static String 	hwtTemplateFilename = "hwt_template.vhd";
	
	private static String   vhdl_file_extension = ".vhd";
	
	@Override
	public void run(IAction action) {
		IStructuredSelection structuredSelection = (IStructuredSelection) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		Iterator<?> files = structuredSelection.iterator();
		
		while (files.hasNext()) {			
			 Object selectedObject = files.next(); 
			 
			 if (selectedObject instanceof CompositeSoundComponentEditPart){
				 
				 CompositeSoundComponentEditPart compositeSoundComponentAbstractEditPart =
						 (CompositeSoundComponentEditPart) selectedObject;
				 
				 
				 CompositeSoundComponent compositeSoundComponent = compositeSoundComponentAbstractEditPart.getCompositeSoundComponent();			 
				 
				 saveTextToFile(getVHDLText(compositeSoundComponent), compositeSoundComponent.getName() + vhdl_file_extension);
			 }
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {	
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {		
	}

	private String getVHDLText(CompositeSoundComponent compositeSoundComponent){

		VHDLStringRepresentation	hwttemplate  = new VHDLStringRepresentation();
		
		VHDLSignalDeclaration		clk			 = new VHDLSignalDeclaration("clk", new VHDLDatatype(VHDLStandardDataType.STD_LOGIC.name()));
		VHDLSignalDeclaration		rst			 = new VHDLSignalDeclaration("rst", new VHDLDatatype(VHDLStandardDataType.STD_LOGIC.name()));
		VHDLSignalDeclaration		ce			 = new VHDLSignalDeclaration("ce", new VHDLDatatype(VHDLStandardDataType.STD_LOGIC.name()));
				
		VHDLEntity					hwtentity    = null;
		
		IFile templatefile = AtomicSoundComponentLibrary.getXMLFolder().getProject().getFile(hwtTemplateFilename);
		
		if(!templatefile.exists()){
			
			CoreException exception = new CoreException( new Status(IStatus.ERROR, SoundgatesDiagramEditorPlugin.ID,
															"Hardware thread template file does not exist!"));
			StatusManager.getManager().handle(exception, SoundgatesDiagramEditorPlugin.ID);
			
		}else{
			/* Read template file */
			try {
				InputStream    	in     	= templatefile.getContents();
				BufferedReader 	reader 	= new BufferedReader(new InputStreamReader(in));
				String 			line	= "";
				
				while ((line = reader.readLine()) != null) { 
		            hwttemplate.addLine(line);
		        }
				
				in.close();
				
			} catch (CoreException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}			
		}
		
		/* Replace entity name */
		hwtentity = new VHDLEntity(compositeSoundComponent.getName(), hwttemplate);
		
		
		/* Add components to vhdl template */
		for (SoundComponent soundComponent : compositeSoundComponent.getEmbeddedComponents()){

			if(soundComponent instanceof AtomicSoundComponent){
				ArrayList<VHDLElement> vhdlports 		  = new ArrayList<VHDLElement>();

				AtomicSoundComponent atomicSoundComponent = ((AtomicSoundComponent) soundComponent);				
				String implname 				 		  = VHDLUtils.getImplName(soundComponent);
				String modelname 						  = atomicSoundComponent.getName();
				
				/* Create VHDL component declaration/instantiation */
				for(Port modelport : soundComponent.getPorts()){
									
					VHDLPortDescriptor xmlport = getVHDLPort(atomicSoundComponent, modelport);

					if( null == xmlport ){						 
						StatusManager.getManager().handle(
								new Status(IStatus.ERROR, SoundgatesDiagramEditorPlugin.ID, 
								"Could not get vhdl port from component description for model port " + modelport.getName()));				
					}else{
						
						vhdlports.add(new VHDLPort(xmlport));						
					}					
				}
				
				VHDLPortDescriptor clkportdesc = getVHDLPort(atomicSoundComponent, VHDLPortDescriptor.SIGIS.CLK);
				VHDLPortDescriptor rstportdesc = getVHDLPort(atomicSoundComponent, VHDLPortDescriptor.SIGIS.RST);
				VHDLPortDescriptor ceportdesc  = getVHDLPort(atomicSoundComponent, VHDLPortDescriptor.SIGIS.CE);
				
				VHDLPort		   clkport     = null;
				VHDLPort		   rstport     = null;
				VHDLPort		   ceport      = null;
				
				/* Check if all required ports are available */
				if(null == clkportdesc || null == rstportdesc || null == ceportdesc)
				{
					String missingports = "";
					
					if(null == clkportdesc){
						missingports += VHDLPortDescriptor.SIGIS.CLK.name();
					}
					
					if(null == rstportdesc){
						missingports += (missingports.isEmpty() ? "" : ", ") + VHDLPortDescriptor.SIGIS.RST.name();
					}
					
					if(null == ceportdesc){
						missingports += (missingports.isEmpty() ? "" : ", ") + VHDLPortDescriptor.SIGIS.CE.name();						
					}
					
					StatusManager.getManager().handle(new Status(IStatus.ERROR, 
							SoundgatesDiagramEditorPlugin.ID, 
							"Could not get port(s): " + missingports + " for " + modelname + "(" + implname + ")"), 
							StatusManager.LOG);
				}else{
					clkport = new VHDLPort(clkportdesc);
					rstport = new VHDLPort(rstportdesc);
					ceport  = new VHDLPort(ceportdesc);
					
					vhdlports.add(clkport);
					vhdlports.add(rstport);
					vhdlports.add(ceport);					
				}
				
				
				
				VHDLComponentDeclaration component_decl = VHDLUtils.<VHDLComponentDeclaration>getElementByLabel(hwtentity.getElements(), implname);
				
				if(null == component_decl){
					
					 component_decl = new VHDLComponentDeclaration(implname, vhdlports);
					 hwtentity.addElement(component_decl);
				}
				
				VHDLComponentInstantiationStatement	component_instance 	= new VHDLComponentInstantiationStatement(modelname, component_decl);
				
				VHDLPortAssignment clk_portassign 						= new VHDLPortAssignment(clkport, clk);
				VHDLPortAssignment rst_portassign 						= new VHDLPortAssignment(rstport, rst);
				VHDLPortAssignment ce_portassign 						= new VHDLPortAssignment(ceport, ce);
				
				component_instance.addElement(clk_portassign);
				component_instance.addElement(rst_portassign);
				component_instance.addElement(ce_portassign);
				
				
				hwtentity.addElement(component_instance);
								
			}
			
			else if(soundComponent instanceof CompositeSoundComponent)
			{
				
				StatusManager.getManager().handle(new Status(IStatus.ERROR, 
						SoundgatesDiagramEditorPlugin.ID, 
						"Embedded composite components are currently not supported."), 
						StatusManager.SHOW);
			}
		}
		
		for(Link link : compositeSoundComponent.getLinks())
		{

			Port srcport = link.getSource();
			Port dstport = link.getTarget();
			
			
			AtomicSoundComponent srccomponent 	= (AtomicSoundComponent) srcport.getComponent();
			AtomicSoundComponent dstcomponent 	= (AtomicSoundComponent) dstport.getComponent();
			String src_modelname    = srccomponent.getName();
			String src_implname 	= VHDLUtils.getImplName(srccomponent);		
			
			String dst_modelname    = dstcomponent.getName();
			String dst_implname 	= VHDLUtils.getImplName(dstcomponent);
			
			VHDLComponentInstantiationStatement src_vhdl_instance =  VHDLUtils.<VHDLComponentInstantiationStatement>getElementByLabel(hwtentity.getElements(), 
																					VHDLComponentInstantiationStatement.getLabelName(src_modelname, src_implname));
			VHDLComponentInstantiationStatement dst_vhdl_instance =  VHDLUtils.<VHDLComponentInstantiationStatement>getElementByLabel(hwtentity.getElements(), 
																			VHDLComponentInstantiationStatement.getLabelName(dst_modelname, dst_implname));
			
			VHDLPort vhdlsrcport = VHDLUtils.<VHDLPort>getElementByLabel(src_vhdl_instance.getDeclaration().getElements(), getVHDLPort(srccomponent, srcport).getVhdlName());
			VHDLPort vhdldstport = VHDLUtils.<VHDLPort>getElementByLabel(dst_vhdl_instance.getDeclaration().getElements(), getVHDLPort(dstcomponent, dstport).getVhdlName());
			
			if(null == src_vhdl_instance || null == dst_vhdl_instance){
								
				Exception e = new NullPointerException("Could not get one of source or destination instance");
				e.setStackTrace(Thread.currentThread().getStackTrace());
				StatusManager.getManager().handle(new Status(IStatus.ERROR,
																SoundgatesDiagramEditorPlugin.ID,
																"Error during code generation!", e),
																StatusManager.LOG);				
			}
			
			VHDLSignalDeclaration signaldecl  = new VHDLSignalDeclaration(src_vhdl_instance.getLabel() + "_" + srcport.getName()
																		  + "_2_"
																		  + dst_vhdl_instance.getLabel() + "_" + dstport.getName(),																	 
																		  new VHDLDatatype(VHDLStandardDataType.STD_LOGIC_VECTOR.name(), 
																		  new VHDLRange(23, 0)));

			VHDLPortAssignment vhdl_srcport_assign = new VHDLPortAssignment(vhdlsrcport, signaldecl);
			VHDLPortAssignment vhdl_dstport_assign = new VHDLPortAssignment(vhdldstport, signaldecl);
			
			src_vhdl_instance.addElement(vhdl_srcport_assign);
			dst_vhdl_instance.addElement(vhdl_dstport_assign);
			
			hwtentity.addElement(signaldecl);
			
		}
		
		
		for(Port port : compositeSoundComponent.getPorts()){
			
			if(port.getDataType() == DataType.SOUND){
			
				Direction dir = port.getDirection();
				
				if(dir == Direction.IN){
					
					/* Create memory to store samples */
					
					List<VHDLElement> ram_signals = createSharedMemory(hwtentity, port, clk);
					
					for(Connection connection : port.getOutgoingConnection()){
						
						Port   targetport 	= connection.getTarget();						
						
						AtomicSoundComponent soundcomponent = (AtomicSoundComponent) targetport.getComponent();
						String vhdlportname = getVHDLPort(soundcomponent, targetport).getVhdlName();
						
						String implname 	= VHDLUtils.getImplName(soundcomponent);
						String modelname    = soundcomponent.getName();
						/* Create signal from local memory to connected component */
						VHDLSignalDeclaration ram2local_decl   	= new VHDLSignalDeclaration("o_RAM_USER_DATA_" 
																							+ port.getName() 
																							+ "_2_" 
																							+ targetport.getName(), 
																							new VHDLDatatype(VHDLStandardDataType.STD_LOGIC_VECTOR.name(), 
																							new VHDLRange(23, 0)));
						
						VHDLSignalAssignmentStatement  ram2local_assign = new VHDLSignalAssignmentStatement(ram2local_decl, 
																											(VHDLSignalDeclaration) ram_signals.get(7), 
																											new VHDLRange(23, 0));
						
						/* Add signals to entities architecture */
						hwtentity.getElements().add(ram2local_decl);
						hwtentity.getElements().add(ram2local_assign);						
						
						/* Create port assignment of connected component */						
						
						VHDLComponentInstantiationStatement vhdlinstance = VHDLUtils.<VHDLComponentInstantiationStatement>getElementByLabel(hwtentity.getElements(), 
																			VHDLComponentInstantiationStatement.getLabelName(modelname, implname));
																		
						VHDLPort targetvhdlport = VHDLUtils.<VHDLPort>getElementByLabel(vhdlinstance.getDeclaration().getElements(), vhdlportname);
						
						VHDLPortAssignment portassignment = new VHDLPortAssignment(targetvhdlport, ram2local_decl);
						
						vhdlinstance.addElement(portassignment);
						
					}
					
				}else if(dir == Direction.OUT){
					/* Create memory to store samples */
					List<VHDLElement> ram_signals = createSharedMemory(hwtentity, port, clk);
				
					Port   sourceport 			  = port.getIncomingConnection().getSource();						
					
					AtomicSoundComponent soundcomponent = (AtomicSoundComponent) sourceport.getComponent();
					String vhdlportname = getVHDLPort(soundcomponent, sourceport).getVhdlName();					
					String implname 	= VHDLUtils.getImplName(soundcomponent);
					String modelname    = soundcomponent.getName();
					/* Create signal from local memory to connected component */
					VHDLSignalDeclaration 			local2ram_decl  = new VHDLSignalDeclaration(sourceport.getName()
																								+ "_2_"
																								+ "o_RAM_USER_DATA_"
																								+ port.getName(),																								 
																								new VHDLDatatype(VHDLStandardDataType.STD_LOGIC_VECTOR.name(), 
																								new VHDLRange(31, 0)));
					
					VHDLSignalAssignmentStatement  local2ram_assign = new VHDLSignalAssignmentStatement((VHDLSignalDeclaration) ram_signals.get(6),local2ram_decl,new VHDLRange(23, 0));
					
					/* Add signals to entities architecture */
					hwtentity.getElements().add(local2ram_decl);
					hwtentity.getElements().add(local2ram_assign);						
					
					/* Create port assignment of connect component */						
					
					VHDLComponentInstantiationStatement vhdlinstance = VHDLUtils.<VHDLComponentInstantiationStatement>getElementByLabel(hwtentity.getElements(), 
																									VHDLComponentInstantiationStatement.getLabelName(modelname, implname));
																	
					VHDLPort vhdlsourceport = VHDLUtils.<VHDLPort>getElementByLabel(vhdlinstance.getDeclaration().getElements(), vhdlportname);
					
					VHDLPortAssignment portassignment = new VHDLPortAssignment(vhdlsourceport, local2ram_decl);
					
					vhdlinstance.getElements().add(portassignment);
				
				}			
			}
		}
		
		
		return hwtentity.getStringRepresentation().toString();
	}

	private List<VHDLElement> createSharedMemory(VHDLEntity hwtentity, Port port, VHDLSignalDeclaration clk) {
		
		VHDLProcessStatement	memctrl_reconos		= null;
		VHDLProcessStatement	memctrl_user		= null;
		
		VHDLSignalDeclaration 	o_ram_reconos_addr 	= null;		// list index 0
		VHDLSignalDeclaration   o_ram_reconos_addr2 = null;		// list index 1
		VHDLSignalDeclaration 	i_ram_reconos_data 	= null;		// list index 2
		VHDLSignalDeclaration 	o_ram_reconos_data 	= null;		// list index 3
		VHDLSignalDeclaration 	o_ram_reconos_we 	= null;		// list index 4
		
		VHDLSignalDeclaration 	o_ram_user_addr 	= null;		// list index 5
		VHDLSignalDeclaration 	i_ram_user_data 	= null;		// list index 6
		VHDLSignalDeclaration 	o_ram_user_data 	= null;		// list index 7
		VHDLSignalDeclaration 	o_ram_user_we 		= null;		// list index 8
		
		VHDLVariableDeclaration shared_varaible 	= null;
				
		List<VHDLElement>		returnval			= new ArrayList<VHDLElement>();
		
		/* Create shared variable */
		{
			
			shared_varaible = new VHDLVariableDeclaration("local_ram_" + port.getName(), true, new VHDLDatatype("LOCAL_MEMORY_T"));
			hwtentity.addElement(shared_varaible);
		}
		
		/* Create reconos interface */
		{

			/* Create new address signal */
			o_ram_reconos_addr = new VHDLSignalDeclaration("o_RAM_RECONOS_ADDR_" + port.getName(), 
									new VHDLDatatype(VHDLStandardDataType.STD_LOGIC_VECTOR.name(), 
											new VHDLRange(0, "C_LOCAL_RAM_ADDRESS_WIDTH - 1", VHDLRange.Direction.TO)));
			
			/* Create new address translate signal */
			o_ram_reconos_addr2 = new VHDLSignalDeclaration("o_RAM_RECONOS_ADDR_2_" + port.getName(), 
									new VHDLDatatype(VHDLStandardDataType.STD_LOGIC_VECTOR.name(), 
											new VHDLRange(0, 31)));
			
			/* Create new signal declaration for output data  */
			o_ram_reconos_data = new VHDLSignalDeclaration("o_RAM_RECONOS_DATA_" + port.getName(), 
									new VHDLDatatype(VHDLStandardDataType.STD_LOGIC_VECTOR.name(), 
											new VHDLRange(0, 31)));
			
			/* Create new signal declaration for input data   */
			i_ram_reconos_data = new VHDLSignalDeclaration("i_RAM_RECONOS_DATA_" + port.getName(), 
									new VHDLDatatype(VHDLStandardDataType.STD_LOGIC_VECTOR.name(), 
											new VHDLRange(0,31)));
							
			/* Create new signal declaration for write enable */
			o_ram_reconos_we = new VHDLSignalDeclaration("o_RAM_RECONOS_WE_" + port.getName(), 
									new VHDLDatatype(VHDLStandardDataType.STD_LOGIC.name()));
		
			VHDLSignalAssignmentStatement addr_translate_stmt = new VHDLSignalAssignmentStatement(o_ram_reconos_addr, o_ram_reconos_addr2, new VHDLRange("(32-C_LOCAL_RAM_ADDRESS_WIDTH)", "31", VHDLRange.Direction.TO));

			returnval.add(o_ram_reconos_addr);
			returnval.add(o_ram_reconos_addr2);
			returnval.add(o_ram_reconos_data);
			returnval.add(i_ram_reconos_data);
			returnval.add(o_ram_reconos_we);
			
			hwtentity.addElement(addr_translate_stmt);
			hwtentity.addElement(o_ram_reconos_addr);
			hwtentity.addElement(o_ram_reconos_addr2);
			hwtentity.addElement(o_ram_reconos_data);
			hwtentity.addElement(i_ram_reconos_data);
			hwtentity.addElement(o_ram_reconos_we);
		}
		
		/* Create entity interface */
		{
			/* create new signal declaration for ram address  */
			o_ram_user_addr = new VHDLSignalDeclaration("o_RAM_USER_ADDR_" + port.getName(), 
								new VHDLDatatype(VHDLStandardDataType.STD_LOGIC_VECTOR.name(), 
										new VHDLRange(0, "C_LOCAL_RAM_ADDRESS_WIDTH - 1", VHDLRange.Direction.TO)));
	
			/* Create new signal declaration for output data  */	
			o_ram_user_data = new VHDLSignalDeclaration("o_RAM_USER_DATA_" + port.getName(), 
								new VHDLDatatype(VHDLStandardDataType.STD_LOGIC_VECTOR.name(), 
										new VHDLRange(0, 31)));
		
			/* Create new signal declaration for input data   */
			i_ram_user_data	= new VHDLSignalDeclaration("i_RAM_USER_DATA_" + port.getName(), 
									new VHDLDatatype(VHDLStandardDataType.STD_LOGIC_VECTOR.name(), 
											new VHDLRange(0,31)));
	
			/* Create new signal declaration for write enable */
			o_ram_user_we = new VHDLSignalDeclaration("i_RAM_USER_WE_" + port.getName(), 
								new VHDLDatatype(VHDLStandardDataType.STD_LOGIC.name()));
			
			returnval.add(o_ram_user_addr);			
			returnval.add(o_ram_user_data);
			returnval.add(i_ram_user_data);
			returnval.add(o_ram_user_we);
			
			hwtentity.addElement(o_ram_user_addr);			
			hwtentity.addElement(o_ram_user_data);
			hwtentity.addElement(i_ram_user_data);
			hwtentity.addElement(o_ram_user_we);
		}
		
		/* Create control processes */
		{			
			/* ------------------------------------------------------------------------------------------------ */
			/* Reconos 																							*/
			/* ------------------------------------------------------------------------------------------------ */	
			ArrayList<VHDLSignalDeclaration> sensitivitylist = new ArrayList<VHDLSignalDeclaration>();
			sensitivitylist.add(clk);
			{
				memctrl_reconos = new VHDLProcessStatement("local_ram_reconos_ctrl_" + port.getName(), sensitivitylist);
	
				VHDLSequentialStatement ifCLKstatement = new VHDLSequentialStatement("if rising_edge(clk) then");
				
				VHDLSequentialStatement ifWEstatement = new VHDLSequentialStatement("if " + o_ram_reconos_we.getName() + " = '1' " + " then");
					ifWEstatement.addElement(new VHDLSequentialStatement(
									shared_varaible.getLabel()
									+ "(to_integer(unsigned("
									+ o_ram_reconos_addr.getName()
									+ "))) := "
									+ i_ram_reconos_data.getName() + ";"));
				
				VHDLSequentialStatement WEelsestatement = new VHDLSequentialStatement("else");
			
					WEelsestatement.addElement(new VHDLSequentialStatement(
									o_ram_reconos_data.getName() 
									+ " <= "
									+ shared_varaible.getLabel()
									+ "(to_integer(unsigned("
									+ o_ram_reconos_addr.getName() + ")));"));
				
				ifCLKstatement.addElement(ifWEstatement);
				ifCLKstatement.addElement(WEelsestatement);
				ifCLKstatement.addElement(new VHDLSequentialStatement("end if;"));
	
				memctrl_reconos.addElement(ifCLKstatement);
				memctrl_reconos.addElement(new VHDLSequentialStatement("end if;"));
				
				hwtentity.addElement(memctrl_reconos);
			}
			/* ------------------------------------------------------------------------------------------------ */
			/* User 																							*/
			/* ------------------------------------------------------------------------------------------------ */
			
			memctrl_user = new VHDLProcessStatement("local_ram_user_ctrl" + port.getName(), sensitivitylist);
			{
				VHDLSequentialStatement ifCLKstatement = new VHDLSequentialStatement("if rising_edge(clk) then");
				
				VHDLSequentialStatement ifWEstatement = new VHDLSequentialStatement("if " + o_ram_user_we.getName() + " = '1' " + " then");
					ifWEstatement.addElement(new VHDLSequentialStatement(
									shared_varaible.getLabel()
									+ "(to_integer(unsigned("
									+ o_ram_user_addr.getName()
									+ "))) := "
									+ o_ram_user_data.getName() + ";"));
				
				VHDLSequentialStatement WEelsestatement = new VHDLSequentialStatement("else");
			
					WEelsestatement.addElement(new VHDLSequentialStatement(
									i_ram_user_data.getName() 
									+ " <= "
									+ shared_varaible.getLabel()
									+ "(to_integer(unsigned("
									+ o_ram_user_addr.getName() + ")));"));
				
				ifCLKstatement.addElement(ifWEstatement);
				ifCLKstatement.addElement(WEelsestatement);
				ifCLKstatement.addElement(new VHDLSequentialStatement("end if;"));
	
				memctrl_user.addElement(ifCLKstatement);
				memctrl_user.addElement(new VHDLSequentialStatement("end if;"));

				hwtentity.addElement(memctrl_user);
			}
		}
		return returnval;
	}
	
	private VHDLPortDescriptor getVHDLPort(AtomicSoundComponent atomic, Port modelPort){
		LinkedList<VHDLPortDescriptor> vhdlPorts = AtomicSoundComponentLibrary.componentTypeToVHDLPortsList.get(atomic.getType());
		VHDLPortDescriptor port = null;
		try{
		
			for(VHDLPortDescriptor vhdlPort : vhdlPorts){
				if(vhdlPort.hasModelName()){
					if(vhdlPort.getModelName().equals(modelPort.getName())){
						port = vhdlPort;
						break;
					}
				}
			}
		}catch(Throwable e){
			
			StatusManager.getManager().handle(new Status(IStatus.ERROR, 
					SoundgatesDiagramEditorPlugin.ID, 
					"Something is fishy! No hardware implementation available for this component? \n"
					+ "Component: " + atomic.getName() 		+ "\n"
					+ "Port: " + modelPort.getName()	+ "\n"
					, e), 
					StatusManager.SHOW);
		}
		
		if(null == port){
			
			StatusManager.getManager().handle(new Status(IStatus.WARNING, 
					SoundgatesDiagramEditorPlugin.ID, 
					"Something is fishy! There is no vhdl for this model port. \n"
					+ "Component: " + atomic.getName() 	+ "\n"
					+ "Port: " + modelPort.getName()	+ "\n"), 
					StatusManager.SHOW);
		}
		
		
		return port;
	}
	
	private VHDLPortDescriptor getVHDLPort(AtomicSoundComponent atomicSoundComponent, VHDLPortDescriptor.SIGIS sigis){
		LinkedList<VHDLPortDescriptor> vhdlPorts = AtomicSoundComponentLibrary.componentTypeToVHDLPortsList.get(atomicSoundComponent.getType());
		for(VHDLPortDescriptor vhdlPort : vhdlPorts){
			if(vhdlPort.getSigis().equals(sigis)){
				return vhdlPort;
			}				
		}
		return null;
	}
	
	private Port getModelPort(AtomicSoundComponent atomicSoundComponent, VHDLPortDescriptor vhdlPort){		
		for(Port modelPort : atomicSoundComponent.getPorts()){
			if(vhdlPort.getModelName().equals(modelPort.getName())){
				return modelPort;
			}
		}
		return null;
	}
	
	private void saveTextToFile(String text, String fileName){	
		
		try {
			IFile newFile = AtomicSoundComponentLibrary.getXMLFolder().getProject().getFile(fileName);
			
			InputStream code = null;
			
			try {
			
				code = new ByteArrayInputStream(text.getBytes("UTF-8"));
			
			} catch (UnsupportedEncodingException e) {
			
				e.printStackTrace();
			}
			
			if(newFile.exists()){
				newFile.delete(true, null);
			}
			newFile.create(code, IResource.FORCE, null);
			
			newFile.getParent().refreshLocal(1, null);
			
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
