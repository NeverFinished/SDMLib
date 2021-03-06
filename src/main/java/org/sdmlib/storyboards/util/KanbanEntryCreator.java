/*
   Copyright (c) 2015 zuendorf
   
   Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
   and associated documentation files (the "Software"), to deal in the Software without restriction, 
   including without limitation the rights to use, copy, modify, merge, publish, distribute, 
   sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is 
   furnished to do so, subject to the following conditions: 
   
   The above copyright notice and this permission notice shall be included in all copies or 
   substantial portions of the Software. 
   
   The Software shall be used for Good, not Evil. 
   
   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
   BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
   DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
   
package org.sdmlib.storyboards.util;

import org.sdmlib.serialization.EntityFactory;
import org.sdmlib.storyboards.KanbanEntry;
import org.sdmlib.storyboards.LogEntryStoryBoard;

import de.uniks.networkparser.IdMap;

public class KanbanEntryCreator extends EntityFactory
{
   private final String[] properties = new String[]
   {
      KanbanEntry.PROPERTY_OLDNOOFLOGENTRIES,
      KanbanEntry.PROPERTY_PHASES,
      KanbanEntry.PROPERTY_LOGENTRIES,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new KanbanEntry();
   }
   
   @Override
   public Object getValue(Object target, String attrName)
   {
      int pos = attrName.indexOf('.');
      String attribute = attrName;
      
      if (pos > 0)
      {
         attribute = attrName.substring(0, pos);
      }

      if (KanbanEntry.PROPERTY_OLDNOOFLOGENTRIES.equalsIgnoreCase(attribute))
      {
         return ((KanbanEntry) target).getOldNoOfLogEntries();
      }

      if (KanbanEntry.PROPERTY_PHASES.equalsIgnoreCase(attribute))
      {
         return ((KanbanEntry) target).getPhases();
      }

      if (KanbanEntry.PROPERTY_LOGENTRIES.equalsIgnoreCase(attribute))
      {
         return ((KanbanEntry) target).getLogEntries();
      }
      
      return null;
   }
   
   @Override
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      if (REMOVE.equals(type) && value != null)
      {
         attrName = attrName + type;
      }

      if (KanbanEntry.PROPERTY_OLDNOOFLOGENTRIES.equalsIgnoreCase(attrName))
      {
         ((KanbanEntry) target).withOldNoOfLogEntries(Integer.parseInt(value.toString()));
         return true;
      }

      if (KanbanEntry.PROPERTY_PHASES.equalsIgnoreCase(attrName))
      {
         ((KanbanEntry) target).withPhases((String) value);
         return true;
      }

      if (KanbanEntry.PROPERTY_LOGENTRIES.equalsIgnoreCase(attrName))
      {
         ((KanbanEntry) target).withLogEntries((LogEntryStoryBoard) value);
         return true;
      }
      
      if ((KanbanEntry.PROPERTY_LOGENTRIES + REMOVE).equalsIgnoreCase(attrName))
      {
         ((KanbanEntry) target).withoutLogEntries((LogEntryStoryBoard) value);
         return true;
      }
      
      return false;
   }
   public static IdMap createIdMap(String sessionID)
   {
      return org.sdmlib.storyboards.util.CreatorCreator.createIdMap(sessionID);
   }
   
   //==========================================================================
   
   @Override
   public void removeObject(Object entity)
   {
      ((KanbanEntry) entity).removeYou();
   }
}
