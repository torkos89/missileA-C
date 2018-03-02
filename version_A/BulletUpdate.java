package version_A;

import java.util.Map;

public class BulletUpdate extends Update {
  @Override
  public boolean update(Map<String, Object> data){
    super.update(data);
    double age = Double.valueOf(data.get("age").toString());
    age++;
    data.put("age", age);
    return age>80;
   
  }
}
