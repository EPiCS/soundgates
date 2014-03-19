package soundgates;

public class VHDLPort {

	String modelName;
	String vhdlName; 
	Direction dir; 
	String dataType; 
	Range range; 
	String sigis;
	boolean isVector;
	String supportfn;
	
	public VHDLPort(String vhdlName, Direction dir, String dataType){
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

	public String getSigis() {
		return sigis;
	}

	public void setSigis(String sigis) {
		this.sigis = sigis;
	}
	
	public boolean hasSigis(){
		return (sigis!=null);
	}
	
	public boolean isVector(){
		return isVector;
	}
	
	public String getSupportfn(){
		return supportfn;
	}
	
	public void setSupportfn(String s){
		this.supportfn = s;
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
