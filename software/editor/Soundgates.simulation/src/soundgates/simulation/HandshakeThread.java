package soundgates.simulation;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import soundgates.AtomicSoundComponent;

public class HandshakeThread extends Thread {
	private boolean stop = false;
	String componentList = "";
	public HandshakeThread(List<AtomicSoundComponent> ioComponents){
		for (AtomicSoundComponent component : ioComponents){
			componentList += component.getName() + " \"f\" [" + component.getFloatProperties().get("MinValue") + ":" + component.getFloatProperties().get("MaxValue") + "]";
			if (!(ioComponents.indexOf(component) == ioComponents.size() - 1)){
				componentList += "||";
			}
		}
		componentList += "\n";
	}
	
	public void stopMe(){
		stop = true;
	}
	
	@Override
	public void run() {
		super.run();
		ServerSocket server;
		try {
			server = new ServerSocket();
			server.setReuseAddress(true);
			server.bind(new InetSocketAddress(50050));
			while(!stop){
				try {
					Socket socket = server.accept();
					DataOutputStream out = new DataOutputStream(socket.getOutputStream());
					out.write(componentList.getBytes());
					System.out.println("Sent data to " + socket.getInetAddress() + ":" + socket.getPort());
					System.out.println(componentList);
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Server socket exception");
			e.printStackTrace();
		}
		
	}
	
}
