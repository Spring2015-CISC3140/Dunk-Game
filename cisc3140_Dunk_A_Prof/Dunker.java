package cisc3140_Dunk_A_Prof;

/**
*
* Initial creation of loader/runner for Dunk-a-Prof for CISC 3140
* 
* Read Readiness.java before doing any real work.
*
**/

public class Dunker {

	//controller will be some form of input
	//view is Graphics' job
	//model is the fun exciting part.

	Model model;
	View view;
	Controller controller;
	
	public Dunker(){
		/*
		this is how the constructors should work.
		The observer model should be used for the model, where it notifies whatever is watching it
		Java 7 has an interface for this model that is good, I assume Java 8 does as well. 
		*/
		model = new Model();
		view = new View(model);
		controller = new Controller(model);
	}
	
	public static void main(String[] args) {
		new Dunker();
	}
}