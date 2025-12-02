public class GameMediator{
  private Ball player;
  private Terrain terrain; 
  private MatchChecker matchChecker;


  GameMediator(Terrain terrain,Ball player,MatchChecker matchChecker){
      this.terrain = terrain;
      this.matchChecker = matchChecker;
      this.player = player;
  }
  public void activatedSquare(Square square){
    matchChecker.addNewWinningSquare();
  };
  interactGameObjects

}
