package DunkAProf;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.image.*;

/*
Standard win menu.
*/
public class WinMenu extends Pane {

    private final double HEIGHT = 600;
    private final double WIDTH = 800;
    boolean closeGame = false;
    boolean restartGame=false;
    private AnchorPane apane = new AnchorPane();

    WinMenu() {
        super();
        super.setPrefSize(WIDTH, HEIGHT);
        apane.setPrefSize(WIDTH, HEIGHT);
        apane.setStyle("-fx-background-color: #1C1C1C;");
        winnerBanner();
        super.getChildren().add(apane);
        
        //Adds Restart button and quit game buttons.
        Button RestartGame = new Button("Restart?");
        RestartGame.setPrefSize(120, 50);
        RestartGame.setStyle("-fx-background-color: #3ADF00;");
        RestartGame.setTextFill(Color.BLUE);
        
        RestartGame.setOnMouseClicked(e ->{
            System.out.println("test");
            restartGame=true;
        });
        
        AnchorPane.setBottomAnchor(RestartGame, 30.0);
        AnchorPane.setRightAnchor(RestartGame, 20.0);
        
        Button QuitGame = new Button("Quit?");
        QuitGame.setPrefSize(120, 50);
        QuitGame.setStyle("-fx-background-color: #3ADF00;");
        QuitGame.setTextFill(Color.BLUE);
        
        QuitGame.setOnMouseClicked(e ->{
            System.out.println("test");
            closeGame=true;
        });
        
        AnchorPane.setBottomAnchor(QuitGame, 30.0);
        AnchorPane.setLeftAnchor(QuitGame, 20.0);
        
        apane.getChildren().add(RestartGame);
        apane.getChildren().add(QuitGame);
    }

    void winnerBanner() {
        StackPane banner = new StackPane();
        Label youWon = new Label("YOU WON");
        youWon.setStyle("-fx-font-family: Sigmar+One; -fx-font-size: 50;");
        youWon.setTextFill(Color.GOLD);
        Image splash = new Image("http://www.clker.com/cliparts/o/P/T/3/R/W/blue-water-splash-few-more-drops-hi.png");
        ImageView splashView = new ImageView(splash);
        splashView.setFitHeight(100);
        splashView.setFitWidth(400);
        banner.getChildren().addAll(splashView, youWon);
        apane.getChildren().add(banner);
        //Sets to middle of pane.
        AnchorPane.setTopAnchor(banner, 200.0);
        AnchorPane.setLeftAnchor(banner, 200.0);
        
    }
                
}
