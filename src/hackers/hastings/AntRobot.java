package hackers.hastings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Timer;

public class AntRobot {
	DifferentialPilot pilot;
	LightSensor light = new LightSensor(SensorPort.S1);
	int rotateAngle = 360;
	int rotateDirection = -1;
	int overCorrectAngle = 2;
	List<Integer> lastHeadings = new LinkedList<Integer>();

	public void go() {
		Button.waitForAnyPress();
		
		pilot.setTravelSpeed(5f);
		pilot.setRotateSpeed(60f);
		pilot.forward();
		
		while (pilot.isMoving()) {
			if (!isOnLine()) {
				pilot.stop();
				if (findLine()) {
					pilot.forward();
				} else {
					LCD.drawString("Well fuck", 0, 3);
				}
			}
		}
		LCD.drawString("Moved: " + pilot.getMovement().getDistanceTraveled(), 0, 1);
		Button.waitForAnyPress();
	}
	
	private boolean findLine() {
		for (int i = 1; i <= rotateAngle; i++) {
			int heading = (i % 2 == 0) ? rotateDirection : -rotateDirection;
			pilot.rotate(heading * i);
			if (isOnLine()) {
				pilot.rotate(heading * overCorrectAngle);
				lastHeadings.add(heading);
				
				int newHeading = 0;
				int counter = 0;
				for (int idx = lastHeadings.size() - 1; idx > 0 ; i--) {
					newHeading += lastHeadings.get(idx);
					counter++;
					if (counter >= 5) {
						break;
					}
				}
				newHeading = newHeading >= 0 ? 1 : -1;
						
				rotateDirection = newHeading;
				return true;
			}
		}
		return false;
	}
	
	private boolean isOnLine() {
		int lightReading = light.getNormalizedLightValue();
		
		LCD.drawString("Light: " + lightReading, 0, 0);
		
		return lightReading > 300 && lightReading < 390;
	}

	public static void main(String[] args) {
		AntRobot ant = new AntRobot();
		ant.pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.C, true);
		ant.go();
	}
}
