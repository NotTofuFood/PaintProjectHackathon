package client;

import java.awt.Color;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingUtilities;

public class Client {
	static SocketChannel client;
	static Thread listenerThread;
	static Canvas canvas;
	
	public List<Circle> circles = new ArrayList<>();

	// begins the link from client to server
	public static boolean connect(Canvas canvas)
	{
		System.out.println("CONNECTING");
		Client.canvas= canvas;
		try {
			InetSocketAddress addr = new InetSocketAddress("127.0.0.1", 1145);
			client = SocketChannel.open(addr);
			System.out.println("CONNECTED");
		} catch(Exception e) {
			System.out.println("SERVER DOWN"); // display a message explaining that the server is down
			e.printStackTrace();
			return false;
		}
		if(listenerThread != null) {
			return true; // listener thread already running, no need to start another one
		}
		listenerThread = new Thread() {
			@Override
			public void run() { // this will run on a seperate thread and try to get input from the server
				while(true) {
					if(isConnected()) {
						List<String> inputs = getInput();
						if(inputs != null) {
							Iterator<String> i = inputs.iterator();
							while(i.hasNext()) {
								String input = i.next();
								System.out.println("RECIEVED: " + input);
								//do whatever with the input here
								String[] args = input.split(" ");
								if(args[0].equals("CIRCLE")) {
									int x;
									int y;
									int radius;
									Color color;
									try {
										x = Integer.parseInt(args[1]);
										y = Integer.parseInt(args[2]);
										radius = Integer.parseInt(args[3]);
										color = new Color(Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
										SwingUtilities.invokeLater(new Runnable() {
											@Override
											public void run() {
												canvas.drawCircle(x, y, radius, color);
											}
										});
									} catch(Exception e){
										e.printStackTrace();
									}
								} else if(args[0].equals("ERASE")) {
									int x;
									int y;
									int length;
									try {
										x = Integer.parseInt(args[1]);
										y = Integer.parseInt(args[2]);
										length = Integer.parseInt(args[3]);
										SwingUtilities.invokeLater(new Runnable() {
											@Override
											public void run() {
												canvas.drawErase(x, y, length);
											}
										});
									} catch(Exception e){
										e.printStackTrace();
									}
								}
							}
						}
					}else {
						return;
					}
						
				}
			}
		};
		listenerThread.start();
		return true;
	}
	
	public static void sendCircle(int x, int y, int radius, Color c) {
		send(String.format("CIRCLE %d %d %d %d %d %d", x, y, radius, c.getRed(), c.getGreen(), c.getBlue()));
	}
	
	public static void sendSize(int size) {
		send(String.format("CIRCLE %d", size));
	}
	
	public static void sendEraser(int x, int y, int length) {
		send(String.format("ERASE %d %d %d", x, y, length));
	}
	
	public static void getCanvas() {
		send("GETCANVAS");
	}
	
	private static String inputBuffer = "";
	//should ask the server for an input
	public static List<String> getInput() {
		ByteBuffer buf = ByteBuffer.allocate(4096); // 4096 is the max length that can be read in one packet, maybe change this later?
		String input;
		int bytesRead;
		try {
			bytesRead = client.read(buf);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		try {
			input = new String(buf.array(), "UTF-8").substring(0, bytesRead);
			if(input.equals("")) {
				//connection closed
				disconnect();
				return null;
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		inputBuffer += input;
		int newlineIndex = 1;
		List<String> outputs = new ArrayList<String>();
		while(newlineIndex != -1) {
			newlineIndex = inputBuffer.indexOf("\n");
			if(newlineIndex != -1) { // theres a new complete message
				String output = inputBuffer.substring(0, newlineIndex);
				inputBuffer = inputBuffer.substring(newlineIndex+1, inputBuffer.length());
				outputs.add(output);
			}
		}
		return outputs;
	}
	
	public static void send(String s) {
		System.out.println("SENDING: " + s);
		s = s+'\n'; // so that we know when the string ends
		byte[] message = new String(s).getBytes();
		// might be able to input string directly but idk lol
		ByteBuffer buffer = ByteBuffer.wrap(message);
		try {
			client.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			//disconnected
			try {
				client.close();
			} catch (IOException e1) {}
			disconnect();
		}
	}
	
	//returns whether or not we are currently connected
	public static boolean isConnected() {
		return client.isConnected();
	}

	
	public static void disconnect()
	{
		System.out.println("DISCONNECTED");
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// we need to stop the listener thread 
		try {
			listenerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		listenerThread = null;
	}
}