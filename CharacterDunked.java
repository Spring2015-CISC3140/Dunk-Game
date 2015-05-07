/*this shows a character after they were dunked

this class is called within the winMenu.java
*/
package DunkAProf;

import javafx.scene.layout.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class CharacterDunked extends Pane{
    private final double HEIGHT=235.0;
    private final double WIDTH=235.0;
    
    
    //this is so that all of the booleans can be passed from the winMenu class to this constructor, 
    //so that pane can show the appropriate animation
    public CharacterDunked(boolean professor, boolean dean, boolean trustee){
        super();
        super.setPrefWidth(WIDTH);
        super.setPrefHeight(HEIGHT);
        
        
        if(!professor && !dean && !trustee){
            super.setOpacity(0.0);
        }
        else{
            Image character;
            ImageView characterView=new ImageView();
            if(professor){
                character=new Image(getClass().getResource("Media/Professor_dunked.png").toExternalForm());
                characterView=new ImageView(character);
                characterView.setFitHeight(character.getHeight()*0.9);
                characterView.setFitWidth(character.getWidth()*0.9);               
            }
            if(dean){
                character=new Image(getClass().getResource("Media/Dean_dunked.png").toExternalForm());
                characterView=new ImageView(character);
                characterView.setFitHeight(character.getHeight()*0.9);
                characterView.setFitWidth(character.getWidth()*0.9);                
            }
            if(trustee){
                character=new Image(getClass().getResource("Media/Trustee_dunked.png").toExternalForm());
                characterView=new ImageView(character);
                characterView.setFitHeight(character.getHeight()*0.5);
                characterView.setFitWidth(character.getWidth()*0.5);                
            }
            super.getChildren().add(characterView);
        }
    }
    
}
