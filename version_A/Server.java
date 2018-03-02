package version_A;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/server")
public class Server {
  private World world = null;
  @OnOpen public void onOpen(Session ses){
    
    ses.getUserProperties().put("angle",0);
    ses.getUserProperties().put("px",400);
    ses.getUserProperties().put("py",250);
    ses.getUserProperties().put("vx",0);
    ses.getUserProperties().put("vy",0);
    ses.getUserProperties().put("type", "ship");
    ses.getUserProperties().put("color", "silver");
    ses.getUserProperties().put("radius", 20);
    
    
    
    ses.getUserProperties().put("engineOn", false);
    ses.getUserProperties().put("brake", false);
    ses.getUserProperties().put("turnLeft", false);
    ses.getUserProperties().put("turnRight", false);
    ses.getUserProperties().put("shooting", false);
    
    world = WorldFactory.getWorld();
    world.addEntity(new Entity(ses,ses.getUserProperties(),new SpaceshipUpdate(),new SpaceshipReport()));
  }
  @OnMessage public void onMessage(String mes, Session ses){
    ses.getUserProperties().put("engineOn", mes.charAt(0)=='1'? "true" : "false");
    ses.getUserProperties().put("brake", mes.charAt(3)=='1'? "true" : "false");
    ses.getUserProperties().put("turnLeft", mes.charAt(1)=='1'? "true" : "false");
    ses.getUserProperties().put("turnRight", mes.charAt(2)=='1'? "true" : "false");
    ses.getUserProperties().put("shooting", mes.charAt(4)=='1'? "true" : "false");
    
    
  //  ses.getAsyncRemote().sendText(mes);
    /*
    double angle = Double.valueOf(ses.getUserProperties().get("angle").toString());
    double x = Double.valueOf(ses.getUserProperties().get("px").toString());
    double y = Double.valueOf(ses.getUserProperties().get("py").toString());
    double vx = Double.valueOf(ses.getUserProperties().get("vx").toString());
    double vy = Double.valueOf(ses.getUserProperties().get("vy").toString());
    boolean engineOn = mes.charAt(0)=='1';
    // update angle
    if(mes.charAt(1)=='1')angle -= 0.2;
    if(mes.charAt(2)=='1')angle += 0.2;
    ses.getUserProperties().put("angle",angle);
    // update velocity
    if(engineOn){
      vx += 1 * Math.sin(-angle);
      vy += 1 * Math.cos(angle);
      ses.getUserProperties().put("vx",vx);
      ses.getUserProperties().put("vy",vy);
    }
    if(mes.charAt(3)=='1'){
      vx -= 1 * Math.sin(-angle);
      vy -= 1 * Math.cos(angle);
      ses.getUserProperties().put("vx",vx);
      ses.getUserProperties().put("vy",vy);
    }
    // update position
    x -= vx;
    y -= vy;
    if(x<0) x += 800;
    else if(x>800) x -= 800;
    if(y<0) y += 500;
    else if(y>500) y -= 500;
    ses.getUserProperties().put("px",x);
    ses.getUserProperties().put("py",y);
    // send results
     * 
     *//*
    ses.getAsyncRemote().sendText("[{\"id\":0,\"type\":\"ship\","
     +"\"data\":{\"color\":\"silver\",\"pos\":{\"x\":"+x+",\"y\":"+y+"},"
      +"\"angle\":"+angle+",\"engineOn\":"+(engineOn?"true":"false")
    +"}},{\"id\":1,\"type\":\"asteroid\",\"data\":{\"color\":\"silver\",\"pos\":{\"x\":200,\"y\":250},\"size\":1}}]");
    */
  }
  
  @OnClose public void onClose(Session ses){

  }
}
