package org.sdmlib.scenarios.creators;

import org.sdmlib.serialization.interfaces.SendableEntityCreator;
import org.sdmlib.serialization.json.JsonIdMap;
import org.sdmlib.scenarios.KanbanEntry;

public class KanbanEntryCreator implements SendableEntityCreator
{
   private final String[] properties = new String[]
   {
      KanbanEntry.PROPERTY_LOGENTRIES,
      KanbanEntry.PROPERTY_NAME,
      KanbanEntry.PROPERTY_PHASE,
      KanbanEntry.PROPERTY_LAST_DEVELOPER,
      KanbanEntry.PROPERTY_HOURS_REMAINING,
      KanbanEntry.PROPERTY_HOURS_SPEND,
      KanbanEntry.PROPERTY_PARENT,
      KanbanEntry.PROPERTY_SUBENTRIES,
      KanbanEntry.PROPERTY_LOGENTRIES,
      KanbanEntry.PROPERTY_PHASE_ENTRIES,
      KanbanEntry.PROPERTY_FILES, 
   };
   
   public String[] getProperties()
   {
      return properties;
   }
   
   public Object getSendableInstance(boolean reference)
   {
      return new KanbanEntry();
   }
   
   public Object getValue(Object target, String attrName)
   {
      return ((KanbanEntry) target).get(attrName);
   }
   
   public boolean setValue(Object target, String attrName, Object value)
   {
      return ((KanbanEntry) target).set(attrName, value);
   }
   
   public static JsonIdMap createIdMap(String sessionID)
   {
      return CreatorCreator.createIdMap(sessionID);
   }
}



