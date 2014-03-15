import java.util.Arrays;


public class Sequence {
	MusicalEvent [] events;
	int importance = 0;

	public Sequence(MusicalEvent[] events, int importance) {
		super();
		this.events = events;
		this.importance = importance;
	}
	
	@Override
	public String toString() {
		return "[Importance: " + importance + Arrays.toString(events) + "]";
	}
}
