import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.lang.Math;

class Vec{
    public double x;
    public double y;

    public Vec(){
      x = 0;
      y = 0;
    }
    public Vec(double x,double y){
      this.x = x;
      this.y = y;
    }
    public void add(Vec rhs){
      this.x += rhs.x;
      this.y += rhs.y;
    }

    public double norm(){
        return this.x * this.x  + this.y*this.y;
    }

    public void mult(double factor){
      this.x *= factor;
      this.y *= factor;
    }
    
    public void negative(){
      this.mult(-1);
    }

    public Vec copy(){
      return new Vec(this.x,this.y);
    }
}

public class Ball implements GraphicElement,MouseMotionListener{
  private Vec pos;
  private Vec speed;
  private Vec direction;
  private int r;
  private Coord lastMousePos;
  private GameMediator mediator;
  private int lives;


  public Ball(){
      this.mediator = null;
      this.lives = 3;
      this.pos = new Vec(10*32,10*32);
      this.speed = new Vec();
      this.direction = new Vec();
      this.lastMousePos = null;
      this.r = 16;
  }

  public void setMediator(GameMediator mediator){
    this.mediator = mediator;
  }

  public void move(){
    double friction = 4;
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

  
  public boolean isDead(){
      return lives == 0;
  } 

  @Override
  public void drawSelf(Graphics2D canvas){
    canvas.setColor(Color.BLACK);
    int x = (int) this.pos.x;
    int y = (int) this.pos.y;

    int diameter = this.r *2;
    canvas.drawOval(x,y,diameter,diameter);
    canvas.fillOval(x,y,diameter,diameter);
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
    mouseVec.mult(0.2);
    this.speed.add(mouseVec);
    
    if(this.speed.norm() > 0){
      this.direction = speed.copy();
      this.direction.mult(1/Math.sqrt(speed.norm()));
    }
  }
}
