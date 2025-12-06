


public class Ball implements GraphicObject{
  private double x;
  private double y;
  private GameMediator mediator;
  private Coord gridCoords;
  private int lives;


  public Ball(GameMediator mediator){
      this.mediator = mediator;
      gridCoords = new Coord(0,0);
      lives = 3;
      x  = 0;
      y  = 0;
  }
  public void move(){};
  
  public boolean isDead(){
      return lives == 0;
  } 

  @Override
  public void drawself(){
      //left to implement
  }

}
