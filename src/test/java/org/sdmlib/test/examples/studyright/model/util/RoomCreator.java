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

import org.sdmlib.serialization.EntityFactory;
import org.sdmlib.test.examples.studyright.model.Assignment;
import org.sdmlib.test.examples.studyright.model.Lecture;
import org.sdmlib.test.examples.studyright.model.Room;
import org.sdmlib.test.examples.studyright.model.Student;
import org.sdmlib.test.examples.studyright.model.University;

import de.uniks.networkparser.IdMap;

public class RoomCreator extends EntityFactory
{
   private final String[] properties = new String[]
   {
      Room.PROPERTY_ROOMNO,
      Room.PROPERTY_CREDITS,
      Room.PROPERTY_NEIGHBORS,
      Room.PROPERTY_LECTURE,
      Room.PROPERTY_UNI,
      Room.PROPERTY_STUDENTS,
      Room.PROPERTY_ROOM,
   };
   
   @Override
   public String[] getProperties()
   {
      return properties;
   }
   
   @Override
   public Object getSendableInstance(boolean reference)
   {
      return new Room();
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

      if (Room.PROPERTY_ROOMNO.equalsIgnoreCase(attribute))
      {
         return ((Room) target).getRoomNo();
      }

      if (Room.PROPERTY_CREDITS.equalsIgnoreCase(attribute))
      {
         return ((Room) target).getCredits();
      }

      if (Room.PROPERTY_NEIGHBORS.equalsIgnoreCase(attribute))
      {
         return ((Room) target).getNeighbors();
      }

      if (Room.PROPERTY_LECTURE.equalsIgnoreCase(attribute))
      {
         return ((Room) target).getLecture();
      }

      if (Room.PROPERTY_UNI.equalsIgnoreCase(attribute))
      {
         return ((Room) target).getUni();
      }

      if (Room.PROPERTY_STUDENTS.equalsIgnoreCase(attribute))
      {
         return ((Room) target).getStudents();
      }

      if (Room.PROPERTY_ROOM.equalsIgnoreCase(attribute))
      {
         return ((Room) target).getRoom();
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

      if (Room.PROPERTY_ROOMNO.equalsIgnoreCase(attrName))
      {
         ((Room) target).setRoomNo((String) value);
         return true;
      }

      if (Room.PROPERTY_CREDITS.equalsIgnoreCase(attrName))
      {
         ((Room) target).setCredits(Integer.parseInt(value.toString()));
         return true;
      }

      if (Room.PROPERTY_NEIGHBORS.equalsIgnoreCase(attrName))
      {
         ((Room) target).addToNeighbors((Room) value);
         return true;
      }
      
      if ((Room.PROPERTY_NEIGHBORS + REMOVE).equalsIgnoreCase(attrName))
      {
         ((Room) target).removeFromNeighbors((Room) value);
         return true;
      }

      if (Room.PROPERTY_LECTURE.equalsIgnoreCase(attrName))
      {
         ((Room) target).addToLecture((Lecture) value);
         return true;
      }
      
      if ((Room.PROPERTY_LECTURE + REMOVE).equalsIgnoreCase(attrName))
      {
         ((Room) target).removeFromLecture((Lecture) value);
         return true;
      }

      if (Room.PROPERTY_UNI.equalsIgnoreCase(attrName))
      {
         ((Room) target).setUni((University) value);
         return true;
      }

      if (Room.PROPERTY_STUDENTS.equalsIgnoreCase(attrName))
      {
         ((Room) target).addToStudents((Student) value);
         return true;
      }
      
      if ((Room.PROPERTY_STUDENTS + REMOVE).equalsIgnoreCase(attrName))
      {
         ((Room) target).removeFromStudents((Student) value);
         return true;
      }

      if (Room.PROPERTY_ROOM.equalsIgnoreCase(attrName))
      {
         ((Room) target).setRoom((Assignment) value);
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
      ((Room) entity).removeYou();
   }
}
