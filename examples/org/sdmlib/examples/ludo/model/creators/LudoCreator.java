package org.sdmlib.examples.ludo.model.creators;

import org.sdmlib.serialization.interfaces.EntityFactory;
import org.sdmlib.serialization.json.JsonIdMap;
import org.sdmlib.serialization.json.SDMLibJsonIdMap;
import org.sdmlib.examples.ludo.model.Ludo;

public class LudoCreator extends EntityFactory
{
   private final String[] properties = new String[]
   {
      Ludo.PROPERTY_DATE,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new Ludo();
   }
   
   @Override
   public Object getValue(Object target, String attrName)
   {
      if (Ludo.PROPERTY_DATE.equalsIgnoreCase(attrName))
      {
         return ((Ludo) target).getDate();
      }

      return null;
   }
   
   @Override
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      if (JsonIdMap.REMOVE.equals(type) && value != null)
      {
         attrName = attrName + type;
      }

      if (Ludo.PROPERTY_DATE.equalsIgnoreCase(attrName))
      {
         ((Ludo) target).setDate((java.awt.Point) value);
         return true;
      }
      return false;
   }
   public static JsonIdMap createIdMap(String sessionID)
   {
      JsonIdMap jsonIdMap = (JsonIdMap) new SDMLibJsonIdMap().withSessionId(sessionID);
      
               jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.creators.LudoCreator());
         jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.creators.LudoPOCreator());
         jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.creators.PlayerCreator());
         jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.creators.PlayerPOCreator());
         jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.creators.DiceCreator());
         jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.creators.DicePOCreator());
         jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.creators.FieldCreator());
         jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.creators.FieldPOCreator());
         jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.creators.PawnCreator());
         jsonIdMap.withCreator(new org.sdmlib.examples.ludo.model.creators.PawnPOCreator());

      return jsonIdMap;
   }
   
   //==========================================================================
   
   @Override
   public void removeObject(Object entity)
   {
      ((Ludo) entity).removeYou();
   }
}

