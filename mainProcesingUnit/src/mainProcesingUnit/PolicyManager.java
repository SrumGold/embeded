package mainProcesingUnit;

import java.util.ArrayList;

public class PolicyManager implements Protocol {
	
	Policy _policy;
	
	ArrayList<ElevControl> _elevs;

	public PolicyManager() {
		_elevs = new ArrayList<>();
	}
	
	public int addElev(ElevControl elev) {
		_elevs.add(elev);
		if(null != _policy) {
			elev.setElevId(_elevs.indexOf(elev));
			_policy.addElev(_elevs.indexOf(elev), elev);
			return _elevs.indexOf(elev);
		}
		return -1;
	}
	
	public void set_policy(Policy policy) {
		this._policy = policy;
		_elevs.forEach(e -> _policy.addElev(_elevs.indexOf(e), e));
	}
	
	@Override
	public void processMsg(Message msg) {
		switch (msg._act) {
		case FLOOR_PRESS_UP:
			_policy.floorUpButton(msg._floor);
			break;
		case FLOOR_PRESS_DOWN:
			_policy.floorDownButton(msg._floor);
			break;
			
		case ELEV_GENERAL_BUTTON:
			_policy.generalButtonInCar(msg._elevId, msg._floor);
			break;
		case ELEV_FLOOR_PRESS:
			_policy.floorButtonInCar(msg._elevId, msg._floor);
			break;

		case ELEV_DOOR_OPENED:
		case ELEV_DOOR_CLOSED:
		case ELEV_FLOOR_ARIVED:
		case ELEV_PASS_BY_FLOOR:
		case ELEV_START_MOVE:
			_policy.elevatorStatusUpdate(msg._elevId, msg._act, msg._floor);
			
			break;
			
		default:
			break;
		}
	}

}
