package MainLogic;

import userInterface.UiImpl;
import Connection.CommunicationUnit;
import ElevatorMoving.Moving;
import SensorsSimulation.SensorsAPI;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Elevator Car is being created...");
		
		CommunicationUnit comm = new CommunicationUnit();
		UiImpl elevatorUI = new UiImpl(comm);
		Moving moving = new Moving();
		SensorsAPI sensorsAPI = new SensorsAPI(moving);
		
		ElevMainProtocol protocol = new ElevMainProtocol(elevatorUI, comm, moving); 
		
		comm.setProtocol(protocol);
		
		moving.setElevManager(protocol);
		moving.setMotor(sensorsAPI);
		
		
		comm.startCarClient();
		

	    Thread uiThread = new Thread(elevatorUI);
	    uiThread.start();
	    uiThread.join();
	    
	}
}
