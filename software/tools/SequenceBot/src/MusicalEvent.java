
public class MusicalEvent {
	private int pitch = 0;
	private long duration = 0;
	private long waitingTime = 0;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MusicalEvent other = (MusicalEvent) obj;
		if (duration != other.duration)
			return false;
		if (pitch != other.pitch)
			return false;
		if (waitingTime != other.waitingTime)
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (duration ^ (duration >>> 32));
		result = prime * result + pitch;
		result = prime * result + (int) (waitingTime ^ (waitingTime >>> 32));
		return result;
	}
	@Override
	public String toString() {
		return "[Pitch:"+pitch+",Duration:" +duration+ ",Wait:" + waitingTime+"]";
	}
	public MusicalEvent(int pitch, long duration, long waitingTime) {
		super();
		this.pitch = pitch;
		this.duration = duration;
		this.waitingTime = waitingTime;
	}
	public long getWaitingTime() {
		return waitingTime;
	}
	public void setWaitingTime(long waitingTime) {
		this.waitingTime = waitingTime;
	}
	public int getPitch() {
		return pitch;
	}
	public void setPitch(int pitch) {
		this.pitch = pitch;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
}
