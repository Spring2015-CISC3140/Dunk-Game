/*this class creates a background of a character sitting, a cloud moving in the background,
and also adds the main game layer and the pause menu layer on top of itself, which are 
designed in other classes.

the background class also works as the intermediary between the pause menu and the main game play layer
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
import java.util.*;

public class BackgroundPane extends Pane {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    AnchorPane apane = new AnchorPane();//pane that all images and canvases will be added to.

    private AudioClip songLoop;//contains the main background song that will loop, defined in the constructor, and activated with the service SongService
    private SongService songService=new SongService();//plays songLoop while soundOn=true
    boolean soundOn;//all sound plays if true, sound turned off if false
    
    boolean closeGame=false, restartGame=false;/*the class that created an object of this class must check if one of these turns true, if the pause menu 
    object has restart button pressed, then restartGame=true, if the pause menu object has quit button pressed then closeGame=true*/
    boolean professor, dean, trustee;//only one of them should be true, and the correct character is displayed depending on which one is true
    
    boolean gameWon = false, gameLost=false;
    boolean continueToNextScene=false;//the class that creates an object of this class, will switch to the win/lose menu if this is true
    
    private AudioClip splashSound;//this is the splashing sound that will play when gameWon is true, and is activated by fallingSeat(), and song is loaded by constructor
    private AudioClip ballHitSound;//this is the sound that will be played when the ball hits the target
    private AudioClip screamSound[];//this is an array of screams that will be picked randomly to play
    
    private Canvas seatCanvas = new Canvas(150, 300);//this canvas contains the backboard and the seat the character will sit on
    private ActivateSeat activateSeat=new ActivateSeat();//if gameWon=true, start the animation for the seat falling, and the character falling
    private GraphicsContext seatGraphics = seatCanvas.getGraphicsContext2D();//adds images of backboars and seat to canvas seatCanvas
    private int count =0;//used as a counter in falling seat
    
    private Canvas cloudCanvas = new Canvas(WIDTH, 250);//creates a canvas for a cloud floating in the background
    private GraphicsContext cloudGraphics = cloudCanvas.getGraphicsContext2D();//adds cloud images to canvas
    private Image currentCloud;//will hold an object of a cloud image
    private int counter1 = 0, counter2=0;//counter1 and counter2 is used in small calculations in floatingCloud()
    private int cloudX, cloudY, cloudWidth, cloudHeight, cloudSway;//will contain the characteristics of current clouds, these characteristics are defined in floatingClouds()
    private double sizeChange;//size of cloud will vary, and will be set randomly
    
    private Canvas characterCanvas= new Canvas(200,300);//will contain the image of the character
    private GraphicsContext characterGraphics = characterCanvas.getGraphicsContext2D();//will put an image of a character on the characterCanvas
    private int characterX, characterY, count1=0, count2=0;//the count1 in fallingCharacter(), and count2 in characterStartled() will be used for small calculations
    private Image character, tearDrop;//character will have a character image loaded into it in seatCharacter(), and teardrop image, in tearDrop, will appear over the character when they get startled, activated by characterStartled()
    private final double decreaseBy=0.25;//what to decrease the actual size of the character image by, when displayed in background. Used in seatCharacter()
    
    private Canvas splashCanvas=new Canvas(WIDTH,HEIGHT);//this will display a splash across the whole screen, when gameWon is true. This is will be activated by slpash()
    private GraphicsContext splashGraphics=splashCanvas.getGraphicsContext2D();
    private Image splashImage;//contains splash image loaded in constructor
    private double splashSizeIncrease=1.05, splashHeight, splashWidth;//splashSizeIncrease if by how much the size of the slash will grow, in a splashing animation
    private int splashX, splashY, count3=0;
    
    private MainGameplayPane mainGameplay;//will contains the actual game play that will over lap the background. An object is created in gamePlay()
    private BallHitService ballHitService=new BallHitService();
    
    private PauseMenu pauseMenu;//will contain the pause menu that will over lap the background and mainGamePlay. An object is created in pauseButton()

    BackgroundPane(boolean professor, boolean dean, boolean trustee, boolean soundOn) {
        super.setHeight(HEIGHT);
        super.setWidth(WIDTH);
        apane.setPrefSize(WIDTH, HEIGHT);//pane will contain all canvases and images that are created in the BackgroundPane class
        
        this.professor=professor;
        this.dean=dean;
        this.trustee=trustee;
        this.soundOn=soundOn;

        try {//load main song game
            URL songLoopResource = getClass().getResource("Media/CloudCastle.wav");
            songLoop = new AudioClip(songLoopResource.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
        
        try{//load splash sound that will play when game is won
            URL splashResource=getClass().getResource("Media/WaterSplash.mp3");
            splashSound=new AudioClip(splashResource.toString());
        }catch(Exception e){
            System.out.println(e);
        }
        
        try{//load ball hit sound that will play when ball hits a target
            URL ballHitResource=getClass().getResource("Media/BallHit.wav");
            ballHitSound=new AudioClip(ballHitResource.toString());
        }catch(Exception e){
            System.out.println(e);
        }        
        
        try{//load image of a splash be displayed, and that will grow when game is won
            if(trustee)
                splashImage=new Image(getClass().getResource("Media/Splash3.png").toExternalForm());    
            else
                splashImage=new Image(getClass().getResource("Media/Splash2.png").toExternalForm());
        }catch(Exception e){
            System.out.println(e);
        }
        
        try{//loads image of a tear drop to display over character's head when character is nervous
            tearDrop=new Image(getClass().getResource("Media/TearDrop.png").toExternalForm());
        }catch(Exception e){
            System.out.println(e);
        }
        
        try{
            URL scream1=getClass().getResource("Media/Scream1.mp3");
            URL scream2=getClass().getResource("Media/Scream2.mp3");
            URL scream3=getClass().getResource("Media/Scream3.mp3");
            screamSound=new AudioClip[]{(new AudioClip(scream1.toString())),(new AudioClip(scream2.toString())),(new AudioClip(scream3.toString()))};
        }catch(Exception e){System.out.println(e);}

        startSongService();//starts service to play background music, plays until soundOn is false

        setDunkSeat();//adds a canvas containing the dunkseat and the backboard of the dunkseat
        apane.getChildren().add(seatCanvas);
        AnchorPane.setBottomAnchor(seatCanvas, 20.0);
        AnchorPane.setRightAnchor(seatCanvas, 18.0);

        setDunkTank();//adds a dunk tank image to AnchorPane apane
        
        floatingClouds();//activates services that make clouds move across the screen in an endless loop
        apane.getChildren().add(cloudCanvas);
        AnchorPane.setTopAnchor(cloudCanvas, 0.0);
        AnchorPane.setLeftAnchor(cloudCanvas, 0.0);
        
        seatCharacter();//chooses which character to display based on if professor, deen, or trustee is true, and overlaps them with the seatCanvas, to make them appear to be sitting
        apane.getChildren().add(characterCanvas);
        AnchorPane.setTopAnchor(characterCanvas, 200.0);
        AnchorPane.setLeftAnchor(characterCanvas, 500.0);

        try {//adds grassy background
            String backgroundPath = getClass().getResource("Media/GrassyBackground.png").toExternalForm();
            super.setStyle("-fx-background-image: url('" + backgroundPath + "');-fx-background-repeat: stretch; -fx-background-size: 800 600;");
        } catch (Exception e) {
            super.setStyle("fx-background-image: url('http://www.clker.com/cliparts/Y/f/N/P/G/E/fresh-grass-and-sky-hi.png')");
        }
        
        super.getChildren().add(apane);

        activateSeat = new ActivateSeat();//this will start a service what will keep checking if gameWon is true yet, to then trigger the seat falling and the character falling, and a splash to grow across the screen
        activateSeat.start();

        //(new Test()).start();
        gamePlay(); //creates an object that will overlap the background, that will display the main game play, and changes gameWon or gameLost booleans
        ballHitService.start();
        
        apane.getChildren().add(splashCanvas);//adds a canvas that will display a big growing splash when gameWon is true
        
        pauseButton();//adds a pause button to the top left of the screen, and a pause pane appears when pressed
    }

    void setDunkTank() {//an appropriate dunk tank is selected depending on what character is chosen, and places them on screen
        ImageView dunkTankView = new ImageView();

        if (professor) {
            Image dunkTank = new Image(getClass().getResource("Media/WaterTankCutOut.png").toExternalForm());
            dunkTankView = new ImageView(dunkTank);
        }
        if (dean) {
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

        AnchorPane.setBottomAnchor(grassPatchView, 78.0);
        AnchorPane.setRightAnchor(grassPatchView, 135.0);

        apane.getChildren().addAll(dunkTankView, grassPatchView);
    }

    private void setDunkSeat() {//places dunk seat on the screen
        Image seat = new Image(getClass().getResource("Media/WoodSeatCutOut1.png").toExternalForm());
        seatGraphics.drawImage(seat, -4, 0, 108, 120);
        
        backOfSeat();
    }

    private void backOfSeat() {//places the backboard of the seat on screen
        Image board = new Image(getClass().getResource("Media/WoodPoleCutOut.png").toExternalForm());
        seatGraphics.drawImage(board, 30, 35, 110, 180);
    }
    
    void seatCharacter(){//chooses the appropriate character to display based on booleans professor, deen, trustee
        if(professor){
            character=new Image(getClass().getResource("Media/sit 3.png").toExternalForm());
            characterX=125;
            characterY=25;
        }
        else if(dean){
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

    void dunkCharacter() {//starts the animation of a character falling
        exclamation();
        
        if(soundOn){
            Random rand=new Random();
            screamSound[rand.nextInt(3)].play(0.5);
        }
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
    
    void characterStartled(){//playes the animation of character jumping up when they get startled, when the a ball hits the target
        Timeline timeline=new Timeline();
        timeline.setCycleCount(3);
        count2=0;
        
        if(soundOn)
            ballHitSound.play(0.5);
        
        KeyFrame shake=new KeyFrame(Duration.seconds(0.2), e->{
            characterGraphics.clearRect(0, 0, 200, 300);
            if(count2==0){
                characterY-=3;
                characterGraphics.drawImage(character, characterX, characterY, character.getWidth()*decreaseBy, character.getHeight()*decreaseBy);
                if(trustee)
                    characterGraphics.drawImage(tearDrop, characterX+25, characterY+10, tearDrop.getWidth()*0.09, tearDrop.getHeight()*0.09);
                else
                    characterGraphics.drawImage(tearDrop, characterX+35, characterY+5, tearDrop.getWidth()*0.1, tearDrop.getHeight()*0.1);
            }
            if(count2==1){
                characterY+=3;
                characterGraphics.drawImage(character, characterX, characterY, character.getWidth()*decreaseBy, character.getHeight()*decreaseBy);
                if(trustee)
                    characterGraphics.drawImage(tearDrop, characterX+25, characterY+10, tearDrop.getWidth()*0.09, tearDrop.getHeight()*0.09);
                else
                    characterGraphics.drawImage(tearDrop, characterX+35, characterY+5, tearDrop.getWidth()*0.1, tearDrop.getHeight()*0.1);
            }
            if(count2==2){
                characterGraphics.drawImage(character, characterX, characterY, character.getWidth()*decreaseBy, character.getHeight()*decreaseBy);
            }
            count2++;
        });
        
        timeline.getKeyFrames().add(shake);
        timeline.play();
    }
    
    void fallingCharacter(){//plays the animation of a character falling down
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
    
    void exclamation(){//places an exclamation mark over characters head
        Image exclamation=new Image(getClass().getResource("Media/exclamation.png").toExternalForm());
        characterGraphics.drawImage(exclamation, 130, -15, exclamation.getWidth()*0.12, exclamation.getHeight()*0.12);
    }
    
    void splash(){//starts an animation of a splash that grows 
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

    void floatingClouds() {//playes the animation of a cloud floating in the background endlessly
        Timeline cloudMoving=new Timeline();
        cloudMoving.setCycleCount(Timeline.INDEFINITE);
        
        Image[] cloud = new Image[2];
        cloud[0] = new Image(getClass().getResource("Media/Cloud1.png").toExternalForm());
        cloud[1] = new Image(getClass().getResource("Media/Cloud2.png").toExternalForm());

        cloudX=WIDTH+1;
        double speed=0.2;
        
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
    
    void startSongService(){//starts to play the background music by activating the songservice
        songService = new SongService();
        songService.start();
    }

    class SongService extends Service<Integer> {//plays background song while soundOn =true

        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() {
                    if(soundOn){
                        songLoop.setCycleCount(AudioClip.INDEFINITE);
                        songLoop.play(0.1);
                    }
                    while (soundOn) {
                        try {
                            Thread.sleep(100);
                        } catch (Exception e) {
                        }
                    }
                    return 0;
                }
                @Override
                protected void succeeded() {
                    songLoop.stop();
                    (new RestartSongService()).start();
                }
            };
        }
    }

    class RestartSongService extends Service<Integer> {//while soundOn is false it waits for soundOn to be true, and activates the SongService to play the song again

        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() {
                    while (!soundOn) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {}
                    }
                    return 0;
                }
                @Override
                protected void succeeded() {
                    startSongService();
                }
            };
        }
    }    

    class Test extends Service<Integer> {//test animation for character startled, and character falling with a timer

        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() {
                    try {
                        Thread.sleep(2770);
                    } catch (Exception e) {
                    }                   
                    characterStartled();
                    try {
                        Thread.sleep(2770);
                    } catch (Exception e) {
                    }
                    gameWon = true;
                    //continueToNextScene=true;
                    return 0;
                }
            };
        }
    }

    class ActivateSeat extends Service<Integer> {//waits for gameWon to be true, and then makes the seat drop and the character fall

        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() {
                    while (!gameWon) {
                        try {
                            Thread.sleep(500);
                        } catch (Exception e) {
                        }
                    }
                    return 0;
                }

                @Override
                protected void succeeded() {
                    dunkCharacter();
                }
            };
        }
    }
    
    void kill(){//used by classes that create the object to kill some threading processes, when this pane is done with.
        songLoop.stop();
        songService.cancel();
        activateSeat.cancel();
    }
    
    void gamePlay(){//adds a pane that will display the main game play
        mainGameplay=new MainGameplayPane(soundOn);
        
        apane.getChildren().add(mainGameplay);
        
        class GameWonService extends Service<Integer>{//waits for game to be declared won by the mainGameplay object, and sets GameWon=true
            @Override
            protected Task<Integer> createTask(){
                return new Task<Integer>(){
                    @Override
                    protected Integer call(){
                        while(!mainGameplay.gameWon){
                            try{
                                Thread.sleep(500);
                            }
                            catch(Exception e){System.out.println(e);}
                        }
                        return 0;
                    }
                    @Override
                    protected void succeeded(){
                        gameWon=true;
                    }
                };
            }
        }
        GameWonService gameWonService=new GameWonService();
        
        class GameLostService extends Service<Integer>{//waits for game to be declared lost by mainGameplay object, and sets GameLost=true
            @Override
            protected Task<Integer> createTask(){
                return new Task<Integer>(){
                    @Override
                    protected Integer call(){
                        while(!mainGameplay.gameLost){
                            try{
                                Thread.sleep(100);
                            }
                            catch(Exception e){System.out.println(e);}
                        }
                        return 0;
                    }
                    @Override
                    protected void succeeded(){
                        gameLost=true;
                        continueToNextScene=true;
                    }
                };
            }
        }
        GameLostService gameLostService=new GameLostService(); 
        
        gameWonService.start();
        gameLostService.start();
    
    }
    class BallHitService extends Service<Integer>{// waits for the ball to hit a target in mainGameplay
        @Override
        protected Task<Integer> createTask(){
            return new Task<Integer>(){
                @Override
                protected Integer call(){
                    while(!mainGameplay.ballHit){
                        try{
                            Thread.sleep(100);
                        }
                        catch(Exception e){System.out.println(e);}
                    }
                    return 0;
                }
                @Override
                protected void succeeded(){
                    if(!mainGameplay.gameWon || !mainGameplay.gameLost){
                        characterStartled();
                        (new BallHitRestartService()).start();
                    }
                }
            };
        }
    }  
    class BallHitRestartService extends Service<Integer>{// waits for the ball to hit a target in mainGameplay
        @Override
        protected Task<Integer> createTask(){
            return new Task<Integer>(){
                @Override
                protected Integer call(){
                    try{Thread.sleep(800);}catch(Exception e){}
                    mainGameplay.ballHit=false;
                    return 0;
                }
                @Override
                protected void succeeded(){
                    ballHitService.restart();
                    mainGameplay.reset();
                }
            };
        }
    }     
    
    void pauseButton(){//adds a pause button to top of screen, and displays the pause pane, with the pause options when it is pressed
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
        
        //waits for the player to request a quit game when pauseMenu is initiated
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
        
        //waits the player to press return from the pause menu
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
                        mainGameplay.gameplayPaused=false;
                        mainGameplay.soundOn=pauseMenu.soundIsOn;
                        apane.getChildren().remove(pauseMenu);//removed the pauseMenu pane from the primaryStage, thus returning to the previous view
                        soundOn=pauseMenu.soundIsOn;
                    }
                };
            }
        }
        ReturnToGameService returnToGameService=new ReturnToGameService();//creating an object of the service that runs to check if the needs to be returned to
 
        
        //check if the user wants to restart the game from the pause menu
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
            
            mainGameplay.gameplayPaused=true;
            
            closeGameService.restart();//starts service to check if the quit button is pressed
            returnToGameService.restart();//starts service to check if return button is pressed
            restartGameService.restart();//starts service to check if the restart button is pressed
        });
        
    } 
   
}
