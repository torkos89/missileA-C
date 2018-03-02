package version_G;

public class Input {
  private boolean up = false;
  private boolean left = false;
  private boolean down = false;
  private boolean right = false;
  private boolean brake = false;
  private boolean shooting = false;
  private boolean rotate = false;
  
  
  public boolean isUp(){
    return up;
  }
  public void setUp(boolean up){
    this.up = up;
  }
  public boolean isLeft(){
    return left;
  }
  public void setLeft(boolean left){
    this.left = left;
  }
  public boolean isDown(){
    return down;
  }
  public void setDown(boolean down){
    this.down = down;
  }
  public boolean isRight(){
    return right;
  }
  public void setRight(boolean right){
    this.right = right;
  }
  public boolean isBrake() {
    return brake;
  }
  public void setBrake(boolean brake) {
    this.brake = brake;
  }
  public boolean isShooting() {
    return shooting;
  }
  public void setShooting(boolean shooting) {
    this.shooting = shooting;
  }
  public boolean isRotate(){
    return rotate;
  }
  public void setRotate(boolean rotate){
    this.rotate = rotate;
  }
  
}
