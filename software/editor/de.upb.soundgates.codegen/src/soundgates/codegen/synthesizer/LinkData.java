package soundgates.codegen.synthesizer;

public class LinkData implements Representable{

	int source;
	int sink;
	int sourcePort;
	int sinkPort;

	
	@Override
	public String getRepresentation() {
		return source + " " + sink + " '" + sourcePort + "," + sinkPort + "'";
	}

	public LinkData(int source,
			int sink, int sourcePort, int sinkPort) {
		super();
		this.source = source;
		this.sink = sink;
		this.sourcePort = sourcePort;
		this.sinkPort = sinkPort;
	}
}
