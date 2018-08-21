package org.sdmlib.test.examples.studyrightWithAssignments.model.util;
import org.sdmlib.test.examples.studyrightWithAssignments.model.University;
import de.uniks.networkparser.list.SimpleSet;
import java.util.Collection;
import de.uniks.networkparser.list.StringList;
import de.uniks.networkparser.list.ObjectSet;
import java.util.Collections;
import org.sdmlib.test.examples.studyrightWithAssignments.model.Student;

public class UniversitySet extends SimpleSet<University>
{

   public Class<?> getTypClass()
   {
      return University.class;
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
		public static final UniversitySet EMPTY_SET = new UniversitySet().withFlag(UniversitySet.READONLY);

   public String getEntryType()
   {
      return "org.sdmlib.test.examples.studyrightWithAssignments.model.University";
   }
   @Override   public UniversitySet getNewList(boolean keyValue)
   {
      return new UniversitySet();
   }

   @SuppressWarnings("unchecked")
   public UniversitySet with(Object value)
   {
      if (value == null)
      {
         return this;
      }
      else if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<University>)value);
      }
      else if (value != null)
      {
         this.add((University) value);
      }
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

   public UniversitySet filterName(String value)
   {
      UniversitySet result = new UniversitySet();
      for(University obj : this)
      {
         if (value == obj.getName())
         {
            result.add(obj);
         }
      }
      return result;
   }

   public UniversitySet withName(String value) {
      for (University obj : this)
      {
         obj.setName(value);
      }
      return this;
   }
   public UniversitySet getStudents()
   {
      UniversitySet result = new UniversitySet();
      for (University obj : this)
      {
         result.with(obj.getStudents());
      }
      return result;
   }

   public UniversitySet filterStudents(Object value)
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
         if (! Collections.disjoint(neighbors, obj.getStudents()))
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
}