package DunkAProf;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.image.*;

/*
Standard lose menu.
*/
public class LoseMenu extends Pane {

    private final double HEIGHT = 600;
    private final double WIDTH = 800;
    boolean closeGame = true;
    private AnchorPane apane = new AnchorPane();

    LoseMenu() {
        super();
        super.setPrefSize(WIDTH, HEIGHT);
        apane.setPrefSize(WIDTH, HEIGHT);
        apane.setStyle("-fx-background-color: #1C1C1C;");
        LoserBanner();
        super.getChildren().add(apane);
    }
    
    void LoserBanner(){
        StackPane banner = new StackPane();
        Label youWon = new Label("YOU LOST");
        youWon.setStyle("-fx-font-family: Sigmar+One; -fx-font-size: 50;");
        youWon.setTextFill(Color.BLUE);
        Image splash = new Image("http://www.clker.com/cliparts/o/P/T/3/R/W/blue-water-splash-few-more-drops-hi.png");
        ImageView splashView = new ImageView(splash);
        splashView.setFitHeight(100);
        splashView.setFitWidth(400);
        banner.getChildren().addAll(splashView, youWon);
        apane.getChildren().add(banner);
        AnchorPane.setTopAnchor(banner, 10.0);
        AnchorPane.setLeftAnchor(banner, 50.0);
    }

}