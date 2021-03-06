/*
   Copyright (c) 2013 ulno (http://contact.ulno.net) 

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
package org.sdmlib.test.examples.studyrightWithAssignments;

import static org.sdmlib.models.pattern.Pattern.CREATE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Assert;
import org.junit.Test;
import org.sdmlib.CGUtil;
import org.sdmlib.models.SDMLibIdMap;
import org.sdmlib.models.YamlIdMap;
import org.sdmlib.models.classes.ClassModel;
import org.sdmlib.models.pattern.Match;
import org.sdmlib.models.pattern.ReachabilityGraph;
import org.sdmlib.models.pattern.ReachableState;
import org.sdmlib.models.pattern.RuleApplication;
import org.sdmlib.models.pattern.util.ReachabilityGraphCreator;
import org.sdmlib.models.pattern.util.ReachabilityGraphPO;
import org.sdmlib.models.pattern.util.ReachableStatePO;
import org.sdmlib.models.pattern.util.ReachableStateSet;
import org.sdmlib.models.pattern.util.RuleApplicationSet;
import org.sdmlib.models.tables.Row;
import org.sdmlib.models.tables.Table;
import org.sdmlib.models.tables.util.CellPO;
import org.sdmlib.models.tables.util.ColumnPO;
import org.sdmlib.models.tables.util.RowPO;
import org.sdmlib.models.tables.util.TablePO;
import org.sdmlib.storyboards.Storyboard;
import org.sdmlib.test.examples.studyrightWithAssignments.model.Assignment;
import org.sdmlib.test.examples.studyrightWithAssignments.model.President;
import org.sdmlib.test.examples.studyrightWithAssignments.model.Prof;
import org.sdmlib.test.examples.studyrightWithAssignments.model.Room;
import org.sdmlib.test.examples.studyrightWithAssignments.model.Student;
import org.sdmlib.test.examples.studyrightWithAssignments.model.TeachingAssistant;
import org.sdmlib.test.examples.studyrightWithAssignments.model.University;
import org.sdmlib.test.examples.studyrightWithAssignments.model.util.AssignmentPO;
import org.sdmlib.test.examples.studyrightWithAssignments.model.util.AssignmentSet;
import org.sdmlib.test.examples.studyrightWithAssignments.model.util.RoomPO;
import org.sdmlib.test.examples.studyrightWithAssignments.model.util.RoomSet;
import org.sdmlib.test.examples.studyrightWithAssignments.model.util.StudentPO;
import org.sdmlib.test.examples.studyrightWithAssignments.model.util.StudentSet;
import org.sdmlib.test.examples.studyrightWithAssignments.model.util.TeachingAssistantPO;
import org.sdmlib.test.examples.studyrightWithAssignments.model.util.TeachingAssistantSet;
import org.sdmlib.test.examples.studyrightWithAssignments.model.util.UniversityCreator;
import org.sdmlib.test.examples.studyrightWithAssignments.model.util.UniversityPO;
import org.sdmlib.test.examples.studyrightWithAssignments.model.util.UniversitySet;

import de.uniks.networkparser.IdMap;
import de.uniks.networkparser.graph.Cardinality;
import de.uniks.networkparser.graph.Clazz;
import de.uniks.networkparser.json.JsonArray;
import de.uniks.networkparser.list.SimpleSet;

public class StudyRightWithAssignmentsStoryboards
{
     /**
    * 
    * <p>Storyboard Yaml</p>
    * <p>Start: Read graph from yaml text:</p>
    * <pre>- studyRight: University 
    *   name:       &quot;\&quot;Study \&quot; Right\&quot;And\&quot;Fast now\&quot;&quot;
    *   students:   karli
    *   rooms:      mathRoom artsRoom sportsRoom examRoom softwareEngineering 
    * 
    * - karli: Student
    *   id:    4242
    *   name:  karli
    * 
    * - albert: Prof
    *   topic:  SE
    * 
    * - Assignment   content:                      points: 
    *   matrixMult:  &quot;Matrix Multiplication&quot;     5
    *   series:      &quot;Series&quot;                    6
    *   a3:          Integrals                     8
    * 
    * - Room                  topic:  credits: doors:                 students: assignments: 
    *   mathRoom:             math    17       null                   karli     [matrixMult series a3]
    *   artsRoom:             arts    16       mathRoom               null      null
    *   sportsRoom:           sports  25       [mathRoom artsRoom]
    *   examRoom:             exam     0       [sportsRoom artsRoom]
    *   softwareEngineering:  &quot;Software Engineering&quot; 42 [artsRoom examRoom]
    * </pre>
    * <p><a name = 'step_1'>Step 1: Call YamlIdMap.decode:</a></p>
    * <pre>            YamlIdMap yamlIdMap = new YamlIdMap(&quot;org.sdmlib.test.examples.studyrightWithAssignments.model&quot;);
    *       
    *       University studyRight = (University) yamlIdMap.decode(yaml);
    * </pre>
    * <p><a name = 'step_2'>Step 2: Decoded object structure:</a></p>
    * <img src="doc-files/YamlStep5.png" alt="YamlStep5.png">
    * <p>Check: root object exists "Study " Right"And"Fast now"</p>
    * <p>Check: pojo albert exists org.sdmlib.test.examples.studyrightWithAssignments.model.Prof@56e8b606</p>
    * <p>Check: pojo attr SE actual SE</p>
    * <p><a name = 'step_3'>Step 3: Generate Yaml from model:</a></p>
    * <pre>- u1: 	University
    *   name: 	&quot;\&quot;Study \&quot; Right\&quot;And\&quot;Fast now\&quot;&quot;
    *   students: 	s2 	
    *   rooms: 	r3 	r4 	r5 	r6 	r7 	
    * 
    * - s2: 	Student
    *   name: 	karli
    *   id: 	4242
    *   assignmentPoints: 	0
    *   motivation: 	0
    *   credits: 	0
    *   university: 	u1
    *   in: 	r3
    * 
    * - r3: 	Room
    *   topic: 	math
    *   credits: 	17
    *   university: 	u1
    *   students: 	s2 	
    *   doors: 	r4 	r5 	
    *   assignments: 	a8 	a9 	a10 	
    * 
    * - r4: 	Room
    *   topic: 	arts
    *   credits: 	16
    *   university: 	u1
    *   doors: 	r3 	r5 	r6 	r7 	
    * 
    * - r5: 	Room
    *   topic: 	sports
    *   credits: 	25
    *   university: 	u1
    *   doors: 	r3 	r4 	r6 	
    * 
    * - r6: 	Room
    *   topic: 	exam
    *   credits: 	0
    *   university: 	u1
    *   doors: 	r5 	r4 	r7 	
    * 
    * - r7: 	Room
    *   topic: 	&quot;Software Engineering&quot;
    *   credits: 	42
    *   university: 	u1
    *   doors: 	r4 	r6 	
    * 
    * - a8: 	Assignment
    *   content: 	&quot;Matrix Multiplication&quot;
    *   points: 	5
    *   room: 	r3
    * 
    * - a9: 	Assignment
    *   content: 	Series
    *   points: 	6
    *   room: 	r3
    * 
    * - a10: 	Assignment
    *   content: 	Integrals
    *   points: 	8
    *   room: 	r3
    * 
    * </pre>
    * <p>Check: yaml starts with - u... true</p>
    * <p><a name = 'step_4'>Step 4: decoded again:</a></p>
    * <img src="doc-files/YamlStep13.png" alt="YamlStep13.png">
    * <p><a name = 'step_5'>Step 5: now read from excel file</a></p>
    * <pre>          *       {
    *     *          &quot;type&quot;:&quot;clazz&quot;,
    *     *          &quot;id&quot;:&quot;artsRoom : Room&quot;,
    *     *          &quot;attributes&quot;:[
    *     *             &quot;credits=16&quot;,
    *     *             &quot;name=7522&quot;,
    * </pre>
    * <p>doc/StudyRightStartSituation.txt</p>
    * <pre>-	studyRight:	University				
    * 	name: 	&quot;&quot;&quot;Study Right&quot;&quot;&quot;				
    * 	students:	karli				
    * 	rooms: 	mathRoom	artsRoom	sportsRoom	examRoom	softwareEngineering
    * 						
    * -	karli:	Student				
    * 	id:	4242				
    * 	name:	karli				
    * 						
    * -	albert: 	Prof				
    * 	topic:	SE				
    * 						
    * -	Assignment	content:	points:			
    * 	matrixMult:	&quot;&quot;&quot;Matrix Multiplication&quot;&quot;&quot;	5			
    * 	series:	Series	6			
    * 	a3:	Integrals	8			
    * 						
    * -	Room	topic:	credits:	doors:	students:	assignments:
    * 	mathRoom:	math	17	null	karli	[matricMult series a3]
    * 	artsRoom:	arts	25	mathRoom		
    * 	sportsRoom:	sports	25	[mathRoom artsRoom]		
    * 	examRoom:	exam	0	[sportsRoom artsRoom]		
    * 	softwareEngineering:	&quot;&quot;&quot;Software Engineering&quot;&quot;&quot;	42	[artsRoom examRoom]		
    * </pre>
    * <p>result:</p>
    * <img src="doc-files/YamlStep19.png" alt="YamlStep19.png">
    * @throws IOException 
    */
   @Test
   public void testYaml() throws IOException
   {
      
      System.out.println(" (StudyRightWithAssignmentsStoryboards.java:85)");
      
      Storyboard story = new Storyboard();

      story.addStep("Read graph from yaml text:");

      String yaml = ""
         + "- studyRight: University \n"
         + "  name:       \"\\\"Study \\\" Right\\\"And\\\"Fast now\\\"\"\n"
         + "  students:   karli\n"
         + "  rooms:      mathRoom artsRoom sportsRoom examRoom softwareEngineering \n"
         + "\n"
         + "- karli: Student\n"
         + "  id:    4242\n"
         + "  name:  karli\n"
         + "\n"
         + "- albert: Prof\n"
         + "  topic:  SE\n"
         + "\n"
         + "- Assignment   content:                      points: \n"
         + "  matrixMult:  \"Matrix Multiplication\"     5\n"
         + "  series:      \"Series\"                    6\n"
         + "  a3:          Integrals                     8\n"
         + "\n"
         + "- Room                  topic:  credits: doors:                 students: assignments: \n"
         + "  mathRoom:             math    17       null                   karli     [matrixMult series a3]\n"
         + "  artsRoom:             arts    16       mathRoom               null      null\n"
         + "  sportsRoom:           sports  25       [mathRoom artsRoom]\n"
         + "  examRoom:             exam     0       [sportsRoom artsRoom]\n"
         + "  softwareEngineering:  \"Software Engineering\" 42 [artsRoom examRoom]\n"
         + "";

      story.addPreformatted(yaml);
      
      story.addStep("Call YamlIdMap.decode:");
      
      story.markCodeStart();
      YamlIdMap yamlIdMap = new YamlIdMap("org.sdmlib.test.examples.studyrightWithAssignments.model");
      
      University studyRight = (University) yamlIdMap.decode(yaml);
      story.addCode();
      
      story.addStep("Decoded object structure:");
      
      story.addObjectDiagramViaGraphViz(studyRight);
      
      story.assertNotNull("root object exists", studyRight);
      
      Object albert = yamlIdMap.getObject("albert");
      
      story.assertNotNull("pojo albert exists", albert);
      
      story.assertEquals("pojo attr", "SE", ((Prof)albert).getTopic());
      
      story.addStep("Generate Yaml from model:");
      
      YamlIdMap yamlEncodeMap = new YamlIdMap("org.sdmlib.test.examples.studyrightWithAssignments.model");
      
      String newYaml = yamlEncodeMap.encode(studyRight);
      
      story.addPreformatted(newYaml);
      
      story.assertTrue("yaml starts with - u...", newYaml.startsWith("- u"));
      
      YamlIdMap yamlDecodeMap = new YamlIdMap("org.sdmlib.test.examples.studyrightWithAssignments.model");
      
      University newStudyRight = (University) yamlIdMap.decode(newYaml);
      
      story.addStep("decoded again:");
      
      story.addObjectDiagramViaGraphViz(newStudyRight);
      
      story.addStep("now read from excel file");
      
      story.markCodeStart();
      byte[] readAllBytes = Files.readAllBytes(Paths.get("doc/StudyRightStartSituation.txt"));
      String excelText = new String(readAllBytes);
      
      YamlIdMap excelIdMap = new YamlIdMap("org.sdmlib.test.examples.studyrightWithAssignments.model");
      
      studyRight = (University) excelIdMap.decode(excelText);
      story.addCode();
      
      story.add("doc/StudyRightStartSituation.txt");
      story.addPreformatted(excelText);
      story.add("result:");
      story.addObjectDiagramViaGraphViz(studyRight);
      story.dumpJavaDoc(YamlIdMap.class.getName());
      story.dumpHTML();
   }

   /**
    * <p>Storyboard StudyRightWithAssignmentsStoryboard</p>
    * <p>1. (start situation/pre-condition) Karli enters the Study-Right University 
    * in the math room. Karli has no credits yet and still a motivation of 214. </p>
    * <pre>            University university = new University()
    *          .withName(&quot;StudyRight&quot;);
    * 
    *       Student karli = university.createStudents()
    *          .withId(&quot;4242&quot;)
    *          .withName(&quot;Karli&quot;);
    * 
    *       Assignment matrixMult = new Assignment()
    *          .withContent(&quot;Matrix Multiplication&quot;)
    *          .withPoints(5);
    * 
    *       Assignment series = new Assignment()
    *          .withContent(&quot;Series&quot;)
    *          .withPoints(6);
    * 
    *       Assignment a3 = new Assignment()
    *          .withContent(&quot;Integrals&quot;)
    *          .withPoints(8);
    * 
    *       Room mathRoom = university.createRooms()
    *          .withName(&quot;senate&quot;)
    *          .withTopic(&quot;math&quot;)
    *          .withCredits(17)
    *          .withStudents(karli)
    *          .withAssignments(matrixMult, series, a3);
    * 
    *       Room artsRoom = university.createRooms()
    *          .withName(&quot;7522&quot;)
    *          .withTopic(&quot;arts&quot;)
    *          .withCredits(16)
    *          .withDoors(mathRoom);
    * 
    *       Room sportsRoom = university.createRooms()
    *          .withName(&quot;gymnasium&quot;)
    *          .withTopic(&quot;sports&quot;)
    *          .withCredits(25)
    *          .withDoors(mathRoom, artsRoom);
    * 
    *       Room examRoom = university.createRooms()
    *          .withName(&quot;The End&quot;)
    *          .withTopic(&quot;exam&quot;)
    *          .withCredits(0)
    *          .withDoors(sportsRoom, artsRoom);
    * 
    *       Room softwareEngineering = university.createRooms()
    *          .withName(&quot;7422&quot;)
    *          .withTopic(&quot;Software Engineering&quot;)
    *          .withCredits(42)
    *          .withDoors(artsRoom, examRoom);
    * </pre>
    * <img src="doc-files/StudyRightWithAssignmentsStoryboardStep2.png" alt="StudyRightWithAssignmentsStoryboardStep2.png">
    * <p>2. Karli does assignment a1 on Matrix Multiplication and earns 5 points <br>
    * (general rule: the student earns always full points for doing an assignment). <br>
    * Karli's motivation is reduced by 5 points to now 209.
    * </p>
    * <pre>            karli.setAssignmentPoints(karli.getAssignmentPoints() + matrixMult.getPoints());
    *       karli.withDone(matrixMult);
    * </pre>
    * <img src="doc-files/StudyRightWithAssignmentsStoryboardStep5.png" alt="StudyRightWithAssignmentsStoryboardStep5.png">
    * <p>3. Karli does assignment a2 on Series and earns another 6 points. <br>
    * Thus Karli has 11 points now. Motivation is reduced to 203.
    * </p>
    * <pre>            karli.setAssignmentPoints(karli.getAssignmentPoints() + series.getPoints());
    *       karli.withDone(series);
    * </pre>
    * <img src="doc-files/StudyRightWithAssignmentsStoryboardStep8.png" alt="StudyRightWithAssignmentsStoryboardStep8.png">
    * <p>4. Karli does the third assignment on Integrals, earns <br>
    * another 8 points and thus Karli has now 19 points and a motivation of 195.
    * </p>
    * <pre>            karli.setAssignmentPoints(karli.getAssignmentPoints() + a3.getPoints());
    *       karli.withDone(a3);
    * </pre>
    * <img src="doc-files/StudyRightWithAssignmentsStoryboardStep11.png" alt="StudyRightWithAssignmentsStoryboardStep11.png">
    * <p>5. Since 19 points are more than the 17 points required 
    * for the 17 math credits, Karli hands the points in and earns the credits 
    * and has his assignmnet points reset to 0. <br>
    * (General rule: if the points earned by the assignments are higher or equal than 
    * the credit points, the credit points will be awarded to the student.)</p>
    * <pre>            if (karli.getAssignmentPoints() &gt;= mathRoom.getCredits())
    *       {
    *          karli.setCredits(karli.getCredits() + mathRoom.getCredits());
    *          karli.setAssignmentPoints(0);
    *       }
    * </pre>
    * <img src="doc-files/StudyRightWithAssignmentsStoryboardStep14.png" alt="StudyRightWithAssignmentsStoryboardStep14.png">
    * <p>6. (end situation/post-condition) Karli has completed the math topic and moves to sports.</p>
    * <p>Check: Karli's credits:  17 actual 17</p>
    * <p>Check: Karli's assignment points:  0 actual 0</p>
    * <p>Check: Number of students:  1 actual 1</p>
    * @see <a href='../../../../../../../../doc/StudyRightWithAssignmentsStoryboard.html'>StudyRightWithAssignmentsStoryboard.html</a>
    */
   @Test
   public void testStudyRightWithAssignmentsStoryboard()
   {
      Storyboard storyboard = new Storyboard();

      // =============================================================
      storyboard.add("1. (start situation/pre-condition) Karli enters the Study-Right University \n"
         + "in the math room. Karli has no credits yet and still a motivation of 214. ");

      storyboard.markCodeStart();
      University university = new University()
         .withName("StudyRight");

      Student karli = university.createStudents()
         .withId("4242")
         .withName("Karli");

      Assignment matrixMult = new Assignment()
         .withContent("Matrix Multiplication")
         .withPoints(5);

      Assignment series = new Assignment()
         .withContent("Series")
         .withPoints(6);

      Assignment a3 = new Assignment()
         .withContent("Integrals")
         .withPoints(8);

      Room mathRoom = university.createRooms()
         .withName("senate")
         .withTopic("math")
         .withCredits(17)
         .withStudents(karli)
         .withAssignments(matrixMult, series, a3);

      Room artsRoom = university.createRooms()
         .withName("7522")
         .withTopic("arts")
         .withCredits(16)
         .withDoors(mathRoom);

      Room sportsRoom = university.createRooms()
         .withName("gymnasium")
         .withTopic("sports")
         .withCredits(25)
         .withDoors(mathRoom, artsRoom);

      Room examRoom = university.createRooms()
         .withName("The End")
         .withTopic("exam")
         .withCredits(0)
         .withDoors(sportsRoom, artsRoom);

      Room softwareEngineering = university.createRooms()
         .withName("7422")
         .withTopic("Software Engineering")
         .withCredits(42)
         .withDoors(artsRoom, examRoom);
      storyboard.addCode();

      storyboard.addObjectDiagramViaGraphViz(
         "studyRight", university,
         "karli", "icons/karli.png", karli,
         "mathRoom", "icons/mathroom.png", mathRoom,
         "artsRoom", artsRoom,
         "sportsRoom", sportsRoom,
         "examRoom", examRoom,
         "placeToBe", softwareEngineering,
         "icons/matrix.png", matrixMult,
         "icons/limes.png", series, "icons/integralAssignment.png", a3);

      // ===============================================================================================
      storyboard.add("2. Karli does assignment a1 on Matrix Multiplication and earns 5 points <br>\n"
         + "(general rule: the student earns always full points for doing an assignment). <br>\n"
         + "Karli's motivation is reduced by 5 points to now 209.\n");

      storyboard.markCodeStart();
      karli.setAssignmentPoints(karli.getAssignmentPoints() + matrixMult.getPoints());
      karli.withDone(matrixMult);
      storyboard.addCode();

      storyboard.addObjectDiagramOnlyWithViaGraphViz(karli, mathRoom, mathRoom.getAssignments());

      // ===============================================================================================
      storyboard.add("3. Karli does assignment a2 on Series and earns another 6 points. <br>\n"
         + "Thus Karli has 11 points now. Motivation is reduced to 203.\n");

      storyboard.markCodeStart();
      karli.setAssignmentPoints(karli.getAssignmentPoints() + series.getPoints());
      karli.withDone(series);
      storyboard.addCode();

      storyboard.addObjectDiagramOnlyWithViaGraphViz(karli, mathRoom, mathRoom.getAssignments());

      // ===============================================================================================
      storyboard.add("4. Karli does the third assignment on Integrals, earns <br>\n"
         + "another 8 points and thus Karli has now 19 points and a motivation of 195.\n");

      storyboard.markCodeStart();
      karli.setAssignmentPoints(karli.getAssignmentPoints() + a3.getPoints());
      karli.withDone(a3);
      storyboard.addCode();

      storyboard.addObjectDiagramOnlyWithViaGraphViz(karli, mathRoom, mathRoom.getAssignments());

      // ===============================================================================================
      storyboard.add("5. Since 19 points are more than the 17 points required \n"
         + "for the 17 math credits, Karli hands the points in and earns the credits \n"
         + "and has his assignmnet points reset to 0. <br>\n"
         + "(General rule: if the points earned by the assignments are higher or equal than \n"
         + "the credit points, the credit points will be awarded to the student.)");

      storyboard.markCodeStart();
      if (karli.getAssignmentPoints() >= mathRoom.getCredits())
      {
         karli.setCredits(karli.getCredits() + mathRoom.getCredits());
         karli.setAssignmentPoints(0);
      }
      storyboard.addCode();

      storyboard.addObjectDiagramOnlyWithViaGraphViz(karli, mathRoom, mathRoom.getAssignments());

      // ===============================================================================================
      storyboard.add("6. (end situation/post-condition) Karli has completed the math topic and moves to sports.");

      // ===============================================================================================
      storyboard.assertEquals("Karli's credits: ", 17, karli.getCredits());
      storyboard.assertEquals("Karli's assignment points: ", 0, karli.getAssignmentPoints());
      storyboard.assertEquals("Number of students: ", 1, university.getStudents().size());

      // ================ Create HTML
      storyboard.dumpHTML();
   }


   @Test
   public void testStudyRightWithAssignmentsAggregation()
   {
      University university = new University()
            .withName("StudyRight");

         Student abu = university.createStudents()
            .withId("1337")
            .withName("Abu");

         Student karli = new TeachingAssistant().withCertified(true);
         university.withStudents(karli
            .withId("4242")
            .withName("Karli"));

         Student alice = university.createStudents()
            .withId("2323")
            .withName("Alice");

         abu.withFriends(alice);

         Assignment a1 = new Assignment()
            .withContent("Matrix Multiplication")
            .withPoints(5)
            .withStudents(abu);

         Assignment a2 = new Assignment()
            .withContent("Series")
            .withPoints(6);

         Assignment a3 = new Assignment()
            .withContent("Integrals")
            .withPoints(8);

         karli.withDone(a1, a2);

         Room mathRoom = university.createRooms()
            .withName("senate")
            .withTopic("math")
            .withCredits(17)
            .withStudents(karli)
            .withAssignments(a1, a2, a3);

         Room artsRoom = university.createRooms()
            .withName("7522")
            .withTopic("arts")
            .withCredits(16)
            .withDoors(mathRoom);

         Room sportsRoom = university.createRooms()
            .withName("gymnasium")
            .withTopic("sports")
            .withCredits(25)
            .withDoors(mathRoom, artsRoom)
            .withStudents(abu, alice);

         Assignment a4 = sportsRoom.createAssignments().withContent("Pushups").withPoints(4).withStudents(abu);

         Room examRoom = university.createRooms()
            .withName("The End")
            .withTopic("exam")
            .withCredits(0)
            .withDoors(sportsRoom, artsRoom);

         Room softwareEngineering = university.createRooms()
            .withName("7422")
            .withTopic("Software Engineering")
            .withCredits(42)
            .withDoors(artsRoom, examRoom);
         
         President president = university.createPresident();
         
         Assert.assertEquals("presidents lives", true, president.alive);
         
         university.removeYou();
         
         Assert.assertEquals("studyright has no more rooms", 0, university.getRooms().size());
         Assert.assertEquals("karli still has assignments", 2, karli.getDone().size());
         Assert.assertEquals("presidents is dead", false, president.alive);
         
   }

   /**
    * <p>Storyboard <a href='./src/test/java/org/sdmlib/test/examples/studyrightWithAssignments/StudyRightWithAssignmentsStoryboards.java' type='text/x-java'>JsonPersistency</a></p>
    * <p>How to serialize an object model to json and how to read json into an object model</p>
    * <p>Start: Example object structure:</p>
    * <p><a name = 'step_1'>Step 1: Serialize to json:</a></p>
    * <pre>          *             &quot;property&quot;:&quot;students&quot;,
    *     *             &quot;id&quot;:&quot;karli : Student&quot;
    *     *          }
    *     *       },
    *     *       {
    *     *          &quot;type&quot;:&quot;assoc&quot;,
    *     *          &quot;source&quot;:{
    *     *             &quot;cardinality&quot;:&quot;many&quot;,
    *     *             &quot;property&quot;:&quot;rooms&quot;,
    * </pre>
    * <p>Results in:</p>
    * <pre>[
    *    {
    *       "session":"demo",
    *       "class":"org.sdmlib.test.examples.studyrightWithAssignments.model.University",
    *       "id":"U527240378695639",
    *       "timestamp":"527240378695639",
    *       "prop":{
    *          "name":"StudyRight",
    *          "students":[
    *             {
    *                "session":"demo",
    *                "class":"org.sdmlib.test.examples.studyrightWithAssignments.model.Student",
    *                "id":"S527240378809973",
    *                "timestamp":"527240378809973"
    *             }
    *          ]
    *       }
    *    },
    *    {
    *       "session":"demo",
    *       "class":"org.sdmlib.test.examples.studyrightWithAssignments.model.Student",
    *       "id":"S527240378809973",
    *       "timestamp":"527240378809973",
    *       "prop":{
    *          "name":"Karli",
    *          "id":"4242",
    *          "university":{
    *             "class":"org.sdmlib.test.examples.studyrightWithAssignments.model.University",
    *             "id":"U527240378695639"
    *          }
    *       }
    *    }
    * ]</pre><p><a name = 'step_2'>Step 2: Now read it back again</a></p>
    * <pre>          *          &quot;source&quot;:{
    *     *             &quot;cardinality&quot;:&quot;many&quot;,
    *     *             &quot;property&quot;:&quot;rooms&quot;,
    *     *             &quot;id&quot;:&quot;artsRoom : Room&quot;
    *     *          },
    *     *          &quot;target&quot;:{
    *     *             &quot;cardinality&quot;:&quot;one&quot;,
    * </pre>
    * <script>
    *    var json = {
    *    "type":"objectdiagram",
    *    "nodes":[
    *       {
    *          "type":"clazz",
    *          "id":"S2 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=0",
    *             "id=4242",
    *             "in=null",
    *             "motivation=0",
    *             "name=Karli"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"U1 : University",
    *          "attributes":[
    *             "name=StudyRight",
    *             "president=null"
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S2 : Student"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasJsonPersistency9", "display":"svg", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * @see <a href='../../../../../../../../doc/JsonPersistency.html'>JsonPersistency.html</a>
    */
   @Test
   public void testJsonPersistency()
   {
      Storyboard storyboard = new Storyboard();

      storyboard.add("How to serialize an object model to json and how to read json into an object model");

      storyboard.addStep("Example object structure:");

      University university = new University()
         .withName("StudyRight");

      Student karli = university.createStudents()
         .withId("4242")
         .withName("Karli");

      Assignment a1 = new Assignment()
         .withContent("Matrix Multiplication")
         .withPoints(5);

      Assignment a2 = new Assignment()
         .withContent("Series")
         .withPoints(6);

      Assignment integrals = new Assignment()
         .withContent("Integrals")
         .withPoints(8);

//      Room mathRoom = university.createRooms()
//         .withName("senate")
//         .withTopic("math")
//         .withCredits(17)
//         .withStudents(karli)
//         .withAssignments(a1, a2, integrals);

//      Room artsRoom = university.createRooms()
//         .withName("7522")
//         .withTopic("arts")
//         .withCredits(16)
//         .withDoors(mathRoom);
//
//      Room sportsRoom = university.createRooms()
//         .withName("gymnasium")
//         .withTopic("sports")
//         .withCredits(25)
//         .withDoors(mathRoom, artsRoom);

//      Room examRoom = university.createRooms()
//         .withName("The End")
//         .withTopic("exam")
//         .withCredits(0)
//         .withDoors(sportsRoom, artsRoom);
//
//      Room softwareEngineering = university.createRooms()
//         .withName("7422")
//         .withTopic("Software Engineering")
//         .withCredits(42)
//         .withDoors(artsRoom, examRoom);

//      storyboard.addObjectDiagram(
//         "studyRight", university,
//         "karli", "icons/karli.png", karli,
//         "mathRoom", "icons/mathroom.png", mathRoom,
//         "artsRoom", artsRoom,
//         "sportsRoom", sportsRoom,
//         "examRoom", examRoom,
//         "placeToBe", softwareEngineering,
//         "icons/matrix.png", a1,
//         "icons/limes.png", a2, "icons/integralAssignment.png", integrals);

      // =====================================================
      storyboard.addStep("Serialize to json:");

      storyboard.markCodeStart();

      IdMap idMap = UniversityCreator.createIdMap("demo");

      JsonArray jsonArray = idMap.toJsonArray(university);

      String jsonText = jsonArray.toString(3);

      // you might write jsonText into a file

      storyboard.addCode();

      storyboard.add("Results in:");

      storyboard.add("<pre>" + jsonText + "</pre>");

      // =====================================================
      storyboard.addStep("Now read it back again");

      storyboard.markCodeStart();

      // read jsonText from file
      IdMap readerMap = UniversityCreator.createIdMap("demo");

      Object rootObject = readerMap.decode(jsonText);

      University readUniversity = (University) rootObject;
      storyboard.addCode();

      storyboard.addObjectDiagram(rootObject);

      storyboard.dumpHTML();
   }

   /**
    * <p>Storyboard <a href='./src/test/java/org/sdmlib/test/examples/studyrightWithAssignments/StudyRightWithAssignmentsStoryboards.java' type='text/x-java'>StudyRightObjectModelNavigationAndQueries</a></p>
    * <p>Extend the class model:</p>
    * <script>
    *    var json = {
    *    "typ":"class",
    *    "nodes":[
    *       {
    *          "typ":"node",
    *          "id":"TeachingAssistant"
    *       },
    *       {
    *          "typ":"node",
    *          "id":"Room"
    *       },
    *       {
    *          "typ":"node",
    *          "id":"Student"
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "typ":"assoc",
    *          "source":{
    *             "id":"TeachingAssistant",
    *             "cardinality":"many",
    *             "property":"tas"
    *          },
    *          "target":{
    *             "id":"Room",
    *             "cardinality":"one",
    *             "property":"room"
    *          }
    *       },
    *       {
    *          "typ":"assoc",
    *          "source":{
    *             "id":"Room",
    *             "cardinality":"one",
    *             "property":"room"
    *          },
    *          "target":{
    *             "id":"TeachingAssistant",
    *             "cardinality":"many",
    *             "property":"tas"
    *          }
    *       },
    *       {
    *          "typ":"assoc",
    *          "source":{
    *             "id":"Student",
    *             "cardinality":"many",
    *             "property":"friends"
    *          },
    *          "target":{
    *             "id":"Student",
    *             "cardinality":"many",
    *             "property":"friends"
    *          }
    *       }
    *    ]
    * }   ;
    *    new Graph(json, {"canvasid":"canvasStudyRightObjectModelNavigationAndQueriesClassDiagram1", "display":"html", fontsize:10, bar:false, propertyinfo:false}).layout(100,100);
    * </script>
    * <p>Full class model from code:</p>
    * <script>
    *    var json = {
    *    "typ":"class",
    *    "nodes":[
    *       {
    *          "typ":"node",
    *          "id":"TeachingAssistant"
    *       },
    *       {
    *          "typ":"node",
    *          "id":"SendableEntity"
    *       },
    *       {
    *          "typ":"node",
    *          "id":"SendableEntityCreator"
    *       },
    *       {
    *          "typ":"node",
    *          "id":"SimpleSet"
    *       },
    *       {
    *          "typ":"node",
    *          "id":"Assignment",
    *          "attributes":[
    *             "content : String",
    *             "points : int"
    *          ],
    *          "methods":[
    *             "createStudentsTeachingAssistant() TeachingAssistant",
    *             "firePropertyChange(String p0, Object p1, Object p2) boolean"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"President",
    *          "attributes":[
    *             "alive : boolean"
    *          ],
    *          "methods":[
    *             "firePropertyChange(String p0, Object p1, Object p2) boolean"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"Prof",
    *          "attributes":[
    *             "topic : String"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"Room",
    *          "attributes":[
    *             "credits : int",
    *             "name : String",
    *             "topic : String"
    *          ],
    *          "methods":[
    *             "createStudentsTeachingAssistant() TeachingAssistant",
    *             "findPath(int p0) String",
    *             "firePropertyChange(String p0, Object p1, Object p2) boolean",
    *             "getDoorsTransitive() RoomSet"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"Student",
    *          "attributes":[
    *             "assignmentPoints : int",
    *             "credits : int",
    *             "id : String",
    *             "motivation : int",
    *             "name : String"
    *          ],
    *          "methods":[
    *             "createFriendsTeachingAssistant() TeachingAssistant",
    *             "firePropertyChange(String p0, Object p1, Object p2) boolean",
    *             "getFriendsTransitive() StudentSet"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"TeachingAssistant",
    *          "attributes":[
    *             "certified : boolean"
    *          ],
    *          "methods":[
    *             "isCertified() boolean"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"University",
    *          "attributes":[
    *             "name : String"
    *          ],
    *          "methods":[
    *             "createStudentsTeachingAssistant() TeachingAssistant",
    *             "firePropertyChange(String p0, Object p1, Object p2) boolean"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"AssignmentCreator",
    *          "attributes":[
    *             "properties : String[]"
    *          ],
    *          "methods":[
    *             "createIdMap(String p0) IdMap",
    *             "getSendableInstance(boolean p1) Object",
    *             "getValue(Object p1, String p2) Object",
    *             "removeObject(Object p0)",
    *             "setValue(Object p1, String p2, Object p3, String p4) boolean"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"AssignmentSet",
    *          "methods":[
    *             "createAssignmentPO() AssignmentPO",
    *             "createContentCondition(String p0) AssignmentSet",
    *             "createContentCondition(String p0, String p1) AssignmentSet",
    *             "createPointsCondition(int p0) AssignmentSet",
    *             "createPointsCondition(int p0, int p1) AssignmentSet",
    *             "filterContent(String p0) AssignmentSet",
    *             "filterContent(String p0, String p1) AssignmentSet",
    *             "filterPoints(int p0) AssignmentSet",
    *             "filterPoints(int p0, int p1) AssignmentSet",
    *             "filterRoom(Object p0) AssignmentSet",
    *             "filterStudents(Object p0) AssignmentSet",
    *             "getContent() ObjectSet",
    *             "getEntryType() String",
    *             "getNewList(boolean p1) AssignmentSet",
    *             "getPoints() NumberList",
    *             "getRoom() RoomSet",
    *             "getStudents() StudentSet",
    *             "getTypClass() Class<?>",
    *             "with(Object p1) AssignmentSet",
    *             "withContent(String p0) AssignmentSet",
    *             "withPoints(int p0) AssignmentSet",
    *             "withRoom(Room p0) AssignmentSet",
    *             "withStudents(Student p0) AssignmentSet",
    *             "without(Assignment p0) AssignmentSet",
    *             "withoutStudents(Student p0) AssignmentSet"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"PresidentCreator",
    *          "attributes":[
    *             "properties : String[]"
    *          ],
    *          "methods":[
    *             "createIdMap(String p0) IdMap",
    *             "getSendableInstance(boolean p1) Object",
    *             "getValue(Object p1, String p2) Object",
    *             "removeObject(Object p0)",
    *             "setValue(Object p1, String p2, Object p3, String p4) boolean"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"PresidentSet",
    *          "methods":[
    *             "createPresidentPO() PresidentPO",
    *             "filterUniversity(Object p0) PresidentSet",
    *             "getEntryType() String",
    *             "getNewList(boolean p1) PresidentSet",
    *             "getTypClass() Class<?>",
    *             "getUniversity() UniversitySet",
    *             "with(Object p1) PresidentSet",
    *             "withUniversity(University p0) PresidentSet",
    *             "without(President p0) PresidentSet"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"RoomCreator",
    *          "attributes":[
    *             "properties : String[]"
    *          ],
    *          "methods":[
    *             "createIdMap(String p0) IdMap",
    *             "getSendableInstance(boolean p1) Object",
    *             "getValue(Object p1, String p2) Object",
    *             "removeObject(Object p0)",
    *             "setValue(Object p1, String p2, Object p3, String p4) boolean"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"RoomSet",
    *          "methods":[
    *             "createCreditsCondition(int p0) RoomSet",
    *             "createCreditsCondition(int p0, int p1) RoomSet",
    *             "createNameCondition(String p0) RoomSet",
    *             "createNameCondition(String p0, String p1) RoomSet",
    *             "createRoomPO() RoomPO",
    *             "createTopicCondition(String p0) RoomSet",
    *             "createTopicCondition(String p0, String p1) RoomSet",
    *             "filterAssignments(Object p0) RoomSet",
    *             "filterDoors(Object p0) RoomSet",
    *             "filterStudents(Object p0) RoomSet",
    *             "filterTas(Object p0) RoomSet",
    *             "filterUniversity(Object p0) RoomSet",
    *             "findPath(int p0) StringList",
    *             "getAssignments() AssignmentSet",
    *             "getCredits() NumberList",
    *             "getDoors() RoomSet",
    *             "getDoorsTransitive() RoomSet",
    *             "getEntryType() String",
    *             "getName() ObjectSet",
    *             "getNewList(boolean p1) RoomSet",
    *             "getStudents() StudentSet",
    *             "getTas() TeachingAssistantSet",
    *             "getTopic() ObjectSet",
    *             "getTypClass() Class<?>",
    *             "getUniversity() UniversitySet",
    *             "with(Object p1) RoomSet",
    *             "withAssignments(Assignment p0) RoomSet",
    *             "withCredits(int p0) RoomSet",
    *             "withDoors(Room p0) RoomSet",
    *             "withName(String p0) RoomSet",
    *             "withStudents(Student p0) RoomSet",
    *             "withTas(TeachingAssistant p0) RoomSet",
    *             "withTopic(String p0) RoomSet",
    *             "withUniversity(University p0) RoomSet",
    *             "without(Room p0) RoomSet",
    *             "withoutAssignments(Assignment p0) RoomSet",
    *             "withoutDoors(Room p0) RoomSet",
    *             "withoutStudents(Student p0) RoomSet",
    *             "withoutTas(TeachingAssistant p0) RoomSet"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"StudentCreator",
    *          "attributes":[
    *             "properties : String[]"
    *          ],
    *          "methods":[
    *             "createIdMap(String p0) IdMap",
    *             "getSendableInstance(boolean p1) Object",
    *             "getValue(Object p1, String p2) Object",
    *             "removeObject(Object p0)",
    *             "setValue(Object p1, String p2, Object p3, String p4) boolean"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"StudentSet",
    *          "methods":[
    *             "createAssignmentPointsCondition(int p0) StudentSet",
    *             "createAssignmentPointsCondition(int p0, int p1) StudentSet",
    *             "createCreditsCondition(int p0) StudentSet",
    *             "createCreditsCondition(int p0, int p1) StudentSet",
    *             "createIdCondition(String p0) StudentSet",
    *             "createIdCondition(String p0, String p1) StudentSet",
    *             "createMotivationCondition(int p0) StudentSet",
    *             "createMotivationCondition(int p0, int p1) StudentSet",
    *             "createNameCondition(String p0) StudentSet",
    *             "createNameCondition(String p0, String p1) StudentSet",
    *             "createStudentPO() StudentPO",
    *             "filterAssignmentPoints(int p0) StudentSet",
    *             "filterAssignmentPoints(int p0, int p1) StudentSet",
    *             "filterCredits(int p0) StudentSet",
    *             "filterCredits(int p0, int p1) StudentSet",
    *             "filterDone(Object p0) StudentSet",
    *             "filterFriends(Object p0) StudentSet",
    *             "filterId(String p0) StudentSet",
    *             "filterId(String p0, String p1) StudentSet",
    *             "filterIn(Object p0) StudentSet",
    *             "filterMotivation(int p0) StudentSet",
    *             "filterMotivation(int p0, int p1) StudentSet",
    *             "filterName(String p0) StudentSet",
    *             "filterName(String p0, String p1) StudentSet",
    *             "filterUniversity(Object p0) StudentSet",
    *             "getAssignmentPoints() NumberList",
    *             "getCredits() NumberList",
    *             "getDone() AssignmentSet",
    *             "getEntryType() String",
    *             "getFriends() StudentSet",
    *             "getFriendsTransitive() StudentSet",
    *             "getId() ObjectSet",
    *             "getIn() RoomSet",
    *             "getMotivation() NumberList",
    *             "getName() ObjectSet",
    *             "getNewList(boolean p1) StudentSet",
    *             "getTypClass() Class<?>",
    *             "getUniversity() UniversitySet",
    *             "instanceOfTeachingAssistant() TeachingAssistantSet",
    *             "with(Object p1) StudentSet",
    *             "withAssignmentPoints(int p0) StudentSet",
    *             "withCredits(int p0) StudentSet",
    *             "withDone(Assignment p0) StudentSet",
    *             "withFriends(Student p0) StudentSet",
    *             "withId(String p0) StudentSet",
    *             "withIn(Room p0) StudentSet",
    *             "withMotivation(int p0) StudentSet",
    *             "withName(String p0) StudentSet",
    *             "withUniversity(University p0) StudentSet",
    *             "without(Student p0) StudentSet",
    *             "withoutDone(Assignment p0) StudentSet",
    *             "withoutFriends(Student p0) StudentSet"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"TeachingAssistantCreator",
    *          "attributes":[
    *             "properties : String[]"
    *          ],
    *          "methods":[
    *             "createIdMap(String p0) IdMap",
    *             "getSendableInstance(boolean p1) Object",
    *             "getValue(Object p1, String p2) Object",
    *             "removeObject(Object p0)",
    *             "setValue(Object p1, String p2, Object p3, String p4) boolean"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"TeachingAssistantSet",
    *          "methods":[
    *             "createAssignmentPointsCondition(int p0) TeachingAssistantSet",
    *             "createAssignmentPointsCondition(int p0, int p1) TeachingAssistantSet",
    *             "createCertifiedCondition(boolean p0) TeachingAssistantSet",
    *             "createCreditsCondition(int p0) TeachingAssistantSet",
    *             "createCreditsCondition(int p0, int p1) TeachingAssistantSet",
    *             "createIdCondition(String p0) TeachingAssistantSet",
    *             "createIdCondition(String p0, String p1) TeachingAssistantSet",
    *             "createMotivationCondition(int p0) TeachingAssistantSet",
    *             "createMotivationCondition(int p0, int p1) TeachingAssistantSet",
    *             "createNameCondition(String p0) TeachingAssistantSet",
    *             "createNameCondition(String p0, String p1) TeachingAssistantSet",
    *             "createTeachingAssistantPO() TeachingAssistantPO",
    *             "filterAssignmentPoints(int p0) TeachingAssistantSet",
    *             "filterAssignmentPoints(int p0, int p1) TeachingAssistantSet",
    *             "filterCertified(boolean p0) TeachingAssistantSet",
    *             "filterCredits(int p0) TeachingAssistantSet",
    *             "filterCredits(int p0, int p1) TeachingAssistantSet",
    *             "filterDone(Object p0) TeachingAssistantSet",
    *             "filterFriends(Object p0) TeachingAssistantSet",
    *             "filterId(String p0) TeachingAssistantSet",
    *             "filterId(String p0, String p1) TeachingAssistantSet",
    *             "filterIn(Object p0) TeachingAssistantSet",
    *             "filterMotivation(int p0) TeachingAssistantSet",
    *             "filterMotivation(int p0, int p1) TeachingAssistantSet",
    *             "filterName(String p0) TeachingAssistantSet",
    *             "filterName(String p0, String p1) TeachingAssistantSet",
    *             "filterRoom(Object p0) TeachingAssistantSet",
    *             "filterUniversity(Object p0) TeachingAssistantSet",
    *             "getAssignmentPoints() NumberList",
    *             "getCertified() BooleanList",
    *             "getCredits() NumberList",
    *             "getDone() AssignmentSet",
    *             "getEntryType() String",
    *             "getFriends() StudentSet",
    *             "getFriendsTransitive() StudentSet",
    *             "getId() ObjectSet",
    *             "getIn() RoomSet",
    *             "getMotivation() NumberList",
    *             "getName() ObjectSet",
    *             "getNewList(boolean p1) TeachingAssistantSet",
    *             "getRoom() RoomSet",
    *             "getTypClass() Class<?>",
    *             "getUniversity() UniversitySet",
    *             "with(Object p1) TeachingAssistantSet",
    *             "withAssignmentPoints(int p0) TeachingAssistantSet",
    *             "withCertified(boolean p0) TeachingAssistantSet",
    *             "withCredits(int p0) TeachingAssistantSet",
    *             "withDone(Assignment p0) TeachingAssistantSet",
    *             "withFriends(Student p0) TeachingAssistantSet",
    *             "withId(String p0) TeachingAssistantSet",
    *             "withIn(Room p0) TeachingAssistantSet",
    *             "withMotivation(int p0) TeachingAssistantSet",
    *             "withName(String p0) TeachingAssistantSet",
    *             "withRoom(Room p0) TeachingAssistantSet",
    *             "withUniversity(University p0) TeachingAssistantSet",
    *             "without(TeachingAssistant p0) TeachingAssistantSet",
    *             "withoutDone(Assignment p0) TeachingAssistantSet",
    *             "withoutFriends(Student p0) TeachingAssistantSet"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"UniversityCreator",
    *          "attributes":[
    *             "properties : String[]"
    *          ],
    *          "methods":[
    *             "createIdMap(String p0) IdMap",
    *             "getSendableInstance(boolean p1) Object",
    *             "getValue(Object p1, String p2) Object",
    *             "removeObject(Object p0)",
    *             "setValue(Object p1, String p2, Object p3, String p4) boolean"
    *          ]
    *       },
    *       {
    *          "typ":"node",
    *          "id":"UniversitySet",
    *          "methods":[
    *             "createNameCondition(String p0) UniversitySet",
    *             "createNameCondition(String p0, String p1) UniversitySet",
    *             "createUniversityPO() UniversityPO",
    *             "filterName(String p0) UniversitySet",
    *             "filterName(String p0, String p1) UniversitySet",
    *             "filterPresident(Object p0) UniversitySet",
    *             "filterRooms(Object p0) UniversitySet",
    *             "filterStudents(Object p0) UniversitySet",
    *             "getEntryType() String",
    *             "getName() ObjectSet",
    *             "getNewList(boolean p1) UniversitySet",
    *             "getPresident() PresidentSet",
    *             "getRooms() RoomSet",
    *             "getStudents() StudentSet",
    *             "getTypClass() Class<?>",
    *             "with(Object p1) UniversitySet",
    *             "withName(String p0) UniversitySet",
    *             "withPresident(President p0) UniversitySet",
    *             "withRooms(Room p0) UniversitySet",
    *             "withStudents(Student p0) UniversitySet",
    *             "without(University p0) UniversitySet",
    *             "withoutRooms(Room p0) UniversitySet",
    *             "withoutStudents(Student p0) UniversitySet"
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "typ":"assoc",
    *          "source":{
    *             "id":"TeachingAssistant",
    *             "cardinality":"many",
    *             "property":"tas"
    *          },
    *          "target":{
    *             "id":"Room",
    *             "cardinality":"one",
    *             "property":"room"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"Assignment",
    *             "cardinality":"one",
    *             "property":"assignment"
    *          },
    *          "target":{
    *             "id":"SendableEntity",
    *             "cardinality":"one",
    *             "property":"sendableentity"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"President",
    *             "cardinality":"one",
    *             "property":"president"
    *          },
    *          "target":{
    *             "id":"SendableEntity",
    *             "cardinality":"one",
    *             "property":"sendableentity"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"Room",
    *             "cardinality":"one",
    *             "property":"room"
    *          },
    *          "target":{
    *             "id":"SendableEntity",
    *             "cardinality":"one",
    *             "property":"sendableentity"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"Student",
    *             "cardinality":"one",
    *             "property":"student"
    *          },
    *          "target":{
    *             "id":"SendableEntity",
    *             "cardinality":"one",
    *             "property":"sendableentity"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"University",
    *             "cardinality":"one",
    *             "property":"university"
    *          },
    *          "target":{
    *             "id":"SendableEntity",
    *             "cardinality":"one",
    *             "property":"sendableentity"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"AssignmentCreator",
    *             "cardinality":"one",
    *             "property":"assignmentcreator"
    *          },
    *          "target":{
    *             "id":"SendableEntityCreator",
    *             "cardinality":"one",
    *             "property":"sendableentitycreator"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"PresidentCreator",
    *             "cardinality":"one",
    *             "property":"presidentcreator"
    *          },
    *          "target":{
    *             "id":"SendableEntityCreator",
    *             "cardinality":"one",
    *             "property":"sendableentitycreator"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"RoomCreator",
    *             "cardinality":"one",
    *             "property":"roomcreator"
    *          },
    *          "target":{
    *             "id":"SendableEntityCreator",
    *             "cardinality":"one",
    *             "property":"sendableentitycreator"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"StudentCreator",
    *             "cardinality":"one",
    *             "property":"studentcreator"
    *          },
    *          "target":{
    *             "id":"SendableEntityCreator",
    *             "cardinality":"one",
    *             "property":"sendableentitycreator"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"TeachingAssistantCreator",
    *             "cardinality":"one",
    *             "property":"teachingassistantcreator"
    *          },
    *          "target":{
    *             "id":"SendableEntityCreator",
    *             "cardinality":"one",
    *             "property":"sendableentitycreator"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"UniversityCreator",
    *             "cardinality":"one",
    *             "property":"universitycreator"
    *          },
    *          "target":{
    *             "id":"SendableEntityCreator",
    *             "cardinality":"one",
    *             "property":"sendableentitycreator"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"AssignmentSet",
    *             "cardinality":"one",
    *             "property":"assignmentset"
    *          },
    *          "target":{
    *             "id":"SimpleSet",
    *             "cardinality":"one",
    *             "property":"simpleset"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"PresidentSet",
    *             "cardinality":"one",
    *             "property":"presidentset"
    *          },
    *          "target":{
    *             "id":"SimpleSet",
    *             "cardinality":"one",
    *             "property":"simpleset"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"RoomSet",
    *             "cardinality":"one",
    *             "property":"roomset"
    *          },
    *          "target":{
    *             "id":"SimpleSet",
    *             "cardinality":"one",
    *             "property":"simpleset"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"StudentSet",
    *             "cardinality":"one",
    *             "property":"studentset"
    *          },
    *          "target":{
    *             "id":"SimpleSet",
    *             "cardinality":"one",
    *             "property":"simpleset"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"TeachingAssistantSet",
    *             "cardinality":"one",
    *             "property":"teachingassistantset"
    *          },
    *          "target":{
    *             "id":"SimpleSet",
    *             "cardinality":"one",
    *             "property":"simpleset"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"UniversitySet",
    *             "cardinality":"one",
    *             "property":"universityset"
    *          },
    *          "target":{
    *             "id":"SimpleSet",
    *             "cardinality":"one",
    *             "property":"simpleset"
    *          }
    *       },
    *       {
    *          "typ":"assoc",
    *          "source":{
    *             "id":"Assignment",
    *             "cardinality":"many",
    *             "property":"assignments"
    *          },
    *          "target":{
    *             "id":"Room",
    *             "cardinality":"one",
    *             "property":"room"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"Assignment",
    *             "cardinality":"one",
    *             "property":"assignment"
    *          },
    *          "target":{
    *             "id":"SendableEntity",
    *             "cardinality":"one",
    *             "property":"sendableentity"
    *          }
    *       },
    *       {
    *          "typ":"assoc",
    *          "source":{
    *             "id":"President",
    *             "cardinality":"one",
    *             "property":"president"
    *          },
    *          "target":{
    *             "id":"University",
    *             "cardinality":"one",
    *             "property":"university"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"President",
    *             "cardinality":"one",
    *             "property":"president"
    *          },
    *          "target":{
    *             "id":"SendableEntity",
    *             "cardinality":"one",
    *             "property":"sendableentity"
    *          }
    *       },
    *       {
    *          "typ":"assoc",
    *          "source":{
    *             "id":"Room",
    *             "cardinality":"one",
    *             "property":"room"
    *          },
    *          "target":{
    *             "id":"TeachingAssistant",
    *             "cardinality":"many",
    *             "property":"tas"
    *          }
    *       },
    *       {
    *          "typ":"assoc",
    *          "source":{
    *             "id":"Room",
    *             "cardinality":"many",
    *             "property":"rooms"
    *          },
    *          "target":{
    *             "id":"University",
    *             "cardinality":"one",
    *             "property":"university"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"Room",
    *             "cardinality":"one",
    *             "property":"room"
    *          },
    *          "target":{
    *             "id":"SendableEntity",
    *             "cardinality":"one",
    *             "property":"sendableentity"
    *          }
    *       },
    *       {
    *          "typ":"assoc",
    *          "source":{
    *             "id":"Student",
    *             "cardinality":"many",
    *             "property":"friends"
    *          },
    *          "target":{
    *             "id":"Student",
    *             "cardinality":"many",
    *             "property":"friends"
    *          }
    *       },
    *       {
    *          "typ":"assoc",
    *          "source":{
    *             "id":"Student",
    *             "cardinality":"many",
    *             "property":"students"
    *          },
    *          "target":{
    *             "id":"University",
    *             "cardinality":"one",
    *             "property":"university"
    *          }
    *       },
    *       {
    *          "typ":"assoc",
    *          "source":{
    *             "id":"Student",
    *             "cardinality":"many",
    *             "property":"students"
    *          },
    *          "target":{
    *             "id":"Room",
    *             "cardinality":"one",
    *             "property":"in"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"Student",
    *             "cardinality":"one",
    *             "property":"student"
    *          },
    *          "target":{
    *             "id":"SendableEntity",
    *             "cardinality":"one",
    *             "property":"sendableentity"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"TeachingAssistant",
    *             "cardinality":"one",
    *             "property":"teachingassistant"
    *          },
    *          "target":{
    *             "id":"Student",
    *             "cardinality":"one",
    *             "property":"student"
    *          }
    *       },
    *       {
    *          "typ":"assoc",
    *          "source":{
    *             "id":"TeachingAssistant",
    *             "cardinality":"many",
    *             "property":"tas"
    *          },
    *          "target":{
    *             "id":"Room",
    *             "cardinality":"one",
    *             "property":"room"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"TeachingAssistant",
    *             "cardinality":"one",
    *             "property":"teachingassistant"
    *          },
    *          "target":{
    *             "id":"Student",
    *             "cardinality":"one",
    *             "property":"student"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"University",
    *             "cardinality":"one",
    *             "property":"university"
    *          },
    *          "target":{
    *             "id":"SendableEntity",
    *             "cardinality":"one",
    *             "property":"sendableentity"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"AssignmentCreator",
    *             "cardinality":"one",
    *             "property":"assignmentcreator"
    *          },
    *          "target":{
    *             "id":"SendableEntityCreator",
    *             "cardinality":"one",
    *             "property":"sendableentitycreator"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"AssignmentSet",
    *             "cardinality":"one",
    *             "property":"assignmentset"
    *          },
    *          "target":{
    *             "id":"SimpleSet",
    *             "cardinality":"one",
    *             "property":"simpleset"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"PresidentCreator",
    *             "cardinality":"one",
    *             "property":"presidentcreator"
    *          },
    *          "target":{
    *             "id":"SendableEntityCreator",
    *             "cardinality":"one",
    *             "property":"sendableentitycreator"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"PresidentSet",
    *             "cardinality":"one",
    *             "property":"presidentset"
    *          },
    *          "target":{
    *             "id":"SimpleSet",
    *             "cardinality":"one",
    *             "property":"simpleset"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"RoomCreator",
    *             "cardinality":"one",
    *             "property":"roomcreator"
    *          },
    *          "target":{
    *             "id":"SendableEntityCreator",
    *             "cardinality":"one",
    *             "property":"sendableentitycreator"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"RoomSet",
    *             "cardinality":"one",
    *             "property":"roomset"
    *          },
    *          "target":{
    *             "id":"SimpleSet",
    *             "cardinality":"one",
    *             "property":"simpleset"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"StudentCreator",
    *             "cardinality":"one",
    *             "property":"studentcreator"
    *          },
    *          "target":{
    *             "id":"SendableEntityCreator",
    *             "cardinality":"one",
    *             "property":"sendableentitycreator"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"StudentSet",
    *             "cardinality":"one",
    *             "property":"studentset"
    *          },
    *          "target":{
    *             "id":"SimpleSet",
    *             "cardinality":"one",
    *             "property":"simpleset"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"TeachingAssistantCreator",
    *             "cardinality":"one",
    *             "property":"teachingassistantcreator"
    *          },
    *          "target":{
    *             "id":"SendableEntityCreator",
    *             "cardinality":"one",
    *             "property":"sendableentitycreator"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"TeachingAssistantSet",
    *             "cardinality":"one",
    *             "property":"teachingassistantset"
    *          },
    *          "target":{
    *             "id":"SimpleSet",
    *             "cardinality":"one",
    *             "property":"simpleset"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"UniversityCreator",
    *             "cardinality":"one",
    *             "property":"universitycreator"
    *          },
    *          "target":{
    *             "id":"SendableEntityCreator",
    *             "cardinality":"one",
    *             "property":"sendableentitycreator"
    *          }
    *       },
    *       {
    *          "typ":"generalisation",
    *          "source":{
    *             "id":"UniversitySet",
    *             "cardinality":"one",
    *             "property":"universityset"
    *          },
    *          "target":{
    *             "id":"SimpleSet",
    *             "cardinality":"one",
    *             "property":"simpleset"
    *          }
    *       }
    *    ]
    * }   ;
    *    new Graph(json, {"canvasid":"canvasStudyRightObjectModelNavigationAndQueriesClassDiagram3", "display":"html", fontsize:10, bar:false, propertyinfo:false}).layout(100,100);
    * </script>
    * <p>How to navigate and query an object model.</p>
    * <p>Start: Example object structure:</p>
    * <script>
    *    var json = {
    *    "type":"objectdiagram",
    *    "nodes":[
    *       {
    *          "type":"clazz",
    *          "id":"A10 : Assignment",
    *          "attributes":[
    *             "content=Matrix Multiplication",
    *             "points=5"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"A11 : Assignment",
    *          "attributes":[
    *             "content=Pushups",
    *             "points=4"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"A12 : Assignment",
    *          "attributes":[
    *             "content=Series",
    *             "points=6"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"A13 : Assignment",
    *          "attributes":[
    *             "content=Integrals",
    *             "points=8"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R5 : Room",
    *          "attributes":[
    *             "credits=17",
    *             "name=senate",
    *             "topic=math"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R6 : Room",
    *          "attributes":[
    *             "credits=16",
    *             "name=7522",
    *             "topic=arts"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R7 : Room",
    *          "attributes":[
    *             "credits=25",
    *             "name=gymnasium",
    *             "topic=sports"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R8 : Room",
    *          "attributes":[
    *             "credits=0",
    *             "name=The End",
    *             "topic=exam"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R9 : Room",
    *          "attributes":[
    *             "credits=42",
    *             "name=7422",
    *             "topic=Software Engineering"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S2 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=0",
    *             "id=1337",
    *             "motivation=0",
    *             "name=Abu"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S4 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=0",
    *             "id=2323",
    *             "motivation=0",
    *             "name=Alice"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"T3 : TeachingAssistant",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "certified=true",
    *             "credits=0",
    *             "id=4242",
    *             "motivation=0",
    *             "name=Karli",
    *             "room=null"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"U1 : University",
    *          "attributes":[
    *             "name=StudyRight",
    *             "president=null"
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"assignments",
    *             "id":"A10 : Assignment"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R5 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"assignments",
    *             "id":"A12 : Assignment"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R5 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"assignments",
    *             "id":"A13 : Assignment"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R5 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"assignments",
    *             "id":"A11 : Assignment"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R7 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"done",
    *             "id":"A10 : Assignment"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"done",
    *             "id":"A11 : Assignment"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"done",
    *             "id":"A10 : Assignment"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"T3 : TeachingAssistant"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"done",
    *             "id":"A12 : Assignment"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"T3 : TeachingAssistant"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R6 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R5 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R7 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R5 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R7 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R6 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R8 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R6 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R9 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R6 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R8 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R7 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R9 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R8 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"friends",
    *             "id":"S4 : Student"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"friends",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R7 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R5 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"T3 : TeachingAssistant"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R7 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S4 : Student"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"rooms",
    *             "id":"R5 : Room"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"rooms",
    *             "id":"R6 : Room"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"rooms",
    *             "id":"R7 : Room"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"rooms",
    *             "id":"R8 : Room"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"rooms",
    *             "id":"R9 : Room"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S2 : Student"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"T3 : TeachingAssistant"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S4 : Student"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightObjectModelNavigationAndQueries7", "display":"svg", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <p><a name = 'step_1'>Step 1: Simple set based navigation:</a></p>
    * <pre>            story.addObjectDiagram(university);
    * 
    *       &#x2F;&#x2F; =====================================================
    *       story.addStep(&quot;Simple set based navigation:&quot;);
    * 
    * </pre>
    * <p>Results in:</p>
    * <pre>      Sum of assignment points: 23.0. 
    *       Sum of points of assignments that have been done by at least one students: 15.0.</pre>
    * <p>Check: Assignment points:  23.0 actual 23.0</p>
    * <p>Check: donePoints:  15.0 actual 15.0</p>
    * <p><a name = 'step_2'>Step 2: Rooms with assignments not yet done by Karli:</a></p>
    * <pre>          *          &quot;attributes&quot;:[
    *     *             &quot;assignmentPoints : int&quot;,
    *     *             &quot;credits : int&quot;,
    *     *             &quot;id : String&quot;,
    *     *             &quot;motivation : int&quot;,
    * </pre>
    * <p>Results in:</p>
    * <pre>      (senate math 17, gymnasium sports 25)</pre>
    * <p>Check: rooms.size():  2 actual 2</p>
    * <p><a name = 'step_3'>Step 3: Filter for attribute:</a></p>
    * <pre>          *          &quot;attributes&quot;:[
    *     *             &quot;certified : boolean&quot;
    *     *          ],
    *     *          &quot;methods&quot;:[
    * </pre>
    * <p>Results in:</p>
    * <pre>      rooms17: (senate math 17)
    *       roomsGE20: (gymnasium sports 25, 7422 Software Engineering 42)</pre>
    * <p><a name = 'step_4'>Step 4: Filter for even values:</a></p>
    * <pre>          *             &quot;createStudentsTeachingAssistant() TeachingAssistant&quot;,
    *     *             &quot;firePropertyChange(String p0, Object p1, Object p2) boolean&quot;
    *     *          ]
    * </pre>
    * <p>Results in:</p>
    * <pre>      (7522 arts 16, The End exam 0, 7422 Software Engineering 42)</pre>
    * <p><a name = 'step_5'>Step 5: Filter for type: </a></p>
    * <pre>          *             &quot;removeObject(Object p0)&quot;,
    *     *             &quot;setValue(Object p1, String p2, Object p3, String p4) boolean&quot;
    *     *          ]
    * </pre>
    * <pre>(Karli 4242 0 0 0)</pre>
    * <p><a name = 'step_6'>Step 6: Write operations on sets: </a></p>
    * <pre>          *             &quot;createPointsCondition(int p0, int p1) AssignmentSet&quot;,
    *     *             &quot;filterContent(String p0) AssignmentSet&quot;,
    *     *             &quot;filterContent(String p0, String p1) AssignmentSet&quot;,
    * </pre>
    * <script>
    *    var json = {
    *    "type":"objectdiagram",
    *    "nodes":[
    *       {
    *          "type":"clazz",
    *          "id":"R5 : Room"
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R7 : Room"
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S2 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=0",
    *             "id=1337",
    *             "motivation=42",
    *             "name=Abu"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S4 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=0",
    *             "id=2323",
    *             "motivation=42",
    *             "name=Alice"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"T3 : TeachingAssistant",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "certified=true",
    *             "credits=0",
    *             "id=4242",
    *             "motivation=42",
    *             "name=Karli",
    *             "room=null"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"U1 : University",
    *          "attributes":[
    *             "president=null"
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"friends",
    *             "id":"S4 : Student"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"friends",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R7 : Room"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"student",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R7 : Room"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"student",
    *             "id":"S4 : Student"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R5 : Room"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"teachingassistant",
    *             "id":"T3 : TeachingAssistant"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"student",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"student",
    *             "id":"S4 : Student"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"teachingassistant",
    *             "id":"T3 : TeachingAssistant"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightObjectModelNavigationAndQueries32", "display":"svg", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <p><a name = 'step_7'>Step 7: Rooms with two students that are friends (and need supervision): </a></p>
    * <pre>          *             &quot;getStudents() StudentSet&quot;,
    *     *             &quot;getTypClass() Class&lt;?&gt;&quot;,
    *     *             &quot;with(Object p1) AssignmentSet&quot;,
    *     *             &quot;withContent(String p0) AssignmentSet&quot;,
    *     *             &quot;withPoints(int p0) AssignmentSet&quot;,
    *     *             &quot;withRoom(Room p0) AssignmentSet&quot;,
    *     *             &quot;withStudents(Student p0) AssignmentSet&quot;,
    *     *             &quot;without(Assignment p0) AssignmentSet&quot;,
    *     *             &quot;withoutStudents(Student p0) AssignmentSet&quot;
    * </pre>
    * <script>
    *    var json = {
    *    "type":"object",
    *    "nodes":[
    *       {
    *          "type":"patternObject",
    *          "id":"r1 : RoomPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"s2 : StudentPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"s3 : StudentPO",
    *          "attributes":[
    *             "motivation == 42"
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r1 : RoomPO"
    *          },
    *          "target":{
    *             "property":"students",
    *             "id":"s2 : StudentPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r1 : RoomPO"
    *          },
    *          "target":{
    *             "property":"students",
    *             "id":"s3 : StudentPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"s3 : StudentPO"
    *          },
    *          "target":{
    *             "property":"friends",
    *             "id":"s2 : StudentPO"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightObjectModelNavigationAndQueriesPatternDiagram34", "display":"html", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <p>Results in:</p>
    * <pre>      (gymnasium sports 25)</pre>
    * <p><a name = 'step_8'>Step 8: Rooms with two students with low motivation that are friends (and need supervision): </a></p>
    * <pre>          *             &quot;setValue(Object p1, String p2, Object p3, String p4) boolean&quot;
    *     *          ]
    *     *       },
    *     *       {
    *     *          &quot;typ&quot;:&quot;node&quot;,
    *     *          &quot;id&quot;:&quot;PresidentSet&quot;,
    *     *          &quot;methods&quot;:[
    *     *             &quot;createPresidentPO() PresidentPO&quot;,
    *     *             &quot;filterUniversity(Object p0) PresidentSet&quot;,
    *     *             &quot;getEntryType() String&quot;,
    *     *             &quot;getNewList(boolean p1) PresidentSet&quot;,
    * </pre>
    * <script>
    *    var json = {
    *    "type":"object",
    *    "nodes":[
    *       {
    *          "type":"patternObject",
    *          "id":"r1 : RoomPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"s2 : StudentPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"s3 : StudentPO",
    *          "attributes":[
    *             "motivation in [0..50]"
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r1 : RoomPO"
    *          },
    *          "target":{
    *             "property":"students",
    *             "id":"s2 : StudentPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r1 : RoomPO"
    *          },
    *          "target":{
    *             "property":"students",
    *             "id":"s3 : StudentPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"s3 : StudentPO"
    *          },
    *          "target":{
    *             "property":"friends",
    *             "id":"s2 : StudentPO"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightObjectModelNavigationAndQueriesPatternDiagram39", "display":"html", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <p>Results in:</p>
    * <pre>      (gymnasium sports 25)</pre>
    * <p><a name = 'step_9'>Step 9: Rooms with two students without supervision that are friends and add teaching assistance: </a></p>
    * <pre>          *          ],
    *     *          &quot;methods&quot;:[
    *     *             &quot;createIdMap(String p0) IdMap&quot;,
    *     *             &quot;getSendableInstance(boolean p1) Object&quot;,
    *     *             &quot;getValue(Object p1, String p2) Object&quot;,
    *     *             &quot;removeObject(Object p0)&quot;,
    *     *             &quot;setValue(Object p1, String p2, Object p3, String p4) boolean&quot;
    *     *          ]
    *     *       },
    *     *       {
    *     *          &quot;typ&quot;:&quot;node&quot;,
    *     *          &quot;id&quot;:&quot;RoomSet&quot;,
    *     *          &quot;methods&quot;:[
    *     *             &quot;createCreditsCondition(int p0) RoomSet&quot;,
    *     *             &quot;createCreditsCondition(int p0, int p1) RoomSet&quot;,
    * </pre>
    * <script>
    *    var json = {
    *    "type":"object",
    *    "nodes":[
    *       {
    *          "type":"patternObject",
    *          "id":"u1 : UniversityPO",
    *          "attributes":[
    *             "<< bound>>"
    *          ]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"r2 : RoomPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"s3 : StudentPO",
    *          "attributes":[
    *             "motivation in [0..42]"
    *          ]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"s4 : StudentPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"objectdiagram",
    *          "style":"nac",
    *          "info":"NegativeApplicationCondition",
    *          "nodes":[
    *             {
    *                "type":"patternObject",
    *                "id":"p5 : PatternObject",
    *                "attributes":[]
    *             }
    *          ]
    *       },
    *       {
    *          "type":"patternObject",
    *          "style":"create",
    *          "id":"t6 : TeachingAssistantPO",
    *          "attributes":[
    *             "<< create>>"
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"u1 : UniversityPO"
    *          },
    *          "target":{
    *             "property":"rooms",
    *             "id":"r2 : RoomPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r2 : RoomPO"
    *          },
    *          "target":{
    *             "property":"students",
    *             "id":"s3 : StudentPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r2 : RoomPO"
    *          },
    *          "target":{
    *             "property":"students",
    *             "id":"s4 : StudentPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"s4 : StudentPO"
    *          },
    *          "target":{
    *             "property":"friends",
    *             "id":"s3 : StudentPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r2 : RoomPO"
    *          },
    *          "target":{
    *             "property":"tas",
    *             "id":"p5 : PatternObject"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r2 : RoomPO"
    *          },
    *          "target":{
    *             "property":"tas",
    *             "id":"t6 : TeachingAssistantPO"
    *          },
    *          "style":"create"
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightObjectModelNavigationAndQueriesPatternDiagram44", "display":"html", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <p>Results in:</p>
    * <script>
    *    var json = {
    *    "type":"objectdiagram",
    *    "nodes":[
    *       {
    *          "type":"clazz",
    *          "id":"R7 : Room",
    *          "attributes":[
    *             "credits=25",
    *             "name=gymnasium",
    *             "topic=sports"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S2 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=0",
    *             "id=1337",
    *             "motivation=42",
    *             "name=Abu"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S4 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=0",
    *             "id=2323",
    *             "motivation=42",
    *             "name=Alice"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"T14 : TeachingAssistant",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "certified=false",
    *             "credits=0",
    *             "id=null",
    *             "in=null",
    *             "motivation=0",
    *             "name=null",
    *             "university=null"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"U1 : University",
    *          "attributes":[
    *             "president=null"
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"friends",
    *             "id":"S4 : Student"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"friends",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S2 : Student"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R7 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S4 : Student"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R7 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"tas",
    *             "id":"T14 : TeachingAssistant"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R7 : Room"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R7 : Room"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"student",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"student",
    *             "id":"S4 : Student"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightObjectModelNavigationAndQueries47", "display":"svg", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <pre>      (gymnasium sports 25)</pre>
    * <p><a name = 'step_10'>Step 10: TAs as students in a room: </a></p>
    * <pre>          *             &quot;getTas() TeachingAssistantSet&quot;,
    *     *             &quot;getTopic() ObjectSet&quot;,
    *     *             &quot;getTypClass() Class&lt;?&gt;&quot;,
    *     *             &quot;getUniversity() UniversitySet&quot;,
    *     *             &quot;with(Object p1) RoomSet&quot;,
    *     *             &quot;withAssignments(Assignment p0) RoomSet&quot;,
    *     *             &quot;withCredits(int p0) RoomSet&quot;,
    *     *             &quot;withDoors(Room p0) RoomSet&quot;,
    *     *             &quot;withName(String p0) RoomSet&quot;,
    * </pre>
    * <script>
    *    var json = {
    *    "type":"object",
    *    "nodes":[
    *       {
    *          "type":"patternObject",
    *          "id":"r1 : RoomPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"s2 : StudentPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"t3 : TeachingAssistantPO",
    *          "attributes":[]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r1 : RoomPO"
    *          },
    *          "target":{
    *             "property":"students",
    *             "id":"s2 : StudentPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"s2 : StudentPO"
    *          },
    *          "target":{
    *             "property":"instanceof",
    *             "id":"t3 : TeachingAssistantPO"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightObjectModelNavigationAndQueriesPatternDiagram50", "display":"html", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <script>
    *    var json = {
    *    "type":"objectdiagram",
    *    "nodes":[
    *       {
    *          "type":"clazz",
    *          "id":"R5 : Room"
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"T3 : TeachingAssistant",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "certified=true",
    *             "credits=0",
    *             "id=4242",
    *             "motivation=42",
    *             "name=Karli",
    *             "room=null"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"U1 : University",
    *          "attributes":[
    *             "president=null"
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R5 : Room"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"teachingassistant",
    *             "id":"T3 : TeachingAssistant"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"teachingassistant",
    *             "id":"T3 : TeachingAssistant"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightObjectModelNavigationAndQueries52", "display":"svg", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <p><a name = 'step_11'>Step 11: Double motivation of all students: </a></p>
    * <pre>          *       },
    *     *       {
    *     *          &quot;typ&quot;:&quot;node&quot;,
    *     *          &quot;id&quot;:&quot;StudentCreator&quot;,
    *     *          &quot;attributes&quot;:[
    *     *             &quot;properties : String[]&quot;
    *     *          ],
    *     *          &quot;methods&quot;:[
    *     *             &quot;createIdMap(String p0) IdMap&quot;,
    *     *             &quot;getSendableInstance(boolean p1) Object&quot;,
    *     *             &quot;getValue(Object p1, String p2) Object&quot;,
    *     *             &quot;removeObject(Object p0)&quot;,
    *     *             &quot;setValue(Object p1, String p2, Object p3, String p4) boolean&quot;
    *     *          ]
    *     *       },
    *     *       {
    *     *          &quot;typ&quot;:&quot;node&quot;,
    *     *          &quot;id&quot;:&quot;StudentSet&quot;,
    *     *          &quot;methods&quot;:[
    *     *             &quot;createAssignmentPointsCondition(int p0) StudentSet&quot;,
    *     *             &quot;createAssignmentPointsCondition(int p0, int p1) StudentSet&quot;,
    *     *             &quot;createCreditsCondition(int p0) StudentSet&quot;,
    *     *             &quot;createCreditsCondition(int p0, int p1) StudentSet&quot;,
    *     *             &quot;createIdCondition(String p0) StudentSet&quot;,
    *     *             &quot;createIdCondition(String p0, String p1) StudentSet&quot;,
    *     *             &quot;createMotivationCondition(int p0) StudentSet&quot;,
    *     *             &quot;createMotivationCondition(int p0, int p1) StudentSet&quot;,
    *     *             &quot;createNameCondition(String p0) StudentSet&quot;,
    *     *             &quot;createNameCondition(String p0, String p1) StudentSet&quot;,
    *     *             &quot;createStudentPO() StudentPO&quot;,
    *     *             &quot;filterAssignmentPoints(int p0) StudentSet&quot;,
    *     *             &quot;filterAssignmentPoints(int p0, int p1) StudentSet&quot;,
    *     *             &quot;filterCredits(int p0) StudentSet&quot;,
    *     *             &quot;filterCredits(int p0, int p1) StudentSet&quot;,
    *     *             &quot;filterDone(Object p0) StudentSet&quot;,
    *     *             &quot;filterFriends(Object p0) StudentSet&quot;,
    *     *             &quot;filterId(String p0) StudentSet&quot;,
    *     *             &quot;filterId(String p0, String p1) StudentSet&quot;,
    *     *             &quot;filterIn(Object p0) StudentSet&quot;,
    *     *             &quot;filterMotivation(int p0) StudentSet&quot;,
    *     *             &quot;filterMotivation(int p0, int p1) StudentSet&quot;,
    *     *             &quot;filterName(String p0) StudentSet&quot;,
    * </pre>
    * <script>
    *    var json = {
    *    "type":"object",
    *    "nodes":[
    *       {
    *          "type":"patternObject",
    *          "id":"r1 : RoomPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"s2 : StudentPO",
    *          "attributes":[]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r1 : RoomPO"
    *          },
    *          "target":{
    *             "property":"students",
    *             "id":"s2 : StudentPO"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightObjectModelNavigationAndQueriesPatternDiagram54", "display":"html", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <script>
    *    var json = {
    *    "type":"objectdiagram",
    *    "nodes":[
    *       {
    *          "type":"clazz",
    *          "id":"R5 : Room",
    *          "attributes":[
    *             "credits=17",
    *             "name=senate",
    *             "topic=math"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R7 : Room",
    *          "attributes":[
    *             "credits=25",
    *             "name=gymnasium",
    *             "topic=sports"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S2 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=0",
    *             "id=1337",
    *             "motivation=168",
    *             "name=Abu"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S4 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=0",
    *             "id=2323",
    *             "motivation=168",
    *             "name=Alice"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"T3 : TeachingAssistant",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "certified=true",
    *             "credits=0",
    *             "id=4242",
    *             "motivation=168",
    *             "name=Karli",
    *             "room=null"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"U1 : University",
    *          "attributes":[
    *             "president=null"
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R5 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R7 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"friends",
    *             "id":"S4 : Student"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"friends",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R7 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R5 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"T3 : TeachingAssistant"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S4 : Student"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R7 : Room"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"student",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R7 : Room"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"student",
    *             "id":"S4 : Student"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"teachingassistant",
    *             "id":"T3 : TeachingAssistant"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R5 : Room"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightObjectModelNavigationAndQueries56", "display":"svg", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <p><a name = 'step_12'>Step 12: lure students from other rooms into math room: </a></p>
    * <pre>          *             &quot;getMotivation() NumberList&quot;,
    *     *             &quot;getName() ObjectSet&quot;,
    *     *             &quot;getNewList(boolean p1) StudentSet&quot;,
    *     *             &quot;getTypClass() Class&lt;?&gt;&quot;,
    *     *             &quot;getUniversity() UniversitySet&quot;,
    *     *             &quot;instanceOfTeachingAssistant() TeachingAssistantSet&quot;,
    *     *             &quot;with(Object p1) StudentSet&quot;,
    *     *             &quot;withAssignmentPoints(int p0) StudentSet&quot;,
    *     *             &quot;withCredits(int p0) StudentSet&quot;,
    *     *             &quot;withDone(Assignment p0) StudentSet&quot;,
    *     *             &quot;withFriends(Student p0) StudentSet&quot;,
    * </pre>
    * <script>
    *    var json = {
    *    "type":"object",
    *    "nodes":[
    *       {
    *          "type":"patternObject",
    *          "id":"r1 : RoomPO",
    *          "attributes":[
    *             "<< bound>>"
    *          ]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"s2 : StudentPO",
    *          "attributes":[]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r1 : RoomPO"
    *          },
    *          "target":{
    *             "property":null,
    *             "id":"s2 : StudentPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"s2 : StudentPO"
    *          },
    *          "target":{
    *             "property":"in",
    *             "id":"r1 : RoomPO"
    *          },
    *          "style":"create"
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightObjectModelNavigationAndQueriesPatternDiagram58", "display":"html", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <script>
    *    var json = {
    *    "type":"objectdiagram",
    *    "nodes":[
    *       {
    *          "type":"clazz",
    *          "id":"R5 : Room",
    *          "attributes":[
    *             "credits=17",
    *             "name=senate",
    *             "topic=math"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R6 : Room",
    *          "attributes":[
    *             "credits=16",
    *             "name=7522",
    *             "topic=arts"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R7 : Room",
    *          "attributes":[
    *             "credits=25",
    *             "name=gymnasium",
    *             "topic=sports"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S2 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=0",
    *             "id=1337",
    *             "motivation=168",
    *             "name=Abu"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S4 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=0",
    *             "id=2323",
    *             "motivation=168",
    *             "name=Alice"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"T3 : TeachingAssistant",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "certified=true",
    *             "credits=0",
    *             "id=4242",
    *             "motivation=168",
    *             "name=Karli",
    *             "room=null"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"U1 : University",
    *          "attributes":[
    *             "president=null"
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R6 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R5 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R7 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R5 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R7 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R6 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"friends",
    *             "id":"S4 : Student"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"friends",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"T3 : TeachingAssistant"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R5 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S2 : Student"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R5 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S4 : Student"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R5 : Room"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R5 : Room"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"teachingassistant",
    *             "id":"T3 : TeachingAssistant"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"student",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"student",
    *             "id":"S4 : Student"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R6 : Room"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R7 : Room"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightObjectModelNavigationAndQueries60", "display":"svg", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <p>Check: New students in math room:  3 actual 3</p>
    * @see <a href=
    *      '../../../../../../../../doc/StudyRightObjectModelNavigationAndQueries.html'>
    *      StudyRightObjectModelNavigationAndQueries.html</a>
    * @see <a href=
    *      '../../../../../../../../doc/StudyRightObjectModelNavigationAndQueries.html'>StudyRightObjectModelNavigationAndQueries.html</a>
    * @see <a href='../../../../../../../../doc/StudyRightObjectModelNavigationAndQueries.html'>StudyRightObjectModelNavigationAndQueries.html</a>
 */
   @Test
   public void testStudyRightObjectModelNavigationAndQueries()
   {
      Storyboard story = new Storyboard();

      story.add("Extend the class model:");

      ClassModel model = new ClassModel();

      Clazz studentClass = model.createClazz(Student.class.getName());

      studentClass.withBidirectional(studentClass, "friends", Cardinality.MANY, "friends", Cardinality.MANY);

      Clazz roomClass = model.createClazz(Room.class.getName());

      Clazz taClass = model.createClazz("TeachingAssistant");

      roomClass.withBidirectional(taClass, "tas", Cardinality.MANY, "room", Cardinality.ONE);

      story.addClassDiagram(model);

      // model.generate("examples");

      model.getGenerator().updateFromCode("src/test/java", "org.sdmlib.test.examples.studyrightWithAssignments.model");

      story.add("Full class model from code:");

      story.addClassDiagram(model);

      story.add("How to navigate and query an object model.");

      story.addStep("Example object structure:");

      University university = new University()
         .withName("StudyRight");

      Student abu = university.createStudents()
         .withId("1337")
         .withName("Abu");

      Student karli = new TeachingAssistant().withCertified(true);
      university.withStudents(karli
         .withId("4242")
         .withName("Karli"));

      Student alice = university.createStudents()
         .withId("2323")
         .withName("Alice");

      abu.withFriends(alice);

      Assignment a1 = new Assignment()
         .withContent("Matrix Multiplication")
         .withPoints(5)
         .withStudents(abu);

      Assignment a2 = new Assignment()
         .withContent("Series")
         .withPoints(6);

      Assignment a3 = new Assignment()
         .withContent("Integrals")
         .withPoints(8);

      karli.withDone(a1, a2);

      Room mathRoom = university.createRooms()
         .withName("senate")
         .withTopic("math")
         .withCredits(17)
         .withStudents(karli)
         .withAssignments(a1, a2, a3);

      Room artsRoom = university.createRooms()
         .withName("7522")
         .withTopic("arts")
         .withCredits(16)
         .withDoors(mathRoom);

      Room sportsRoom = university.createRooms()
         .withName("gymnasium")
         .withTopic("sports")
         .withCredits(25)
         .withDoors(mathRoom, artsRoom)
         .withStudents(abu, alice);

      Assignment a4 = sportsRoom.createAssignments().withContent("Pushups").withPoints(4).withStudents(abu);

      Room examRoom = university.createRooms()
         .withName("The End")
         .withTopic("exam")
         .withCredits(0)
         .withDoors(sportsRoom, artsRoom);

      Room softwareEngineering = university.createRooms()
         .withName("7422")
         .withTopic("Software Engineering")
         .withCredits(42)
         .withDoors(artsRoom, examRoom);

      story.addObjectDiagram(university);

      // =====================================================
      story.addStep("Simple set based navigation:");

      story.markCodeStart();

      double assignmentPoints = university.getRooms().getAssignments().getPoints().sum();

      double donePoints = university.getStudents().getDone().getPoints().sum();

      story.addCode();

      story.add("Results in:");

      String text = CGUtil.replaceAll(
         "      Sum of assignment points: 42. \n" +
            "      Sum of points of assignments that have been done by at least one students: 84.",
         "42", assignmentPoints,
         "84", donePoints);

      story.addPreformatted(text);

      story.assertEquals("Assignment points: ", 23.0, assignmentPoints);
      story.assertEquals("donePoints: ", 15.0, donePoints);

      // =====================================================
      story.addStep("Rooms with assignments not yet done by Karli:");

      story.markCodeStart();

      AssignmentSet availableAssignments = university.getRooms().getAssignments().minus(karli.getDone());

      RoomSet rooms = availableAssignments.getRoom();

      story.addCode();

      story.add("Results in:");

      story.addPreformatted("      " + rooms.toString());

      story.assertEquals("rooms.size(): ", 2, rooms.size());

      story.addStep("Filter for attribute:");

      story.markCodeStart();

      RoomSet rooms17 = university.getRooms().createCreditsCondition(17);
      RoomSet roomsGE20 = university.getRooms().createCreditsCondition(20, Integer.MAX_VALUE);

      story.addCode();

      story.add("Results in:");

      story.addPreformatted("      rooms17: " + rooms17.toString()
         + "\n      roomsGE20: " + roomsGE20);

      story.addStep("Filter for even values:");

      story.markCodeStart();

      SimpleSet<Room> roomsEven = university.getRooms().filter(r -> r.getCredits() % 2 == 0);

      story.addCode();

      story.add("Results in:");

      story.addPreformatted("      " + roomsEven);

      
      // ====================================================
      story.addStep("Filter for type: ");

      story.markCodeStart();

      TeachingAssistantSet taStudents = university.getRooms().getStudents().instanceOfTeachingAssistant();

      story.addCode();

      story.addPreformatted("" + taStudents);

      
      // ====================================================
      story.addStep("Write operations on sets: ");

      story.markCodeStart();

      university.getStudents().withMotivation(42);

      story.addCode();

      story.addObjectDiagramOnlyWith(university.getStudents());

      
      // =====================================================
      story.addStep("Rooms with two students that are friends (and need supervision): ");

      story.markCodeStart();

      RoomPO roomPO = university.getRooms().createRoomPO();

      StudentPO stud1PO = roomPO.createStudentsPO();

      roomPO.createStudentsPO().createMotivationCondition(42).createFriendsLink(stud1PO);

      rooms = roomPO.allMatches();

      story.addCode();

      story.addPattern(roomPO, false);

      story.add("Results in:");

      story.addPreformatted("      " + rooms.toString());

      
      // =====================================================
      story.addStep("Rooms with two students with low motivation that are friends (and need supervision): ");

      story.markCodeStart();

      roomPO = university.getRooms().createRoomPO();

      stud1PO = roomPO.createStudentsPO();

      final StudentPO stud2PO = roomPO.createStudentsPO().createMotivationCondition(0, 50);

      stud2PO.createFriendsLink(stud1PO);

      rooms = roomPO.allMatches();

      story.addCode();

      story.addPattern(roomPO, false);

      story.add("Results in:");

      story.addPreformatted("      " + rooms.toString());

      // =====================================================
      story.addStep("Rooms with two students without supervision that are friends and add teaching assistance: ");

      story.markCodeStart();

      UniversityPO uniPO = new UniversityPO(university);

      roomPO = uniPO.createRoomsPO();

      stud1PO = roomPO.createStudentsPO().createMotivationCondition(0, 42);

      roomPO.createStudentsPO().createFriendsLink(stud1PO);

      roomPO.createTasLink(null);

      roomPO.createTasPO(CREATE);

      rooms = roomPO.allMatches();

      story.addCode();

      story.addPattern(uniPO, false);

      // story.add("Internal pattern structure for debugging.");
      //
      // story.addObjectDiagramWith(roomPO.getPattern(),
      // roomPO.getPattern().getElementsTransitive(null));

      story.add("Results in:");

      story.addObjectDiagramOnlyWith(rooms, rooms.getStudents(), rooms.getTas());

      story.addPreformatted("      " + rooms.toString());

      // =====================================================
      story.addStep("TAs as students in a room: ");

      story.markCodeStart();

      roomPO = university.getRooms().createRoomPO();

      stud1PO = roomPO.createStudentsPO();

      TeachingAssistantPO taPO = stud1PO.instanceOf(new TeachingAssistantPO());

      TeachingAssistantSet taSet = taPO.allMatches();

      story.addCode();

      story.addPattern(roomPO, false);

      story.addObjectDiagramOnlyWith(taSet, taSet.getRoom());

      // =====================================================
      story.addStep("Double motivation of all students: ");

      story.markCodeStart();

      roomPO = university.getRooms().createRoomPO();

      stud1PO = roomPO.createStudentsPO();

      for (Match match : (Iterable<Match>) roomPO.getPattern())
      {
         Student currentMatch = stud1PO.getCurrentMatch();

         currentMatch.withMotivation(currentMatch.getMotivation() * 2);

         // or more simple:
         stud1PO.withMotivation(stud1PO.getMotivation() * 2);

         Room assertMatch = roomPO.getCurrentMatch();

         if (match.number == 1)
         {
            Assert.assertEquals("Karli", currentMatch.getName());
            Assert.assertEquals("senate", assertMatch.getName());
            Assert.assertEquals("math", assertMatch.getTopic());
            Assert.assertEquals(17, assertMatch.getCredits());
         }
         else if (match.number == 2)
         {
            Assert.assertEquals("Abu", currentMatch.getName());
            Assert.assertEquals("gymnasium", assertMatch.getName());
            Assert.assertEquals("sports", assertMatch.getTopic());
            Assert.assertEquals(25, assertMatch.getCredits());
         }
         else if (match.number == 3)
         {
            Assert.assertEquals("Alice", currentMatch.getName());
            Assert.assertEquals("gymnasium", assertMatch.getName());
            Assert.assertEquals("sports", assertMatch.getTopic());
            Assert.assertEquals(25, assertMatch.getCredits());
         }

         // System.out.println("match " + match.number + ": " + currentMatch + "
         // in room " + roomPO.getCurrentMatch());
      }

      story.addCode();

      story.addPattern(roomPO, false);

      story.addObjectDiagramOnlyWith(university.getStudents(), university.getStudents().getIn());

      // =====================================================
      story.addStep("lure students from other rooms into math room: ");

      story.markCodeStart();

      roomPO = new RoomPO(mathRoom);

      stud1PO = roomPO.createPath(r -> ((Room) r).getDoors().getStudents(), new StudentPO());

      stud1PO.startCreate();

      stud1PO.createInLink(roomPO);

      stud1PO.allMatches();

      story.addCode();

      story.addPattern(roomPO, false);

      story.addObjectDiagramOnlyWith(mathRoom, mathRoom.getDoors(), mathRoom.getStudents());

      story.assertEquals("New students in math room: ", 3, mathRoom.getStudents().size());

      story.dumpHTML();
   }

   
   /**
    * @see <a href='../../../../../../../../doc/StudyRightTablesAndReports.html'>StudyRightTablesAndReports.html</a>
    */
   @Test
   public void testReports()
   {
      University university = new University()
         .withName("StudyRight");

      Student abu = university.createStudents()
         .withId("1337")
         .withName("Abu");

      Student alice = university.createStudents()
         .withId("2323")
         .withName("Alice");

      abu.withFriends(alice);

      IdMap map=new IdMap();
      map.with(new org.sdmlib.test.examples.studyrightWithAssignments.model.util.UniversityCreator());
      map.with(new org.sdmlib.test.examples.studyrightWithAssignments.model.util.StudentCreator());
      
      System.out.println(map.toJsonArray(university).toString(2));
   }
   
   /**
    * <p>Storyboard StudyRightTablesAndReports</p>
    * <p>How to generate table reports from a model.</p>
    * <p>Start: Example object structure:</p>
    * <img src="doc-files/StudyRightTablesAndReportsStep2.png" alt="StudyRightTablesAndReportsStep2.png">
    * <p><a name = 'step_1'>Step 1: Query for table</a></p>
    * <pre>      
    *          UniversityPO universityPO = new UniversityPO(university);
    * 
    *          RoomPO createRoomsPO = universityPO.createRoomsPO();
    * 
    *          Table table = universityPO.createResultTable();
    * 
    * </pre>
    * <script>
    *    var json = {
    *    "type":"object",
    *    "nodes":[
    *       {
    *          "type":"patternObject",
    *          "id":"u1 : UniversityPO",
    *          "attributes":[
    *             "<< bound>>"
    *          ]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"r2 : RoomPO",
    *          "attributes":[]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"u1 : UniversityPO"
    *          },
    *          "target":{
    *             "property":"rooms",
    *             "id":"r2 : RoomPO"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightTablesAndReportsPatternDiagram5", "display":"html", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <p>Results in:</p>
    * <table style="width: auto;" class="table table-bordered table-condensed">
    * <thead>
    * <tr>
    * <th class="text-center">A</th>
    * <th class="text-center">B</th>
    * </tr>
    * </thead>
    * 
    * <tbody>
    * <tr>
    * <td >StudyRight</td>
    * <td >senate math 17</td>
    * </tr>
    * <tr>
    * <td >StudyRight</td>
    * <td >7522 arts 16</td>
    * </tr>
    * <tr>
    * <td >StudyRight</td>
    * <td >gymnasium sports 25</td>
    * </tr>
    * </tbody>
    * 
    * </table>
    * <script>
    *    var json = {
    *    "type":"object",
    *    "nodes":[
    *       {
    *          "type":"patternObject",
    *          "id":"t1 : TablePO",
    *          "attributes":[
    *             "<< bound>>"
    *          ]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"r2 : RowPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"c3 : CellPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"r4 : RoomPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"objectdiagram",
    *          "style":"nac",
    *          "info":"NegativeApplicationCondition",
    *          "nodes":[
    *             {
    *                "type":"patternObject",
    *                "id":"s5 : StudentPO",
    *                "attributes":[]
    *             }
    *          ]
    *       },
    *       {
    *          "type":"objectdiagram",
    *          "style":"nac",
    *          "info":"OptionalSubPattern",
    *          "nodes":[
    *             {
    *                "type":"patternObject",
    *                "id":"c6 : CellPO",
    *                "attributes":[]
    *             }
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"t1 : TablePO"
    *          },
    *          "target":{
    *             "property":"rows",
    *             "id":"r2 : RowPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r2 : RowPO"
    *          },
    *          "target":{
    *             "property":"cells",
    *             "id":"c3 : CellPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"c3 : CellPO"
    *          },
    *          "target":{
    *             "property":"value",
    *             "id":"r4 : RoomPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r4 : RoomPO"
    *          },
    *          "target":{
    *             "property":"students",
    *             "id":"s5 : StudentPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r2 : RowPO"
    *          },
    *          "target":{
    *             "property":"cells",
    *             "id":"c6 : CellPO"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightTablesAndReportsPatternDiagram8", "display":"html", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <pre>      
    *          table.createColumns(&quot;Topic&quot;, row -&gt; {
    *             Room r = row.getCellValue(&quot;B&quot;);
    *             return r.getTopic();
    *          });
    *          table.createColumns(&quot;Credits&quot;, row -&gt; ((Room) row.getCellValue(&quot;B&quot;)).getCredits())
    *             .withTdCssClass(&quot;text-right&quot;);
    *          table.createColumns(&quot;Students&quot;, row -&gt; ((Room) row.getCellValue(&quot;B&quot;)).getStudents().size())
    *             .withTdCssClass(&quot;text-right&quot;);
    *          table.withoutColumns(&quot;A&quot;, &quot;B&quot;);
    * 
    * </pre>
    * <table style="width: auto;" class="table table-bordered table-condensed">
    * <thead>
    * <tr>
    * <th class="text-center">Topic</th>
    * <th class="text-center">Credits</th>
    * <th class="text-center">Students</th>
    * </tr>
    * </thead>
    * 
    * <tbody>
    * <tr>
    * <td >math</td>
    * <td class="text-right">17</td>
    * <td class="text-right">1</td>
    * </tr>
    * <tr>
    * <td >arts</td>
    * <td class="text-right">16</td>
    * <td class="text-right">0</td>
    * </tr>
    * <tr>
    * <td >sports</td>
    * <td class="text-right">25</td>
    * <td class="text-right">2</td>
    * </tr>
    * </tbody>
    * 
    * </table>
    * <script>
    *    var json = {
    *    "type":"object",
    *    "nodes":[
    *       {
    *          "type":"patternObject",
    *          "id":"t1 : TablePO",
    *          "attributes":[
    *             "<< bound>>"
    *          ]
    *       },
    *       {
    *          "type":"patternObject",
    *          "style":"create",
    *          "id":"c2 : ColumnPO",
    *          "attributes":[
    *             "<< create>>",
    *             "name == Topic"
    *          ]
    *       },
    *       {
    *          "type":"objectdiagram",
    *          "style":"nac",
    *          "info":"OptionalSubPattern",
    *          "nodes":[
    *             {
    *                "type":"patternObject",
    *                "id":"r3 : RowPO",
    *                "attributes":[]
    *             },
    *             {
    *                "type":"patternObject",
    *                "id":"c4 : CellPO",
    *                "attributes":[]
    *             },
    *             {
    *                "type":"patternObject",
    *                "id":"r5 : RoomPO",
    *                "attributes":[]
    *             },
    *             {
    *                "type":"patternObject",
    *                "style":"create",
    *                "id":"c6 : CellPO",
    *                "attributes":[
    *                   "<< create>>",
    *                   "value == r5.topic"
    *                ]
    *             }
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"t1 : TablePO"
    *          },
    *          "target":{
    *             "property":"columns",
    *             "id":"c2 : ColumnPO"
    *          },
    *          "style":"create"
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"t1 : TablePO"
    *          },
    *          "target":{
    *             "property":"rows",
    *             "id":"r3 : RowPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r3 : RowPO"
    *          },
    *          "target":{
    *             "property":"cells",
    *             "id":"c4 : CellPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"c4 : CellPO"
    *          },
    *          "target":{
    *             "property":"value",
    *             "id":"r5 : RoomPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"c2 : ColumnPO"
    *          },
    *          "target":{
    *             "property":"cells",
    *             "id":"c6 : CellPO"
    *          },
    *          "style":"create"
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightTablesAndReportsPatternDiagram11", "display":"html", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <p><a name = 'step_2'>Step 2: List all topics:</a></p>
    * <pre>      
    *          UniversityPO universityPO = new UniversityPO(university);
    * 
    *          TablePO tablePO = new TablePO(CREATE);
    * 
    *          universityPO.addToPattern(tablePO);
    * 
    *          tablePO.createNameAssignment(&quot;University&quot;);
    * 
    *          ColumnPO col1PO = tablePO.createColumnsPO(CREATE).createNameAssignment(&quot;Topic&quot;);
    * 
    *          ColumnPO col2PO = tablePO.createColumnsPO(CREATE)
    *             .createNameAssignment(&quot;Credits&quot;)
    *             .createTdCssClassAssignment(&quot;text-right&quot;);
    * 
    *          ColumnPO col3PO = tablePO.createColumnsPO(CREATE)
    *             .createNameAssignment(&quot;Students&quot;)
    *             .createTdCssClassAssignment(&quot;text-right&quot;);
    * 
    *          RoomPO roomsPO = universityPO.createRoomsPO();
    * 
    *          RowPO rowPO = tablePO.createRowsPO(CREATE);
    * 
    *          CellPO cell1PO = rowPO.createCellsPO(CREATE).createColumnLink(col1PO, CREATE);
    *          cell1PO.createCondition(cell -&gt; cell.withValue(roomsPO.getTopic()) != null);
    * 
    *          CellPO cell2PO = rowPO.createCellsPO(CREATE).createColumnLink(col2PO, CREATE);
    *          cell2PO.createCondition(cell -&gt; cell.withValue(roomsPO.getCredits()) != null);
    * 
    *          CellPO cell3PO = rowPO.createCellsPO(CREATE).createColumnLink(col3PO, CREATE);
    *          cell3PO.createCondition(cell -&gt; cell.withValue(roomsPO.getStudents().size()) != null);
    *          
    *          universityPO.doAllMatches();
    * 
    * </pre>
    * <p>Results in:</p>
    * <table style="width: auto;" class="table table-bordered table-condensed">
    * <thead>
    * <tr>
    * <th class="text-center">Topic</th>
    * <th class="text-center">Credits</th>
    * <th class="text-center">Students</th>
    * </tr>
    * </thead>
    * 
    * <tbody>
    * <tr>
    * <td >math</td>
    * <td class="text-right">17</td>
    * <td class="text-right">1</td>
    * </tr>
    * <tr>
    * <td >arts</td>
    * <td class="text-right">16</td>
    * <td class="text-right">0</td>
    * </tr>
    * <tr>
    * <td >sports</td>
    * <td class="text-right">25</td>
    * <td class="text-right">2</td>
    * </tr>
    * </tbody>
    * 
    * </table>
    * <img src="doc-files/StudyRightTablesAndReportsStep16.png" alt="StudyRightTablesAndReportsStep16.png">
    * <p><a name = 'step_3'>Step 3: Do a nested table</a></p>
    * <pre>      
    *          UniversityPO universityPO = new UniversityPO(university);
    * 
    *          RoomPO createRoomsPO = universityPO.createRoomsPO();
    * 
    *          Table table = universityPO.createResultTable();
    * 
    *          table.createColumns(&quot;Topic&quot;, row -&gt; ((Room) row.getCellValue(&quot;B&quot;)).getTopic());
    *          table.createColumns(&quot;Assignments&quot;, row -&gt; addAssignments(row));
    *          table.createColumns(&quot;Students&quot;, row -&gt; ((Room) row.getCellValue(&quot;B&quot;)).getStudents().size())
    *             .withTdCssClass(&quot;text-right&quot;);
    *          table.withoutColumns(&quot;A&quot;, &quot;B&quot;);
    * 
    * </pre>
    * <table style="width: auto;" class="table table-bordered table-condensed">
    * <thead>
    * <tr>
    * <th class="text-center">Topic</th>
    * <th class="text-center">Assignments</th>
    * <th class="text-center">Students</th>
    * </tr>
    * </thead>
    * 
    * <tbody>
    * <tr>
    * <td >math</td>
    * <td ><table style="width: auto;" class="table table-bordered table-condensed">
    * <thead>
    * <tr>
    * <th class="text-center">Content</th>
    * <th class="text-center">Points</th>
    * </tr>
    * </thead>
    * 
    * <tbody>
    * <tr>
    * <td >Matrix Multiplication</td>
    * <td class="text-right">5</td>
    * </tr>
    * <tr>
    * <td >Series</td>
    * <td class="text-right">6</td>
    * </tr>
    * <tr>
    * <td >Integrals</td>
    * <td class="text-right">8</td>
    * </tr>
    * </tbody>
    * 
    * </table>
    * </td>
    * <td class="text-right">1</td>
    * </tr>
    * <tr>
    * <td >arts</td>
    * <td ><table style="width: auto;" class="table table-bordered table-condensed">
    * <thead>
    * <tr>
    * <th class="text-center">Content</th>
    * <th class="text-center">Points</th>
    * </tr>
    * </thead>
    * 
    * <tbody>
    * </tbody>
    * 
    * </table>
    * </td>
    * <td class="text-right">0</td>
    * </tr>
    * <tr>
    * <td >sports</td>
    * <td ><table style="width: auto;" class="table table-bordered table-condensed">
    * <thead>
    * <tr>
    * <th class="text-center">Content</th>
    * <th class="text-center">Points</th>
    * </tr>
    * </thead>
    * 
    * <tbody>
    * </tbody>
    * 
    * </table>
    * </td>
    * <td class="text-right">2</td>
    * </tr>
    * </tbody>
    * 
    * </table>
    * @see <a href='../../../../../../../../doc/StudyRightTablesAndReports.html'>StudyRightTablesAndReports.html</a>
    */
   @Test
   public void testStudyRightTablesAndReports()
   {
      Storyboard story = new Storyboard();

      story.add("How to generate table reports from a model.");

      story.addStep("Example object structure:");

      University university = new University()
         .withName("StudyRight");

      Student abu = university.createStudents()
         .withId("1337")
         .withName("Abu");

      Student karli = new TeachingAssistant().withCertified(true);
      university.withStudents(karli
         .withId("4242")
         .withName("Karli"));

      Student alice = university.createStudents()
         .withId("2323")
         .withName("Alice");

      abu.withFriends(alice);

      Assignment a1 = new Assignment()
         .withContent("Matrix Multiplication")
         .withPoints(5)
         .withStudents(abu);

      Assignment a2 = new Assignment()
         .withContent("Series")
         .withPoints(6);
//
      Assignment a3 = new Assignment()
         .withContent("Integrals")
         .withPoints(8);

      karli.withDone(a1, a2);

      Room mathRoom = university.createRooms()
         .withName("senate")
         .withTopic("math")
         .withCredits(17)
         .withStudents(karli)
         .withAssignments(a1, a2, a3);

      Room artsRoom = university.createRooms()
         .withName("7522")
         .withTopic("arts")
         .withCredits(16)
         .withDoors(mathRoom);

      Room sportsRoom = university.createRooms()
         .withName("gymnasium")
         .withTopic("sports")
         .withCredits(25)
         .withDoors(mathRoom, artsRoom)
         .withStudents(abu, alice);

//      Assignment a4 = sportsRoom.createAssignments().withContent("Pushups").withPoints(4).withStudents(abu);

//      Room examRoom = university.createRooms()
//         .withName("The End")
//         .withTopic("exam")
//         .withCredits(0)
//         .withDoors(sportsRoom, artsRoom);

//      Room softwareEngineering = university.createRooms()
//         .withName("7422")
//         .withTopic("Software Engineering")
//         .withCredits(42)
//         .withDoors(artsRoom, examRoom);

      story.addObjectDiagramViaGraphViz(university);

      story.addStep("Query for table");

      {
         story.markCodeStart();

         UniversityPO universityPO = new UniversityPO(university);

         RoomPO createRoomsPO = universityPO.createRoomsPO();

         Table table = universityPO.createResultTable();

         story.addCode();
         
         story.addPattern(universityPO, false);

         story.add("Results in:");

         story.addTable(table);

         // filter row rule
         TablePO tablePO = new TablePO(table);
         RowPO rowPO = tablePO.createRowsPO();
         CellPO cellPO = rowPO.createCellsPO();
         RoomPO roomPO = new RoomPO();
         cellPO.hasLink("value", roomPO);
         roomPO.startNAC();
         roomPO.createStudentsPO();
         roomPO.endNAC();
         rowPO.startSubPattern();
         CellPO otherCellPO = rowPO.createCellsPO();
         otherCellPO.destroy();
         otherCellPO.doAllMatches();
         rowPO.endSubPattern();
         rowPO.destroy();
         rowPO.doAllMatches();
         
         story.addPattern(tablePO, false);
         
         story.markCodeStart();

         table.createColumns("Topic", row -> {
            Room r = row.getCellValue("B");
            return r.getTopic();
         });
         table.createColumns("Credits", row -> ((Room) row.getCellValue("B")).getCredits())
            .withTdCssClass("text-right");
         table.createColumns("Students", row -> ((Room) row.getCellValue("B")).getStudents().size())
            .withTdCssClass("text-right");
         table.withoutColumns("A", "B");

         story.addCode();
         
         story.addTable(table);
         
         double creditsSum = table.getColumn("Credits").getValueSum();
         
         String csv = table.getCSV();
         
         try
         {
            Files.write(Paths.get("doc/StudyRight.csv"), csv.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
         }
         catch (IOException e)
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
         tablePO = new TablePO(table);
         ColumnPO columnsPO = tablePO.createColumnsPO(CREATE);
         columnsPO.createNameAssignment("Topic");
         tablePO.startSubPattern();
         rowPO = tablePO.createRowsPO();
         cellPO = rowPO.createCellsPO();
         roomPO = new RoomPO();
         cellPO.hasLink("value", roomPO);
         cellPO.startCreate();
         CellPO cellPO2 = columnsPO.createCellsPO(CREATE);
         cellPO2.createValueAssignment("r5.topic");
         cellPO.endCreate();
         tablePO.endSubPattern();
         
         
         story.addPattern(tablePO, false);
         
      }

      // =====================================================
      story.addStep("List all topics:");

      {
         story.markCodeStart();

         UniversityPO universityPO = new UniversityPO(university);

         TablePO tablePO = new TablePO(CREATE);

         universityPO.addToPattern(tablePO);

         tablePO.createNameAssignment("University");

         ColumnPO col1PO = tablePO.createColumnsPO(CREATE).createNameAssignment("Topic");

         ColumnPO col2PO = tablePO.createColumnsPO(CREATE)
            .createNameAssignment("Credits")
            .createTdCssClassAssignment("text-right");

         ColumnPO col3PO = tablePO.createColumnsPO(CREATE)
            .createNameAssignment("Students")
            .createTdCssClassAssignment("text-right");

         RoomPO roomsPO = universityPO.createRoomsPO();

         RowPO rowPO = tablePO.createRowsPO(CREATE);

         CellPO cell1PO = rowPO.createCellsPO(CREATE).createColumnLink(col1PO, CREATE);
         cell1PO.createCondition(cell -> cell.withValue(roomsPO.getTopic()) != null);

         CellPO cell2PO = rowPO.createCellsPO(CREATE).createColumnLink(col2PO, CREATE);
         cell2PO.createCondition(cell -> cell.withValue(roomsPO.getCredits()) != null);

         CellPO cell3PO = rowPO.createCellsPO(CREATE).createColumnLink(col3PO, CREATE);
         cell3PO.createCondition(cell -> cell.withValue(roomsPO.getStudents().size()) != null);
         
         universityPO.doAllMatches();

         story.addCode();

         story.add("Results in:");

         story.addTable(tablePO.getCurrentMatch());

         story.addObjectDiagramViaGraphViz(tablePO.getCurrentMatch());
      }

      story.addStep("Do a nested table");
      {
         story.markCodeStart();

         UniversityPO universityPO = new UniversityPO(university);

         RoomPO createRoomsPO = universityPO.createRoomsPO();

         Table table = universityPO.createResultTable();

         table.createColumns("Topic", row -> ((Room) row.getCellValue("B")).getTopic());
         table.createColumns("Assignments", row -> addAssignments(row));
         table.createColumns("Students", row -> ((Room) row.getCellValue("B")).getStudents().size())
            .withTdCssClass("text-right");
         table.withoutColumns("A", "B");

         story.addCode();

         story.addTable(table);
      }
      story.dumpHTML();
   }

   public Table addAssignments(Row row)
   {
      Room room = (Room) row.getCellValue("B");

      RoomPO roomPO = new RoomPO(room);

      AssignmentPO assignmentPO = roomPO.createAssignmentsPO();

      Table table = roomPO.createResultTable();

      table.createColumns("Content", r -> ((Assignment) r.getCellValue("B")).getContent());
      table.createColumns("Points", r -> ((Assignment) r.getCellValue("B")).getPoints())
         .withTdCssClass("text-right");
      table.withoutColumns("A", "B");

      return table;
   }

   /**
    * 
    * <p>Storyboard <a href='./src/test/java/org/sdmlib/test/examples/studyrightWithAssignments/StudyRightWithAssignmentsStoryboards.java' type='text/x-java'>StudyRightReachabilityGraph</a></p>
    * <p>Start: Build a start graph</p>
    * <script>
    *    var json = {
    *    "type":"objectdiagram",
    *    "nodes":[
    *       {
    *          "type":"clazz",
    *          "id":"R3 : Room",
    *          "attributes":[
    *             "credits=17",
    *             "name=senate",
    *             "topic=math"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R4 : Room",
    *          "attributes":[
    *             "credits=16",
    *             "name=7522",
    *             "topic=arts"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R5 : Room",
    *          "attributes":[
    *             "credits=25",
    *             "name=gymnasium",
    *             "topic=sports"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R6 : Room",
    *          "attributes":[
    *             "credits=0",
    *             "name=The End",
    *             "topic=exam"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R7 : Room",
    *          "attributes":[
    *             "credits=42",
    *             "name=7422",
    *             "topic=Software Engineering"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S2 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=17",
    *             "id=1337",
    *             "motivation=83",
    *             "name=Karli"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"U1 : University",
    *          "attributes":[
    *             "name=StudyRight",
    *             "president=null"
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R4 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R3 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R5 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R3 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R5 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R4 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R6 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R4 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R7 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R4 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R6 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R5 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R7 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"doors",
    *             "id":"R6 : Room"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R3 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"rooms",
    *             "id":"R3 : Room"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"rooms",
    *             "id":"R4 : Room"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"rooms",
    *             "id":"R5 : Room"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"rooms",
    *             "id":"R6 : Room"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"rooms",
    *             "id":"R7 : Room"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S2 : Student"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightReachabilityGraph2", "display":"svg", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <script>
    *    var json = {
    *    "type":"object",
    *    "nodes":[
    *       {
    *          "type":"patternObject",
    *          "id":"u1 : UniversityPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"s2 : StudentPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"r3 : RoomPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"r4 : RoomPO",
    *          "attributes":[]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"u1 : UniversityPO"
    *          },
    *          "target":{
    *             "property":"students",
    *             "id":"s2 : StudentPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"s2 : StudentPO"
    *          },
    *          "target":{
    *             "property":"in",
    *             "id":"r3 : RoomPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r3 : RoomPO"
    *          },
    *          "target":{
    *             "property":"doors",
    *             "id":"r4 : RoomPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"s2 : StudentPO"
    *          },
    *          "target":{
    *             "property":"in",
    *             "id":"r4 : RoomPO"
    *          },
    *          "style":"create"
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightReachabilityGraphPatternDiagram2", "display":"html", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <script>
    *    var json = {
    *    "type":"objectdiagram",
    *    "nodes":[
    *       {
    *          "type":"clazz",
    *          "id":"R12 : Room",
    *          "attributes":[
    *             "credits=0",
    *             "name=The End",
    *             "topic=exam"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R13 : ReachabilityGraph"
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R14 : ReachableState",
    *          "attributes":[
    *             "descr=22 0.0\u000aStudyRight",
    *             "failureState=false",
    *             "metricValue=0.0",
    *             "number=22"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R16 : ReachableState",
    *          "attributes":[
    *             "descr=9 0.0\u000aStudyRight",
    *             "failureState=false",
    *             "metricValue=0.0",
    *             "number=9"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R18 : ReachableState",
    *          "attributes":[
    *             "descr=3 0.0\u000aStudyRight",
    *             "failureState=false",
    *             "metricValue=0.0",
    *             "number=3"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R20 : RuleApplication",
    *          "attributes":[
    *             "description=next"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R21 : ReachableState",
    *          "attributes":[
    *             "descr=1 0.0\u000aStudyRight",
    *             "failureState=false",
    *             "metricValue=0.0",
    *             "number=1"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R22 : RuleApplication",
    *          "attributes":[
    *             "description=next"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R23 : RuleApplication",
    *          "attributes":[
    *             "description=next"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R27 : Room",
    *          "attributes":[
    *             "credits=42",
    *             "name=7422",
    *             "topic=Software Engineering"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R28 : Room",
    *          "attributes":[
    *             "credits=16",
    *             "name=7522",
    *             "topic=arts"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R29 : Room",
    *          "attributes":[
    *             "credits=25",
    *             "name=gymnasium",
    *             "topic=sports"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R3 : Room",
    *          "attributes":[
    *             "credits=17",
    *             "name=senate",
    *             "topic=math"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R8 : ReachableState",
    *          "attributes":[
    *             "descr=43 0.0\u000aStudyRight",
    *             "failureState=false",
    *             "metricValue=0.0",
    *             "number=43"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"R9 : RuleApplication",
    *          "attributes":[
    *             "description=next"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S11 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=100",
    *             "id=1337",
    *             "motivation=0",
    *             "name=Karli"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S2 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=17",
    *             "id=1337",
    *             "motivation=83",
    *             "name=Karli"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S24 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=100",
    *             "id=1337",
    *             "motivation=0",
    *             "name=Karli"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S25 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=58",
    *             "id=1337",
    *             "motivation=42",
    *             "name=Karli"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"S26 : Student",
    *          "attributes":[
    *             "assignmentPoints=0",
    *             "credits=42",
    *             "id=1337",
    *             "motivation=58",
    *             "name=Karli"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"U1 : University",
    *          "attributes":[
    *             "name=StudyRight",
    *             "president=null"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"U10 : University",
    *          "attributes":[
    *             "name=StudyRight",
    *             "president=null"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"U15 : University",
    *          "attributes":[
    *             "name=StudyRight",
    *             "president=null"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"U17 : University",
    *          "attributes":[
    *             "name=StudyRight",
    *             "president=null"
    *          ]
    *       },
    *       {
    *          "type":"clazz",
    *          "id":"U19 : University",
    *          "attributes":[
    *             "name=StudyRight",
    *             "president=null"
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"graphRoot",
    *             "id":"U10 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"reachablestate",
    *             "id":"R8 : ReachableState"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"graphRoot",
    *             "id":"U15 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"reachablestate",
    *             "id":"R14 : ReachableState"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"graphRoot",
    *             "id":"U17 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"reachablestate",
    *             "id":"R16 : ReachableState"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"graphRoot",
    *             "id":"U19 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"reachablestate",
    *             "id":"R18 : ReachableState"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"graphRoot",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"reachablestate",
    *             "id":"R21 : ReachableState"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R12 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S11 : Student"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R27 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S24 : Student"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R28 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S25 : Student"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R29 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S26 : Student"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"in",
    *             "id":"R3 : Room"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S2 : Student"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"parent",
    *             "id":"R13 : ReachabilityGraph"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"reachablestate",
    *             "id":"R8 : ReachableState"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"parent",
    *             "id":"R13 : ReachabilityGraph"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"reachablestate",
    *             "id":"R14 : ReachableState"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"parent",
    *             "id":"R13 : ReachabilityGraph"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"reachablestate",
    *             "id":"R16 : ReachableState"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"parent",
    *             "id":"R13 : ReachabilityGraph"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"reachablestate",
    *             "id":"R18 : ReachableState"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"parent",
    *             "id":"R13 : ReachabilityGraph"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"reachablestate",
    *             "id":"R21 : ReachableState"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"resultOf",
    *             "id":"R20 : RuleApplication"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"tgt",
    *             "id":"R18 : ReachableState"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"ruleapplications",
    *             "id":"R9 : RuleApplication"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"src",
    *             "id":"R14 : ReachableState"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"src",
    *             "id":"R21 : ReachableState"
    *          },
    *          "target":{
    *             "cardinality":"many",
    *             "property":"ruleapplications",
    *             "id":"R20 : RuleApplication"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"src",
    *             "id":"R16 : ReachableState"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"ruleapplication",
    *             "id":"R22 : RuleApplication"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"src",
    *             "id":"R18 : ReachableState"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"ruleapplication",
    *             "id":"R23 : RuleApplication"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S11 : Student"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U10 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S24 : Student"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U15 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S25 : Student"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U17 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S26 : Student"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U19 : University"
    *          }
    *       },
    *       {
    *          "type":"assoc",
    *          "source":{
    *             "cardinality":"many",
    *             "property":"students",
    *             "id":"S2 : Student"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"tgt",
    *             "id":"R8 : ReachableState"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"ruleapplication",
    *             "id":"R9 : RuleApplication"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"tgt",
    *             "id":"R14 : ReachableState"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"ruleapplication",
    *             "id":"R22 : RuleApplication"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"tgt",
    *             "id":"R16 : ReachableState"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"ruleapplication",
    *             "id":"R23 : RuleApplication"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U10 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R12 : Room"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U15 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R27 : Room"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U17 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R28 : Room"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U19 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R29 : Room"
    *          }
    *       },
    *       {
    *          "type":"edge",
    *          "source":{
    *             "cardinality":"one",
    *             "property":"university",
    *             "id":"U1 : University"
    *          },
    *          "target":{
    *             "cardinality":"one",
    *             "property":"room",
    *             "id":"R3 : Room"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightReachabilityGraph4", "display":"svg", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * <p>Total number of states 65</p>
    * <p>Success state 43</p>
    * <script>
    *    var json = {
    *    "type":"object",
    *    "nodes":[
    *       {
    *          "type":"patternObject",
    *          "id":"r1 : ReachabilityGraphPO",
    *          "attributes":[
    *             "<< bound>>"
    *          ]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"r2 : ReachableStatePO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"o3 : ObjectPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"u4 : UniversityPO",
    *          "attributes":[]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"s5 : StudentPO",
    *          "attributes":[
    *             "motivation == 0"
    *          ]
    *       },
    *       {
    *          "type":"patternObject",
    *          "id":"r6 : RoomPO",
    *          "attributes":[
    *             "topic == exam"
    *          ]
    *       }
    *    ],
    *    "edges":[
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r1 : ReachabilityGraphPO"
    *          },
    *          "target":{
    *             "property":"states",
    *             "id":"r2 : ReachableStatePO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"r2 : ReachableStatePO"
    *          },
    *          "target":{
    *             "property":"graphRoot",
    *             "id":"o3 : ObjectPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"o3 : ObjectPO"
    *          },
    *          "target":{
    *             "property":"instanceof",
    *             "id":"u4 : UniversityPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"u4 : UniversityPO"
    *          },
    *          "target":{
    *             "property":"students",
    *             "id":"s5 : StudentPO"
    *          }
    *       },
    *       {
    *          "typ":"EDGE",
    *          "source":{
    *             "property":" ",
    *             "id":"s5 : StudentPO"
    *          },
    *          "target":{
    *             "property":"in",
    *             "id":"r6 : RoomPO"
    *          }
    *       }
    *    ]
    * }   ;
    *    json["options"]={"canvasid":"canvasStudyRightReachabilityGraphPatternDiagram6", "display":"html", "fontsize":10,"bar":true};   var g = new Graph(json);
    *    g.layout(100,100);
    * </script>
    * @see <a href=
    *      '../../../../../../../../doc/StudyRightReachabilityGraph.html'>StudyRightReachabilityGraph.html</a>
    * @see <a href='../../../../../../../../doc/StudyRightReachabilityGraph.html'>StudyRightReachabilityGraph.html</a>
 */
   @Test
   public void testStudyRightReachabilityGraph()
   {
      Storyboard story = new Storyboard();

      story.addStep("Build a start graph");

      University university = new University()
         .withName("StudyRight");

      Student karli = university.createStudents()
         .withId("1337")
         .withName("Karli")
         .withMotivation(100);

      Room mathRoom = university.createRooms()
         .withName("senate")
         .withTopic("math")
         .withCredits(17)
         .withStudents(karli);

      karli.withCredits(17).withMotivation(100 - 17);

      Room artsRoom = university.createRooms()
         .withName("7522")
         .withTopic("arts")
         .withCredits(16)
         .withDoors(mathRoom);

      Room sportsRoom = university.createRooms()
         .withName("gymnasium")
         .withTopic("sports")
         .withCredits(25)
         .withDoors(mathRoom, artsRoom);

      Room examRoom = university.createRooms()
         .withName("The End")
         .withTopic("exam")
         .withCredits(0)
         .withDoors(sportsRoom, artsRoom);

      Room softwareEngineering = university.createRooms()
         .withName("7422")
         .withTopic("Software Engineering")
         .withCredits(42)
         .withDoors(artsRoom, examRoom);

      story.addObjectDiagramOnlyWith(university, karli, university.getRooms());

      // =====================================================
      IdMap idMap = UniversityCreator.createIdMap("s");
      idMap.with(ReachabilityGraphCreator.createIdMap("rg"));

      ReachableState startState = new ReachableState().withGraphRoot(university);
      ReachabilityGraph reachabilityGraph = new ReachabilityGraph().withMasterMap(idMap)
         .withStates(startState).withTodo(startState);

      UniversityPO uniPO = new UniversityPO();

      StudentPO studPO = uniPO.createStudentsPO();

      RoomPO currentRoomPO = studPO.createInPO();

      RoomPO nextRoomPO = currentRoomPO.createDoorsPO();

      studPO.createCondition(s -> studPO.getMotivation() >= nextRoomPO.getCredits());

      uniPO.getPattern().clone(reachabilityGraph);

      studPO.startCreate().createInLink(nextRoomPO).endCreate();

      studPO.createCondition(s -> {
         studPO.withMotivation(studPO.getMotivation() - nextRoomPO.getCredits());
         studPO.withCredits(studPO.getCredits() + nextRoomPO.getCredits());
         return true;
      });

      story.addPattern(uniPO, false);

      reachabilityGraph.addToRules(uniPO.getPattern().withName("next"));

      reachabilityGraph.explore();

      ReachableStateSet allStates = reachabilityGraph.getStates();
      // interestingStates.addAll(reachabilityGraph.getStates().filterNumber(60,
      // Integer.MAX_VALUE));

      ReachableStateSet interestingStates = new ReachableStateSet();

      for (ReachableState state : allStates)
      {
         University uni = (University) state.getGraphRoot();
         Student student = uni.getStudents().first();
         Room room = student.getIn();
         if (student.getMotivation() == 0 && room.getTopic().equals("exam"))
         {
            interestingStates.add(state);
            break;
         }
      }

      RuleApplicationSet ruleApplications = new RuleApplicationSet();

      ReachableState endState = interestingStates.first();

      for (int i = 1; i <= 10; i++)
      {
         RuleApplication ruleApplication = endState.getResultOf().first();

         if (ruleApplication != null)
         {
            ruleApplications.add(ruleApplication);
            endState = ruleApplication.getSrc();
            interestingStates.add(endState);
         }
         else
         {
            break;
         }
      }

      UniversitySet interestingUniversities = CGUtil.instanceOf(interestingStates.getGraphRoot(), new UniversitySet());

      StudentSet studentsSet = CGUtil.instanceOf(allStates.getGraphRoot(), new UniversitySet()).getStudents().filterMotivation(0);

      story.addObjectDiagramOnlyWith(
         interestingStates,
         ruleApplications,
         interestingUniversities,
         interestingUniversities.getStudents(),
         interestingUniversities.getStudents().getIn());

      // ok do it with search pattern
      ReachabilityGraphPO reachabilityGraphPO = new ReachabilityGraphPO(reachabilityGraph);
      ReachableStatePO statePO = reachabilityGraphPO.createStatesPO();
      UniversityPO universityPO =
            statePO.createGraphRootPO().instanceOf(new UniversityPO());
      StudentPO studentPO =
            universityPO.createStudentsPO().createMotivationCondition(0);
      RoomPO roomPO = studentPO.createInPO().createTopicCondition("exam");

      ReachableState currentMatch = statePO.getCurrentMatch();

      story.add("Total number of states " + reachabilityGraph.getStates().size());

      story.add("Success state " + currentMatch.getNumber());

      story.addPattern(reachabilityGraphPO, true);

      story.dumpHTML();
   }
}
