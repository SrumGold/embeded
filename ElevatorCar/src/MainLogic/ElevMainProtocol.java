package MainLogic;

import mainProcesingUnit.Action;
import mainProcesingUnit.Direction;
import mainProcesingUnit.Message;
import Connection.Protocol;
import Connection.CommuicationHandler;
import ElevatorMoving.Moving;
import ElevatorStatus.Status;
import SensorsSimulation.SensorTypes;

public class ElevMainProtocol implements Protocol , Interrupted{

	private ElevatorUI _elevatorUI;
	private CommuicationHandler _comm;
	private int _nextFloor;
	private Moving _moving; 
	
	public ElevMainProtocol(ElevatorUI elevatorUI, CommuicationHandler comm, Moving moving) {
		_elevatorUI = elevatorUI;
		_comm = comm;
		_moving = moving;
		_nextFloor = 0;
	}
	
	@Override
	public void processMsg(Message msg) {
		Message m;
		// TODO - complete the relevant protocol for the client
		
		switch (msg._act) {
		case GOTO_FLOOR:
			goToFloor(msg._floor);
			break;
			
		case QUERY_CAN_STOP:
			boolean ans = canStop(msg._floor);
			m = new Message(-1, Action.ANS_CAN_STOP, ans, -1);
			_comm.sendToCPU(m);
			break;
			
		case QUERY_LAST_FLOOR:
			int lastFloor = Status.getLastFloor();
			m = new Message(lastFloor, Action.ANS_LAST_FLOOR, false, -1);
			_comm.sendToCPU(m);
			break;
			
		case SET_INDICATION_LED:
			_elevatorUI.setIndication(msg._floor , msg._status);
			break;
			
		case CLOSE_DOOR:
			// TODO - tell door module to open door
			break;
		case OPEN_DOOR:
			// TODO - tell door module to close door
			break;
		default:
			System.out.println("debbug: cant handle messege");
			break;
		}

	}

	private boolean canStop(int floor) {
		boolean ans = false;
		int lastFloor = Status.getLastFloor();
		Direction direction = Status.getDirection();
		switch (direction) {
		case IDLE:
			ans = true;
			break;
		case UP:
			ans = (floor - lastFloor) > 1;
			break;
		case DOWN:
			ans = (lastFloor - floor) > 1;
			break;
		default:
			break;
		}
		
		return ans;
	}

	/**
	 * update next destenation to new floor and go to that floor
	 * @param floor
	 */
	private void goToFloor(int floor) {
		if (!canStop(floor) || _nextFloor == floor) {
			System.out.println("debug: can't stop at floor: " + floor);
			return;
		}
		_nextFloor = floor;
		
		if (!Status.isDoorClose()) {
			System.out.println("debug: closing door befor goto floor");
			// TODO - tell door module to close door
			// TODO - it's better to start listener for door closing that will call 'goToFloor(_nextFloor)'
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO - start door timer
					while (true) {
						// TODO - if door timeout exit thread and display message
						if (Status.isDoorClose()) {
							goToFloor(_nextFloor);
							return;							
						}
						
						// do not use too much cpu
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();			
		}
		
		_moving.goToFloor(floor);
	}

	@Override
	public void interrupt(SensorTypes sensor) {
		if (sensor.equals(SensorTypes.ON_FLOOR)){
			System.out.println("debug: reach to floor " + _nextFloor);
			// TODO - open doors
			_comm.sendToCPU(_nextFloor, Action.ELEV_FLOOR_ARIVED, true);
			
		}
	}
}
