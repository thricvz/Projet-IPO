
public class GameWinningSquare extends SolidSquare {
    enum Color{
        RED,
        GREEN,
        BLUE
    }
    private Color color;
    private boolean solved;
    public GameMediator mediator;   

    public GameWinningSquare(Color color,Coord coordinates,GameMediator mediator){
       super(coordinates);
       this.color = color;
       this.solved = false;
       this.mediator = mediator;
       mediator.addNewWinningSquare(this);
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
        //just changes the skin to covered block

    } 

    public void hide(){
        //have to implement changing skin etc
    };

    public Color getColor(){
        return this.color;
    }
    
    @Override 
    public void onCollision(){
        if(!hasBeenSolved()){
          reveal();
          mediator.activatedSquare(this);
        }
    };

}
