package version_F;

import java.util.LinkedList;
import javax.websocket.Session;

public class Entity {
  
  private Update u;
  private final Session SESSION;
  private final Data DATA;
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
  private final Input INPUT;
  private int health = 1;
  
  Entity(Update update){
    INPUT = null;
    u = update;
    SESSION = null;
    DATA = null;
    report = new Report();
  }
  Entity(Update update, Data data){
    INPUT = null;
    u = update;
    SESSION = null;
    DATA = data;
    report = new Report();
  }
  Entity(Update update, Report report){
    INPUT = null;
    u = update;
    SESSION = null;
    DATA = null;        //TODO: implement an AI
    this.report = report;
  }
  Entity(Session session,Data data, Input input,Update update,  Report report){  
    SESSION = session;
    DATA = data;
    INPUT = input;
    u = update;
    this.report = report;
  }

  public void update(int id, LinkedList<Integer>less, LinkedList<Entity>more){
    u.update(this,id,less,more);
  }
  public void send(String mes){
    if(SESSION!=null&&SESSION.isOpen()){ 
        //try {
          SESSION.getAsyncRemote().sendText(mes);
       // } catch (IOException e) {
        
       //   e.printStackTrace();
       // }
    }
  }
  public String getReport(){
//    return report.getData(DATA);
    return report.getData(this);
  }
 
  public Session getSession(){
    return SESSION;
  }
  public Data getData(){
    return DATA;
  }
  public Input getInput(){
    return INPUT;
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
  public int getHealth() {
    return health;
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
  public Entity setHealth(int health) {
    this.health = health;
    return this;
  } 
  
}
