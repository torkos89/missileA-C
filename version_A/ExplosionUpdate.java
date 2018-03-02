package version_A;

import java.util.Map;

public class ExplosionUpdate extends Update {
  @Override
  public boolean update(Map<String, Object> data){
    double radius = Double.valueOf(data.get("radius").toString());
    radius--;
    data.put("radius", radius);
    return radius==0;
   
  }
}

