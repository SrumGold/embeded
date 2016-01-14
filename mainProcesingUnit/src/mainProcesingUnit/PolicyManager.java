package mainProcesingUnit;

import java.util.ArrayList;

import floor.Message;

public class PolicyManager implements Protocol {
	
	Policy _polocy;
	
	ArrayList<ElevControl> _elevs;

	public PolicyManager() {
		_elevs = new ArrayList<>();
	}
	
	public int addElev(ElevControl elev) {
		_elevs.add(elev);
		if(null != _polocy) {
			_polocy.addElev(_elevs.indexOf(elev), elev);
		}
		return _elevs.indexOf(elev);
	}
	
	public void set_polocy(Policy polocy) {
		this._polocy = polocy;
		_elevs.forEach(e -> _polocy.addElev(_elevs.indexOf(e), e));
	}
	
	@Override
	public void processMsg(Message msg) {
		switch (msg._act) {
		case FLOOR_PRESS_UP:
			_polocy.floorUpButton(msg._floor);
			break;
		case FLOOR_PRESS_DOWN:
			_polocy.floorDownButton(msg._floor);
			break;
			
		case ELEV_GENERAL_BUTTON:
			_polocy.generalButtonInCar(msg._elevId, msg._floor);
			break;
		case ELEV_FLOOR_PRESS:
			_polocy.floorButtonInCar(msg._elevId, msg._floor);
			break;

		case ELEV_DOOR_OPENED:
		case ELEV_DOOR_CLOSED:
		case ELEV_FLOOR_ARIVED:
		case ELEV_PASS_BY_FLOOR:
		case ELEV_START_MOVE:
			_polocy.elevatorStatusUpdate(msg._elevId, msg._act, msg._floor);
			
			break;
			
		default:
			break;
		}
	}

}
