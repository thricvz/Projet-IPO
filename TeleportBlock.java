import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Image;

public class TeleportBlock extends SolidSquare implements GraphicElement {

    private GameMediator mediator;
    private Random coordGenerator;

    public TeleportBlock(Coord coord,GameMediator mediator){
        super(coord);
        this.mediator = mediator;
        this.coordGenerator = new Random();

    }   

    @Override
    public void onCollision(){
        BallData data = this.mediator.getPlayerCharacteristics();
        data.position = randomPosition();
        //data.speed = new Vec();
        this.mediator.modifyPlayer(data);
    }

    private Vec randomPosition(){
        Coord randomPosition = new Coord(
            this.coordGenerator.nextInt(Config.levelWidthInBlocks+1),
            this.coordGenerator.nextInt(Config.levelHeightInBlocks+1)
        );

        Square selectedSquare = this.mediator.getSquareObject(randomPosition);

        if(selectedSquare.isTraversable()){
            Vec position = new Vec(randomPosition.x,randomPosition.y);
            return position;
        }

        return randomPosition();
    }

    @Override
    public void drawSelf(Graphics2D canvas) {
        Image sprite = this.assetsLibrary.getAsset("teleport");        
        canvas.drawImage(sprite,coordinates.x*Config.blockSize,coordinates.y*Config.blockSize,Config.blockSize,Config.blockSize,null);
    }
}
