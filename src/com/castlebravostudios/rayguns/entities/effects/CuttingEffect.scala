package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.entities.NoDuplicateCollisions
import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.utils.Extensions._
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemPickaxe
import net.minecraft.item.ItemSpade
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import com.google.common.io.ByteArrayDataOutput
import com.google.common.io.ByteArrayDataInput

trait CuttingEffect extends Entity with BaseEffect with IEntityAdditionalSpawnData {
  self : Shootable =>

  var harvestLevel : Int = 1
  var remainingPower : Float = 0

  def hitEntity( entity : Entity ) : Boolean = true

  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    val blockId = worldObj.getBlockId(hitX, hitY, hitZ)
    val block = Block.blocksList(blockId)
    val meta = worldObj.getBlockMetadata(hitX, hitY, hitZ)

    val particleStr = s"tilecrack_${blockId}_${meta}"
    for ( k <- 0 until 4 ) {
      this.worldObj.spawnParticle(particleStr, hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D);
    }

    if ( !canBreakBlock( hitX, hitY, hitZ, block ) ) { return true }
    else {
      remainingPower -= block.getBlockHardness(worldObj, hitX, hitY, hitZ)
      val player = shooter match {
        case pl : EntityPlayer => pl
        case _ => null
      }
      if ( block.removeBlockByPlayer(worldObj, player, hitX, hitY, hitZ) ) {
        block.onBlockDestroyedByPlayer(worldObj, hitX, hitY, hitZ, meta)
      }
      block.harvestBlock(worldObj, player, hitX, hitY, hitZ, meta)
      block.onBlockHarvested(worldObj, hitX, hitY, hitZ, meta, player)
      false
    }
  }

  def canBreakBlock( x : Int, y : Int, z : Int, block : Block ) : Boolean = {
    val pick = harvestLevel match {
      case 0 => Item.pickaxeWood
      case 1 => Item.pickaxeStone
      case 2 => Item.pickaxeIron
      case 3 => Item.pickaxeDiamond
    }
    val pickCanHarvest = pick.canHarvestBlock(block) ||
      ItemPickaxe.blocksEffectiveAgainst.contains(block)

    val shovelCanHarvest = ItemSpade.blocksEffectiveAgainst.contains( block )

    val hardness = block.getBlockHardness(worldObj, x, y, z)
    if ( hardness == -1.0f ) {
      return false
    }

    hardness <= remainingPower && ( pickCanHarvest || shovelCanHarvest )
  }

  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit = ()

  override def readEffectFromNbt( tag : NBTTagCompound ) : Unit = {
    harvestLevel = tag.getInteger("harvestLevel")
    remainingPower = tag.getFloat( "remainingPower" )
  }

  override def writeEffectToNbt( tag : NBTTagCompound ) : Unit = {
    tag.setInteger( "harvestLevel", harvestLevel )
    tag.setFloat( "remainingPower", remainingPower )
  }

  def writeSpawnData( out : ByteArrayDataOutput ) : Unit = {
    out.writeInt( harvestLevel )
  }

  def readSpawnData( in : ByteArrayDataInput ) : Unit = {
    harvestLevel = in.readInt()
  }
}

class CuttingBoltEntity(world : World) extends BaseBoltEntity(world) with CuttingEffect with NoDuplicateCollisions {
  override def texture = new ResourceLocation( "rayguns", s"textures/bolts/cutting_bolt_t${harvestLevel}.png" )
}
class CuttingBeamEntity(world : World) extends BaseBeamEntity(world) with CuttingEffect {
  override def texture = new ResourceLocation( "rayguns", s"textures/beams/cutting_beam_t${harvestLevel}.png" )
}