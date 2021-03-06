package org.sdmlib.test.examples.gofpattern.strategy.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import org.sdmlib.test.examples.gofpattern.strategy.MoveDown;

import de.uniks.networkparser.IdMap;

public class MoveDownPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new MoveDownPO(new MoveDown[]{});
      } else {
          return new MoveDownPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return org.sdmlib.test.examples.gofpattern.strategy.util.CreatorCreator.createIdMap(sessionID);
   }
}
