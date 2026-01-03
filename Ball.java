import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Graphics2D;
import java.lang.Math;
import java.util.ArrayList;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;


public class Ball implements GraphicElement,MouseMotionListener{
  private double radius = 0.3 ;
  private double vit = 0.02 ;
  private double f = 0.01 ;
  private Vec pos, speed, direction;
  private double frict = 0.005 ;
  private GameMediator mediator;
  private Coord lastMousePos;
  private int lives ;
 
  public Ball(){
      this.mediator = null;
      this.lives = 3 ;
      this.speed = new Vec();
      this.pos = new Vec(10.5, 10.5);
      this.direction = new Vec();
      this.lastMousePos = null ;
  }
  public void loadData(BallData data){
      this.radius = data.radius;
      this.pos = data.position;
      this.speed = data.speed;
      this.direction = data.direction;
      this.lives = data.lives;
  }

  public BallData produceData(){
      return new BallData(radius, pos, speed, direction, lives);
  }

  //pas toucher : pas besoin de modifier, il s'agit d'une abstraction
  public void setMediator(GameMediator mediator){
    this.mediator = mediator;
  }

  private Coord getCurrentCell() { // obtenir coordonnées case sous la balle
    int cellX = (int)Math.floor(pos.x); // conversion en entier des coordonnées
    int cellY = (int)Math.floor(pos.y);
    return new Coord(cellX, cellY);
  }

  public boolean CornerCollision(int cellX, int cellY) { // gestion collision coin choisi

    Coord cellToCheck = new Coord(cellX,cellY);

    Square square = mediator.getSquareObject(cellToCheck);
    if (square == null || square.isTraversable()) {
        return false; // case vide ou traversable => coin pas solide
    }

    double coinX = cellX;
    double coinY = cellY;

    double dx = pos.x - coinX; // distance balle - coin
    double dy = pos.y - coinY;
    double distance = Math.sqrt(dx*dx + dy*dy);

    if (distance < radius) {

        ((SolidSquare) square).onCollision();

        speed.x = -speed.x * 0.8; // rebond SIMPLE : inverser les deux vitesses
        speed.y = -speed.y * 0.8;
        
        pos.x = coinX + (radius * dx/distance); // sortir la balle du mur
        pos.y = coinY + (radius * dy/distance);

        return true; // detection collision
    }
    return false; // pas de collision
  }

  public void checkCornerCollision(int cellX, int cellY) {
    if(CornerCollision(cellX,cellY)) return; // coin haut-gauche
    if(CornerCollision(cellX+1,cellY)) return; // coin haut-droit
    if(CornerCollision(cellX,cellY+1)) return; // coin bas-gauche
    if(CornerCollision(cellX+1,cellY+1)) return; // coin bas-droit
  }

  // méthode qui fait bouger le joueur
  public void move() {
    pos.add(speed); // modifier position de la balle selon sa vitesse

    int currentCellX = this.getCurrentCell().x;  // coordonnées case actuelle de la balle
    int currentCellY = this.getCurrentCell().y;

    boolean hasRebounced = false ; // booléen vérifiant rebond de la balle

    if (pos.x + radius > currentCellX + 1) { // collision à droite
      Square rightSquare = mediator.getSquareObject(new Coord(currentCellX+1, currentCellY));
      if (rightSquare != null && !rightSquare.isTraversable()) { // mur => repousser la balle
          speed.x = -speed.x * 0.8;
          pos.x = currentCellX + 1 - radius;
          hasRebounced = true ;
          ((SolidSquare) rightSquare).onCollision();
      }
    }

    if (pos.x - radius < currentCellX) { // collision à gauche
      Square leftSquare = mediator.getSquareObject(new Coord(currentCellX-1, currentCellY));
      if (leftSquare != null && !leftSquare.isTraversable()) {
          speed.x = -speed.x * 0.8;
          pos.x = currentCellX + radius;
          hasRebounced = true ;
          ((SolidSquare) leftSquare).onCollision();

      } 
    }

    if (pos.y + radius > currentCellY + 1) { // collision en bas
      Square botSquare = mediator.getSquareObject(new Coord(currentCellX, currentCellY+1));
      if (botSquare != null && !botSquare.isTraversable()) {
          speed.y = -speed.y * 0.8;
          pos.y = currentCellY + 1 - radius;
          hasRebounced = true ;
          ((SolidSquare) botSquare).onCollision();

      }
    }

    if (pos.y - radius < currentCellY) { // collision en haut
      Square highSquare = mediator.getSquareObject(new Coord(currentCellX, currentCellY-1));
      if (highSquare != null && !highSquare.isTraversable()) {
          speed.y = -speed.y * 0.8;
          pos.y = currentCellY + radius;
          hasRebounced = true ;
          ((SolidSquare) highSquare).onCollision();
      } 
    }

    if (!hasRebounced) { // vérification collision avec les coins
      checkCornerCollision(currentCellX, currentCellY);
    }

    double norme = speed.norm();
    if (norme > 0) { // vérifie le mouvement de la balle
      direction.x = speed.x / norme ; // calcul vecteur unitaire de direction (-1 < direction < 1)
      direction.y = speed.y / norme ;

      if (norme <= frict) { // vérifier norme par rapport au frottement
        speed.x = 0 ;
        speed.y = 0 ;
      } else {
          speed.x = speed.x - frict * direction.x; // appliquer force de friction du sol
          speed.y = speed.y - frict * direction.y;
        }

    } else { // vitesse nulle => direction nulle
      direction.x = 0 ;
      direction.y = 0 ;
    }
  }

  public boolean isDead() {
    return this.lives <= 0 ;
  }
 
  // implementer la methode qui permet a la balle de s'afficher sur l'écran
  @Override
  public void drawSelf(Graphics2D canvas){
    int centerX = (int)(pos.x * Config.blockSize); // CONVERTIR terrain => pixels
    int centerY = (int)(pos.y * Config.blockSize);
    
    int radiusPixels = (int)(radius * Config.blockSize);  // radius = 0.3
    
    int draw_x = centerX - radiusPixels;
    int draw_y = centerY - radiusPixels;
    
    int diameter = radiusPixels * 2;
    
    canvas.setColor(Color.BLACK);
    canvas.drawOval(draw_x, draw_y, diameter, diameter);
    canvas.fillOval(draw_x, draw_y, diameter, diameter);
}


  //methodes de l'interface MouseMotionListener sont declenchees une fois la souris bougee
 // l'idee est de mettre a jour la position de la balle qnd la souris est bougee
  @Override
  public void mouseDragged(MouseEvent event){
    int currentX = event.getX();
    int currentY = event.getY();
    if (lastMousePos == null) { // premier appel si la position est nulle
      lastMousePos = new Coord(currentX,currentY);
      return;
    }
    lastMousePos = new Coord(currentX,currentY);
  };


  @Override
  public void mouseMoved(MouseEvent event){
    int currentX = event.getX();
    int currentY = event.getY();    
  
    if (lastMousePos == null) { // premier appel si la position est nulle
      lastMousePos = new Coord(currentX,currentY);
      return;
    }
    int dx = currentX - lastMousePos.x;
    int dy = currentY - lastMousePos.y;

    double dxTerrain = dx / (double)Config.blockSize; // ajustement à l'échelle du terrain
    double dyTerrain = dy / (double)Config.blockSize;

    int ballCenterPixelX = (int)(pos.x * Config.blockSize); // position de la balle (pixels)
    int ballCenterPixelY = (int)(pos.y * Config.blockSize);

    int distFromCursorX = currentX - (ballCenterPixelX); // distance entre curseur et balle
    int distFromCursorY = currentY - (ballCenterPixelY);

    double distanceFactor = 1.0;
    
    if (Math.abs(distFromCursorX) > 50 || Math.abs(distFromCursorY) > 50) {
        // Quand la balle s'éloigne du curseur, augmente progressivement
        double distCr = Math.sqrt(distFromCursorX*distFromCursorX + distFromCursorY*distFromCursorY);
        distanceFactor += (distCr / 200.0); // Ex: distance=100 => facteur=2.0
    }
    
    speed.x += dxTerrain * f * distanceFactor;
    speed.y += dyTerrain * f * distanceFactor;

    double currentSpeed = speed.norm();
    if (currentSpeed > 0.2) { // vérifie la limite de la vitesse actuelle
        double factor = 0.2 / currentSpeed;
        speed.x *= factor;
        speed.y *= factor;
    }

    lastMousePos.x = currentX; // actualise la dernière position de la souris
    lastMousePos.y = currentY;
  }
}
