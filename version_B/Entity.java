package version_B;

import java.io.IOException;
import java.util.Map;

import javax.websocket.Session;

public class Entity {
  private final Map<String,Object> DATA;
  private Update u;
  private final Session SESSION;
  private Report report;
  private double x = 0;
  private double y = 0;
  private double velX = 0;
  private double velY = 0;
  private double angle = 0;
  private String type = "";
  private int radius = 0;
  private String color = "";
  private int age = 0;
  
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
    return u.update(this);
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
//    return report.getData(DATA);
    return report.getData(this);
  }
  public Map<String,Object>getData(){
    return DATA;
  }
  public Session getSession(){
    return SESSION;
  }
  public double getX() {
    return x;
  } 
  public double getY() {
    return y;
  }
  public double getVelX() {
    return velX;
  } 
  public double getVelY() {
    return velY;
  }
  public double getAngle() {
    return angle;
  }
  public String getType() {
    return type;
  }
  public String getColor() {
    return color;
  }
  public int getRadius() {
    return radius;
  } 
  public int getAge() {
    return age;
  }
  
  public Entity setX(double x) {
    this.x = x;
    return this;
  } 
  public Entity setY(double y) {
    this.y = y;
    return this;
  }
  public Entity setVelX(double velX) {
    this.velX = velX;
    return this;
  } 
  public Entity setVelY(double velY) {
    this.velY = velY;
    return this;
  }
  public Entity setAngle(double angle) {
    this.angle = angle;
    return this;
  }
  public Entity setType(String type) {
    this.type = type;
    return this;
  }
  public Entity setColor(String color) {
    this.color = color;
    return this;
  }
  public Entity setRadius(int radius) {
    this.radius = radius;
    return this;
  }
  public Entity setAge(int age) {
    this.age = age;
    return this;
  }
}
