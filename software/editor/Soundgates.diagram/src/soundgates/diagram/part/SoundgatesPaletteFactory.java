package soundgates.diagram.part;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.gef.Request;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import soundgates.diagram.providers.SoundgatesElementTypes;
import soundgates.diagram.soundcomponents.AtomicSoundComponentLibrary;
import soundgates.diagram.soundcomponents.CompositeSoundComponentLibrary;

/**
 * @generated
 */
public class SoundgatesPaletteFactory {
	
	private HashMap<String,PaletteDrawer> categoryToPaletteDrawer = new HashMap<>();

	/**
	 * @generated NOT
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		
		//atomics		
		List<String> availableTypes = AtomicSoundComponentLibrary.getInstance().getAvailableTypes();		

		for(String category : AtomicSoundComponentLibrary.typeToCategory.values())
		{
			PaletteDrawer categoryContainer = new PaletteDrawer(category);
			categoryContainer.setInitialState(PaletteDrawer.INITIAL_STATE_CLOSED);
			categoryContainer.setId(category);
			
			if(!categoryToPaletteDrawer.containsKey(category)){
				categoryToPaletteDrawer.put(category, categoryContainer);
				
				paletteRoot.add(categoryContainer);
			}
		}
		
		String category;
		for (int i = 0; i < availableTypes.size(); i++) {
			category = AtomicSoundComponentLibrary.typeToCategory.get(availableTypes.get(i));
			PaletteDrawer paletteDrawer = categoryToPaletteDrawer.get(category);			
			paletteDrawer.add(createConcreteAtomicNodeCreationTool(availableTypes.get(i)));
		}
		
		//separator
		PaletteSeparator paletteSeparator = new PaletteSeparator();
		paletteRoot.add(paletteSeparator);
		
		//composites
		paletteRoot.add(createNewCompositeSoundComponents());
		paletteRoot.add(createImportedCompositeSoundComponents());
		
		//separator
		PaletteSeparator paletteSeparator2 = new PaletteSeparator();
		paletteRoot.add(paletteSeparator2);
		
		paletteRoot.add(createConnections3Group());
	}

	/**
	 * Creates "Create Composite Components" palette tool group
	 * 
	 * @generated NOT
	 */
	private PaletteContainer createNewCompositeSoundComponents() {
		PaletteDrawer paletteContainer = new PaletteDrawer(
				"Create Composite Sound Components");
		paletteContainer.setId("createComponents2Group"); //$NON-NLS-1$

		paletteContainer.add(createCompositeSoundComponent2CreationTool());

		paletteContainer.add(createPort1CreationTool());

		paletteContainer.setInitialState(PaletteDrawer.INITIAL_STATE_CLOSED);
		
		return paletteContainer;
	}
	
	/**
	 * Creates "Imported Composite Components" palette tool group
	 * 
	 * @generated NOT
	 */
	private PaletteContainer createImportedCompositeSoundComponents() {
		PaletteDrawer paletteContainer = new PaletteDrawer(
				"Imported Composite Sound Components");
		paletteContainer.setId("createImportedCompositeSoundComponents"); //$NON-NLS-1$
		
		List<String> availableComponents = CompositeSoundComponentLibrary
				.getInstance().getAvailableComponents();
		for (int i = 0; i < availableComponents.size(); i++) {
			paletteContainer
					.add(createConcreteCompositeNodeCreationTool(availableComponents
							.get(i))); // MYTOOL
		}
		
		paletteContainer.setInitialState(PaletteDrawer.INITIAL_STATE_CLOSED);

		return paletteContainer;
	}

	/**
	 * Creates "Connections" palette tool group
	 * 
	 * @generated NOT
	 */
	private PaletteContainer createConnections3Group() {
		PaletteDrawer paletteContainer = new PaletteDrawer("Connections");
		paletteContainer.setId("createConnections3Group"); //$NON-NLS-1$
		paletteContainer.setDescription(Messages.Connections3Group_desc);
		paletteContainer.add(createLink1CreationTool());
		paletteContainer.add(createDelegation2CreationTool());
		
		paletteContainer.setInitialState(PaletteDrawer.INITIAL_STATE_CLOSED);
		return paletteContainer;
	}

	/**
	 * @generated NOT
	 */
	private ToolEntry createCompositeSoundComponent2CreationTool() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(2);
		types.add(SoundgatesElementTypes.CompositeSoundComponent_2002);
		types.add(SoundgatesElementTypes.CompositeSoundComponent_3003);
		NodeToolEntry entry = new NodeToolEntry(
				"New composite sound component",
				Messages.CompositeSoundComponent2CreationTool_desc, types);
		entry.setId("createCompositeSoundComponent2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SoundgatesElementTypes
				.getImageDescriptor(SoundgatesElementTypes.CompositeSoundComponent_2002));
		entry.setLargeIcon(entry.getSmallIcon());

		return entry;
	}

	/**
	 * @generated NOT
	 */
	private ToolEntry createPort1CreationTool() {
		NodeToolEntry entry = new NodeToolEntry(
				"New Port",
				Messages.Port1CreationTool_desc,
				Collections.singletonList(SoundgatesElementTypes.Port_3001));
		entry.setId("createPort1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SoundgatesDiagramEditorPlugin
				.findImageDescriptor("/Soundgates.edit/icons/toolspalette/PortIn.gif")); //$NON-NLS-1$
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated NOT
	 */
	private ToolEntry createLink1CreationTool() {
		ArrayList<IElementType> types = new ArrayList<IElementType>(2);
		types.add(SoundgatesElementTypes.Link_4001);
		types.add(SoundgatesElementTypes.Link_4002);
		LinkToolEntry entry = new LinkToolEntry(
				Messages.Link1CreationTool_title,
				"A link connects two ports of sound components that are on the same level", 
				types);
		entry.setId("createLink1CreationTool"); //$NON-NLS-1$
		return entry;
	}

	/**
	 * @generated NOT
	 */
	private ToolEntry createDelegation2CreationTool() {
		LinkToolEntry entry = new LinkToolEntry(
				Messages.Delegation2CreationTool_title,
				"A delegation connects a port of a composite sound component with a port of its embedded sound component",
				Collections
						.singletonList(SoundgatesElementTypes.Delegation_4003));
		entry.setId("createDelegation2CreationTool"); //$NON-NLS-1$
		return entry;
	}

	// MYTOOL

	// atomic components
	private ToolEntry createConcreteAtomicNodeCreationTool(String atomicType) {

		ArrayList<IElementType> types = new ArrayList<IElementType>(2);
		types.add(SoundgatesElementTypes.AtomicSoundComponent_2001);
		types.add(SoundgatesElementTypes.AtomicSoundComponent_3002);

		NodeToolEntry entry = new ConcreteAtomicNodeToolEntry(atomicType,
				"Create " + atomicType, types);
		entry.setId("createAtomicSoundComponent1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SoundgatesElementTypes
				.getImageDescriptor(SoundgatesElementTypes.AtomicSoundComponent_2001));
		entry.setLargeIcon(entry.getSmallIcon()); // TODO Icon anpassen?
		return entry;
	}

	private static class ConcreteAtomicTypeCreationTool extends
			UnspecifiedTypeCreationTool {
		private String atomicType;

		public ConcreteAtomicTypeCreationTool(List elementTypes,
				String atomicType) {
			super(elementTypes);
			this.atomicType = atomicType;
		}

		protected Request createTargetRequest() {
			Request r = super.createTargetRequest();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("concreteType", this.atomicType);
			r.setExtendedData(map);
			return r;
		}
	}

	private static class ConcreteAtomicNodeToolEntry extends NodeToolEntry {
		private String atomicType;

		protected ConcreteAtomicNodeToolEntry(String title, String description,
				List<IElementType> elementTypes) {
			super(title, description, elementTypes);
			this.atomicType = title;
		}

		public Tool createTool() {
			Tool tool = new ConcreteAtomicTypeCreationTool(elementTypes,
					atomicType);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}

	// composite components
	private ToolEntry createConcreteCompositeNodeCreationTool(String name) {

		ArrayList<IElementType> types = new ArrayList<IElementType>(2);
		types.add(SoundgatesElementTypes.CompositeSoundComponent_2002);
		types.add(SoundgatesElementTypes.CompositeSoundComponent_3003);

		NodeToolEntry entry = new ConcreteCompositeNodeToolEntry(name,
				"Create " + name, types);
		entry.setId("createCompositeSoundComponent1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SoundgatesElementTypes
				.getImageDescriptor(SoundgatesElementTypes.CompositeSoundComponent_2002));
		entry.setLargeIcon(entry.getSmallIcon()); // TODO Icon anpassen?
		return entry;
	}

	private static class ConcreteCompositeCreationTool extends
			UnspecifiedTypeCreationTool {
		private String name;

		public ConcreteCompositeCreationTool(List names, String name) {
			super(names);
			this.name = name;
		}

		protected Request createTargetRequest() {
			Request r = super.createTargetRequest();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", this.name);
			r.setExtendedData(map);
			return r;
		}
	}

	private static class ConcreteCompositeNodeToolEntry extends NodeToolEntry {
		private String name;

		protected ConcreteCompositeNodeToolEntry(String title,
				String description, List<IElementType> names) {
			super(title, description, names);
			this.name = title;
		}

		public Tool createTool() {
			Tool tool = new ConcreteCompositeCreationTool(elementTypes, name);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}

	// ---MYTOOL

	/**
	 * @generated
	 */
	private static class NodeToolEntry extends ToolEntry {

		/**
		 * @generated NOT
		 */
		protected final List<IElementType> elementTypes;

		/**
		 * @generated
		 */
		private NodeToolEntry(String title, String description,
				List<IElementType> elementTypes) {
			super(title, description, null, null);
			this.elementTypes = elementTypes;
		}

		/**
		 * @generated
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeCreationTool(elementTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}

	/**
	 * @generated
	 */
	private static class LinkToolEntry extends ToolEntry {

		/**
		 * @generated
		 */
		private final List<IElementType> relationshipTypes;

		/**
		 * @generated
		 */
		private LinkToolEntry(String title, String description,
				List<IElementType> relationshipTypes) {
			super(title, description, null, null);
			this.relationshipTypes = relationshipTypes;
		}

		/**
		 * @generated
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}
}
