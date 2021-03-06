package org.sdmlib.test.examples.gofpattern.strategy.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import org.sdmlib.test.examples.gofpattern.strategy.Blast;

import de.uniks.networkparser.IdMap;

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
   
   public static IdMap createIdMap(String sessionID) {
      return org.sdmlib.test.examples.gofpattern.strategy.util.CreatorCreator.createIdMap(sessionID);
   }
}
