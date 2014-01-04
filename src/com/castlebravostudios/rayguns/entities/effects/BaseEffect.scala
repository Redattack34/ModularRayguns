package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import com.castlebravostudios.rayguns.entities.BoltRenderer
import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.utils.BlockPos

import net.minecraft.block.Block
import net.minecraft.block.BlockFluid
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.fluids.IFluidBlock

trait BaseEffect {

  /**
   * Get the module key for this module. This key will be stored in the data
   * of the bolts and beams fired so that the effect can be looked up again
   * client-side.
   *
   * IMPORTANT NOTE: This key must not be changed once your plugin is released!
   */
  def effectKey : String

  /**
   * A collision has been detected against the given entity. Return true if the
   * bolt/beam should stop after this collision, or return false to indicate
   * that the bolt/beam penetrated through the entity.
   */
  def hitEntity( shootable : Shootable, entity : Entity ) : Boolean

  /**
   * A collision has been detected against the given side of the block at the
   * given coords. Return true if the bolt/beam should stop after this collision,
   * or return false to indicate that the bolt/beam penetrated through the block.
   */
  def hitBlock( shootable : Shootable, hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean

  /**
   * A collision has been detected at the given coords. Create impact particles
   * or perform other effects which require the precise coords.
   */
  def createImpactParticles( shootable : Shootable, hitX : Double, hitY : Double, hitZ : Double ) : Unit = ()

  def canCollideWithBlock( shootable : Shootable, b : Block, metadata : Int, pos : BlockPos ) =
    if ( b.isInstanceOf[BlockFluid] || b.isInstanceOf[IFluidBlock] ) collidesWithLiquids(shootable)
    else true

  def canCollideWithEntity( shootable : Shootable, entity : Entity ) = !(entity == shootable.shooter)

  def collidesWithLiquids( shootable : Shootable ) : Boolean = false

  /**
   * Get the opposite side of the given side.
   */
  def invertSide( side : Int ) = side match {
      case 0 => 1
      case 1 => 0
      case 2 => 3
      case 3 => 2
      case 4 => 5
      case 5 => 4
    }

  def hitOffset( side : Int ) : BlockPos = side match {
    case 0 => BlockPos(0, -1, 0)
    case 1 => BlockPos(0, +1, 0)
    case 2 => BlockPos(0, 0, -1)
    case 3 => BlockPos(0, 0, +1)
    case 4 => BlockPos(-1, 0, 0)
    case 5 => BlockPos(+1, 0, 0)
  }

  /**
   * Adjust the coords to the block adjacent to the struck side.
   */
  def adjustCoords( x : Int, y : Int, z : Int, side : Int ) : BlockPos =
    BlockPos( x, y, z ).add( hitOffset( side ) )

  def boltTexture : ResourceLocation
  def beamTexture : ResourceLocation
  def lineTexture : ResourceLocation = BoltRenderer.lineBlackTexture

  def createBeamEntity( world : World ) : BaseBeamEntity = {
    val beam = new BaseBeamEntity( world )
    beam.effect = this
    beam
  }

  def createBoltEntity( world : World ) : BaseBoltEntity = {
    val bolt = new BaseBoltEntity( world )
    bolt.effect = this
    bolt
  }
}