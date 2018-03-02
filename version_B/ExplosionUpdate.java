package version_B;



public class ExplosionUpdate extends Update {
  @Override
  public boolean update(Entity e){ 
    e.setRadius(e.getRadius()-1);
    return e.getRadius()==0;
  }
}

