package cisc3140_Dunk_A_Prof;

/**
*
* That which gets things specifically thrown at it.
* Holds information about THINGS.
*
**/

/**
 * TODO: SPECS: What is going on with this? What behaviors you do want displayed?
 */

public class Target {
	private int positionX;// represent position of the target
        private int positionY;
        //private int speed; //(work on this later if needed)
	public Target(){
            //initial position of the target left top corner
            positionX = 0;
            positionY = 0;
            //speed = 0
	}
        
        public Target (int posX, int posY){
            //can pass coordinates to target
            positionX = posX;
            positionY = posY;
        }
        
        public void changePosition(int posX, int posY){
            //allows to change position of the target on the screen
            positionX = posX;
            positionY = posY;
        }
        
        public void changeX(int x){
            positionX = x;
        }
        
        public void changeY(int y){
            positionY = y;
        }
        
        public int getX(){
            return positionX;
        }
        public int getY(){
            return positionY;
        }
              
}