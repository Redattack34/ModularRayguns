package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.utils.Extensions._

import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemPickaxe
import net.minecraft.item.ItemSpade
import net.minecraft.util.ResourceLocation

class CuttingEffect( val key : String, val harvestLevel : Int, val powerMultiplier : Float ) extends BaseEffect {

  implicit class ShootableExtension(val shootable : Shootable) {
    def harvestPower( ) : Float = shootable.charge.toFloat * powerMultiplier
    def setHarvestPower( power : Float ) = {
      shootable.charge = power / powerMultiplier
    }
  }


  def hitEntity( shootable : Shootable, entity : Entity ) : Boolean = true

  def hitBlock( shootable : Shootable,  hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = {
    val worldObj = shootable.worldObj
    val blockId = worldObj.getBlockId(hitX, hitY, hitZ)
    val block = Block.blocksList(blockId)
    val meta = worldObj.getBlockMetadata(hitX, hitY, hitZ)

    val particleStr = s"tilecrack_${blockId}_${meta}"
    for ( k <- 0 until 10 ) {
      worldObj.spawnParticle(particleStr, hitX, hitY, hitZ, 0.0D, 0.0D, 0.0D);
    }

    if ( !canBreakBlock( shootable, hitX, hitY, hitZ, block ) ) { return true }
    else if ( shootable.worldObj.isOnServer ) {
      shootable.setHarvestPower( shootable.harvestPower - block.getBlockHardness(worldObj, hitX, hitY, hitZ) )
      val player = shootable.shooter match {
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
    else true
  }

  def canBreakBlock( shootable : Shootable, x : Int, y : Int, z : Int, block : Block ) : Boolean = {
    val pick = harvestLevel match {
      case 0 => Item.pickaxeWood
      case 1 => Item.pickaxeStone
      case 2 => Item.pickaxeIron
      case 3 => Item.pickaxeDiamond
    }
    val pickCanHarvest = pick.canHarvestBlock(block) ||
      ItemPickaxe.blocksEffectiveAgainst.contains(block)

    val shovelCanHarvest = ItemSpade.blocksEffectiveAgainst.contains( block )

    val hardness = block.getBlockHardness(shootable.worldObj, x, y, z)
    if ( hardness == -1.0f ) {
      return false
    }

    hardness <= shootable.harvestPower && ( pickCanHarvest || shovelCanHarvest )
  }

  def effectKey : String = key

  def boltTexture = new ResourceLocation( "rayguns", s"textures/bolts/cutting_bolt_t${harvestLevel}.png" )
  def beamTexture = new ResourceLocation( "rayguns", s"textures/beams/cutting_beam_t${harvestLevel}.png" )
}
