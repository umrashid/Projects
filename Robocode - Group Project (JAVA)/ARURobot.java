
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import static robocode.util.Utils.normalRelativeAngle;





public class ARURobot extends AdvancedRobot
{	//Project for INFO 2313 : S10
	// Group : Umair Rashid, Ryan Mock and Annie Phan
	//this value tells whether the robot is moving forward or not. true if its moving forward and false if its not.
	boolean movingahead;
	public void run() {
	   // grabs the field height and width and stores it in a variable
	   double fieldheight = getBattleFieldHeight();
 	   double fieldwidth = getBattleFieldWidth();
	   //everything in the robot turns independently from one another
	   setAdjustGunForRobotTurn(true);
	   setAdjustRadarForGunTurn(true);
	   setAdjustRadarForRobotTurn(true);
		
		while(true) {
					
					// scans the whole field and move forward until it finds someone
					setTurnRadarRight(360);	
					ahead(75);
					//keeps track that the robot is moving forward
					movingahead = true;
					// get robot x and y coordinates		
					double xcord = getX();
					double ycord = getY();
					// check if we didnt exceed the boundary limit and if we did then turn away from hitting the wall
					if ((xcord > 0 && xcord < 100)||(xcord > (fieldwidth - 100) && xcord < fieldwidth) )
					{
                        back(40); //turn back
						movingahead = false;
						turnLeft(90); // turn left
					}
					else if ((ycord > 0 && ycord < 100)||(ycord > (fieldheight - 100) && ycord < fieldheight))
					{
                        back(40);
						movingahead = false;
						turnLeft(90);
					}
				 

		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
	    //location of the enemy robot, our own gun heading and radar heading and robot heading
		double bearingfromN = e.getBearing() + getHeading();// enemies absolute bearing
		// Not Using: double bearingFromGun = normalRelativeAngleDegrees(bearingfromN - getGunHeading());//enemy's bearing from the gun 
		double bearingFromRadar = normalRelativeAngleDegrees(bearingfromN - getRadarHeading());//enemy's bearing from the radar
		//calculating later velocity of the moving enemy robot
		double laterVelocity = e.getVelocity() * Math.sin(Math.toRadians(e.getHeading() - bearingfromN));
	
		//Turn the gun right based on later velocity so that it can shoot and not miss
		setTurnGunRightRadians(normalRelativeAngle(Math.toRadians(bearingfromN) - getGunHeadingRadians() + laterVelocity/22));
		//Turn the radar right so it can keep scanning and track of the enemy robot 
		setTurnRadarRight(bearingFromRadar);
		
		//get distance of the robot scanned
		double distance = e.getDistance();
		//head to the robot if the distance between the robot and enemy robot is greater than 250 pixel
		if(distance > 250){
			//Turn the Robot right so it can move towards enerby robot
			setTurnRight(e.getBearing());
			setAhead(50);
			// keep track of the moving direction of the robot
			movingahead = true;
			}
		else{
			// turns the robot 90 degree to the enemy robot and keep it moving forming an arc around the enemy robot
			// if enemy robot is moving straight then it runs parallel to it
			 setTurnRight(e.getBearing() + 90);
			  if(movingahead)
				{
			 		setAhead(100);
			 		movingahead = true;
			 	}
			 	else
			 	{
					setBack(100);
				 	movingahead = false;
				}
			}
		// checks if the gun has turned full to avoid premature shooting
		//checks if the enemy is close enough to fire
		// checks if the velocity of the enemy robot is less than so it has a better chance to hit it	
		if 	(getGunTurnRemaining() < 10 && e.getDistance() < 500 && e.getVelocity() < 4)
		{
				if (distance >= 450)
					setFire(0.5);
				else if (distance >= 400 && distance < 450)
					setFire(1.5);
				else if (distance >= 300 && distance < 400)
					setFire(2.3);
				else if (distance >= 250 && distance < 300)
					setFire(2.6);
				else if (distance >= 150 && distance < 200)
					setFire(2.9);
				else if (distance >= 100 && distance < 150)
					setFire(3.0);
				else
				  setFire(3);
		 }
		 //checks the same conditions as above and works when the velocity is higher than 4
		 else if(getGunTurnRemaining() < 10 && e.getDistance() < 500 && e.getVelocity() >= 4) 
		 { 
			setFire(0.5);
		 }
		
 		
	 	}


	
	public void onHitWall(HitWallEvent e) {
			//checks if the robot's direction when it hits the wall and reverses the direction of the robot
			if(movingahead)
				movingahead =false;
			else
				movingahead = true;
	}
	
}

