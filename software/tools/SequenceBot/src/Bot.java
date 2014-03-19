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

	static final float CONFIG_TEMPO_SCALE = 2f;
	static final int CONFIG_FIRST_BASE_NOTE = 0;
	static final float CONFIG_PRESS_PERCENTAGE = 0.6f;
	
	//Moll: 0 2 3 5 7 8 10 12
	
	static final int[] SCALE = new int[] { 0, 2, 3, 5, 7, 8, 10 };
	static final int SCALE_LENGTH = 12;
	//static int [] baseNoteImportances = new int [] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

	static int [] baseNoteImportances = new int [] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,20,20,80,20,20,20,80,20,80,80,20,20,20,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	static Score score;
	
	static final float CONFIG_BASE_NOTE_CHANGE_PROBABILITY = 1f;
	static final String CONFIG_RYTHM_SYNC_MESSAGE = "/rythmSync";
	static boolean useRythmSync = false;
	static float speedfactor = 1;

	private static float[] frequencies = new float[128];
	private static int [] notes;
	

	static OSCPortOut sender;
	static OSCPortIn receiver;
	
	static int listenPort = 50050;
	static String filename = "";
	
	static boolean playSequenceNow = false;
	static Date currentSequenceEnd = Calendar.getInstance().getTime();
	
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

	

		final Random random = new Random();

		
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
	
	private static int generateAndPlay(Random random, float [] positionProbabilities, Sequence [][] voices){
		
		int baseNoteSum = 0;
		for (int i = 0; i < notes.length; i++){
			baseNoteSum += baseNoteImportances[notes[i]];
			
		}
		
		float [] baseNoteProbabilites = new float [baseNoteImportances.length];
		for (int i = 0; i < baseNoteImportances.length; i++){
			baseNoteProbabilites[i] = (float)(baseNoteImportances[i]) / baseNoteSum;
		}
		
		float nextPositionValue = random.nextFloat();
		float nextBaseNoteValue = random.nextFloat();
		
		int nextPositionIndex = 0;
		while(nextPositionValue > positionProbabilities[nextPositionIndex]){
			nextPositionValue -= positionProbabilities[nextPositionIndex];
			nextPositionIndex++;
			if (nextPositionIndex >= positionProbabilities.length -1){
				break;
			}
		}
		int nextBaseNoteIndex = CONFIG_FIRST_BASE_NOTE;
		if (random.nextFloat() < CONFIG_BASE_NOTE_CHANGE_PROBABILITY){
			nextBaseNoteIndex = 0;

			while(nextBaseNoteValue > baseNoteProbabilites[notes[nextBaseNoteIndex]]){
				nextBaseNoteValue -= baseNoteProbabilites[notes[nextBaseNoteIndex]];
				nextBaseNoteIndex++;
				if (nextBaseNoteIndex >= notes.length - 1){
					break;
				}
			}
			
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
		long length = 0;
		for (int i = 0; i < voices.length; i ++){
			long tmpLength = 0;
			for (MusicalEvent event : voices[i][position].events){
				tmpLength += event.getDuration() + event.getWaitingTime();
			}
			if (length < tmpLength){
				length = tmpLength;
			}
		}
		return length;
	}
	
	private static Score createScore() {
		Score result = new Score(2);
		result.addSequence(0, 0, new Sequence(new MusicalEvent [] {new MusicalEvent(0, 200, 0),new MusicalEvent(1, 100, 100),new MusicalEvent(2, 100, 100),new MusicalEvent(3, 100, 100),new MusicalEvent(7, 100, 0),new MusicalEvent(5, 100, 0),new MusicalEvent(3, 100, 100),new MusicalEvent(2, 100, 100)}, 20));
		result.addSequence(0, 1, new Sequence(new MusicalEvent [] {new MusicalEvent(4, 100, 100),new MusicalEvent(2, 100, 100),new MusicalEvent(4, 100, 0),new MusicalEvent(1, 100, 0)}, 15));
		result.addSequence(0, 2, new Sequence(new MusicalEvent [] {new MusicalEvent(8, 100, 100),new MusicalEvent(7, 100, 100),new MusicalEvent(8, 100, 100),new MusicalEvent(9, 100, 100)}, 10));
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
								sendFloat(sender, "frequency" + voice + "_" + i, frequencies[event.getChord().notes[i] - event.getModifier() + baseNote]);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						try {
							sendFloat(sender, "volume", 1);
							Thread.sleep((long) (event.getDuration() * CONFIG_PRESS_PERCENTAGE *CONFIG_TEMPO_SCALE * speedfactor));
							sendFloat(sender, "volume", 0);
							Thread.sleep((long) ((event.getWaitingTime()*CONFIG_TEMPO_SCALE+event.getDuration()*(1-CONFIG_PRESS_PERCENTAGE)) * speedfactor));
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
