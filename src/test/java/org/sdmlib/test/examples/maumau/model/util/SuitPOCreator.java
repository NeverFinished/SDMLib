package org.sdmlib.test.examples.maumau.model.util;

import org.sdmlib.models.pattern.util.PatternObjectCreator;
import org.sdmlib.test.examples.maumau.model.Suit;

import de.uniks.networkparser.IdMap;

public class SuitPOCreator extends PatternObjectCreator
{
   @Override
   public Object getSendableInstance(boolean reference)
   {
      if(reference) {
          return new SuitPO(new Suit[]{});
      } else {
          return new SuitPO();
      }
   }
   
   public static IdMap createIdMap(String sessionID) {
      return org.sdmlib.test.examples.maumau.model.util.CreatorCreator.createIdMap(sessionID);
   }
}
