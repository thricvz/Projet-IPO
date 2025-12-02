import java.util.*;

public class Terrain implements GraphicObject{
    public int height;
    public int width;
    
    private ArrayList<ArrayList<Square>> field;
    public GameMediator mediator;   

    public Terrain(String LevelFile,SquareFactory factory){
      //read line per line and request the factory to give the correct square object
    }
    
    public Square getSquareAt(Coord coordinates){
        //0,0 is upper left corner
        boolean x_is_valid = (coordinates.x < width) && (coordinates.x >= 0 );
        boolean y_is_valid = (coordinates.y < width) && (coordinates.y >= 0 );
        
        if(x_is_valid && y_is_valid){
          return field[x][y];
        }
        return null;
    } 
  
   @Override 
   public void drawself(){
      for( ArrayList<Square> line : field){
          for( Square square : line){
              square.drawself();
          } 
      } 
   };
};
