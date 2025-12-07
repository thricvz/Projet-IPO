
abstract class Square{

    protected Coord coordinates; 

    public Square(Coord coordinates){
      this.coordinates = coordinates;
    }

    abstract public boolean isTraversable();
}
