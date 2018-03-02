package version_E;

public class WorldFactory {
  private static World world = null;
  private static Thread thread = null;
  public static World getWorld(){
    if(world == null){
      world = new World();
      thread = new Thread(world); 
      thread.start();
    }
    return world;
  }
}