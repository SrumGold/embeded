package SensorsSimulation;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import mainProcesingUnit.Direction;
import ElevatorMoving.Moving;
import ElevatorStatus.Status;
import SensorsSimulation.SensorTypes;

public class SensorsAPI{

	//Fields for the Sensors simulation
	float _velocity; // the motor's velocity of the elevator - real number from range: [0,1]
	long _timeStamp; // time of last update
	SensorTypes _doorSensor; // the state of the elevator's door
	float _currentPosition; // the actual floor number the elevator is at
	Timer _interruptSchedTimer; // The timer that is responsible for interrupting the appropriate listeners
	
	
	// the listeners that can be interrupted by the sensors
	Moving _moving;
	
	public SensorsAPI(Moving moving) {
		_interruptSchedTimer = new Timer("interruptSimulationTimer");
		_velocity = 0;
		_currentPosition = 0;
		_doorSensor = SensorTypes.DOOR_CLOSED;
		_moving = moving;
	}
	
//                 _             
//                | |            
// _ __ ___   ___ | |_ ___  _ __ 
//| '_ ` _ \ / _ \| __/ _ \| '__|
//| | | | | | (_) | || (_) | |   
//|_| |_| |_|\___/ \__\___/|_|   
//                 
    
	private void updatePosition() {
		/* update new position */
		long now = Calendar.getInstance().getTimeInMillis();
		long deltaT = now - _timeStamp;
		_currentPosition = _currentPosition + ((_velocity * (float)deltaT) / (float)1000.0);
		_timeStamp = now;
	}
	
	public void changeVelocity(float newVelocity){
		
		/* update new position */
		updatePosition();
		_velocity = newVelocity;
		
		initiateInterruptFromOrientationSensors();
		
	}
	
	/**
	 * restart the timer
	 */
	public void initiateInterruptFromOrientationSensors(){
		double delay = 0;
		SensorTypes nextSensor;
		
		_interruptSchedTimer.cancel();
		_interruptSchedTimer = new Timer();
		
		if (0 == _velocity) {
			return;
		}
		
		if (0.1 >= (_currentPosition % 1.0)){
			if (0 < _velocity) {
				double deltaX = 0.1 - (_currentPosition % 1.0);
				delay = _velocity * deltaX * 1000.0;
				nextSensor = SensorTypes.ABOVE_FLOOR;
			} else {
				double deltaX = (_currentPosition % 1.0) - 0.0;
				delay = -1.0 * _velocity * deltaX * 1000.0;
				nextSensor = SensorTypes.ON_FLOOR;
			}
				
		} else if (0.9 > (_currentPosition % 1.0)) {
			if (0 < _velocity) {
				double deltaX = 0.9 - (_currentPosition % 1.0);
				delay = _velocity * deltaX * 1000.0;
				nextSensor = SensorTypes.BELLOW_FLOOR;
			} else {
				double deltaX = (_currentPosition % 1.0) - 0.1;
				delay = -1.0 * _velocity * deltaX * 1000.0;
				nextSensor = SensorTypes.ABOVE_FLOOR;
			}
		} else {
			if (0 < _velocity) {
				double deltaX = 1.0 - (_currentPosition % 1.0);
				delay = _velocity * deltaX * 1000.0;
				nextSensor = SensorTypes.ON_FLOOR;
			} else {
				double deltaX = (_currentPosition % 1.0) - 0.9;
				delay = -1.0 * _velocity * deltaX * 1000.0;
				nextSensor = SensorTypes.BELLOW_FLOOR;
			}			
		}
		
		System.out.println("debug: schedule interupt in " + delay + " mils");
		if (delay <= 1.0) {
			delay += 1;
		}
		
		_interruptSchedTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				updatePosition();
				System.out.println("debug: interupt sensor : " + nextSensor + " , loc = " + _currentPosition);
				initiateInterruptFromOrientationSensors();
				_moving.interrupt(nextSensor);
			}
		}, (long) delay);
		
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
