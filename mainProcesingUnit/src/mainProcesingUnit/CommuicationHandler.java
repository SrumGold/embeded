package mainProcesingUnit;

public interface CommuicationHandler {
	public void sendToCar(Message msg);
	public void sendToCar(int floor, Action act, boolean status);
	public void setProtocol(Protocol p);
}
