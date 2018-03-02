package version_H;

import java.util.LinkedList;

public class AsteroidUpdate extends Update{

  AsteroidUpdate(int worldX, int worldY) {
    super(worldX, worldY);
  }
  @Override
  public void update(Entity e,int id, LinkedList<Integer> less,LinkedList<Entity> more){
    super.update(e, id, less, more);
    
    e.setAngle(e.getAngle()+((AsteroidData)e.getData()).getRotationSpeed());
    
  }
}
