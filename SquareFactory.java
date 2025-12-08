
public class SquareFactory{
   public static Square createSquare(String squareSymbol,Coord position,GameMediator mediator){
      switch(squareSymbol){
          case "#":
            return new WallSquare(position);              

          case " ":
            return new FloorSquare(position);              

          case "R":
            return new GameWinningSquare(Color.RED,position,mediator);              

          case "G":
            return new GameWinningSquare(Color.GREEN,position,mediator);              

          case "B":
            return new GameWinningSquare(Color.BLUE,position,mediator);              
      };
      return null;
  }


};
