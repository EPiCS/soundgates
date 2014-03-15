
public class Chord {
	int [] notes;
	int [] modifiers;
	
	public Chord (int... notes){
		this.notes = notes;
		this.modifiers = new int [notes.length];
	}
	
	@Override
	public String toString() {
		String result = "[";
		for(int i = 0; i < notes.length; i++){
			result += "Note: " + notes[i] + " Mod: " + modifiers[i];
			if (i == notes.length -1){
				result += "]";
			} else {
				result += " ";
			}
		}
		return result;
	}
}
