package cisc3140_Dunk_A_Prof;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

/**
* 
* Controller code
* Listens for things, then sends them over to the model.
* This stuff can also be accomplished via buttons, but atm am unsure of how to implement this in MVC atm
* 
**/

/**
 * TODO: SPECS: What keys and or mouseclicks do what? 
 */

public class Controller implements MouseInputListener, KeyListener, Runnable {
	
	Model model;
	Thread thread;
	
	public Controller(){
		;//need a default, but won't use it
	}
	
	public Controller(Model model){
		this.model = model;
		thread = new Thread(this);
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch(keyCode){ //implement code for UP key
                case KeyEvent.VK_KP_UP:
                    //implement code for UP key. Should send parameters to the model or can change velocity
                    //increment angle
                    break;
                case KeyEvent.VK_KP_DOWN:
                    //implementation for DOWN key. decrement angle 
                    break;
                case KeyEvent.VK_KP_RIGHT:
                    //increment direction angle
                    break;
                case KeyEvent.VK_KP_LEFT:
                    //decrement directional angle
                    break;
            }
		/*switch (e.getKeyChar()) {
		case 'w':
			System.out.println("w pressed");
			model.go = false;
		default: break; 
		}*/
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		while(model.go){
			
		}
		
	}
}