package mainProcesingUnit;

import floor.Action;
import floor.Message;

public class Main {

	public static void main(String[] args) {

		CommunicationUnit comm = new CommunicationUnit();
		comm.setProtocol(new Protocol() {
			
			@Override
			public void processMsg(Message msg) {
				switch (msg._act) {
				case FLOOR_PRESS_UP:
					comm.sendIndication(msg._floor, Action.FLOOR_LED_UP, true);
					break;
				case FLOOR_PRESS_DOWN:
					comm.sendIndication(msg._floor, Action.FLOOR_LED_DOWN, true);
					break;
				default:
					System.out.println("debbug: cant handle messege");
					break;
				}
			}
		});

		comm.startFloorControl();
		
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
