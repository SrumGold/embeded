package mainProcesingUnit;

import floor.Message;

public interface Protocol {
	public void processMsg(Message msg);

	public int addElev(ElevControl elevControl);
}
