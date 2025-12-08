import javax.swing.*;
import java.awt.*;

class FloorSquare extends TraversableSquare {
    public FloorSquare(Coord coordinates){
        super(coordinates);
    }  
   
    @Override
    public void enter(Ball player){

    }

    @Override
    public void drawSelf(Graphics2D canvas){
        Image sprite = this.assetsLibrary.getAsset("floor");
        canvas.drawImage(sprite,0,0,50,50,null);
    };
      

}
