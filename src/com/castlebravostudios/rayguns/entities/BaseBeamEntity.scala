package com.castlebravostudios.rayguns.entities

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityThrowable
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World
import net.minecraft.nbt.NBTTagCompound

class BaseBeamEntity( world : World ) extends EntityThrowable( world ) {

  def colorRed : Float = 1.0f
  def colorBlue : Float = 1.0f
  def colorGreen : Float = 1.0f
  def colorAlpha : Float = 1.0f

  def lifetime = 20
  private var lifeRemaining = lifetime

  private var _shooter : EntityLivingBase = _
  private var shooterName : String = ""

  def shooter_=( shooter : EntityLivingBase ) : Unit = {
    _shooter = shooter
    shooterName = shooter.getEntityName
  }
  def shooter : EntityLivingBase = {
    if ( _shooter == null && shooterName != null && !shooterName.isEmpty ) {
      _shooter = BaseBeamEntity.this.worldObj.getPlayerEntityByName(shooterName);
    }
    _shooter
  }

  def pitchOffset : Float = func_70183_g()
  def velocityMultiplier : Float = func_70182_d()
  override def getGravityVelocity = 0.0f

  override def onUpdate() : Unit = {
    super.onUpdate
    lifeRemaining -= 1
    if ( lifeRemaining <= 0 ) {
      setDead()
    }
  }

  override def setSize( width : Float, height : Float ) = super.setSize( width, height )

  override def onImpact( pos : MovingObjectPosition ) : Unit = {
    BaseBeamEntity.this.setDead()
  }

  override def getThrower : EntityLivingBase = shooter

  override def writeEntityToNBT( tag : NBTTagCompound ) : Unit = {
    super.writeEntityToNBT(tag)
    tag.setString("ownerName", shooterName);
  }

  override def readEntityFromNBT( tag : NBTTagCompound ) : Unit = {
    super.readEntityFromNBT(tag)
    shooterName = tag.getString("ownerName")
  }

}