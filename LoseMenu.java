/*this is the page that will be displayed if the game is lost,
contains a 'you lose' messege and a character mocking you for losing,
and the buttons to close or restart the game
*/

package DunkAProf;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import javafx.scene.media.*;
import java.net.*;
import javafx.scene.media.AudioClip;

public class LoseMenu extends Pane {

    private final double HEIGHT = 600;
    private final double WIDTH = 800;
    boolean closeGame = false;
    boolean restartGame=false;
    private AnchorPane apane = new AnchorPane();
    private boolean professor, dean, trustee;
    private boolean soundOn;
    private AudioClip booSound;
    
    LoseMenu(boolean professor, boolean dean, boolean trustee, boolean soundOn) {
        super();
        super.setPrefSize(WIDTH, HEIGHT);
        apane.setPrefSize(WIDTH, HEIGHT);
        apane.setStyle("-fx-background-color: #1C1C1C;");
        
        this.soundOn=soundOn;
        
        if(soundOn){
            URL booResource=getClass().getResource("Media/Crowd Boo.mp3");
            booSound=new AudioClip(booResource.toString());
            booSound.play(0.5);
        }
        
        LoserBanner();
        super.getChildren().add(apane);
        
        this.professor=professor;
        this.dean=dean;
        this.trustee=trustee;        
        
        Image restartImage;//try to get restart image from class path, if not found then load it from url
        try{
            restartImage=new Image(getClass().getResource("Media/restart.png").toExternalForm());
        }
        catch(Exception e){restartImage=new Image("https://openclipart.org/image/800px/svg_to_png/212123/rodentia-icons_view-refresh.png");}
        
        ImageView restartView=new ImageView(restartImage);
        restartView.setFitHeight(20);
        restartView.setFitWidth(20);        
        
        //Adds Restart button and quit game buttons.
        Button RestartGame = new Button("Restart?",restartView);
        RestartGame.setPrefSize(120, 50);
        RestartGame.setStyle("-fx-background-color: #585858;");
        RestartGame.setTextFill(Color.WHITE);
        
        RestartGame.setOnMouseClicked(e ->{
            restartGame=true;
        });        
        
        AnchorPane.setBottomAnchor(RestartGame, 30.0);
        AnchorPane.setRightAnchor(RestartGame, 20.0);
        
        //loading a picture of a red x to add to the button
        Image xImage;//try loading it from local classpath, and if not then from url
        try{
            xImage=new Image(getClass().getResource("Media/X.png").toExternalForm());
        }
        catch(Exception e){xImage=new Image("https://openclipart.org/image/800px/svg_to_png/15815/Arnoud999-Right-or-wrong-5.png");}
        
        ImageView xView=new ImageView(xImage);
        xView.setFitHeight(20);
        xView.setFitWidth(20);                
        
        Button QuitGame = new Button("Quit?",xView);
        QuitGame.setPrefSize(120, 50);
        QuitGame.setStyle("-fx-background-color: #585858;");
        QuitGame.setTextFill(Color.WHITE);
        
        QuitGame.setOnMouseClicked(e ->{
            closeGame=true;
        });
        
        AnchorPane.setBottomAnchor(QuitGame, 30.0);
        AnchorPane.setLeftAnchor(QuitGame, 20.0);
        apane.getChildren().add(RestartGame);
        apane.getChildren().add(QuitGame);
        
        characterView(professor,dean,trustee);
    }
    
    void LoserBanner(){
        StackPane banner = new StackPane();
        Label youWon = new Label("YOU LOST");
        youWon.setStyle("-fx-font-family: Sigmar+One; -fx-font-size: 50;");
        youWon.setTextFill(Color.RED);
        Image splash = new Image("http://www.clker.com/cliparts/o/P/T/3/R/W/blue-water-splash-few-more-drops-hi.png");
        ImageView splashView = new ImageView(splash);
        splashView.setFitHeight(100);
        splashView.setFitWidth(400);
        banner.getChildren().addAll(splashView, youWon);
        apane.getChildren().add(banner);
        //Sets to middle of pane.
        AnchorPane.setTopAnchor(banner, 75.0);
        AnchorPane.setLeftAnchor(banner, 200.0);
    }

    void characterView(boolean professor, boolean dean, boolean trustee){
        CharacterNotDunked character=new CharacterNotDunked(professor, dean, trustee);
        apane.getChildren().add(character);
        AnchorPane.setTopAnchor(character, 225.0);
        AnchorPane.setLeftAnchor(character, 325.0);        
    }    
    
    void kill(){
        if(soundOn){
            if(booSound.isPlaying()){
            booSound.stop();
            }
        }
    }
}
