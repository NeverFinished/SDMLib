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
   
package org.sdmlib.models.transformations;

import org.sdmlib.utils.PropertyChangeInterface;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import org.sdmlib.utils.StrUtil;
import org.sdmlib.models.transformations.creators.PlaceHolderDescriptionSet;

public class PlaceHolderDescription implements PropertyChangeInterface
{

   
   //==========================================================================
   
   public Object get(String attrName)
   {
      if (PROPERTY_TEXTFRAGMENT.equalsIgnoreCase(attrName))
      {
         return getTextFragment();
      }

      if (PROPERTY_VALUE.equalsIgnoreCase(attrName))
      {
         return getValue();
      }

      if (PROPERTY_ATTRNAME.equalsIgnoreCase(attrName))
      {
         return getAttrName();
      }

      if (PROPERTY_TEMPLATE.equalsIgnoreCase(attrName))
      {
         return getTemplate();
      }

      if (PROPERTY_SUBTEMPLATE.equalsIgnoreCase(attrName))
      {
         return getSubTemplate();
      }

      if (PROPERTY_ISKEYATTRIBUTE.equalsIgnoreCase(attrName))
      {
         return getIsKeyAttribute();
      }

      return null;
   }

   
   //==========================================================================
   
   public boolean set(String attrName, Object value)
   {
      if (PROPERTY_TEXTFRAGMENT.equalsIgnoreCase(attrName))
      {
         setTextFragment((String) value);
         return true;
      }

      if (PROPERTY_VALUE.equalsIgnoreCase(attrName))
      {
         setValue((String) value);
         return true;
      }

      if (PROPERTY_ATTRNAME.equalsIgnoreCase(attrName))
      {
         setAttrName((String) value);
         return true;
      }

      if (PROPERTY_TEMPLATE.equalsIgnoreCase(attrName))
      {
         setTemplate((Template) value);
         return true;
      }

      if (PROPERTY_SUBTEMPLATE.equalsIgnoreCase(attrName))
      {
         setSubTemplate((Template) value);
         return true;
      }

      if (PROPERTY_ISKEYATTRIBUTE.equalsIgnoreCase(attrName))
      {
         setIsKeyAttribute((Boolean) value);
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
   
   public void addPropertyChangeListener(PropertyChangeListener listener) 
   {
      getPropertyChangeSupport().addPropertyChangeListener(listener);
   }

   
   //==========================================================================
   
   public void removeYou()
   {
      setTemplate(null);
      setSubTemplate(null);
      getPropertyChangeSupport().firePropertyChange("REMOVE_YOU", this, null);
   }

   
   //==========================================================================
   
   public static final String PROPERTY_TEXTFRAGMENT = "textFragment";
   
   private String textFragment;

   public String getTextFragment()
   {
      return this.textFragment;
   }
   
   public void setTextFragment(String value)
   {
      if ( ! StrUtil.stringEquals(this.textFragment, value))
      {
         String oldValue = this.textFragment;
         this.textFragment = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_TEXTFRAGMENT, oldValue, value);
      }
   }
   
   public PlaceHolderDescription withTextFragment(String value)
   {
      setTextFragment(value);
      return this;
   } 

   public String toString()
   {
      StringBuilder _ = new StringBuilder();
      
      _.append(" ").append(this.getTextFragment());
      _.append(" ").append(this.getValue());
      _.append(" ").append(this.getAttrName());
      return _.substring(1);
   }


   
   //==========================================================================
   
   public static final String PROPERTY_VALUE = "value";
   
   private String value;

   public String getValue()
   {
      return this.value;
   }
   
   public void setValue(String value)
   {
      if ( ! StrUtil.stringEquals(this.value, value))
      {
         String oldValue = this.value;
         this.value = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_VALUE, oldValue, value);
      }
   }
   
   public PlaceHolderDescription withValue(String value)
   {
      setValue(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_ATTRNAME = "attrName";
   
   private String attrName;

   public String getAttrName()
   {
      return this.attrName;
   }
   
   public void setAttrName(String value)
   {
      if ( ! StrUtil.stringEquals(this.attrName, value))
      {
         String oldValue = this.attrName;
         this.attrName = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_ATTRNAME, oldValue, value);
      }
   }
   
   public PlaceHolderDescription withAttrName(String value)
   {
      setAttrName(value);
      return this;
   } 

   
   public PlaceHolderDescription with(String fragmentText, String attrName)
   {
      this.setTextFragment(fragmentText);
      this.setAttrName(attrName);
      
      return this;
   }
   
   public static final PlaceHolderDescriptionSet EMPTY_SET = new PlaceHolderDescriptionSet();

   
   /********************************************************************
    * <pre>
    *              many                       one
    * PlaceHolderDescription ----------------------------------- Template
    *              placeholders                   template
    * </pre>
    */
   
   public static final String PROPERTY_TEMPLATE = "template";
   
   private Template template = null;
   
   public Template getTemplate()
   {
      return this.template;
   }
   
   public boolean setTemplate(Template value)
   {
      boolean changed = false;
      
      if (this.template != value)
      {
         Template oldValue = this.template;
         
         if (this.template != null)
         {
            this.template = null;
            oldValue.withoutPlaceholders(this);
         }
         
         this.template = value;
         
         if (value != null)
         {
            value.withPlaceholders(this);
         }
         
         getPropertyChangeSupport().firePropertyChange(PROPERTY_TEMPLATE, oldValue, value);
         changed = true;
      }
      
      return changed;
   }
   
   public PlaceHolderDescription withTemplate(Template value)
   {
      setTemplate(value);
      return this;
   } 
   
   public Template createTemplate()
   {
      Template value = new Template();
      withTemplate(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       one
    * PlaceHolderDescription ----------------------------------- Template
    *              placeholderDescription                   subTemplate
    * </pre>
    */
   
   public static final String PROPERTY_SUBTEMPLATE = "subTemplate";
   
   private Template subTemplate = null;
   
   public Template getSubTemplate()
   {
      return this.subTemplate;
   }
   
   public boolean setSubTemplate(Template value)
   {
      boolean changed = false;
      
      if (this.subTemplate != value)
      {
         Template oldValue = this.subTemplate;
         
         if (this.subTemplate != null)
         {
            this.subTemplate = null;
            oldValue.setPlaceholderDescription(null);
         }
         
         this.subTemplate = value;
         
         if (value != null)
         {
            value.withPlaceholderDescription(this);
         }
         
         getPropertyChangeSupport().firePropertyChange(PROPERTY_SUBTEMPLATE, oldValue, value);
         changed = true;
      }
      
      return changed;
   }
   
   public PlaceHolderDescription withSubTemplate(Template value)
   {
      setSubTemplate(value);
      return this;
   } 
   
   public Template createSubTemplate()
   {
      Template value = new Template();
      withSubTemplate(value);
      return value;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_ISKEYATTRIBUTE = "isKeyAttribute";
   
   private boolean isKeyAttribute;

   public boolean getIsKeyAttribute()
   {
      return this.isKeyAttribute;
   }
   
   public void setIsKeyAttribute(boolean value)
   {
      if (this.isKeyAttribute != value)
      {
         boolean oldValue = this.isKeyAttribute;
         this.isKeyAttribute = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_ISKEYATTRIBUTE, oldValue, value);
      }
   }
   
   public PlaceHolderDescription withIsKeyAttribute(boolean value)
   {
      setIsKeyAttribute(value);
      return this;
   } 
}

