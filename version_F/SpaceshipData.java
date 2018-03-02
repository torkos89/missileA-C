package version_F;

public class SpaceshipData extends Data{
  private int reloadTimer = 1;
  private int reloadRate = 10;
  private int hits = 0;
  private int upgrade = 1;
  private int score = 0;
  private double shipFacing;
  
 
  public void reload(){
    if(reloadTimer > 0){
    reloadTimer--;  
    }
  }
  public void fire(){
    reloadTimer = reloadRate;
  }
  public int getReloadTimer() {
    return reloadTimer;
  }
  public void setReloadTimer(int reloadTimer) {
    this.reloadTimer = reloadTimer;
  }
  public boolean isReloaded(){
    return(reloadTimer==0);
  }
  public void addXp() {
    setScore(getScore() + 1); 
    
    hits++;
    if(reloadRate>1){
      if(hits>5){
        reloadRate--;
        hits=0;
      }
    }
    else{
      upgrade++;
      reloadRate = 10;
    }
  }
  public int getScore() {
    return score;
  }
  public void setScore(int score) {
    this.score = score;
  }
  public int getUpgrade() {
    return upgrade;
  }
  public double getShipFacing() {
    return shipFacing;
  }
  public void setShipFacing(double shipFacing) {
    this.shipFacing = shipFacing;
  }

}
