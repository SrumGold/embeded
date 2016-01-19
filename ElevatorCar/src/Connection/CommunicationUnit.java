package Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import mainProcesingUnit.Action;
import mainProcesingUnit.Message;


public class CommunicationUnit implements CommuicationHandler{
	public static final int PASSENGER_PORT = 12345;

	Protocol _protocol;

	Socket _passangerSocket; // socket to connect to passenger UI inside the elevator
	PrintWriter _passengerOut;
	BufferedReader _passengerIn;

	ServerSocket _carClientSocket;

	private Socket _socket;  // socket to the CPU unit
	private PrintWriter _out;
	private BufferedReader _in;

	public void startPassengerControl() {
		// TODO - remove
		throw new RuntimeException("shuld be removed");
		/*
		try {
			_passangerSocket = new ServerSocket(PASSENGER_PORT).accept();
		} catch (IOException e1) {
			System.out.println("can't start server");
			//e1.printStackTrace();
			return;
		}

		try {
			_passengerIn = new BufferedReader(new InputStreamReader(_passangerSocket.getInputStream()));
		} catch (IOException e) {
			System.out.println("can't open input stream");
			return;
		}

		try {
			_passengerOut = new PrintWriter(_passangerSocket.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("can't open output stream");
			return;
		}

		System.out.println("passenger UI connected!");
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					System.out.println("debbug: start run");
					String line;
					Message m = null;
					try {
						line = _passengerIn.readLine();
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
		*/
	}
	
	public void startCarClient() {
		if (null == _protocol) {
			throw new NullPointerException("protocol is null");
		}
		
		try {
			_socket = new Socket(mainProcesingUnit.CommunicationUnit.CPU_ADDR, mainProcesingUnit.CommunicationUnit.ELEV_PORT);
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
				System.out.println("debug: commonocation is up!");
				while (true) {
					String line;
					Message m = null;
					try {
						line = _in.readLine();
						m = Message.decode(line);
					} catch (IOException e) {
						System.out.println("fail to read line from net");
						continue;
					}
					
					if (null != m) {
						_protocol.processMsg(m);
					}
					
				}
			}
		}).start();
	}

	@Override
	public void sendToCPU(Message msg) {
		System.out.println("debug: sending to CPU: " + msg.encode());
		_out.println(msg.encode());
	}

	@Override
	public void sendToCPU(int floor, Action act, boolean status) {
		Message msg = new Message(floor, act, status, -1);
		sendToCPU(msg);
	}

	public void setProtocol(Protocol p) {
		_protocol = p;
	}

	
}
