/*this is the pane for putting together the main gameplay
it contains two canvas:
the first will contain two lines forming an angle that you can make smaller and larger using 'w' and's' keys
the second canvas contains an image of a ball and a target

NOTE: if the postion of the ball isn't satisfactory, change START_BALL_X, and START_BALL_Y, and everything else moves to reflect that change
*/
package DunkAProf;

import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.image.*;
import javafx.concurrent.*;
import javafx.scene.media.*;
import java.net.*;
import javafx.scene.canvas.*;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.media.*;
import java.util.*;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import javafx.scene.paint.*;
import javafx.scene.paint.LinearGradient;


public class MainGameplayPane extends Pane {
    private final int WIDTH = 800;//width of pane
    private final int HEIGHT = 600;//height of pane
    
    private final int START_BALL_X=60, START_BALL_Y=450;//where the ball will always be placed at the start
    private int ballCurrentX=START_BALL_X, ballCurrentY=START_BALL_Y;//current x and y location of a ball
    private final int TARGET_X=705, TARGET_Y=350;// x and y position of target
    
    boolean gameWon=false, gameLost=false, ballHit=false;
    
    private Canvas ballTargetCanvas=new Canvas(WIDTH,HEIGHT);//the canvas will contain a ball and a target image
    private GraphicsContext ballTargetContext = ballTargetCanvas.getGraphicsContext2D();
    private Canvas anglePowerCanvas= new Canvas(WIDTH,HEIGHT);//the canvas will contain an angle and a power gauge
    private GraphicsContext anglePowerContext = anglePowerCanvas.getGraphicsContext2D();
    
    Image ball=new Image(getClass().getResource("Media/RedBall.png").toExternalForm());//image of a ball
    Image target=new Image(getClass().getResource("Media/SidewaysTarget.png").toExternalForm());//image of a target    
    
    /*this is for the creating of two lines that will form an angle*/
    private final int ANGLE_PIVOT_X=START_BALL_X+(int)(ball.getHeight()*0.04/2), ANGLE_PIVOT_Y=(int)(START_BALL_Y+(ball.getWidth()*0.04)/2);/*point at which a line will rotate around*/
    private final int END_OF_LINE_X=ANGLE_PIVOT_X+125, END_OF_LINE_Y=ANGLE_PIVOT_Y;
    private int currentEndLineX=END_OF_LINE_X, currentEndLineY=END_OF_LINE_Y;//current x and y values of the end of a line that rotates around the pivot point
    
    private double angle=0;//will hold the current angle
    
    MainGameplayPane(){
        super.setHeight(HEIGHT);
        super.setWidth(WIDTH);
        
        this.setFocusTraversable(true);
        
        draw();//draws the two lines forming an angle, the ball and the target
 
        super.getChildren().addAll(ballTargetCanvas, anglePowerCanvas);
        
        this.setOnKeyTyped(e->{
            if(e.getCharacter().matches("w") || e.getCharacter().matches("W")){
                if(angle<90){
                    rotateXY(angle+5);
                    draw();
                }
            }
            if(e.getCharacter().matches("s") || e.getCharacter().matches("S")){
                if(angle>0){    
                    rotateXY(angle-5);
                    draw();
                }
            } 
        });
    }
    
    void draw(){
        anglePowerContext.clearRect(0, 0, WIDTH, HEIGHT);
        ballTargetContext.clearRect(0, 0, WIDTH, HEIGHT);
        
        //drawing two lines that form an angle
        anglePowerContext.setLineWidth(2.0);
        anglePowerContext.setStroke(Color.RED);
        anglePowerContext.strokeLine(ANGLE_PIVOT_X,ANGLE_PIVOT_Y,END_OF_LINE_X, END_OF_LINE_Y);
        anglePowerContext.strokeLine(ANGLE_PIVOT_X,ANGLE_PIVOT_Y,currentEndLineX, currentEndLineY);
        
        //drawing a ball and a target
        ballTargetContext.drawImage(ball, ballCurrentX, ballCurrentY, ball.getWidth()*0.04, ball.getHeight()*0.04);
        ballTargetContext.drawImage(target, TARGET_X, TARGET_Y, target.getWidth()*0.7, target.getHeight()*0.7);
        
        addGradiantRectangle();//adds a gradient rectangle to be used for the power guage
    }
    
    void rotateXY(double degrees){//uses matrix multiplication to change x and y end-point values of line rotating around a pivot point
        angle=degrees;
        currentEndLineX=(int)(((END_OF_LINE_X-ANGLE_PIVOT_X)*Math.cos(Math.toRadians(360-degrees)))-((END_OF_LINE_Y-ANGLE_PIVOT_Y)*Math.sin(Math.toRadians(360-degrees)))+ANGLE_PIVOT_X);
        currentEndLineY=(int)(((END_OF_LINE_X-ANGLE_PIVOT_X)*Math.sin(Math.toRadians(360-degrees)))+((END_OF_LINE_Y-ANGLE_PIVOT_Y)*Math.cos(Math.toRadians(360-degrees)))+ANGLE_PIVOT_Y);
    }
    
    void addGradiantRectangle(){//draws a gradient rectangle for the power guage
        Stop[] stops=new Stop[]{new Stop(0, Color.LIGHTGREEN), new Stop(1, Color.RED)};
        
        LinearGradient lg=new LinearGradient(0,0,1,0, true, CycleMethod.NO_CYCLE, stops);
        
        anglePowerContext.setFill(lg);
        anglePowerContext.fillRect(START_BALL_X, START_BALL_Y+ball.getHeight()*0.04+10, 200, 20);
        
    }
}
