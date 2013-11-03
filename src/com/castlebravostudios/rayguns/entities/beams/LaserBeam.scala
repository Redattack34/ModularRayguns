package com.castlebravostudios.rayguns.entities.beams

import net.minecraft.client.particle.EntityFX
import net.minecraft.world.World
import net.minecraft.entity.Entity
import net.minecraft.util.EntityDamageSource
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.EnumMovingObjectType
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.Vec3
import com.castlebravostudios.rayguns.utils.Extensions.WorldExtension
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import cpw.mods.fml.common.registry.IThrowableEntity
import com.google.common.io.ByteArrayDataOutput

class LaserBeam(world : World) extends Entity( world ) {

  private var timeRemaining = 3
  var length : Double = 0

  ignoreFrustumCheck = true

  //I've... repurposed a bunch of the vanilla fields to avoid messing with packets and so on.
  def setStart( start : Vec3 ) : Unit = {
    setPosition(start.xCoord, start.yCoord, start.zCoord)
  }


  private var _shooter : Entity = _
  private var shooterName : String = ""
  def shooter_=( shooter : Entity ) : Unit = {
    _shooter = shooter
    shooterName = shooter.getEntityName
  }
  def shooter : Entity = {
    if ( _shooter == null && shooterName != null && !shooterName.isEmpty ) {
      _shooter = this.worldObj.getPlayerEntityByName(shooterName)
    }
    _shooter
  }
  def getThrower = shooter
  def setThrower( e : Entity ) = shooter = e

  def onImpact( pos : MovingObjectPosition ) {
    createSmoke( pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord )

    pos.typeOfHit match {
      case EnumMovingObjectType.ENTITY => hitEntity( pos.entityHit )
      case EnumMovingObjectType.TILE => hitBlock( pos.blockX, pos.blockY, pos.blockZ, pos.sideHit )
    }
  }

  override def onUpdate() : Unit = {
    super.onUpdate
    timeRemaining -= 1
    if ( timeRemaining <= 0 ) {
      setDead()
    }
  }

  def hitEntity( entity : Entity ) : Unit = {
    entity.attackEntityFrom(
      new EntityDamageSource("laser", shooter), 2.0f)
  }

  def hitBlock(hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Unit = {
  }

  private def createSmoke( x : Double, y : Double, z : Double ) {
    for ( _ <- 0 until 4 ) {
      this.worldObj.spawnParticle("smoke", x, y, z, 0.0D, 0.0D, 0.0D);
    }
  }

  override def writeEntityToNBT( tag : NBTTagCompound ) : Unit = {
    tag.setString("ownerName", shooterName)
    tag.setShort("lifetime", timeRemaining.shortValue )
  }

  override def readEntityFromNBT( tag : NBTTagCompound ) : Unit = {
    shooterName = tag.getString("ownerName")
    timeRemaining = tag.getShort( "lifetime" )
  }
  protected override def entityInit()  : Unit = ()
}