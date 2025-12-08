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
    
    private Color color;
    private boolean solved;
    private String sprite;
    private GameMediator mediator;   
    
    public GameWinningSquare(Color color,Coord coordinates,GameMediator mediator){
       super(coordinates);
       this.color = color;
       this.solved = false;
       this.mediator = mediator;
       mediator.addNewWinningSquare(this);
       this.reveal();
    } 

    public void markAsSolved(){
      reveal();
      solved = true;
    };
    public boolean hasBeenSolved(){
        return this.solved;
    };
   
    //functions that handle the skin of the actual block    
    private void reveal(){
        this.sprite = "puzzlebox_" + Color.getColorName(this.color) + "_revealed";
    } 

    public void hide(){
        this.sprite = "puzzlebox_hidden";
    };

    public Color getColor(){
        return this.color;
    }
    @Override 
    public void drawSelf(Graphics2D canvas){
        Image sprite = this.assetsLibrary.getAsset(this.sprite);
        canvas.drawImage(sprite,coordinates.x*32,coordinates.y*32,32,32,null);
    }
    @Override 
    public void onCollision(){
        if(!hasBeenSolved()){
          reveal();
          mediator.activatedSquare(this);
        }
    };

}
