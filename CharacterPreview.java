
package DunkAProf;

import javafx.scene.layout.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class CharacterPreview extends Pane{
    private final double HEIGHT=235.0;
    private final double WIDTH=235.0;
    
    
    //this is so that all of the booleans can be passed from the StartMenu class to this constructor, 
    //so that pane can show the appropriate animation
    public CharacterPreview(boolean professor, boolean deen, boolean trustee){
        super();
        super.setPrefWidth(WIDTH);
        super.setPrefHeight(HEIGHT);
        
        
        if(!professor && !deen && !trustee){
            super.setOpacity(0.0);
        }
        else{
            Image character;
            ImageView characterView=new ImageView();
            if(professor){
                character=new Image(getClass().getResource("Professor1.png").toExternalForm());
                characterView=new ImageView(character);
                characterView.setFitHeight(character.getHeight()*0.65);
                characterView.setFitWidth(character.getWidth()*0.65);               
            }
            if(deen){
                character=new Image(getClass().getResource("Deen1.png").toExternalForm());
                characterView=new ImageView(character);
                characterView.setFitHeight(character.getHeight()*0.65);
                characterView.setFitWidth(character.getWidth()*0.65);                
            }
            if(trustee){
                character=new Image(getClass().getResource("Trustee1.png").toExternalForm());
                characterView=new ImageView(character);
                characterView.setFitHeight(character.getHeight()*0.65);
                characterView.setFitWidth(character.getWidth()*0.65);                
            }
            super.getChildren().add(characterView);
        }
    }
    
}
