public class GameMediator{
  private Ball player;
  private Terrain terrain; 
  private MatchChecker matchChecker;


  GameMediator(Terrain terrain,Ball player,MatchChecker matchChecker){
      this.terrain = terrain;
      this.matchChecker = matchChecker;
      this.player = player;
  }


  public void interactGameobjects(){
      player.move();
  };

  public void drawGameObjects(){
      terrain.drawself();
      ball.drawself();
  };

  public boolean gameIsOver(){
     return player.isDead(); 
  };

  public void activatedSquare(Square square){
    matchChecker.addNewWinningSquare((GameWinningSquare) square);
  };

  public Square getSquareObject(Coord coord){
      return terrain.getSquareAt(coord);
  };
}
