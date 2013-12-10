package org.sdmlib.examples.m2m.creators;

import org.sdmlib.models.pattern.creators.PatternObjectCreator;

public class PersonPOCreator extends PatternObjectCreator
{
   public Object getSendableInstance(boolean reference)
   {
      return new PersonPO();
   }
   
   public Object getValue(Object target, String attrName)
   {
      return ((PersonPO) target).get(attrName);
   }
   
   public boolean setValue(Object target, String attrName, Object value, String type)
   {
      return ((PersonPO) target).set(attrName, value);
   }
}
