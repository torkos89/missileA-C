package version_G;

import java.util.LinkedList;

public class Update {
  protected int worldX;
  protected int worldY;
  Update(int worldX,int worldY){
    this.worldX = worldX;
    this.worldY = worldY;
  }
  public void update(Entity e,int id, LinkedList<Integer> less,LinkedList<Entity> more){
    double x = e.getX();
    double y = e.getY();
    x -= e.getVelX();
    y -= e.getVelY();
    if(x<0) x += worldX;
    else if(x>worldX) x -= worldX;
    if(y<0) y += worldY;
    else if(y>worldY) y -= worldY;
    e.setX(x);
    e.setY(y);
    
  }
}
