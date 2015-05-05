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
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    
    private final int BALL_X=60, BALL_Y=450;
    private final int TARGET_X=705, TARGET_Y=350;
    
    boolean gameWon=false, gameLost=false, ballHit=false;
    
    private Canvas ballCanvas=new Canvas(WIDTH,HEIGHT);
    private GraphicsContext ballContext = ballCanvas.getGraphicsContext2D();
    private Canvas angleCanvas= new Canvas(WIDTH,HEIGHT);
    private GraphicsContext angleContext = angleCanvas.getGraphicsContext2D();
    
    Image ball=new Image(getClass().getResource("Media/RedBall.png").toExternalForm());
    Image target=new Image(getClass().getResource("Media/SidewaysTarget.png").toExternalForm());    
    
    private int startX=BALL_X+(int)(ball.getHeight()*0.04/2), startY=(int)(BALL_Y+(ball.getWidth()*0.04)/2);
    private int endX=startX+125, endY=startY;
    private int newEndX=endX, newEndY=endY;
    private double angle=0;
    
    MainGameplayPane(){
        super.setHeight(HEIGHT);
        super.setWidth(WIDTH);
        
        this.setFocusTraversable(true);
        
        draw();
 
        super.getChildren().addAll(ballCanvas, angleCanvas);
        
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
        angleContext.clearRect(0, 0, WIDTH, HEIGHT);
        ballContext.clearRect(0, 0, WIDTH, HEIGHT);
        
        angleContext.setLineWidth(2.0);
        angleContext.setStroke(Color.RED);
        angleContext.strokeLine(startX,startY,endX, endY);
        angleContext.strokeLine(startX,startY,newEndX, newEndY);
        
        ballContext.drawImage(ball, BALL_X, BALL_Y, ball.getWidth()*0.04, ball.getHeight()*0.04);
        ballContext.drawImage(target, TARGET_X, TARGET_Y, target.getWidth()*0.7, target.getHeight()*0.7);
        
        addGradiantRectangle();
    }
    
    void rotateXY(double degrees){
        angle=degrees;
        newEndX=(int)(((endX-startX)*Math.cos(Math.toRadians(360-degrees)))-((endY-startY)*Math.sin(Math.toRadians(360-degrees)))+startX);
        newEndY=(int)(((endX-startX)*Math.sin(Math.toRadians(360-degrees)))+((endY-startY)*Math.cos(Math.toRadians(360-degrees)))+startY);
    }
    
    void addGradiantRectangle(){
        Stop[] stops=new Stop[]{new Stop(0, Color.LIGHTGREEN), new Stop(1, Color.RED)};
        
        LinearGradient lg=new LinearGradient(0,0,1,0, true, CycleMethod.NO_CYCLE, stops);
        
        angleContext.setFill(lg);
        angleContext.fillRect(BALL_X, BALL_Y+ball.getHeight()*0.04+10, 200, 20);
        
    }
}
