package com.castlebravostudios.rayguns.items.misc

import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.items.MoreInformation
import com.castlebravostudios.rayguns.items.ScalaItem
import com.castlebravostudios.rayguns.items.accessories.RefireCapacitor
import com.castlebravostudios.rayguns.items.lenses.ChargeBeamLens
import com.castlebravostudios.rayguns.items.lenses.ChargeLens
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.mod.ModularRayguns
import com.castlebravostudios.rayguns.utils.FireEvent
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Icon
import net.minecraft.world.World

object RayGun extends ScalaItem( Config.rayGun ) with MoreInformation {

  private val maxChargeTime : Double = 3.0d
  private val ticksPerSecond : Int = 20
  private val maxChargeTicks : Int = ( maxChargeTime * ticksPerSecond ).toInt

  import RaygunNbtUtils._

  private val cooldownTime = "CooldownTime"

  setMaxStackSize(1)
  setCreativeTab(ModularRayguns.raygunsTab)
  setUnlocalizedName("rayguns.Raygun")

  override def getAdditionalInfo(item : ItemStack, player : EntityPlayer) : Iterable[String] = {
    val components = RaygunNbtUtils.getComponentInfo( item )
    val maxCharge = RaygunNbtUtils.getMaxDamage( item )
    val depleted = RaygunNbtUtils.getChargeDepleted( item )
    ( (maxCharge - depleted) + "/" + maxCharge ) :: components
  }

  override def onPlayerStoppedUsing(item : ItemStack, world : World, player : EntityPlayer, itemUseCount : Int ) : Unit = {
    val ticksCharged = Math.min( maxChargeTicks, getMaxItemUseDuration(item) - itemUseCount )
    val secondsCharged = ticksCharged.toFloat / ticksPerSecond
    val chargePower = Math.pow( secondsCharged, 2 ) / maxChargeTime

    val components = getComponents( item )
    val lens = components.flatMap( _.lens )
    if ( lens.isEmpty || ( lens.get != ChargeBeamLens && lens.get != ChargeLens ) ) {
      return
    }

    val event = components.get.getFireEvent( chargePower )
    val creator = BeamRegistry.getFunction( event )
    creator.foreach { fire(item, components.get, event, world, player, _) }
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
    setCooldownTime( item, newTime );

    getComponents(item).flatMap( _.accessory )
      .foreach( _.onGunUpdate(world, entity, item ) )
  }

  private def getBaseCooldownTime( components : GunComponents ) = {
    components.accessory match {
      case Some(RefireCapacitor) => 5
      case _ => 10
    }
  }

  private def setCooldownTime( item : ItemStack, ticks : Int ) =
    getTagCompound(item).setShort( cooldownTime, ticks.shortValue )

  private def getCooldownTime( item : ItemStack ) =
    getTagCompound(item).getShort( cooldownTime )

  override def getDamage( item : ItemStack ) : Int = 1
  override def getDisplayDamage( item : ItemStack ) : Int = getChargeDepleted(item)
  override def isDamaged( item : ItemStack ) = getDisplayDamage( item ) > 0

  override def getMaxDamage( item: ItemStack ) : Int = RaygunNbtUtils.getMaxDamage( item )

  override def requiresMultipleRenderPasses() = true
  override def getRenderPasses(metadata : Int) = 1

  override def getIcon( item : ItemStack, pass : Int ) : Icon = {
    getComponents( item ).map( _.body.getIconFromDamage(0) ).getOrElse(itemIcon)
  }

  override def getMaxItemUseDuration( item : ItemStack ) : Int = {
    val lens = getComponents( item ).flatMap( _.lens )
    lens match {
      case Some( ChargeLens ) => 72000
      case Some( ChargeBeamLens ) => 72000
      case _ => 0
    }
  }
}