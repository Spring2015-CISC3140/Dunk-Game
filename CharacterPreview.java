package DunkAProf;

import javafx.scene.layout.*;


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
            super.setStyle("-fx-background-color: #BDBDBD;");//this is just a test line to test canvas..should be deleted later
        }
        
    }
    
}
