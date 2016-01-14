package mainProcesingUnit;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ElevComminication implements ElevControl {
	Socket _carSocket;
	PrintWriter _carOut;
	BufferedReader _carIn;
	
	public ElevComminication(Socket carSocket, PrintWriter carOut, BufferedReader carIn) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getFloor() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canStopAt(int floor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean goToFloor(int floor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean openDoor() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeDoor() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void emergencyStop() {
		// TODO Auto-generated method stub

	}

}
