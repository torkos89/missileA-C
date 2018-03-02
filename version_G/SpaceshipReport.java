package version_G;

public class SpaceshipReport extends Report {
  
  public String getData(Entity e) {
    SpaceshipData spData = (SpaceshipData)e.getData();
    /*{\"id\":0,\"type\":\"ship\","
        +"\"data\":{\"color\":\"silver\",\"pos\":{\"x\":"+x+",\"y\":"+y+"},"
        +"\"angle\":"+angle+",\"engineOn\":"+(engineOn?"true":"false")
      +"}}*/
    return "{"+getPair("type","ship")+","+getPair("id",e.getSession().getId())+","
      +getPair("shipFacing",spData.getShipFacing())+","+getIntPair("score",spData.getScore())+","
      +getIntPair("health",e.getHealth())+","+getPair("engineOn",spData.isEngineOn())+","
      +"\"pos\":{"+getIntPair("x",e.getX())+","+getIntPair("y",e.getY())+"},"+getPair("angle",e.getAngle())+"}";  //","
              //+getPair("engineOn",e.getInput().isEngineOn())+"}";
  }
}/*
    return "{"+getPair("type","ship")+","+getPair("color","silver")+","
     +"\"pos\":{"
       +getPair("x",Double.parseDouble(data.get("px").toString()))+","+getPair("y",Double.parseDouble(data.get("py").toString()))
     +"},"+getPair("angle",Double.parseDouble(data.get("angle").toString()))+","
           +getPair("engineOn",Boolean.parseBoolean(data.get("engineOn").toString()))+"}";*/