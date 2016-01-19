package userInterface;

import java.util.Scanner;

import mainProcesingUnit.Action;
import Connection.CommuicationHandler;
import MainLogic.ElevatorUI;

public class UiImpl implements ElevatorUI , Runnable {

	private CommuicationHandler _comm;

	public UiImpl(CommuicationHandler comm) {
		_comm = comm;
	}
	
	@Override
	public void setIndication(int floor, boolean status) {
		System.out.println("UI les of floor " + floor + " is set to " + status);
		
	}

	@Override
	public void run() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String line = scanner.nextLine();
			if (line.equals("exit")){
				return;
			}
			try {
			int floor = Integer.parseInt(line);
			_comm.sendToCPU(floor, Action.ELEV_FLOOR_PRESS, true);
			} catch (NumberFormatException e) {
				System.out.println("Error: please enter legal floor number");
				continue;
			}
		}
	}

}
