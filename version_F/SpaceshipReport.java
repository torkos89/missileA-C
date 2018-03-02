package version_F;

public class SpaceshipReport extends Report {
  
  public String getData(Entity e) {
    SpaceshipData spData = (SpaceshipData)e.getData();
    /*{\"id\":0,\"type\":\"ship\","
        +"\"data\":{\"color\":\"silver\",\"pos\":{\"x\":"+x+",\"y\":"+y+"},"
        +"\"angle\":"+angle+",\"engineOn\":"+(engineOn?"true":"false")
      +"}}*/
    return "{"+getPair("type","ship")+","+getPair("id",e.getSession().getId())+","+getPair("color","silver")+","
      +getPair("shipFacing",spData.getShipFacing())+","+getPair("score",spData.getScore())+","+getPair("health",e.getHealth())+","
     +"\"pos\":{"+getPair("x",e.getX())+","+getPair("y",e.getY())+"},"+getPair("angle",e.getAngle())+"}";  //","
              //+getPair("engineOn",e.getInput().isEngineOn())+"}";
  }
}/*
    return "{"+getPair("type","ship")+","+getPair("color","silver")+","
     +"\"pos\":{"
       +getPair("x",Double.parseDouble(data.get("px").toString()))+","+getPair("y",Double.parseDouble(data.get("py").toString()))
     +"},"+getPair("angle",Double.parseDouble(data.get("angle").toString()))+","
           +getPair("engineOn",Boolean.parseBoolean(data.get("engineOn").toString()))+"}";*/