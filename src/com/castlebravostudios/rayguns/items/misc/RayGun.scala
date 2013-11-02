package com.castlebravostudios.rayguns.items.misc

import com.castlebravostudios.rayguns.api.BeamRegistry
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils
import net.minecraft.entity.Entity
import com.castlebravostudios.rayguns.items.accessories.RefireCapacitor
import com.castlebravostudios.rayguns.mod.Config
import net.minecraft.util.Icon
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils.buildBrokenGun
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils.getChargeDepleted
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils.getTagCompound
import com.castlebravostudios.rayguns.utils.GunComponents

object RayGun extends Item( Config.rayGun ) {

  import RaygunNbtUtils._

  private val cooldownTime = "CooldownTime"

  setMaxStackSize(1)
  setCreativeTab(CreativeTabs.tabCombat)
  setUnlocalizedName("rayguns.raygun")

  override def onItemRightClick(item : ItemStack, world : World, player : EntityPlayer ) : ItemStack = {
    val components = getComponents( item )
    val creator = components.flatMap( BeamRegistry.getFunction )
    creator match {
      case Some( f ) => { fire(item, components.get, world, player, f); item }
      case None => buildBrokenGun( item )
    }
  }

  private def fire( item : ItemStack, components : GunComponents,
      world : World, player : EntityPlayer, f : BeamRegistry.BeamCreator ): Unit = {
    if ( getCooldownTime(item) != 0 ) return
    if ( !components.battery.drainPower( player, item, components ) ) return

    f( world, player )
    setCooldownTime( item, getBaseCooldownTime( components ) )
  }

  override def onUpdate( item: ItemStack, world : World, entity: Entity, par4 : Int, par5 : Boolean) : Unit = {
    val currentTime = getCooldownTime(item)
    val newTime = if ( currentTime > 0 ) currentTime - 1 else 0
    setCooldownTime( item, newTime );

    getComponents(item).flatMap( _.acc )
      .foreach( _.onGunUpdate(world, entity, item ) )
  }

  private def getBaseCooldownTime( components : GunComponents ) = components match {
    case GunComponents(_, _, _, _, Some(RefireCapacitor)) => 5
    case _ => 10
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
}