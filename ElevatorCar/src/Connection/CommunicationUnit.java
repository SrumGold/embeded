package Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import MainLogic.ElevatorUI;
import floor.Action;
import floor.Direction;
import floor.Message;

public class CommunicationUnit {
	public static final int ELEVCAR_PORT = 12345;
	ElevatorUI _elevatorUI;
	Socket _socket;
	PrintWriter _out;
	BufferedReader _in;
	
	public void setUI(ElevatorUI elevatorUI) {
		_elevatorUI = elevatorUI;
		
	}

	public void start() {
		if (null == _elevatorUI) {
			throw new NullPointerException("elevatorUI not initialized");
		}
	
		try {
			_socket = new Socket("127.0.0.1", ELEVCAR_PORT);
		} catch (IOException e) {
			System.out.println("can't connect server");
			return;
		}
		
		try {
			_in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("can't open input stream");
			return;
		}
		
		try {
			_out = new PrintWriter(_socket.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("can't open output stream");
			return;
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					String line;
					Message m = null;
					try {
						line = _in.readLine();
						m = Message.decode(line);
					} catch (IOException e) {
						System.out.println("fail to read line from net");
					}
					
					if (null != m) {
						//TODO - make all cases for requests from this unit according to actions
						System.out.println("ECHO - recieved: " + m);
					}
					
				}
			}
		}).start();
		
	}
	
	public void sendPressToFloor(int floor) {
		Message m;
		m = new Message(floor, Action.ELEV_FLOOR_PRESS, true, -1);
		
		System.out.println("Sending press button in elev. to floor:\n " + m.encode());
		_out.println(m.encode());
	}

}
