package version_A;

import java.util.Map;

public class SpaceshipReport extends Report {
  public String getData(Map<String, Object> data) {
    /*{\"id\":0,\"type\":\"ship\","
        +"\"data\":{\"color\":\"silver\",\"pos\":{\"x\":"+x+",\"y\":"+y+"},"
        +"\"angle\":"+angle+",\"engineOn\":"+(engineOn?"true":"false")
      +"}}*/
    return "{"+getPair("type","ship")+","+getPair("color","silver")+","
     +"\"pos\":{"
       +getPair("x",Double.parseDouble(data.get("px").toString()))+","+getPair("y",Double.parseDouble(data.get("py").toString()))
     +"},"+getPair("angle",Double.parseDouble(data.get("angle").toString()))+","
              +getPair("engineOn",Boolean.parseBoolean(data.get("engineOn").toString()))+"}";
  }
}
