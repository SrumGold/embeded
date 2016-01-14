package mainProcesingUnit;

public interface ElevControl {
	
	public int getFloor(); //TODO needed?
	public boolean canStopAt(int floor);
	
	public boolean goToFloor(int floor);
	public boolean openDoor();
	public boolean closeDoor();
	public void emergencyStop();
	
}
