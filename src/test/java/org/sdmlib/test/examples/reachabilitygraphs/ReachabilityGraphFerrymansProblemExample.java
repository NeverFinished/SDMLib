package org.sdmlib.test.examples.reachabilitygraphs;

import java.util.Collection;

import org.junit.Test;
import org.sdmlib.models.pattern.LazyCloneOp;
import org.sdmlib.models.pattern.Pattern;
import org.sdmlib.models.pattern.ReachabilityGraph;
import org.sdmlib.models.pattern.ReachableState;
import org.sdmlib.models.pattern.ReachabilityGraph.Searchmode;
import org.sdmlib.models.pattern.util.ReachabilityGraphCreator;
import org.sdmlib.storyboards.Kanban;
import org.sdmlib.storyboards.Storyboard;
import org.sdmlib.test.examples.reachabilitygraphs.ferrymansproblem.Bank;
import org.sdmlib.test.examples.reachabilitygraphs.ferrymansproblem.Boat;
import org.sdmlib.test.examples.reachabilitygraphs.ferrymansproblem.Cargo;
import org.sdmlib.test.examples.reachabilitygraphs.ferrymansproblem.River;
import org.sdmlib.test.examples.reachabilitygraphs.ferrymansproblem.util.BankPO;
import org.sdmlib.test.examples.reachabilitygraphs.ferrymansproblem.util.BankSet;
import org.sdmlib.test.examples.reachabilitygraphs.ferrymansproblem.util.BoatPO;
import org.sdmlib.test.examples.reachabilitygraphs.ferrymansproblem.util.CargoPO;
import org.sdmlib.test.examples.reachabilitygraphs.ferrymansproblem.util.RiverCreator;
import org.sdmlib.test.examples.reachabilitygraphs.ferrymansproblem.util.RiverPO;
import org.sdmlib.test.examples.reachabilitygraphs.ferrymansproblem.util.RiverSet;
import org.sdmlib.test.examples.reachabilitygraphs.unidirferrymansproblem.UBank;
import org.sdmlib.test.examples.reachabilitygraphs.unidirferrymansproblem.UBoat;
import org.sdmlib.test.examples.reachabilitygraphs.unidirferrymansproblem.UCargo;
import org.sdmlib.test.examples.reachabilitygraphs.unidirferrymansproblem.URiver;
import org.sdmlib.test.examples.reachabilitygraphs.unidirferrymansproblem.util.UBankPO;
import org.sdmlib.test.examples.reachabilitygraphs.unidirferrymansproblem.util.UBankSet;
import org.sdmlib.test.examples.reachabilitygraphs.unidirferrymansproblem.util.UBoatPO;
import org.sdmlib.test.examples.reachabilitygraphs.unidirferrymansproblem.util.UCargoPO;
import org.sdmlib.test.examples.reachabilitygraphs.unidirferrymansproblem.util.URiverCreator;
import org.sdmlib.test.examples.reachabilitygraphs.unidirferrymansproblem.util.URiverPO;
import org.sdmlib.test.examples.reachabilitygraphs.unidirferrymansproblem.util.URiverSet;

import de.uniks.networkparser.IdMap;
import de.uniks.networkparser.list.ObjectSet;
import de.uniks.networkparser.list.SimpleKeyValueList;
import de.uniks.networkparser.list.SimpleSet;

public class ReachabilityGraphFerrymansProblemExample
{
   /**
    * 
    * @see <a href='../../../../../../../../doc/FerrymansProblemExample.html'>FerrymansProblemExample.html</a>
    */
   @Test // 
   public void FerrymansProblemExample()
   {
      Storyboard storyboard = new Storyboard();

      // ================================================
      storyboard.add("initial situation:");

      River river = new River();

      Boat boat = river.createBoat();

      Bank left = river.createBanks().withName("left").withBoat(boat);

      left.createCargos().withName("cabbage");
      left.createCargos().withName("goat");
      left.createCargos().withName("wolf");

      river.createBanks().withName("right");

      storyboard.addObjectDiagram(river);


      ReachableState rs1 = new ReachableState().withGraphRoot(river);

      RiverCreator cc = new RiverCreator();

      IdMap map = cc.createIdMap("s");

      ReachabilityGraph reachabilityGraph = new ReachabilityGraph()
            .withMasterMap(map).withStates(rs1).withTodo(rs1);

      Object s1cert = rs1.lazyComputeCertificate();

      storyboard.addPreformatted(s1cert.toString());


      // ================================================
      // map.with(new ModelPatternCreator());
      // FlipBook flipBook = new FlipBook().withMap(map);
      // String id = map.getId(reachabilityGraph);
      //
      // ================================================
      // load boat rule

      RiverPO riverPO = new RiverPO();

      Pattern loadPattern = (Pattern) riverPO.getPattern().withName("load").withDebugMode(Kanban.DEBUG_ON);
      // map.getId(loadPattern);

      BoatPO boatPO = riverPO.hasBoat();

      boatPO.startNAC().hasCargo().endNAC();

      BankPO bankPO = boatPO.hasBank();

      CargoPO cargoPO = bankPO.hasCargos();

      CargoPO goatPO = bankPO.startNAC().hasCargos().hasName("goat").hasMatchOtherThen(cargoPO);
      bankPO.hasCargos().hasMatchOtherThen(cargoPO).hasMatchOtherThen(goatPO);
      bankPO.endNAC();

      loadPattern.createCloneOp();

      bankPO.startDestroy().hasCargos(cargoPO).endDestroy();

      boatPO.createCargo(cargoPO);

      storyboard.addPattern(loadPattern, false);

      reachabilityGraph.addToRules(loadPattern);

      // ================================================
      // move boat rule

      riverPO = new RiverPO();

      Pattern movePattern = (Pattern) riverPO.getPattern().withName("move").withDebugMode(Kanban.DEBUG_ON);

      boatPO = riverPO.hasBoat();

      BankPO oldBankPO = boatPO.hasBank();

      BankPO newBankPO = riverPO.hasBanks().hasMatchOtherThen(oldBankPO);

      // do not leave the goat alone with some other cargo
      goatPO = oldBankPO.startNAC().hasCargos().hasName("goat");
      oldBankPO.hasCargos().hasMatchOtherThen(goatPO).endNAC();

      movePattern.createCloneOp();

      boatPO.startDestroy().hasBank(oldBankPO).endDestroy();

      boatPO.startCreate().hasBank(newBankPO).endCreate();

      cargoPO = boatPO.startSubPattern().hasCargo();

      cargoPO.createBoatLink(boatPO, Pattern.DESTROY);

      cargoPO.startCreate().hasBank(newBankPO).endCreate().endSubPattern();

      storyboard.addPattern(movePattern, false);

      reachabilityGraph.addToRules(movePattern);

      // ================================================
      long size = reachabilityGraph.explore();

      for (ReachableState s : reachabilityGraph.getStates())
      {
         storyboard.add("Reachable State " + s.getNumber());
         River r = (River) s.getGraphRoot();
         storyboard.addObjectDiagram(r, r.getBoat(), r.getBanks(), r.getBanks().getCargos());
      }


      storyboard.assertEquals("Number of Reachable States expected: ", 27L, size);

      storyboard.add("Small reachbility graph with hyperlinks to states: ");
      storyboard.add(reachabilityGraph.dumpDiagram("ferrymansproblemRG"));

      storyboard.add("large reachbility graph with embedded states: ");
      storyboard.addObjectDiagram(reachabilityGraph);

      RiverSet rivers = new RiverSet().with(reachabilityGraph.getStates().getGraphRoot());
      BankSet banks = rivers.getBanks().hasName("right");

      for (Bank bank : banks)
      {
         if (bank.getCargos().size() == 3)
         {
            storyboard.add("Found a solution.");
            break;
         }
      }

      storyboard.dumpHTML();
   }

   /**
    * 
    * @see <a href='../../../../../../../../doc/FerrymansProblemManuel.html'>FerrymansProblemManuel.html</a>
    */
   @Test
   public void FerrymansProblemManuel()
   {
      Storyboard storyboard = new Storyboard();

      // ================================================
      storyboard.add("initial situation:");

      River river = new River();

      Boat boat = river.createBoat();

      Bank left = river.createBanks().withName("left").withBoat(boat);

      left.createCargos().withName("cabbage");
      left.createCargos().withName("goat");
      left.createCargos().withName("wolf");

      river.createBanks().withName("right");

      storyboard.addObjectDiagram(river);


      ReachableState rs1 = new ReachableState().withGraphRoot(river);

      RiverCreator cc = new RiverCreator();

      IdMap map = cc.createIdMap("s");
      map.with(ReachabilityGraphCreator.createIdMap("rg"));

      LazyCloneOp lazyCloneOp = new LazyCloneOp().setMap(map);
      ReachabilityGraph reachabilityGraph = new ReachabilityGraph()
            .withMasterMap(map)
            .withStart(rs1)
            .withLazyCloning();

      // ================================================
      // load boat operations

      reachabilityGraph.withTrafo("load wolf", g -> load(g, "wolf"));
      reachabilityGraph.withTrafo("load goat", g -> load(g, "goat"));
      reachabilityGraph.withTrafo("load cabbage", g -> load(g, "cabbage"));

      // ================================================
      // move boat rule
      reachabilityGraph.withTrafo("move boat", g -> move(g));


      // ================================================
      long size = reachabilityGraph.explore();

      for (ReachableState s : reachabilityGraph.getStates())
      {
         storyboard.add("Reachable State " + s.getNumber());
         River r = (River) s.getGraphRoot();
         storyboard.addObjectDiagram(r);
      }


      storyboard.assertEquals("Number of Reachable States expected: ", 27L, size);

      storyboard.add("large reachbility graph with embedded states: ");
      storyboard.addObjectDiagram(reachabilityGraph);

      RiverSet rivers = new RiverSet().with(reachabilityGraph.getStates().getGraphRoot());
      SimpleSet<Bank> banks = rivers.getBanks()
            .hasName("right")
            .filter(bank -> bank.getCargos().size() == 3);

      storyboard.assertTrue("found a solution ", ! banks.isEmpty());

      storyboard.dumpHTML();
   }


   private void load(Object g, String cargoName)
   {
      River river = (River) g;

      Boat boat = river.getBoat();

      if (boat.getCargo() == null)
      {
         Bank bank = boat.getBank();

         if (bank.getCargos().size() == 3 && ! cargoName.equals("goat"))
         {
            // reject
            return;
         }

         for (Cargo cargo : bank.getCargos())
         {
            if (cargo.getName().equals(cargoName))
            {
               bank.withoutCargos(cargo);
               boat.withCargo(cargo);
               return;
            }
         }
      }
   }


   private void move(Object g)
   {
      River river = (River) g;

      Boat boat = river.getBoat();

      Bank oldBank = boat.getBank();

      if (oldBank.getCargos().size() >= 2)
      {
         // would i leave alone the goat with some other cargo?
         for (Cargo cargo : oldBank.getCargos())
         {
            if (cargo.getName().equals("goat"))
            {
               // reject
               return;
            }
         }
      }

      for (Bank newBank : river.getBanks())
      {
         if (newBank != oldBank)
         {
            boat.setBank(newBank);

            Cargo cargo = boat.getCargo();
            if (cargo != null)
            {
               boat.setCargo(null);
               newBank.withCargos(cargo);
            }
            return;
         }
      }
   }


   /**
    * 
    * @see <a href='../../../../../../../../doc/UniDirFerrymansProblemRules.html'>UniDirFerrymansProblemRules.html</a>
    */
   @Test
   public void UniDirFerrymansProblemRules()
   {
      Storyboard storyboard = new Storyboard();

      // ================================================
      storyboard.add("initial situation:");

      URiver river = new URiver();

      UBoat boat = river.createBoat();

      UBank left = river.createBanks().withName("left");

      boat.withBank(left);

      left.createCargos().withName("cabbage");
      UCargo goat = left.createCargos().withName("goat");
      UCargo wolf = left.createCargos().withName("wolf");

      UBank right = river.createBanks().withName("right");

      storyboard.addObjectDiagram(river);

      IdMap map = URiverCreator.createIdMap("rg");
      map.with(ReachabilityGraphCreator.createIdMap("rg"));

      ReachabilityGraph reachabilityGraph = new ReachabilityGraph()
            .withMasterMap(map);
      reachabilityGraph.withLazyCloning();

      ReachableState rs1 = reachabilityGraph.createStates().withGraphRoot(river);
      reachabilityGraph.withTodo(rs1);


      // load boat rule
      URiverPO riverPO = new URiverPO();

      Pattern loadPattern = (Pattern) riverPO.getPattern().withName("load").withDebugMode(Kanban.DEBUG_ON);
      map.getId(loadPattern);

      UBoatPO boatPO = riverPO.createBoatPO();

      boatPO.startNAC().createCargoPO().endNAC();

      UBankPO bankPO = boatPO.createBankPO();

      UCargoPO cargoPO = bankPO.createCargosPO();

      UCargoPO goatPO = bankPO.startNAC()
            .createCargosPO().createNameCondition("goat").hasMatchOtherThen(cargoPO);
      bankPO.createCargosPO().hasMatchOtherThen(cargoPO).hasMatchOtherThen(goatPO);
      bankPO.endNAC();

      bankPO.createCargosLink(cargoPO, Pattern.DESTROY);

      boatPO.createCargoLink(cargoPO, Pattern.CREATE);

      reachabilityGraph.addToRules(loadPattern);

      storyboard.addPattern(loadPattern, false);


      // ================================================
      // move boat rule

      riverPO = new URiverPO();

      Pattern movePattern = (Pattern) riverPO.getPattern().withName("move").withDebugMode(Kanban.DEBUG_ON);

      boatPO = riverPO.createBoatPO();

      UBankPO oldBankPO = boatPO.createBankPO();

      UBankPO newBankPO = riverPO.createBanksPO().hasMatchOtherThen(oldBankPO);

      // do not leave the goat alone with some other cargo
      goatPO = oldBankPO.startNAC().createCargosPO().createNameCondition("goat");
      oldBankPO.createCargosPO().hasMatchOtherThen(goatPO).endNAC();

      boatPO.createBankLink(oldBankPO, Pattern.DESTROY);

      boatPO.createBankLink(newBankPO, Pattern.CREATE);

      cargoPO = boatPO.startSubPattern().createCargoPO();

      boatPO.createCargoLink(cargoPO, Pattern.DESTROY);

      newBankPO.createCargosLink(cargoPO, Pattern.CREATE)
      .endSubPattern();

      storyboard.addPattern(movePattern, false);

      reachabilityGraph.addToRules(movePattern);


      // ======================================================================================
      reachabilityGraph.explore();

      long size = reachabilityGraph.getStates().size();



      for (ReachableState s : reachabilityGraph.getStates())
      {
         storyboard.add("Reachable State " + s.getNumber());
         URiver r = (URiver) s.getGraphRoot();
         SimpleKeyValueList<Object, Object> graph = new SimpleKeyValueList<Object, Object>();
         reachabilityGraph.getLazyCloneOp().aggregate(graph, r, r);
         // storyboard.addObjectDiagram(graph);
         storyboard.addObjectDiagramOnlyWith(graph.keySet());
      }


      storyboard.assertEquals("Number of Reachable States expected: ", 26L, size);

      storyboard.add("Small reachbility graph with hyperlinks to states: ");
      storyboard.addObjectDiagramOnlyWith(reachabilityGraph.getStates(), reachabilityGraph.getStates().getRuleapplications());

      // storyboard.add(reachabilityGraph.dumpDiagram("ferrymansproblemRG"));

      storyboard.add("large reachbility graph with embedded states: ");
      storyboard.addObjectDiagram(reachabilityGraph);

      URiverSet rivers = new URiverSet().with(reachabilityGraph.getStates().getGraphRoot());
      UBankSet banks = rivers.getBanks().createNameCondition("right");

      for (UBank bank : banks)
      {
         if (bank.getCargos().size() == 3)
         {
            storyboard.add("Found a solution.");
            break;
         }
      }

      storyboard.dumpHTML();
   }


   //   /**
   //    * 
   //    * @see <a href='../../../../../../../../doc/LayzFerrymansProblemIsomorphim.html'>LayzFerrymansProblemIsomorphim.html</a>
   //    */
   //
   //   @Test
   //   public void LayzFerrymansProblemIsomorphim()
   //   {
   //      Storyboard storyboard = new Storyboard();
   //
   //      // ================================================
   //      storyboard.add("initial situation:");
   //
   //      LRiver river = new LRiver();
   //
   //      LBoat boat = river.createBoat();
   //
   //      LBank left = river.createBanks().withName("left").withBoat(boat);
   //
   //      left.createCargos().withName("cabbage");
   //      LCargo goat = left.createCargos().withName("goat");
   //      LCargo wolf = left.createCargos().withName("wolf");
   //
   //      LBank right = river.createBanks().withName("right");
   //
   //      storyboard.addObjectDiagram(river);
   //
   //      ObjectSet graphElems = new ObjectSet();
   //
   //      // try aggregation 
   //      LazyCloneOp lazyCloneOp = new LazyCloneOp().setMap(LRiverCreator.createIdMap("lazy"));
   //
   //      LRiver river2 = (LRiver) lazyCloneOp.clone(river);
   //
   //      storyboard.add("compute certificates");
   //
   //      ReachableState rs1 = new ReachableState().withGraphRoot(river);
   //
   //      LRiverCreator cc = new LRiverCreator();
   //
   //      IdMap map = LRiverCreator.createIdMap("s");
   //      map.with(ReachabilityGraphCreator.createIdMap("rg"));
   //
   //      ReachabilityGraph reachabilityGraph = new ReachabilityGraph()
   //            .withMasterMap(map).withStates(rs1).withTodo(rs1).setLazyCloneOp(lazyCloneOp);
   //
   //      // add dummy rule computing a lazy clone. 
   //      // explore should recognize it as equal and terminate immediately
   //
   //      LRiverPO riverPO = new LRiverPO();
   //
   //      riverPO.createCondition(r -> lazyClone(riverPO, r));
   //
   //      reachabilityGraph.withRules(riverPO);
   //
   //      long size = reachabilityGraph.explore();

   //      // ================================================
   //      // map.with(new ModelPatternCreator());
   //      // FlipBook flipBook = new FlipBook().withMap(map);
   //      // String id = map.getId(reachabilityGraph);
   //      //
   //      // ================================================
   //      // load boat rule
   //
   //      LRiverPO riverPO = new LRiverPO();
   //
   //      Pattern loadPattern = (Pattern) riverPO.getPattern().withName("load").withDebugMode(Kanban.DEBUG_ON);
   //      map.getId(loadPattern);
   //
   //      LBoatPO boatPO = riverPO.createBoatPO();
   //
   //      boatPO.startNAC().createCargoPO().endNAC();
   //
   //      LBankPO bankPO = boatPO.createBankPO();
   //
   //      LCargoPO cargoPO = bankPO.createCargosPO();
   //
   //      LCargoPO goatPO = bankPO.startNAC()
   //            .createCargosPO().createNameCondition("goat").hasMatchOtherThen(cargoPO);
   //      bankPO.createCargosPO().hasMatchOtherThen(cargoPO).hasMatchOtherThen(goatPO);
   //      bankPO.endNAC();
   //
   //      loadPattern.createCloneOp();
   //
   //      bankPO.createCargosLink(cargoPO, Pattern.DESTROY);
   //
   //      boatPO.createCargoLink(cargoPO, Pattern.CREATE);
   //
   //      storyboard.addPattern(loadPattern, false);
   //
   //      reachabilityGraph.addToRules(loadPattern);
   //
   //      // ================================================
   //      // move boat rule
   //
   //      riverPO = new LRiverPO();
   //
   //      Pattern movePattern = (Pattern) riverPO.getPattern().withName("move").withDebugMode(Kanban.DEBUG_ON);
   //
   //      boatPO = riverPO.createBoatPO();
   //
   //      LBankPO oldBankPO = boatPO.createBankPO();
   //
   //      LBankPO newBankPO = riverPO.createBanksPO().hasMatchOtherThen(oldBankPO);
   //
   //      // do not leave the goat allone with some other cargo
   //      goatPO = oldBankPO.startNAC().createCargosPO().createNameCondition("goat");
   //      oldBankPO.createCargosPO().hasMatchOtherThen(goatPO).endNAC();
   //
   //      movePattern.createCloneOp();
   //
   //      boatPO.createBankLink(oldBankPO, Pattern.DESTROY);
   //
   //      boatPO.createBankLink(newBankPO, Pattern.CREATE);
   //
   //      cargoPO = boatPO.startSubPattern().createCargoPO();
   //
   //      cargoPO.createBoatLink(boatPO, Pattern.DESTROY);
   //
   //      cargoPO.createBankLink(newBankPO, Pattern.CREATE).endSubPattern();
   //
   //      storyboard.addPattern(movePattern, false);
   //
   //      reachabilityGraph.addToRules(movePattern);
   //
   //      // ================================================
   //      long size = reachabilityGraph.explore();
   //      
   //      for (ReachableState s : reachabilityGraph.getStates())
   //      {
   //         storyboard.add("Reachable State " + s.getNumber());
   //         LRiver r = (LRiver) s.getGraphRoot();
   //         storyboard.addObjectDiagram(r, r.getBoat(), r.getBanks(), r.getBanks().getCargos());
   //      }
   //      
   //
   //      storyboard.assertEquals("Number of Reachable States expected: ", 27L, size);
   //
   //      storyboard.add("Small reachbility graph with hyperlinks to states: ");
   //      storyboard.add(reachabilityGraph.dumpDiagram("ferrymansproblemRG"));
   //
   //      storyboard.add("large reachbility graph with embedded states: ");
   //      storyboard.addObjectDiagram(map, reachabilityGraph, true);
   //
   //      LRiverSet rivers = new LRiverSet().with(reachabilityGraph.getStates().getGraphRoot());
   //      LBankSet banks = rivers.getBanks().createNameCondition("right");
   //
   //      for (LBank bank : banks)
   //      {
   //         if (bank.getCargos().size() == 3)
   //         {
   //            storyboard.add("Found a solution.");
   //            break;
   //         }
   //      }
   //
   //storyboard.dumpHTML();
   // }


}
