import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Random;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;


public class Bot {

	static final float CONFIG_TEMPO_SCALE = 2f;
	static final float CONFIG_PRESS_PERCENTAGE = 0.6f;
	
	static Sequence [] sequences = new Sequence []{
		new Sequence(new MusicalEvent [] {new MusicalEvent(0, 100, 100),new MusicalEvent(2, 100, 100),new MusicalEvent(4, 100, 100),new MusicalEvent(3, 100, 100),new MusicalEvent(0, 100, 100),new MusicalEvent(2, 100, 100),new MusicalEvent(4, 100, 100),new MusicalEvent(3, 100, 100)}, 20),
		new Sequence(new MusicalEvent [] {new MusicalEvent(4, 100, 100),new MusicalEvent(2, 100, 100),new MusicalEvent(4, 100, 100),new MusicalEvent(3, 100, 100)}, 15),
		new Sequence(new MusicalEvent [] {new MusicalEvent(10, 100, 100),new MusicalEvent(8, 100, 100),new MusicalEvent(6, 100, 100),new MusicalEvent(3, 100, 100)}, 10)
	};
	
	static final float CONFIG_BASE_NOTE_CHANGE_PROBABILITY = 0.3f;
	
	static float speedfactor = 1;
	static int baseNoteWindowWidth = 30;
	static int baseNoteWindowFirst = 0;
	
	
	static int [] baseNoteImportances = new int [] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,10,0,20,0,30,0,0,0,20,0,30,0,0,20,10,0,0,40,0,20};
	private static float[] frequencies = new float[88];
	
	public static void main(String[] args) throws SocketException, UnknownHostException{
		frequencies[0]=(float) 27.5;
		for (int i = 1; i < frequencies.length; i++){
			frequencies[i] = 1.059463094f * frequencies[i-1];
		}
		
		//calculate probabilities
		

	
		int sequenceSum = 0;
		for (Sequence s : sequences){
			sequenceSum += s.importance;
		}
		
	
		float [] sequenceProbabilites = new float [sequences.length];
		for (int i = 0; i < sequences.length; i++){
			sequenceProbabilites[i] = (float)(sequences[i].importance) / sequenceSum;
		}
		

	
		Random random = new Random();

		OSCPortOut sender = new OSCPortOut(InetAddress.getByName("localhost"),50050);
		int nextBaseNoteIndex = -1;
		int nextSequenceIndex;
		while(true){
			
			int baseNoteSum = 0;
			for (int i = baseNoteWindowFirst; i < baseNoteWindowWidth; i++){
				baseNoteSum += baseNoteImportances[i];
			}
			
			float [] baseNoteProbabilites = new float [baseNoteWindowWidth];
			for (int i = baseNoteWindowFirst; i < baseNoteWindowWidth; i++){
				baseNoteProbabilites[i] = (float)(baseNoteImportances[i]) / baseNoteSum;
			}
			
			float nextSequenceValue = random.nextFloat();
			float nextBaseNoteValue = random.nextFloat();
			
			nextSequenceIndex = 0;
			while(nextSequenceValue > 0){
				nextSequenceValue -= sequenceProbabilites[nextSequenceIndex];
				nextSequenceIndex++;
				if (nextSequenceIndex >= sequences.length -1){
					break;
				}
			}
			
			if (nextBaseNoteIndex < 0 || random.nextFloat() < CONFIG_BASE_NOTE_CHANGE_PROBABILITY){
				nextBaseNoteIndex = 0;

				while(nextBaseNoteValue > 0){
					nextBaseNoteValue -= baseNoteProbabilites[nextBaseNoteIndex];
					nextBaseNoteIndex++;
					if (nextBaseNoteIndex >= baseNoteWindowWidth - 1){
						break;
					}
				}
				System.out.println("Changed Basenote to: " + nextBaseNoteIndex);
			}
			
			try {
				playSequence(sender, sequences[nextSequenceIndex], nextBaseNoteIndex + baseNoteWindowFirst);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static void sendFloat(OSCPortOut sender, String componentName, float value) throws IOException{
		OSCMessage message = new OSCMessage("/" + componentName, new Object [] { value });
		sender.send(message);
		//System.out.println("Sent: " + message.getAddress() + Arrays.toString(message.getArguments()));
	}
	
	private static void playSequence(OSCPortOut sender, Sequence sequence, int baseNote) throws IOException, InterruptedException {
		System.out.println("Playing Sequence (BaseNote: "+baseNote+"): " + Arrays.toString(sequence.events));
		for (MusicalEvent event : sequence.events){
			sendFloat(sender, "frequency", frequencies[event.getPitch() + baseNote]);
			sendFloat(sender, "volume", 1);
			
			Thread.sleep((long) (event.getDuration() * CONFIG_PRESS_PERCENTAGE *CONFIG_TEMPO_SCALE * speedfactor));
			sendFloat(sender, "volume", 0);
			Thread.sleep((long) ((event.getWaitingTime()*CONFIG_TEMPO_SCALE+event.getDuration()*(1-CONFIG_PRESS_PERCENTAGE)) * speedfactor));	
		}
		
	}

}
