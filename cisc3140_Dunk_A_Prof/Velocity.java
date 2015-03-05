package cisc3140_Dunk_A_Prof;

/**
*
* Velocity storage container
* Direction and movement speed.
* Physics.
*
**/

public class Velocity {
	
	float angle;
	float speed;
	
	public Velocity(){
		;//default, always gets called
	}
	
	public Velocity(float angle, float speed){
		this.angle = angle;
		this.speed = speed;
	}
}