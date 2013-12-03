package soundgates.codegen.synthesizer;

public class LinkData implements Representable{
	int sourcePort;
	int sinkPort;
	ComponentData source;
	ComponentData sink;
	
	@Override
	public String getRepresentation() {
		return source.getId() + " " + sink.getId() + " '" + sourcePort + "," + sinkPort + "'";
	}

	public LinkData(int sourcePort, int sinkPort, ComponentData source,
			ComponentData sink) {
		super();
		this.sourcePort = sourcePort;
		this.sinkPort = sinkPort;
		this.source = source;
		this.sink = sink;
	}
}
