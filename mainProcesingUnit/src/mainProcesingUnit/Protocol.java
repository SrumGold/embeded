package mainProcesingUnit;

public interface Protocol {
	public void processMsg(Message msg);

	public int addElev(ElevControl elevControl);
	
}
