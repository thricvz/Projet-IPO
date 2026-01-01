public class Vec{
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
        return Math.sqrt(this.x * this.x  + this.y*this.y);
    }

    public void mult(double factor){
      this.x *= factor;
      this.y *= factor;
    }
    
    public void mult(Vec vec){
        this.x *= vec.x;
        this.y *= vec.y;
    }
    public void negative(){
      this.mult(-1);
    }

    public Vec copy(){
      return new Vec(this.x,this.y);
    }

}