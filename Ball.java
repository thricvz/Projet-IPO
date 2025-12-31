import java.awt.event.KeyListener;
import java.util.concurrent.ConcurrentHashMap;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.awt.Color;

public class Ball implements KeyListener,GraphicElement{
    private GameMediator mediator;
    private Vec speed;
    private Vec position;
    private Vec direction;
    private Vec maxSpeed = new Vec(7,7); 

    private double rayon = Config.blockSize/2;
    private double frictionFactor = 0.2;
    private double accelerationFactor = 2.5; 

    public Ball(){
        this.mediator = null;
        this.position = new Vec(10*Config.blockSize,10*Config.blockSize);
        this.speed = new Vec();
        this.direction = new Vec();
    }

    public void setMediator(GameMediator mediator){
        this.mediator = mediator;
    }

    public void move(){
        this.position.add(this.speed);
        this.applyFriction();
        this.limitSpeed();
        this.handleCollisions();
        //modify speed vector
    }
    private void applyFriction(){
        if(this.speed.norm() < this.frictionFactor){
          this.speed = new Vec();
        }else{
          Vec frictionForce = this.direction.copy();
          frictionForce.negative();
          frictionForce.mult(frictionFactor);

          this.speed.add(frictionForce);
        }
    }
    
    private void limitSpeed(){
        double speedNorm = this.speed.norm();
        if(speedNorm > maxSpeed.norm()){
            double limitedXSpeed = (this.speed.x / speedNorm) * maxSpeed.x;
            double limitedYSpeed = (this.speed.y / speedNorm) * maxSpeed.y;
            this.speed = new Vec(limitedXSpeed,limitedYSpeed); 
        }

    }   

    private void handleCollisions(){
      int currentX = ((int) this.position.x )/ Config.blockSize;
      int currentY = ((int) this.position.y )/ Config.blockSize;

      if(this.position.x + this.rayon > (currentX + 1) * Config.blockSize ){
         Coord squarePosition = new Coord(currentX+1,currentY);
         Square collidedSquare = this.mediator.getSquare(squarePosition);
         if(!collidedSquare.isTraversable()){
            SolidSquare square = (SolidSquare) collidedSquare;
            square.onCollision();
            this.speed.mult(new Vec(-1,1));
         }
      }

      if(this.position.x - this.rayon < currentX * Config.blockSize ){
         Coord squarePosition = new Coord(currentX-1,currentY);
         Square collidedSquare = this.mediator.getSquare(squarePosition);
         if(!collidedSquare.isTraversable()){
            SolidSquare square = (SolidSquare) collidedSquare;
            square.onCollision();
            this.speed.mult(new Vec(-1,1));
         }
      }
      
      if(this.position.y + this.rayon > (currentY + 1) * Config.blockSize ){
         Coord squarePosition = new Coord(currentX,currentY+1);
         Square collidedSquare = this.mediator.getSquare(squarePosition);
         if(!collidedSquare.isTraversable()){
            SolidSquare square = (SolidSquare) collidedSquare;
            square.onCollision();
            this.speed.mult(new Vec(1,-1));
         }
    }

      if(this.position.y - this.rayon > currentY * Config.blockSize ){
         Coord squarePosition = new Coord(currentX,currentY-1);
         Square collidedSquare = this.mediator.getSquare(squarePosition);
         if(!collidedSquare.isTraversable()){
            SolidSquare square = (SolidSquare) collidedSquare;
            square.onCollision();
            this.speed.mult(new Vec(1,-1));
         }
      }
    } 
    public boolean isDead(){
        return false;
    }

    @Override
    public void drawSelf(Graphics2D canvas){
        int diameter = Config.blockSize;
        Coord  canvasPosition = new Coord(
            (int)(position.x) - (diameter/2),
            (int)(position.y) - (diameter/2)
        );

        canvas.setColor(Color.BLUE);
        canvas.drawOval(canvasPosition.x, canvasPosition.y,diameter,diameter);
        canvas.fillOval(canvasPosition.x, canvasPosition.y, diameter,diameter);
    }

    @Override
    public void keyPressed(KeyEvent e){
    }

    @Override
    public void keyReleased(KeyEvent e){}

    @Override
    public void keyTyped(KeyEvent e){
        char keyPressed = e.getKeyChar();
        Vec speedIncrement = new Vec();

        switch(keyPressed){
            case 'l':
               speedIncrement.add(new Vec(1,0));
               break;
            case 'j':
               speedIncrement.add(new Vec(-1,0));
               break;
            case 'i':
               speedIncrement.add(new Vec(0,-1));
               break;
            case 'k':
               speedIncrement.add(new Vec(0,1));
               break;
        }
        speedIncrement.mult(this.accelerationFactor);
        this.speed.add(speedIncrement);
        //calculate the direction
        
        double normSpeed = this.speed.norm();
        if(normSpeed > 0.0){
            this.direction = new Vec(
                this.speed.x / normSpeed,
                this.speed.y / normSpeed
            );
        }


    }
}
