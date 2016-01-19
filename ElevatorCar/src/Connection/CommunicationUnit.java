package Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import floor.Action;
import floor.Direction;
import floor.Message;
import userInterface.UiHandler;

public class CommunicationUnit implements UiHandler, commuicationHandler{
	public static final int PASSENGER_PORT = 12345;
	public static final int CPU_PORT = 12355;

	Protocol _protocol;

	Socket _passangerSocket; // socket to connect to passenger UI inside the elevator
	PrintWriter _passengerOut;
	BufferedReader _passengerIn;

	ServerSocket _carClientSocket;

	@SuppressWarnings("resource")
	public void startPassengerControl() {
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
	}
	
	public void startCarClient() {
		// TODO - open connection with the CPU server
		
	}

	@Override
	public void sendToCPU(Message msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendToCPU(int floor, Action act, boolean status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendIndication(Action act, boolean status) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setProtocol(Protocol p) {
		// TODO Auto-generated method stub
		
	}

	
}
