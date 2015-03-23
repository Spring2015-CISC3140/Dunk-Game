package DunkAProf;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.concurrent.*;


public class Main extends Application {
    
    private StartMenu startMenu=new StartMenu();//startMenu is a subclass of the pane class
    private WinLoseMenu winLoseMenu=new WinLoseMenu();
    
    private Scene scene=new Scene(startMenu);//this is the scene that will hold each pane class
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setTitle("Dunk-A-Prof");
        
                
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Chewy");//loading a font from google
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Sigmar+One");
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Task<Integer> task=new Task<Integer>(){//this task loops to check is the start game button was pressed in StartMenu scene
            @Override
            protected Integer call(){
                boolean gameIsOn=false;
                while(!gameIsOn){
                    try{
                            Thread.sleep(1000);
                        }
                    catch(Exception e){System.out.println(e);}
                    if(startMenu.continueToNextScene){
                        gameIsOn=true;
                    }
                }
                return 0;//returns 0 if everything whent right like in c++
            }
            @Override
            protected void succeeded(){
                scene=new Scene(winLoseMenu);
                winLoseMenu.gameWon(true);
                primaryStage.setScene(scene);
                primaryStage.show();
            }
        };
        Thread thread=new Thread(task);//this will allow for a thread to execute a task in the background
        thread.setDaemon(true);
        thread.start();        

    }

   
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}

