/*
 * Copyright (c) 2014, Brook 'redattack34' Heisler
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the ModularRayguns team nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
import com.castlebravostudios.rayguns.utils.GunComponents
import com.castlebravostudios.rayguns.utils.DefaultFireEvent
import com.castlebravostudios.rayguns.utils.DefaultFireEvent
import com.castlebravostudios.rayguns.utils.GunComponents

case class PrefireEvent(
    val player : EntityPlayer,
    val world : World,
    val gun : ItemStack,
    val components : GunComponents,
    var canFire : Boolean,
    var fireEvent : FireEvent,
    var cooldownTicks : Int,
    var powerMult : Double )

case class PostfireEvent(
    val player : EntityPlayer,
    val world : World,
    val gun : ItemStack,
    val components : GunComponents,
    val fireEvent : FireEvent,
    val powerMult : Double )

case class GunTickEvent(
    val player : EntityPlayer,
    val world : World,
    val gun : ItemStack,
    val components : GunComponents,
    val isSelected : Boolean )

object RayGun extends ScalaItem( Config.rayGun ) with MoreInformation
  with ChargableItem with RFItemPowerConnector with IC2ItemPowerConnector {

  private val maxChargeTime : Double = 3.0d
  private val ticksPerSecond : Int = 20
  private val maxChargeTicks : Int = ( maxChargeTime * ticksPerSecond ).toInt

  import RaygunNbtUtils._

  private val cooldownTime = "CooldownTime"
  private val maxCooldownTime = "MaxCooldownTime"

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

    val prefire = new PrefireEvent( player, world, item, components.get,
        getCooldownTime( item ) == 0,
        components.get.getFireEvent( 1.0d ), 10,
        components.get.components.map(_.powerModifier).product )
    if ( !prefire.canFire ) return item

    val creator = BeamRegistry.getFunction( prefire.fireEvent )
    creator.foreach { fire( prefire, _) }
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

    val prefire = new PrefireEvent( player, world, item, components.get,
        getCooldownTime( item ) == 0,
        components.get.getFireEvent( 1.0d ), 10,
        components.get.components.map(_.powerModifier).product )
    components.get.components.foreach( comp => comp.handlePrefireEvent( prefire ) );
    if ( !prefire.canFire ) return item

    val creator = BeamRegistry.getFunction( prefire.fireEvent )

    creator match {
      case Some( f ) => { fire( prefire, f ); item }
      case None => buildBrokenGun( item )
    }
  }

  private def fire( prefire : PrefireEvent, f : BeamRegistry.BeamCreator ): Unit = {
    f( prefire.world, prefire.player )
    setCooldownTime( prefire.gun, prefire.cooldownTicks )
    setMaxCooldownTime( prefire.gun, prefire.cooldownTicks )

    val postFire = PostfireEvent( prefire.player, prefire.world,
        prefire.gun, prefire.components, prefire.fireEvent, prefire.powerMult )

    prefire.components.components.foreach(
        comp => comp.handlePostfireEvent( postFire ) )
  }

  override def onUpdate( item: ItemStack, world : World, entity: Entity, par4 : Int, par5 : Boolean) : Unit = {
    val currentTime = getCooldownTime(item)
    val newTime = if ( currentTime > 0 ) currentTime - 1 else 0

    if ( world.isOnServer ) {
      setCooldownTime( item, newTime );
    }

    val components = getComponents( item )
    if ( components.isEmpty ) return

    entity match {
      case player : EntityPlayer => {
        val tickEvent = new GunTickEvent( player, world, item, components.get,
            player.inventory.getCurrentItem() eq item );
        components.get.components.foreach( comp => comp.handleTickEvent( tickEvent ) )
      }
      case _ => ()
    }
  }

  private def setCooldownTime( item : ItemStack, ticks : Int ) =
    item.getTagCompoundSafe.setShort( cooldownTime, ticks.shortValue )

  private def setMaxCooldownTime( item : ItemStack, ticks : Int ) =
    item.getTagCompoundSafe.setShort( maxCooldownTime, ticks.shortValue )

  def getCooldownTime( item : ItemStack ) =
    item.getTagCompoundSafe.getShort( cooldownTime )

  def getMaxCooldownTime( item : ItemStack ) =
    item.getTagCompoundSafe.getShort( maxCooldownTime )

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

  override def getMaxItemUseDuration( item : ItemStack ) : Int = Integer.MAX_VALUE

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