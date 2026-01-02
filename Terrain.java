import java.util.*;
import java.awt.Graphics2D;
import java.io.File;

public class Terrain implements GraphicElement{
    private String levelName;
    public int height;
    public int width;
    
    private ArrayList<ArrayList<Square>> field;
    public GameMediator mediator;   
    public SquareFactory factory;

    public Terrain(String levelname){
      this.height = 0;
      this.width = 0;
      this.field =  new ArrayList<ArrayList<Square>>();
      this.factory = new SquareFactory();
      this.levelName = levelname;
    }
    public void setMediator(GameMediator mediator){
        this.mediator = mediator;

    }
    public void generateTerrain(){
      try{
        File levelFile = new File(this.levelName);
        Scanner fileReader = new Scanner(levelFile); 

        int current_x = 0; 
        int current_y = -1; 
        
        while(fileReader.hasNextLine()){
            current_y++;
            String currentLine = fileReader.nextLine();  
            
            //create space for the new list  
            field.add(new ArrayList<Square>());
            ArrayList<Square> refFieldLine = field.get(current_y);

            for(int i=0;i < currentLine.length();i++) {
               current_x = i;
               Coord  squarePos = new Coord(current_x,current_y);
               String squareSymbol = Character.toString(currentLine.charAt(current_x));
               refFieldLine.add(this.factory.createSquare(squareSymbol,squarePos,this.mediator));
               
               
            }
        }
        this.height = current_y+1; 
        this.width = current_x; 
        
        fileReader.close();

      }catch(Exception e){
        System.out.println("Error with reading file " + e.getMessage());
      }


    }

    public Square getSquareAt(Coord coordinates){
        //0,0 is upper left corner
        boolean x_is_valid = (coordinates.x <= width) && (coordinates.x >= 0 );
        boolean y_is_valid = (coordinates.y <= width) && (coordinates.y >= 0 );
        
        if(x_is_valid && y_is_valid){
          return field.get(coordinates.y).get(coordinates.x);
        }
        return null;
    } 
  
    @Override
    public void drawSelf(Graphics2D canvas){
        for(ArrayList<Square> line : field){
            for( Square square : line){
                square.drawSelf(canvas);
            } 
        } 
     };
    
};
