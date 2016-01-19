package floor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import mainProcesingUnit.Action;
import mainProcesingUnit.Direction;
import mainProcesingUnit.Message;

public class CommunicationUnit {
	
	FloorUI _floorUI;
	Socket _socket;
	PrintWriter _out;
	BufferedReader _in;
	
	public void setUI(FloorUI floorUI) {
		_floorUI = floorUI;
		
	}

	public void start() {
		if (null == _floorUI) {
			throw new NullPointerException("FloorUI not initialized");
		}
	
		try {
			_socket = new Socket(mainProcesingUnit.CommunicationUnit.CPU_ADDR, mainProcesingUnit.CommunicationUnit.FLOOR_PORT);
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
						if (Action.FLOOR_LED_UP == m._act) {
							_floorUI.setLed(Direction.UP, m._status);
						} else if (Action.FLOOR_LED_DOWN == m._act) {
							_floorUI.setLed(Direction.DOWN, m._status);							
						}
					}
					
				}
			}
		}).start();
		
	}
	
	public void sendPress(int floor, Direction dir) {
		Message m;
		if (Direction.UP == dir) {
			m = new Message(floor, Action.FLOOR_PRESS_UP, true, -1);
		} else {
			m = new Message(floor, Action.FLOOR_PRESS_DOWN, true, -1);
		}
		
		System.out.println("Sending press " + m.encode());
		_out.println(m.encode());
	}

}
