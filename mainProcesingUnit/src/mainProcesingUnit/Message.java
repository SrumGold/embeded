package mainProcesingUnit;

import java.util.StringTokenizer;


public class Message {
	public int _elevId;
	public int _floor;
	public Action _act;
	public boolean _status;
	
	
	public Message() {
		this(0,Action.UNDEFINED, false, 0);
	}

	public Message(int floor, Action act, boolean status, int elevId) {
		_floor = floor;
		_act = act;
		_status = status;
		_elevId = elevId;
	}
	
	public String encode() {
	String s = _elevId + ";" + _floor + ";" + _act.toString() + ";" + _status;
	return s;
	}
	
	public static Message decode(String s) {
		Message m = new Message();
		StringTokenizer st = new StringTokenizer(s, ";");
		
		m._elevId = Integer.parseInt(st.nextToken());
		m._floor = Integer.parseInt(st.nextToken());		

		
		String a = st.nextToken();
		
		m._act = Action.valueOf(a);
		
		/*
		switch (a) {
		case "FLOOR_PRESS_UP":
			m._act = Action.FLOOR_PRESS_UP;
			break;
		case "FLOOR_PRESS_DOWN":
			m._act = Action.FLOOR_PRESS_DOWN;	
			break;
		case "FLOOR_LED_UP":
			m._act = Action.FLOOR_LED_UP;
			break;
		case "FLOOR_LED_DOWN":
			m._act = Action.FLOOR_LED_DOWN;		
			break;
		}
		*/
		
		if (st.nextToken().equals("true")) {
			m._status = true;
		} else {
			m._status = false;
		}
		
		return m;
	}
}
