/*
   Copyright (c) 2014 zuendorf 
   
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
   
package org.sdmlib.models.taskflows.util;

import org.sdmlib.models.taskflows.LogEntry;
import org.sdmlib.models.taskflows.Logger;
import org.sdmlib.models.taskflows.TaskFlow;
import org.sdmlib.serialization.EntityFactory;

import de.uniks.networkparser.IdMap;

public class LoggerCreator extends EntityFactory
{
   private final String[] properties = new String[]
   {
      Logger.PROPERTY_STARTPEER,
      TaskFlow.PROPERTY_TASKNO,
      TaskFlow.PROPERTY_IDMAP,
      TaskFlow.PROPERTY_SUBFLOW,
      TaskFlow.PROPERTY_PARENT,
      Logger.PROPERTY_ENTRIES,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new Logger();
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

      if (Logger.PROPERTY_STARTPEER.equalsIgnoreCase(attribute))
      {
         return ((Logger) target).getStartPeer();
      }

      if (TaskFlow.PROPERTY_TASKNO.equalsIgnoreCase(attribute))
      {
         return ((TaskFlow) target).getTaskNo();
      }

      if (TaskFlow.PROPERTY_IDMAP.equalsIgnoreCase(attribute))
      {
         return ((TaskFlow) target).getIdMap();
      }

      if (Logger.PROPERTY_SUBFLOW.equalsIgnoreCase(attribute))
      {
         return ((Logger) target).getSubFlow();
      }

      if (Logger.PROPERTY_PARENT.equalsIgnoreCase(attribute))
      {
         return ((Logger) target).getParent();
      }

      if (Logger.PROPERTY_ENTRIES.equalsIgnoreCase(attribute))
      {
         return ((Logger) target).getEntries();
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

      if (Logger.PROPERTY_STARTPEER.equalsIgnoreCase(attrName))
      {
         ((Logger) target).withStartPeer((org.sdmlib.models.taskflows.PeerProxy) value);
         return true;
      }

      if (TaskFlow.PROPERTY_TASKNO.equalsIgnoreCase(attrName))
      {
         ((TaskFlow) target).withTaskNo(Integer.parseInt(value.toString()));
         return true;
      }

      if (TaskFlow.PROPERTY_IDMAP.equalsIgnoreCase(attrName))
      {
         ((TaskFlow) target).withIdMap((org.sdmlib.serialization.SDMLibJsonIdMap) value);
         return true;
      }

      if (Logger.PROPERTY_SUBFLOW.equalsIgnoreCase(attrName))
      {
         ((Logger) target).setSubFlow((TaskFlow) value);
         return true;
      }

      if (Logger.PROPERTY_PARENT.equalsIgnoreCase(attrName))
      {
         ((Logger) target).setParent((TaskFlow) value);
         return true;
      }

      if (Logger.PROPERTY_ENTRIES.equalsIgnoreCase(attrName))
      {
         ((Logger) target).withEntries((LogEntry) value);
         return true;
      }
      
      if ((Logger.PROPERTY_ENTRIES + REMOVE).equalsIgnoreCase(attrName))
      {
         ((Logger) target).withoutEntries((LogEntry) value);
         return true;
      }
      
      return false;
   }
   public static IdMap createIdMap(String sessionID)
   {
      return CreatorCreator.createIdMap(sessionID);
   }
   
   //==========================================================================
   
   @Override
   public void removeObject(Object entity)
   {
      ((Logger) entity).removeYou();
   }
}
