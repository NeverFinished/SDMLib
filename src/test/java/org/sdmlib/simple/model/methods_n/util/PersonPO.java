package org.sdmlib.simple.model.methods_n.util;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.simple.model.methods_n.Person;

public class PersonPO extends PatternObject<PersonPO, Person>
{

    public PersonSet allMatches()
   {
      this.setDoAllMatches(true);
      
      PersonSet matches = new PersonSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Person) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public PersonPO(){
      newInstance(null);
   }

   public PersonPO(Person... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(null, hostGraphObject);
   }

   public PersonPO(String modifier)
   {
      this.setModifier(modifier);
   }
   
   //==========================================================================
   
   public boolean checkSomething()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Person) getCurrentMatch()).checkSomething();
      }
      return false;
   }

}