package floor;

public interface Floor {
	void push(Direction dir);
	void setLed(Direction dir, boolean status);
	boolean getLed(Direction dir);
}
