package version_D;

import java.util.LinkedList;

public class SpaceshipUpdate extends Update{

  SpaceshipUpdate(int worldX, int worldY) {
    super(worldX, worldY);
   
  }

  @Override
  public void update(Entity e, int id, LinkedList<Integer>less, LinkedList<Entity>more) {
    
   // double angle = e.getAngle();
    double x = e.getX();
    double y = e.getY();
    double vx = e.getVelX();
    double vy = e.getVelY();
    
    //Map<String, Object> data = e.getData();
    //boolean engineOn = Boolean.valueOf(data.get("engineOn").toString());
    //boolean brake = Boolean.valueOf(data.get("brake").toString());
    //boolean turnLeft = Boolean.valueOf(data.get("turnLeft").toString());
    //boolean turnRight = Boolean.valueOf(data.get("turnRight").toString());
    // update angle
    //if(turnLeft)angle -= 0.2;
    //if(turnRight)angle += 0.2;
    //e.setAngle(angle);
    // update velocity
    
    if(e.getInput().isUp()) e.setVelY(vy+1);
    if(e.getInput().isDown()) e.setVelY(vy-1);
    if(e.getInput().isLeft()) e.setVelX(vx+1);
    if(e.getInput().isRight()) e.setVelX(vx-1);
    
    if(e.getInput().isBrake()){
      //if(Math.abs(vx)+Math.abs(vy) < 1){    //reverse
        //vx -= 1 * Math.sin(-angle);
        //vy -= 1 * Math.cos(angle);
     // }else{
        vx -= vx*.1; // 1 * Math.sin(-angle);
        vy -= vy*.1; //1 * Math.cos(angle);
      //}
      e.setVelX(vx);
      e.setVelY(vy);
    }
    if(e.getInput()!=null){
      SpaceshipData data = (SpaceshipData)e.getData();
      //SpaceshipData playerId = (SpaceshipData)e.getSession();
      if(e.getInput().isShooting()&&data.isReloaded()){
        more.add(createBullet(e));  //TODO: add owner
        data.fire();
      }
      data.reload();
    }
    //TODO: make data smarter, better weapons 
    //super.update(e);
    x -= vx;
    y -= vy;
    if(x<0) x += worldX;
    else if(x>worldX) x -= worldX;
    if(y<0) y += worldY;
    else if(y>worldY) y -= worldY;
    e.setX(x);
    e.setY(y);
    
    if(!e.getSession().isOpen()) less.add(id);
  }
  private Entity createBullet(Entity src){
    double angleX = Math.sin(-src.getAngle());   
    double angleY = Math.cos(src.getAngle());
    
    return new Entity(new BulletUpdate(worldX,worldY),new BulletData().setOwner(src)).setType("bullet")
        .setX(src.getX()-angleX*35).setY(src.getY()-angleY*35)
        .setVelX(angleX*20+src.getVelX()).setVelY(angleY*20+src.getVelY()).setRadius(5).setColor("silver");
  }
}
