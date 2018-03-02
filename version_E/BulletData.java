package version_E;

public class BulletData extends Data{
  private Entity owner;

  public Entity getOwner() {
    return owner;
  }

  public BulletData setOwner(Entity owner) {
    this.owner = owner;
    return this;
  }
  
}
