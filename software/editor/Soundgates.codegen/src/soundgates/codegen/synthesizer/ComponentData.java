package soundgates.codegen.synthesizer;

public class ComponentData implements Representable{
	int id;
	String type;
	String implementationType;
	
	@Override
	public String getRepresentation() {
		return id + " " + type + "/" + implementationType;
	}

	public int getId() {
		return id;
	}
}
