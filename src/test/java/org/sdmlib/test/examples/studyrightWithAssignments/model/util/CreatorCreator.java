package org.sdmlib.test.examples.studyrightWithAssignments.model.util;

import de.uniks.networkparser.json.JsonIdMap;
import org.sdmlib.serialization.SDMLibJsonIdMap;

class CreatorCreator{

   public static JsonIdMap createIdMap(String sessionID)
   {
      JsonIdMap jsonIdMap = (JsonIdMap) new SDMLibJsonIdMap().withSessionId(sessionID);
      jsonIdMap.withCreator(new UniversityCreator());
      jsonIdMap.withCreator(new UniversityPOCreator());
      jsonIdMap.withCreator(new StudentCreator());
      jsonIdMap.withCreator(new StudentPOCreator());
      jsonIdMap.withCreator(new RoomCreator());
      jsonIdMap.withCreator(new RoomPOCreator());
      jsonIdMap.withCreator(new AssignmentCreator());
      jsonIdMap.withCreator(new AssignmentPOCreator());
      jsonIdMap.withCreator(new TeachingAssistantCreator());
      jsonIdMap.withCreator(new TeachingAssistantPOCreator());
      return jsonIdMap;
   }
}