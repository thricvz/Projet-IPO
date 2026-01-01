import java.awt.Graphics2D;
abstract class Square implements GraphicElement{
    protected AssetsLibrary assetsLibrary;
    protected Coord coordinates; 

    public Square(Coord coordinates){
      this.coordinates = coordinates;
      this.assetsLibrary = AssetsLibrary.Instance();
    }

    abstract public boolean isTraversable();

    @Override 
    public void drawSelf(Graphics2D canvas){};

}

