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
   
package org.sdmlib.examples.ludo;
   
import org.junit.Test;
import org.sdmlib.examples.ludo.LudoModel.LudoColor;
import org.sdmlib.examples.ludo.model.Dice;
import org.sdmlib.examples.ludo.model.Field;
import org.sdmlib.examples.ludo.model.Ludo;
import org.sdmlib.examples.ludo.model.Pawn;
import org.sdmlib.examples.ludo.model.Player;
import org.sdmlib.examples.ludo.model.util.DicePO;
import org.sdmlib.examples.ludo.model.util.FieldPO;
import org.sdmlib.examples.ludo.model.util.LudoCreator;
import org.sdmlib.examples.ludo.model.util.PawnPO;
import org.sdmlib.examples.ludo.model.util.PlayerPO;
import org.sdmlib.storyboards.Kanban;
import org.sdmlib.storyboards.Storyboard;
import org.sdmlib.storyboards.StoryboardManager;

import de.uniks.networkparser.json.JsonArray;
import de.uniks.networkparser.json.JsonIdMap;
   
public class LudoStoryboard
{
   private static final String RED = "red";


   @Test
   public void testLudoStoryboard()
   {
      // file:///C:/Users/zuendorf/eclipseworkspaces/indigo/SDMLib/doc/LudoStoryboard.html
      Storyboard storyboard = new Storyboard("examples", "LudoStoryboard");
      
      storyboard.setSprint("Sprint.002.Examples");
      
      storyboard.add("Start situation: ",
         Kanban.DONE, "zuendorf", "19.08.2012 22:59:05", 40, 0);
      
      JsonIdMap jsonIdMap = LudoCreator.createIdMap("l1");
      
      // create a simple ludo storyboard
      
      Ludo ludo = new Ludo();
      
      Player tom = ludo.createPlayers().withName("Tom").withColor("blue").withEnumColor(LudoColor.blue);
      
      JsonArray jsonArray = jsonIdMap.toJsonArray(tom);
      
      System.out.println(jsonArray.toString(3));
      
      
      Player sabine = ludo.createPlayers().withName("Sabine").withColor(RED).withEnumColor(LudoColor.red);
      
      JsonIdMap jsonIdMapClone = LudoCreator.createIdMap("l2");

      Object clone = jsonIdMap.decode(jsonArray);
      
      Dice dice = tom.createDice().withValue(6);
      
      Pawn p2 = tom.createPawns().withColor("blue");
      
      Field tomStartField = tom.createStart().withColor("blue").withKind("start");
      
      sabine.createStart().withColor(RED).withKind("start");
      
      Field tmp = tomStartField;
      for (int i = 0; i < 4; i++)
      {
         tmp = tmp.createNext();
      }
      
      Field tomBase = tom.createBase().withColor("blue").withKind("base").withPawns(p2);
      
      Pawn p9 = sabine.createPawns().withColor(RED)
            .withPos(tomStartField);
      
      
      storyboard.addObjectDiagram(ludo);
      
      storyboard.add("now the pawn may move to Tom's start field");
      
      storyboard.markCodeStart();
      // build move operation with SDM model transformations
      PawnPO pawnPO = new PawnPO(p2);
      
      PlayerPO playerPO = pawnPO.hasPlayer();
      
      DicePO dicePO = playerPO.hasDice().hasValue(6);
      
      FieldPO baseField = pawnPO.hasPos().hasKind("base");
      
      playerPO.hasBase(baseField);
      
      FieldPO startFieldPO = playerPO.hasStart();
      
      startFieldPO.startNAC().hasPawns().hasPlayer(playerPO).endNAC();
      storyboard.addCode("examples");
      
      // storyboard.addObjectDiag(jsonIdMap, pawnPO);
      storyboard.addPattern(pawnPO, true);
      
      storyboard.markCodeStart();
      pawnPO.startDestroy();
      pawnPO.hasPos(baseField);
      
      pawnPO.startCreate();
      pawnPO.hasPos(startFieldPO);
      storyboard.addCode("examples");
      
      //storyboard.addObjectDiag(jsonIdMap, pawnPO);
      storyboard.addPattern(pawnPO, true);
      
      System.out.println("pattern has match is " + pawnPO.getPattern().getHasMatch());
      
      StoryboardManager.get()
      .add(storyboard)
      .dumpHTML();
   }

   @Test
   public void testLudoStoryboardManual()
   {
      Storyboard storyboard = new Storyboard("examples", "LudoStoryboardManual");
      
      storyboard.setSprint("Sprint.002.Examples");
      
      storyboard.add("Start situation: ",
         Kanban.DONE, "zuendorf", "19.07.2012 14:41:05", 1, 0);
      
      // create a simple ludo storyboard
      
      Player tom = new Player().withName("Tom").withColor("blue");
      Player sabine = new Player().withName("Sabine").withColor(RED);
      
      Dice dice = new Dice().withValue(6)
            .withPlayer(tom);
      
      Pawn p8 = new Pawn().withColor("blue")
            .withPlayer(tom);
      
      Field tomStartField = new Field().withColor("blue").withKind("start");
      tom.withStart(tomStartField);
      
      Field tmp = tomStartField;
      for (int i = 0; i < 4; i++)
      {
         tmp = new Field().withPrev(tmp);
      }
      
      Field tomBase = new Field().withColor("blue").withKind("base").withPawns(p8);
      tom.withBase(tomBase);
      
      Pawn p9 = new Pawn().withColor(RED)
            .withPlayer(sabine)
            .withPos(tomStartField);
      
      JsonIdMap jsonIdMap = LudoCreator.createIdMap("l1");
      
      storyboard.addObjectDiagram(tom);
      
      storyboard.add("now the pawn may move to Tom's start field");
      
      storyboard.markCodeStart();
      // build move operation with SDM model transformations
      Player player = p8.getPlayer();
      
      if (player.getDice() != null && player.getDice().getValue() == 6
            && p8.getPos() != null && "base".equals(p8.getPos().getKind())
            && p8.getPos() == player.getBase())
      {
         Field startField = player.getStart();
         boolean hasOtherOwnPawn = false;
         
         for (Pawn otherOwnPawn : startField.getPawns())
         {
            if (otherOwnPawn.getPlayer() == player)
            {
               hasOtherOwnPawn = true;
               break;
            }
         }
         
         if ( ! hasOtherOwnPawn)
         {
            p8.setPos(startField);
         }
      }
      storyboard.addCode("examples");
      
      storyboard.addObjectDiagram(tom);
      
      StoryboardManager.get()
      .add(storyboard)
      .dumpHTML();
   }
}

