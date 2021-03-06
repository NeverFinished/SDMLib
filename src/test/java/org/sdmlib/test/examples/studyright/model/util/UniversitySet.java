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
   
package org.sdmlib.test.examples.studyright.model.util;

import java.util.Collection;
import java.util.Collections;

import org.sdmlib.test.examples.studyright.model.Room;
import org.sdmlib.test.examples.studyright.model.Student;
import org.sdmlib.test.examples.studyright.model.University;

import de.uniks.networkparser.list.ObjectSet;
import de.uniks.networkparser.list.SimpleSet;
import de.uniks.networkparser.list.StringList;

public class UniversitySet extends SimpleSet<University>
{


   public UniversityPO hasUniversityPO()
   {
      return new UniversityPO(this.toArray(new University[this.size()]));
   }

   @SuppressWarnings("unchecked")
   public UniversitySet with(Object value)
   {
      if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<University>)value);
      }
      else if (value != null)
      {
         this.add((University) value);
      }
      
      return this;
   }
   
   public UniversitySet without(University value)
   {
      this.remove(value);
      return this;
   }

   public StringList getName()
   {
      StringList result = new StringList();
      
      for (University obj : this)
      {
         result.add(obj.getName());
      }
      
      return result;
   }

   public UniversitySet hasName(String value)
   {
      UniversitySet result = new UniversitySet();
      
      for (University obj : this)
      {
         if (value.equals(obj.getName()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }

   public UniversitySet withName(String value)
   {
      for (University obj : this)
      {
         obj.setName(value);
      }
      
      return this;
   }

   public RoomSet getRooms()
   {
      RoomSet result = new RoomSet();
      
      for (University obj : this)
      {
         result.addAll(obj.getRooms());
      }
      
      return result;
   }

   public UniversitySet hasRooms(Object value)
   {
      ObjectSet neighbors = new ObjectSet();

      if (value instanceof Collection)
      {
         neighbors.addAll((Collection<?>) value);
      }
      else
      {
         neighbors.add(value);
      }
      
      UniversitySet answer = new UniversitySet();
      
      for (University obj : this)
      {
         if ( ! Collections.disjoint(neighbors, obj.getRooms()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   public UniversitySet withRooms(Room value)
   {
      for (University obj : this)
      {
         obj.withRooms(value);
      }
      
      return this;
   }

   public UniversitySet withoutRooms(Room value)
   {
      for (University obj : this)
      {
         obj.withoutRooms(value);
      }
      
      return this;
   }

   public StudentSet getStudents()
   {
      StudentSet result = new StudentSet();
      
      for (University obj : this)
      {
         result.addAll(obj.getStudents());
      }
      
      return result;
   }

   public UniversitySet hasStudents(Object value)
   {
      ObjectSet neighbors = new ObjectSet();

      if (value instanceof Collection)
      {
         neighbors.addAll((Collection<?>) value);
      }
      else
      {
         neighbors.add(value);
      }
      
      UniversitySet answer = new UniversitySet();
      
      for (University obj : this)
      {
         if ( ! Collections.disjoint(neighbors, obj.getStudents()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   public UniversitySet withStudents(Student value)
   {
      for (University obj : this)
      {
         obj.withStudents(value);
      }
      
      return this;
   }

   public UniversitySet withoutStudents(Student value)
   {
      for (University obj : this)
      {
         obj.withoutStudents(value);
      }
      
      return this;
   }


   public static final UniversitySet EMPTY_SET = new UniversitySet().withFlag(UniversitySet.READONLY);
   public UniversitySet hasName(String lower, String upper)
   {
      UniversitySet result = new UniversitySet();
      
      for (University obj : this)
      {
         if (lower.compareTo(obj.getName()) <= 0 && obj.getName().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }



   public UniversityPO filterUniversityPO()
   {
      return new UniversityPO(this.toArray(new University[this.size()]));
   }


   public String getEntryType()
   {
      return "org.sdmlib.test.examples.studyright.model.University";
   }

   /**
    * Loop through the current set of University objects and collect those University objects where the name attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of University objects that match the parameter
    */
   public UniversitySet filterName(String value)
   {
      UniversitySet result = new UniversitySet();
      
      for (University obj : this)
      {
         if (value.equals(obj.getName()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of University objects and collect those University objects where the name attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of University objects that match the parameter
    */
   public UniversitySet filterName(String lower, String upper)
   {
      UniversitySet result = new UniversitySet();
      
      for (University obj : this)
      {
         if (lower.compareTo(obj.getName()) <= 0 && obj.getName().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   public UniversitySet()
   {
      // empty
   }

   public UniversitySet(University... objects)
   {
      for (University obj : objects)
      {
         this.add(obj);
      }
   }

   public UniversitySet(Collection<University> objects)
   {
      this.addAll(objects);
   }


   public UniversityPO createUniversityPO()
   {
      return new UniversityPO(this.toArray(new University[this.size()]));
   }


   @Override
   public UniversitySet getNewList(boolean keyValue)
   {
      return new UniversitySet();
   }

   /**
    * Loop through the current set of University objects and collect those University objects where the name attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of University objects that match the parameter
    */
   public UniversitySet createNameCondition(String value)
   {
      UniversitySet result = new UniversitySet();
      
      for (University obj : this)
      {
         if (value.equals(obj.getName()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of University objects and collect those University objects where the name attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of University objects that match the parameter
    */
   public UniversitySet createNameCondition(String lower, String upper)
   {
      UniversitySet result = new UniversitySet();
      
      for (University obj : this)
      {
         if (lower.compareTo(obj.getName()) <= 0 && obj.getName().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }

}
