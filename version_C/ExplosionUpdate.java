package version_C;



public class ExplosionUpdate extends Update {
  ExplosionUpdate(int worldX, int worldY) {
    super(worldX, worldY);
  }

  @Override
  public boolean update(Entity e){ 
    e.setRadius(e.getRadius()-1);
    return e.getRadius()==0;
  }
}

