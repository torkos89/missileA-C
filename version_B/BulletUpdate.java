package version_B;

public class BulletUpdate extends Update {
  @Override
  public boolean update(Entity e){
    super.update(e);
    e.setAge(e.getAge()+1);
    
    return e.getAge()>80;
  }
}
