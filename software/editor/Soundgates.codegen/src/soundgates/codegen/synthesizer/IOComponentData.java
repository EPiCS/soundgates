package soundgates.codegen.synthesizer;

public class IOComponentData extends ComponentData{
	
	boolean input;
	String oscAddress;
	String oscDataType;
	String range;
	
	public IOComponentData(int id, String type, String implementationType,
			Object value, int hwSlot, String oscAddress,
			String oscDataType, String range) {
		
		super(id, type, implementationType, value, hwSlot);
		
		this.oscAddress = oscAddress;
		this.oscDataType = oscDataType;
		this.range = range;		
	}

	
	@Override
	public String getRepresentation() {
		
		String result = super.getRepresentation(); 
		result += "/'/"+oscAddress + " ";
		result += "\\\""+oscDataType + "\\\"";
		result += range + "'";
		
		return result;
	}
}
