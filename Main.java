package DunkAProf;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setTitle("Dunk-A-Prof");
        
        Scene scene=new Scene(new StartMenu());
        primaryStage.setScene(scene);
        
        primaryStage.show();
    }

   
    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
