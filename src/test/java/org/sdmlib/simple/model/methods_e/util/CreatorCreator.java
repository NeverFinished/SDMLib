package org.sdmlib.simple.model.methods_e.util;

import de.uniks.networkparser.IdMap;

class CreatorCreator{

   public static IdMap createIdMap(String sessionID)
   {
      IdMap jsonIdMap = new IdMap().withSessionId(sessionID);
      jsonIdMap.with(new PersonCreator());
      jsonIdMap.with(new PersonPOCreator());
      jsonIdMap.with(new RoomCreator());
      jsonIdMap.with(new RoomPOCreator());
      return jsonIdMap;
   }
}