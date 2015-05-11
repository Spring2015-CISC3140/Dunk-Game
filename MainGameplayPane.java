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
import javafx.scene.text.Font;


public class MainGameplayPane extends Pane {
    private final int WIDTH = 800;//width of pane
    private final int HEIGHT = 600;//height of pane
    
    private final int START_BALL_X=60, START_BALL_Y=450;//where the ball will always be placed at the start
    private int ballCurrentX=START_BALL_X, ballCurrentY=START_BALL_Y;//current x and y location of a ball
    private final int TARGET_X=705, TARGET_Y=350;// x and y position of target
    
    boolean gameWon=false, gameLost=false, ballHit=false;
    private int numberMissed=0, numberHit=0;
    
    private Canvas ballTargetCanvas=new Canvas(WIDTH,HEIGHT);//the canvas will contain a ball and a target image
    private GraphicsContext ballTargetContext = ballTargetCanvas.getGraphicsContext2D();
    private Canvas angleCanvas= new Canvas(WIDTH,HEIGHT);//the canvas will contain an angle
    private GraphicsContext angleContext = angleCanvas.getGraphicsContext2D();
    private Canvas powerCanvas = new Canvas(WIDTH, HEIGHT);//this canvas will add a power guage
    private GraphicsContext powerContext= powerCanvas.getGraphicsContext2D();
    private Canvas scoreCanvas=new Canvas(WIDTH,HEIGHT);
    private GraphicsContext scoreContext= scoreCanvas.getGraphicsContext2D();
    //private Canvas keysCanvas=new Canvas(WIDTH,HEIGHT);
    //private GraphicsContext keysContext=keysCanvas.getGraphicsContext2D();
    
    Image ball=new Image(getClass().getResource("Media/RedBall.png").toExternalForm());//image of a ball
    Image target=new Image(getClass().getResource("Media/SidewaysTarget.png").toExternalForm());//image of a target    
    
    /*this is for the creating of two lines that will form an angle*/
    private final int ANGLE_PIVOT_X=START_BALL_X+(int)(ball.getHeight()*0.04/2), ANGLE_PIVOT_Y=(int)(START_BALL_Y+(ball.getWidth()*0.04)/2);/*point at which a line will rotate around*/
    private final int END_OF_LINE_X=ANGLE_PIVOT_X+125, END_OF_LINE_Y=ANGLE_PIVOT_Y;
    private int currentEndLineX=END_OF_LINE_X, currentEndLineY=END_OF_LINE_Y;//current x and y values of the end of a line that rotates around the pivot point
    
    private double angle=0;//will hold the current angle
    private int power=0;
    private double horizontalVelocity, verticalVelocity, verticalAcceleration=-9.81, time=0;
    private boolean powerGuageForward=true;
    
    private boolean setUpState=false, ballThrownState=false;//this goes into two states, when the ball is being thrown and when angle and power is being set up
    boolean soundOn;
    boolean gameplayPaused=false;
    
    private AudioClip swooshSound;
    
    MainGameplayPane(boolean soundOn){
        super.setHeight(HEIGHT);
        super.setWidth(WIDTH);
        
        this.setFocusTraversable(true);
        
        this.soundOn=soundOn;
        
        setUpState=true;
        ballAndTarget();
        rotateXY(45);
        drawAngle();//draws the two lines forming an angle, the ball and the target
        addPowerGauge();//adds a gradient rectangle to be used for the power gauge  
        score();
 
        super.getChildren().addAll(ballTargetCanvas, angleCanvas, powerCanvas, scoreCanvas);
        
        try{//load swoosh sound that will play when ball is thrown
            URL swooshResource=getClass().getResource("Media/Swooshing.mp3");
            swooshSound=new AudioClip(swooshResource.toString());
        }catch(Exception e){
            System.out.println(e);
        }        
        
        this.setOnKeyTyped(e->{
            if(e.getCharacter().matches("w") || e.getCharacter().matches("W")){
                if(angle<90){
                    if(setUpState && !gameplayPaused ){
                        rotateXY(angle+5);
                        drawAngle();
                    }
                }
            }
            if(e.getCharacter().matches("s") || e.getCharacter().matches("S")){
                if(angle>0){  
                    if(setUpState && !gameplayPaused){
                        rotateXY(angle-5);
                        drawAngle();
                    }
                }
            }
            if(e.getCharacter().matches(" ")){
                if(setUpState && !gameplayPaused){
                    setUpState=false;
                    ballThrownState=true;
                    if(soundOn)
                        swooshSound.play(0.5);
                }
            }
        });
        

     }
    
    void drawAngle(){
        angleContext.clearRect(0, 0, WIDTH, HEIGHT);
        
        //drawing two lines that form an angle
        angleContext.setLineWidth(2.0);
        angleContext.setStroke(Color.RED);
        angleContext.strokeLine(ANGLE_PIVOT_X,ANGLE_PIVOT_Y,END_OF_LINE_X, END_OF_LINE_Y);
        angleContext.strokeLine(ANGLE_PIVOT_X,ANGLE_PIVOT_Y,currentEndLineX, currentEndLineY);
        
    }
    
    void rotateXY(double degrees){//uses matrix multiplication to change x and y end-point values of line rotating around a pivot point
        angle=degrees;
        currentEndLineX=(int)(((END_OF_LINE_X-ANGLE_PIVOT_X)*Math.cos(Math.toRadians(360-degrees)))-((END_OF_LINE_Y-ANGLE_PIVOT_Y)*Math.sin(Math.toRadians(360-degrees)))+ANGLE_PIVOT_X);
        currentEndLineY=(int)(((END_OF_LINE_X-ANGLE_PIVOT_X)*Math.sin(Math.toRadians(360-degrees)))+((END_OF_LINE_Y-ANGLE_PIVOT_Y)*Math.cos(Math.toRadians(360-degrees)))+ANGLE_PIVOT_Y);
    }
    
    void addPowerGauge(){//draws a gradient rectangle for the power guage
        int widthOfGradient=200, heightOfGradient=20;
        int gradientX=START_BALL_X, gradientY=(int)(START_BALL_Y+ball.getHeight()*0.04+10);
                
        Stop[] stops=new Stop[]{new Stop(0, Color.LIGHTGREEN), new Stop(1, Color.RED)};
        LinearGradient lg=new LinearGradient(0,0,1,0, true, CycleMethod.NO_CYCLE, stops);
        
        Timeline timeline= new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        KeyFrame keyFrame=new KeyFrame(Duration.seconds(0.01), e->{

            if(setUpState && !gameplayPaused){
                powerContext.clearRect(0, 0, WIDTH, HEIGHT);
                
                powerContext.setFill(lg);
                powerContext.fillRect(gradientX, gradientY, widthOfGradient, heightOfGradient);
        
                powerContext.setLineWidth(3.0);
                powerContext.setStroke(Color.BLACK);
                powerContext.strokeLine(power+gradientX, gradientY-2, power+gradientX , gradientY+heightOfGradient+2);

                if(powerGuageForward){
                    if(power+2<widthOfGradient)
                       power+=1;
                    else powerGuageForward=false;
                }
                if(!powerGuageForward){
                    if(power>0)
                        power-=1;
                    else powerGuageForward=true;
                }
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
    
    void ballAndTarget(){
        Timeline timeline= new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        
        KeyFrame keyFrame=new KeyFrame(Duration.seconds(0.01), e->{
            if(setUpState && !gameplayPaused){
                ballTargetContext.clearRect(0, 0, WIDTH, HEIGHT);
                //drawing a ball and a target
                ballTargetContext.drawImage(target, TARGET_X, TARGET_Y, target.getWidth()*0.7, target.getHeight()*0.7); 
                ballTargetContext.drawImage(ball, ballCurrentX, ballCurrentY, ball.getWidth()*0.04, ball.getHeight()*0.04);
            }
            if(ballThrownState && !gameplayPaused){
                ballTargetContext.clearRect(0, 0, WIDTH, HEIGHT);

                time+=0.03;
                horizontalVelocity=(double)(power)*Math.cos(Math.toRadians(angle));
                verticalVelocity=(double)(power)*Math.sin(Math.toRadians(angle));
                
                ballCurrentX=START_BALL_X+(int)(horizontalVelocity*time);
                ballCurrentY=START_BALL_Y-(int)((verticalVelocity*time)+(0.5*verticalAcceleration*time*time));
                
                ballTargetContext.drawImage(target, TARGET_X, TARGET_Y, target.getWidth()*0.7, target.getHeight()*0.7);      
                ballTargetContext.drawImage(ball, ballCurrentX, ballCurrentY, ball.getWidth()*0.04, ball.getHeight()*0.04);
                
                if((ballCurrentX+(ball.getWidth()*0.04)/2)>TARGET_X && (ballCurrentX+ball.getWidth()*0.04/2)<TARGET_X+target.getWidth()*0.7 && ballCurrentY+ball.getHeight()*0.04>TARGET_Y && ballCurrentY<(TARGET_Y-4+target.getHeight()*0.7)){
                    numberHit+=1;
                    if(numberHit==3){
                        score();
                        gameWon=true;
                    }
                    else{
                        score();
                        ballHit=true;
                    }
                    ballThrownState=false;

                }
                if(ballCurrentX>WIDTH || ballCurrentY>HEIGHT ){//misses
                    ballThrownState=false;
                    numberMissed+=1;
                    if(numberMissed<3){
                        score();
                        reset();
                    }
                    if(numberMissed==3){
                        score();
                        gameLost=true;
                    }
                    
                }
            }
        });
        
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }
    
    void reset(){
        ballCurrentX=START_BALL_X;
        ballCurrentY=START_BALL_Y;
        setUpState=true;
        time=0;
    }
    
    void score(){
        scoreContext.clearRect(0, 0, WIDTH, HEIGHT);
        scoreContext.setFont(Font.font(25));
        scoreContext.fillText("Hits: "+numberHit+" Misses: "+numberMissed, 540, 30);
    }
    
}

