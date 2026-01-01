import java.awt.Graphics2D;
import java.awt.Image;

enum Color{
        RED,
        GREEN,
        BLUE;

        public static String getColorName(Color color){
            switch(color){
                case RED:
                    return "red";
                case BLUE:
                    return "blue";
                case GREEN:
                    return "green";
            }
            return "";
        }
}

public class GameWinningSquare extends SolidSquare {
    enum State{
        HIDDEN,
        REVEALED
    }
    private Color color;
    private boolean solved;
    private String sprite;
    private GameMediator mediator;   
    private State state;
    
    public GameWinningSquare(Color color,Coord coordinates,GameMediator mediator){
       super(coordinates);
       this.color = color;
       this.solved = false;
       this.mediator = mediator;
       mediator.addNewWinningSquare(this);
       this.state = State.HIDDEN;
       this.hide();
    } 

    public void markAsSolved(){
      solved = true;
    };
    public boolean hasBeenSolved(){
        return this.solved;
    };
   
    //functions that handle the skin of the actual block    
    private void reveal(){
      this.sprite = "puzzlebox_" + Color.getColorName(this.color) + "_revealed";
      this.state = State.REVEALED;
      
    } 

    public void hide(){
        this.sprite = "puzzlebox_hidden";
        this.state = State.HIDDEN;

    };

    public Color getColor(){
        return this.color;
    }
    @Override 
    public void drawSelf(Graphics2D canvas){
        Image sprite = this.assetsLibrary.getAsset(this.sprite);
        canvas.drawImage(sprite,coordinates.x*Config.blockSize,coordinates.y*Config.blockSize,Config.blockSize,Config.blockSize,null);
    }
    @Override 
    public void onCollision(){
        if(hasBeenSolved()){
            reveal();
            return;
        }else if(state == State.HIDDEN){
            reveal();
            mediator.activatedSquare(this);
        }else{
            hide();
        }
    };

}
