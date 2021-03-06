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
   
package org.sdmlib.replication.util;

import org.sdmlib.replication.SeppelScope;
import org.sdmlib.replication.SeppelSpaceProxy;
import org.sdmlib.serialization.EntityFactory;

import de.uniks.networkparser.IdMap;

public class SeppelScopeCreator extends EntityFactory
{
   private final String[] properties = new String[]
   {
      SeppelScope.PROPERTY_SCOPENAME,
      SeppelScope.PROPERTY_SUBSCOPES,
      SeppelScope.PROPERTY_SUPERSCOPES,
      SeppelScope.PROPERTY_SPACES,
      SeppelScope.PROPERTY_OBSERVEDOBJECTS,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new SeppelScope();
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

      if (SeppelScope.PROPERTY_SCOPENAME.equalsIgnoreCase(attribute))
      {
         return ((SeppelScope) target).getScopeName();
      }

      if (SeppelScope.PROPERTY_SUBSCOPES.equalsIgnoreCase(attribute))
      {
         return ((SeppelScope) target).getSubScopes();
      }

      if (SeppelScope.PROPERTY_SUPERSCOPES.equalsIgnoreCase(attribute))
      {
         return ((SeppelScope) target).getSuperScopes();
      }

      if (SeppelScope.PROPERTY_SPACES.equalsIgnoreCase(attribute))
      {
         return ((SeppelScope) target).getSpaces();
      }

      if (SeppelScope.PROPERTY_OBSERVEDOBJECTS.equalsIgnoreCase(attribute))
      {
         return ((SeppelScope) target).getObservedObjects();
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

      if (SeppelScope.PROPERTY_SCOPENAME.equalsIgnoreCase(attrName))
      {
         ((SeppelScope) target).withScopeName((String) value);
         return true;
      }

      if (SeppelScope.PROPERTY_SUBSCOPES.equalsIgnoreCase(attrName))
      {
         ((SeppelScope) target).withSubScopes((SeppelScope) value);
         return true;
      }
      
      if ((SeppelScope.PROPERTY_SUBSCOPES + REMOVE).equalsIgnoreCase(attrName))
      {
         ((SeppelScope) target).withoutSubScopes((SeppelScope) value);
         return true;
      }

      if (SeppelScope.PROPERTY_SUPERSCOPES.equalsIgnoreCase(attrName))
      {
         ((SeppelScope) target).withSuperScopes((SeppelScope) value);
         return true;
      }
      
      if ((SeppelScope.PROPERTY_SUPERSCOPES + REMOVE).equalsIgnoreCase(attrName))
      {
         ((SeppelScope) target).withoutSuperScopes((SeppelScope) value);
         return true;
      }

      if (SeppelScope.PROPERTY_SPACES.equalsIgnoreCase(attrName))
      {
         ((SeppelScope) target).withSpaces((SeppelSpaceProxy) value);
         return true;
      }
      
      if ((SeppelScope.PROPERTY_SPACES + REMOVE).equalsIgnoreCase(attrName))
      {
         ((SeppelScope) target).withoutSpaces((SeppelSpaceProxy) value);
         return true;
      }

      if (SeppelScope.PROPERTY_OBSERVEDOBJECTS.equalsIgnoreCase(attrName))
      {
         ((SeppelScope) target).withObservedObjects((Object) value);
         return true;
      }
      
      if ((SeppelScope.PROPERTY_OBSERVEDOBJECTS + REMOVE).equalsIgnoreCase(attrName))
      {
         ((SeppelScope) target).withoutObservedObjects((Object) value);
         return true;
      }
      
      return false;
   }
   public static IdMap createIdMap(String sessionID)
   {
      return org.sdmlib.replication.util.CreatorCreator.createIdMap(sessionID);
   }
   
   //==========================================================================
   
   @Override
   public void removeObject(Object entity)
   {
      ((SeppelScope) entity).removeYou();
   }
}
