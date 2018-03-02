package version_C;



public class SpaceshipUpdate extends Update{

  SpaceshipUpdate(int worldX, int worldY) {
    super(worldX, worldY);
   
  }

  @Override
  public boolean update(Entity e) {
    
    //double angle = e.getAngle();
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
    
    if(e.getInput().isUp()){
      y -= 1;
      e.setY(y);
      vy += 1;
      e.setVelY(vy);
      //vx += 1;// * Math.sin(-angle);
      //* Math.cos(angle);
      //e.setVelX(vx); 
    }
    
    if(e.getInput().isLeft()){
      x -= 1;
      e.setY(x);
      vx += 1;
      e.setVelX(vx);
    }
    
    if(e.getInput().isDown()){
      y += 1;
      e.setY(y);
      vy -= 1;
      e.setVelY(vy);
    }
    
    if(e.getInput().isRight()){
      x += 1;
      e.setY(x);
      vx -= 1;
      e.setVelX(vx);
    }
    
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
                                            // update position
    
    //super.update(e);
    x -= vx;
    y -= vy;
    if(x<0) x += worldX;
    else if(x>worldX) x -= worldX;
    if(y<0) y += worldY;
    else if(y>worldY) y -= worldY;
    e.setX(x);
    e.setY(y);
    
    return !e.getSession().isOpen();
  }

}
