package MainLogic;

import userInterface.UiImpl;
import Connection.CommunicationUnit;
import ElevatorMoving.Moving;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Elevator Car is being created...");
		
		CommunicationUnit comm = new CommunicationUnit();
		UiImpl elevatorUI = new UiImpl(comm);
		Moving moving = new Moving();
		
		comm.setProtocol(new ElevMainProtocol(elevatorUI, comm, moving));
		
		comm.startCarClient();
		

	    Thread uiThread = new Thread(elevatorUI);
	    uiThread.start();
	    uiThread.join();
	    
	}
}
