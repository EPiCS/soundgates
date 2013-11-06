import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;


public class OSCTester {

	
	public static void main(String[] args) throws IOException, InterruptedException {
		//startPD();
		makeMusic();
	}
	
	private static void makeMusic() throws IOException, InterruptedException {
		sendOff(sender, "synth1");
		sendOff(sender, "synth2");
		sendOff(sender, "synth3");
		
		new Beeper(70, 2000, 4000, "synth1", sender).start();
		Thread.sleep(1000);
		new Beeper(76, 2000, 4000, "synth2", sender).start();
		Thread.sleep(1000);
		new Beeper(82, 2000, 4000, "synth3", sender).start();
	}

	static {
		try {
			sender = new OSCPortOut(InetAddress.getByName("localhost"));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
	
	private static OSCPortOut sender;
	
	public static void sendOff(OSCPortOut sender, String synth) throws IOException{
		sender.send(new OSCMessage("/synths/" + synth + "/off"));
	}
	
	public static void sendOn(OSCPortOut sender, String synth, Integer frequency) throws IOException{
		sender.send(new OSCMessage("/synths/" + synth + "/on", new Object [] {frequency}));
	}
	
	private static void startPD() throws IOException {
		String [] pd = new String [] {
//				"bash",
				"/usr/lib/pd-extended/bin/pdextended",
//				"-nogui",
				"-alsa",
				"-alsaadd", "pulse",
				"-open", "/home/olaf/Studium/Projektgruppe/OSCreceiver.pd",
//				"-send", "\"pd dsp 1\""
		};
		

		
		ProcessBuilder builder = new ProcessBuilder(pd);
		final Process process = builder.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() { process.destroy(); }
		});
		
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		
		while ((line = br.readLine()) != null) {
		  System.out.println(line);
		}
	}
}
