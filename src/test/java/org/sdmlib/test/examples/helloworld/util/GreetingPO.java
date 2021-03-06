package org.sdmlib.test.examples.helloworld.util;

import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.Pattern;
import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.test.examples.helloworld.Greeting;
import org.sdmlib.test.examples.helloworld.GreetingMessage;
import org.sdmlib.test.examples.helloworld.Person;

public class GreetingPO extends PatternObject<GreetingPO, Greeting>
{
   public GreetingPO(){
      newInstance(CreatorCreator.createIdMap("PatternObjectType"));
   }

   public GreetingPO(Greeting... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(CreatorCreator.createIdMap("PatternObjectType"), hostGraphObject);
  }
   
   
    public GreetingSet allMatches()
   {
      this.setDoAllMatches(true);
      
      GreetingSet matches = new GreetingSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Greeting) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }
   
     /**
    * 
    * @see <a href='../../../../../../../../../src/test/java/org/sdmlib/test/examples/helloworld/HelloWorldTTC2011.java'>HelloWorldTTC2011.java</a>
* @see org.sdmlib.test.examples.helloworld.HelloWorldTTC2011#testTTC2011HelloWorldConstantTransformation1
 */
   public GreetingPO hasText(String value)
   {
      new AttributeConstraint()
      .withAttrName(Greeting.PROPERTY_TEXT)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
     /**
    * 
    * @see <a href='../../../../../../../../../src/test/java/org/sdmlib/test/examples/helloworld/HelloWorldTTC2011.java'>HelloWorldTTC2011.java</a>
* @see org.sdmlib.test.examples.helloworld.HelloWorldTTC2011#testTTC2011HelloWorldConstantTransformation1
 */
   public GreetingPO hasText(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Greeting.PROPERTY_TEXT)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public GreetingPO createText(String value)
   {
      this.startCreate().hasText(value).endCreate();
      return this;
   }
   
   public String getText()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Greeting) getCurrentMatch()).getText();
      }
      return null;
   }
   
   public GreetingPO withText(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Greeting) getCurrentMatch()).setText(value);
      }
      return this;
   }
   
     /**
    * 
    * @see <a href='../../../../../../../../../src/test/java/org/sdmlib/test/examples/helloworld/HelloWorldTTC2011.java'>HelloWorldTTC2011.java</a>
* @see org.sdmlib.test.examples.helloworld.HelloWorldTTC2011#testTTC2011HelloWorldConstantTransformation2WithReferences
 * @see org.sdmlib.test.examples.helloworld.HelloWorldTTC2011#testTTC2011HelloWorldModelToText
 */
   public GreetingMessagePO hasGreetingMessage()
   {
      GreetingMessagePO result = new GreetingMessagePO();
      result.setModifier(this.getPattern().getModifier());
      
      super.hasLink(Greeting.PROPERTY_GREETINGMESSAGE, result);
      
      return result;
   }

   public GreetingMessagePO createGreetingMessage()
   {
      return this.startCreate().hasGreetingMessage().endCreate();
   }

     /**
    * 
    * @see <a href='../../../../../../../../../src/test/java/org/sdmlib/test/examples/helloworld/HelloWorldTTC2011.java'>HelloWorldTTC2011.java</a>
* @see org.sdmlib.test.examples.helloworld.HelloWorldTTC2011#testTTC2011HelloWorldConstantTransformation2WithReferences
 * @see org.sdmlib.test.examples.helloworld.HelloWorldTTC2011#testTTC2011HelloWorldModelToText
 */
   public GreetingPO hasGreetingMessage(GreetingMessagePO tgt)
   {
      return hasLinkConstraint(tgt, Greeting.PROPERTY_GREETINGMESSAGE);
   }

   public GreetingPO createGreetingMessage(GreetingMessagePO tgt)
   {
      return this.startCreate().hasGreetingMessage(tgt).endCreate();
   }

   public GreetingMessage getGreetingMessage()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Greeting) this.getCurrentMatch()).getGreetingMessage();
      }
      return null;
   }

     /**
    * 
    * @see <a href='../../../../../../../../../src/test/java/org/sdmlib/test/examples/helloworld/HelloWorldTTC2011.java'>HelloWorldTTC2011.java</a>
* @see org.sdmlib.test.examples.helloworld.HelloWorldTTC2011#testTTC2011HelloWorldConstantTransformation2WithReferences
 * @see org.sdmlib.test.examples.helloworld.HelloWorldTTC2011#testTTC2011HelloWorldModelToText
 */
   public PersonPO hasPerson()
   {
      PersonPO result = new PersonPO();
      result.setModifier(this.getPattern().getModifier());
      
      super.hasLink(Greeting.PROPERTY_PERSON, result);
      
      return result;
   }

   public PersonPO createPerson()
   {
      return this.startCreate().hasPerson().endCreate();
   }

     /**
    * 
    * @see <a href='../../../../../../../../../src/test/java/org/sdmlib/test/examples/helloworld/HelloWorldTTC2011.java'>HelloWorldTTC2011.java</a>
* @see org.sdmlib.test.examples.helloworld.HelloWorldTTC2011#testTTC2011HelloWorldConstantTransformation2WithReferences
 * @see org.sdmlib.test.examples.helloworld.HelloWorldTTC2011#testTTC2011HelloWorldModelToText
 */
   public GreetingPO hasPerson(PersonPO tgt)
   {
      return hasLinkConstraint(tgt, Greeting.PROPERTY_PERSON);
   }

   public GreetingPO createPerson(PersonPO tgt)
   {
      return this.startCreate().hasPerson(tgt).endCreate();
   }

   public Person getPerson()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Greeting) this.getCurrentMatch()).getPerson();
      }
      return null;
   }

   public GreetingPO hasTgt()
   {
      GreetingPO result = new GreetingPO(new org.sdmlib.test.examples.helloworld.Greeting[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Greeting.PROPERTY_TGT, result);
      
      return result;
   }

   public GreetingPO createTgt()
   {
      return this.startCreate().hasTgt().endCreate();
   }

   public GreetingPO hasTgt(GreetingPO tgt)
   {
      return hasLinkConstraint(tgt, Greeting.PROPERTY_TGT);
   }

   public GreetingPO createTgt(GreetingPO tgt)
   {
      return this.startCreate().hasTgt(tgt).endCreate();
   }

   public Greeting getTgt()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Greeting) this.getCurrentMatch()).getTgt();
      }
      return null;
   }

   public GreetingMessagePO filterGreetingMessage()
   {
      GreetingMessagePO result = new GreetingMessagePO(new org.sdmlib.test.examples.helloworld.GreetingMessage[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Greeting.PROPERTY_GREETINGMESSAGE, result);
      
      return result;
   }

   public GreetingPO filterGreetingMessage(GreetingMessagePO tgt)
   {
      return hasLinkConstraint(tgt, Greeting.PROPERTY_GREETINGMESSAGE);
   }

   public PersonPO filterPerson()
   {
      PersonPO result = new PersonPO(new org.sdmlib.test.examples.helloworld.Person[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Greeting.PROPERTY_PERSON, result);
      
      return result;
   }

   public GreetingPO filterPerson(PersonPO tgt)
   {
      return hasLinkConstraint(tgt, Greeting.PROPERTY_PERSON);
   }

   public GreetingPO filterText(String value)
   {
      new AttributeConstraint()
      .withAttrName(Greeting.PROPERTY_TEXT)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public GreetingPO filterText(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Greeting.PROPERTY_TEXT)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public GreetingPO filterTgt()
   {
      GreetingPO result = new GreetingPO(new org.sdmlib.test.examples.helloworld.Greeting[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Greeting.PROPERTY_TGT, result);
      
      return result;
   }

   public GreetingPO filterTgt(GreetingPO tgt)
   {
      return hasLinkConstraint(tgt, Greeting.PROPERTY_TGT);
   }

   public GreetingPO filterGreeting()
   {
      GreetingPO result = new GreetingPO(new org.sdmlib.test.examples.helloworld.Greeting[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Greeting.PROPERTY_GREETING, result);
      
      return result;
   }

   public GreetingPO createGreeting()
   {
      return this.startCreate().filterGreeting().endCreate();
   }

   public GreetingPO filterGreeting(GreetingPO tgt)
   {
      return hasLinkConstraint(tgt, Greeting.PROPERTY_GREETING);
   }

   public GreetingPO createGreeting(GreetingPO tgt)
   {
      return this.startCreate().filterGreeting(tgt).endCreate();
   }

   public Greeting getGreeting()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Greeting) this.getCurrentMatch()).getGreeting();
      }
      return null;
   }


   public GreetingPO(String modifier)
   {
      this.setModifier(modifier);
   }
   public GreetingMessagePO createGreetingMessagePO()
   {
      GreetingMessagePO result = new GreetingMessagePO(new org.sdmlib.test.examples.helloworld.GreetingMessage[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Greeting.PROPERTY_GREETINGMESSAGE, result);
      
      return result;
   }

   public GreetingMessagePO createGreetingMessagePO(String modifier)
   {
      GreetingMessagePO result = new GreetingMessagePO(new org.sdmlib.test.examples.helloworld.GreetingMessage[]{});
      
      result.setModifier(modifier);
      super.hasLink(Greeting.PROPERTY_GREETINGMESSAGE, result);
      
      return result;
   }

   public GreetingPO createGreetingMessageLink(GreetingMessagePO tgt)
   {
      return hasLinkConstraint(tgt, Greeting.PROPERTY_GREETINGMESSAGE);
   }

   public GreetingPO createGreetingMessageLink(GreetingMessagePO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Greeting.PROPERTY_GREETINGMESSAGE, modifier);
   }

   public PersonPO createPersonPO()
   {
      PersonPO result = new PersonPO(new org.sdmlib.test.examples.helloworld.Person[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Greeting.PROPERTY_PERSON, result);
      
      return result;
   }

   public PersonPO createPersonPO(String modifier)
   {
      PersonPO result = new PersonPO(new org.sdmlib.test.examples.helloworld.Person[]{});
      
      result.setModifier(modifier);
      super.hasLink(Greeting.PROPERTY_PERSON, result);
      
      return result;
   }

   public GreetingPO createPersonLink(PersonPO tgt)
   {
      return hasLinkConstraint(tgt, Greeting.PROPERTY_PERSON);
   }

   public GreetingPO createPersonLink(PersonPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Greeting.PROPERTY_PERSON, modifier);
   }

   public GreetingPO createTextCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(Greeting.PROPERTY_TEXT)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public GreetingPO createTextCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(Greeting.PROPERTY_TEXT)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public GreetingPO createTextAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(Greeting.PROPERTY_TEXT)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public GreetingPO createTgtPO()
   {
      GreetingPO result = new GreetingPO(new org.sdmlib.test.examples.helloworld.Greeting[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Greeting.PROPERTY_TGT, result);
      
      return result;
   }

   public GreetingPO createTgtPO(String modifier)
   {
      GreetingPO result = new GreetingPO(new org.sdmlib.test.examples.helloworld.Greeting[]{});
      
      result.setModifier(modifier);
      super.hasLink(Greeting.PROPERTY_TGT, result);
      
      return result;
   }

   public GreetingPO createTgtLink(GreetingPO tgt)
   {
      return hasLinkConstraint(tgt, Greeting.PROPERTY_TGT);
   }

   public GreetingPO createTgtLink(GreetingPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Greeting.PROPERTY_TGT, modifier);
   }

}



