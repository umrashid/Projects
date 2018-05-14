#include "sumovore.h"
#include "motor_control.h"
#include <xc.h>

void follow_simple_curves(void);
void spin_left(void);
void turn_left(void);
void straight_fwd(void);
void slow_turn_right(void);
void slow_turn_left(void);
void turn_right(void);
void spin_right(void);

void turn_90_right(void);
void turn_90_left(void);
void whether_gap(void);
void all_sensor(void);
void turn_30_left(void);
void turn_30_right(void);



void motor_control(void)
{
   // very simple motor control
     switch(SeeLine.B)
     {
        case 0b00100u:
					  straight_fwd(); // for going straight forward
					  break;
        case 0b00010u:
        case 0b00001u:
        case 0b01000u:
        case 0b10000u:
                       follow_simple_curves();
                       break;
        case 0b00000u:
                        whether_gap();    // for gap and dead end
                       break;
        case 0b00011u:
        case 0b00111u:
        case 0b01111u: 
                        turn_90_right();  // for 90 degree right turn and right crossover
                       break;
		case 0b00110u:
						slow_turn_right(); // tight curve or deviation
						break;
		case 0b01100u:
						slow_turn_left();  // tight curve or deviation
						break;
        case 0b11000u:
        case 0b11100u:
        case 0b11110u: 
                        turn_90_left();   // for 90 degree left turn and right crossover
                       break;
        case 0b11111u: 
                        all_sensor();    // for straight crossing, merging and final end
                        break;
        case 0b10100u:
        case 0b11010u:
                        turn_30_left();  // for small left angle turn, nearby trail, crossing at angle
                        break;
		case 0b10110u:
						slow_turn_right(); // when encounter nearby trail, robot still follow the original trail
						break;
        case 0b00101u:
        case 0b01011u:
			            turn_30_right(); // for small left angle turn, nearby trail, crossing at angle
                        break;
		case 0b01101u:
						slow_turn_left();  // when encountering nearby trail, robot still follow the original trail
						break;
        default:       break;
      } 

}

void follow_simple_curves(void)
{
     if (SeeLine.b.Left) turn_left();// 
     else if (SeeLine.b.CntLeft) turn_left();
     else if (SeeLine.b.CntRight) turn_right();
    else if (SeeLine.b.Right) spin_right();
}

void spin_left(void) // for spining left
{		
		set_motor_speed(left, rev_fast, 0); 
 		set_motor_speed(right, fast, 0);
}

void turn_left(void) // for turning left
{	
		set_motor_speed(left, rev_slow, 0); 
  		set_motor_speed(right, fast, 0);
}
void straight_fwd(void)
{
  set_motor_speed(left, fast, -40); //fixing the deviation of the robot
  set_motor_speed(right, fast, 0); 
}

void spin_right(void)
{
		set_motor_speed(left, fast, 0); 
  		set_motor_speed(right, rev_fast, 0);
}
void turn_right(void)
{
		set_motor_speed(left, fast, 0); 
  		set_motor_speed(right, rev_medium, 0);
}

void slow_turn_right(void) // for the curves with low radius. it keeps the robot in the middle
{
	//Start timing
	//Within 0.16s, robot keeps turning right
	//If only the central sensor detects the track, return
    OpenTimer0(TIMER_INT_OFF & T0_SOURCE_INT & T0_16BIT & T0_PS_1_128);
    WriteTimer0(0); 
	do
	{
		set_motor_speed(left, fast, 0); 
  		set_motor_speed(right,rev_slow , 0);
		check_sensors();
		set_leds();
		if(SeeLine.B == 0b11100u) // just a precation if the robot is turning  and it senses a 90 degree turn then it breaks out of the function
			{
			  return;
		 	}
		if(SeeLine.B == 0b00111u)// just a precation if the robot is turning  and it senses a 90 degree turn then it breaks out of the function
			{
			  return;
			}
		if(SeeLine.B == 0b11111u)  // sometimes when the robot encounters the right crossover, then return
			{
			  return;
			}
		if (ReadTimer0() > 10000)   // for avoiding turning too much
		                            //1/32000000*4*128*100000=0.16s
				{
					return;
				}
	}while(SeeLine.B != 0b00100u); // turn right until only central sensor detect the track
	
}

void slow_turn_left(void)
{
	//Start timing
	//Within 0.16s, robot keeps turning left
	//If only the central sensor detects the track, return
	OpenTimer0(TIMER_INT_OFF & T0_SOURCE_INT & T0_16BIT & T0_PS_1_128);
    WriteTimer0(0); 
	do
	{
		set_motor_speed(left, rev_slow, 0); 
  		set_motor_speed(right, fast, 0);
		check_sensors();
		set_leds();
		if(SeeLine.B == 0b11100u)// just a precation if the robot is turning  and it senses a 90 degree turn then it breaks out of the function
			{
			  return;
		 	}
		if(SeeLine.B == 0b00111u)// just a precation if the robot is turning  and it senses a 90 degree turn then it breaks out of the function
			{
			  return;
			}
		if(SeeLine.B == 0b11111u)  // sometimes when the robot encounters the right crossover, then return
			{
			  return;
			}
		
		if (ReadTimer0() > 10000)// for avoiding turning too much
		                         //1/32000000*4*128*100000=0.16s
					{
						return;
					}
	}while(SeeLine.B != 0b00100u); // turn left until only the central sensor detects the track

}
   
void turn_90_right(void)
{
	    //when robot encounters 90 degree turn, robot goes straight until all sensors detect white, then robot turns right 
	    //In the meantime, start timing.Robot goes straight not more than 0.32s. Once overflows, return
  		OpenTimer0(TIMER_INT_OFF & T0_SOURCE_INT & T0_16BIT & T0_PS_1_64); // for crossing
      	WriteTimer0(0); // for crossing
		 do
	     {
		    straight_fwd();
		    check_sensors();
		    set_leds();
			if(SeeLine.B == 0b11000u) // for merging
			{
			  return;
		 	}
			if(SeeLine.B == 0b11111u) // sometimes when robot comes to right crossover, this function may be triggered.
			                          // In this case,return
			{
			  return;
		 	}
			if (ReadTimer0() > 40000)// for crossing
			                         //1/32000000*4*64*40000=0.32s
				{
					return;
				}
			if(SeeLine.B == 0b10100u)  // for perfecting 30 degree turns
			{
			  return;
			}
			
		 }while(SeeLine.B != 0b00000u); // until it detects whtie goes straight
			
         	do
			{
			
			set_motor_speed(left, medium, 0); 
	  		set_motor_speed(right, rev_fast, 0);
			check_sensors();
			set_leds();
			if(SeeLine.B == 0b01100u || SeeLine.B == 0b11000u || SeeLine.B == 0b10000u || SeeLine.B == 0b11100u ) // precaution for over turn
			{
			  return;
		 	}
			}while(SeeLine.B != 0b00100u ); // After all sensors detect white, 
		                                 	// turn right until only the central sensor detects the track
			
		
        
}	
void turn_90_left(void)
{
	    //when robot encounters 90 degree turn, robot goes straight until all sensors detect white and turn left 
	    //In the meantime, start timing.Robot only goes straight not more than 0.32s. Once overflows, return
        OpenTimer0(TIMER_INT_OFF & T0_SOURCE_INT & T0_16BIT & T0_PS_1_64); // for crossing
      	WriteTimer0(0); // for crossing
           do
	       {
		    straight_fwd();
		    check_sensors();
		    set_leds();
			if(SeeLine.B == 0b11111u) // sometimes when robot comes to right crossover, return
			{
			  return;
		 	}
			if(SeeLine.B == 0b00011u) // for merging
			{
			  return;
		 	}
			if (ReadTimer0() > 40000)// for crossing
			                          //1/32000000*4*64*40000=0.32s
				{
					return;
				}
		   }while(SeeLine.B != 0b00000u);
			do
			{
			set_motor_speed(left, rev_fast, 0); 
	  		set_motor_speed(right, medium, 0);
			check_sensors();
			set_leds();
			if(SeeLine.B == 0b00001u ) // for precaution regarding too much turn
				{
			  	return;
		 		}
			if(SeeLine.B == 0b00011u ) // for precaution regarding too much turn
				{
			 	 return;
		 		}
			}while(SeeLine.B != 0b00100u );// After all sensors detect white,
		                                	// turn left until only the central sensor detects the track
			
}	
void whether_gap(void)
{ 
        //when the robot encounters the gap, the robot goes straight and starts timing
        //the robot only goes straight not more than 0.48s
        //if more than 0.48s, the robot turns around and goes back to the track
        //if any sensor detect any track within 0.48s,return
	    OpenTimer0(TIMER_INT_OFF & T0_SOURCE_INT & T0_16BIT & T0_PS_1_128);
    	WriteTimer0(0);   
            do
            {
	             straight_fwd();
	             check_sensors();
	             set_leds();
	             
	         if(ReadTimer0() >30000)// (1/32000000*4)*128*30000=0.48s
	         {
		         //dead end, turn around
		        OpenTimer0(TIMER_INT_OFF & T0_SOURCE_INT & T0_16BIT & T0_PS_1_128);
    			WriteTimer0(0);
    			while(ReadTimer0()<40000u)   //(1/32000000*128*40000=0.64s
    			{
	    			set_motor_speed(left, fast, 0); 
  	        		set_motor_speed(right, rev_fast, 0);	
  	    		}   
  	    		WriteTimer0(0); // resetting the timer for loop		
  	    		}		
				 
            }while(SeeLine.B == 0b00000u );  // going straight until it senses something other than white	  	    
}
void all_sensor(void)
{
	     //When this function is triggered, start timing and robot keeps going straight
	     //Within 0.12s, if only leftmost sensor detects trail, the robot has already crossed the merging and then turn left
	     //if only rightmost sensor detects the track, the robot also crossed the merging and then turn right
	    OpenTimer0(TIMER_INT_OFF & T0_SOURCE_INT & T0_16BIT & T0_PS_1_64);
      	WriteTimer0(0);
     	while( ReadTimer0()< 15000u)     //1/32000000*4*64*15000=0.12s
     	{
	    straight_fwd();
	    check_sensors();
	    if (SeeLine.B == 0b10000u)//for the merging
	    {
		    slow_turn_left();
		} 
		if (SeeLine.B == 0b00001u)//for the merging
	    {
		    slow_turn_right();
		} 
	    }
        check_sensors();   
        set_leds();
        if(SeeLine.B==0b11111u)   //After 0.12s, if all sensors still detect black,then the robot stops
		{
			 set_motor_speed(left, rev_medium, 0); // for reducing inertia
             set_motor_speed(right, rev_medium, 0); 
        	do{
             set_motor_speed(left, stop, 0); 
             set_motor_speed(right, stop, 0); 
             check_sensors();   
             set_leds();
           }while(SeeLine.B == 0b11111u); // stays still until this condition changes  
		}
		
}

void turn_30_left(void)
{
	//When this function is triggered, start timing
	//Within 0.64s, the robot keeps going straight until all sensors detect white, then the robot turns left
	//After 0.64s, the robot has already passed a crossing at angle
    OpenTimer0(TIMER_INT_OFF & T0_SOURCE_INT & T0_16BIT & T0_PS_1_128);// for crossing
    WriteTimer0(0);// for crossing 
	do{
	straight_fwd();
    check_sensors();
	set_leds();
	
	if(SeeLine.B == 0b11111u) // sometimes when robot comes to right crossover, return
		{
		 return;
		 }
	if(SeeLine.B == 0b10110u) // When two tracks are too close and the robot is deviating the main track,return
	{
		return;
	}
		if (ReadTimer0() > 40000)// for crossing
		                         // 1/32000000*4*128*40000=0.64s
	{
			return;
	}

	}while(SeeLine.B != 0b00000u);
	do
	{
		set_motor_speed(left, rev_fast, 0); 
  		set_motor_speed(right, fast, 0);
		check_sensors();
		set_leds();
		if(SeeLine.B == 0b00001u )  // precaution in case it turns too much and doesnt detect the middle sensor
			{
			  return;
		 	}
	}while(SeeLine.B != 0b01000u); // After all sensors detect white,
	                               //turn left until the central left sensor detects a track
			
       
         
}	  
void turn_30_right(void)
{
	//When this function is triggered, start timing
	//Within 0.64s, the robot keeps going straight until all sensors detect white, then the robot turns right
	//After 0.64s, the robot has already passed a crossing at angle
    OpenTimer0(TIMER_INT_OFF & T0_SOURCE_INT & T0_16BIT & T0_PS_1_128);// for crossing
    WriteTimer0(0);// for crossing
	do{
	straight_fwd();
    check_sensors();
	set_leds();
	if(SeeLine.B == 0b11111u) // Sometimes when robot comes to right crossover,return
			{
			  return;
		 	}
    if(SeeLine.B == 0b01101u) // When two tracks are too close and the robot is deviating the main track,return
		{
			return;
		}
	if (ReadTimer0() > 40000)// for crossing
	                         // 1/32000000*4*128*40000=0.64s
	{
			return;
	}

	}while(SeeLine.B != 0b00000u);
	do
	{
		set_motor_speed(left, fast, 0); 
  		set_motor_speed(right, rev_fast, 0);
		check_sensors();
		set_leds();
		if(SeeLine.B == 0b10000u ) // precaution in case it turns too much and doesnt detect the middle sensor
			{
			  return;
		 	}
	}while(SeeLine.B != 0b00010u);  // After all sensors detect white,
	                                // the robot turns right until the central right sensor detects a track
}	 