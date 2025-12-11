
import java.util.*;

public class MatchChecker{
    private ArrayList<GameWinningSquare> winningSquares;
    private GameWinningSquare lastActivated;    
    private GameWinningSquare nowActivated;    

    public MatchChecker(){
       lastActivated = null;
       nowActivated = null;
       winningSquares = new ArrayList<GameWinningSquare>();
    }
    
    public void addNewWinningSquare(GameWinningSquare square){
      winningSquares.add(square);
    } 

    public void recordActivation(GameWinningSquare activatedSquare){
        //no pair has been initiated yet
        if(lastActivated == null){
           lastActivated = activatedSquare;
           return;
        }
        // is always initially null
        nowActivated = activatedSquare;
       
        if(lastActivated.getColor() == nowActivated.getColor()){
          lastActivated.markAsSolved();
          nowActivated.markAsSolved();
          lastActivated = null;
          nowActivated = null;
        }else{
          lastActivated.hide();
          lastActivated = nowActivated;
          nowActivated = null;
        }
    }; 
    
  public boolean puzzleSolved(){
      boolean solved = true;
      for(GameWinningSquare square : winningSquares){
          solved  = solved && square.hasBeenSolved();
          if(!solved)
            break;
      } 
      
      return solved;

  }
};
