package Background;

import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.image.*;

public class BackgroundPane extends Pane{
    private final int WIDTH=800;
    private final int HEIGHT=600;
    

    
    AnchorPane apane=new AnchorPane();
    
    BackgroundPane(boolean professor, boolean deen, boolean trustee){
        super.setHeight(HEIGHT);
        super.setWidth(WIDTH);
        apane.setPrefSize(WIDTH, HEIGHT);
        

        try{
            String backgroundPath= getClass().getResource("GrassyBackground.png").toExternalForm();
            super.setStyle("-fx-background-image: url('"+backgroundPath+"');-fx-background-repeat: stretch;");
        }
        catch(Exception e){super.setStyle("fx-background-image: url('http://www.clker.com/cliparts/Y/f/N/P/G/E/fresh-grass-and-sky-hi.png')");}
        
        
        ImageView dunkTankView=new ImageView();
        
        if(professor){
            Image dunkTank= new Image(getClass().getResource("WaterTankCutOut.png").toExternalForm());
            dunkTankView=new ImageView(dunkTank);
        }
        if(deen){
            Image dunkTank= new Image(getClass().getResource("BubbleTankCutOut.png").toExternalForm());
            dunkTankView=new ImageView(dunkTank);
        }
        if(trustee){
            Image dunkTank= new Image(getClass().getResource("AcidTankCutOut.png").toExternalForm());
            dunkTankView=new ImageView(dunkTank);
        }

        dunkTankView.setFitHeight(175);
        dunkTankView.setFitWidth(275);
        
        
        AnchorPane.setBottomAnchor(dunkTankView, 100.0);
        AnchorPane.setRightAnchor(dunkTankView, 100.0);
        
        Image grassPatch;
        try{
            grassPatch=new Image(getClass().getResource("GrassPatch.png").toExternalForm());
        }
        catch(Exception e){grassPatch=new Image("http://images.clipartpanda.com/grass-border-clipart-acqK97xcM.png");}
        ImageView grassPatchView=new ImageView(grassPatch);
        grassPatchView.setFitHeight(35.0);
        grassPatchView.setFitWidth(75.0);
        
        AnchorPane.setBottomAnchor(grassPatchView, 130.0);
        AnchorPane.setRightAnchor(grassPatchView, 180.0);
        
        
        apane.getChildren().addAll(dunkTankView,grassPatchView);
        
        super.getChildren().add(apane);
    }
    
}
