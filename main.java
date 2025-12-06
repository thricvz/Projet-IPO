
public class main{

  public static void main(String[] argv){
    System.out.println("main file");      
    MatchChecker matchChecker = new MatchChecker();
    Terrain t = new Terrain("/home/eric/Projects/TPProjet/levels/level1");
    GameMediator myMediator = new GameMediator(t, null, matchChecker);
    t.setMediator(myMediator);
    t.generateTerrain();
  }

}
