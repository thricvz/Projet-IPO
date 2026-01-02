import java.util.HashMap;

public class SquareFactory{
   public static Square createSquare(String squareSymbol,Coord position,GameMediator mediator){
      //refactor with a map 
      //that then sets t he coordinate
      switch(squareSymbol){
          case "#": return new WallSquare(position);              

          case " ": return new FloorSquare(position);              

          case "R": return new GameWinningSquare(Color.RED,position,mediator);              

          case "G": return new GameWinningSquare(Color.GREEN,position,mediator);              

          case "B": return new GameWinningSquare(Color.BLUE,position,mediator);
          
          case "I": return new InstantKillSquare(position,mediator);   
                    
          case "T": return new TeleportBlock(position,mediator);             
      };

      return null;
  }


};
