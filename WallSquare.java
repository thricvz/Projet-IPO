import java.awt.Graphics2D;
import java.awt.Image;

class WallSquare extends SolidSquare {
    public WallSquare(Coord coordinates){
        super(coordinates);
    }  
   
  
   @Override  
   public void onCollision(){
        //does nothing :-(
   };

   @Override  
   public void drawSelf(Graphics2D canvas){
        Image sprite = this.assetsLibrary.getAsset("wall");        
        canvas.drawImage(sprite,coordinates.x*32,coordinates.y*32,32,32,null);

            
   };
}
