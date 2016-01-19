package mainProcesingUnit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DefaultPolicy implements Policy {

	class Status {
		public int lastFloor;
		public int destenationFloor;
		public int doorLastStatus;
		public Collection<Integer> requests;
		
		public Status(int lastFloor, int destenationFloor, int doorLastStatus, Collection<Integer> requests) {
			this.lastFloor = lastFloor;
			this.destenationFloor = destenationFloor;
			this.doorLastStatus = doorLastStatus;
			this.requests = requests;
		}
	}
	
	private Map<Integer, ElevControl> _elevsComm;
	private Map<Integer, Status> _elevsStatus;
	private UiHandler _uiHandler;

	
	
	public DefaultPolicy() {
		_elevsComm = new HashMap<>();
		_elevsStatus = new HashMap<>();
	}

	@Override
	public void floorUpButton(int floor) {
		changeFloorUpIndication(floor, true);
		for (Status s : _elevsStatus.values()) {
			if (s.destenationFloor == floor || 
					s.requests.contains(floor)){
				return;
			}
		}

		Entry<Integer, Status> bestElev = null;
		
		for ( Entry<Integer, Status> elev : _elevsStatus.entrySet()) {
			if (elev.getValue().destenationFloor == floor){
				return;
			}
			if (null == bestElev){
				bestElev = elev;
				continue;
			}
			if (bestElev.getValue().requests.size() > elev.getValue().requests.size()) {
				bestElev = elev;
			}
		}
	
		if (null != bestElev) {
			addStop(bestElev.getKey(), floor);
		}

	}
	
	@Override
	public void floorDownButton(int floor) {
		changeFloorDownIndication(floor, true);
		// TODO - very dummy policy
		floorUpButton(floor);
	}

	private void addStop(Integer elevId, int floor) {
		Status s = _elevsStatus.get(elevId);
		s.requests.add(floor);
		updateNext(elevId);
	}

	private void updateNext(Integer elevId) {
		Status s = _elevsStatus.get(elevId);
		
		if(s.destenationFloor != s.lastFloor) {
			return;
		} else {
			if (!s.requests.isEmpty()){
				int next = s.lastFloor;
				for (int f : s.requests) {
					if (Math.abs(f-s.lastFloor) < next-s.lastFloor){
						next = f;
					}
				}
				
				s.destenationFloor = next;
				s.requests.remove(next);
				_elevsComm.get(elevId).goToFloor(next);
				// TODO - start "goto" timeout timer
			}
		}
	}

	private void changeFloorUpIndication(int floor, boolean b) {
		_uiHandler.sendIndication(floor, Action.FLOOR_LED_UP, b);		
	}
	
	private void changeFloorDownIndication(int floor, boolean b) {
		_uiHandler.sendIndication(floor, Action.FLOOR_LED_DOWN, b);
	}

	private void changeElevIndication(int elevId, int floor, boolean b) {
		_elevsComm.get(elevId).sendIndication(floor, Action.SET_INDICATION_LED, b);
	}

	@Override
	public void floorButtonInCar(int elevId, int floor) {
		System.out.println("debug: button " + floor + " was pressed in car " +  elevId);
		Status s = _elevsStatus.get(elevId);

		changeElevIndication(elevId, floor, true);
		
		if(s.destenationFloor == floor ||
				s.requests.contains(floor)) {
			return;
		}
		
		s.requests.add(floor);
		updateNext(elevId);
	}

	@Override
	public void generalButtonInCar(int elevId, int buttonId) {
		// TODO Auto-generated method stub
		System.out.println("debug: general button in car: [" + elevId + " : " + buttonId + " ]");
	}

	@Override
	public void elevatorStatusUpdate(int elevId, ElevStatus status) {
		// TODO - probably need to be removed
		throw new RuntimeException(" not shuld be called");
	}

	@Override
	public void elevatorStatusUpdate(int elevId, Action act, int intVal) {
		Status s = _elevsStatus.get(elevId);
		
		switch (act) {
		case ELEV_FLOOR_ARIVED:
			// TODO - stop "goto" timeout timer
			changeElevIndication(elevId, intVal, false);
			s.lastFloor = intVal;
			updateNext(elevId);
			break;
			
		case ELEV_DOOR_OPENED:
		case ELEV_DOOR_CLOSED:
			//TODO - cancel timeout timer
			System.out.println("debug: got status update: " + act.toString());
			break;

		default:
			// TODO - another stuff to handle ..
			System.out.println("debug: got status update: " + act.toString() + " , intVal=" + intVal);
			break;
		}
	}

	@Override
	public void errors() {
		System.out.println("debug: policy got error!");
		// TODO Auto-generated method stub

	}

	@Override
	public void addElev(int elevID, ElevControl elev) {
		_elevsComm.put(elevID, elev);
		_elevsStatus.put(elevID, new Status(0, 0, 0, new ArrayList<Integer>()));
	}

	@Override
	public void setUiHandler(UiHandler uiHandler) {
		_uiHandler = uiHandler;
	}

}
