package de.upb.soundgates.mariobot;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Random;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

public class Bot {

	static float [] frequencies = new float [88];
	
	public static void main(String[] args) {
		
		frequencies[0]=(float) 27.5;
		for (int i = 1; i < frequencies.length; i++){
			frequencies[i] = 1.059463094f * frequencies[i-1];
		}
		
		try {
//			SeedData seedData = readMidiFile(new File("entchen.mid"));
			SeedData seedData = readMidiFile(new File("sm3grass.mid"));
			randomize(seedData);
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void randomize(SeedData seedData) throws SocketException, UnknownHostException {
		HashMap<MusicalEvent, HashMap<MusicalEvent, Double>> data = seedData.getProbabilities();
		
		Random random = new Random();
		
		MusicalEvent currentEvent = (MusicalEvent) data.keySet().toArray()[random.nextInt(data.keySet().size())];
		MusicalEvent nextElement = null;
		
		OSCPortOut sender = new OSCPortOut(InetAddress.getByName("localhost"),50050);
		
		while(true){
			//find next event
			double nextElementRandomValue = random.nextDouble();
			MusicalEvent [] nextElementCandidates = new MusicalEvent [0];
			if (data.containsKey(currentEvent)){
				nextElementCandidates = data.get(currentEvent).keySet().toArray(nextElementCandidates);	
			}
			
			int counter = 0;
			for(; counter < nextElementCandidates.length; counter++){
				nextElementRandomValue -= data.get(currentEvent).get(nextElementCandidates[counter]);
				if (nextElementRandomValue <= 0 || counter >= nextElementCandidates.length - 1){
					break;
				}
			}
			
			if (nextElementCandidates.length > 0){
				nextElement = nextElementCandidates[counter];	
			} else {
				nextElement = (MusicalEvent) data.keySet().toArray()[random.nextInt(data.keySet().size())];
			}
		
			//send it
			try {
				sendFloat(sender, "frequency", frequencies[currentEvent.getPitch()]);
				sendFloat(sender, "volume", 1);
				Thread.sleep(currentEvent.getDuration());
				sendFloat(sender, "volume", 0);
				Thread.sleep(currentEvent.getWaitingTime());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//cleanup
			
			currentEvent = nextElement;
		}
		

	}
	
	public static void sendFloat(OSCPortOut sender, String componentName, float value) throws IOException{
		OSCMessage message = new OSCMessage("/" + componentName, new Object [] { value });
		sender.send(message);
		System.out.println("Sent: " + message.getAddress() + message.getArguments().toString());
	}
	
	public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    
	static SeedData readMidiFile(File midi) throws InvalidMidiDataException, IOException{
		SeedData result = new SeedData();
		
        Sequence sequence = MidiSystem.getSequence(midi);

        
        /*
        int trackNumber = 0;
        for (Track track :  sequence.getTracks()) {
            trackNumber++;
            System.out.println("Track " + trackNumber + ": size = " + track.size());
            System.out.println();
            for (int i=0; i < track.size(); i++) { 
                MidiEvent event = track.get(i);
                System.out.print("@" + event.getTick() + " ");
                MidiMessage message = event.getMessage();
                if (message instanceof ShortMessage) {
                    ShortMessage sm = (ShortMessage) message;
                    System.out.print("Channel: " + sm.getChannel() + " ");
                    if (sm.getCommand() == NOTE_ON) {
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        int velocity = sm.getData2();
                        System.out.println("Note on, " + octave + " key=" + key + " velocity: " + velocity);
                    } else if (sm.getCommand() == NOTE_OFF) {
                        int key = sm.getData1();
                        int octave = (key / 12)-1;
                        int note = key % 12;
                        int velocity = sm.getData2();
                        System.out.println("Note off, " + octave + " key=" + key + " velocity: " + velocity);
                    } else {
                        System.out.println("Command:" + sm.getCommand());
                    }
                } else {
                    System.out.println("Other message: " + message.getClass());
                }
            }

            System.out.println();
        }*/
        
        Track track = sequence.getTracks()[1];
        boolean lookingForNoteOn = true;
        
        
        int key = 0;
        long currentNoteOnTick = 0;
        long lastNoteOffTick = 0;
        MusicalEvent previousEvent = null;
        for (int i = 0; i < track.size(); i++){
        	MidiEvent event = track.get(i);
        	if (event.getMessage() instanceof ShortMessage){
        		ShortMessage message = (ShortMessage) event.getMessage();
        		if (lookingForNoteOn && message.getCommand() == NOTE_ON){
        			System.out.println("Found expected NOTE_ON");
        			key = message.getData1();
        			currentNoteOnTick = event.getTick();
        			lookingForNoteOn = false;
        			System.out.println("\tKey: " + key + " Tick: " + currentNoteOnTick);
        		} else if (!lookingForNoteOn && message.getCommand() == NOTE_OFF){
        			System.out.println("Found expected NOTE_OFF");
        			MusicalEvent me = new MusicalEvent(key, event.getTick() - currentNoteOnTick);
        			
        			if (previousEvent != null){
        				previousEvent.setWaitingTime(currentNoteOnTick - lastNoteOffTick);
        			}
        			result.add(me);
        			previousEvent = me;
        			lookingForNoteOn = true;
        			lastNoteOffTick = event.getTick();
        			System.out.println("\tDuration: " + me.getDuration());
        		}
        	}
        }
        
        
        
		return result;

    }
	
}
