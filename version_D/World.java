package version_D;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ConcurrentModificationException;

public class World {
  final HashMap<Integer, Entity> ENTITIES =  new HashMap<>();
  final HashMap<Integer, Entity> PLAYERS =  new HashMap<>();
  private Thread thread = null;
  final int worldX = 2000;
  final int worldY = 2000;
  private final WorldThread WT;
  private int i = 0;
 
  World(){
    WT = new WorldThread(this);
    thread = new Thread(WT); 
    
    thread.start();
  }
  public HashMap<Integer, Entity> getEntities(){
    return ENTITIES;
  }
  public HashMap<Integer, Entity> getPlayers(){
    return PLAYERS;
  }
  public void addEntity(Entity e){
    if(e.getType().equals("ship")){
      for(Entity ent : ENTITIES.values()){ //TODO prevent pop in asteroids, do test before and after randomization
        if(collide(e,ent)){
          while(collide(e,ent)){
            //TODO fix spawn point collision
           e.setX(Math.random()*worldX) ;
           e.setY(Math.random()*worldY) ;
          }
        }
      }
      
      PLAYERS.put(i,e);
    }
    //WT.addPlayer(i++,e);
    ENTITIES.put(i++, e);
    //e.getSession().getAsyncRemote().sendText("added"+(i-1));
  }
  public void removeEntity(int id){
    ENTITIES.remove(id);
  }
  private boolean collide( Entity entA , Entity entB ){
    if(entA.getColor().equals(entB.getColor())) return false;
    if(entA.getColor().equals("explosion")||entB.getColor().equals("explosion")) return false;
    double X = Math.abs( entA.getX() - entB.getX());
    double Y = Math.abs(entA.getY() - entB.getY());
    double R = entA.getRadius() + entB.getRadius();
    if(R < X || R < Y) return false;
    return(Math.sqrt(X*X + Y*Y) < R);
  }
  private class WorldThread implements Runnable{  // The Runnable interface defines a single method, 
    final World WORLD;                            // run, meant to contain the code executed in the thread.
    int asteroidCount = 0;
    int asteroidMax = 50;

    public WorldThread(World world){
      WORLD = world;
    }
    @Override
    public void run() {
      HashMap< Integer, Entity> entities = WORLD.getEntities();
     try{
      Thread.sleep(50);
       while(true){
         
         LinkedList<Entity> more = new LinkedList<>();
         LinkedList<Integer> less = new LinkedList<>();
         updateLoop(entities, less, more);
         
         if(less.size()>0){           
           for(int i:less)WORLD.removeEntity(i);          
           less.clear();
         }
                
         //if(entities.size()>0)entities.get(0).getSession().getBasicRemote().sendText("top5");
         
         for(Integer id : entities.keySet()){ 
           Entity e1 = entities.get(id);
           
           boolean hit = false;                     
           if(e1.getType().equals("explosion")){
             hit = false;
             continue;         
           }
           for(Integer id2 : entities.keySet()){ 
             if(!hit&&id!=id2){
              
               Entity e2 = entities.get(id2);
               
               if(collide(e1 , e2)){   
                 hit = true;
                 collided(id2,e2,more);
                 less.addLast(id2);
                 break;
               }         
             }
           }
           if(hit){
             collided(id,e1,more);
             less.addLast(id);
             break;
           }
         }
         if(less.size()>0)for(int i:less)WORLD.removeEntity(i);          
         while(asteroidCount<asteroidMax) more.add(createAsteroid(((int)(Math.random()*3)+1)*10));//(int)(Math.random()*3)+1));
         if(more.size()>0){
           for(Entity e : more)//ListIterator<Entity> li;
           WORLD.addEntity(e);
         }
         sendLoop(ENTITIES, WORLD.getPlayers());
         Thread.sleep(50);
       }
     }
     catch(InterruptedException|ConcurrentModificationException | IOException e){
       //try{
         
       //entities.get(0).getSession().getBasicRemote().sendText(e.getMessage()); 
       
      // }catch(IOException e2){
       //}
     } 
    }
    private void updateLoop(Map<Integer, Entity> entities ,LinkedList<Integer> less, LinkedList<Entity> more){
      for(Integer id : entities.keySet()){
        entities.get(id).update(id, less, more);
        
        //if(e.update())less.add(id); // killme
        /*
        else if(e.getInput()!=null){
          SpaceshipData data = (SpaceshipData)e.getData();
          if(e.getInput().isShooting()&&data.isReloaded()){
            more.add(createBullet(e));
            data.fire();
          }
          data.reload();
        }  
        */      
      }
    }
    private void sendLoop(Map<Integer, Entity> entities, Map<Integer, Entity> players) throws IOException{ // make better.
      String state = "";
      for(Integer id : entities.keySet()){
        state += ","+ entities.get(id).getReport();  
      }
      if(state.length()>0){
        state = "["+state.substring(1)+"]";
        for(Integer id : players.keySet()){
          players.get(id).send(state);  
          //if(entities.size()>0)entities.get(0).getSession().getBasicRemote().sendText(state);
        }
      }
    }
    /*
     private Entity createBullet(Entity src){
       double angleX = Math.sin(-src.getAngle());
       double angleY = Math.cos(src.getAngle());
       
       return new Entity(new BulletUpdate(worldX,worldY)).setType("bullet").setX(src.getX()-angleX*35).setY(src.getY()-angleY*35)
           .setVelX(angleX*20+src.getVelX()).setVelY(angleY*20+src.getVelY()).setRadius(5).setColor("silver");
     }*/
     private Entity createAsteroid(int radius){
       return createAsteroid(radius,Math.random()*worldX,Math.random()*worldY);
     }
     private Entity createAsteroid(int radius,double x,double y){  //TODO set safe spawn around player
       
       asteroidCount++;
       return new Entity(new Update(worldX,worldY), new AsteroidReport())
           .setType("asteroid").setX(x)
           .setY(y)
           .setRadius(radius).setColor("lightgray")
           .setVelX(Math.random()*(Math.random()<0.5? 1 : -1)*(4-radius/10)).setVelY(Math.random()*(Math.random()<0.5? 1 : -1)*(4-radius/10));
       /*
       .setX(x!=ENTITIES.get(0).getX()+ENTITIES.get(0).getRadius()||
           x!=ENTITIES.get(0).getX()-ENTITIES.get(0).getRadius()?x:Math.random()*x)
           */  
     }
     
     private Entity createExplosion(Entity src){
       return new Entity(new ExplosionUpdate(worldX,worldY)).setType("explosion")
           .setColor("explosion").setX(src.getX()).setY(src.getY()).setRadius(4);
     }
     

     private void collided(int id,Entity e,LinkedList<Entity> more){
       if(e.getType().equals("asteroid")){
         asteroidCount--;
         int radius = e.getRadius();
         if(radius>10){
           more.add(createAsteroid(radius-10,e.getX(),e.getY()));
           more.add(createAsteroid(radius-10,e.getX(),e.getY()));
         }
       }else if(e.getType().equals("bullet")){
       ((SpaceshipData)((BulletData)e.getData()).getOwner().getData()).addXp();
       
       }
       more.add(createExplosion(e));
     }
  }
  
}
