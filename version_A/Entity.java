package version_A;

import java.io.IOException;
import java.util.Map;

import javax.websocket.Session;

public class Entity {
  private final Map<String,Object> DATA;
  private Update u;
  private final Session SESSION;
  private Report report;
  
  Entity(Map<String,Object> data,Update update){
    DATA = data;
    u = update;
    SESSION = null;
    report = new Report();
  }
  Entity(Map<String,Object> data,Update update, Report report){
    DATA = data;
    u = update;
    SESSION = null;
    this.report = report;
  }
  Entity(Session session,Map<String,Object> data,Update update,  Report report){
    DATA = data;
    u = update;
    SESSION = session;
    this.report = report;
  }

  public boolean update(){
    return u.update(DATA);
  }
  public void send(String mes){
    if(SESSION!=null){
      try {
        SESSION.getBasicRemote().sendText(mes);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
  public String getReport(){
    return report.getData(DATA);
  }
  public Map<String,Object>getData(){
    return DATA;
  }
  public Session getSession(){
    return SESSION;
  }
}
