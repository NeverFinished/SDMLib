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
   
package org.sdmlib.codegen;

import org.sdmlib.utils.PropertyChangeInterface;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import org.sdmlib.utils.StrUtil;
import java.util.LinkedHashSet;
import org.sdmlib.serialization.json.JsonIdMap;

public class StatementEntry implements PropertyChangeInterface
{

   public String toString()
   {
      StringBuilder text = new StringBuilder();
      for (String word : getTokenList())
      {
         text.append(word);
      }
      
      return text.toString();
   }
   
   //==========================================================================
   
   public Object get(String attrName)
   {
      if (PROPERTY_ASSIGNTARGETVARNAME.equalsIgnoreCase(attrName))
      {
         return getAssignTargetVarName();
      }

      if (PROPERTY_TOKENLIST.equalsIgnoreCase(attrName))
      {
         return getTokenList();
      }

      if (PROPERTY_KIND.equalsIgnoreCase(attrName))
      {
         return getKind();
      }

      if (PROPERTY_BODYSTATS.equalsIgnoreCase(attrName))
      {
         return getBodyStats();
      }

      if (PROPERTY_PARENT.equalsIgnoreCase(attrName))
      {
         return getParent();
      }

      return null;
   }

   
   //==========================================================================
   
   public boolean set(String attrName, Object value)
   {
      if (PROPERTY_ASSIGNTARGETVARNAME.equalsIgnoreCase(attrName))
      {
         setAssignTargetVarName((String) value);
         return true;
      }

      if (PROPERTY_TOKENLIST.equalsIgnoreCase(attrName))
      {
         setTokenList((ArrayList<String>) value);
         return true;
      }

      if (PROPERTY_KIND.equalsIgnoreCase(attrName))
      {
         setKind((String) value);
         return true;
      }

      if (PROPERTY_BODYSTATS.equalsIgnoreCase(attrName))
      {
         addToBodyStats((StatementEntry) value);
         return true;
      }
      
      if ((PROPERTY_BODYSTATS + JsonIdMap.REMOVE).equalsIgnoreCase(attrName))
      {
         removeFromBodyStats((StatementEntry) value);
         return true;
      }

      if (PROPERTY_PARENT.equalsIgnoreCase(attrName))
      {
         setParent((StatementEntry) value);
         return true;
      }

      return false;
   }

   
   //==========================================================================
   
   protected final PropertyChangeSupport listeners = new PropertyChangeSupport(this);
   
   public PropertyChangeSupport getPropertyChangeSupport()
   {
      return listeners;
   }

   
   //==========================================================================
   
   public void removeYou()
   {
      removeAllFromBodyStats();
      setParent(null);
      getPropertyChangeSupport().firePropertyChange("REMOVE_YOU", this, null);
   }

   
   //==========================================================================
   
   public static final String PROPERTY_KIND = "kind";
   
   private String kind;
   
   public String getKind()
   {
      return this.kind;
   }
   
   public void setKind(String value)
   {
      if ( ! StrUtil.stringEquals(this.kind, value))
      {
         String oldValue = this.kind;
         this.kind = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_KIND, oldValue, value);
      }
   }
   
   public StatementEntry withKind(String value)
   {
      setKind(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_TOKENLIST = "tokenList";
   
   private ArrayList<String> tokenList = new ArrayList<String>();
   
   public ArrayList<String> getTokenList()
   {
      return this.tokenList;
   }
   
   public void setTokenList(ArrayList<String> value)
   {
      if (this.tokenList != value)
      {
         ArrayList<String> oldValue = this.tokenList;
         this.tokenList = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_TOKENLIST, oldValue, value);
      }
   }
   
   public StatementEntry withTokenList(ArrayList<String> value)
   {
      setTokenList(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_ASSIGNTARGETVARNAME = "assignTargetVarName";
   
   private String assignTargetVarName;
   
   public String getAssignTargetVarName()
   {
      return this.assignTargetVarName;
   }
   
   public void setAssignTargetVarName(String value)
   {
      if ( ! StrUtil.stringEquals(this.assignTargetVarName, value))
      {
         String oldValue = this.assignTargetVarName;
         this.assignTargetVarName = value;
         getPropertyChangeSupport().firePropertyChange(PROPERTY_ASSIGNTARGETVARNAME, oldValue, value);
      }
   }
   
   public StatementEntry withAssignTargetVarName(String value)
   {
      setAssignTargetVarName(value);
      return this;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * StatementEntry ----------------------------------- StatementEntry
    *              parent                   bodyStats
    * </pre>
    */
   
   public static final String PROPERTY_BODYSTATS = "bodyStats";
   
   private LinkedHashSet<StatementEntry> bodyStats = null;
   
   public LinkedHashSet<StatementEntry> getBodyStats()
   {
      if (this.bodyStats == null)
      {
         return StatementEntry.EMPTY_SET;
      }
   
      return this.bodyStats;
   }
   
   public boolean addToBodyStats(StatementEntry value)
   {
      boolean changed = false;
      
      if (value != null)
      {
         if (this.bodyStats == null)
         {
            this.bodyStats = new LinkedHashSet<StatementEntry>();
         }
         
         changed = this.bodyStats.add (value);
         
         if (changed)
         {
            value.withParent(this);
            getPropertyChangeSupport().firePropertyChange(PROPERTY_BODYSTATS, null, value);
         }
      }
         
      return changed;   
   }
   
   public boolean removeFromBodyStats(StatementEntry value)
   {
      boolean changed = false;
      
      if ((this.bodyStats != null) && (value != null))
      {
         changed = this.bodyStats.remove (value);
         
         if (changed)
         {
            value.setParent(null);
            getPropertyChangeSupport().firePropertyChange(PROPERTY_BODYSTATS, value, null);
         }
      }
         
      return changed;   
   }
   
   public StatementEntry withBodyStats(StatementEntry value)
   {
      addToBodyStats(value);
      return this;
   } 
   
   public StatementEntry withoutBodyStats(StatementEntry value)
   {
      removeFromBodyStats(value);
      return this;
   } 
   
   public void removeAllFromBodyStats()
   {
      LinkedHashSet<StatementEntry> tmpSet = new LinkedHashSet<StatementEntry>(this.getBodyStats());
   
      for (StatementEntry value : tmpSet)
      {
         this.removeFromBodyStats(value);
      }
   }

   
   public static final LinkedHashSet<StatementEntry> EMPTY_SET = new LinkedHashSet<StatementEntry>();

   
   /********************************************************************
    * <pre>
    *              many                       one
    * StatementEntry ----------------------------------- StatementEntry
    *              bodyStats                   parent
    * </pre>
    */
   
   public static final String PROPERTY_PARENT = "parent";
   
   private StatementEntry parent = null;
   
   public StatementEntry getParent()
   {
      return this.parent;
   }
   
   public boolean setParent(StatementEntry value)
   {
      boolean changed = false;
      
      if (this.parent != value)
      {
         StatementEntry oldValue = this.parent;
         
         if (this.parent != null)
         {
            this.parent = null;
            oldValue.withoutBodyStats(this);
         }
         
         this.parent = value;
         
         if (value != null)
         {
            value.withBodyStats(this);
         }
         
         getPropertyChangeSupport().firePropertyChange(PROPERTY_PARENT, oldValue, value);
         changed = true;
      }
      
      return changed;
   }
   
   public StatementEntry withParent(StatementEntry value)
   {
      setParent(value);
      return this;
   } 
}
