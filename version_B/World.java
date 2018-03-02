package version_B;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ConcurrentModificationException;

public class World {
  final HashMap<Integer, Entity> ENTITIES =  new HashMap<>();
  private Thread thread = null;
  private int i = 0;
 
  World(){
    thread = new Thread(new WorldThread(this));  // points to World ^
    thread.start();
  }
  
  public HashMap<Integer, Entity> getEntities(){
    return ENTITIES;
  }
  public void addEntity(Entity e){
    ENTITIES.put(i++, e);
    //e.getSession().getAsyncRemote().sendText("added"+(i-1));
  }
  public void removeEntity(int id){
    ENTITIES.remove(id);
  }
  
  private class WorldThread implements Runnable{
    final World WORLD;
    int asteroidCount = 0;
    int asteroidMax = 20;
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
           if(e1.getType().equals("explosion")) continue;         
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

         while(asteroidCount<asteroidMax) more.add(createAsteroid(30));//(int)(Math.random()*3)+1));
         if(more.size()>0){
           for(Entity e : more)//ListIterator<Entity> li;
           WORLD.addEntity(e);
         }
         sendLoop(entities);
        
         Thread.sleep(50);
       }
     }
     catch(InterruptedException|ConcurrentModificationException | IOException e){
       try{
       entities.get(0).getSession().getBasicRemote().sendText(e.getMessage()); 
       }catch(IOException e2){
       }
     } 
    }
    private void updateLoop(Map<Integer, Entity> entities ,LinkedList<Integer> less, LinkedList<Entity> more){
      for(Integer id : entities.keySet()){
        if(entities.get(id).update())less.add(id); // killme
        else{
          Object shooting = entities.get(id).getData().get("shooting");
          if(shooting != null&&Boolean.valueOf(shooting.toString())){
            more.add(createBullet(entities.get(id))); 
          }
        }
      }
    }
    private void sendLoop(Map<Integer, Entity> entities) throws IOException{ // make better.
      String state = "";
      for(Integer id : entities.keySet()){
        state += ","+ entities.get(id).getReport();  
      }
      if(state.length()>0){
        state = "["+state.substring(1)+"]";
        for(Integer id : entities.keySet()){
          entities.get(id).send(state);  
        }
      }
    }
    
     private Entity createBullet(Entity src){
       return new Entity(new HashMap<String , Object>(), new BulletUpdate()).setType("bullet").setX(src.getX()).setY(src.getY())
           .setVelX(Math.sin(-src.getAngle())*6).setVelY(Math.cos(src.getAngle())*6).setRadius(2).setColor("silver");
     }
     private Entity createAsteroid(int radius){
       return createAsteroid(radius,Math.random()*800,Math.random()*500);
     }
     private Entity createAsteroid(int radius,double x,double y){
       asteroidCount++;
       return new Entity(new HashMap<String , Object>(), new Update(), new AsteroidReport())
           .setType("asteroid").setX(x).setY(y).setRadius(radius).setColor("lightgray")
           .setVelX(Math.random()*(Math.random()<0.5? 1 : -1)).setVelY(Math.random()*(Math.random()<0.5? 1 : -1));
     }
     
     private Entity createExplosion(Entity src){
       return new Entity(new HashMap<String,Object>(), new ExplosionUpdate()).setType("explosion")
           .setColor("explosion").setX(src.getX()).setY(src.getY()).setRadius(4);
     }
     
     private boolean collide( Entity entA , Entity entB ){
       if(entA.getColor().equals(entB.getColor())) return false;
       double X = Math.abs( entA.getX() - entB.getX() );
       double Y = Math.abs(entA.getY() - entB.getY());
       double R = entA.getRadius() + entB.getRadius();
       if(R < X || R < Y) return false;
       return(Math.sqrt(X*X + Y*Y) < R);
     }
     private void collided(int id,Entity e,LinkedList<Entity> more){
       if(e.getType().equals("asteroid")){
         asteroidCount--;
         
         int radius = e.getRadius();
         if(radius>10){
           more.add(createAsteroid(radius-10,e.getX(),e.getY()));
           more.add(createAsteroid(radius-10,e.getX(),e.getY()));
         }
       }else more.add(createExplosion(e));
     }
  }
  
}
