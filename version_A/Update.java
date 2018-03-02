package version_A;

import java.util.Map;

public class Update {
  public boolean update(Map<String, Object> data){
    double x = Double.parseDouble(data.get("px").toString());
    double y = Double.parseDouble(data.get("py").toString());
    double vx = Double.parseDouble(data.get("vx").toString());
    double vy = Double.parseDouble(data.get("vy").toString());
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
