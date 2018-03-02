package version_A;

import java.util.Map;

public class Report {

  public String getData(Map<String, Object> data) {
    return "{"+getPair("type",data.get("type").toString())+","+getPair("color",data.get("color").toString())+","
     +"\"pos\":{"
       +getPair("x",Double.parseDouble(data.get("px").toString()))+","+getPair("y",Double.parseDouble(data.get("py").toString()))
     +"},"+getPair("radius",Integer.parseInt(data.get("radius").toString()))+"}";

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
