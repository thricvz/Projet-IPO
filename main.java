import javax.swing.JFrame;


public class main{
  public static void main(String[] argv){
    JFrame frame = new JFrame("My window");
    frame.setSize(32*21,32*15);
    frame.setResizable(false);
    
    GamePanel myGamePanel = new GamePanel();
    frame.add(myGamePanel);

 
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
