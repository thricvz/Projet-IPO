import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Timer;

public class GamePanel extends JPanel {
    private GameMediator mediator;
    private Timer gameTimer;

    public GamePanel() {
        setupGame("/home/eric/Projects/TPProjet/levels/level1");
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

        setFocusable(true);
        requestFocus();
        requestFocusInWindow();

        this.addKeyListener(player);
    }

    private void startGameLoop() {
        int delay = 10; // milliseconds
        
        gameTimer = new Timer(delay, e -> {
            if (!mediator.gameIsOver()) {
                try {
                    mediator.interactGameObjects();
                    repaint();
                } catch (Exception ex) {
                    System.out.println("Error occurred: " + ex.getMessage());
                    gameTimer.stop();
                }
            } else {
                gameTimer.stop();
                System.out.println("Game over!");
            }
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
