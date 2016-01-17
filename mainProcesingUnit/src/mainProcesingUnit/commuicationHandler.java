package mainProcesingUnit;

import floor.Action;
import floor.Message;

public interface commuicationHandler {
	public void sendToCar(Message msg);
	public void sendToCar(int floor, Action act, boolean status);
	public void setProtocol(Protocol p);
}
