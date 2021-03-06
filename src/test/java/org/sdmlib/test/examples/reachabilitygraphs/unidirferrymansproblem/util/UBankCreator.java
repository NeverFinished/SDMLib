/*
   Copyright (c) 2017 zuendorf
   
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
   
package org.sdmlib.test.examples.reachabilitygraphs.unidirferrymansproblem.util;

import de.uniks.networkparser.interfaces.AggregatedEntityCreator;
import org.sdmlib.test.examples.reachabilitygraphs.unidirferrymansproblem.UBank;
import de.uniks.networkparser.list.ObjectSet;
import de.uniks.networkparser.interfaces.SendableEntityCreator;
import de.uniks.networkparser.IdMap;
import org.sdmlib.test.examples.reachabilitygraphs.unidirferrymansproblem.UCargo;

public class UBankCreator implements AggregatedEntityCreator
{
   public static final UBankCreator it = new UBankCreator();
   
   private final String[] properties = new String[]
   {
      UBank.PROPERTY_NAME,
      UBank.PROPERTY_CARGOS,
   };
   
   private final String[] upProperties = new String[]
   {
   };
   
   private final String[] downProperties = new String[]
   {
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public String[] getUpProperties()
   {
      return upProperties;
   }
   
   @Override
   public String[] getDownProperties()
   {
      return downProperties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new UBank();
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

      if (UBank.PROPERTY_NAME.equalsIgnoreCase(attribute))
      {
         return ((UBank) target).getName();
      }

      if (UBank.PROPERTY_CARGOS.equalsIgnoreCase(attribute))
      {
         return ((UBank) target).getCargos();
      }
      
      return null;
   }
   
   @Override
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      if (UBank.PROPERTY_NAME.equalsIgnoreCase(attrName))
      {
         ((UBank) target).setName((String) value);
         return true;
      }

      if(SendableEntityCreator.REMOVE_YOU.equals(type)) {
           ((UBank)target).removeYou();
           return true;
      }
      if (SendableEntityCreator.REMOVE.equals(type) && value != null)
      {
         attrName = attrName + type;
      }

      if (UBank.PROPERTY_CARGOS.equalsIgnoreCase(attrName))
      {
         ((UBank) target).withCargos((UCargo) value);
         return true;
      }
      
      if ((UBank.PROPERTY_CARGOS + SendableEntityCreator.REMOVE).equalsIgnoreCase(attrName))
      {
         ((UBank) target).withoutCargos((UCargo) value);
         return true;
      }
      
      return false;
   }
   public static IdMap createIdMap(String sessionID)
   {
      return org.sdmlib.test.examples.reachabilitygraphs.unidirferrymansproblem.util.CreatorCreator.createIdMap(sessionID);
   }
   
   //==========================================================================
      public void removeObject(Object entity)
   {
      ((UBank) entity).removeYou();
   }
}
