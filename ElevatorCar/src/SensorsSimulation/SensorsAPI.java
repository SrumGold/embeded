package SensorsSimulation;

import java.util.Timer;

import mainProcesingUnit.Direction;
import ElevatorMoving.Moving;
import ElevatorStatus.Status;
import SensorsSimulation.SensorTypes;

public class SensorsAPI{

	//Fields for the Sensors simulation
	float _velocity; // the motor's velocity of the elevator - real number from range: [0,1]
	Direction _dir; // the direction on which the elevator is moving
	SensorTypes _doorSensor; // the state of the elevator's door
	int _currentFloor; // the actual floor number the elevator is at
	Timer _interruptSchedTimer; // The timer that is responsible for interrupting the appropriate listeners
	
	// the listeners that can be interrupted by the sensors
	Moving _moving;
	Status _status;
	
	public SensorsAPI(Moving moving, Status status) {
		_interruptSchedTimer = new Timer("interruptSimulationTimer");
		_velocity = 0;
		_dir = Direction.IDLE;
		_currentFloor = 0;
		_doorSensor = SensorTypes.DOOR_CLOSED;
		_moving = moving;
		_status = status;
	}
	
//                 _             
//                | |            
// _ __ ___   ___ | |_ ___  _ __ 
//| '_ ` _ \ / _ \| __/ _ \| '__|
//| | | | | | (_) | || (_) | |   
//|_| |_| |_|\___/ \__\___/|_|   
//                 
                  
	public void changeVelocity(float newVelocity){
		_velocity = newVelocity;
	}
	
	public void initiateInterruptFromOrientationSensors(){
		
		switch (_dir){
		case UP:
			//TODO - update the global variables and interrupt the listeners accordingly
			break;
		case DOWN:
			//TODO - update the global variables and interrupt the listeners accordingly
			break;
		default:
			break;
		}
	}
	
//    _                      
//   | |                     
// __| | ___   ___  _ __ ___ 
/// _` |/ _ \ / _ \| '__/ __|
//| (_| | (_) | (_) | |  \__ \
//\__,_|\___/ \___/|_|  |___/
//                           
                            
	
	public void openDoor(){
		
	}
	
	public void closeDoor(){
		
	}
	
	public void interruptDoor(){
		// TODO - for the sake of simulation. Figure out a smart way to somehow find a reason to invoke this method...
	}
	
	public void initiateInterruptFromDoorSensors(SensorTypes doorSensor){
		// TODO - schedule the interrupt after door movement
	}	
	
//              _       _     _   
//             (_)     | |   | |  
//__      _____ _  __ _| |__ | |_ 
//\ \ /\ / / _ \ |/ _` | '_ \| __|
// \ V  V /  __/ | (_| | | | | |_ 
//  \_/\_/ \___|_|\__, |_| |_|\__|
//                 __/ |          
//                |___/           
//
	
	// Weight Sensor
	//TODO - support somehow the simulation for the weight sensor in the project
		// idea: when elevator car stops for passenger load, then each passenger is simulated as input of his weight.
		// 		so, a weight accumulator needs to be supported. Transfered through the message frmo the CPU of the system,
	
	
}
