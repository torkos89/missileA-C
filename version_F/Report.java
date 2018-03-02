package version_F;

public class Report {

  public String getData(Entity e) {
    return "{"+getPair("type",e.getType())+","+getPair("color",e.getColor())+","
     +"\"pos\":{"
       +getPair("x",e.getX())+","+getPair("y",e.getY())
     +"},"+getPair("radius",e.getRadius())+"}";
  }
  protected String getPair(String key, Number value){
    return "\""+key+"\":"+value;
  }
  protected String getPair(String key, String value){
    return "\""+key+"\":\""+value+"\"";
  }
  protected String getPair(String key, Boolean value){
    return "\""+key+"\":"+(value?"true":"false")+"";
  }
}
