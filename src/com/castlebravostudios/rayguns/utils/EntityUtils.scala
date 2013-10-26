package com.castlebravostudios.rayguns.utils

import net.minecraft.entity.EntityLivingBase
import net.minecraft.world.World
import net.minecraft.entity.Entity
import com.castlebravostudios.rayguns.entities.BaseBeamEntity
import net.minecraft.util.MathHelper
import java.util.Random

object EntityUtils {

  private final val rand = new Random();

  def spawnNormal( world : World, beam : BaseBeamEntity, shooter : EntityLivingBase ) : Unit = {
    initBeam(beam, shooter)
    setMotion(beam, beam.rotationYaw, beam.rotationPitch)
    world.spawnEntityInWorld(beam)
  }

  def spawnPrecise( world : World, beam : BaseBeamEntity, shooter : EntityLivingBase ) : Unit = {
    initBeam(beam, shooter)
    setMotion(beam, beam.rotationYaw, beam.rotationPitch, velocityMultiplier = 2.0f)
    world.spawnEntityInWorld(beam)
  }

  def spawnScatter( world : World, beam : => BaseBeamEntity, shooter : EntityLivingBase, shots : Int, scatterFactor: Float ) : Unit = {
    for ( _ <- 0 until shots ) {
      val shot = beam
      initBeam( shot, shooter )
      val scatterYaw : Float = (getClampedGaussian * scatterFactor)
      val scatterPitch : Float = (getClampedGaussian * scatterFactor).floatValue
      setMotion( shot, shot.rotationYaw + scatterYaw, shot.rotationPitch + scatterPitch )
      world.spawnEntityInWorld( shot )
    }
  }

  private def getClampedGaussian() : Float =
    MathHelper.clamp_float(-2.0f, rand.nextGaussian().floatValue, 2.0f)

  private def toRadians(yaw: Float): Float = {
    yaw / 180.0F * Math.PI.floatValue
  }

  private def initBeam(beam: com.castlebravostudios.rayguns.entities.BaseBeamEntity, shooter: net.minecraft.entity.EntityLivingBase): Unit = {
      beam.shooter = shooter;
      beam.setSize(0.25F, 0.25F);
      initPositionAngle(beam, shooter)
      offsetInFrontOfShooter(beam)
  }

  /**
   * Initializes the position and angles to be at the position of the shooter,
   * at eye level, in the direction the shooter is looking.
   */
  private def initPositionAngle(beam: BaseBeamEntity, shooter: net.minecraft.entity.EntityLivingBase): Unit = {
    beam.setLocationAndAngles(shooter.posX, shooter.posY + shooter.getEyeHeight().doubleValue(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
  }

  /**
   * Offset the position based on the shooter's rotation so that it spawns just
   * in front of the shooter.
   */
  private def offsetInFrontOfShooter(beam: com.castlebravostudios.rayguns.entities.BaseBeamEntity): Unit = {
    beam.posX -= (MathHelper.cos(toRadians(beam.rotationYaw)) * 0.16F).doubleValue();
    beam.posY -= 0.10000000149011612D;
    beam.posZ -= (MathHelper.sin(toRadians(beam.rotationYaw)) * 0.16F).doubleValue();
    beam.setPosition(beam.posX, beam.posY, beam.posZ);
    beam.yOffset = 0.0F;
  }

  /**
   * Sets the motion values to produce a given base velocity in the given
   * direction. Useful for changing the direction without affecting the velocity.
   */
  private def setMotion(beam: BaseBeamEntity, yaw: Float, pitch: Float, velocityMultiplier : Float = 1.0f): Unit = {
    val baseVelocity = 0.4
    beam.motionX = (-MathHelper.sin(toRadians(yaw)) * MathHelper.cos(toRadians(pitch)) * baseVelocity).doubleValue;
    beam.motionZ = (MathHelper.cos(toRadians(yaw)) * MathHelper.cos(toRadians(pitch)) * baseVelocity).doubleValue;
    beam.motionY = (-MathHelper.sin(toRadians(pitch + beam.pitchOffset)) * baseVelocity).doubleValue;
    beam.setThrowableHeading(beam.motionX, beam.motionY, beam.motionZ, beam.velocityMultiplier * velocityMultiplier, 1.0F);
  }
}