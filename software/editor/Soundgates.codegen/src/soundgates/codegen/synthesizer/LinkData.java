package soundgates.codegen.synthesizer;

public class LinkData implements Representable{
	int SourcePort;
	int SinkPort;
	ComponentData Source;
	ComponentData Sink;
	
	@Override
	public String getRepresentation() {
		return Source.getId() + " " + Sink.getId() + " (" + SourcePort + "," + SinkPort + ")";
	}
}
