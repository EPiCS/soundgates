package soundgates;

public class VHDLPortDescriptor {
	
	
	public enum SIGIS{
		
		CLK,
		RST,
		CE,
		UNKNOWN
	}
	
	private String 		modelName;
	private String 		vhdlName; 
	private Direction 	dir; 
	private String 		dataType; 
	private Range 		range; 
	
	private boolean 	isVector;
	private SIGIS		sigis;
	
	public VHDLPortDescriptor(String vhdlName, Direction dir, String dataType){
		this.vhdlName = vhdlName;
		this.dir = dir;
		this.dataType = dataType;	
		
		sigis = null;
		range = null;
		modelName = null;
		isVector = false;
	}
	
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	public boolean hasModelName(){
		return (modelName!=null);
	}

	public String getVhdlName() {
		return vhdlName;
	}

	public void setVhdlName(String vhdlName) {
		this.vhdlName = vhdlName;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	public String getDatatype() {
		return dataType;
	}

	public void setDatatype(String dataType) {
		this.dataType = dataType;
	}

	public Range getRange() {
		return range;
	}

	public void setRange(int leftValue, int rightValue) {
		this.range = new Range(leftValue, rightValue);
		isVector = true;
	}

	public boolean isClockSignal(){		
		return this.getSigis().equals(SIGIS.CLK);
	}
	
	public boolean isResetSignal(){
		return this.getSigis().equals(SIGIS.RST);
	}
	
	public boolean isChipEnableSignal(){
		return this.getSigis().equals(SIGIS.CE);
	}
	
	public SIGIS getSigis() {
		
		return this.sigis;
	}

	public void setSigis(String sigis) {
		
		if(sigis.toUpperCase().equals(SIGIS.CLK.name())){
			this.sigis = SIGIS.CLK;		
		}else if(sigis.toUpperCase().equals(SIGIS.RST.name())){
			this.sigis = SIGIS.RST;
		}else if(sigis.toUpperCase().equals(SIGIS.CE.name())){
			this.sigis = SIGIS.CE;
		}else{
			this.sigis = SIGIS.UNKNOWN;
		}
	}
	
	public boolean hasSigis(){
		return (sigis!=null);
	}
	
	public boolean isVector(){
		return isVector;
	}
	
	public class Range{
		int leftValue;
		int rightValue;
		
		public Range(int leftValue, int rightValue) {
			this.leftValue = leftValue;
			this.rightValue = rightValue;	
		}
		
		public int getLeftValue() {
			return leftValue;
		}

		public int getRightValue() {
			return rightValue;
		}	
	}
}
