/*this shows a character if the player didn't manage to dunk a character

this class is called within the winMenu.java
*/
package DunkAProf;

import javafx.scene.layout.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.text.*;

public class CharacterNotDunked extends AnchorPane{
    private final double HEIGHT=350.0;
    private final double WIDTH=275.0;
    
    
    //this is so that all of the booleans can be passed from the winMenu class to this constructor, 
    //so that pane can show the appropriate animation
    public CharacterNotDunked(boolean professor, boolean dean, boolean trustee){
        super();
        super.setPrefWidth(WIDTH);
        super.setPrefHeight(HEIGHT);
                
        if(!professor && !dean && !trustee){
            super.setOpacity(0.0);
        }
        else{
            Image character;
            ImageView characterView=new ImageView();
            
            Label taunt=new Label("");
            
            if(professor){
                character=new Image(getClass().getResource("Media/Professor1.png").toExternalForm());
                characterView=new ImageView(character);
                characterView.setFitHeight(character.getHeight()*0.8);
                characterView.setFitWidth(character.getWidth()*0.8);
                taunt=new Label("   F");
            }
            if(dean){
                character=new Image(getClass().getResource("Media/Dean1.png").toExternalForm());
                characterView=new ImageView(character);
                characterView.setFitHeight(character.getHeight()*0.8);
                characterView.setFitWidth(character.getWidth()*0.8); 
                taunt=new Label("Ha Ha");
            }
            if(trustee){
                character=new Image(getClass().getResource("Media/Trustee1.png").toExternalForm());
                characterView=new ImageView(character);
                characterView.setFitHeight(character.getHeight()*0.8);
                characterView.setFitWidth(character.getWidth()*0.8);  
                taunt=new Label("Loser");
            }
            
            this.getChildren().add(characterView);
            AnchorPane.setBottomAnchor(characterView, 0.0);
            AnchorPane.setLeftAnchor(characterView, 0.0);

            
            Image speechBubble=new Image(getClass().getResource("Media/SpeechBubble.png").toExternalForm());
            ImageView speechBubbleView=new ImageView(speechBubble);
            speechBubbleView.setFitWidth(speechBubble.getWidth()*0.5);
            speechBubbleView.setFitHeight(speechBubble.getWidth()*0.5);
            this.getChildren().add(speechBubbleView);
            AnchorPane.setRightAnchor(speechBubbleView, 0.0);
            AnchorPane.setTopAnchor(speechBubbleView, 0.0);
            
            taunt.setStyle("-fx-font-size: 20");
            this.getChildren().add(taunt);
            AnchorPane.setTopAnchor(taunt, 38.0);
            AnchorPane.setLeftAnchor(taunt, 175.0);            
        }
    }
    
}
