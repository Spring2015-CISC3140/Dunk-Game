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

public class MainGamePane extends Pane {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    AnchorPane apane = new AnchorPane();

    private AudioClip songLoop;
    private SongService songService=new SongService();
    boolean soundOn;
    boolean closeGame=false, restartGame=false;
    boolean professor, deen, trustee;
    
    private boolean won = false;
    boolean continueToNextScene=false;
    
    private AudioClip splashSound;
    
    private Canvas seatCanvas = new Canvas(150, 300);
    private ActivateSeat activateSeat=new ActivateSeat();
    private GraphicsContext seatGraphics = seatCanvas.getGraphicsContext2D();
    private int count =0;
    
    private Canvas cloudCanvas = new Canvas(WIDTH, 250);
    private GraphicsContext cloudGraphics = cloudCanvas.getGraphicsContext2D();
    private Image currentCloud;
    private int counter1 = 0, counter2=0, cloudX, cloudY, cloudWidth, cloudHeight, cloudSway;
    private double sizeChange, speed;
    
    private Canvas characterCanvas= new Canvas(200,300);
    private GraphicsContext characterGraphics = characterCanvas.getGraphicsContext2D();
    private int characterX, characterY, count1=0, count2=0;
    private Image character, tearDrop;
    private final double decreaseBy=0.25;
    
    private Canvas splashCanvas=new Canvas(WIDTH,HEIGHT);
    private GraphicsContext splashGraphics=splashCanvas.getGraphicsContext2D();
    private Image splashImage;
    private double splashSizeIncrease=1.05, splashHeight, splashWidth;
    private int splashX, splashY, count3=0;
    
    private PauseMenu pauseMenu;

    MainGamePane(boolean professor, boolean deen, boolean trustee, boolean soundOn) {
        super.setHeight(HEIGHT);
        super.setWidth(WIDTH);
        apane.setPrefSize(WIDTH, HEIGHT);
        
        this.professor=professor;
        this.deen=deen;
        this.trustee=trustee;
        this.soundOn=soundOn;

        try {
            URL songLoopResource = getClass().getResource("Media/CloudCastle.wav");
            songLoop = new AudioClip(songLoopResource.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
        
        try{
            URL splashResource=getClass().getResource("Media/WaterSplash.mp3");
            splashSound=new AudioClip(splashResource.toString());
        }catch(Exception e){
            System.out.println(e);
        }
        
        try{
            splashImage=new Image(getClass().getResource("Media/Splash2.png").toExternalForm());
        }catch(Exception e){
            System.out.println(e);
        }
        
        try{
            tearDrop=new Image(getClass().getResource("Media/TearDrop.png").toExternalForm());
        }catch(Exception e){
            System.out.println(e);
        }

        startSongService();

        setDunkSeat();
        apane.getChildren().add(seatCanvas);
        AnchorPane.setBottomAnchor(seatCanvas, 20.0);
        AnchorPane.setRightAnchor(seatCanvas, 18.0);

        setDunkTank();
        
        floatingClouds();
        apane.getChildren().add(cloudCanvas);
        AnchorPane.setTopAnchor(cloudCanvas, 0.0);
        AnchorPane.setLeftAnchor(cloudCanvas, 0.0);
        
        seatCharacter();
        apane.getChildren().add(characterCanvas);
        AnchorPane.setTopAnchor(characterCanvas, 200.0);
        AnchorPane.setLeftAnchor(characterCanvas, 500.0);

        try {
            String backgroundPath = getClass().getResource("Media/GrassyBackground.png").toExternalForm();
            super.setStyle("-fx-background-image: url('" + backgroundPath + "');-fx-background-repeat: stretch; -fx-background-size: 800 600;");
        } catch (Exception e) {
            super.setStyle("fx-background-image: url('http://www.clker.com/cliparts/Y/f/N/P/G/E/fresh-grass-and-sky-hi.png')");
        }
        
        apane.getChildren().add(splashCanvas);

        super.getChildren().add(apane);

        activateSeat = new ActivateSeat();
        activateSeat.start();

        Test test = new Test();
        test.start();
        
        pauseButton();
    }

    void setDunkTank() {
        ImageView dunkTankView = new ImageView();

        if (professor) {
            Image dunkTank = new Image(getClass().getResource("Media/WaterTankCutOut.png").toExternalForm());
            dunkTankView = new ImageView(dunkTank);
        }
        if (deen) {
            Image dunkTank = new Image(getClass().getResource("Media/BubbleTankCutOut.png").toExternalForm());
            dunkTankView = new ImageView(dunkTank);
        }
        if (trustee) {
            Image dunkTank = new Image(getClass().getResource("Media/AcidTankCutOut.png").toExternalForm());
            dunkTankView = new ImageView(dunkTank);
        }

        dunkTankView.setFitHeight(200);
        dunkTankView.setFitWidth(275);

        if(trustee){
            AnchorPane.setBottomAnchor(dunkTankView, 60.0);
            AnchorPane.setRightAnchor(dunkTankView, 13.0);    
        }
        else{
            AnchorPane.setBottomAnchor(dunkTankView, 60.0);
            AnchorPane.setRightAnchor(dunkTankView, 30.0);
        }

        Image grassPatch;
        try {
            grassPatch = new Image(getClass().getResource("Media/GrassPatch.png").toExternalForm());
        } catch (Exception e) {
            grassPatch = new Image("http://images.clipartpanda.com/grass-border-clipart-acqK97xcM.png");
        }

        ImageView grassPatchView = new ImageView(grassPatch);
        grassPatchView.setFitHeight(35.0);
        grassPatchView.setFitWidth(75.0);

        AnchorPane.setBottomAnchor(grassPatchView, 85.0);
        AnchorPane.setRightAnchor(grassPatchView, 135.0);

        apane.getChildren().addAll(dunkTankView, grassPatchView);
    }

    private void setDunkSeat() {
        Image seat = new Image(getClass().getResource("Media/WoodSeatCutOut1.png").toExternalForm());
        seatGraphics.drawImage(seat, -4, 0, 108, 120);
        
        backOfSeat();
    }

    private void backOfSeat() {
        Image board = new Image(getClass().getResource("Media/WoodPoleCutOut.png").toExternalForm());
        seatGraphics.drawImage(board, 30, 35, 110, 180);
    }
    
    void seatCharacter(){
        if(professor){
            character=new Image(getClass().getResource("Media/sit 3.png").toExternalForm());
            characterX=125;
            characterY=25;
        }
        else if(deen){
            character=new Image(getClass().getResource("Media/sit 1.png").toExternalForm());
            characterX=125;
            characterY=25;
        }
        else if(trustee){
            character=new Image(getClass().getResource("Media/sit 2.png").toExternalForm());
            characterX=135;
            characterY=15;
        }
            characterGraphics.drawImage(character, characterX, characterY, character.getWidth()*decreaseBy, character.getHeight()*decreaseBy);

    }

    void fallingSeat() {
        exclamation();
        
        if(soundOn)
            splashSound.play(1.0);
        
        Timeline timeline = new Timeline();
        timeline.setCycleCount(4);

        Image []seat=new Image[4];
        seat[0]=new Image(getClass().getResource("Media/WoodSeatCutOut2.png").toExternalForm());
        seat[1]=new Image(getClass().getResource("Media/WoodSeatCutOut3.png").toExternalForm());
        seat[2]=new Image(getClass().getResource("Media/WoodSeatCutOut4.png").toExternalForm());
        seat[3]=new Image(getClass().getResource("Media/WoodSeatCutOut5.png").toExternalForm());
        
        count = 0;

        KeyFrame falling = new KeyFrame(Duration.seconds(0.05), e -> {
            seatGraphics.clearRect(0, 0, 300, 300);
            if(count==0)
                seatGraphics.drawImage(seat[count], -8, -2, 130, 140);
            else if(count==1){
                seatGraphics.drawImage(seat[count], 31, 40, 55, 78);
                fallingCharacter();
            }
            else if(count==2)
                seatGraphics.drawImage(seat[count], -15, 8, 160, 140);
            else if(count==3)
                seatGraphics.drawImage(seat[count], 24, 24, 100, 112);
            backOfSeat();
            count++;
        });
        timeline.getKeyFrames().add(falling);
        timeline.play();
    }
    
    void characterShake(){
        Timeline timeline=new Timeline();
        timeline.setCycleCount(3);
        count2=0;
        
        
        KeyFrame shake=new KeyFrame(Duration.seconds(0.2), e->{
            characterGraphics.clearRect(0, 0, 200, 300);
            if(count==0){
                characterY-=3;
                characterGraphics.drawImage(character, characterX, characterY, character.getWidth()*decreaseBy, character.getHeight()*decreaseBy);
                if(trustee)
                    characterGraphics.drawImage(tearDrop, characterX+25, characterY+10, tearDrop.getWidth()*0.09, tearDrop.getHeight()*0.09);
                else
                    characterGraphics.drawImage(tearDrop, characterX+35, characterY+5, tearDrop.getWidth()*0.1, tearDrop.getHeight()*0.1);
            }
            if(count==1){
                characterY+=3;
                characterGraphics.drawImage(character, characterX, characterY, character.getWidth()*decreaseBy, character.getHeight()*decreaseBy);
                if(trustee)
                    characterGraphics.drawImage(tearDrop, characterX+25, characterY+10, tearDrop.getWidth()*0.09, tearDrop.getHeight()*0.09);
                else
                    characterGraphics.drawImage(tearDrop, characterX+35, characterY+5, tearDrop.getWidth()*0.1, tearDrop.getHeight()*0.1);
            }
            if(count==2){
                characterGraphics.drawImage(character, characterX, characterY, character.getWidth()*decreaseBy, character.getHeight()*decreaseBy);
            }
            count++;
        });
        
        timeline.getKeyFrames().add(shake);
        timeline.play();
    }
    
    void fallingCharacter(){
        Timeline timeline=new Timeline();
        timeline.setCycleCount(27);
        count1=0;
        
        KeyFrame falling= new KeyFrame(Duration.seconds(0.01), e->{
            characterGraphics.clearRect(0, 0, 200, 300);
            exclamation();
            characterGraphics.drawImage(character, characterX, characterY, character.getWidth()*decreaseBy, character.getHeight()*decreaseBy);
            characterY+=2;
            if(count1==22){
                splash();
            }
            else
                count1++;
        });
        
        timeline.getKeyFrames().add(falling);
        timeline.play();
    }
    
    void exclamation(){
        Image exclamation=new Image(getClass().getResource("Media/exclamation.png").toExternalForm());
        characterGraphics.drawImage(exclamation, 130, -15, exclamation.getWidth()*0.12, exclamation.getHeight()*0.12);
    }
    
    void splash(){
        Timeline timeline=new Timeline();
        timeline.setCycleCount(10);
        count3=0;

        splashHeight=splashImage.getHeight()*0.2;
        splashWidth=splashImage.getWidth()*0.2;
        splashX=580;
        splashY=320;
        
        KeyFrame splash=new KeyFrame(Duration.seconds(0.04), e->{
            splashGraphics.clearRect(0, 0, WIDTH, HEIGHT);
            splashGraphics.drawImage(splashImage, splashX, splashY, splashHeight, splashWidth);
            splashX-= Math.abs(splashWidth-splashWidth*splashSizeIncrease)/2;
            splashY-= Math.abs(splashHeight-splashHeight*splashSizeIncrease)/2;
            splashHeight*=splashSizeIncrease;
            splashWidth*=splashSizeIncrease;
            if(count3==9){
                System.out.println("continueToNextScene");
                continueToNextScene=true;
            }
            count3++;
        });
       
        timeline.getKeyFrames().add(splash);
        timeline.play();
    }

    void floatingClouds() {
        Timeline cloudMoving=new Timeline();
        cloudMoving.setCycleCount(Timeline.INDEFINITE);
        
        Image[] cloud = new Image[2];
        cloud[0] = new Image(getClass().getResource("Media/Cloud1.png").toExternalForm());
        cloud[1] = new Image(getClass().getResource("Media/Cloud2.png").toExternalForm());

        cloudX=WIDTH+1;
        speed=0.2;
        
        KeyFrame floating = new KeyFrame(Duration.seconds(speed), e -> {  
            if(cloudX>WIDTH){
                currentCloud = cloud[(new Random()).nextInt(2)];
                sizeChange=(new Random()).nextDouble()+0.5;
                cloudWidth = (int)(150*sizeChange);
                cloudHeight =(int)(100*sizeChange);
            
                cloudX = 0-cloudWidth;
                cloudY = 0+((new Random()).nextInt(125));
                cloudSway= (new Random()).nextInt(15) + 5;
                counter1=0;
                counter2=0;
            }
            
            cloudGraphics.clearRect(0, 0, WIDTH, 300);
            cloudGraphics.drawImage(currentCloud, cloudX, cloudY, cloudWidth, cloudHeight);
            cloudX += 3;
            if(counter1==cloudSway){
                counter1=0-cloudSway;
                counter2=cloudSway;
            }
            else if(counter2!=0){
                if((cloudY+cloudWidth+4)>250)
                    cloudY +=(new Random()).nextInt(4)-4;
                else
                    cloudY +=(new Random()).nextInt(8)-4;
                counter2--;
            }
            else counter1++;
        });

        cloudMoving.getKeyFrames().add(floating);
        cloudMoving.play();
    }
    
    void startSongService(){
        songService = new SongService();
        songService.start();
    }

    class SongService extends Service<Integer> {

        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() {
                    songLoop.setCycleCount(AudioClip.INDEFINITE);
                    songLoop.play(0.1);
                    while (soundOn) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                    }
                    return 0;
                }
                @Override
                protected void succeeded() {
                    songLoop.stop();
                }
            };
        }
    }

    class Test extends Service<Integer> {

        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() {
                    try {
                        Thread.sleep(2770);
                    } catch (Exception e) {
                    }                   
                    characterShake();
                    try {
                        Thread.sleep(2770);
                    } catch (Exception e) {
                    }
                    won = true;
                    return 0;
                }
            };
        }
    }

    class ActivateSeat extends Service<Integer> {

        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() {
                    while (!won) {
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {
                        }
                    }
                    return 0;
                }

                @Override
                protected void succeeded() {
                    fallingSeat();
                }
            };
        }
    }
    
    
    void pauseButton(){
        //loading a pause icon
        Image pause;
        try{
            pause=new Image(getClass().getResource("Media/Pause.png").toExternalForm());
        }catch(Exception e){pause=new Image("http://images.clipartpanda.com/pause-clipart-media-playback-pause_Clip_Art.png");}
        
        ImageView pauseView=new ImageView(pause);
        pauseView.setFitHeight(50);
        pauseView.setFitWidth(50);
        
        apane.getChildren().add(pauseView);//attaches pause icon to main pane
        AnchorPane.setTopAnchor(pauseView, 10.0);
        AnchorPane.setLeftAnchor(pauseView, 10.0);
        
        //first background task waits for the player to request a quit game when pauseMenu is initiated
        class CloseGameService extends Service<Integer>{
            @Override
            protected Task<Integer> createTask(){
                return new Task<Integer>(){
                    @Override
                    protected Integer call(){
                        while(!pauseMenu.quitGame){//while main menu's quit button isn't pressed, keep checking
                            try{
                                Thread.sleep(500);
                            }
                            catch(Exception e){System.out.println(e);}
                        }
                        return 0;
                    }
                    @Override
                    protected void succeeded(){
                        closeGame=true;//closed the stage, thus quiting the game
                    }
                };
            }
        }
        CloseGameService closeGameService=new CloseGameService();//creats an object of a service that will check if the game needs to be closed
        
        //second task waits the player to press return from the pause menu
        class ReturnToGameService extends Service<Integer>{
            @Override
            protected Task<Integer> createTask(){
                return new Task<Integer>(){
                    @Override
                    protected Integer call(){
                        while(pauseMenu.pauseInProgress){//keep checking till pauseMenu's return button isn't pressed
                            try{
                              Thread.sleep(500);
                            }
                            catch(Exception e){System.out.println(e);}
                        }
                         return 0;
                    }
                    @Override
                    protected void succeeded(){
                        apane.getChildren().remove(pauseMenu);//removed the pauseMenu pane from the primaryStage, thus returning to the previous view
                        soundOn=pauseMenu.soundIsOn;
                    }
                };
            }
        }
        ReturnToGameService returnToGameService=new ReturnToGameService();//creating an object of the service that runs to check if the needs to be returned to
 
        
        //the third task is to check if the user wants to restart the game from the pause menu
        class RestartGameService extends Service<Integer>{
            @Override
            protected Task<Integer> createTask(){
                return new Task<Integer>(){
                    @Override
                    protected Integer call(){
                        while(!pauseMenu.startGameOver){//keep checking while pauseMenu's restart button doens't get pressed
                            try{
                                Thread.sleep(500);
                            }
                            catch(Exception e){System.out.println(e);}
                        }
                        return 0;
                    }
                    @Override
                    protected void succeeded(){
                        restartGame=true;
                    }
                };
            }
        }
        RestartGameService restartGameService=new RestartGameService();//creats a service, when activated will check if the game needs restarting
        
        
        pauseView.setOnMouseClicked(e ->{
            pauseMenu=new PauseMenu(soundOn);
            pauseMenu.soundIsOn=soundOn;//you must tell the pause menu if the sound is on before adding it to the scene
            pauseMenu.pauseInProgress=true;//you must tell pause menu that the pause is in progress before adding it to the scene
            apane.getChildren().add(pauseMenu);
            
            closeGameService.restart();//starts service to check if the quit button is pressed
            returnToGameService.restart();//starts service to check if return button is pressed
            restartGameService.restart();//starts service to check if the restart button is pressed
        });
        
    } 
    
    void kill(){
        songLoop.stop();
        songService.cancel();
        activateSeat.cancel();
    }
    
}
