package org.sdmlib.examples.helloworld.creators;

import org.sdmlib.examples.helloworld.GreetingMessage;
import org.sdmlib.serialization.interfaces.EntityFactory;
import org.sdmlib.serialization.json.JsonIdMap;

public class GreetingMessageCreator extends EntityFactory
{
   private final String[] properties = new String[]
   { GreetingMessage.PROPERTY_TEXT, GreetingMessage.PROPERTY_GREETING, };

   public String[] getProperties()
   {
      return properties;
   }

   public Object getSendableInstance(boolean reference)
   {
      return new GreetingMessage();
   }

   public Object getValue(Object target, String attrName)
   {
      return ((GreetingMessage) target).get(attrName);
   }

   public boolean setValue(Object target, String attrName, Object value,
         String type)
   {
      return ((GreetingMessage) target).set(attrName, value);
   }

   public static JsonIdMap createIdMap(String sessionID)
   {
      return CreatorCreator.createIdMap(sessionID);
   }

   // ==========================================================================

   @Override
   public void removeObject(Object entity)
   {
      ((GreetingMessage) entity).removeYou();
   }
}
