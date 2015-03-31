package Background;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class TestBakcground extends Application {
    boolean professor=false;
    boolean deen=false;
    boolean trustee=false;
    BackgroundCanvas background;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setTitle("Dunk-A-Prom");
        
        deen=true;
        background=new BackgroundCanvas(professor, deen, trustee);
        Scene scene=new Scene(background);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
