package version_G;

import java.util.LinkedList;


public class BulletUpdate extends Update {
  BulletUpdate(int worldX, int worldY) {
    super(worldX, worldY);
  }

  @Override
  public void update(Entity e, int id, LinkedList<Integer>less, LinkedList<Entity>more){
    double x = e.getX();
    double y = e.getY();
    int age = e.getAge();
    x -= e.getVelX();
    y -= e.getVelY();
    if(x<0) x += worldX;
    else if(x>worldX) x -= worldX;
    if(y<0) y += worldY;
    else if(y>worldY) y -= worldY;
    e.setX(x);
    e.setY(y);
    e.setAge(age+1);
    switch(age%10){
    case 0: //e.setColor();
    case 1: 
    case 2: //e.setColor();
    case 3:  
    case 4: e.setColor("lightgreen"); break;
    //case 5:
    default: e.setColor("#4fe34f");
    }
    if(e.getAge()>40) less.add(id) ;
  }
}
