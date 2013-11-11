package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import net.minecraft.entity.Entity
import java.util.Random
import cpw.mods.fml.common.Mod.Metadata
import net.minecraft.block.Block
import net.minecraft.block.BlockFluid
import net.minecraftforge.fluids.IFluidBlock

trait BaseEffect extends Entity {
  self : Shootable =>

  def colourRed : Float
  def colourBlue : Float
  def colourGreen : Float
  def colourAlpha : Float = 1.0f

  /**
   * A collision has been detected against the given entity. Return true if the
   * bolt/beam should stop after this collision, or return false to indicate
   * that the bolt/beam penetrated through the entity.
   */
  def hitEntity( entity : Entity ) : Boolean

  /**
   * A collision has been detected against the given side of the block at the
   * given coords. Return true if the bolt/beam should stop after this collision,
   * or return false to indicate that the bolt/beam penetrated through the block.
   */
  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean

  /**
   * A collision has been detected at the given coords. Create impact particles
   * or perform other effects which require the precise coords.
   */
  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit

  def canCollideWithBlock( b : Block, metadata : Int, pos : (Int, Int, Int) ) =
    if ( b.isInstanceOf[BlockFluid] || b.isInstanceOf[IFluidBlock] ) collidesWithLiquids
    else true

  def canCollideWithEntity( entity : Entity ) = !(entity == shooter)

  def collidesWithLiquids : Boolean = false

  def random : Random
}