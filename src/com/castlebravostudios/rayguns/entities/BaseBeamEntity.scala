package com.castlebravostudios.rayguns.entities

import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import net.minecraft.entity.Entity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumMovingObjectType
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.Vec3
import net.minecraft.world.World
import net.minecraft.util.ResourceLocation
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteArrayDataOutput

abstract class BaseBeamEntity(world : World) extends Entity( world ) with Shootable with IEntityAdditionalSpawnData {
  self : BaseEffect =>

  var charge : Double = 1.0d
  def depletionRate = 0.3d
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
    charge -= depletionRate
    if ( charge <= 0 ) {
      setDead()
    }
  }

  override def writeEntityToNBT( tag : NBTTagCompound ) : Unit = {
    tag.setDouble("charge", charge)
    writeEffectToNbt(tag)
  }

  override def readEntityFromNBT( tag : NBTTagCompound ) : Unit = {
    charge = tag.getDouble("charge")
    readEffectFromNbt(tag)
  }

  def writeSpawnData( out : ByteArrayDataOutput ) : Unit = {
    out.writeDouble( charge )
  }

  def readSpawnData( in : ByteArrayDataInput ) : Unit = {
    charge = in.readDouble()
  }

  protected override def entityInit()  : Unit = ()

  def random = this.rand

  def texture : ResourceLocation
}