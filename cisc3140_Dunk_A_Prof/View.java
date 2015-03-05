package cisc3140_Dunk_A_Prof;

import java.util.Observable;
import java.util.Observer;

/***
*
* View portion.
* Generally up to Graphics, but boilerplate ahoy.
*
****/

public class View implements Observer {
	
	//Constructors
	public View(){
		;//don't really care about this. Need to have the model to do anything productive
		// but need a default constructor. I like to make it explicit
	}
	
	public View(Model model){
		model.addObserver(this); //ensure that this can get updates.
		//Graphics adds stuff as required.
	}
	
	//Graphics gets model updates from here.
	public void update(Observable observ, Object message) {
		// TODO Auto-generated method stub
		
	}
	
}
