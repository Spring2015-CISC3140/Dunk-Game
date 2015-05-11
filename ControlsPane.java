/*this is a pane that will show how to play the game
accessed from the pauseMenu
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
import javafx.scene.text.Text;

class ControlsPane extends Pane{
    boolean quitControl=false;
    
    AnchorPane apane=new AnchorPane();
    ControlsPane(){
        this.getChildren().add(apane);
        apane.setPrefSize(800, 600);
        
        apane.setStyle("-fx-background-color: #000000;");
        apane.setOpacity(1.0);
        
        Image help = new Image(getClass().getResource("Media/Help.png").toExternalForm());
        Image W = new Image(getClass().getResource("Media/W_Key.png").toExternalForm());
        Image S = new Image(getClass().getResource("Media/S_Key.png").toExternalForm());
        Image space = new Image(getClass().getResource("Media/Space_Key.png").toExternalForm());

        
        Text w_text;
        Text s_text;
        Text space_text;
        Text info;
        
        //display image unto the screen
        ImageView imageview = new ImageView();
        ImageView imageview2 = new ImageView();
        ImageView imageview3 = new ImageView();
        ImageView imageview4 = new ImageView();
        
        imageview.setImage(help);
        imageview2.setImage(W);
        imageview2.setFitWidth(50);
        imageview2.setPreserveRatio(true);
        imageview2.setSmooth(true);
        imageview2.setCache(true);
        
        imageview3.setImage(S);
        imageview3.setFitWidth(50);
        imageview3.setPreserveRatio(true);
        imageview3.setSmooth(true);
        imageview3.setCache(true);
        
        imageview4.setImage(space);
        imageview4.setFitWidth(400);
        imageview4.setPreserveRatio(true);
        imageview4.setSmooth(true);
        imageview4.setCache(true);
        
        imageview.setLayoutX(120);
        imageview2.setLayoutX(50);
        imageview2.setLayoutY(120);
        w_text = new Text(imageview2.getLayoutX()+200,imageview2.getLayoutY()+25, 
        "This Key guages the direction of the ball up-wards");
        w_text.setScaleX(1.8);
        w_text.setScaleY(1.8);
        w_text.setFill(Color.WHITE);
        imageview3.setLayoutX(50);
        imageview3.setLayoutY(220);
        s_text = new Text(imageview3.getLayoutX()+200,imageview3.getLayoutY()+25, 
        "This Key guages the direction of the ball down-wards");
        s_text.setScaleX(1.8);
        s_text.setScaleY(1.8);
        s_text.setFill(Color.WHITE);
        imageview4.setLayoutX(50);
        imageview4.setLayoutY(320);
        space_text = new Text(520,imageview4.getLayoutY()+28, 
        "This Key releases the ball");
        space_text.setScaleX(1.8);
        space_text.setScaleY(1.8);
        space_text.setFill(Color.WHITE);
     
        info = new Text(220,460,"Game Instructions: The Goal of the Game is "
                + "to simply\nHit the target with the ball 3 out of 5 times."
                + "\nEnjoy! and Good Luck!");
        info.setScaleX(2.2);
        info.setScaleY(2.2);
        info.setFill(Color.RED);
        
        
        Pane pane = new Pane();
        pane.getChildren().add(imageview);
        pane.getChildren().add(imageview2);
        pane.getChildren().add(imageview3);
        pane.getChildren().add(imageview4);
        pane.getChildren().add(w_text);
        pane.getChildren().add(s_text);
        pane.getChildren().add(space_text);
        pane.getChildren().add(info);        
        
        
        Button returnToGame=new Button("Return");
        returnToGame.setStyle("-fx-background-color: #585858; -fx-font-size: 25;");
        returnToGame.setTextFill(Color.WHITE);
        returnToGame.setPrefWidth(125);
        returnToGame.setPrefHeight(50);
        AnchorPane.setBottomAnchor(returnToGame, 25.0);
        AnchorPane.setRightAnchor(returnToGame, 5.0);
        
        returnToGame.setOnMouseClicked(e->{
            quitControl=true;
        });
        
        apane.getChildren().addAll(pane,returnToGame);
      
    }
}
