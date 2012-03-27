/*
   Copyright (c) 2012 zuendorf 
   
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
   
package org.sdmlib.models.classes;

import java.util.LinkedHashSet;

import org.sdmlib.codegen.CGUtil;
import org.sdmlib.codegen.Parser;
import org.sdmlib.scenarios.PhaseEntry;
import org.sdmlib.serialization.json.JsonIdMap;
import org.sdmlib.utils.StrUtil;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

public class Role
{
   public static final String ONE = "one";
   public static final String MANY = "many";
   public static final String VANILLA = "vanilla";
   public static final String AGGREGATION = "aggregation";
   
   
   //==========================================================================
   
   public static final String PROPERTY_CLAZZ = "clazz";
   
   private Clazz clazz= null;
   
   public Clazz getClazz()
   {
      return this.clazz;
   }
   
   public boolean setClazz(Clazz value)
   {
      boolean changed = false;
      
      if (this.clazz != value)
      {
         Clazz oldValue = this.clazz;
         
         this.clazz = value;
         
         // getPropertyChangeSupport().firePropertyChange(PROPERTY_ASSOC, null, value);
         changed = true;
      }
      
      return changed;
   }
   
   public Role withClazz(Clazz value)
   {
      setClazz(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_CARD = "card";
   
   private String card= MANY;
   
   public String getCard()
   {
      return this.card;
   }
   
   public void setCard(String value)
   {
      this.card = value;
   }
   
   public Role withCard(String value)
   {
      setCard(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_NAME = "name";
   
   private String name= null;
   
   public String getName()
   {
      return this.name;
   }
   
   public void setName(String value)
   {
      this.name = value;
   }
   
   public Role withName(String value)
   {
      setName(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_KIND = "kind";
   
   private String kind= VANILLA;
   
   public String getKind()
   {
      return this.kind;
   }
   
   public void setKind(String value)
   {
      this.kind = value;
   }
   
   public Role withKind(String value)
   {
      setKind(value);
      return this;
   }

   public void generate(String rootDir, Role partnerRole, boolean doGenerate)
   {
      Parser myParser = getClazz().getOrCreateParser(rootDir);
      
      int pos = myParser.indexOf(Parser.ATTRIBUTE + ":" + partnerRole.getName());
      
      if (pos < 0)
      {
         // add attribute declaration in class file
         StringBuilder text = new StringBuilder();
         
         if (StrUtil.stringEquals(partnerRole.getCard(), MANY))
         {
            generateToManyRole(partnerRole, text);
            getClazz().insertImport(LinkedHashSet.class.getName());
         }
         else
         {
            generateToOneRole(partnerRole, text);
         }

         pos = myParser.indexOf(Parser.CLASS_END);
         myParser.getFileBody().insert(pos, text.toString());
         getClazz().setFileHasChanged(true);
      }
      
      if (StrUtil.stringEquals(partnerRole.getCard(), MANY))
      {
         generateEmptySetInPartnerClass(rootDir, partnerRole, doGenerate);
      }
      
      insertCaseInGenericGet(myParser, partnerRole);

      if (StrUtil.stringEquals(partnerRole.getCard(), MANY))
      {
         insertCaseInGenericSetToMany(myParser, partnerRole);
      }
      else
      {
         insertCaseInGenericSetToOne(myParser, partnerRole);
      }
      
      getClazz().printFile(doGenerate);
      
      
      // generate property in creator class
      Parser creatorParser = getClazz().getOrCreateParserForCreatorClass(rootDir);
      
      insertPropertyInCreatorClass(creatorParser, partnerRole);
      
      getClazz().printCreatorFile(doGenerate);
   }
   
   private void insertPropertyInCreatorClass(Parser parser, Role partnerRole)
   {
      String key = Parser.ATTRIBUTE + ":properties";
      int pos = parser.indexOf(key);

      if (pos < 0)
      {
         // ups, did not find generic get method. 
         System.err.println("Warning: SDMLib codgen for role " + partnerRole.getName() + " for creator class for " + getClazz().getName() 
            + ": \nDid not find properties field. Should have been generated by my clazz. " 
            + "\nCould not add required code fragment there. :( ");

         return;
      }

      // OK, found method, parse its body to find if that handles me. 
      int endOfStringArrayInit = parser.getEndOfAttributeInitialization();
      
      String propertyName = "PROPERTY_" + partnerRole.getName().toUpperCase();
      
      int propertyNameIndex = parser.getFileBody().indexOf(propertyName, pos);

      if (propertyNameIndex < 0 || propertyNameIndex > endOfStringArrayInit)
      {         
         // need to add property to string array
         
         StringBuilder text = new StringBuilder(  "   className.PROPERTY_NAME,\n   ");

         CGUtil.replaceAll(text, 
            "className", CGUtil.shortClassName(getClazz().getName()),
            "PROPERTY_NAME", "PROPERTY_" + partnerRole.getName().toUpperCase()
            );

         parser.getFileBody().insert(endOfStringArrayInit, text.toString());
         getClazz().setCreatorFileHasChanged(true);
      }
   }


   
   private void insertCaseInGenericSetToMany(Parser parser, Role partnerRole)
   {
      int pos = parser.indexOf(Parser.METHOD + ":set(String,Object)");

      if (pos < 0)
      {
         // ups, did not find generic set method. 
         System.err.println("Warning: SDMLib codgen for role " + partnerRole.getName() + " for class " + getClazz().getName() 
            + ": \nDid not find method set(String,Object). Should have been generated by my clazz. " 
            + "\nCould not add required code fragment there. :( ");

         return;
      }

      // OK, found method, parse its body to find if that handles me. 
      int methodBodyStartPos = parser.getMethodBodyStartPos();
      
      pos = parser.methodBodyIndexOf(Parser.NAME_TOKEN + ":PROPERTY_" + partnerRole.getName().toUpperCase() , methodBodyStartPos);

      if (pos < 0)
      {         
         // need to add if block to generic set method
         parser.methodBodyIndexOf(Parser.METHOD_END, methodBodyStartPos);
         
         int lastIfEndPos = parser.lastIfEnd + 2; // add 1 to be after } and 1 to be after \n
         if (lastIfEndPos - 2  < 0)
         {
            lastIfEndPos = methodBodyStartPos + 1;
         }
         
         StringBuilder text = new StringBuilder
            (  "\n      if (PROPERTY_NAME.equalsIgnoreCase(attrName))" +
               "\n      {" +
               "\n         addToPropertyName((type) value);" +
               "\n         return true;" +
               "\n      }" +
               "\n      " + 
               "\n      if ((PROPERTY_NAME + JsonIdMap.REMOVE_SUFFIX).equalsIgnoreCase(attrName))" +
               "\n      {" +
               "\n         removeFromPropertyName((type) value);" +
               "\n         return true;" +
               "\n      }" +
               "\n" 
               );

         String typePlaceholder = "type";
         String type = CGUtil.shortClassName(partnerRole.getClazz().getName());

         CGUtil.replaceAll(text, 
            typePlaceholder, type, 
            "PropertyName", StrUtil.upFirstChar(partnerRole.getName()),
            "PROPERTY_NAME", "PROPERTY_" + partnerRole.getName().toUpperCase()
            );

         parser.getFileBody().insert(lastIfEndPos, text.toString());
         
         getClazz().insertImport(JsonIdMap.class.getName());
         
         getClazz().setFileHasChanged(true);
      }
   }


   private void insertCaseInGenericSetToOne(Parser parser, Role partnerRole)
   {
      int pos = parser.indexOf(Parser.METHOD + ":set(String,Object)");

      if (pos < 0)
      {
         // ups, did not find generic set method. 
         System.err.println("Warning: SDMLib codgen for role " + partnerRole.getName() + " for class " + getClazz().getName() 
            + ": \nDid not find method set(String,Object). Should have been generated by my clazz. " 
            + "\nCould not add required code fragment there. :( ");

         return;
      }

      // OK, found method, parse its body to find if that handles me. 
      int methodBodyStartPos = parser.getMethodBodyStartPos();
      
      pos = parser.methodBodyIndexOf(Parser.NAME_TOKEN + ":PROPERTY_" + partnerRole.getName().toUpperCase() , methodBodyStartPos);

      if (pos < 0)
      {         
         // need to add if block to generic set method
         parser.methodBodyIndexOf(Parser.METHOD_END, methodBodyStartPos);
         
         int lastIfEndPos = parser.lastIfEnd + 2; // add 1 to be after } and 1 to be after \n
         if (lastIfEndPos - 2  < 0)
         {
            lastIfEndPos = methodBodyStartPos + 1;
         }
         
         StringBuilder text = new StringBuilder
            (  "\n      if (PROPERTY_NAME.equalsIgnoreCase(attrName))" +
               "\n      {" +
               "\n         setPropertyName((type) value);" +
               "\n         return true;" +
               "\n      }" +
               "\n" 
               );

         String typePlaceholder = "type";
         String type = CGUtil.shortClassName(partnerRole.getClazz().getName());

         CGUtil.replaceAll(text, 
            typePlaceholder, type, 
            "PropertyName", StrUtil.upFirstChar(partnerRole.getName()),
            "PROPERTY_NAME", "PROPERTY_" + partnerRole.getName().toUpperCase()
            );

         parser.getFileBody().insert(lastIfEndPos, text.toString());
         getClazz().setFileHasChanged(true);
      }
   }


   private void insertCaseInGenericGet(Parser parser, Role partnerRole)
   {
      int pos = parser.indexOf(Parser.METHOD + ":get(String)");

      if (pos < 0)
      {
         // ups, did not find generic get method. 
         System.err.println("Warning: SDMLib codgen for role" + partnerRole.getName() + " in class " + getClazz().getName() 
            + ": \nDid not find method get(String). Should have been generated by the clazz. " 
            + "\nCould not add required code fragment there. :( ");

         return;
      }

      // OK, found method, parse its body to find if that handles me. 
      int methodBodyStartPos = parser.getMethodBodyStartPos();
      
      pos = parser.methodBodyIndexOf(Parser.NAME_TOKEN + ":PROPERTY_" + partnerRole.getName().toUpperCase() , methodBodyStartPos);

      if (pos < 0)
      {         
         // need to add if block to generic get method
         parser.methodBodyIndexOf(Parser.METHOD_END, methodBodyStartPos);
         
         int lastIfEndPos = parser.lastIfEnd + 2; // add 1 to be after } and 1 to be after \n
         if (lastIfEndPos - 2  < 0)
         {
            lastIfEndPos = methodBodyStartPos + 1;
         }
         
         StringBuilder text = new StringBuilder
            (  "\n      if (PROPERTY_NAME.equalsIgnoreCase(attrName))" +
               "\n      {" +
               "\n         return getPropertyName();" +
               "\n      }" +
               "\n" 
               );

         CGUtil.replaceAll(text, 
            "PropertyName", StrUtil.upFirstChar(partnerRole.getName()),
            "PROPERTY_NAME", "PROPERTY_" + partnerRole.getName().toUpperCase()
            );

         parser.getFileBody().insert(lastIfEndPos, text.toString());
         getClazz().setFileHasChanged(true);
      }
   }

   


   private void generateEmptySetInPartnerClass(String rootDir, Role partnerRole, boolean doGenerate)
   {
      // generate EMPTY_SET in partner class
      Parser partnerParser = partnerRole.getClazz().getOrCreateParser(rootDir);
      
      int partnerPos = partnerParser.indexOf(Parser.ATTRIBUTE + ":EMPTY_SET");
      
      if (partnerPos < 0)
      {
         // add attribute declaration in class file
         partnerPos = partnerParser.indexOf(Parser.CLASS_END);

         StringBuilder partnerText = new StringBuilder
            (  "\n   " +
               "\n   public static final type EMPTY_SET = new type();" +
               "\n"
               );
         
         String partnerClassName = CGUtil.shortClassName(partnerRole.getClazz().getName());
         
         CGUtil.replaceAll(partnerText, 
            "type", "LinkedHashSet<" + partnerClassName + ">"
            );
         
         partnerParser.getFileBody().insert(partnerPos, partnerText.toString());
         
         partnerRole.getClazz().insertImport(LinkedHashSet.class.getName());
         
         partnerRole.getClazz().setFileHasChanged(true);
         partnerRole.getClazz().printFile(doGenerate);

      }
   }

   private void generateToManyRole(Role partnerRole, StringBuilder text)
   {
      text.append
         (  "\n   " +
            "\n   /********************************************************************" +
            "\n    * <pre>" +
            "\n    *              myCard                       partnerCard" +
            "\n    * myClassName ----------------------------------- partnerClassName" +
            "\n    *              myRoleName                   partnerRoleName" +
            "\n    * </pre>" +
            "\n    */" + 
            "\n   " +
            "\n   public static final String PROPERTY_PARTNER_ROLE_NAME = \"partnerRoleName\";" +
            "\n   " +
            "\n   private type partnerRoleName = null;" +
            "\n   " +
            "\n   public type getPartnerRoleName()" +
            "\n   {" +
            "\n      if (this.partnerRoleName == null)" +
            "\n      {" +
            "\n         return partnerClassName.EMPTY_SET;" +
            "\n      }" +
            "\n   " +
            "\n      return this.partnerRoleName;" +
            "\n   }" +
            "\n   " +
            "\n   public boolean addToPartnerRoleName(partnerClassName value)" +
            "\n   {" +
            "\n      boolean changed = false;" +
            "\n      " +
            "\n      if (value != null)" +
            "\n      {" +
            "\n         if (this.partnerRoleName == null)" +
            "\n         {" +
            "\n            this.partnerRoleName = new type();" +
            "\n         }" +
            "\n         " +
            "\n         changed = this.partnerRoleName.add (value);" +
            "\n         " +
            "\n         if (changed)" +
            "\n         {" +
            "\n            value.withMyRoleName(this);" +
            "\n            getPropertyChangeSupport().firePropertyChange(PROPERTY_PARTNER_ROLE_NAME, null, value);" +
            "\n         }" +
            "\n      }" +
            "\n         " +
            "\n      return changed;   " +
            "\n   }" +
            "\n   " +
            "\n   public boolean removeFromPartnerRoleName(partnerClassName value)" +
            "\n   {" +
            "\n      boolean changed = false;" +
            "\n      " +
            "\n      if ((this.partnerRoleName != null) && (value != null))" +
            "\n      {" +
            "\n         changed = this.partnerRoleName.remove (value);" +
            "\n         " +
            "\n         if (changed)" +
            "\n         {" +
            "\n            value.reverseWithoutCall(this);" +
            "\n            getPropertyChangeSupport().firePropertyChange(PROPERTY_PARTNER_ROLE_NAME, value, null);" +
            "\n         }" +
            "\n      }" +
            "\n         " +
            "\n      return changed;   " +
            "\n   }" +
            "\n   " +
            "\n   public myClassName withPartnerRoleName(partnerClassName value)" +
            "\n   {" +
            "\n      addToPartnerRoleName(value);" +
            "\n      return this;" +
            "\n   } " +
            "\n   " +
            "\n   public myClassName withoutPartnerRoleName(partnerClassName value)" +
            "\n   {" +
            "\n      removeFromPartnerRoleName(value);" +
            "\n      return this;" +
            "\n   } " +
            "\n   " +
            "\n   public void removeAllFromPartnerRoleName()" +
            "\n   {" +
            "\n      LinkedHashSet<partnerClassName> tmpSet = new LinkedHashSet<partnerClassName>(this.getPartnerRoleName());" +
            "\n   " +
            "\n      for (partnerClassName value : tmpSet)" +
            "\n      {" +
            "\n         this.removeFromPartnerRoleName(value);" +
            "\n      }" +
            "\n   }" +
            "\n"                  
            );


      String myClassName = CGUtil.shortClassName(getClazz().getName());
      
      String partnerClassName = CGUtil.shortClassName(partnerRole.getClazz().getName());
      
      String partnerRoleName = partnerRole.getName();
      
      String partnerRoleUpFirstChar = StrUtil.upFirstChar(partnerRoleName);
      
      String reverseWithoutCall = "set" + StrUtil.upFirstChar(getName()) + "(null)";
      
      if (getCard().equals(MANY))
      {
         reverseWithoutCall = "without" + StrUtil.upFirstChar(getName()) + "(this)";
      }
      
      CGUtil.replaceAll(text, 
         "myCard", this.getCard(),
         "partnerCard", partnerRole.getCard(),
         "type", "LinkedHashSet<" + partnerClassName + ">", 
         "myClassName", myClassName,
         "partnerClassName", partnerClassName,
         "myRoleName", getName(),
         "MyRoleName", StrUtil.upFirstChar(getName()),
         "partnerRoleName", partnerRoleName,
         "PARTNER_ROLE_NAME", partnerRoleName.toUpperCase(),
         "PartnerRoleName", partnerRoleUpFirstChar,
         "reverseWithoutCall(this)", reverseWithoutCall
         );
   } 
   
   private void generateToOneRole(Role partnerRole, StringBuilder text)
   {
      text.append
         (  "\n   " +
            "\n   /********************************************************************" +
            "\n    * <pre>" +
            "\n    *              myCard                       partnerCard" +
            "\n    * myClassName ----------------------------------- partnerClassName" +
            "\n    *              myRoleName                   partnerRoleName" +
            "\n    * </pre>" +
            "\n    */" + 
            "\n   " +
            "\n   public static final String PROPERTY_PARTNER_ROLE_NAME = \"partnerRoleName\";" +
            "\n   " +
            "\n   private partnerClassName partnerRoleName = null;" +
            "\n   " +
            "\n   public partnerClassName getPartnerRoleName()" +
            "\n   {" +
            "\n      return this.partnerRoleName;" +
            "\n   }" +
            "\n   " +
            "\n   public boolean setPartnerRoleName(partnerClassName value)" +
            "\n   {" +
            "\n      boolean changed = false;" +
            "\n      " +
            "\n      if (this.partnerRoleName != value)" +
            "\n      {" +
            "\n         partnerClassName oldValue = this.partnerRoleName;" +
            "\n         " +
            "\n         if (this.partnerRoleName != null)" +
            "\n         {" +
            "\n            this.partnerRoleName = null;" +
            "\n            oldValue.withoutMyRoleName(this);" +
            "\n         }" +
            "\n         " +
            "\n         this.partnerRoleName = value;" +
            "\n         " +
            "\n         if (value != null)" +
            "\n         {" +
            "\n            value.withMyRoleName(this);" +
            "\n         }" +
            "\n         " +
            "\n         getPropertyChangeSupport().firePropertyChange(PROPERTY_PARTNER_ROLE_NAME, oldValue, value);" +
            "\n         changed = true;" +
            "\n      }" +
            "\n      " +
            "\n      return changed;" +
            "\n   }" +
            "\n   " +
            "\n   public myClassName withPartnerRoleName(partnerClassName value)" +
            "\n   {" +
            "\n      setPartnerRoleName(value);" +
            "\n      return this;" +
            "\n   } " +
            "\n"
            );


      String myClassName = CGUtil.shortClassName(getClazz().getName());
      
      String partnerClassName = CGUtil.shortClassName(partnerRole.getClazz().getName());
      
      String partnerRoleName = partnerRole.getName();
      
      String partnerRoleUpFirstChar = StrUtil.upFirstChar(partnerRoleName);
      
      String reverseWithoutCall = "set" + partnerRoleUpFirstChar + "(null)";
      
      if (getCard().equals(MANY))
      {
         reverseWithoutCall = "without" + partnerRoleUpFirstChar + "(this)";
      }
      
      CGUtil.replaceAll(text, 
         "myCard", this.getCard(),
         "partnerCard", partnerRole.getCard(),
         "myClassName", myClassName,
         "partnerClassName", partnerClassName,
         "myRoleName", getName(),
         "MyRoleName", StrUtil.upFirstChar(getName()),
         "partnerRoleName", partnerRoleName,
         "PARTNER_ROLE_NAME", partnerRoleName.toUpperCase(),
         "PartnerRoleName", partnerRoleUpFirstChar,
         "withoutMyRoleName(this)", reverseWithoutCall
         );
   } 


   
   public static final LinkedHashSet<Role> EMPTY_SET = new LinkedHashSet<Role>();

   
   /********************************************************************
    * <pre>
    *              one                       one
    * Role ----------------------------------- Association
    *              source                   assoc
    * </pre>
    */
   
   public static final String PROPERTY_ASSOC = "assoc";
   
   private Association assoc = null;
   
   public Association getAssoc()
   {
      return this.assoc;
   }
   
   public boolean setAssoc(Association value)
   {
      boolean changed = false;
      
      if (this.assoc != value)
      {
         Association oldValue = this.assoc;
         
         this.assoc = value;
         
         // getPropertyChangeSupport().firePropertyChange(PROPERTY_ASSOC, null, value);
         changed = true;
      }
      
      return changed;
   }
   
   public Role withAssoc(Association value)
   {
      setAssoc(value);
      return this;
   } 

   
   //==========================================================================
   
   public Object get(String attrName)
   {
      int pos = attrName.indexOf('.');
      String attribute = attrName;
      
      if (pos > 0)
      {
         attribute = attrName.substring(0, pos);
      }

      if (PROPERTY_NAME.equalsIgnoreCase(attrName))
      {
         return getName();
      }

      if (PROPERTY_CARD.equalsIgnoreCase(attrName))
      {
         return getCard();
      }

      if (PROPERTY_KIND.equalsIgnoreCase(attrName))
      {
         return getKind();
      }
      
      return null;
   }

   
   //==========================================================================
   
   public boolean set(String attrName, Object value)
   {
      if (PROPERTY_NAME.equalsIgnoreCase(attrName))
      {
         setName((String) value);
         return true;
      }

      if (PROPERTY_CARD.equalsIgnoreCase(attrName))
      {
         setCard((String) value);
         return true;
      }

      if (PROPERTY_KIND.equalsIgnoreCase(attrName))
      {
         setKind((String) value);
         return true;
      }

      return false;
   }
}




