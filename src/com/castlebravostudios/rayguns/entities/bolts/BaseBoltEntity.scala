package com.castlebravostudios.rayguns.entities.bolts

import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumMovingObjectType
import net.minecraft.entity.Entity
import net.minecraft.entity.IProjectile
import net.minecraft.util.MathHelper
import scala.collection.JavaConversions._

/**
 * Abstract base class for beam entities. Most of this code is a poor translation
 * of the source of EntityThrowable, since I needed to modify something deep in
 * onUpdate. Not coincedentally, most of this code is really non-idiomatic Scala
 * and should not be taken as an example.
 */
abstract class BaseBoltEntity( world : World ) extends Entity( world ) with IProjectile {

  def colorRed : Float = 1.0f
  def colorBlue : Float = 1.0f
  def colorGreen : Float = 1.0f

  def lifetime = 20
  private var timeRemaining = lifetime

  private var _shooter : EntityLivingBase = _
  private var shooterName : String = ""

  def shooter_=( shooter : EntityLivingBase ) : Unit = {
    _shooter = shooter
    shooterName = shooter.getEntityName
  }
  def shooter : EntityLivingBase = {
    if ( _shooter == null && shooterName != null && !shooterName.isEmpty ) {
      _shooter = this.worldObj.getPlayerEntityByName(shooterName)
    }
    _shooter
  }

  def pitchOffset : Float = 0.5f
  def velocityMultiplier : Float = 1.5f

  override def onUpdate() : Unit = {
    this.lastTickPosX = this.posX
    this.lastTickPosY = this.posY
    this.lastTickPosZ = this.posZ
    super.onUpdate()

    var movingobjectposition : Option[MovingObjectPosition] = traceCollision()
    val startPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ)
    val endPos =
      if ( movingobjectposition.isEmpty ) this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ)
      else this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.get.hitVec.xCoord, movingobjectposition.get.hitVec.yCoord, movingobjectposition.get.hitVec.zCoord)

    if (!this.worldObj.isRemote) {
        var collidedWith : Entity = null
        var list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D))
        var minDistance = 0.0D

        list.toVector.foreach{ case( entity1 : Entity ) =>
            if (entity1.canBeCollidedWith() && (entity1 != shooter))
            {
                val axisalignedbb = entity1.boundingBox.expand(0.3d, 0.3d, 0.3d)
                val movingobjectposition1 = axisalignedbb.calculateIntercept(startPos, endPos)

                if (movingobjectposition1 != null)
                {
                    val curDistance = startPos.distanceTo(movingobjectposition1.hitVec)

                    if (curDistance < minDistance || minDistance == 0.0D)
                    {
                        collidedWith = entity1
                        minDistance = curDistance
                    }
                }
            }
        }

        if (collidedWith != null)
        {
            movingobjectposition = Some( new MovingObjectPosition(collidedWith) )
        }
    }

    movingobjectposition.foreach( this.onImpact _ )

    this.posX += this.motionX
    this.posY += this.motionY
    this.posZ += this.motionZ

    if (this.isInWater())
    {
        for ( _ <- 0 until 4 )
        {
            val f4 = 0.25F
            this.worldObj.spawnParticle("bubble",
                this.posX - this.motionX * f4.doubleValue,
                this.posY - this.motionY * f4.doubleValue,
                this.posZ - this.motionZ * f4.doubleValue,
                this.motionX, this.motionY, this.motionZ)
        }
    }

    this.setPosition(this.posX, this.posY, this.posZ)

    timeRemaining -= 1
    if ( timeRemaining <= 0 ) {
      setDead()
    }
  }

  def traceCollision() : Option[MovingObjectPosition] = {
    val startPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ)
    val endPos = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ)
    Option( this.worldObj.clip(startPos, endPos, collidesWithLiquids) )
  }

  def collidesWithLiquids : Boolean = false

  override def setSize( width : Float, height : Float ) = super.setSize( width, height )

  def onImpact( pos : MovingObjectPosition ) {

    pos.typeOfHit match {
      case EnumMovingObjectType.ENTITY => hitEntity( pos.entityHit )
      case EnumMovingObjectType.TILE => hitBlock( pos.blockX, pos.blockY, pos.blockZ, pos.sideHit )
    }

    setDead()
  }

  def hitEntity( entity : Entity ) : Unit
  def hitBlock( hitX : Int, hitY : Int, hitZ : Int, side : Int ) : Unit

  override def writeEntityToNBT( tag : NBTTagCompound ) : Unit = {
    tag.setString("ownerName", shooterName)
    tag.setShort("lifetime", timeRemaining.shortValue )
  }

  override def readEntityFromNBT( tag : NBTTagCompound ) : Unit = {
    shooterName = tag.getString("ownerName")
    timeRemaining = tag.getShort( "lifetime" )
  }

  override def isInRangeToRenderDist(limit : Double) : Boolean = {
    var d1 = this.boundingBox.getAverageEdgeLength() * 4.0D
    d1 *= 64.0D
    return limit < d1 * d1
  }

  override def setThrowableHeading(xIn : Double, yIn : Double, zIn : Double, velocityMult : Float, scatterMult : Float ) : Unit = {
    var x = xIn
    var y = yIn
    var z = zIn
    val f2 = MathHelper.sqrt_double(x * x + y * y + z * z)
    x /= f2.doubleValue
    y /= f2.doubleValue
    z /= f2.doubleValue
    x += this.rand.nextGaussian() * 0.007499999832361937D * scatterMult.doubleValue
    y += this.rand.nextGaussian() * 0.007499999832361937D * scatterMult.doubleValue
    z += this.rand.nextGaussian() * 0.007499999832361937D * scatterMult.doubleValue
    x *= velocityMult.doubleValue
    y *= velocityMult.doubleValue
    z *= velocityMult.doubleValue
    this.motionX = x
    this.motionY = y
    this.motionZ = z
    val f3 = MathHelper.sqrt_double(x * x + z * z)
    this.rotationYaw = (Math.atan2(x, z) * 180.0D / Math.PI).floatValue()
    this.prevRotationYaw = rotationYaw
    this.rotationPitch = (Math.atan2(y, f3.doubleValue) * 180.0D / Math.PI).floatValue()
    this.prevRotationPitch = rotationPitch
  }

  protected override def entityInit()  : Unit = ()
}