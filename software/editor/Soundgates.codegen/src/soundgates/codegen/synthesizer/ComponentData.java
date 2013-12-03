package soundgates.codegen.synthesizer;

public class ComponentData implements Representable{
	int id;
	String type;
	String implementationType;
	Object value;
	int hwSlot;
	String oscAddress;
	String oscDataType;
	
	
	
	public ComponentData(int id, String type, String implementationType,
			Object value, int hwSlot, String oscAddress, String oscDataType) {
		super();
		this.id = id;
		this.type = type;
		this.implementationType = implementationType;
		this.value = value;
		this.hwSlot = hwSlot;
		this.oscAddress = oscAddress;
		this.oscDataType = oscDataType;
	}

	@Override
	public String getRepresentation() {
		String result = id + " ";
		result += type + "/" + implementationType;
		result += (implementationType.equals("hw")? "(" + hwSlot + ")":"");
		result += "'" + value + "'";
		return result;
	}

	public int getId() {
		return id;
	}
}
