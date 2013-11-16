import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;


public class OSCTester {

	
	public static void main(String[] args) throws IOException, InterruptedException {
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		printHelp();
		String prompt = "OSCTester> ";

		OSCPortOut sender = new OSCPortOut(InetAddress.getByName("localhost"),50050);
		do {
			System.out.print(prompt);
			line = console.readLine();
			if (line.startsWith("quit")){
				break;
			} else if (line.startsWith("net")){
				String [] address = line.split(" ")[1].split(":");
				String ip = address[0];
				int port = Integer.parseInt(address[1]);
				sender.close();
				sender = new OSCPortOut(InetAddress.getByName(ip),port);
			} else if (line.startsWith("help")){
				printHelp();
			} else if (line.startsWith("send")){
			String [] command = line.split(" ");
			try{
				float value = Float.parseFloat(command[2]);
				sendFloat(sender, command[1], value);
				System.out.println("Value "+ value +" sent to component " + command[1]);
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
				System.out.println("Command string format not recognized. Hint: <componentName> <value>");
			}}
		} while (true);
	}
	
	private static void printHelp(){
		System.out.println("Possible commands;");
		System.out.println("\tnet <ip>:<port>");
		System.out.println("\tsend <componentName> <value>");
		System.out.println("\thelp");
	}
	

	
	public static void sendFloat(OSCPortOut sender, String componentName, float value) throws IOException{
		sender.send(new OSCMessage("/" + componentName, new Object [] { "f", value }));
	}
}
