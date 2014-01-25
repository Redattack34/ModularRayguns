package com.castlebravostudios.rayguns.items.misc

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.items.MoreInformation
import com.castlebravostudios.rayguns.items.ScalaItem
import com.castlebravostudios.rayguns.items.accessories.RefireCapacitor
import com.castlebravostudios.rayguns.items.lenses.ChargeBeamLens
import com.castlebravostudios.rayguns.items.lenses.ChargeLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.mod.ModularRayguns
import com.castlebravostudios.rayguns.plugins.ic2.IC2ItemPowerConnector
import com.castlebravostudios.rayguns.plugins.te.RFItemPowerConnector
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension
import com.castlebravostudios.rayguns.utils.FireEvent
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import com.castlebravostudios.rayguns.utils.Extensions.ItemStackExtension
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.Icon
import net.minecraft.world.World
import com.castlebravostudios.rayguns.items.ChargableItem

object RayGun extends ScalaItem( Config.rayGun ) with MoreInformation
  with ChargableItem with RFItemPowerConnector with IC2ItemPowerConnector {

  private val maxChargeTime : Double = 3.0d
  private val ticksPerSecond : Int = 20
  private val maxChargeTicks : Int = ( maxChargeTime * ticksPerSecond ).toInt

  import RaygunNbtUtils._

  private val cooldownTime = "CooldownTime"

  setMaxStackSize(1)
  setCreativeTab(ModularRayguns.raygunsTab)
  setUnlocalizedName("rayguns.Raygun")
  setTextureName("rayguns:dummy")

  override def getAdditionalInfo(item : ItemStack, player : EntityPlayer) : Iterable[String] =
    getBattery( item ).map( _.getChargeString( item ) ) ++ RaygunNbtUtils.getComponentInfo( item )

  override def onPlayerStoppedUsing(item : ItemStack, world : World, player : EntityPlayer, itemUseCount : Int ): Unit = {
    val chargePower = getChargePower(item, itemUseCount)

    val components = getComponents( item )
    val lens = components.flatMap( _.lens )
    if ( lens.isEmpty || ( lens.get != ChargeBeamLens && lens.get != ChargeLens ) ) {
      return
    }

    val event = components.get.getFireEvent( chargePower )
    val creator = BeamRegistry.getFunction( event )
    creator.foreach { fire(item, components.get, event, world, player, _) }
  }

  def getChargePower(item: ItemStack, itemUseCount: Int): Double = {
    val ticksCharged = Math.min( maxChargeTicks, getMaxItemUseDuration(item) - itemUseCount )
    val secondsCharged = ticksCharged.toFloat / ticksPerSecond
    Math.pow( secondsCharged, 2 ) / maxChargeTime
  }

  override def onItemRightClick(item : ItemStack, world : World, player : EntityPlayer ) : ItemStack = {
    val components = getComponents( item )

    if ( components.isEmpty ) {
      return buildBrokenGun( item )
    }

    val lens = components.flatMap( _.lens )
    if ( lens.exists( lens => lens == ChargeBeamLens || lens == ChargeLens ) ) {
      player.setItemInUse(item, getMaxItemUseDuration(item))
      return item
    }

    val creator = for {
      comp <- components
      event = comp.getFireEvent( 1.0d )
      function <- BeamRegistry.getFunction( event )
    } yield function

    creator match {
      case Some( f ) => { fire(item, components.get, components.get.getFireEvent( 1.0d ), world, player, f); item }
      case None => buildBrokenGun( item )
    }
  }

  private def fire( item : ItemStack, components : GunComponents, event : FireEvent,
      world : World, player : EntityPlayer, f : BeamRegistry.BeamCreator ): Unit = {
    if ( getCooldownTime(item) != 0 ) return
    if ( !components.battery.drainPower( player, item, event ) ) return

    f( world, player )
    setCooldownTime( item, getBaseCooldownTime( components ) )
  }

  override def onUpdate( item: ItemStack, world : World, entity: Entity, par4 : Int, par5 : Boolean) : Unit = {
    val currentTime = getCooldownTime(item)
    val newTime = if ( currentTime > 0 ) currentTime - 1 else 0

    if ( world.isOnServer ) {
      setCooldownTime( item, newTime );
    }

    getComponents(item).flatMap( _.accessory )
      .foreach( _.onGunUpdate(world, entity, item ) )
  }

  def getBaseCooldownTime( item : ItemStack ) : Short = getComponents(item) match {
    case Some( comp ) => getBaseCooldownTime(comp)
    case None => 0
  }

  private def getBaseCooldownTime( components : GunComponents ) : Short = {
    components.accessory match {
      case Some(RefireCapacitor) => 5
      case _ => 10
    }
  }

  private def setCooldownTime( item : ItemStack, ticks : Int ) =
    item.getTagCompoundSafe.setShort( cooldownTime, ticks.shortValue )

  def getCooldownTime( item : ItemStack ) =
    item.getTagCompoundSafe.getShort( cooldownTime )

  override def getDamage( item : ItemStack ) : Int = 1
  override def getDisplayDamage( item : ItemStack ) : Int = getChargeDepleted( item )
  override def isDamaged( item : ItemStack ) = getDisplayDamage( item ) > 0

  override def getMaxDamage( item: ItemStack ) : Int = getChargeCapacity( item )

  override def requiresMultipleRenderPasses() = true
  override def getRenderPasses(metadata : Int) = 1

  override def getIcon( item : ItemStack, pass : Int ) : Icon = {
    val bodyIcon = for {
      components <- getComponents( item )
      bodyItem <- Option( components.body.item )
      icon = bodyItem.getIconFromDamage(0)
    } yield icon
    bodyIcon.getOrElse(itemIcon)
  }

  override def getMaxItemUseDuration( item : ItemStack ) : Int = {
    val lens = getComponents( item ).flatMap( _.lens )
    lens match {
      case Some( ChargeLens ) => 72000
      case Some( ChargeBeamLens ) => 72000
      case _ => 0
    }
  }

  def getChargeCapacity( item : ItemStack ) : Int =
    getBattery( item ).map( _.maxCapacity ).getOrElse(1)
  def getChargeDepleted( item : ItemStack ) : Int =
    getBattery( item ).map( _.getChargeDepleted( item ) ).getOrElse( 0 )
  def setChargeDepleted( item : ItemStack, depleted : Int ) : Unit =
    getBattery( item ).foreach( _.setChargeDepleted( item, depleted ) )
  def addCharge( item : ItemStack, delta : Int ) : Unit =
    getBattery( item ).foreach( _.addCharge( item, delta ) )
  def getMaxChargePerTick( item : ItemStack ) : Int =
    getBattery( item ).map( _.maxChargePerTick ).getOrElse( 0 )
  def getIC2Tier( item : ItemStack ) : Int =
    getBattery( item ).map( _.ic2Tier ).getOrElse( Integer.MAX_VALUE )
}