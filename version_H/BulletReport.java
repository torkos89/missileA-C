package version_H;

public class BulletReport extends Report{
 
  public String getData(Entity e) {
    return "{"+getPair("color",e.getColor())+","+"\"pos\":{"+getIntPair("x",e.getX())+","+getIntPair("y",e.getY())
       +"},"+getPair("radius",e.getRadius())+","+getPair("angle",e.getAngle())+"}";
  }
}
