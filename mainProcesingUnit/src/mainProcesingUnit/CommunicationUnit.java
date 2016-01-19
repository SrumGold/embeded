package mainProcesingUnit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CommunicationUnit implements UiHandler , CommuicationHandler{
	public static final int FLOOR_PORT = 10000;
	public static final int ELEV_PORT = 10001;
	public static final String CPU_ADDR = "localhost";	

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
				System.out.println("debbug: start run");
				while (true) {
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
		try {
			_carServerSocket = new ServerSocket(ELEV_PORT);
		} catch (IOException e) {
			System.out.println("can't start server");
			//e.printStackTrace();
			return;
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					
					Socket socket;
					ElevComminication elevControl;
					try {
						socket = _carServerSocket.accept();
					} catch (IOException e) {
						System.out.println("an elevator fail to connect");
						// e.printStackTrace();
						continue;
					}
					
					try {
						elevControl = new ElevComminication(socket, _protocol);
						new Thread(elevControl).start();
					} catch (Exception e) {
						System.out.println("fail to build the elevator handle");
						// e.printStackTrace();
						continue;
					}
					
					_protocol.addElev(elevControl);
					
				}
			}
		}).start();
		
	}

	public void sendToFloor(Message msg) {
		System.out.println("debug: sending to floor: " + msg.encode());
		_floorOut.println(msg.encode());
	}

	@Override
	public void sendToCar(Message msg) {
		throw new RuntimeException("unimplemented");
		/* TODO - remove method 
		System.out.println("sending to car: " + msg.encode());
		_carOut.println(msg.encode());
		*/
		}

	@Override
	public void sendToCar(int floor, Action act, boolean status) {
		throw new RuntimeException("unimplemented");
		/* TODO - remove method
		Message msg = new Message(floor, act, status, 0);
		sendToCar(msg);
		*/
	}

	@Override
	public void sendIndication(int floor, Action act, boolean status) {
		Message msg = new Message(floor, act, status, -1);
		sendToFloor(msg);
	}

}
