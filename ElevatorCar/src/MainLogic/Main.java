package MainLogic;

import Connection.CommunicationUnit;
import Connection.Protocol;
import ElevatorMoving.Moving;
import ElevatorStatus.Status;
import floor.Action;
import floor.Message;

public class Main {
	public static void main(String[] args) {
		System.out.println("Elevator Car is being created...");
		
		CommunicationUnit comm = new CommunicationUnit();
		comm.setProtocol(new Protocol() {
			
			@Override
			public void processMsg(Message msg) {
				// TODO - complete the relevant protocol for the client
				switch (msg._act) {
				/*case FLOOR_PRESS_UP:
					comm.sendIndication(msg._floor, Action.FLOOR_LED_UP, true);
					break;
				case FLOOR_PRESS_DOWN:
					comm.sendIndication(msg._floor, Action.FLOOR_LED_DOWN, true);
					break;
				default:
					System.out.println("debbug: cant handle messege");
					break;*/
				}
			}
		});
		
		
	}
}
