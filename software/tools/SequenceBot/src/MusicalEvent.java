

public class MusicalEvent {
	private Chord chord = null;
	private int modifier = 0;
	private long duration = 0;
	private long waitingTime = 0;
	
	@Override
	public String toString() {
		return "[Chord:"+chord+",Duration:" +duration+ ",Wait:" + waitingTime+"]";
	}
	
	public MusicalEvent(int pitch, long duration, long waitingTime) {
		this(new Chord(pitch), duration, waitingTime);
	}
	
	public MusicalEvent(Chord chord, long duration, long waitingTime){
		super();
		this.chord = chord;
		this.duration = duration;
		this.waitingTime = waitingTime;
	}
	
	public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	public MusicalEvent(int pitch, int modifier, long duration, long waitingTime) {
		this(pitch, duration, waitingTime);
		this.modifier = modifier;
	}
	public long getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(long waitingTime) {
		this.waitingTime = waitingTime;
	}

	public Chord getChord() {
		return chord;
	}

	public void setChord(Chord chord) {
		this.chord = chord;
	}

	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
}
