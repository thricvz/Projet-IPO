import javax.swing.JFrame;


public class main{
  public static void main(String[] argv){
    JFrame frame = new JFrame("My window");
    GamePanel myGamePanel = new GamePanel();

    frame.setResizable(false);

    frame.add(myGamePanel);
    frame.pack(); 

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
