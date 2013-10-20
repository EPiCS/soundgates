package soundgates.codegen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import soundgates.AtomicSoundComponent;
import soundgates.Element;
import soundgates.Link;
import soundgates.Patch;
import soundgates.SoundComponent;
import soundgates.SoundgatesPackage;

public class Codegen {

	private String folder;
	
	public void setFolder(String folder){
		this.folder=folder;
	}
	
	public Patch getPatch(String path){
	    SoundgatesPackage.eINSTANCE.eClass();
	    
	    // Register the XMI resource factory for the .website extension

	    Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	    Map<String, Object> m = reg.getExtensionToFactoryMap();
	    m.put("xmi", new XMIResourceFactoryImpl());

	    // Obtain a new resource set
	    ResourceSet resSet = new ResourceSetImpl();

	    // Get the resource
	    Resource resource = resSet.getResource(URI
	        .createURI(path), true);
	    // Get the first model element and cast it to the right type, in my
	    // example everything is hierarchical included in this first node
	    Patch patch = (Patch) resource.getContents().get(0);
	    return patch;		
	}
	
	public void generateCodeForPatch(Patch patch) throws IOException {
		
		List<SoundComponent> componentList = new ArrayList<SoundComponent>();
		List<Link> linkList = new ArrayList<Link>();
		
		for(Element element : patch.getElements()){
			if(element instanceof AtomicSoundComponent){
				
				AtomicSoundComponent acomp = ((AtomicSoundComponent) element); 
				componentList.add(acomp);
				handleAtomicComponent(acomp);

			}
			
			else if(element instanceof Link){
				linkList.add((Link) element);
			}
		}
		
		writePDForPatch(componentList, linkList);
	}
	
	private void writePDForPatch(List<SoundComponent> componentList, List<Link> linkList) throws IOException{
		
		FileWriter writer =  new FileWriter(new File(folder+ "patch.pd"));
		writer.write("#N canvas 621 551 450 300 10; \n");		
		
		for (SoundComponent comp : componentList){
			if(comp instanceof AtomicSoundComponent)
				writer.write("#X obj 0 0 "+ ((AtomicSoundComponent)comp).getType() + ";\n");
		}
		
		for (Link link : linkList){
			
			int source = componentList.indexOf(link.getSource().getComponent());
			int sourcePort = 0;
			int sink = componentList.indexOf(link.getTarget().getComponent());
			int sinkPort = 0;
			
			writer.write("#X connect " + source + " " + sourcePort + " " + sink + " " + sinkPort + ";\n");
		}
		
		writer.close();
	}
	
	
	private void handleAtomicComponent(AtomicSoundComponent acomp)  throws IOException {
		String compName = acomp.getType();
		String fileName = folder + compName + ".pd";
		FileWriter writer =  new FileWriter(new File(fileName));
		if (acomp.getStringProperties() != null){

			for(String key : acomp.getStringProperties().keySet()){
				System.out.println(key);
			}
			if (acomp.getStringProperties().containsKey("codeGenProp_Simulation")){
				writer.write(acomp.getStringProperties().get("codeGenProp_Simulation"));	
				
			}
		}
		writer.close();
	}
	

}
