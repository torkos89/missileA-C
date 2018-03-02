package version_E;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ConcurrentModificationException;

public class World implements Runnable{
  final Map<Integer, Entity> ENTITIES = new LinkedHashMap<>();
  final Map<Integer, Entity> PLAYERS = new LinkedHashMap<>();
  final int worldX = 2000;
  final int worldY = 2000;
  int asteroidCount = 0;
  int asteroidMax = 100;
  LinkedList<Entity> more = new LinkedList<>();
  LinkedList<Integer> less = new LinkedList<>();
  Map<Integer,Entity> friends = new LinkedHashMap<>();
  Map<Integer,Entity> foes = new LinkedHashMap<>();
  
  private int i = 0;
  World(){   
  }
  public void addEntity(Entity e){
    
    switch(e.getType()){
      case"ship": for(Entity ent : foes.values()){ 
        while(collide(e,ent)){
         e.setX(Math.random()*worldX) ;
         e.setY(Math.random()*worldY) ;
        }
      }
      PLAYERS.put(i,e);
      case"bullet": friends.put(i,e);break;
      case"asteroid":for(Entity ent : PLAYERS.values()){ 
        while(collide(e,ent)){
          e.setX(Math.random()*worldX) ;
          e.setY(Math.random()*worldY) ;
         }
       } 
      foes.put(i,e);break;
    }
    ENTITIES.put(i++,e);
  }
    /*
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
      friend.put(i,e);
    }
    else if(e.getType().equals("asteroid")){
      for(Entity ent : PLAYERS.values()){
        if(collide(e,ent)){
          while(collide(e,ent)){
            e.setX(Math.random()*worldX) ;
            e.setY(Math.random()*worldY) ;
          }
        }
      }
      foe.put(i,e);
    }
    ENTITIES.put(i++, e);
    //e.getSession().getAsyncRemote().sendText("added"+(i-1));
  }
  */
  public void removeEntity(int id){
    ENTITIES.remove(id);
    friends.remove(id);
    foes.remove(id);
  }
  private boolean collide(Entity entA , Entity entB){
   // if(entA.getColor().equals(entB.getColor())) return false;
   // if(entA.getColor().equals("explosion") || entB.getColor().equals("explosion")) return false;
   // if(entA.getType().equals("ship") && entB.getType().equals("bullet")) return false;
    double X = Math.abs( entA.getX() - entB.getX());
    double Y = Math.abs(entA.getY() - entB.getY());
    double R = entA.getRadius() + entB.getRadius();
    if(R < X || R < Y) return false;
    //if(entA.getType().equals("bullet") || entB.getType().equals("bullet"))
    return(Math.sqrt(X*X + Y*Y) < R);
  }

    @Override
    public void run() {
     try{
      Thread.sleep(50);
       while(true){
         updateLoop(ENTITIES, less, more);
         while(less.size()>0){
           removeEntity(less.pop());
         }
         for(int id : friends.keySet()){
          // if(less.contains(id)) continue;
           Entity e1 = friends.get(id);
           boolean hit = false;
           for(int id2 : foes.keySet()){
             //if(less.contains(id2)) continue;
             Entity e2 = foes.get(id2);
             if(collide(e1,e2)){
               hit = true;
               collided(id2,e2,more);
               less.addLast(id2);
               break;
             }
           }
           if(hit){
             collided(id,e1,more);
             less.addLast(id); 
           }
         }
         while(less.size()>0){
           removeEntity(less.pop());
         }
         while(asteroidCount<asteroidMax) more.add(createAsteroid(((int)(Math.random()*3)+1)*10));//(int)(Math.random()*3)+1));
         while(more.size()>0){
           addEntity(more.pop());
         }
         sendLoop(ENTITIES, PLAYERS);
         Thread.sleep(50);
       }
     }
     catch(InterruptedException|ConcurrentModificationException|IOException e){
     }
    }

    private void updateLoop(Map<Integer, Entity> entities ,LinkedList<Integer> less, LinkedList<Entity> more){
      for(Integer id : entities.keySet()){
        entities.get(id).update(id, less, more);    
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

     private Entity createAsteroid(int radius){
       return createAsteroid(radius,Math.random()*worldX,Math.random()*worldY);
     }
     private Entity createAsteroid(int radius,double x,double y){  
       asteroidCount++;
       return new Entity(new Update(worldX,worldY), new AsteroidReport())
           .setType("asteroid").setX(x).setY(y).setRadius(radius).setColor("lightgray")
           .setVelX(Math.random()*(Math.random()<0.5? 1 : -1)*(4-radius/10)).setVelY(Math.random()*(Math.random()<0.5? 1 : -1)*(4-radius/10));
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
  
//}
