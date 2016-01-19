package mainProcesingUnit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ElevComminication implements ElevControl, Runnable {
	private static final long WAIT_TIME_OUT = 100;

	int _elevId;
	
	Socket _carSocket;
	PrintWriter _carOut;
	BufferedReader _carIn;
	Protocol _protocol;

	int _floorAns;
	boolean _boolAns;
	
	
	public ElevComminication(Socket carSocket, Protocol protocol) throws Exception {
		_protocol = protocol;
		_elevId = -1;
		
		if (!setSocket(carSocket)) {
			throw new Exception("fail to initialize bufers");
		}

	}

	private boolean setSocket(Socket socket) {
		_carSocket = socket;
		
		try {
			_carIn = new BufferedReader(new InputStreamReader(_carSocket.getInputStream()));
		} catch (IOException e) {
			System.out.println("can't open input stream");
			return false;
		}

		try {
			_carOut = new PrintWriter(_carSocket.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println("can't open output stream");
			return false;
		}
		
		return true;
	}
	
	public void sendToCar(Message msg) {
		if (-1 == _elevId) {
			throw new RuntimeException("elevator id not initialized");
		}
		
		System.out.println("sending to car: " + msg.encode());
		_carOut.println(msg.encode());
	}

	
	
	public void sendToCar(int floor, Action act, boolean status) {
		Message msg = new Message(floor, act, status, 0);
		sendToCar(msg);
	}
	
	@Override
	public int getFloor() {
		sendToCar(-1, Action.QUERY_LAST_FLOOR, false);
		try {
			wait(WAIT_TIME_OUT);
		} catch (InterruptedException e) {
			return -1;
		}
		return _floorAns;
	}

	@Override
	public boolean canStopAt(int floor) {
		sendToCar(-1, Action.QUERY_CAN_STOP, false);
		try {
			wait(WAIT_TIME_OUT);
		} catch (InterruptedException e) {
			return false;
		}
		return _boolAns;
	}

	@Override
	public boolean goToFloor(int floor) {
		this.sendToCar(floor, Action.GOTO_FLOOR, true);
		return true; //TODO - check connection 
	}

	@Override
	public boolean openDoor() {
		this.sendToCar(-1, Action.OPEN_DOOR, true);
		return true; //TODO - check connection 
	}

	@Override
	public boolean closeDoor() {
		this.sendToCar(-1, Action.CLOSE_DOOR, true);
		return true; //TODO - check connection 
	}

	@Override
	public void emergencyStop() {
		this.sendToCar(-1, Action.EMERGENCY_STOP, true);

	}

	public int getElevId() {
		return _elevId;
	}
	
	public void setElevId(int _elevId) {
		this._elevId = _elevId;
	}


	@Override
	public void run() {
		while (true) {
			System.out.println("debbug: elev " + _elevId + "start run");
			String line;
			Message m = null;
			try {
				line = _carIn.readLine();
				m = Message.decode(line);
				m._elevId = _elevId;
			} catch (IOException e) {
				System.out.println("fail to read line from net");
				return;
			}
			System.out.println("debbug: got car message: " + line);
			if (null != m) {
				if (Action.ANS_CAN_STOP == m._act){
					_boolAns = m._status;
					notify();
				} else if (Action.ANS_LAST_FLOOR == m._act) {
					_floorAns = m._floor;
					notify();
				} else {
				_protocol.processMsg(m);
				}
			}

		}


	}

	@Override
	public void sendIndication(int floor, Action act, boolean status) {
		this.sendToCar(floor, Action.SET_INDICATION_LED, status);
	}

}
