package com.castlebravostudios.rayguns.utils

import net.minecraft.entity.EntityLivingBase
import net.minecraft.world.World
import net.minecraft.entity.Entity
import net.minecraft.util.MathHelper
import java.util.Random
import net.minecraft.entity.player.EntityPlayer
import com.castlebravostudios.rayguns.entities.bolts.BaseBoltEntity

object BoltUtils {

  private final val rand = new Random();

  def spawnNormal( world : World, bolt : BaseBoltEntity, shooter : EntityLivingBase ) : Unit = {
    if ( !world.isRemote ) return
    initBolt(bolt, shooter)
    setMotion(bolt, bolt.rotationYaw, getBasePitch(shooter, bolt))
    world.spawnEntityInWorld(bolt)
  }

  def spawnPrecise( world : World, bolt : BaseBoltEntity, shooter : EntityLivingBase ) : Unit = {
    if ( !world.isRemote ) return
    initBolt(bolt, shooter)
    setMotion(bolt, bolt.rotationYaw, getBasePitch(shooter, bolt), velocityMultiplier = 2.0f)
    world.spawnEntityInWorld(bolt)
  }

  def spawnScatter( world : World, shooter : EntityLivingBase, shots : Int, scatterFactor: Float )( bolt : () => BaseBoltEntity ) : Unit = {
    if ( !world.isRemote ) return
    for ( _ <- 0 until shots ) {
      val shot = bolt()
      initBolt( shot, shooter )
      val scatterYaw : Float = (getClampedGaussian * scatterFactor)
      val scatterPitch : Float = (getClampedGaussian * scatterFactor).floatValue
      setMotion( shot, shot.rotationYaw + scatterYaw, getBasePitch(shooter, shot) + scatterPitch )
      world.spawnEntityInWorld( shot )
    }
  }

  private def getClampedGaussian() : Float =
    MathHelper.clamp_float(-2.0f, rand.nextGaussian().floatValue, 2.0f)

  private def toRadians(yaw: Float): Float = {
    yaw / 180.0F * Math.PI.floatValue
  }

  private def initBolt(bolt: BaseBoltEntity, shooter: net.minecraft.entity.EntityLivingBase): Unit = {
      bolt.shooter = shooter;
      bolt.setSize(0.25F, 0.25F);
      initPositionAngle(bolt, shooter)
      offsetInFrontOfShooter(bolt)
  }

  /**
   * Initializes the position and angles to be at the position of the shooter,
   * at eye level, in the direction the shooter is looking.
   */
  private def initPositionAngle(bolt: BaseBoltEntity, shooter: net.minecraft.entity.EntityLivingBase): Unit = {
    bolt.setLocationAndAngles(shooter.posX, shooter.posY + shooter.getEyeHeight().doubleValue(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
  }

  /**
   * Offset the position based on the shooter's rotation so that it spawns just
   * in front of the shooter.
   */
  private def offsetInFrontOfShooter(bolt: BaseBoltEntity): Unit = {
    bolt.posX -= (MathHelper.cos(toRadians(bolt.rotationYaw)) * 0.16F).doubleValue();
    bolt.posY -= 0.10000000149011612D;
    bolt.posZ -= (MathHelper.sin(toRadians(bolt.rotationYaw)) * 0.16F).doubleValue();
    bolt.setPosition(bolt.posX, bolt.posY, bolt.posZ);
    bolt.yOffset = 0.0F;
  }

  /**
   * Sets the motion values to produce a given base velocity in the given
   * direction. Useful for changing the direction without affecting the velocity.
   */
  private def setMotion(bolt: BaseBoltEntity, yaw: Float, pitch: Float, velocityMultiplier : Float = 1.0f): Unit = {
    val baseVelocity = 0.4
    bolt.motionX = (-MathHelper.sin(toRadians(yaw)) * MathHelper.cos(toRadians(pitch)) * baseVelocity).doubleValue;
    bolt.motionZ = (MathHelper.cos(toRadians(yaw)) * MathHelper.cos(toRadians(pitch)) * baseVelocity).doubleValue;
    bolt.motionY = (-MathHelper.sin(toRadians(pitch + bolt.pitchOffset)) * baseVelocity).doubleValue;
    bolt.setThrowableHeading(bolt.motionX, bolt.motionY, bolt.motionZ, bolt.velocityMultiplier * velocityMultiplier, 1.0F);
  }

  private def getBasePitch(shooter : EntityLivingBase, bolt: BaseBoltEntity): Float =
    if ( shooter.isSneaking() && !isCreativeFlying( shooter ) ) 0.0f
    else bolt.rotationPitch

  private def isCreativeFlying( shooter : EntityLivingBase ) = shooter match {
      case p : EntityPlayer => p.capabilities.isFlying
      case _ => false
    }
}