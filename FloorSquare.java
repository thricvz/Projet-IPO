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
        canvas.drawImage(sprite,coordinates.x*Config.blockSize,coordinates.y*Config.blockSize,Config.blockSize,Config.blockSize,null);

    };
      

}
