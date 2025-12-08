import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{
    private GameMediator mediator;
    private Thread gameThread;

    public GamePanel(){
        setupGame("/home/eric/Projects/TPProjet/levels/level1");
        startGameLoop();
    }

    private void setupGame(String levelname){
        Terrain terrain =  new Terrain(levelname);
        Ball player = new Ball();
        MatchChecker matchChecker = new MatchChecker();

        this.mediator = new GameMediator(terrain, player, matchChecker);
        terrain.setMediator(mediator);
        terrain.generateTerrain();
        player.setMediator(mediator);
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        while(!mediator.gameIsOver()){
            try{
                mediator.interactGameObjects();
                repaint();
                Thread.sleep(100);
        
            }catch(Exception e){
                System.out.println("execption");
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        mediator.drawGameObjects((Graphics2D) g);
    }
}