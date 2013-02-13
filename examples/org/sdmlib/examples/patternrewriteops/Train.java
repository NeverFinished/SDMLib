/*
   Copyright (c) 2013 zuendorf 
   
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
   
package org.sdmlib.examples.patternrewriteops;

import org.sdmlib.utils.PropertyChangeInterface;
import java.beans.PropertyChangeSupport;
import org.sdmlib.examples.patternrewriteops.creators.TrainSet;
import org.sdmlib.examples.patternrewriteops.creators.PersonSet;
import java.util.LinkedHashSet;
import org.sdmlib.serialization.json.JsonIdMap;

public class Train implements PropertyChangeInterface
{

   
   //==========================================================================
   
   public Object get(String attrName)
   {
      if (PROPERTY_STATION.equalsIgnoreCase(attrName))
      {
         return getStation();
      }

      if (PROPERTY_PASSENGERS.equalsIgnoreCase(attrName))
      {
         return getPassengers();
      }

      return null;
   }

   
   //==========================================================================
   
   public boolean set(String attrName, Object value)
   {
      if (PROPERTY_STATION.equalsIgnoreCase(attrName))
      {
         setStation((Station) value);
         return true;
      }

      if (PROPERTY_PASSENGERS.equalsIgnoreCase(attrName))
      {
         addToPassengers((Person) value);
         return true;
      }
      
      if ((PROPERTY_PASSENGERS + JsonIdMap.REMOVE).equalsIgnoreCase(attrName))
      {
         removeFromPassengers((Person) value);
         return true;
      }

      return false;
   }

   
   //==========================================================================
   
   protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
   
   public PropertyChangeSupport getPropertyChangeSupport()
   {
      return listeners;
   }

   
   //==========================================================================
   
   public void removeYou()
   {
      setStation(null);
      removeAllFromPassengers();
      getPropertyChangeSupport().firePropertyChange("REMOVE_YOU", this, null);
   }

   
   /********************************************************************
    * <pre>
    *              many                       one
    * Train ----------------------------------- Station
    *              trains                   station
    * </pre>
    */
   
   public static final String PROPERTY_STATION = "station";
   
   private Station station = null;
   
   public Station getStation()
   {
      return this.station;
   }
   
   public boolean setStation(Station value)
   {
      boolean changed = false;
      
      if (this.station != value)
      {
         Station oldValue = this.station;
         
         if (this.station != null)
         {
            this.station = null;
            oldValue.withoutTrains(this);
         }
         
         this.station = value;
         
         if (value != null)
         {
            value.withTrains(this);
         }
         
         getPropertyChangeSupport().firePropertyChange(PROPERTY_STATION, oldValue, value);
         changed = true;
      }
      
      return changed;
   }
   
   public Train withStation(Station value)
   {
      setStation(value);
      return this;
   } 
   
   public Station createStation()
   {
      Station value = new Station();
      withStation(value);
      return value;
   } 

   
   public static final TrainSet EMPTY_SET = new TrainSet();

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Train ----------------------------------- Person
    *              train                   passengers
    * </pre>
    */
   
   public static final String PROPERTY_PASSENGERS = "passengers";
   
   private PersonSet passengers = null;
   
   public PersonSet getPassengers()
   {
      if (this.passengers == null)
      {
         return Person.EMPTY_SET;
      }
   
      return this.passengers;
   }
   
   public boolean addToPassengers(Person value)
   {
      boolean changed = false;
      
      if (value != null)
      {
         if (this.passengers == null)
         {
            this.passengers = new PersonSet();
         }
         
         changed = this.passengers.add (value);
         
         if (changed)
         {
            value.withTrain(this);
            getPropertyChangeSupport().firePropertyChange(PROPERTY_PASSENGERS, null, value);
         }
      }
         
      return changed;   
   }
   
   public boolean removeFromPassengers(Person value)
   {
      boolean changed = false;
      
      if ((this.passengers != null) && (value != null))
      {
         changed = this.passengers.remove (value);
         
         if (changed)
         {
            value.setTrain(null);
            getPropertyChangeSupport().firePropertyChange(PROPERTY_PASSENGERS, value, null);
         }
      }
         
      return changed;   
   }
   
   public Train withPassengers(Person value)
   {
      addToPassengers(value);
      return this;
   } 
   
   public Train withoutPassengers(Person value)
   {
      removeFromPassengers(value);
      return this;
   } 
   
   public void removeAllFromPassengers()
   {
      LinkedHashSet<Person> tmpSet = new LinkedHashSet<Person>(this.getPassengers());
   
      for (Person value : tmpSet)
      {
         this.removeFromPassengers(value);
      }
   }
   
   public Person createPassengers()
   {
      Person value = new Person();
      withPassengers(value);
      return value;
   } 
}
