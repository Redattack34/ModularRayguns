package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import net.minecraft.entity.Entity
import java.util.Random
import cpw.mods.fml.common.Mod.Metadata
import net.minecraft.block.Block
import net.minecraft.block.BlockFluid
import net.minecraftforge.fluids.IFluidBlock

trait BaseEffect extends Entity {
  def colourRed : Float
  def colourBlue : Float
  def colourGreen : Float
  def colourAlpha : Float = 1.0f

  def hitEntity( entity : Entity ) : Unit
  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Unit
  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit

  def canCollideWithBlock( b : Block, metadata : Int ) =
    if ( b.isInstanceOf[BlockFluid] || b.isInstanceOf[IFluidBlock] ) collidesWithLiquids
    else true

  def canCollideWithEntity( entity : Entity ) = true

  def collidesWithLiquids : Boolean = false

  def random : Random
}