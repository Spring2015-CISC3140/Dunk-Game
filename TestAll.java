import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.concurrent.*;


public class TestAll extends Application {
    
    private StartMenu startMenu=new StartMenu();//startMenu is a subclass of the pane class
    private WinLoseMenu winLoseMenu=new WinLoseMenu();
    private BackgroundPane backgroundPane;
    private boolean professor, deen, trustee, soundOn;
    
    private Stage primaryStage=new Stage();
    private Scene scene=new Scene(startMenu);//this is the scene that will hold each pane class
    private boolean gameIsOn=false;
    private boolean gameWon=false;
    private boolean gameLost=false;
    
    private CloseGameService closeGameService=new CloseGameService();
    private RestartGameService restartGameService=new RestartGameService();
    private SwitchToGamePane switchToGamePane=new SwitchToGamePane();
    private SwitchToWinPane switchToWinPane=new SwitchToWinPane();
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage.setResizable(false);
        this.primaryStage.setWidth(800);
        this.primaryStage.setHeight(600);
        this.primaryStage.setTitle("Dunk-A-Prof");
        
                
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Chewy");//loading a font from google
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Sigmar+One");
        
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
        
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
                        deen=startMenu.deen;
                        trustee=startMenu.trustee;
                        soundOn=startMenu.soundIsOn;
                
                        scene=new Scene(backgroundPane=new BackgroundPane(professor,deen,trustee,soundOn));
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
                        while(gameIsOn && !backgroundPane.closeGame){
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
                        System.out.println("closing game");
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
                        while(gameIsOn && !backgroundPane.restartGame){//keep checking while pauseMenu's restart button doens't get pressed
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
                        backgroundPane.kill();
                        
                        startMenu=new StartMenu();
                        scene=new Scene(startMenu);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        
                        switchToGamePane.restart();
                    }
                };
            }
        } 
        

        class SwitchToWinPane extends Service<Integer>{//this task loops to check is the start game button was pressed in StartMenu scene
            @Override 
            protected Task<Integer> createTask(){
                return new Task<Integer>(){
                    @Override
                    protected Integer call(){
                        while(gameIsOn){
                            try{
                                    Thread.sleep(1000);
                            }
                            catch(Exception e){System.out.println(e);}
                            if(backgroundPane.continueToNextScene){
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

                        winLoseMenu.gameWon(true);
                        scene=new Scene(winLoseMenu);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                    }
                };
            }
        }
        

    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
