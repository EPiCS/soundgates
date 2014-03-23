import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;
import com.illposed.osc.OSCPortOut;


public class Bot {

	static final int NOTE_ZERO = 0;
	static final int NOTE_SIXTEENTH = 625;
	static final int NOTE_EIGTHS = 1250;
	static final int NOTE_QUARTER = 2500;
	static final int NOTE_HALF = 5000;
	static final int NOTE_FULL = 10000;
	
	static final float CONFIG_TEMPO_SCALE = 0.2f;
	static final int CONFIG_FIRST_BASE_NOTE = 0;
	static final float CONFIG_PRESS_PERCENTAGE = 0.6f;
	
	//Moll: 0 2 3 5 7 8 10 12
	
	static final int[] SCALE = new int[] { 0, 2, 3, 5, 7, 8, 10 };
	static final int SCALE_LENGTH = 12;
	
	
	static Score score;

	static final float CONFIG_BASE_NOTE_CHANGE_PROBABILITY = 1f;
	static int baseNoteMin = 1;
	static int baseNoteMax = 1;
	
	static final String CONFIG_RYTHM_SYNC_MESSAGE = "/rythmSync";
	static boolean useRythmSync = false;

	private static float[] frequencies = new float[128];
	private static int [] notes;
	
	static int baseNoteMean = 30; 
	static float baseNoteStandardDeviation = 1;
	
	static OSCPortOut sender;
	static OSCPortIn receiver;
	
	static int listenPort = 50050;
	static String filename = "";
	
	static boolean playSequenceNow = false;
	static Date currentSequenceEnd = Calendar.getInstance().getTime();

	final static Random random = new Random();
	
	public static void main(String[] args) throws SocketException, UnknownHostException{
		if (args.length == 0){

			System.out.println("Usage: Bot <filename> <optionalListenPort>");
		}
		
		if (args.length >= 1){
			filename = args[0];
		}
		if (args.length >= 2){
			useRythmSync = true;
			listenPort = Integer.parseInt(args[1]);
		}
		
		sender = new OSCPortOut(InetAddress.getByName("localhost"),50050);

		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			@Override
			public void run() {
				System.out.println("Terminating");
				try {
					sendFloat(sender, "volume", 0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sender.close();
				receiver.stopListening();
				receiver.close();
				
			}
		});
		
		score = createScore();
		
		frequencies[0]=(float) 27.5;
		for (int i = 1; i < frequencies.length; i++){
			frequencies[i] = 1.059463094f * frequencies[i-1];
		}
		
		calculateNotes();

	


		
		final Sequence [][] voices = score.getVoices();
		final float [] positionProbabilities = score.getPositionProbabilites();
		
		if (useRythmSync){
			receiver = new OSCPortIn(listenPort);
			receiver.addListener(CONFIG_RYTHM_SYNC_MESSAGE ,new OSCListener() {
				
				@Override
				public void acceptMessage(Date receivedAt, OSCMessage arg1) {
					if (currentSequenceEnd.before(receivedAt)){
						generateAndPlay(random, positionProbabilities, voices);
					}
				}
			});
			receiver.startListening();
		} else {
			while(true){
				int playedPosition = generateAndPlay(random, positionProbabilities, voices);
				try {
					Thread.sleep(getLengthOfPosition(voices, playedPosition));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private static int getGaussian(double mean, double deviation, int min, int max){
		double result = random.nextGaussian() * deviation + mean;
		if (result < min){
			return min;
		} else if (result > max){
			return max;
		}
		return (int) Math.round(result);
	}
	
	private static int generateAndPlay(Random random, float [] positionProbabilities, Sequence [][] voices){
		
		float nextPositionValue = random.nextFloat();
		
		int nextPositionIndex = 0;
		while(nextPositionValue > positionProbabilities[nextPositionIndex]){
			nextPositionValue -= positionProbabilities[nextPositionIndex];
			nextPositionIndex++;
			if (nextPositionIndex >= positionProbabilities.length -1){
				break;
			}
		}
		
		int nextBaseNoteIndex = 0;
		if (random.nextFloat() < CONFIG_BASE_NOTE_CHANGE_PROBABILITY){
			nextBaseNoteIndex = getGaussian(baseNoteMean, baseNoteStandardDeviation, baseNoteMin, baseNoteMax);;

			
			System.out.println("Changed Basenote to: " + notes[nextBaseNoteIndex]);
		}
		
		try {
			playSequence(sender, voices, nextPositionIndex, notes[nextBaseNoteIndex]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nextPositionIndex;
	}

	private static long getLengthOfPosition(Sequence[][] voices,
			int position) {
		double length = 0;
		for (int i = 0; i < voices.length; i ++){
			double tmpLength = 0;
			for (MusicalEvent event : voices[i][position].events){
				tmpLength += (event.getDuration() + event.getWaitingTime() ) *CONFIG_TEMPO_SCALE;
			}
			if (length < tmpLength){
				length = tmpLength;
			}
		}
		return (long)Math.ceil(length);
	}
	
	private static Score createScore() {
		Score result = new Score(2);
		result.addSequence(0, 0, new Sequence(new MusicalEvent[]{
				new MusicalEvent(new Chord(0,3,5), NOTE_FULL, NOTE_ZERO),
				new MusicalEvent(new Chord(1,4,6), NOTE_FULL, NOTE_ZERO)},  20));
		result.addSequence(0, 1, new Sequence(new MusicalEvent[]{
				new MusicalEvent(new Chord(0,3,6), NOTE_FULL, NOTE_ZERO)},  5));
		result.addSequence(0, 2, new Sequence(new MusicalEvent[]{
				new MusicalEvent(new Chord(0,4,6), NOTE_FULL, NOTE_ZERO)},  5));
		result.addSequence(1, 0, new Sequence(new MusicalEvent [] {
				new MusicalEvent(0, NOTE_QUARTER, NOTE_ZERO),
				new MusicalEvent(3, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(3, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(4, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(5, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(6, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(5, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(4, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(3, NOTE_EIGTHS, NOTE_HALF)}, 20));
		result.addSequence(1, 1, new Sequence(new MusicalEvent [] {
				new MusicalEvent(0, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(2, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(3, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(4, NOTE_QUARTER, NOTE_ZERO),
				new MusicalEvent(4, NOTE_QUARTER, NOTE_EIGTHS)}, 20));
		result.addSequence(1, 2, new Sequence(new MusicalEvent [] {
				new MusicalEvent(3, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(3, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(3, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(3, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(4, NOTE_QUARTER, NOTE_ZERO),
				new MusicalEvent(3, NOTE_QUARTER, NOTE_ZERO)}, 20));
		result.setPositionImportance(0, 20);
		result.setPositionImportance(1, 15);
		result.setPositionImportance(2, 10);
		return result;
	}

	private static void calculateNotes() {
		int currentNote = 0;
		int octaveCounter = 0;
		List<Integer> noteList = new ArrayList<Integer>();
		int index = 0;
		
		while (true) {
			
			currentNote = SCALE[index % SCALE.length] + octaveCounter * SCALE_LENGTH;
			if (currentNote >= frequencies.length){
				break;
			}
			
			noteList.add(currentNote);
			index++;
			if (index % SCALE.length == 0){
				octaveCounter++;
			}
			
		}

		notes = new int [noteList.size()];
		for (int i = 0; i < notes.length; i++){
			notes [i] = noteList.get(i);
		}
		
		baseNoteMin = 0;
		baseNoteMax = notes.length - getMaxNote() - 1;
	}
	
	private static int getMaxNote(){
		//FIXME actually calculate
		return 8;
	}

	public static void sendFloat(OSCPortOut sender, String componentName, float value) throws IOException{
		OSCMessage message = new OSCMessage("/" + componentName, new Object [] { value });
		sender.send(message);
		//System.out.println("Sent: " + message.getAddress() + Arrays.toString(message.getArguments()));
	}
	
	private static void playSequence(final OSCPortOut sender, Sequence [][] voices, final int position,final int baseNote) throws IOException, InterruptedException {
		System.out.print("Playing Sequence (BaseNote: "+baseNote+"): ");
		
		for (int i = 0; i < voices.length; i++){
			System.out.print(voices[i][position]);
		}
		System.out.println();
		
		for (int i = 0; i < voices.length; i++){
			final Sequence toPlay = voices[i][position];
			final int voice = i;
			Thread sequencePlayer = new Thread(){
				@Override
				public void run() {
					for (MusicalEvent event : toPlay.events){
						for (int i = 0; i < event.getChord().notes.length; i++){
							try {
								sendFloat(sender, "frequency_" + voice + "_" + i, frequencies[notes[event.getChord().notes[i] - event.getModifier()] + baseNote]);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						try {
							sendFloat(sender, "trigger_" + voice, 1);
							Thread.sleep((long) ((float)(event.getDuration() * CONFIG_PRESS_PERCENTAGE) * CONFIG_TEMPO_SCALE));
							sendFloat(sender, "trigger_" + voice, 0);
							Thread.sleep((long) ((float)(event.getWaitingTime()+event.getDuration())*CONFIG_TEMPO_SCALE*(1-CONFIG_PRESS_PERCENTAGE)));
						} catch (IOException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
							
					}
				}
				
			};
			sequencePlayer.start();
		}
		
		
	}

}
