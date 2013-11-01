package com.castlebravostudios.rayguns.items.accessories

import java.util.Random

import scala.collection.mutable.MapBuilder
import scala.collection.mutable.WeakHashMap

import com.castlebravostudios.rayguns.api.items.ItemAccessory
import com.castlebravostudios.rayguns.mod.Config
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils

import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.world.World

object SolarPanel extends Item( Config.solarPanel ) with ItemAccessory {

  val moduleKey = "SolarPanel"
  val powerModifier = 1.0
  register
  setUnlocalizedName("rayguns.SolarPanel")
  setTextureName("rayguns:solar_panel")

  private[this] val entityMap = WeakHashMap[Entity, Boolean]()
  private[this] val random = new Random()

  override def onGunUpdate(world : World, entity : Entity, stack : ItemStack ) : Unit = {
    if ( canSeeTheSun( world, entity ) && random.nextInt( 2 ) == 0 ) {
      RaygunNbtUtils.addCharge(1, stack)
    }
  }

  private def canSeeTheSun( world : World, entity : Entity )  : Boolean = {
    //translation of cpw's code for the solar helmet. All glory to cpw.
    if ( world.isRemote || world.provider.hasNoSky ) return false

    val x = Math.floor( entity.posX ).intValue()
    val y = Math.floor( entity.posY ).intValue()
    val z = Math.floor( entity.posZ ).intValue()

    if ( world.getTotalWorldTime() % 20 == 0 || !entityMap.contains( entity ) ) {
      val canRain = world.getWorldChunkManager().getBiomeGenAt(x, z).getIntRainfall() > 0
      entityMap.put( entity, canRain )
    }
    val canRain = entityMap( entity )
    val isRaining = canRain && ( world.isRaining() || world.isThundering() )

    !isRaining && world.isDaytime() && world.canBlockSeeTheSky(x, y, z)
  }

  GameRegistry.addRecipe( new ItemStack( this, 1 ),
    "S  ",
    "GGG",
    "RIR",
    'S' : Character, Block.stone,
    'I' : Character, Item.ingotIron,
    'R' : Character, Block.blockRedstone,
    'G' : Character, Block.glass )
}