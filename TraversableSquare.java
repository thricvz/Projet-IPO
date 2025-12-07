

abstract class TraversableSquare extends Square{
    public TraversableSquare(Coord coordinates){
        super(coordinates);
    }  
   

   abstract public void enter(Ball player); 

   @Override  
   public boolean isTraversable(){
      return true;
   }

}
