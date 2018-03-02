package version_G;

public class AsteroidData extends Data{
  final private double ROTATIONSPEED;
  public AsteroidData(int radius){
    ROTATIONSPEED = ((Math.random()>.5? 1:-1)*Math.random())/radius;
  }
  public double getRotationSpeed(){
    return ROTATIONSPEED;
  }
}
