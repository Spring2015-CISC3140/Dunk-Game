package PauseMenu;

import com.sun.prism.paint.Color;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.concurrent.*;


public class TestPause extends Application {
    boolean soundIsOn=true;
    
    private PauseMenu pauseMenu=new PauseMenu(soundIsOn);//creating an object of the pause menu
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);//so that no one can resize the window
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setTitle("Dunk-A-Prof");
        
        AnchorPane apane =new AnchorPane();//main pane that everything will be attached to 
        apane.setStyle("-fx-background-color: #F8F8F8;");
        
        //loading a pause icon
        Image pause= new Image("http://images.clipartpanda.com/pause-clipart-media-playback-pause_Clip_Art.png");
        ImageView pauseView=new ImageView(pause);
        pauseView.setFitHeight(50);
        pauseView.setFitWidth(50);
        
        
        apane.getChildren().add(pauseView);//attaches pause icon to main pane
        AnchorPane.setTopAnchor(pauseView, 10.0);
        AnchorPane.setLeftAnchor(pauseView, 10.0);
        
        
        Scene scene=new Scene(apane);//creats a scene that contains the main pane "apane" as its node
        
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Luckiest+Guy");//loads font from google
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        
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
                        primaryStage.close();//closed the stage, thus quiting the game
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
                        soundIsOn=pauseMenu.soundIsOn;
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
                        //this is incomplete and will edited when attached to the whole program
                    }
                };
            }
        }
        RestartGameService restartGameService=new RestartGameService();//creats a service, when activated will check if the game needs restarting
        
        
        pauseView.setOnMouseClicked(e ->{
            pauseMenu.soundIsOn=this.soundIsOn;//you must tell the pause menu if the sound is on before adding it to the scene
            pauseMenu.pauseInProgress=true;//you must tell pause menu that the pause is in progress before adding it to the scene
            apane.getChildren().add(pauseMenu);
            
            closeGameService.restart();//starts service to check if the quit button is pressed
            returnToGameService.restart();//starts service to check if return button is pressed
            restartGameService.restart();//starts service to check if the restart button is pressed
        });
        
    }
  
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
