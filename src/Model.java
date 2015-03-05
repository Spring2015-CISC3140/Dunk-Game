import java.util.Observable;

/**
*
* Model class
* The exciting part, wooo!
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
	
}