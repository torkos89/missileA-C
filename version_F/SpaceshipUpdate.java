package version_F;

import java.util.LinkedList;

public class SpaceshipUpdate extends Update{

  SpaceshipUpdate(int worldX, int worldY) {
    super(worldX, worldY);
   
  }

  @Override
  public void update(Entity e, int id, LinkedList<Integer>less, LinkedList<Entity>more) {
    
    double vx = e.getVelX();
    double vy = e.getVelY();

    // update velocity
    if(e.getInput().isUp()){
      e.setVelY(vy+1);
     
    }
    if(e.getInput().isDown()) e.setVelY(vy-1);
    if(e.getInput().isLeft()) e.setVelX(vx+1);
    if(e.getInput().isRight()) e.setVelX(vx-1);

    if(e.getInput().isBrake()){
        vx -= vx*.2; // 1 * Math.sin(-angle);
        vy -= vy*.2; //1 * Math.cos(angle);
      e.setVelX(vx);
      e.setVelY(vy);
    }
    super.update(e,id,less,more);
    SpaceshipData data = (SpaceshipData)e.getData();
    if(e.getInput()!=null){
      
      //SpaceshipData playerId = (SpaceshipData)e.getSession();
      if(e.getInput().isShooting()&&data.isReloaded()){
        //boolean offset = true;
       // for(int i = 0 ; i<data.getUpgrade(); i++){
         // more.add(createBullet(e,(offset?1:-1)*i/10.0)); 
        //  offset = !offset;
       // }
        more.add(createBullet(e, Math.sin(data.getShipFacing())*20, 
            -Math.cos(data.getShipFacing())*20 , data.getUpgrade())); 
        more.add(createBullet(e,-Math.sin(data.getShipFacing())*20, 
            Math.cos(data.getShipFacing())*20 , data.getUpgrade())); 
        data.fire();
      }
      data.reload();
    }
    //TODO: make data smarter, better weapons 
    
    data.setShipFacing(Math.atan2(vy, vx));
    
    if(!e.getSession().isOpen()) less.add(id);
  }
  /*
  private Entity createBullet(Entity src, double angleOffset){
    double angleX = Math.sin(-src.getAngle()+angleOffset);   
    double angleY = Math.cos(src.getAngle()+angleOffset);
    
    return new Entity(new BulletUpdate(worldX,worldY),new BulletData().setOwner(src)).setType("bullet")
        .setX(src.getX()-angleX*35).setY(src.getY()-angleY*35)
        .setVelX(src.getVelX()+angleX*20).setVelY(src.getVelY()+angleY*20).setRadius(5).setColor("silver").setHealth(1);
  }
*/
  private Entity createBullet(Entity src, double xOffset, double yOffset , int health){
    double angleX = Math.sin(-src.getAngle());   
    double angleY = Math.cos(src.getAngle());
    
    return new Entity(new BulletUpdate(worldX,worldY),new BulletData().setOwner(src)).setType("bullet")
        .setX(src.getX()+xOffset).setY(src.getY()+yOffset)
        .setVelX(src.getVelX()+angleX*20).setVelY(src.getVelY()+angleY*20).setRadius(5).setColor("silver").setHealth(health);
  }
}
