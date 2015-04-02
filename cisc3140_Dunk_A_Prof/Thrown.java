package cisc3140_Dunk_A_Prof;

/**
*
* The thing which is thrown.
* x/y coordinates/velocity atm?
* potential for properties at a later time
*
**/


/********************************************
 * 
 * NOTE: EVERYTHING THAT IS PHYSICS IN THIS CLASS IS BAD PRACTICE.
 * 		 THE ONLY REASON WE ARE DOING PHYSICS HERE IS BECAUSE THIS IS LITERALLY THE ONLY OBJECT WE CARE ABOUT.
 * 		 THE MOMENT WE HAVE TWO PHYSICS OBJECTS, WE WRITE A PHYSICS CLASS.
 *
 ***********************************************/

public class Thrown {
	
	float x, y, z;
	Velocity velocity;
	
	/**
	 * Terrible Physics Variables
	 */
	float grav;
	float wind;
	
	public Thrown(){
		//thou shalt not have uninitialized variables.
		grav = .01f;
		wind = 0f;
	}
	
	public Thrown(float initialX, float initialY, Velocity initialVelocity){
		z = 0;//whatever player depth is.
	}
	
	//do movement based things; probably increment x/y position by velocity + some sort of gravity/wind?
	/**
	 * TODO: SPECS: What affects movement? How is movement defined?
	 */
	public void move(){
		;
	}
		
	//apply changes to velocity.
	public void doPhysics(){
		//change the angle, need to insert some form of error checking here, likely in the form of a setter in the
		//velocity class.
		velocity.angle -= grav; 
	}
	
}