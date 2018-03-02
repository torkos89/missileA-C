package version_G;

import java.text.DecimalFormat;

public class Report {
  private static DecimalFormat df = new DecimalFormat("0.0##");
  public String getData(Entity e) {
    return "{"+getPair("type",e.getType())+","+getPair("color",e.getColor())+","
     +"\"pos\":{"+getIntPair("x",e.getX())+","+getIntPair("y",e.getY())
     +"},"+getPair("radius",e.getRadius())+","+getPair("angle",e.getAngle())+"}";
  }
  protected String getIntPair(String key, double value){
    return "\""+key+"\":"+Math.round(value);
  }
  protected String getPair(String key, double value){
    return "\""+key+"\":"+df.format(value);
  }
  protected String getPair(String key, String value){
    return "\""+key+"\":\""+value+"\"";
  }
  protected String getPair(String key, Boolean value){
    return "\""+key+"\":"+(value?"true":"false")+"";
  }
 
}
