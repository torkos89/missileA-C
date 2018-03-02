package version_A;

import java.util.Map;

public class SpaceshipUpdate extends Update{

  @Override
  public boolean update(Map<String,Object> data) {
    
    double angle = Double.valueOf(data.get("angle").toString());
    double x = Double.valueOf(data.get("px").toString());
    double y = Double.valueOf(data.get("py").toString());
    double vx = Double.valueOf(data.get("vx").toString());
    double vy = Double.valueOf(data.get("vy").toString());
    
    boolean engineOn = Boolean.valueOf(data.get("engineOn").toString());
    boolean brake = Boolean.valueOf(data.get("brake").toString());
    boolean turnLeft = Boolean.valueOf(data.get("turnLeft").toString());
    boolean turnRight = Boolean.valueOf(data.get("turnRight").toString());
    // update angle
    if(turnLeft)angle -= 0.2;
    if(turnRight)angle += 0.2;
    data.put("angle",angle);
    // update velocity
    if(engineOn){
      vx += 1 * Math.sin(-angle);
      vy += 1 * Math.cos(angle);
      data.put("vx",vx);
      data.put("vy",vy);
    }
    if(brake){
      vx -= 1 * Math.sin(-angle);
      vy -= 1 * Math.cos(angle);
      data.put("vx",vx);
      data.put("vy",vy);
    }
    // update position
    x -= vx;
    y -= vy;
    if(x<0) x += 800;
    else if(x>800) x -= 800;
    if(y<0) y += 500;
    else if(y>500) y -= 500;
    data.put("px",x);
    data.put("py",y);
    
    return false;
  }

}
