package org.sdmlib.models.classes.util;

import org.sdmlib.models.classes.ClassModel;
import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.LinkConstraint;
import org.sdmlib.models.pattern.PatternObject;

public class ClassModelPO extends PatternObject<ClassModelPO, ClassModel>
{
   public ClassModelPO startNAC()
   {
      return (ClassModelPO) super.startNAC();
   }
   
   public ClassModelPO endNAC()
   {
      return (ClassModelPO) super.endNAC();
   }
   
   public ClassModelSet allMatches()
   {
      ClassModelSet matches = new ClassModelSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((ClassModel) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }
   
   public ClazzPO hasClasses()
   {
      ClazzPO result = new ClazzPO();
      result.setModifier(this.getPattern().getModifier());
      
      super.hasLink(ClassModel.PROPERTY_CLASSES, result);
      
      return result;
   }
   
   public ClassModelPO hasClasses(ClazzPO tgt)
   {
      LinkConstraint patternLink = (LinkConstraint) new LinkConstraint()
      .withTgt(tgt).withTgtRoleName(ClassModel.PROPERTY_CLASSES)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier());
      
      this.getPattern().addToElements(patternLink);
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public ClazzSet getClasses()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((ClassModel) this.getCurrentMatch()).getClasses();
      }
      return null;
   }
   
   public AssociationPO hasAssociations()
   {
      AssociationPO result = new AssociationPO();
      result.setModifier(this.getPattern().getModifier());
      
      super.hasLink(ClassModel.PROPERTY_ASSOCIATIONS, result);
      
      return result;
   }
   
   public ClassModelPO hasAssociations(AssociationPO tgt)
   {
      LinkConstraint patternLink = (LinkConstraint) new LinkConstraint()
      .withTgt(tgt).withTgtRoleName(ClassModel.PROPERTY_ASSOCIATIONS)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier());
      
      this.getPattern().addToElements(patternLink);
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public AssociationSet getAssociations()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((ClassModel) this.getCurrentMatch()).getAssociations();
      }
      return null;
   }
   
   public ClassModelPO hasPackageName(String value)
   {
      AttributeConstraint constr = (AttributeConstraint) new AttributeConstraint()
      .withAttrName(ClassModel.PROPERTY_PACKAGENAME)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public String getPackageName()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((ClassModel) getCurrentMatch()).getPackageName();
      }
      return null;
   }
   
   public ClassModelPO hasPackageName(String lower, String upper)
   {
      AttributeConstraint constr = (AttributeConstraint) new AttributeConstraint()
      .withAttrName(ClassModel.PROPERTY_PACKAGENAME)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public ClassModelPO createPackageName(String value)
   {
      this.startCreate().hasPackageName(value).endCreate();
      return this;
   }
   
   public ClazzPO createClasses()
   {
      return this.startCreate().hasClasses().endCreate();
   }

   public ClassModelPO createClasses(ClazzPO tgt)
   {
      return this.startCreate().hasClasses(tgt).endCreate();
   }

   public AssociationPO createAssociations()
   {
      return this.startCreate().hasAssociations().endCreate();
   }

   public ClassModelPO createAssociations(AssociationPO tgt)
   {
      return this.startCreate().hasAssociations(tgt).endCreate();
   }

}



