/*this is the main class which contains threads that allow for the transition
between the different parts of the game.
It first starts at the start menu at startMenu.java,
then it transitions to the main game at backgroundPane.java,
and then to either a win or lose menu, depending on the state of the backgroundPane
*/

package DunkAProf;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.concurrent.*;


public class DunkAProfMain extends Application {
    
    private StartMenu startMenu=new StartMenu();//startMenu is a subclass of the pane class
    private BackgroundPane backgroundPane;
    private LoseMenu loseMenu;
    private WinMenu winMenu;
    private boolean professor, dean, trustee, soundOn;
    
    private Stage primaryStage=new Stage();
    private Scene scene=new Scene(startMenu);//this is the scene that will hold each pane class
    private boolean gameIsOn=false;
    private boolean gameWon=false;
    private boolean gameLost=false;
    
    private CloseGameService closeGameService=new CloseGameService();
    private RestartGameService restartGameService=new RestartGameService();
    private SwitchToGamePane switchToGamePane=new SwitchToGamePane();
    private SwitchToWinLosePane switchToWinPane=new SwitchToWinLosePane();
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage.setResizable(false);
        this.primaryStage.setWidth(800);
        this.primaryStage.setHeight(600);
        this.primaryStage.setTitle("Dunk-A-Prof");

        this.primaryStage.setScene(scene);
        this.primaryStage.show();
        
        
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Chewy");//loading a font from google
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Sigmar+One");
        
        switchToGamePane.start();//this will allow for a thread to execute a task in the background
    }

    
        class SwitchToGamePane extends Service<Integer>{
            protected Task<Integer> createTask(){
                return new Task<Integer>(){//this task loops to check is the start game button was pressed in StartMenu scene
                    @Override
                    protected Integer call(){
                        gameIsOn=false;
                        
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
                        professor=startMenu.professor;
                        dean=startMenu.dean;
                        trustee=startMenu.trustee;
                        soundOn=startMenu.soundIsOn;
                
                        scene=new Scene(backgroundPane=new BackgroundPane(professor,dean,trustee,soundOn));
                        primaryStage.setScene(scene);
                        primaryStage.show();
                
                        switchToWinPane.restart();
                
                        closeGameService.restart();
                        restartGameService.restart();
                        
                    }
                };
            }
        }    
    
 
        class CloseGameService extends Service<Integer>{
            @Override
            protected Task<Integer> createTask(){
                return new Task<Integer>(){
                    @Override
                    protected Integer call(){
                        while((gameIsOn && !backgroundPane.closeGame) || (gameLost && !loseMenu.closeGame) || ( gameWon && !winMenu.closeGame)){
                            try{
                                Thread.sleep(500);
                            }
                            catch(Exception e){System.out.println(e);}
                        }
                        return 0;
                    }
                    @Override
                    protected void succeeded(){
                        restartGameService.cancel();
                        primaryStage.close();
                    }
                };
            }
        }
    
        class RestartGameService extends Service<Integer>{
            @Override
            protected Task<Integer> createTask(){
                return new Task<Integer>(){
                    @Override
                    protected Integer call(){
                        while((gameIsOn && !backgroundPane.restartGame)|| (gameLost && !loseMenu.restartGame) || (gameWon && !winMenu.restartGame)){//keep checking while pauseMenu's restart button doens't get pressed
                            try{
                                Thread.sleep(500);
                            }
                            catch(Exception e){System.out.println(e);}
                        }
                        return 0;
                    }
                    @Override
                    protected void succeeded(){
                        closeGameService.cancel();
                        switchToWinPane.cancel();
                        switchToGamePane=new SwitchToGamePane();
                        closeGameService=new CloseGameService();
                        switchToWinPane=new SwitchToWinLosePane();
                        backgroundPane.kill();
                        
                        if(gameLost && loseMenu.restartGame)
                            loseMenu.kill();
                        if(gameWon && winMenu.restartGame)
                            winMenu.kill();
                        
                        startMenu=new StartMenu();
                        scene=new Scene(startMenu);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        
                        switchToGamePane.restart();
                    }
                };
            }
        } 
        

        class SwitchToWinLosePane extends Service<Integer>{//this task loops to check is the start game button was pressed in StartMenu scene
            @Override 
            protected Task<Integer> createTask(){
                return new Task<Integer>(){
                    @Override
                    protected Integer call(){
                        while(gameIsOn){
                            try{
                                    Thread.sleep(500);
                            }
                            catch(Exception e){System.out.println(e);}
                            if(backgroundPane.continueToNextScene){
                                try{Thread.sleep(800);}catch(Exception e){}
                                gameIsOn=false;
                            }
                        }
                        return 0;//returns 0 if everything whent right like in c++
                    }
                    @Override
                    protected void succeeded(){
                        closeGameService.cancel();
                        restartGameService.cancel();
                        backgroundPane.kill();

                        if(backgroundPane.gameWon){
                            gameWon=true;
                            winMenu=new WinMenu(professor, dean, trustee, backgroundPane.soundOn);
                            scene=new Scene(winMenu);
                        }
                        else if(backgroundPane.gameLost){
                            gameLost=true;
                            loseMenu=new LoseMenu(professor, dean, trustee, backgroundPane.soundOn);
                            scene=new Scene(loseMenu);
                        }
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        restartGameService.restart();
                        closeGameService.restart();
                    }
                };
            }
        }
        
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
