package org.sdmlib.replication.util;

import org.sdmlib.models.pattern.PatternObject;

public class ObjectPO extends PatternObject<ObjectPO, Object>
{

    public ObjectSet allMatches()
   {
      this.setDoAllMatches(true);
      
      ObjectSet matches = new ObjectSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Object) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public ObjectPO(){
      newInstance(org.sdmlib.replication.util.CreatorCreator.createIdMap("PatternObjectType"));
   }

   public ObjectPO(Object... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(org.sdmlib.replication.util.CreatorCreator.createIdMap("PatternObjectType"), hostGraphObject);
   }

   public ObjectPO(String modifier)
   {
      this.setModifier(modifier);
   }
}
