package version_D;

public class SpaceshipData extends Data{
  private int reloadTimer = 1;
  private int reloadRate = 10;
  private int hits = 0;
  private int score = 0;
  
  public void reload(){
    if(reloadTimer > 0){
    reloadTimer--;  
    }
  }
  public void fire(){
    reloadTimer = reloadRate;
    //hits++;
    /*
    if(reloadRate>1 && hits>50){
      reloadRate--;
      hits=0;
    }
    */
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
    if(reloadRate>1 && hits>50){
      reloadRate--;
      hits=0;
    }
  }
  public int getScore() {
    return score;
  }
  public void setScore(int score) {
    this.score = score;
  }
}
