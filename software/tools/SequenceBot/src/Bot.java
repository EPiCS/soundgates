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
	
	static final float CONFIG_TEMPO_SCALE_DEFAULT = 0.2f;
	static final int CONFIG_FIRST_BASE_NOTE = 0;
	static final float CONFIG_PRESS_PERCENTAGE_DEFAULT = 0.6f;
	
	static float pressPercentage = CONFIG_PRESS_PERCENTAGE_DEFAULT;
	static float tempoScale = CONFIG_TEMPO_SCALE_DEFAULT;

	//Moll: 0 2 3 5 7 8 10 12
	//Dur: 0 2 4 5 7 9 11 12

	static final int[] SCALE = new int[] { 0, 2, 4, 5, 7, 9, 11 };
	//static final int[] SCALE = new int[] { 0, 2, 3, 5, 7, 8, 10 };
	static final int SCALE_LENGTH = 12;
	

	static Score score;
	static Score rythm;

	static final float CONFIG_BASE_NOTE_CHANGE_PROBABILITY = 1f;
	static int baseNoteMin = 1;
	static int baseNoteMax = 1;

	static final String CONFIG_RYTHM_SYNC_MESSAGE = "/rythmSync";
	static final String CONFIG_BASENOTE_MEAN_MESSAGE = "/baseNoteMean";
	static final String CONFIG_SEQUENCE_MEAN_MESSAGE = "/sequenceMean";
	static final String CONFIG_RYTHM_MEAN_MESSAGE = "/rythmMean";
	static final String CONFIG_BASENOTE_DEVIATION_MESSAGE = "/baseNoteDeviation";
	static final String CONFIG_SEQUENCE_DEVIATION_MESSAGE = "/sequenceDeviation";
	static final String CONFIG_RYTHM_DEVIATION_MESSAGE = "/rythmDeviation";
	static final String CONFIG_HIT_PERCENTAGE_MESSAGE = "/hitPercentage";
	static final String CONFIG_SPEED_MESSAGE = "/slowdown";
	
	static boolean useRythmSync = false;

	private static float[] frequencies = new float[128];
	private static int [] notes;
	
	static int baseNoteMean = 30; 
	static float baseNoteStandardDeviation = 1;
	
	static int sequenceMean = 0; 
	static float sequenceStandardDeviation = 1;
	
	static int rythmMean = 0; 
	static float rythmStandardDeviation = 1;
	
	static OSCPortOut sender;
	static OSCPortIn receiver;
	
	static int listenPort = 50051;
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
		
		List<String []> inputs = new ArrayList<String []>();
		inputs.add(new String []{CONFIG_BASENOTE_DEVIATION_MESSAGE, "0", "10"});
		inputs.add(new String []{CONFIG_BASENOTE_MEAN_MESSAGE, "0", "50"});
		inputs.add(new String []{CONFIG_SEQUENCE_MEAN_MESSAGE, "0", "10"});
		inputs.add(new String []{CONFIG_SEQUENCE_DEVIATION_MESSAGE, "0", "2"});
		inputs.add(new String []{CONFIG_HIT_PERCENTAGE_MESSAGE, "0.1", "0.99"});
		inputs.add(new String []{CONFIG_RYTHM_SYNC_MESSAGE, "0", "1"});
		inputs.add(new String []{CONFIG_SPEED_MESSAGE, "0.1", "0.4"});
		final HandshakeThread hsThread = new HandshakeThread(inputs, 50051);
		hsThread.start();
		
		sender = new OSCPortOut(InetAddress.getByName("localhost"),50050);

		receiver = new OSCPortIn(listenPort);
		receiver.startListening();
		
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
				hsThread.stopMe();
				
			}
		});
		
		//score = createScore();
		score = vengaScore();
		rythm = vengaRythm();
		
		addListener(receiver);
		
		frequencies[0]=(float) 27.5;
		for (int i = 1; i < frequencies.length; i++){
			frequencies[i] = 1.059463094f * frequencies[i-1];
		}
		
		calculateNotes();

	



		final Sequence [][] voices = score.getVoices();
		final Sequence [][] rythmVoices = rythm.getVoices();
		final float [] positionProbabilities = score.getPositionProbabilites();
		
		if (useRythmSync){
			receiver.addListener(CONFIG_RYTHM_SYNC_MESSAGE ,new OSCListener() {
				
				@Override
				public void acceptMessage(Date receivedAt, OSCMessage arg1) {
					if (currentSequenceEnd.before(receivedAt)){
						generateAndPlay(random, positionProbabilities, voices, rythmVoices);
					}
				}
			});
			
		} else {
			while(true){
				long time = generateAndPlay(random, positionProbabilities, voices, rythmVoices);
				try {
					
					
					Thread.sleep(time);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void addListener(OSCPortIn receiver) {
		receiver.addListener(CONFIG_BASENOTE_MEAN_MESSAGE, new OSCListener() {
			
			@Override
			public void acceptMessage(Date arg0, OSCMessage arg1) {
				baseNoteMean = (int)Math.round((float)arg1.getArguments()[0]);
			}
		});
		receiver.addListener(CONFIG_BASENOTE_DEVIATION_MESSAGE, new OSCListener() {
			
			@Override
			public void acceptMessage(Date arg0, OSCMessage arg1) {
				baseNoteStandardDeviation = (float) arg1.getArguments()[0];
			}
		});
		receiver.addListener(CONFIG_HIT_PERCENTAGE_MESSAGE, new OSCListener() {
			
			@Override
			public void acceptMessage(Date arg0, OSCMessage arg1) {
				pressPercentage = (float) arg1.getArguments()[0];
			}
		});
		receiver.addListener(CONFIG_SPEED_MESSAGE, new OSCListener() {
			
			@Override
			public void acceptMessage(Date arg0, OSCMessage arg1) {
				tempoScale =  (float) arg1.getArguments()[0];
			}
		});
		receiver.addListener(CONFIG_SEQUENCE_DEVIATION_MESSAGE, new OSCListener() {
			
			@Override
			public void acceptMessage(Date arg0, OSCMessage arg1) {
				sequenceStandardDeviation = (float)arg1.getArguments()[0];
			}
		});
		receiver.addListener(CONFIG_SEQUENCE_MEAN_MESSAGE, new OSCListener() {
			
			@Override
			public void acceptMessage(Date arg0, OSCMessage arg1) {
				sequenceMean = (int)Math.round((float)arg1.getArguments()[0]);
			}
		});
		receiver.addListener(CONFIG_RYTHM_DEVIATION_MESSAGE, new OSCListener() {
			
			@Override
			public void acceptMessage(Date arg0, OSCMessage arg1) {
				rythmStandardDeviation = (float)arg1.getArguments()[0];
			}
		});
		receiver.addListener(CONFIG_RYTHM_MEAN_MESSAGE, new OSCListener() {
			
			@Override
			public void acceptMessage(Date arg0, OSCMessage arg1) {
				rythmMean = (int)Math.round((float)arg1.getArguments()[0]);
			}
		});
	}

	private static int getGaussian(double mean, double deviation, int min, int max){
		double result = random.nextGaussian() * deviation + mean;
		if (result < min){
			return min;
		} else if (result > max -1){
			return max -1;
		}
		return (int) Math.round(result);
	}
	
	private static long generateAndPlay(Random random, float [] positionProbabilities, Sequence [][] voices, Sequence [][] rythmVoices){
		

		int nextPositionIndex = 0;
		nextPositionIndex = getGaussian(sequenceMean, sequenceStandardDeviation, 0, voices[0].length);
		System.out.println("Changed Position to: " + nextPositionIndex);
		int nextRythmIndex = 0;
		nextRythmIndex = getGaussian(rythmMean, rythmStandardDeviation, 0, rythmVoices[0].length);
		System.out.println("Changed Rythm to: " + nextRythmIndex);
		
		int nextBaseNoteIndex = 0;
		if (random.nextFloat() < CONFIG_BASE_NOTE_CHANGE_PROBABILITY){
			nextBaseNoteIndex = getGaussian(baseNoteMean, baseNoteStandardDeviation, baseNoteMin, baseNoteMax);
			System.out.println("Changed Basenote to: " + notes[nextBaseNoteIndex]);
		}
		long length = 0;
		try {
			playSequence(sender, "melody" , voices, nextPositionIndex, notes[nextBaseNoteIndex]);
			playSequence(sender, "rythm", rythmVoices, nextRythmIndex, notes[nextBaseNoteIndex]);
			
			length = getLengthOfPosition(voices, nextPositionIndex);
			long tmp = getLengthOfPosition(rythmVoices, nextRythmIndex);
			if (length < tmp) length = tmp;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return length;
	}

	private static long getLengthOfPosition(Sequence[][] voices,
			int position) {
		double length = 0;
		for (int i = 0; i < voices.length; i ++){
			double tmpLength = 0;
			for (MusicalEvent event : voices[i][position].events){
				tmpLength += (event.getDuration() + event.getWaitingTime() ) * tempoScale;
			}
			if (length < tmpLength){
				length = tmpLength;
			}
		}
		return (long)Math.ceil(length);
	}
	
	private static Score vengaRythm(){
		Score result = new Score(4);
		Sequence rythm1 = new Sequence(new RythmEvent[]{
				new RythmEvent(new Chord(0), NOTE_QUARTER, NOTE_QUARTER),	
				new RythmEvent(new Chord(0), NOTE_QUARTER, NOTE_QUARTER),	
				new RythmEvent(new Chord(0), NOTE_QUARTER, NOTE_QUARTER),	
				new RythmEvent(new Chord(0), NOTE_QUARTER, NOTE_QUARTER)	
		}, 20);

		Sequence rythm2 = new Sequence(new RythmEvent[]{
				new RythmEvent(new Chord(0), NOTE_ZERO, NOTE_EIGTHS),
				new RythmEvent(new Chord(0), NOTE_SIXTEENTH, NOTE_ZERO),
				new RythmEvent(new Chord(0), NOTE_SIXTEENTH, NOTE_EIGTHS),
				new RythmEvent(new Chord(0), NOTE_SIXTEENTH, NOTE_ZERO),
				new RythmEvent(new Chord(0), NOTE_SIXTEENTH, NOTE_EIGTHS),
				new RythmEvent(new Chord(0), NOTE_SIXTEENTH, NOTE_ZERO),
				new RythmEvent(new Chord(0), NOTE_SIXTEENTH, NOTE_EIGTHS),
				new RythmEvent(new Chord(0), NOTE_SIXTEENTH, NOTE_ZERO),
				new RythmEvent(new Chord(0), NOTE_SIXTEENTH, NOTE_EIGTHS)	
		}, 20);

		result.addSequence(0, 4, rythm1);
		result.addSequence(0, 5, rythm1);
		result.addSequence(2, 5, rythm2);
		
		return result;
	}
	
	private static Score vengaScore(){
		Score result = new Score(1);
		
		Sequence empty = new Sequence(new MusicalEvent[]{
				new MusicalEvent(new Chord(), NOTE_ZERO, NOTE_FULL)},  20);
		
		Sequence slow = new Sequence(new MusicalEvent[]{
				new MusicalEvent(new Chord(0,9), NOTE_EIGTHS + NOTE_SIXTEENTH, NOTE_HALF + NOTE_QUARTER + NOTE_SIXTEENTH)},  20);
		
		Sequence mediumUp = new Sequence(new MusicalEvent[]{
				new MusicalEvent(new Chord(0,9), NOTE_EIGTHS, NOTE_QUARTER),
				new MusicalEvent(new Chord(0,9), NOTE_EIGTHS, NOTE_QUARTER),
				new MusicalEvent(new Chord(3,12), NOTE_EIGTHS, NOTE_EIGTHS),
				new MusicalEvent(new Chord(3,12), NOTE_EIGTHS, NOTE_EIGTHS)},  20);
		
		Sequence mediumDown = new Sequence(new MusicalEvent[]{
				new MusicalEvent(new Chord(4,13), NOTE_EIGTHS + NOTE_SIXTEENTH, NOTE_EIGTHS + NOTE_SIXTEENTH),
				new MusicalEvent(new Chord(4,13), NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(new Chord(3,12), NOTE_EIGTHS + NOTE_SIXTEENTH, NOTE_SIXTEENTH),
				new MusicalEvent(new Chord(3,12), NOTE_EIGTHS + NOTE_SIXTEENTH, NOTE_SIXTEENTH)},  20);
		
		Sequence mediumUp2 = new Sequence(new MusicalEvent[]{
				new MusicalEvent(new Chord(0,9), NOTE_EIGTHS + NOTE_SIXTEENTH, NOTE_EIGTHS + NOTE_SIXTEENTH),
				new MusicalEvent(new Chord(0,9), NOTE_EIGTHS, NOTE_QUARTER),
				new MusicalEvent(new Chord(3,12), NOTE_EIGTHS, NOTE_EIGTHS),
				new MusicalEvent(new Chord(3,12), NOTE_EIGTHS, NOTE_EIGTHS)},  20);
		
		Sequence fast1 = new Sequence(new MusicalEvent[]{
				new MusicalEvent(new Chord(4,13), NOTE_EIGTHS + NOTE_SIXTEENTH, NOTE_EIGTHS + NOTE_SIXTEENTH),
				new MusicalEvent(new Chord(4,13), NOTE_SIXTEENTH, NOTE_ZERO),
				new MusicalEvent(new Chord(4,13), NOTE_SIXTEENTH, NOTE_ZERO),
				new MusicalEvent(new Chord(4,13), NOTE_SIXTEENTH, NOTE_SIXTEENTH),
				new MusicalEvent(new Chord(4,13), NOTE_SIXTEENTH, NOTE_SIXTEENTH),
				new MusicalEvent(new Chord(3,12), NOTE_SIXTEENTH, NOTE_SIXTEENTH),
				new MusicalEvent(new Chord(3,12), NOTE_SIXTEENTH, NOTE_SIXTEENTH)
				},  20);
		
		Sequence fast2 = new Sequence(new MusicalEvent[]{
				new MusicalEvent(new Chord(0,9), NOTE_EIGTHS, NOTE_SIXTEENTH),
				new MusicalEvent(new Chord(0,9), NOTE_SIXTEENTH, NOTE_EIGTHS),
				new MusicalEvent(new Chord(4,13,15), NOTE_SIXTEENTH, NOTE_SIXTEENTH),
				new MusicalEvent(new Chord(4,13,15), NOTE_SIXTEENTH, NOTE_SIXTEENTH),
				new MusicalEvent(new Chord(4,13,15), NOTE_SIXTEENTH, NOTE_SIXTEENTH),
				new MusicalEvent(new Chord(3,12,15), NOTE_SIXTEENTH, NOTE_SIXTEENTH),
				new MusicalEvent(new Chord(3,12,15), NOTE_SIXTEENTH, NOTE_SIXTEENTH)
				},  20);


		result.addSequence(0, 0, empty);
		result.addSequence(0, 1, slow);
		result.addSequence(0, 2, mediumUp);
		result.addSequence(0, 3, mediumDown);
		result.addSequence(0, 4, mediumUp2);
		result.addSequence(0, 5, fast1);
		result.addSequence(0, 6, fast2);
		
		return result;
	}
	
	private static Score createScore() {
		Score result = new Score(2);
		
		Sequence chord1 = new Sequence(new MusicalEvent[]{
				new MusicalEvent(new Chord(0,3,5), NOTE_FULL, NOTE_ZERO),
				new MusicalEvent(new Chord(1,4,6), NOTE_FULL, NOTE_ZERO)},  20);
		
		Sequence chord2 = new Sequence(new MusicalEvent[]{
				new MusicalEvent(new Chord(0,3,6), NOTE_FULL, NOTE_ZERO)},  5);
		
		Sequence chord3 = new Sequence(new MusicalEvent[]{
				new MusicalEvent(new Chord(0,4,6), NOTE_FULL, NOTE_ZERO)},  5);
		
		
		Sequence melody1 = new Sequence(new MusicalEvent [] {
				new MusicalEvent(0, NOTE_QUARTER, NOTE_ZERO),
				new MusicalEvent(3, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(4, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(0, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(3, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(4, NOTE_QUARTER, NOTE_ZERO)}, 20);
		
		Sequence melody2 = new Sequence(new MusicalEvent [] {
				new MusicalEvent(0, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(2, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(3, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(4, NOTE_QUARTER, NOTE_ZERO),
				new MusicalEvent(4, NOTE_QUARTER, NOTE_EIGTHS)}, 20);
		
		Sequence melody3 = new Sequence(new MusicalEvent [] {
				new MusicalEvent(0, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(3, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(0, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(3, NOTE_EIGTHS, NOTE_ZERO),
				new MusicalEvent(4, NOTE_QUARTER, NOTE_ZERO),
				new MusicalEvent(3, NOTE_QUARTER, NOTE_ZERO)}, 20);
		
		result.addSequence(0, 0, chord1);
		result.addSequence(0, 1, chord2);
		result.addSequence(0, 2, chord3);
		result.addSequence(1, 0, melody1);
		result.addSequence(1, 1, melody2);
		result.addSequence(1, 2, melody3);
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
		return 16;
	}
	
	public static void sendFloat(OSCPortOut sender, String componentName, float value) throws IOException{
		OSCMessage message = new OSCMessage("/" + componentName, new Object [] { value });
		sender.send(message);
		//System.out.println("Sent: " + message.getAddress() + Arrays.toString(message.getArguments()));
	}
	
	private static void playSequence(final OSCPortOut sender,final String prefix, Sequence [][] voices, final int position,final int baseNote) throws IOException, InterruptedException {
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
							if (!(event instanceof RythmEvent))
								sendFrequency(sender, voice, i, event);
						}
						try {
							if (!(event instanceof RythmEvent)){
								if (event.getDuration() > 0){
									sendFloat(sender, prefix +  "_trigger_" + voice, 1);	
								}
								Thread.sleep((long) ((float)(event.getDuration() * pressPercentage) * tempoScale));
								sendFloat(sender, prefix +  "_trigger_" + voice, 0);
								Thread.sleep((long) ((float)(event.getWaitingTime()+event.getDuration())*tempoScale*(1-pressPercentage)));
							} else {
								if (event.getDuration() > 0){
									sendFloat(sender, prefix +  "_trigger_" + voice, 1);	
								}
								Thread.sleep((long) ((float)(event.getWaitingTime()+event.getDuration())*tempoScale));
							}
						} catch (IOException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
							
					}
				}

				private void sendFrequency(OSCPortOut sender, int voice, int i,
						MusicalEvent event) {
					try {
						sendFloat(sender, prefix +  "_frequency_" + voice + "_" + i, frequencies[notes[event.getChord().notes[i] - event.getModifier()] + baseNote]);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			};
			sequencePlayer.start();
		}
		
		
	}
	
}
