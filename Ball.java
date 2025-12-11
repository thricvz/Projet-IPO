import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.lang.Math;
import java.util.ArrayList;

class Pair<K,V>{
  public K first;
  public V second;

  public Pair(K val1,V val2){
    first = val1;
    second = val2;
  }
}
public class Ball implements GraphicElement,MouseMotionListener{
  private Vec pos;
  private Vec speed;
  private Vec direction;
  private double radius;
  private Coord lastMousePos;
  private GameMediator mediator;
  private int lives;
  private boolean nextFrameCalculated = false;


  public Ball(){
      this.mediator = null;
      this.lives = 3;
      this.pos = new Vec(10.5*Config.blockSize,10.5*Config.blockSize);
      this.speed = new Vec();
      this.direction = new Vec();
      this.lastMousePos = null;
      this.radius = 10;
  }

  public void setMediator(GameMediator mediator){
    this.mediator = mediator;
  }

  public void move(){
    this.predictCollision();
    if(this.nextFrameCalculated){
      this.nextFrameCalculated = false;
      return;
    }
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


    }
    

    
  };

  private void predictCollision(){
      int n_predictions = 10;

      for(int i = 1; i <= n_predictions;i++){
        Vec speedCopy = this.speed.copy();
        Vec positionCopy = this.pos.copy();
        speedCopy.mult(((double)i)/n_predictions);
        positionCopy.add(speedCopy);
        
        Pair<Boolean,Vec> result = checkCollision(positionCopy);
        if(result.first){
          this.pos = positionCopy;
          this.speed.mult(result.second);
          this.speed.mult(0.9);
          this.direction.mult(result.second);
          this.nextFrameCalculated = true;
          break;
        }
      }
  }
  private Pair<Boolean,Vec> checkCollision(Vec position){
    ArrayList<Square> collidedSquares = new ArrayList<Square>();
    Coord currentSquarePos = this.getPosBoard(position);
    Vec collisionVec = new Vec(1,1);
    
    
    if(( position.x + this.radius ) > ((currentSquarePos.x+1)*Config.blockSize) ){
      Square collidedSquare = mediator.getSquareObject(
        new Coord(currentSquarePos.x+1,currentSquarePos.y)
      );

      if(!collidedSquare.isTraversable()){
          collidedSquares.add(collidedSquare);
          collisionVec.x *=-1;
      }

    }
    
    
    if((position.y + this.radius ) > ((currentSquarePos.y+1)*Config.blockSize) ){
      Square collidedSquare = mediator.getSquareObject(
        new Coord(currentSquarePos.x,currentSquarePos.y+1)
      );

      if(!collidedSquare.isTraversable()){
          collidedSquares.add(collidedSquare);
          collisionVec.y *=-1;
      }

    }


    if((position.x - this.radius ) < ((currentSquarePos.x)*Config.blockSize) ){
      Square collidedSquare = mediator.getSquareObject(
        new Coord(currentSquarePos.x-1,currentSquarePos.y)
      );

      if(!collidedSquare.isTraversable()){
          collidedSquares.add(collidedSquare);
          collisionVec.x *=-1;
      }

    }

    if((position.y - this.radius ) < ((currentSquarePos.y)*Config.blockSize) ){
      Square collidedSquare = mediator.getSquareObject(
        new Coord(currentSquarePos.x,currentSquarePos.y-1)
      );

      if(!collidedSquare.isTraversable()){
          collidedSquares.add(collidedSquare);
          collisionVec.y *=-1;
      }

      
    }
    for(Square square : collidedSquares){
          ((SolidSquare) square).onCollision();
    }
    return new Pair((collidedSquares.size() > 0),collisionVec);
  }

  public Coord getPosBoard(Vec positon){
    Coord currentPosition = new Coord(
      (int) (positon.x/Config.blockSize),
      (int) (positon.y/Config.blockSize)
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

    int draw_x = x - (int) this.radius;
    int draw_y = y - (int) this.radius;

    int diameter = ((int) radius)*2;
    canvas.setColor(Color.BLACK);
    canvas.drawOval(draw_x,draw_y,diameter,diameter);
    canvas.fillOval(draw_x,draw_y,diameter,diameter);

    
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
    double speedFactor = 0.4;
    mouseVec.mult(speedFactor);
    this.speed.add(mouseVec);
    
    if(this.speed.norm() > 0){
      this.direction = speed.copy();
      this.direction.mult(1/Math.sqrt(speed.norm()));
    }

  
  }
}
