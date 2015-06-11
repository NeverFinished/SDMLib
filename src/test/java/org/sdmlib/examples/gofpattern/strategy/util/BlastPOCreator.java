package org.sdmlib.examples.gofpattern.strategy.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import de.uniks.networkparser.json.JsonIdMap;
import org.sdmlib.examples.gofpattern.strategy.Blast;

public class BlastPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new BlastPO(new Blast[]{});
      } else {
          return new BlastPO();
      }
   }
   
   public static JsonIdMap createIdMap(String sessionID) {
      return org.sdmlib.examples.gofpattern.strategy.util.CreatorCreator.createIdMap(sessionID);
   }
}