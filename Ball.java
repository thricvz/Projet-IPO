import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.lang.Math;
import java.util.ArrayList;



public class Ball implements GraphicElement,MouseMotionListener{
  private Vec pos;
  private Vec speed;
  private Vec direction;
  private double radius;
  private Coord lastMousePos;
  private GameMediator mediator;
  private int lives;


  public Ball(){
      this.mediator = null;
      this.lives = 3;
      this.pos = new Vec(10*Config.blockSize,10*Config.blockSize);
      this.speed = new Vec();
      this.direction = new Vec();
      this.lastMousePos = null;
      this.radius = 1;
  }

  public void setMediator(GameMediator mediator){
    this.mediator = mediator;
  }

  public void move(){
    double friction = 7;
    this.pos.add(this.speed);

    if(this.speed.norm() < friction){
      this.speed = new Vec();
      this.direction = new Vec();
    }else{
      Vec totalFriction = this.direction.copy();
      totalFriction.mult(friction);
      totalFriction.negative();
      this.speed.add(totalFriction);


      this.checkCollision();
    }
  };

  private void checkCollision(){
    ArrayList<Square> collidedSquares = new ArrayList<Square>();
    Coord currentSquarePos = this.getPosBoard();
    Vec collisionVec = new Vec(1,1);
    double pushForce = 3 * radius;
    
    // right
    if((this.pos.x + this.radius ) > ((currentSquarePos.x)*Config.blockSize) ){
      Square collidedSquare = mediator.getSquareObject(
        new Coord(currentSquarePos.x+1,currentSquarePos.y)
      );

      //this.pos.x -= pushForce;
      if(!collidedSquare.isTraversable()){
          collidedSquares.add(collidedSquare);
          collisionVec.x *=-1;
      }

    }

    if((this.pos.y + this.radius ) > ((currentSquarePos.y)*Config.blockSize) ){
      Square collidedSquare = mediator.getSquareObject(
        new Coord(currentSquarePos.x,currentSquarePos.y+1)
      );

      //this.pos.x -= pushForce;
      if(!collidedSquare.isTraversable()){
          collidedSquares.add(collidedSquare);
          collisionVec.y *=-1;
      }

    }
    //new file
    if((this.pos.x - this.radius ) < ((currentSquarePos.x)*Config.blockSize) ){
      Square collidedSquare = mediator.getSquareObject(
        new Coord(currentSquarePos.x,currentSquarePos.y)
      );

      //this.pos.x -= pushForce;
      if(!collidedSquare.isTraversable()){
          collidedSquares.add(collidedSquare);
          collisionVec.x *=-1;
      }

    }

    if((this.pos.y - this.radius ) < ((currentSquarePos.y)*Config.blockSize) ){
      Square collidedSquare = mediator.getSquareObject(
        new Coord(currentSquarePos.x,currentSquarePos.y)
      );

      //this.pos.x -= pushForce;
      if(!collidedSquare.isTraversable()){
          collidedSquares.add(collidedSquare);
          collisionVec.y *=-1;
      }

    }

    this.speed.mult(collisionVec);
    this.direction.mult(collisionVec);
    
    
  }

  public Coord getPosBoard(){
    Coord currentPosition = new Coord(
      (int) (this.pos.x/Config.blockSize),
      (int) (this.pos.y/Config.blockSize)
    );

    return currentPosition;
  }
  
  public boolean isDead(){
      return lives == 0;
  } 

  @Override
  public void drawSelf(Graphics2D canvas){
    int x = (int) this.pos.x;
    int y = (int) this.pos.y;

    int draw_x = x - Config.blockSize/2;
    int draw_y = y - Config.blockSize/2;

    canvas.setColor(Color.BLACK);
    canvas.drawOval(draw_x,draw_y,Config.blockSize,Config.blockSize);
    canvas.fillOval(draw_x,draw_y,Config.blockSize,Config.blockSize);

    
  }

  @Override
  public void mouseDragged(MouseEvent event){};


  @Override
  public void mouseMoved(MouseEvent event){      
    if(this.lastMousePos == null){
      lastMousePos = new Coord(event.getX(),event.getY());
    }

    Vec mouseVec = new Vec(
      event.getX() - lastMousePos.x,
      event.getY() - lastMousePos.y
    ); 

    
    lastMousePos = new Coord(event.getX(), event.getY());
    double speedFactor = 0.2;
    mouseVec.mult(speedFactor);
    this.speed.add(mouseVec);

    if(this.speed.norm() > 0){
      this.direction = speed.copy();
      this.direction.mult(1/Math.sqrt(speed.norm()));
    }
  }
}
