package mainProcesingUnit;

public interface ElevControl extends UiHandler{
	
	public int getFloor(); //TODO needed?
	public boolean canStopAt(int floor);
	
	public boolean goToFloor(int floor);
	public boolean openDoor();
	public boolean closeDoor();
	public void emergencyStop();
	
	public int getElevId();
	public void setElevId(int _elevId);
	
}
