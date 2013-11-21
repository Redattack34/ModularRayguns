package com.castlebravostudios.rayguns.entities.effects

import com.castlebravostudios.rayguns.entities.Shootable
import com.castlebravostudios.rayguns.entities.BaseBoltEntity
import net.minecraft.entity.Entity
import net.minecraft.util.EntityDamageSource
import net.minecraft.world.World
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import com.castlebravostudios.rayguns.entities.NoDuplicateCollisions
import net.minecraftforge.event.entity.living.EnderTeleportEvent
import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.common.MinecraftForge
import net.minecraft.util.DamageSource
import net.minecraft.entity.boss.IBossDisplayData
import com.castlebravostudios.rayguns.utils.RaytraceUtils
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension

trait EnderEffect extends Entity with BaseEffect {
  self : Shootable =>

  def colourRed : Float = 1.0f
  def colourBlue : Float = 1.0f
  def colourGreen : Float = 0.0f

  def hitEntity( entity : Entity ) : Boolean = {
    if ( worldObj.isOnServer ) {
      doTeleport( entity )
    }
    true
  }

  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Boolean = true

  def createImpactParticles( hitX : Double, hitY : Double, hitZ : Double ) : Unit = {
    for ( _ <- 0 until 16 ) {
        this.worldObj.spawnParticle("portal", hitX, hitY, hitZ, random.nextGaussian(), 0.0D, random.nextGaussian());
    }
  }

  private def getNewCoords(entity : EntityLivingBase, x : Int, y : Int, z : Int) : (Int, Int, Int) = {
    val start = worldObj.getWorldVec3Pool().getVecFromPool(entity.posX, entity.posY, entity.posZ)
    val end = worldObj.getWorldVec3Pool().getVecFromPool(x, y, z)
    val hit = RaytraceUtils.rayTraceBlocks(worldObj, start, end){
      (b, m, p) => b.blockMaterial.blocksMovement() }.headOption

    hit match {
      case Some( mop ) => adjustCoords( mop.blockX, mop.blockY, mop.blockZ, mop.sideHit)
      case None => (x, y, z)
    }
  }

  def getTeleportY(x: Int, y: Int, z: Int) : Option[Int] = {
    var newY = y;
    while ( newY < y + 32 ) {
      if ( worldObj.isAirBlock(x, newY, z) && worldObj.isAirBlock(x, newY + 1, z) ) return Some( newY )
      newY += 1
    }
    return None
  }

  def doTeleport(entity: Entity) : Unit = {
    if ( entity.isInstanceOf[IBossDisplayData] ) return
    if ( !entity.isInstanceOf[EntityLivingBase] ) return
    val living = entity.asInstanceOf[EntityLivingBase]

    val x = (entity.posX + ( random.nextFloat() - 0.5 ) * 16).toInt
    val z = (entity.posZ + ( random.nextFloat() - 0.5 ) * 16).toInt
    val optY = getTeleportY( x, living.posY.toInt, z )
    if ( optY.isEmpty ) return
    val y = optY.get

    val (newX, newY, newZ) : (Int, Int, Int) = getNewCoords( living, x, y, z )
    val event = new EnderTeleportEvent( living, newX + (newX.signum * 0.5), newY, newZ + (newZ.signum * 0.5), 5 );

    if (!MinecraftForge.EVENT_BUS.post(event)) {
      living.setPositionAndUpdate(event.targetX, event.targetY, event.targetZ);
      living.prevPosX = event.targetX
      living.prevPosY = event.targetY
      living.prevPosZ = event.targetZ
      living.fallDistance = 0.0F;
      living.attackEntityFrom(DamageSource.fall, event.attackDamage);
    }
  }

}

class EnderBoltEntity(world : World) extends BaseBoltEntity(world) with EnderEffect with NoDuplicateCollisions
class EnderBeamEntity(world : World) extends BaseBeamEntity(world) with EnderEffect