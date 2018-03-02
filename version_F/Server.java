
package version_F;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/serverF")
public class Server {
  private World world = null;
  private Entity player = null;
  private Input input = null;
  private int worldX = 10000;
  private int worldY = 10000;

  @OnOpen public void onOpen(Session ses){
    
    ses.getAsyncRemote().sendText("_"+ses.getId()+","+worldX+","+worldY);
 
    //ses.getUserProperties().put("engineOn", false);
    //ses.getUserProperties().put("brake", false);
    //ses.getUserProperties().put("turnLeft", false);
    //ses.getUserProperties().put("turnRight", false);
    //ses.getUserProperties().put("shooting", false);
    input = new Input();
    
    player = new Entity(ses,new SpaceshipData(),input,new SpaceshipUpdate(worldX,worldY),new SpaceshipReport())
        .setX(Math.random()*worldX).setY(Math.random()*worldY).setVelX(0).setVelY(0)
        .setAngle(0).setColor("silver")
        .setType("ship").setRadius(20).setHealth(6);
    world = WorldFactory.getWorld();
    world.addEntity(player);
   
  }
  @OnMessage public void onMessage(String mes, Session ses){
    //ses.getUserProperties().put("engineOn", mes.charAt(0)=='1'? "true" : "false");
    //ses.getUserProperties().put("brake", mes.charAt(1)=='1'? "true" : "false");
    //ses.getUserProperties().put("turnLeft", mes.charAt(1)=='1'? "true" : "false");
    //ses.getUserProperties().put("turnRight", mes.charAt(2)=='1'? "true" : "false");
    //ses.getUserProperties().put("shooting", mes.charAt(2)=='1'? "true" : "false");
    input.setUp(mes.charAt(0)=='1');   
    input.setLeft(mes.charAt(1)=='1');   
    input.setDown(mes.charAt(2)=='1');      
    input.setRight(mes.charAt(3)=='1');
    input.setBrake(mes.charAt(4)=='1');
    input.setShooting(mes.charAt(5)=='1');
    input.setRotate(mes.charAt(6)=='1');
    player.setAngle(Double.parseDouble(mes.substring(7)));
    
  //  ses.getAsyncRemote().sendText(mes);
    
  }
  
  @OnClose public void onClose(Session ses){

  }
}
