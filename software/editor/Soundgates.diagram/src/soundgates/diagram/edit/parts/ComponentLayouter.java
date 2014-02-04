package soundgates.diagram.edit.parts;

import soundgates.AtomicSoundComponent;
import soundgates.Direction;
import soundgates.Port;

public class ComponentLayouter {
	private static final int portSize = 10;
	private static final int distanceFromBorder = 10;
	private static final int distanceBetweenPorts = 90;
	
	public static final int componentHeight = 40;
	
	private int maxPortsNumber;
	
	private int[] inputPortsXPositions;
	private int[] outputPortsXPositions;
	
	public ComponentLayouter(AtomicSoundComponent atomicSoundComponent){
		computePortsXPositions(atomicSoundComponent);
	}
	
	public int getMaxPortsNumber(){
		return maxPortsNumber;
	}
	
	public int getComponentWidth(){
		return computeComponentWidth(maxPortsNumber);
	}
	
	public int[] getInputPortsXPositions(){
		return inputPortsXPositions;
	}
	
	public int[] getOutputPortsXPositions(){
		return outputPortsXPositions;
	}
	
	private int computeComponentWidth(int maxPortsNumber){
		return 2*distanceFromBorder + 
				maxPortsNumber*portSize + 
				(maxPortsNumber-1)*distanceBetweenPorts; 
	
	}
	
	private void computePortsXPositions(AtomicSoundComponent atomicSoundComponent){
		int inputPorts = 0;
		int outputPorts = 0;
		char sideWithMorePorts;
		for (Port port : atomicSoundComponent.getPorts()){
			if(port.getDirection()==Direction.IN)
				inputPorts++;
			else 
				outputPorts++;
		}
		if(inputPorts>outputPorts){
			maxPortsNumber = inputPorts;
			sideWithMorePorts='i';
		}
		else if (inputPorts<outputPorts){
			maxPortsNumber = outputPorts;
			sideWithMorePorts='o';
		}
		else {
			maxPortsNumber = inputPorts;
			sideWithMorePorts = 'b';
		}
		
		inputPortsXPositions = new int[inputPorts];
		outputPortsXPositions = new int[outputPorts];
		
		int portWithDistance = portSize + distanceBetweenPorts;
		
		int tmpPos = distanceFromBorder;
		if(sideWithMorePorts=='i'){
			for(int i=0; i<inputPorts; i++){
				inputPortsXPositions[i]=tmpPos;
				tmpPos += portWithDistance;
			}
			
			int emptyCellsAtStart = (inputPorts-outputPorts)/2;
			tmpPos = distanceFromBorder + portWithDistance*emptyCellsAtStart;
			for(int i=0; i<outputPorts; i++){
				outputPortsXPositions[i]=tmpPos;
				tmpPos += portWithDistance;
			}
		}
		else if(sideWithMorePorts=='o'){
			for(int i=0; i<outputPorts; i++){
				outputPortsXPositions[i]=tmpPos;
				tmpPos += portWithDistance;
			}
			
			int emptyCellsAtStart = (outputPorts-inputPorts)/2;
			tmpPos = distanceFromBorder + portWithDistance*emptyCellsAtStart;
			for(int i=0; i<inputPorts; i++){
				inputPortsXPositions[i]=tmpPos;
				tmpPos += portWithDistance;
			}
		}else if(sideWithMorePorts=='b'){
			for(int i=0; i<outputPorts; i++){
				outputPortsXPositions[i]=tmpPos;
				inputPortsXPositions[i]=tmpPos;
				tmpPos += portWithDistance;
			}			
		}
	}

}
