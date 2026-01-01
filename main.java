import javax.swing.JFrame;


public class main{
  public static void main(String[] argv){
    JFrame frame = new JFrame("My window");
    frame.setSize(Config.screenWidth,Config.screenHeight);
    frame.setResizable(false);
    
    GamePanel myGamePanel = new GamePanel();
    frame.add(myGamePanel);

 
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
