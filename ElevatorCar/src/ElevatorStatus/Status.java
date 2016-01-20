package ElevatorStatus;

import mainProcesingUnit.Direction;

public class Status {
	
	private static int _lastFloor = 0;
	private static Direction _dir = Direction.IDLE;
	
	private Status() {	}  // make it static
	
	/**
	 * @return - the last floor that we stopped in or passed by 
	 */
	public static int getLastFloor() {
		return _lastFloor;
	}

	/**
	 * @return - return the direction of the elevator 
	 */
	public static Direction getDirection() {
		return _dir;
	}

	/**
	 * @return true if the door is closed (real time query)
	 */
	public static boolean isDoorClose() {
		// TODO Auto-generated method stub
		//throw new RuntimeException("unimplemented");
		return true;
	}
	
	public static void updateFloor(int floor){
		_lastFloor = floor;
	}
	
	public static void updateDir(Direction dir){
		_dir = dir;
	}

}
