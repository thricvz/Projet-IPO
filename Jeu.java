
public class Jeu(){
    private GameMediator mediator;
    
    public Jeu(GameMediator mediator){
        this.mediator = mediator;
    }

    public void run(){
       while(!mediator.gameIsOver()){
          mediator.interactGameObjects();
          mediator.drawGameObjects();
      }

    };
}
