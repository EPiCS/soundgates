package de.upb.soundgates.mariobot;

public class ProbabilityData {
	private MusicalEvent event;
	private float probability = 0;
	
	public ProbabilityData(MusicalEvent event, float probability) {
		super();
		this.event = event;
		this.probability = probability;
	}
	public MusicalEvent getEvent() {
		return event;
	}
	public void setEvent(MusicalEvent event) {
		this.event = event;
	}
	public float getProbability() {
		return probability;
	}
	public void setProbability(float probability) {
		this.probability = probability;
	}
	
}
