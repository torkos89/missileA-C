package version_C;

public class BulletUpdate extends Update {
  BulletUpdate(int worldX, int worldY) {
    super(worldX, worldY);
  }

  @Override
  public boolean update(Entity e){
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
    e.setAge(e.getAge()+1);
    
    return e.getAge()>40;
  }
}
