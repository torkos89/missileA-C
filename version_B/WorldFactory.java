package version_B;

public class WorldFactory {
  private static World world = null;
  public static World getWorld(){
    if(world == null){
      world = new World();
    }
    return world;
  }
}