package de.uniks.jism.gui.table;
/*
Copyright (c) 2012, Stefan Lindel
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. All advertising materials mentioning features or use of this software
   must display the following acknowledgement:
   This product includes software developed by Stefan Lindel.
4. Neither the name of contributors may be used to endorse or promote products
   derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY STEFAN LINDEL ''AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL STEFAN LINDEL BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.sdmlib.serialization.IdMap;
import org.sdmlib.serialization.interfaces.PeerMessage;
import org.sdmlib.serialization.interfaces.SendableEntityCreator;

public class SearchResultUpdater implements ModifyListener
{
   private String lastSearchCriteria = "####";
   private Display myDisplay;
   private Text searchText;
   private TableViewerColumn tableViewerColumn;
   private String columnTitle;
   private PeerMessage searchResult;
   private String property;
   private PeerMessage searchIn;
   private ArrayList<String> searchProperties=new ArrayList<String>();
   
   
   public SearchResultUpdater(Text searchField, PeerMessage searchList, String property, PeerMessage blanko, String searchProperties){
	   init(Display.getDefault(), searchField, searchList, property, searchProperties);
	   
	   this.searchResult=blanko;
   }
   
   public SearchResultUpdater(Text searchField, PeerMessage searchList, String property, IdMap map, String searchProperties){
	   init(Display.getDefault(), searchField, searchList, property, searchProperties);
	   
	   SendableEntityCreator creatorClass = map.getCreatorClass(searchList);
	   this.searchResult=(PeerMessage) creatorClass.getSendableInstance(false);
   }

   public SearchResultUpdater(Display display, Text searchField, PeerMessage searchList, String property, IdMap map, String searchProperties){
	   init(display, searchField, searchList, property, searchProperties);
	   
	   SendableEntityCreator creatorClass = map.getCreatorClass(searchList);
	   this.searchResult=(PeerMessage) creatorClass.getSendableInstance(false);
   }
   
   public void init(Display display, Text searchField, PeerMessage searchList, String property, String searchProperties){
	      this.myDisplay=display;
	      this.searchText=searchField;
	      this.searchIn=searchList;
	      
	      
	      this.property=property;
	      String[] properties = searchProperties.split(",");
	      for(String item : properties){
	    	  this.searchProperties.add(item);
	      }
   }
   
   public void setTableViewColumn(String columnTitle, TableViewerColumn tableViewerColumn){
      this.columnTitle = columnTitle;
      this.tableViewerColumn=tableViewerColumn;
   }

   public void refreshContent(){
		String searchCriteria = searchText.getText().trim().toLowerCase();

		// new search
		lastSearchCriteria = searchCriteria;

		String[] split = new String[] {};
		if (searchCriteria != null) {
			split = searchCriteria.split(" ");
		}

		// compare root.talklist and searchresults
		Collection<?> resultList = (Collection<?>) searchResult.get(property);
		Collection<?> sourceList = (Collection<?>) searchIn.get(property);
		System.out.println("SEARCH IN :"+Thread.currentThread().getName()+"-"+resultList.toString());
		Object[] list= resultList.toArray();
		for(int i=0;i<list.length;i++){
			// is this still in root.talklist?
			
			if (!sourceList.contains(list[i])) {
				getSearchResults().set(property + IdMap.REMOVE, list[i]);
				continue;
			}

			// does it still match the search criteria?
			if (!matchesSearchCriteria((PeerMessage) list[i], split)) {
				getSearchResults().set(property + IdMap.REMOVE, list[i]);
				continue;
			}
		}
		// and now the other way round
		for (Object child : sourceList) {
			if (!resultList.contains(child)) {
				if (matchesSearchCriteria((PeerMessage) child, split)) {
					getSearchResults().set(property, child);
				}
			}
		}
		if (tableViewerColumn != null) {
			tableViewerColumn.getColumn().setText(
					columnTitle + " (" + resultList.size() + ")");
		}
	}

   private boolean matchesSearchCriteria(PeerMessage item, String[] split)
   {
	   String fullText = "";
	   // SEARCH FOR #ID:3
	   for(String property : searchProperties){
		   fullText += " "+item.get(property).toString().toLowerCase();   
	   }
	   fullText=fullText.trim();
      
      Boolean matches = true;
      for (String word : split)
      {
         if (! "".equals(word)) {
         	if(word.startsWith("#")&&":".indexOf(word)>1){
         		int pos=":".indexOf(word);
         		String propString=word.substring(1, pos);
         		
         		if(searchProperties.contains(propString)){
         			String value=word.substring(pos+1);
         			String itemValue = item.get(word.substring(1)).toString().toLowerCase();
         			//Search for simple Property
         			if(itemValue.indexOf(value) < 0){
         				return false;
         			}
         		}
         	}else if(word.startsWith("-")&&word.length()>1){
               if(fullText.indexOf(word.substring(1)) >= 0){
                  return false;
               }
            }else if(fullText.indexOf(word) < 0){
               // no this search word is not found in full text
               return false;
            }
         }
      }
      
      return matches;
   }
   
   public PeerMessage getSearchResults(){
	   return searchResult;
   }
   public PeerMessage getSearchList(){
	   return searchIn;
   }

   @Override
   public void modifyText(ModifyEvent event)
   {
	   refresh();
   }
   public void refresh(){
	   String searchCriteria = searchText.getText();
       // if search did not change do nothing
       if (searchCriteria == null && lastSearchCriteria == null)  return; // <========= sudden death
       
       searchCriteria = searchCriteria.trim().toLowerCase();
       if (searchCriteria.equals(lastSearchCriteria))   return; // <========= sudden death
       if (myDisplay == null) return; // <=========================sudden death
       
       myDisplay.syncExec(new Runnable()
       {
 		@Override
          public void run(){
 			refreshContent();
 		}
       });
   }

	public String getProperty() {
		return property;
	}
}