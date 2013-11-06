import java.io.IOException;

import com.illposed.osc.OSCPortOut;


public class Beeper extends Thread {
	
	int midinote, duration, pause;
	String synth;
	OSCPortOut sender;
	
	public Beeper(int midinote, int duration, int pause, String synth, OSCPortOut sender){
		this.midinote = midinote;
		this.duration = duration;
		this.pause = pause;
		this.synth = synth;
		this.sender = sender;
	}

	@Override
	public void run(){
		try {
		while(true){
			OSCTester.sendOn(sender, synth, midinote);
			Thread.sleep(duration);
			OSCTester.sendOff(sender, synth);
			Thread.sleep(pause);
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
