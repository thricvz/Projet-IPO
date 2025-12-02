


public class Ball implements GraphicObject{
  private double x;
  private double y;
  private GameMediator mediator;
  private Coord gridCoords;


  public Ball(GameMediator mediator){
      this.mediator = mediator;
      gridCoords = new Coord(0,0);
      x  = 0;
      y  = 0;
  }
  public void move();


  @Override
  public void drawself(){
      //left to implement
  }

}
