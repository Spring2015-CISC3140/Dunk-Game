package cisc3140_Dunk_A_Prof;

/**
*
* Thrower class
*
**/

public class Thrower {

	float power;
	float angle;
	Velocity velocity;
	
	//could implement via Singleton, but then what if we wanted two throwers?
	//also bad for MVC pattern as people want to misuse statics. Doubly bad for threading.
	//probably want to start the thrower at a static number.
	//remember Java will always call the default constructor, even if not defined.
	//one should define it for every class, to avoid weird errors of vaguery. 
	public Thrower(){
		power = 0.0f;
		angle = 0.0f;
		
	}
	
	
	//it's super hard to break out of Finite State Machine thinking, rabble. Too many voids.
	//create methods for static or variable increase/decreases.
	public void powerInc(float inc){
		power += inc;
	}
	public void powerInc(){
		power += 1.0f;
	}
	public void powerDec(float dec){
		power -= dec;
	}
	public void powerDec(){
		power -= 1.0f;
	}
	
	public void angleInc(float inc){
		angle += inc;
	}
	public void angleInc(){
		angle += 1.0f;
	}
	public void angleDec(float dec){
		angle -= dec;
	}
	public void angleDec(){
		angle -= 1.0f;
	}
	
	//........ et cetera for both.
	//setters/getters, whatnot
	
	//some method for the creation of a thrown object potentially; 
	//do we want Model.java to handle that instead?
	//Model.java will need to have a reference to whatever is created.
	// Jeff - I think the thrown should be created here, as we'll have to reference this object anyway
	//also makes thing scalable if we want?
	
	public Thrown throwBall(){ // so much state machine, blaaaaahhh. I'm not sure how to fix it.
		//uses the magic of everything is a reference in Java to deal with handing this 
		//back to whatever called it. Makes it so that we don't have to deal with any actual responses
		//in the thrower class. It's very convenient!
		return new Thrown(power, angle, velocity ); // throw that ball, using current power/angle. 	
	}
		
}