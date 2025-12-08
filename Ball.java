import java.awt.Color;
import java.awt.Graphics2D;

public class Ball implements GraphicElement{
  private double x;
  private double y;
  private GameMediator mediator;
  private Coord gridCoords;
  private int lives;


  public Ball(){
      this.mediator = null;
      gridCoords = new Coord(0,0);
      lives = 3;
      x  = 0;
      y  = 0;
  }
  public void setMediator(GameMediator mediator){
    this.mediator = mediator;
  }
  public void move(){};
  
  public boolean isDead(){
      return lives == 0;
  } 

  @Override
  public void drawSelf(Graphics2D canvas){
    this.gridCoords.x += 10;
    canvas.setColor(Color.BLACK);
    canvas.drawRect(gridCoords.x,gridCoords.y,40,40);
    canvas.fillRect(gridCoords.x,gridCoords.y,40,40);
  }

  
}
