package com.castlebravostudios.rayguns.entities

import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import net.minecraft.entity.Entity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumMovingObjectType
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.Vec3
import net.minecraft.world.World
import net.minecraft.util.ResourceLocation

abstract class BaseBeamEntity(world : World) extends Entity( world ) with Shootable {
  self : BaseEffect =>

  def lifetime = 3
  var timeRemaining = lifetime
  var length : Double = 0

  ignoreFrustumCheck = true

  //I've... repurposed a bunch of the vanilla fields to avoid messing with packets and so on.
  def setStart( start : Vec3 ) : Unit = {
    setPosition(start.xCoord, start.yCoord, start.zCoord)
  }

  def onImpact( pos : MovingObjectPosition ) : Boolean = {
    createImpactParticles(pos.hitVec.xCoord, pos.hitVec.yCoord, pos.hitVec.zCoord)
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

  override def writeEntityToNBT( tag : NBTTagCompound ) : Unit = {
    tag.setShort("lifetime", timeRemaining.shortValue )
    writeEffectToNbt(tag)
  }

  override def readEntityFromNBT( tag : NBTTagCompound ) : Unit = {
    timeRemaining = tag.getShort( "lifetime" )
    readEffectFromNbt(tag)
  }

  protected override def entityInit()  : Unit = ()

  def random = this.rand

  def texture : ResourceLocation
}