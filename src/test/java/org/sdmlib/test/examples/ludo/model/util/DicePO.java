package org.sdmlib.test.examples.ludo.model.util;

import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.Pattern;
import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.test.examples.ludo.model.Dice;
import org.sdmlib.test.examples.ludo.model.Ludo;
import org.sdmlib.test.examples.ludo.model.Player;

public class DicePO extends PatternObject<DicePO, Dice>
{

    public DiceSet allMatches()
   {
      this.setDoAllMatches(true);
      
      DiceSet matches = new DiceSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((Dice) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public DicePO(){
      newInstance(CreatorCreator.createIdMap("PatternObjectType"));
   }

   public DicePO(Dice... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(CreatorCreator.createIdMap("PatternObjectType"), hostGraphObject);
   }
   public DicePO hasValue(int value)
   {
      new AttributeConstraint()
      .withAttrName(Dice.PROPERTY_VALUE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public DicePO hasValue(int lower, int upper)
   {
      new AttributeConstraint()
      .withAttrName(Dice.PROPERTY_VALUE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      this.getPattern().findMatch();
      
      return this;
   }
   
   public DicePO createValue(int value)
   {
      this.startCreate().hasValue(value).endCreate();
      return this;
   }
   
   public int getValue()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Dice) getCurrentMatch()).getValue();
      }
      return 0;
   }
   
   public DicePO withValue(int value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((Dice) getCurrentMatch()).setValue(value);
      }
      return this;
   }
   
   public LudoPO hasGame()
   {
      LudoPO result = new LudoPO(new Ludo[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Dice.PROPERTY_GAME, result);
      
      return result;
   }

   public LudoPO createGame()
   {
      return this.startCreate().hasGame().endCreate();
   }

   public DicePO hasGame(LudoPO tgt)
   {
      return hasLinkConstraint(tgt, Dice.PROPERTY_GAME);
   }

   public DicePO createGame(LudoPO tgt)
   {
      return this.startCreate().hasGame(tgt).endCreate();
   }

   public Ludo getGame()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Dice) this.getCurrentMatch()).getGame();
      }
      return null;
   }

   public PlayerPO hasPlayer()
   {
      PlayerPO result = new PlayerPO(new Player[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Dice.PROPERTY_PLAYER, result);
      
      return result;
   }

   public PlayerPO createPlayer()
   {
      return this.startCreate().hasPlayer().endCreate();
   }

   public DicePO hasPlayer(PlayerPO tgt)
   {
      return hasLinkConstraint(tgt, Dice.PROPERTY_PLAYER);
   }

   public DicePO createPlayer(PlayerPO tgt)
   {
      return this.startCreate().hasPlayer(tgt).endCreate();
   }

   public Player getPlayer()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((Dice) this.getCurrentMatch()).getPlayer();
      }
      return null;
   }

   public DicePO filterValue(int value)
   {
      new AttributeConstraint()
      .withAttrName(Dice.PROPERTY_VALUE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public DicePO filterValue(int lower, int upper)
   {
      new AttributeConstraint()
      .withAttrName(Dice.PROPERTY_VALUE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public LudoPO filterGame()
   {
      LudoPO result = new LudoPO(new Ludo[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Dice.PROPERTY_GAME, result);
      
      return result;
   }

   public DicePO filterGame(LudoPO tgt)
   {
      return hasLinkConstraint(tgt, Dice.PROPERTY_GAME);
   }

   public PlayerPO filterPlayer()
   {
      PlayerPO result = new PlayerPO(new Player[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Dice.PROPERTY_PLAYER, result);
      
      return result;
   }

   public DicePO filterPlayer(PlayerPO tgt)
   {
      return hasLinkConstraint(tgt, Dice.PROPERTY_PLAYER);
   }


   public DicePO(String modifier)
   {
      this.setModifier(modifier);
   }
   public DicePO createValueCondition(int value)
   {
      new AttributeConstraint()
      .withAttrName(Dice.PROPERTY_VALUE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public DicePO createValueCondition(int lower, int upper)
   {
      new AttributeConstraint()
      .withAttrName(Dice.PROPERTY_VALUE)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public DicePO createValueAssignment(int value)
   {
      new AttributeConstraint()
      .withAttrName(Dice.PROPERTY_VALUE)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public LudoPO createGamePO()
   {
      LudoPO result = new LudoPO(new Ludo[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Dice.PROPERTY_GAME, result);
      
      return result;
   }

   public LudoPO createGamePO(String modifier)
   {
      LudoPO result = new LudoPO(new Ludo[]{});
      
      result.setModifier(modifier);
      super.hasLink(Dice.PROPERTY_GAME, result);
      
      return result;
   }

   public DicePO createGameLink(LudoPO tgt)
   {
      return hasLinkConstraint(tgt, Dice.PROPERTY_GAME);
   }

   public DicePO createGameLink(LudoPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Dice.PROPERTY_GAME, modifier);
   }

   public PlayerPO createPlayerPO()
   {
      PlayerPO result = new PlayerPO(new Player[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(Dice.PROPERTY_PLAYER, result);
      
      return result;
   }

   public PlayerPO createPlayerPO(String modifier)
   {
      PlayerPO result = new PlayerPO(new Player[]{});
      
      result.setModifier(modifier);
      super.hasLink(Dice.PROPERTY_PLAYER, result);
      
      return result;
   }

   public DicePO createPlayerLink(PlayerPO tgt)
   {
      return hasLinkConstraint(tgt, Dice.PROPERTY_PLAYER);
   }

   public DicePO createPlayerLink(PlayerPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, Dice.PROPERTY_PLAYER, modifier);
   }

}
