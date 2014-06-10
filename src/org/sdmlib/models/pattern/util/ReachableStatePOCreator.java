package org.sdmlib.models.pattern.util;

import org.sdmlib.models.pattern.ReachabilityGraph;

import de.uniks.networkparser.json.JsonIdMap;

public class ReachableStatePOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
         return new ReachabilityGraphPO(new ReachabilityGraph[]{});
      } else {
         return new ReachabilityGraphPO();
      }
   }
   
   public static JsonIdMap createIdMap(String sessionID)
   {
      return CreatorCreator.createIdMap(sessionID);
   }
}

