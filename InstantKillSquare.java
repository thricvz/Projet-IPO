import java.awt.Graphics2D;

public class InstantKillSquare extends SolidSquare implements GraphicElement {
    public InstantKillSquare(Coord coordinates){
        super(coordinates);

    }

    @Override
    public void onCollision(){

    }

    @Override
    public void drawSelf(Graphics2D canvas){
        Image sprite = this.assetsLibrary.getAsset("instant_kill_square");        
        canvas.drawImage(sprite,coordinates.x*Config.blockSize,coordinates.y*Config.blockSize,Config.blockSize,Config.blockSize,null);

    }
}
