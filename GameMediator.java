import java.awt.*;
import java.awt.Color;

public class GameMediator{
  private Ball player;
  private Terrain terrain; 
  private MatchChecker matchChecker;


  GameMediator(Terrain terrain,Ball player,MatchChecker matchChecker){
      this.terrain = terrain;
      this.matchChecker = matchChecker;
      this.player = player;
  }


  public void interactGameObjects(){
      player.move();
  };

  public void drawGameObjects(Graphics2D canvas){
      terrain.drawSelf(canvas);
      player.drawSelf(canvas);
  }

  public boolean gameIsOver(){
     return player.isDead(); 
  };

  public void addNewWinningSquare(Square square){
    matchChecker.addNewWinningSquare((GameWinningSquare) square);
  };
  

  public void activatedSquare(Square square){
    matchChecker.recordActivation((GameWinningSquare) square);
  };
  public Square getSquareObject(Coord coord){
      return terrain.getSquareAt(coord);
  };
}
