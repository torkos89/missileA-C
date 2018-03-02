package version_A;

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
    int asteroidMax = 1;
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
           boolean hit = false;
           if(entities.get(id).getData().toString().equals("explosion")) continue;
           for(Integer id2 : entities.keySet()){                   
             if(!hit&&id!=id2){               
               Map<String,Object> data2 = entities.get(id2).getData();
               if(collide(entities.get(id).getData() , data2)){   
                 hit = true;
                 collided(id2,data2,more);
                 less.addLast(id);
                 break;
                 
               }           
             }
           }

           if(hit){
             collided(id,entities.get(id).getData(),more);
             less.addLast(id);
             break;
           }
         }
         if(less.size()>0)for(int i:less)WORLD.removeEntity(i);         
                  
         
         while(asteroidCount<asteroidMax) more.add(createAsteroid(3));//(int)(Math.random()*3)+1));
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
        if(entities.get(id).update())less.add(id);// killme
        else{
          Object shooting = entities.get(id).getData().get("shooting");
          if(shooting != null&&Boolean.valueOf(shooting.toString())){
            more.add(createBullet(entities.get(id).getData())); 
          }
        }
      }
    }
    private void sendLoop(Map<Integer, Entity> entities) throws IOException{
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
    
     private Entity createBullet(Map<String , Object> src){
       HashMap<String , Object> data = new HashMap<>();
       data.put("px", src.get("px").toString());
       data.put("py", src.get("py").toString());
       double angle = Double.valueOf(src.get("angle").toString());
     
       data.put("vx", Math.sin(-angle)*6);
       data.put("vy", Math.cos(angle)*6);
       data.put("radius", 2);
       data.put("age", 0);
       data.put("type", "bullet");
       data.put("color", "silver");
       return new Entity(data, new BulletUpdate());
     }
     private Entity createAsteroid(int size){
       return createAsteroid(size,Math.random()*800,Math.random()*500);
     }
     private Entity createAsteroid(int size,double x,double y){
       HashMap<String , Object> data = new HashMap<>();
       data.put("px", x);
       data.put("py", y);
     
       data.put("vx", Math.random()*(Math.random()<0.5? 1 : -1));
       data.put("vy", Math.random()*(Math.random()<0.5? 1 : -1));
       data.put("size", size);
       data.put("radius", 10*size);
       data.put("type", "asteroid");
       data.put("color", "lightgray");
       asteroidCount++;
       return new Entity(data, new Update(), new AsteroidReport());
     }
     /*
     private Entity createExplosion(Map<String , Object> src){
       HashMap<String , Object> data = new HashMap<>();
       data.put("px", src.get("px").toString());
       data.put("py", src.get("py").toString());
     
       data.put("radius", 4);
       data.put("type", "explosion");
       data.put("color", "explosion");
       return new Entity(data, new ExplosionUpdate());
     }
     */
     private boolean collide( Map<String,Object> data1,  Map<String,Object> data2){
       if(data1.get("color").toString().equals(data2.get("color").toString())) return false;
       double X = Math.abs(Double.parseDouble(data1.get("px").toString()) - Double.parseDouble(data2.get("px").toString()));
       double Y = Math.abs(Double.parseDouble(data1.get("py").toString()) - Double.parseDouble(data2.get("py").toString()));
       double R = Double.parseDouble(data1.get("radius").toString())+Double.parseDouble(data2.get("radius").toString());
       if(R < X || R < Y) return false;
       return(Math.sqrt(X*X + Y*Y) < R);
     }
     private void collided(int id,Map<String,Object> data,LinkedList<Entity> more){
       if(data.get("type").toString().equals("asteroid")){
         asteroidCount--;
         int size = (int)Double.parseDouble(data.get("size").toString());
         if(size>0){
           more.add(createAsteroid(size-1,Double.parseDouble(data.get("px").toString())
               ,Double.parseDouble(data.get("py").toString())));
           more.add(createAsteroid(size-1,Double.parseDouble(data.get("px").toString())
               ,Double.parseDouble(data.get("py").toString())));
         }
       }//else more.add(createExplosion(data));

     }
  }
  
}
