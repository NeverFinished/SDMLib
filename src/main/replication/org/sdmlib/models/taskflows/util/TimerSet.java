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

import java.util.Collection;
import java.util.Timer;

import org.sdmlib.models.taskflows.SDMTimer;

import de.uniks.networkparser.list.SimpleSet;

public class TimerSet extends SimpleSet<Timer>
{


   public TimerPO hasTimerPO()
   {
      return new TimerPO(this.toArray(new Timer[this.size()]));
   }

   @SuppressWarnings("unchecked")
   public TimerSet with(Object value)
   {
      if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<Timer>)value);
      }
      else if (value != null)
      {
         this.add((Timer) value);
      }
      
      return this;
   }
   
   public TimerSet without(Timer value)
   {
      this.remove(value);
      return this;
   }


   public static final TimerSet EMPTY_SET = new TimerSet().withFlag(TimerSet.READONLY);


   public TimerPO filterTimerPO()
   {
      return new TimerPO(this.toArray(new Timer[this.size()]));
   }


   public String getEntryType()
   {
      return "java.util.Timer";
   }

   public TimerSet()
   {
      // empty
   }

   public TimerSet(Timer... objects)
   {
      for (Timer obj : objects)
      {
         this.add(obj);
      }
   }

   public TimerSet(Collection<Timer> objects)
   {
      this.addAll(objects);
   }


   public TimerPO createTimerPO()
   {
      return new TimerPO(this.toArray(new Timer[this.size()]));
   }


   @Override
   public TimerSet getNewList(boolean keyValue)
   {
      return new TimerSet();
   }

   public SDMTimerSet instanceOfSDMTimer()
   {
      SDMTimerSet result = new SDMTimerSet();
      
      for(Object obj : this)
      {
         if (obj instanceof SDMTimer)
         {
            result.with(obj);
         }
      }
      
      return result;
   }}
