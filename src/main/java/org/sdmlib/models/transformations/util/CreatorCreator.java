package org.sdmlib.models.transformations.util;

import de.uniks.networkparser.json.JsonIdMap;
import org.sdmlib.serialization.SDMLibJsonIdMap;

public class CreatorCreator{

   public static JsonIdMap createIdMap(String sessionID)
   {
      JsonIdMap jsonIdMap = (JsonIdMap) new SDMLibJsonIdMap().withSessionId(sessionID);
      
      jsonIdMap.withCreator(new org.sdmlib.models.transformations.util.TemplateCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.transformations.util.TemplatePOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.transformations.util.PlaceHolderDescriptionCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.transformations.util.PlaceHolderDescriptionPOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.transformations.util.ChoiceTemplateCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.transformations.util.ChoiceTemplatePOCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.transformations.util.MatchCreator());
      jsonIdMap.withCreator(new org.sdmlib.models.transformations.util.MatchPOCreator());

      return jsonIdMap;
   }
}
