package mainProcesingUnit;

import floor.Action;

public interface Policy {
	
	public void floorUpButton(int floor);
	public void floorDownButton(int floor);
	public void floorButtonInCar(int elevId, int _floor);
	public void generalButtonInCar(int elevId, int buttonId);
	public void elevatorStatusUpdate(int elevId, ElevStatus status); // TODO - needed?
	public void elevatorStatusUpdate(int elevId, Action act, int intVal);
	
	public void errors(); // TODO
	public void addElev(int elevID, ElevControl elev);
	public void setUiHandler(UiHandler uiHandler);

}
