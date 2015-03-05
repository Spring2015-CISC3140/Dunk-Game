package cisc3140_Dunk_A_Prof;

import java.util.Observable;

/**
*
* Model class
* The exciting part, wooo!
* Should probably be a superclass with no actual objects,
* but used for subclassing. interface doesn't work because can't ensure they have things
* we need to have, like variables. There's also the extends Observable thing
*
**/

public class Model extends Observable {

	//there are four parts of the model
	//the thrower
	//the target
	//the thrown thing
	//the dunkee
	
	Dunkee dunkee;
	Thrower thrower;
	Target target;
	Thrown thrown; // need a better name, thrown is too close to throw.
	
	public Model(){
		;//default constructor
	}
	
}