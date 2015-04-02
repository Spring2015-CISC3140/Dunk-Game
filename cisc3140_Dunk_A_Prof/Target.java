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
    boolean targetHit;
    
    //private int speed; //(work on this later if needed)
	public Target(){
            //initial position of the target left top corner
            positionX = 0;
            positionY = 0;
            targetHit = false;
            //speed = 0
	}
        
        public Target (int posX, int posY){
            //can pass coordinates to target
            positionX = posX;
            positionY = posY;
            targetHit = false;
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
        public void changeHit(boolean state){
            targetHit = state;
        }
        public void hitSuccess(){
            targetHit = true;
        }
        
        public boolean isHit(){
            return targetHit;
        }
        
        public void moveTargetHorizontaly(){
            //1st simple version of the target move 
            //function will cause target's X coordinates to change
            //makes target move from left to right and then from right to left
            //increments X coordinate to 800 and then decrement to 0
            
            //this is just idea not sure if it is going to work in the View or how to incorporate it in graphics
            boolean moveRight;
            moveRight = positionX != 799;
            
            while(true){
                //target every time move until it hit
                if(targetHit)
                    break;
                if(moveRight){
                    positionX++;
                    if(positionX==799)
                        moveRight = false;
                }
                if(!moveRight){
                    positionX--;
                    if(positionX==1)
                        moveRight = true;
                }
            }
        }          
}