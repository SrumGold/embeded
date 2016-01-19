package Connection;

import mainProcesingUnit.Action;
import mainProcesingUnit.Message;

public interface CommuicationHandler {
	public void sendToCPU(Message msg);
	public void sendToCPU(int floor, Action act, boolean status);
	public void setProtocol(Protocol p);
}
