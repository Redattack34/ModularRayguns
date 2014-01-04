package com.castlebravostudios.rayguns.entities

import com.castlebravostudios.rayguns.api.EffectRegistry
import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteArrayDataOutput

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData
import net.minecraft.entity.Entity
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

abstract class BaseShootable( world : World ) extends Entity( world ) with Shootable with IEntityAdditionalSpawnData {

  var effect : BaseEffect = _
  var charge : Double = 1.0d

  override def writeEntityToNBT( tag : NBTTagCompound ) : Unit = {
    tag.setDouble("charge", charge)
    tag.setString("effect", effect.effectKey )
  }

  override def readEntityFromNBT( tag : NBTTagCompound ) : Unit = {
    charge = tag.getDouble("charge")

    val key = tag.getString( "effect" )
    initEffect( key )
  }

  def writeSpawnData( out : ByteArrayDataOutput ) : Unit = {
    out.writeDouble( charge )
    out.writeUTF( effect.effectKey )
  }

  def readSpawnData( in : ByteArrayDataInput ) : Unit = {
    charge = in.readDouble()
    val key = in.readUTF()
    initEffect( key )
  }

  private def initEffect( key : String ) : Unit = {
    val e = EffectRegistry.getEffect( key )
    e match {
      case Some(effect) => this.effect = effect
      case None => {
        System.err.println("Unknown effect key: " + key )
        setDead()
      }
    }
  }

  def entityInit() : Unit = ()

  def random = this.rand

}