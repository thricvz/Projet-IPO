public class BallData {
    public double radius = 0.3 ;
    public Vec position, speed, direction;
    public int lives;
    
    BallData(double radius,Vec pos,Vec speed, Vec direction,int lives){
        this.radius = radius;
        this.position = pos.copy();
        this.speed = speed.copy();
        this.direction = direction.copy();
        this.lives = lives;
    }
}
