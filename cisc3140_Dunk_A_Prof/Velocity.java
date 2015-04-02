package cisc3140_Dunk_A_Prof;

/**
*
* Velocity storage container
* Direction and movement speed.
* Physics.
*
**/

public class Velocity {
	//TODO: Make these not public and build accessors.
	private float angle;
	private float speed;
    private float direction;
	
	public Velocity(){
		angle = 45f;
        speed = 1f; //assuming speed can't be 0 because nothing will happen
        direction = 0f; //will point straigt. vlaues 0-90 will direct to the left
        //values from 90-180 will point right
                
	}
	
	public Velocity(float angle, float speed, float direction){
		this.angle = angle;
		this.speed = speed;
        this.direction = direction;
	}
        
        //accesors and mutators
        public void setAngle(float angle){
            //angel can't be more than 90.
            if(angle>=0 && angle<=90)
                this.angle = angle;
            //add else clause. What should do throw exeption or print error?
        }
        public float getAngle(){
            return angle;
        }
        public void setSpeed(float speed){
            this.speed = speed;
        }
        public float getSpeed(){
            return speed;
        }
        public void setDirection(float direction){
            if(direction>=0 && direction<=180)
                this.direction = direction;
            //add else clause. Should throw exeption or print error?
        }
        public float getDirection(){
            return direction;
        }
}

