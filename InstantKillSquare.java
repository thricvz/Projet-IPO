import java.awt.Graphics2D;
import java.awt.Image;

public class InstantKillSquare extends SolidSquare implements GraphicElement {
    private GameMediator mediator;
    public InstantKillSquare(Coord coordinates,GameMediator mediator){
        super(coordinates);
        this.mediator = mediator;
    }

    @Override
    public void onCollision(){
        BallData playerState = this.mediator.getPlayerCharacteristics();
        playerState.lives = 0;
        this.mediator.modifyPlayer(playerState);
    }

    @Override
    public void drawSelf(Graphics2D canvas){
        Image sprite = this.assetsLibrary.getAsset("instant_kill_square");        
        canvas.drawImage(sprite,coordinates.x*Config.blockSize,coordinates.y*Config.blockSize,Config.blockSize,Config.blockSize,null);

    }
}
