package floor;

import java.util.Scanner;
import java.util.StringTokenizer;

import mainProcesingUnit.Direction;


public class Main {
	
	static CommunicationUnit comm = new CommunicationUnit();
	
	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		// TODO - start comunication
		comm.setUI(new FloorUI() {
			
			@Override
			public void setLed(Direction dir, boolean status) {
				if (status) {
					System.out.println("change " + dir.toString() + " led status to on" );
				} else {
					System.out.println("change " + dir.toString() + " led status to off" );					
				}
				
			}
		});
		
		comm.start();
		
		while (true) {
			String line = scanner.nextLine();
			if (line.equals("exit")){
				return;
			}
			
			StringTokenizer st = new StringTokenizer(line, ":");
			
			int floor = Integer.parseInt(st.nextToken());
			Direction dir;
			if (st.nextToken().equals("up")) {
				dir = Direction.UP;
			} else {
				dir = Direction.DOWN;
			}
			
			comm.sendPress(floor, dir);
			
		}
	}
}
