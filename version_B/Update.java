package version_B;

public class Update {
  public boolean update(Entity e){
    double x = e.getX();
    double y = e.getY();
    x -= e.getVelX();
    y -= e.getVelY();
    if(x<0) x += 800;
    else if(x>800) x -= 800;
    if(y<0) y += 500;
    else if(y>500) y -= 500;
    e.setX(x);
    e.setY(y);
    return false;
  }
  
}
