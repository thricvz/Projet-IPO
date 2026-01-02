import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Timer;

public class GamePanel extends JPanel {
    private GameMediator mediator;
    private Timer gameTimer;

    public GamePanel() {
        setupGame("levels/level1");
        setPreferredSize(new Dimension(Config.screenWidth,Config.screenHeight));
        startGameLoop();
    }

    private void setupGame(String levelname) {
        Terrain terrain = new Terrain(levelname);
        Ball player = new Ball();
        MatchChecker matchChecker = new MatchChecker();

        this.mediator = new GameMediator(terrain, player, matchChecker);
        terrain.setMediator(mediator);
        terrain.generateTerrain();
        player.setMediator(mediator);
        
        this.addMouseMotionListener(player);
    }

    private void startGameLoop() {
        int delay = 10; // milliseconds
        
        gameTimer = new Timer(delay, e -> {
            if(mediator.gameIsOver()){
                System.out.println("Vous avez Perdu");
                gameTimer.stop();
            }

            if(mediator.gameWon()){
                System.out.println("BRAVO vous avez gagné");
                gameTimer.stop();
            }

            mediator.interactGameObjects();
            repaint();
            
        });
        
        gameTimer.start();
    }

    // Méthode pour ajuster la vitesse si nécessaire
    public void setGameSpeed(int fps) {
        // Limiter entre 20 FPS (50ms) et 100 FPS (10ms)
        fps = Math.max(20, Math.min(100, fps));
        int delay = 1000 / fps;
        
        if (gameTimer != null) {
            gameTimer.setDelay(delay);
        }
    }

    
    public void stopGameLoop() {
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        mediator.drawGameObjects((Graphics2D) g);
    }
}