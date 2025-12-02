abstract class Square implements GraphicObject{
    protected Coord coordinates; 

    public Square(Coord coordinates){
      this.coordinates = coordinates;
    }
    abstract public boolean isTraversable();
}
