package soundgates.diagram.part;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.eclipse.gef.Request;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import soundgates.diagram.providers.SoundgatesElementTypes;
import soundgates.diagram.soundcomponents.AtomicSoundComponentLibrary;

/**
 * @generated
 */
public class SoundgatesPaletteFactory {

	/**
	 * @generated
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createComponents1Group());
		paletteRoot.add(createPorts2Group());
		paletteRoot.add(createConnections3Group());
	}

	/**
	 * Creates "Components" palette tool group
	 * 
	 * @generated NOT
	 */
	private PaletteContainer createComponents1Group() {
		PaletteGroup paletteContainer = new PaletteGroup(
				Messages.Components1Group_title);
		paletteContainer.setId("createComponents1Group"); //$NON-NLS-1$
		paletteContainer.add(createAtomicSoundComponent1CreationTool());
		List<String> availableTypes = AtomicSoundComponentLibrary.getInstance()
				.getAvailableTypes();
		// instead of putting the type descriptor in the palette, use the name
		// of the blueprint soundcomponent
		for (int i = 0; i < availableTypes.size(); i++) {
			paletteContainer
					.add(createConcreteAtomicNodeCreationTool(availableTypes
							.get(i))); // MYTOOL
		}
		paletteContainer.add(createCompositeSoundComponent2CreationTool());
		return paletteContainer;
	}

	/**
	 * Creates "Ports" palette tool group
	 * 
	 * @generated
	 */
	private PaletteContainer createPorts2Group() {
		PaletteGroup paletteContainer = new PaletteGroup(
				Messages.Ports2Group_title);
		paletteContainer.setId("createPorts2Group"); //$NON-NLS-1$
		paletteContainer.add(createPort1CreationTool());
		return paletteContainer;
	}

	/**
	 * Creates "Connections" palette tool group
	 * 
	 * @generated
	 */
	private PaletteContainer createConnections3Group() {
		PaletteGroup paletteContainer = new PaletteGroup(
				Messages.Connections3Group_title);
		paletteContainer.setId("createConnections3Group"); //$NON-NLS-1$
		paletteContainer.setDescription(Messages.Connections3Group_desc);
		paletteContainer.add(createLink1CreationTool());
		paletteContainer.add(createDelegation2CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createCompositeSoundComponent2CreationTool() {
		NodeToolEntry entry = new NodeToolEntry(
				Messages.CompositeSoundComponent2CreationTool_title,
				Messages.CompositeSoundComponent2CreationTool_desc,
				Collections
						.singletonList(SoundgatesElementTypes.CompositeSoundComponent_2002));
		entry.setId("createCompositeSoundComponent2CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SoundgatesElementTypes
				.getImageDescriptor(SoundgatesElementTypes.CompositeSoundComponent_2002));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createPort1CreationTool() {
		NodeToolEntry entry = new NodeToolEntry(
				Messages.Port1CreationTool_title,
				Messages.Port1CreationTool_desc,
				Collections.singletonList(SoundgatesElementTypes.Port_3001));
		entry.setId("createPort1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SoundgatesDiagramEditorPlugin
				.findImageDescriptor("/Soundgates.edit/icons/toolspalette/PortIn.gif")); //$NON-NLS-1$
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createLink1CreationTool() {
		LinkToolEntry entry = new LinkToolEntry(
				Messages.Link1CreationTool_title,
				Messages.Link1CreationTool_desc,
				Collections.singletonList(SoundgatesElementTypes.Link_4001));
		entry.setId("createLink1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SoundgatesElementTypes
				.getImageDescriptor(SoundgatesElementTypes.Link_4001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createDelegation2CreationTool() {
		ToolEntry entry = new ToolEntry(Messages.Delegation2CreationTool_title,
				Messages.Delegation2CreationTool_desc, null, null) {
		};
		entry.setId("createDelegation2CreationTool"); //$NON-NLS-1$
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createAtomicSoundComponent1CreationTool() {
		NodeToolEntry entry = new NodeToolEntry(
				Messages.AtomicSoundComponent1CreationTool_title,
				Messages.AtomicSoundComponent1CreationTool_desc,
				Collections
						.singletonList(SoundgatesElementTypes.AtomicSoundComponent_2001));
		entry.setId("createAtomicSoundComponent1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SoundgatesElementTypes
				.getImageDescriptor(SoundgatesElementTypes.AtomicSoundComponent_2001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	// MYTOOL
	private ToolEntry createConcreteAtomicNodeCreationTool(String atomicType) {
		String atomicName = AtomicSoundComponentLibrary.getInstance()
				.createAtomicSoundComponentInstance(atomicType).getName();
		NodeToolEntry entry = new ConcreteAtomicNodeToolEntry(
				atomicName,
				atomicType,
				"Create " + atomicName,
				Collections
						.singletonList(SoundgatesElementTypes.AtomicSoundComponent_2001));
		entry.setId("createAtomicSoundComponent1CreationTool"); //$NON-NLS-1$
		entry.setSmallIcon(SoundgatesElementTypes
				.getImageDescriptor(SoundgatesElementTypes.AtomicSoundComponent_2001));
		entry.setLargeIcon(entry.getSmallIcon()); // TODO Icon anpassen?
		return entry;
	}

	// private ToolEntry createSineGeneratorCreationTool() {
	// NodeToolEntry entry = new ConcreteAtomicNodeToolEntry(
	// "Sine",
	// Messages.AtomicSoundComponent1CreationTool_desc,
	// Collections
	// .singletonList(SoundgatesElementTypes.AtomicSoundComponent_2001));
	//		entry.setId("createAtomicSoundComponent1CreationTool"); //$NON-NLS-1$
	// entry.setSmallIcon(SoundgatesElementTypes
	// .getImageDescriptor(SoundgatesElementTypes.AtomicSoundComponent_2001));
	// entry.setLargeIcon(entry.getSmallIcon());
	// return entry;
	// }

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

		protected ConcreteAtomicNodeToolEntry(String title, String type,
				String description, List<IElementType> elementTypes) {
			super(title, description, elementTypes);
			this.atomicType = type;
		}

		public Tool createTool() {
			Tool tool = new ConcreteAtomicTypeCreationTool(elementTypes,
					atomicType);
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
