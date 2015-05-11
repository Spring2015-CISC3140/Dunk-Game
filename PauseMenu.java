/*this is the pause menu that is to be used only by backgroundPane.java
the pause menu allows for a restart, quit, or return to game,
and also a sound off/on option
*/
package DunkAProf;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import javafx.concurrent.*;
import javafx.scene.media.*;
import java.net.*;

public class PauseMenu extends Pane {
    private final double HEIGHT=600;//height of the pause menu
    private final double WIDTH=800;//width of the pause menu
   
    public boolean pauseInProgress=false;//pauseInProgress must be set to true by the holder of the PauseMenu object before making it visible
    public boolean soundIsOn;//must be set by the the holder of the pauseMenu object before making it visible
    public boolean quitGame=false;//is set true if the quit game button is pressed
    public boolean startGameOver=false;//is set true if the restart button is pressed
            
    private AnchorPane apane=new AnchorPane();//this will be the pane every component of the pause Menu is attached to
    
    private URL blopResource;//resource for the blop sound 
    private AudioClip blop;//plays blop sound
    
    private ControlsPane controlsPane;
    

    PauseMenu(boolean soundIsOn){
        super();
        
        this.soundIsOn=soundIsOn;
        pauseInProgress=true;
        
        apane.setPrefSize(WIDTH, HEIGHT);//sets size of pause menu
        apane.setStyle("-fx-background-color: #A4A4A4;");//background color of pane is gray
        apane.setOpacity(0.7);//the pane will be slightly transparent
        
        pausedBanner();//adds the banner that says game paused
           
        options();//adds sound on/off, quit, refresh, and return buttons
        
        super.getChildren().add(apane);
        
        try{//this gets a blop loaded and ready to be used when needed by a Service inner class
            blopResource=getClass().getResource("Media/Blop.wav");
            blop=new AudioClip(blopResource.toString());
        }
        catch(Exception e){System.out.println(e);}
    }
    
    void pausedBanner(){//this sets a banner that says "Game Paused"
        HBox hBox=new HBox();//this will hold the banner with the words "Game Paused"
        hBox.setPrefWidth(WIDTH);//makes a vertical pane be the width of the pause menu
        hBox.setStyle("-fx-background-color: #585858;");//sets background to a darker grey
        hBox.setOpacity(0.9);
        
        Label paused=new Label("Game Paused");
        paused.setStyle( "-fx-font-family: Luckiest+Guy; -fx-font-size: 50;");//this font is loaded from google
        paused.setTextFill(Color.BLACK);
        paused.setOpacity(1.0);
        
        hBox.getChildren().add(paused);
        
        apane.getChildren().add(hBox);//adds the vertical pane holding the "Game Paused" Banner to the pane that holds the whole pauseMenu
        AnchorPane.setTopAnchor(hBox, 175.0);
    }
    
    void options(){
        final double BUTTONWIDTH=100;//for ensuring that all buttons are the same width in this method
        
        BlopSoundService blopSound=new BlopSoundService();//creats an object of the inner service class for playing a bloping sound
        
        HBox hBox=new HBox();//this will hold a 
        hBox.setPrefHeight(100);
        hBox.setSpacing(30);
        
        //adding sound on off 
        Pane soundPane=new Pane();
        
        //sound on image
        Image soundOn;//load soundOn image from ULR, if not found in Local file
        try{
            soundOn=new Image(getClass().getResource("Media/SoundOn.png").toExternalForm());
        }
        catch(Exception e){soundOn=new Image("https://cdn4.iconfinder.com/data/icons/defaulticon/icons/png/128x128/media-volume-2.png");} 
        
        ImageView soundOnView=new ImageView(soundOn);
        soundOnView.setFitWidth(40);
        soundOnView.setFitHeight(40);
        
        //sound off image
        Image soundOff;//if  SoundOff image can't load from local file, then it is loaded from a url
        try{
            soundOff=new Image(getClass().getResource("Media/SoundOff.png").toExternalForm());
        }
        catch(Exception e){soundOff=new Image("https://cdn4.iconfinder.com/data/icons/defaulticon/icons/png/128x128/media-volume-0.png");}
        
        ImageView soundOffView=new ImageView(soundOff);
        soundOffView.setFitWidth(40);
        soundOffView.setFitHeight(40);
        
        
        if(soundIsOn){//of sound is on then add the icon that shows sound is on
            soundPane.getChildren().add(soundOnView);
        }
        else{//if sound if off then add the icon that shows sound off 
            soundPane.getChildren().add(soundOffView);
        }
        
        soundPane.setOnMouseClicked(e ->{
            if(soundIsOn){//if the icon is sound on and clicked it will change to sound off icon
                soundPane.getChildren().remove(soundOnView);
                soundPane.getChildren().add(soundOffView);
                soundIsOn=false;
            }
            else{//if the icon is sound off and clicked it will change to sound on icon
                soundPane.getChildren().remove(soundOffView);
                soundPane.getChildren().add(soundOnView);
                soundIsOn=true;
            }
        });
        
        
        //adding quit game button
        Pane quitPane=new Pane();
        quitPane.setOpacity(1.0);
        
        //loading a picture of a red x to add to the button
        Image xImage;//try loading it from local classpath, and if not then from url
        try{
            xImage=new Image(getClass().getResource("Media/X.png").toExternalForm());
        }
        catch(Exception e){xImage=new Image("https://openclipart.org/image/800px/svg_to_png/15815/Arnoud999-Right-or-wrong-5.png");}
        
        ImageView xView=new ImageView(xImage);
        xView.setFitHeight(20);
        xView.setFitWidth(20);
        
        Button quit=new Button("Quit", xView);
        quit.setStyle("-fx-background-color: #585858;");
        quit.setTextFill(Color.WHITE);
        quit.setPrefWidth(BUTTONWIDTH);
        
        quitPane.getChildren().add(quit);
        
        quit.setOnMouseClicked( e->{
            quitGame=true;
            if(soundIsOn){blopSound.restart();}//if sound if on, when button is clicked, a blop sound if played
        });
        
        
        //adding return to Game button
        Pane returnPane=new Pane();
        
        //loading a picture of a green arrow to add to the button
        Image returnImage;//try to load image from local file, if not then from url
        try{
            returnImage=new Image(getClass().getResource("Media/return.png").toExternalForm());
        }
        catch(Exception e){returnImage=new Image("https://openclipart.org/image/800px/svg_to_png/17018/jean-victor-balin-icon-arrow-left-green.png");}
        
        ImageView returnView=new ImageView(returnImage);
        returnView.setFitHeight(20);
        returnView.setFitWidth(20);
        
        Button returnToGame=new Button("Return", returnView);
        returnToGame.setStyle("-fx-background-color: #585858;");
        returnToGame.setTextFill(Color.WHITE);
        returnToGame.setPrefWidth(BUTTONWIDTH);
        
        returnPane.getChildren().add(returnToGame);
        
        returnToGame.setOnMouseClicked(e->{
            pauseInProgress=false;
            if(soundIsOn){blopSound.restart();}//if sound if on, when button is clicked, a blop sound if played
        });

        
        //restart game button
        Pane restartPane=new Pane();
        
        //loading a picture of a green return/refresh arrow to add to button
        Image restartImage;//try to get restart image from class path, if not found then load it from url
        try{
            restartImage=new Image(getClass().getResource("Media/restart.png").toExternalForm());
        }
        catch(Exception e){restartImage=new Image("https://openclipart.org/image/800px/svg_to_png/212123/rodentia-icons_view-refresh.png");}
        
        ImageView restartView=new ImageView(restartImage);
        restartView.setFitHeight(20);
        restartView.setFitWidth(20);
        
        Button restartGame=new Button("Restart", restartView);
        restartGame.setStyle("-fx-background-color: #585858;");
        restartGame.setTextFill(Color.WHITE);
        restartGame.setPrefWidth(BUTTONWIDTH);

        restartPane.getChildren().add(restartGame);
        
        restartGame.setOnMouseClicked(e->{
            startGameOver=true;
            if(soundIsOn){blopSound.restart();}//if sound if on, when button is clicked, a blop sound if played
        });
        
        
        //adding a button to view the controls
        Button controls=new Button("Controls");
        controls.setStyle("-fx-background-color: #585858;");
        controls.setTextFill(Color.WHITE);
        controls.setPrefWidth(BUTTONWIDTH);
        controls.setPrefHeight(31);
        
        controls.setOnMouseClicked(e->{
            controlsPane=new ControlsPane();
                apane.getChildren().add(controlsPane);
                (new QuitControlsService()).restart();
        });
        
        //adding all option buttons to a horizontal pane
        hBox.getChildren().addAll(soundPane, quitPane, returnToGame, restartGame, controls);
        
        apane.getChildren().add(hBox);//adding the horizontal pane of options to the main pane holding the pause menu
        AnchorPane.setBottomAnchor(hBox, 250.0);
        AnchorPane.setLeftAnchor(hBox, 100.0);
    }
    
    //this is a service inner class that plays a blop sound when the service is started
    class BlopSoundService extends Service<Integer>{
        @Override
        protected Task<Integer> createTask(){
            return new Task<Integer>(){
                @Override
                protected Integer call(){
                    blop.play(1.0);//blop is downloaded earlier from a class path, in the defult constructor
                    return 0;
                }
            };  
        } 
    } 
    class QuitControlsService extends Service<Integer>{
        @Override
        protected Task<Integer> createTask(){
            return new Task<Integer>(){
                @Override
                protected Integer call(){
                    while(!controlsPane.quitControl){
                        try{Thread.sleep(100);}catch(Exception e){}
                    }
                    return 0;
                }
                protected void succeeded(){
                    apane.getChildren().remove(controlsPane);
                }
            };  
        } 
    }  
}

