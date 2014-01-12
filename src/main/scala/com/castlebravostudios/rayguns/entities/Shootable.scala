package com.castlebravostudios.rayguns.entities

import cpw.mods.fml.common.registry.IThrowableEntity
import net.minecraft.entity.Entity
import net.minecraft.world.World
import net.minecraft.nbt.NBTTagCompound
import java.util.Random

trait Shootable extends Entity with IThrowableEntity {

  def charge : Double
  def charge_=( charge : Double ) : Unit

  private var _shooter : Entity = _
  private var shooterName : String = ""
  def shooter_=( shooter : Entity ) : Unit = {
    _shooter = shooter
    if ( shooter != null ) shooterName = shooter.getEntityName
  }
  def shooter : Entity = {
    if ( _shooter == null && shooterName != null && !shooterName.isEmpty ) {
      _shooter = this.worldObj.getPlayerEntityByName(shooterName)
    }
    _shooter
  }
  def getThrower = shooter
  def setThrower( e : Entity ) = shooter = e

  override def writeEntityToNBT( tag : NBTTagCompound ) : Unit = {
    tag.setString("ownerName", shooterName)
  }

  override def readEntityFromNBT( tag : NBTTagCompound ) : Unit = {
    shooterName = tag.getString("ownerName")
  }

  def random : Random
}