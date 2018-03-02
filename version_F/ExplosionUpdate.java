package version_F;

import java.util.LinkedList;

public class ExplosionUpdate extends Update {
  ExplosionUpdate(int worldX, int worldY) {
    super(worldX, worldY);
  }

  @Override
  public void update(Entity e, int id, LinkedList<Integer> less, LinkedList<Entity> more){ 
    e.setRadius(e.getRadius()-1);
    if(e.getRadius()==0) less.add(id);  //kill me
  }
}

