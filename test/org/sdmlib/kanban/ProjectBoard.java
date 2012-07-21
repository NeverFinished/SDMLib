/*
   Copyright (c) 2012 Albert Z�ndorf

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

package org.sdmlib.kanban;

import org.junit.Test;
import org.sdmlib.models.classes.Association;
import org.sdmlib.models.classes.ClassModel;
import org.sdmlib.models.classes.Clazz;
import org.sdmlib.models.classes.Role;
import org.sdmlib.scenarios.KanbanEntry;
import org.sdmlib.scenarios.LogEntry;
import org.sdmlib.scenarios.Scenario;
import org.sdmlib.scenarios.ScenarioManager;

public class ProjectBoard
{
   public static final String MODELING = "modeling";
   public static final String ACTIVE = "active";
   public static final String DONE = "done";
   public static final String IMPLEMENTATION = "implementation";
   public static final String BACKLOG = "backlog";

   @Test
   public void testAdmin()
   {
      ScenarioManager man = ScenarioManager.get();
      
      
      KanbanEntry kanbanBoard = man.loadOldKanbanEntries()
         .withName("SDMLibProject")
         .withPhase(ACTIVE);

      KanbanEntry sprint1 = kanbanBoard.findOrCreate("Sprint.001.Booting")
            .withLastDeveloper("zuendorf")
            .withPhase(ACTIVE)
            .withParent(kanbanBoard);
      
      KanbanEntry entry = kanbanBoard.findOrCreate("ScenarioInfrastructure")
            .withParent(sprint1);

      LogEntry logEntry;
      
      entry = kanbanBoard.findOrCreate("ProjectManagement")
            .withParent(kanbanBoard);

      logEntry = entry.findOrCreateLogEntry("07.05.2012 23:38:42", ACTIVE)
            .withDeveloper("zuendorf")
            .withHoursSpend(0)
            .withHoursRemainingInTotal(0);
      
      entry = kanbanBoard.findOrCreate("StudyRightClassesCodeGen")
            .withParent(sprint1);
      
      entry = kanbanBoard.findOrCreate("StudyRightObjectScenarios")
            .withParent(sprint1);
      entry.linkToTest("examples", "org.sdmlib.examples.studyright.StudyRightClassesCodeGen", entry.getName());
      
      entry = kanbanBoard.findOrCreate("StudyRightReverseClassModel")
            .withParent(sprint1);
      entry.linkToTest("examples", "org.sdmlib.examples.studyright.StudyRightClassesCodeGen", entry.getName());
      
      
      
      entry = kanbanBoard.findOrCreate("ClassModelCodeGen")
            .withParent(sprint1);
      
      
      entry = kanbanBoard.findOrCreate("GroupAccountCodegen")
            .withParent(sprint1);
      entry.linkToTest("examples", "org.sdmlib.examples.groupAccount.GroupAccountTests", entry.getName());
      
      entry = kanbanBoard.findOrCreate("GroupAccountRuleRecognition")
            .withParent(sprint1);
      entry.linkToTest("examples", "org.sdmlib.examples.groupAccount.GroupAccountTests", entry.getName());
      
      entry = kanbanBoard.findOrCreate("TransformationsCodegen")
            .withParent(sprint1);
      entry.linkToTest("test", "org.sdmlib.models.transformations.TransformationsCodeGen", entry.getName());
      
      KanbanEntry sprint2 = kanbanBoard.findOrCreate("Sprint.002.Transformations")
            .withLastDeveloper("zuendorf")
            .withPhase(ACTIVE)
            .withParent(kanbanBoard);
      
      entry = kanbanBoard.findOrCreate("PatternModelCodeGen")
            .withParent(sprint2)
            .linkToTest("test", "org.sdmlib.models.patterns.PatternModelCodeGen", entry.getName());
      
      entry = kanbanBoard.findOrCreate("GenericObjectDiagram")
            .withParent(sprint1);
     
      entry.linkToTest("test", "org.sdmlib.models.objects.GenericObjectsTest", entry.getName());
      
      entry = kanbanBoard.findOrCreate("LudoModel")
            .withParent(sprint2)
            .linkToTest("examples", "org.sdmlib.examples.ludo.LudoModel", entry.getName());

      entry = kanbanBoard.findOrCreate("LudoScenario")
            .withParent(sprint2)
            .linkToTest("examples", "org.sdmlib.examples.ludo.LudoScenario", entry.getName());

      man.dumpKanban();
   }

   @Test
   public void testScenarioInfrastructure()
   {
      Scenario scenario = new Scenario("test", "ScenarioInfrastructure");
      
      scenario.add("This scenario tests the scenario infrastructure. ");
      scenario.add("At first creating the html file just with text should work. ");
      scenario.add("Next we need to create some class model. This will be done in a parallel activity.");
      scenario.add("With the class model we create an object model and try to dump it here.");
      scenario.add("Well, dumping the class model would be great, either.");

      scenario.add("need to restructure design: logentries shall be direct kids of kanbanentries. \n" +
            "(has been below phase entries before.)\n" +
            "phase entries will be used for planning, in future");
      
      ClassModel model = new ClassModel(); 
      
      Clazz kanbanEntryClass = new Clazz("org.sdmlib.scenarios.KanbanEntry");

      Clazz logEntryClass = new Clazz("org.sdmlib.scenarios.LogEntry");
      
      new Association()
      .withSource("kanbanEntry", kanbanEntryClass, Role.ONE, Role.AGGREGATION)
      .withTarget("logEntries", logEntryClass, Role.MANY);
      
      scenario.addImage(model.dumpClassDiag("ScenarioClasses.001"));
      model.generate("src", "srchelpers");

      scenario.add(" Editing the log entries works now fine as part of the add method. " , 
            DONE, "zuendorf", "07.05.2012 23:36:42", 0, 0);
      
      scenario.add("Seems that we have solved the problem with the sorting of log entries after loading. " , 
         DONE, "zuendorf", "19.05.2012 19:22:42", 1, 0);
   
      ScenarioManager.get()
      .add(scenario)
      .dumpHTML();
   }
}

