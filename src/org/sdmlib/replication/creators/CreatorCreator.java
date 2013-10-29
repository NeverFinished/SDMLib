package org.sdmlib.replication.creators;

import java.util.LinkedHashSet;
import org.sdmlib.serialization.interfaces.SendableEntityCreator;
import org.sdmlib.serialization.json.JsonIdMap;
import org.sdmlib.serialization.json.SDMLibJsonIdMap;

public class CreatorCreator
{
   public static LinkedHashSet<SendableEntityCreator> creatorSet = null;
   
   public static LinkedHashSet<SendableEntityCreator> getCreatorSet()
   {
      if (creatorSet == null)
      {
         creatorSet = new LinkedHashSet<SendableEntityCreator>();
         creatorSet.add(new org.sdmlib.replication.creators.ReplicationNodeCreator());
         creatorSet.add(new org.sdmlib.replication.creators.ReplicationNodePOCreator());
         creatorSet.add(new org.sdmlib.replication.creators.SharedSpaceCreator());
         creatorSet.add(new org.sdmlib.replication.creators.SharedSpacePOCreator());
         creatorSet.add(new org.sdmlib.replication.creators.ReplicationChannelCreator());
         creatorSet.add(new org.sdmlib.replication.creators.ReplicationChannelPOCreator());
         creatorSet.add(new org.sdmlib.replication.creators.ReplicationServerCreator());
         creatorSet.add(new org.sdmlib.replication.creators.ReplicationServerPOCreator());
         creatorSet.add(new org.sdmlib.replication.creators.ServerSocketAcceptThreadCreator());
         creatorSet.add(new org.sdmlib.replication.creators.ServerSocketAcceptThreadPOCreator());
         creatorSet.add(new org.sdmlib.replication.creators.TaskCreator());
         creatorSet.add(new org.sdmlib.replication.creators.TaskPOCreator());
         creatorSet.add(new org.sdmlib.replication.creators.LogEntryCreator());
         creatorSet.add(new org.sdmlib.replication.creators.LogEntryPOCreator());
         creatorSet.add(new org.sdmlib.replication.creators.ChangeHistoryCreator());
         creatorSet.add(new org.sdmlib.replication.creators.ChangeHistoryPOCreator());
         creatorSet.add(new org.sdmlib.replication.creators.ReplicationChangeCreator());
         creatorSet.add(new org.sdmlib.replication.creators.ReplicationChangePOCreator());
         creatorSet.add(new org.sdmlib.replication.creators.TaskFlowBoardCreator());
         creatorSet.add(new org.sdmlib.replication.creators.TaskFlowBoardPOCreator());
         creatorSet.add(new org.sdmlib.replication.creators.LaneCreator());
         creatorSet.add(new org.sdmlib.replication.creators.LanePOCreator());
         creatorSet.add(new org.sdmlib.replication.creators.BoardTaskCreator());
         creatorSet.add(new org.sdmlib.replication.creators.BoardTaskPOCreator());
         creatorSet.add(new org.sdmlib.replication.creators.ObjectCreator());
         creatorSet.add(new org.sdmlib.replication.creators.ObjectPOCreator());
         creatorSet.add(new org.sdmlib.replication.creators.PropertyChangeSupportCreator());
         creatorSet.add(new org.sdmlib.replication.creators.PropertyChangeSupportPOCreator());
         creatorSet.add(new org.sdmlib.replication.creators.SharedModelRootCreator());
         creatorSet.add(new org.sdmlib.replication.creators.SharedModelRootPOCreator());
         creatorSet.addAll(org.sdmlib.models.pattern.creators.CreatorCreator.getCreatorSet());
      }
      
      return creatorSet;
   }

   public static JsonIdMap createIdMap(String sessionID)
   {
      JsonIdMap jsonIdMap = (JsonIdMap) new SDMLibJsonIdMap().withSessionId(sessionID);
      
      jsonIdMap.withCreator(getCreatorSet());

      return jsonIdMap;
   }
}





