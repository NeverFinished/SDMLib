package org.sdmlib.examples.ludo.model.creators;

import org.sdmlib.serialization.interfaces.EntityFactory;
import org.sdmlib.serialization.json.JsonIdMap;
import org.sdmlib.serialization.json.SDMLibJsonIdMap;
import org.sdmlib.examples.ludo.model.Player;

public class PlayerCreator extends EntityFactory
{
   private final String[] properties = new String[]
   {
      Player.PROPERTY_COLOR,
      Player.PROPERTY_ENUMCOLOR,
      Player.PROPERTY_NAME,
      Player.PROPERTY_X,
      Player.PROPERTY_Y,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new Player();
   }
   
   @Override
   public Object getValue(Object target, String attrName)
   {
      if (Player.PROPERTY_COLOR.equalsIgnoreCase(attrName))
      {
         return ((Player) target).getColor();
      }

      if (Player.PROPERTY_ENUMCOLOR.equalsIgnoreCase(attrName))
      {
         return ((Player) target).getEnumColor();
      }

      if (Player.PROPERTY_NAME.equalsIgnoreCase(attrName))
      {
         return ((Player) target).getName();
      }

      if (Player.PROPERTY_X.equalsIgnoreCase(attrName))
      {
         return ((Player) target).getX();
      }

      if (Player.PROPERTY_Y.equalsIgnoreCase(attrName))
      {
         return ((Player) target).getY();
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

      if (Player.PROPERTY_COLOR.equalsIgnoreCase(attrName))
      {
         ((Player) target).setColor((String) value);
         return true;
      }

      if (Player.PROPERTY_ENUMCOLOR.equalsIgnoreCase(attrName))
      {
         ((Player) target).setEnumColor((java.awt.Point) value);
         return true;
      }

      if (Player.PROPERTY_NAME.equalsIgnoreCase(attrName))
      {
         ((Player) target).setName((String) value);
         return true;
      }

      if (Player.PROPERTY_X.equalsIgnoreCase(attrName))
      {
         ((Player) target).setX(Integer.parseInt(value.toString()));
         return true;
      }

      if (Player.PROPERTY_Y.equalsIgnoreCase(attrName))
      {
         ((Player) target).setY(Integer.parseInt(value.toString()));
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
      ((Player) entity).removeYou();
   }
}

