

abstract class TraversableSquare extends Square implements GraphicObject{
    public TraversableSquare(Coord coordinates){
        super(coordinates);
    }  
   

   abstract public void enter(Ball player); 

   @Override  
   public boolean isTraversable(){
      return true;
   }

}
