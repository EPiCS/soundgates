import java.util.HashMap;


public class Score {
	HashMap<Integer, HashMap<Integer, Sequence>> voices = new HashMap<Integer, HashMap<Integer, Sequence>>();
	HashMap<Integer, Integer> positionImportance = new HashMap<Integer,Integer>();
	
	public Score (int numberOfVoices){
		for (int i = 0; i < numberOfVoices; i++){
			voices.put(i, new HashMap<Integer, Sequence>());
		}
	}
	public void setPositionImportance(int position, int importance){
		positionImportance.put(position, importance);
	}
	
	public void addSequence(int voice, int position, Sequence sequence){
		voices.get(voice).put(position, sequence);
	}
	
	public float [] getPositionProbabilites(){
		int positions = findLargestPosition();
		float [] result = new float [positions +1];
		int sum = 0;
		for (int i = 0; i <= positions; i++){
			if (positionImportance.containsKey(i))
				sum += positionImportance.get(i);
		}
		
		for (int i = 0; i < result.length; i++){
			if (positionImportance.containsKey(i)){
				result[i] = (float)positionImportance.get(i) / sum;
			} else {
				result[i] = 0;
			}
		}
		return result;
	}

	private int findLargestPosition(){
		int largestVoice = 0;
		for (Object key : voices.keySet()){
			HashMap<Integer, Sequence> map = voices.get(key);
			for (Integer position : map.keySet()){
				if (position > largestVoice){
					largestVoice = position;
				}
			}
		}
		return largestVoice;
	}
	
	public Sequence [][] getVoices(){
		Sequence [][] result = new Sequence [voices.size()][];
		

		
		for (int i = 0; i < result.length; i++){
			result [i] = new Sequence [findLargestPosition() + 1];
			for(int j = 0; j < result[i].length; j++){
				if (voices.get(i).containsKey(j)){
					result[i][j] = voices.get(i).get(j);
				} else {
					result[i][j] = new Sequence(new MusicalEvent[]{}, 0);
				}
			}
		}
		return result;
	}
}
