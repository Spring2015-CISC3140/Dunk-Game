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
* 
* TODO: Do we want to use a state machine here to control menu/game things?
* 		at this moment, deciding no; we only have the two states, and want the model to 
* 		be able to remember things, so just will create a state class that tracks state.
* 		Probably essentially a re-write of Boolean that allows for menu/game instead of true/false.
* 			-> this did not work because enums are weird in java. Still, made it more type safe.
* 				-> Type safety is important for reasons. http://en.wikipedia.org/wiki/Type_safety
* 				-> For cereal, it's a cool thing. Check it out. 	
*
**/

public class Model extends Observable implements Runnable{

	//there are four parts of the model
	//the thrower
	//the target
	//the thrown thing
	//the dunkee
		
	Dunkee dunkee;
	Thrower thrower;
	Target target;
	Thrown thrown; // need a better name, thrown is too close to throw.
	
	Thread gameLoop;
	
	boolean go;
	
	State state;
	
	public Model(){
		//enums in java are weird man.
		//tell me about it bro..
		state = State.MENU;//default constructor
		go = true;
		gameLoop = new Thread(this);
		gameLoop.start();
	}
	
	/**
	 * 
	 * One might ask, why all this separation? Why not just have the listener here instead of in Controller?
	 * The answer is separation; if we end up needing to change how something works, we don't have to do a 
	 * whole rework of the nonsense, we can just change what needs changed. The controller doesn't care
	 * how it's inputs are dealt with, it just tells the model what to do. We could change the model to fart
	 * on left arrow and controller wouldn't care, it'd still call whatever we told it to on left arrow.
	 * It goes deeper, but I don't think we're going to need to support custom keybinds? 
	 * I know it doesn't look like it's actually doing anything, but trust me.
	 * 
	 */
	public void powerInc(){
		thrower.powerInc();
	}
	public void powerDec(){
		thrower.powerDec();
	}
	public void angleInc(){
		thrower.angleInc();
	}
	public void angleDec(){
		thrower.angleDec();
	}
	
	public void throwBall(){
		thrown = thrower.throwBall();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(go){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				updates++;
				delta--;
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("Ticks: " + updates + " FPS: " + frames);
				updates = 0;
				frames = 0;
			}
		}		
			
			//thrown logic happens
			
			//observer/able functions. We can pass an object to notifyObservers to use as a message.
			//good practice denotes we make a message class so that observers can check against it
			//and not go haywire. 
			setChanged();
		    notifyObservers(/*can include a message object*/);
		    clearChanged();
	}
}