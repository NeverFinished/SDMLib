package org.sdmlib.examples.m2m.model.util;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.examples.m2m.model.GraphComponent;
import org.sdmlib.examples.m2m.model.util.GraphComponentSet;
import org.sdmlib.models.pattern.Pattern;
import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.examples.m2m.model.util.GraphPO;
import org.sdmlib.examples.m2m.model.Graph;
import org.sdmlib.examples.m2m.model.util.GraphComponentPO;

public class GraphComponentPO extends PatternObject<GraphComponentPO, GraphComponent>
{

    public GraphComponentSet allMatches()
   {
      this.setDoAllMatches(true);
      
      GraphComponentSet matches = new GraphComponentSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((GraphComponent) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public GraphComponentPO(){
      Pattern<Object> pattern = new Pattern<Object>(CreatorCreator.createIdMap("PatternObjectType"));
      pattern.addToElements(this);
   }

   public GraphComponentPO(GraphComponent... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
          return;
      }
      Pattern<Object> pattern = new Pattern<Object>(CreatorCreator.createIdMap("PatternObjectType"));
      pattern.addToElements(this);
      if(hostGraphObject.length>1){
           this.withCandidates(hostGraphObject);
      } else {
           this.withCandidates(hostGraphObject[0]);
      }
      pattern.findMatch();
  }
   public GraphComponentPO hasText(String value)
   {
      new AttributeConstraint()
      .withAttrName(GraphComponent.PROPERTY_TEXT)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public GraphComponentPO hasText(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(GraphComponent.PROPERTY_TEXT)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public GraphComponentPO createText(String value)
   {
      this.startCreate().hasText(value).endCreate();
      return this;
   }
   
   public String getText()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((GraphComponent) getCurrentMatch()).getText();
      }
      return null;
   }
   
   public GraphComponentPO withText(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((GraphComponent) getCurrentMatch()).setText(value);
      }
      return this;
   }
   
   public GraphPO hasParent()
   {
      GraphPO result = new GraphPO(new Graph[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(GraphComponent.PROPERTY_PARENT, result);
      
      return result;
   }

   public GraphPO createParent()
   {
      return this.startCreate().hasParent().endCreate();
   }

   public GraphComponentPO hasParent(GraphPO tgt)
   {
      return hasLinkConstraint(tgt, GraphComponent.PROPERTY_PARENT);
   }

   public GraphComponentPO createParent(GraphPO tgt)
   {
      return this.startCreate().hasParent(tgt).endCreate();
   }

   public Graph getParent()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((GraphComponent) this.getCurrentMatch()).getParent();
      }
      return null;
   }

}

