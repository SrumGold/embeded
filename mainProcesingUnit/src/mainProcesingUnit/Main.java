package mainProcesingUnit;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);    // Engineers UI

		// initiate the main server
		CommunicationUnit comm = new CommunicationUnit();
		PolicyManager policyManager = new PolicyManager();
		
		Policy policy = new DefaultPolicy();
		policy.setUiHandler(comm);
		
		policyManager.set_policy(policy);
		
		comm.setProtocol(policyManager);

		comm.startFloorControl();
		comm.startCarServer();
		
		System.out.println("debug: main(): CPU is ready!");
		
		while (true) {
			String line = scanner.nextLine();
			if (line.equals("exit")){
				// TODO - stop everything
				return;
			}
			
			// TODO - add here engineering operations
		}
		
	}

}
