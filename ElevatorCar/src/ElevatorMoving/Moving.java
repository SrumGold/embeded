package ElevatorMoving;

import mainProcesingUnit.Direction;
import ElevatorStatus.Status;
import MainLogic.Interrupted;
import SensorsSimulation.SensorTypes;
import SensorsSimulation.SensorsAPI;

public class Moving implements Interrupted{

	
		
	private int _lastFloor;
	private int _destFloor;
	private Direction _dir;
	
	private SensorsAPI _motor;
	private Interrupted _elevManager;

	public Moving() {
		_motor = null;
		_elevManager = null;
		_lastFloor = 0;
		_destFloor = 0;
		_dir = Direction.IDLE;
	}
	
	public void setElevManager(Interrupted elevManager) {
		_elevManager = elevManager;
	}
	
	public void setMotor(SensorsAPI motor) {
		_motor = motor;
	}
	
	@Override
	public void interrupt(SensorTypes sensor) {
		float dirFactor;
		if (_dir.equals(Direction.UP)) {
			dirFactor = +1;
		} else {
			dirFactor = -1;
		}
		
		if (sensor.equals(SensorTypes.ON_FLOOR)) {
			_lastFloor = (_lastFloor + (int)dirFactor);
			Status.updateFloor(_lastFloor);
			if (Math.abs(_lastFloor - _destFloor) == 1){
				_motor.changeVelocity(dirFactor * 0.5f);
			}
			if (_lastFloor == _destFloor) {
				_motor.changeVelocity(0);
				_dir = Direction.IDLE;
				Status.updateDir(_dir);
				// notify the main
				_elevManager.interrupt(SensorTypes.ON_FLOOR);
			}
			
		} else if (sensor.equals(SensorTypes.BELLOW_FLOOR)) {
			if (_dir.equals(Direction.UP)){
				_motor.changeVelocity(0.1f);
			}
			
		} else if (sensor.equals(SensorTypes.ABOVE_FLOOR)) {
			if (_dir.equals(Direction.DOWN)){
				_motor.changeVelocity(-0.f);
			}
		}
		
	}

	/**
	 * check if it's legal and start moving to floor 'floor'
	 * if already moving, change the destination if it's legal 
	 * @param floor
	 */
	public void goToFloor(int floor) {
		// TODO check if it's legal
		if (!Status.isDoorClose()) {
			System.out.println("debug: can't move with door open");
			return;
		}
		
		if (_destFloor == floor){
			return;
		}
		
		_destFloor = floor;
		
		if (_lastFloor < floor) {
			_dir = Direction.UP;
			_motor.changeVelocity(1);
		} else {
			_dir = Direction.DOWN;
			_motor.changeVelocity(-1);
		}
		Status.updateDir(_dir);
		
	}

}
