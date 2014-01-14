package soundgates.codegen.synthesizer;

public class ComponentData implements Representable{
	int id;
	String type;
	String implName;
	String implType;
	Object value;
	int hwSlot;
	
	public ComponentData(int id, String type, String implName, String implType, Object value, int hwSlot) {
		super();
		this.id = id;
		this.type = type;
		this.implName = implName;
		this.implType = implType;
		this.value = value;
		this.hwSlot = hwSlot;
	}

	@Override
	public String getRepresentation() {
		String result = id + " ";
		result += implName + "/" + implType;
		result += (implType.equals("hw")? "(" + hwSlot + ")":"");
		result += (!value.equals(""))? "/'" + value + "'" : "";
		
		return result;
	}

	public int getId() {
		return id;
	}
}
