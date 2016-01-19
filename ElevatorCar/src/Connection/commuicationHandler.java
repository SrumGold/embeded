package Connection;

import floor.Action;
import floor.Message;

public interface commuicationHandler {
	public void sendToCPU(Message msg);
	public void sendToCPU(int floor, Action act, boolean status);
	public void setProtocol(Protocol p);
}
