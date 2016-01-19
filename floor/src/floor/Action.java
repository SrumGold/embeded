package floor;

public enum Action {
	// Actions - floor module support
	FLOOR_PRESS_UP,
	FLOOR_PRESS_DOWN,	
	FLOOR_LED_UP,
	FLOOR_LED_DOWN,
	
	// Actions - Commands recieved from Elevator Car
	ELEV_FLOOR_PRESS,
	ELEV_GENERAL_BUTTON,
	ELEV_DOOR_OPENED,
	ELEV_DOOR_CLOSED,
	ELEV_FLOOR_ARIVED,
	ELEV_PASS_BY_FLOOR,
	ELEV_START_MOVE,
	
	// Actions - Commands sent to Elevator Car
	EMERGENCY_STOP, 
	CLOSE_DOOR, 
	OPEN_DOOR, 
	GOTO_FLOOR,
	SET_INDICATION_LED,    // TODO - Michael you need to add this
	
	// Actions queries for the Elevator Car
	QUERY_LAST_FLOOR,
	ANS_LAST_FLOOR,
	QUERY_CAN_STOP,
	ANS_CAN_STOP,
	
	UNDEFINED
}
