/*
this displays the start menu, along with the character selection.
when a character is selected it allows the user to press the start game button
*/
package DunkAProf;

import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.image.*;
import javafx.scene.shape.Circle;
import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import java.net.URL;
import javafx.concurrent.*;
import java.io.*;
import java.lang.Exception.*;



public class StartMenu extends Pane{
    
    //this is for later use with the sound on and off option
    public boolean soundIsOn=true;
    //this is for later for when you can pick your character
    public boolean professor;
    public boolean dean;
    public boolean trustee;
    //will be true when startGame button is pressed
    public boolean continueToNextScene=false;
    
    private Button startGame;//this is here so that mutiple functions can manipulate the start button being visible
    
    private AnchorPane apane=new AnchorPane();//this will be the pane we will add everything to
    
    private CharacterPreview characterPreview;//this is a pane which will allow for the preview of characters, also setCharacterPreview() will make an instence of this object
    
    //the following are going to be used to hold paths to a sound effect
        private AudioClip click;
        private AudioClip splash;
    
    
    
    public StartMenu(){
        //setting background image
        try{//try to load background image from local file, if not then from URL
            String backgroundPath= getClass().getResource("Media/Wood-HD-Wallpaper.jpg").toExternalForm();
            super.setStyle("-fx-background-image: url('"+backgroundPath+"')");
        }
        catch(Exception e){super.setStyle("-fx-background-image: url('http://burnlipo.com/wp-content/uploads/2013/06/Wood-HD-Wallpaper-10-Download.jpg')");}
        
        //setting width and height of our main AnchorPane
        apane.setPrefWidth(800);
        apane.setPrefHeight(600);
        
       logo();
       
       soundOnOff();
        
       startGameButton();
      
       characterSelection();
       
       setCharacterPreview();//sets the character preview object to the current characters selected state
       
       super.getChildren().add(apane);
       
       
       //connection of url to local file, to be opened as clicking sound
       try{
           URL clickingResource= getClass().getResource("Media/switch1.wav");//using getClass().getResource() to open a classPath file
            click=new AudioClip(clickingResource.toString());
       }
       catch(Exception e){
            System.out.println(e);
       }
       
       //connection of url to local file, to be opened as splash sound
       try{
            URL splashingResource= getClass().getResource("Media/Splash.wav");//using getClass().getResource() to open a classPath file
            splash=new AudioClip(splashingResource.toString());
       }
       catch(Exception e){
            System.out.println(e);
       }
       
    }
    
    private void logo(){
        StackPane spane=new StackPane();//this is for the logo
        
        Image splash;//if splash image couldn't load from local file then load from url
        try{
            splash=new Image(getClass().getResource("Media/Splash.png").toExternalForm());
        }
        catch(Exception e){splash=new Image("http://4.bp.blogspot.com/-2TeP5L1KLVs/UTZFMpNjp6I/AAAAAAAABlw/DeU_XmEDlNw/s1600/splash-md.png");}
        
        ImageView splashView=new ImageView(splash);
        splashView.setFitHeight(260);
        splashView.setFitWidth(260);
        spane.getChildren().add(splashView);
        
        //these is the drawing of the target board
        Circle bigCircle=new Circle(42);
        bigCircle.setFill(Color.RED);
        Circle middleCircle=new Circle(32);
        middleCircle.setFill(Color.WHITE);
        Circle smallCircle=new Circle(22);
        smallCircle.setFill(Color.RED);
        
        spane.getChildren().addAll(bigCircle,middleCircle,smallCircle);
        
        //this will be the background shading of the title
        Label titleOutline=new Label("DUNK-A-PROF");
        titleOutline.setTextFill(Color.BLACK);
        titleOutline.setStyle("-fx-font-family: Chewy; -fx-font-size: 65;");//uses font downloaded by google from the main class
        spane.getChildren().add(titleOutline);
        
        //this will be the title
        Label title=new Label("DUNK-A-PROF");
        title.setTextFill(Color.LIGHTGRAY);
        title.setStyle("-fx-font-family: Chewy; -fx-font-size: 60;");//uses font downloaded by google from the main class
        spane.getChildren().add(title);
        
        title.setOnMouseEntered( e->{
           title.setScaleX(1.5);//this will increase the title size
           title.setScaleY(1.5);
           
           titleOutline.setScaleX(1.5);//this will increase the outline of the title size
           titleOutline.setScaleY(1.5);
        });
        title.setOnMouseExited( e ->{
           title.setScaleX(1.0);//this will revert the title size to normal
           title.setScaleY(1.0);
           
           titleOutline.setScaleX(1.0);//this will revert the title outlinee to normal size
           titleOutline.setScaleY(1.0);
        });
        //adding the logo to the main AnchorPane
       apane.getChildren().add(spane);
       AnchorPane.setLeftAnchor(spane, 210.0);
       
    }
    
    private void soundOnOff(){
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
        
        //Pane countaining the sound on and sound off
        soundPane.getChildren().add(soundOnView);
        
        soundIsOn=true;
        soundPane.setOnMouseClicked(e ->{
            if(soundIsOn){
                soundPane.getChildren().remove(soundOnView);
                soundPane.getChildren().add(soundOffView);
                soundIsOn=false;
            }
            else{
                soundPane.getChildren().remove(soundOffView);
                soundPane.getChildren().add(soundOnView);
                soundIsOn=true;
            }
        });
        //adding soundPane to the main AnchorPane
       apane.getChildren().add(soundPane);
       AnchorPane.setLeftAnchor(soundPane, 10.0);
       AnchorPane.setTopAnchor(soundPane, 10.0);  
    }
    
    private void startGameButton(){
       startGame=new Button("Start Game");
       startGame.setDisable(true);//will be disabled till a character is choosen
       startGame.setPrefSize(120,50);
       startGame.setStyle("-fx-background-color: #3ADF00;");
       apane.getChildren().add(startGame);
       
       SplashSound splashService=new SplashSound();//creats an object of the inner SplashSound class which extends Service
       
       startGame.setOnAction(e ->{
           continueToNextScene=true;
           
           splashService.reset();
           splashService.start();
       });
       
       AnchorPane.setBottomAnchor(startGame, 30.0);
       AnchorPane.setRightAnchor(startGame, 20.0);
    }
    
    private void characterSelection(){
       HBox characterPane=new HBox();//pane of characters
       characterPane.setSpacing(60);
       
       
       double buttonWidth=160.0;
       double buttonHeight=100.0;
       
       //this image will be under each character name
       Image plank;//loaded from class path, if not then from url
       try{
           plank=new Image(getClass().getResource("Media/Strip-of-wood.png").toExternalForm());
       }
       catch(Exception e){plank=new Image("http://www.pd4pic.com/images/strip-of-wood-wood-border-wood-lath-wood-plank.png");}
       
       //this has to repeat 3 times for each 'button' made
       ImageView plankImage1= new ImageView(plank);
       plankImage1.setFitWidth(buttonWidth);
       plankImage1.setFitHeight(buttonHeight);
       ImageView plankImage2= new ImageView(plank);
       plankImage2.setFitWidth(buttonWidth);
       plankImage2.setFitHeight(buttonHeight);
       ImageView plankImage3= new ImageView(plank);
       plankImage3.setFitWidth(buttonWidth);
       plankImage3.setFitHeight(buttonHeight);
       
       //this will be a pane just for the professor button
       StackPane professorPane=new StackPane();
       Label professorLabel=new Label("Professor");
       professorLabel.setFont(new Font("Ariel", 15));
       professorPane.getChildren().addAll(plankImage1, professorLabel);
       
       //this will be the pane just to hold the deen button
       StackPane deenPane=new StackPane();
       Label deanLabel=new Label("Dean");
       deanLabel.setFont(new Font("Ariel", 15));
       deenPane.getChildren().addAll(plankImage2, deanLabel);
       
       //this will be the pane just to hold the trustee button
       StackPane trusteePane=new StackPane();
       Label trusteeLabel=new Label("Trustee");
       trusteeLabel.setFont(new Font("Ariel", 15));
       trusteePane.getChildren().addAll(plankImage3, trusteeLabel);
       
       //this is an inner service class with a thread inside it, to be used with professorPane, deenPane, and trusteePane
       ClickingSounds clickService=new ClickingSounds();
       
       //this is for the animation of the 'buttons' being clicked on
       professorPane.setOnMouseClicked(e ->{
           clickService.reset();//to make sure that the click service is in ready state before starting it
           professorLabel.setTextFill(Color.WHITE);
           deanLabel.setTextFill(Color.BLACK);
           trusteeLabel.setTextFill(Color.BLACK);
           professor=true;
           dean=false;
           trustee=false;
           startGame.setDisable(false);
           clickService.start();//starts the thread in the click service class, to make a sound
           
           setCharacterPreview();
        });
       
        deenPane.setOnMouseClicked(e ->{
           clickService.reset();//to make sure that the click service is in ready state before starting it
           professorLabel.setTextFill(Color.BLACK);
           deanLabel.setTextFill(Color.WHITE);
           trusteeLabel.setTextFill(Color.BLACK);
           dean=true;
           professor=false;
           trustee=false;
           startGame.setDisable(false);
           clickService.start();//starts the thread in the click service class, to make a sound
           
           setCharacterPreview();
        });
        
        trusteePane.setOnMouseClicked(e ->{
           clickService.reset();//to make sure that the click service is in ready state before starting it
           professorLabel.setTextFill(Color.BLACK);
           deanLabel.setTextFill(Color.BLACK);
           trusteeLabel.setTextFill(Color.WHITE);
           dean=false;
           professor=false;
           trustee=true;
           startGame.setDisable(false);
           clickService.start();//starts the thread in the click service class, to make a sound
           
           setCharacterPreview();
        });
       
       
       
       characterPane.getChildren().addAll(professorPane, deenPane, trusteePane);
       
       AnchorPane.setLeftAnchor(characterPane, 85.0);
       AnchorPane.setTopAnchor(characterPane, 260.0);
       apane.getChildren().add(characterPane);
        
    }
    
    void setCharacterPreview(){
        //we first must check if the characterPreview node was alreaded added to the main AnchorPane, and delete it if it was
        if(apane.getChildren().contains(characterPreview)){
            apane.getChildren().remove(characterPreview);
        }
        
       characterPreview=new CharacterPreview(professor, dean, trustee);
       apane.getChildren().add(characterPreview);
       AnchorPane.setBottomAnchor(characterPreview, 30.0);
       AnchorPane.setLeftAnchor(characterPreview, 315.0);
        
    }
    
    
    //this is a internal class for handeling a thread/task
    private class ClickingSounds extends Service<Integer>{
        boolean playSound=true;
        @Override
        protected Task<Integer> createTask(){
            return new Task<Integer>(){
                @Override
                protected Integer call(){
                    if(soundIsOn){     //if sound if off there will be no clicking
                        try{
                            click.play(1.0);
                            playSound=false;
                        }
                        catch(Exception e){
                                System.out.println(e);
                                }
                    }
                    return 0;//to signify everything went right like in c++
                }
            };
        };
    }
    private class SplashSound extends Service<Integer>{
        boolean playSound=true;
        @Override
        protected Task<Integer> createTask(){
            return new Task<Integer>(){
                @Override
                protected Integer call(){
                    if(soundIsOn){     //if sound if off there will be no clicking
                        try{
                            splash.play(1.0);
                            playSound=false;
                        }
                        catch(Exception e){
                                System.out.println(e);
                                }
                    }
                    return 0;//to signify everything went right like in c++
                }
            };
        };         
    }
    
   
}

