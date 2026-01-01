
abstract class SolidSquare extends Square {
    public SolidSquare(Coord coordinates){
        super(coordinates);
    }  
   

   abstract public void onCollision(); 

   @Override  
   public boolean isTraversable(){
      return false;
   }

}
