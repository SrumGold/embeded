package mainProcesingUnit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import floor.Action;
import floor.Message;

public class CommunicationUnit implements UiHandler , commuicationHandler{
	public static final int FLOOR_PORT = 10000;

	Protocol _protocol;

	Socket _floorSocket;
	PrintWriter _floorOut;
	BufferedReader _floorIn;

	ServerSocket _carServerSocket;

	public void setProtocol(Protocol p) {
		_protocol = p;
	}

	public void startFloorControl() {
		try {
			_floorSocket = new ServerSocket(FLOOR_PORT).accept();
		} catch (IOException e1) {
			System.out.println("can't start server");
			//e1.printStackTrace();
			return;
		}

		try {
			_floorIn = new BufferedReader(new InputStreamReader(_floorSocket.getInputStream()));
		} catch (IOException e) {
			System.out.println("can't open input stream");
			return;
		}

		try {
			_floorOut = new PrintWriter(_floorSocket.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("can't open output stream");
			return;
		}

		System.out.println("floor UI connected!");
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					System.out.println("debbug: start run");
					String line;
					Message m = null;
					try {
						line = _floorIn.readLine();
						m = Message.decode(line);
					} catch (IOException e) {
						System.out.println("fail to read line from net");
						return;
					}
					System.out.println("debbug: got message: " + line);
					if (null != m) {
						_protocol.processMsg(m);
					}

				}
			}
		}).start();
	}
	
	public void startCarServer() {
		_carServerSocket = 
	}

	public void sendToFloor(Message msg) {
		System.out.println("sending to floor: " + msg.encode());
		_floorOut.println(msg.encode());
	}

	@Override
	public void sendToCar(Message msg) {
		System.out.println("sending to car: " + msg.encode());
		_carOut.println(msg.encode());
	}

	@Override
	public void sendToCar(int floor, Action act, boolean status) {
		Message msg = new Message(floor, act, status, 0);
		sendToCar(msg);
	}

	@Override
	public void sendIndication(int floor, Action act, boolean status) {
		Message msg = new Message(floor, act, status, 0);
		sendToFloor(msg);
	}

}
