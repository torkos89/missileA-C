package version_B;

public class SpaceshipReport extends Report {
  public String getData(Entity e) {
    /*{\"id\":0,\"type\":\"ship\","
        +"\"data\":{\"color\":\"silver\",\"pos\":{\"x\":"+x+",\"y\":"+y+"},"
        +"\"angle\":"+angle+",\"engineOn\":"+(engineOn?"true":"false")
      +"}}*/
    return "{"+getPair("type","ship")+","+getPair("color","silver")+","
     +"\"pos\":{"+getPair("x",e.getX())+","+getPair("y",e.getY())+"},"+getPair("angle",e.getAngle())+","
              +getPair("engineOn",Boolean.parseBoolean(e.getData().get("engineOn").toString()))+"}";
  }
}/*
    return "{"+getPair("type","ship")+","+getPair("color","silver")+","
     +"\"pos\":{"
       +getPair("x",Double.parseDouble(data.get("px").toString()))+","+getPair("y",Double.parseDouble(data.get("py").toString()))
     +"},"+getPair("angle",Double.parseDouble(data.get("angle").toString()))+","
           +getPair("engineOn",Boolean.parseBoolean(data.get("engineOn").toString()))+"}";*/