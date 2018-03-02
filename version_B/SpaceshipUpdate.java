package version_B;

import java.util.Map;

public class SpaceshipUpdate extends Update{

  @Override
  public boolean update(Entity e) {
    
    double angle = e.getAngle();
    double x = e.getX();
    double y = e.getY();
    double vx = e.getVelX();
    double vy = e.getVelY();
    
    Map<String, Object> data = e.getData();
    boolean engineOn = Boolean.valueOf(data.get("engineOn").toString());
    boolean brake = Boolean.valueOf(data.get("brake").toString());
    boolean turnLeft = Boolean.valueOf(data.get("turnLeft").toString());
    boolean turnRight = Boolean.valueOf(data.get("turnRight").toString());
    // update angle
    if(turnLeft)angle -= 0.2;
    if(turnRight)angle += 0.2;
    e.setAngle(angle);
    // update velocity
    if(engineOn){
      vx += 1 * Math.sin(-angle);
      vy += 1 * Math.cos(angle);
      e.setVelX(vx);
      e.setVelY(vy);
    }
    if(brake){
      if(Math.abs(vx)+Math.abs(vy) < 1){
        vx -= 1 * Math.sin(-angle);
        vy -= 1 * Math.cos(angle);
      }else{
        vx -= vx*.1; // 1 * Math.sin(-angle);
        vy -= vy*.1; //1 * Math.cos(angle);
      }
      e.setVelX(vx);
      e.setVelY(vy);
    }
    // update position
    x -= vx;
    y -= vy;
    if(x<0) x += 800;
    else if(x>800) x -= 800;
    if(y<0) y += 500;
    else if(y>500) y -= 500;
    e.setX(x);
    e.setY(y);
    
    return false;
  }

}
