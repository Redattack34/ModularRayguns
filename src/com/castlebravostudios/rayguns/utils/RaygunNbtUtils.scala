package com.castlebravostudios.rayguns.utils

import net.minecraft.nbt.NBTTagCompound
import com.castlebravostudios.rayguns.api.items.ItemModule
import net.minecraft.item.ItemStack
import com.castlebravostudios.rayguns.api.BeamRegistry
import com.castlebravostudios.rayguns.api.ModuleRegistry
import com.castlebravostudios.rayguns.blocks.gunbench.GunBenchTileEntity
import com.castlebravostudios.rayguns.api.items.ItemBody
import com.castlebravostudios.rayguns.api.items.ItemChamber
import com.castlebravostudios.rayguns.api.items.ItemLens
import com.castlebravostudios.rayguns.api.items.ItemAccessory
import com.castlebravostudios.rayguns.api.items.ItemBattery
import com.castlebravostudios.rayguns.items.RayGun
import com.castlebravostudios.rayguns.items.Items
import com.castlebravostudios.rayguns.items.BrokenGun

case class GunComponents(body : ItemBody, chamber : ItemChamber, battery : ItemBattery,
    lens : Option[ItemLens], acc : Option[ItemAccessory] ) {

  def powerMultiplier : Double = body.powerModifier * chamber.powerModifier * battery.powerModifier *
    lens.map(_.powerModifier).getOrElse(1.0) * acc.map(_.powerModifier).getOrElse(1.0)

  def components : Seq[ItemModule] = Seq( body, chamber, battery ) ++ lens ++ acc

  def isValid : Boolean = components.forall( c => c != null && ModuleRegistry.isRegistered(c) )
}
object RaygunNbtUtils {

  import ModuleRegistry._

  val BODY_STR = "body"
  val LENS_STR = "lens"
  val CHAMBER_STR = "chamber"
  val BATTERY_STR = "battery"
  val ACC_STR = "accessory"

  val MODULES_TAG = "raygunModules"

  val chargeDepleted = "ChargeDepleted"

  /**
   * Get the components from a stack that contains a RayGun. Returns Some if
   * all of the components in the stack are valid components and if the body,
   * chamber and battery are all set. The returned components may or may not
   * form a valid gun.
   */
  def getComponents( item : ItemStack ) : Option[GunComponents] = {
    for { body <- getComponent(item, BODY_STR)(ModuleRegistry.getBody)
          chamber <- getComponent(item, CHAMBER_STR)(getChamber)
          battery <- getComponent(item, BATTERY_STR)(getBattery)
          lens = getComponent(item, LENS_STR)(getLens)
          accessory = getComponent(item, ACC_STR)(getAccessory) }
      yield GunComponents( body, chamber, battery, lens, accessory )
    }

  private def getComponent[T <: ItemModule](item : ItemStack, key: String )(f : String => Option[T]) : Option[T] = {
    for { name <- getModuleName( item, key )
          module <- f( name )
        } yield module
  }

  private def getModuleName( item : ItemStack, key : String ) : Option[String] =
    for {
      moduleTag <- getModuleTag(item)
      name <- Option(moduleTag.getString( key ) )
    } yield name

  private def getModuleTag( item : ItemStack ) : Option[NBTTagCompound] = {
    for {
      moduleTag <- Option( getTagCompound(item).getCompoundTag( MODULES_TAG ) )
    } yield moduleTag
  }

  def buildGun( components : GunComponents ) : Option[ItemStack] =
    if ( BeamRegistry.isValid(components) ) Some( buildValidGun( components ) )
    else None

  private def buildValidGun( components : GunComponents ) : ItemStack = {
    val stack = new ItemStack( RayGun )
    stack.stackSize = 1
    stack.setTagInfo( MODULES_TAG, buildModuleTag( components ) )
    stack
  }

  private def buildModuleTag( components : GunComponents ) : NBTTagCompound =
    buildModuleTag( new OptionalGunComponents( components ) )
  private def buildModuleTag( components : OptionalGunComponents ) : NBTTagCompound = {
    val tag = new NBTTagCompound( MODULES_TAG )
    components.body.foreach( setTag( tag, BODY_STR )(_) )
    components.chamber.foreach( setTag( tag, CHAMBER_STR )(_) )
    components.battery.foreach( setTag( tag, BATTERY_STR )(_) )
    components.lens.foreach( setTag( tag, LENS_STR )(_) )
    components.acc.foreach( setTag( tag, ACC_STR )(_) )
    tag
  }

  private def setTag( tag : NBTTagCompound, str : String )( item : ItemModule ) : Unit = {
    tag.setString(str, item.moduleKey)
  }

  def buildBrokenGun( item : ItemStack ) : ItemStack = {
    val stack = new ItemStack( BrokenGun )
    stack.stackSize = 1
    stack.setTagInfo( MODULES_TAG, buildModuleTag( getAllValidComponents( item ) ) )
    setChargeDepleted( getChargeDepleted( item ), stack );
    stack
  }

  /**
   * Get whatever valid components a stack contains. This is used for salvaging
   * a broken gun and converting a functional gun to a broken one when fired if
   * it is invalid.
   */
  def getAllValidComponents( item : ItemStack ) : OptionalGunComponents = {
    val body = getComponent(item, BODY_STR)(getBody)
    val chamber = getComponent(item, CHAMBER_STR)(getChamber)
    val battery = getComponent(item, BATTERY_STR)(getBattery)
    val lens = getComponent(item, LENS_STR)(getLens)
    val accessory = getComponent(item, ACC_STR)(getAccessory)
    OptionalGunComponents( body, chamber, battery, lens, accessory )
  }

  def addCharge( delta : Int, stack : ItemStack ) : Unit =
    setChargeDepleted( getChargeDepleted(stack) - delta , stack)

  def setChargeDepleted( charge : Int, stack : ItemStack ) : Unit = {
    def clamp( min : Int, cur : Int, max : Int ) : Int =
      if ( cur < min ) min
      else if ( cur > max ) max
      else cur

    val actualCharge = clamp( 0, charge, getMaxDamage( stack ) )

    getTagCompound(stack).setInteger( chargeDepleted, actualCharge )
  }

  def getChargeDepleted( stack : ItemStack ) : Int = {
    val tag = getTagCompound(stack)
    if ( tag == null || !tag.hasKey( chargeDepleted ) ) {
      setChargeDepleted( 0, stack )
    }
    tag.getInteger(chargeDepleted)
  }

  def getMaxDamage( item: ItemStack ) : Int = item.getItem() match {
    case bat: ItemBattery => bat.maxCapacity
    case _ => getComponents( item ).map( _.battery.maxCapacity ).getOrElse(Integer.MAX_VALUE)
  }

  def getTagCompound( item : ItemStack ) : NBTTagCompound = {
    if ( item.getTagCompound() == null ) {
      item.setTagCompound( new NBTTagCompound() );
    }
    item.getTagCompound()
  }

  case class OptionalGunComponents(
    body : Option[ItemBody], chamber : Option[ItemChamber], battery : Option[ItemBattery],
    lens : Option[ItemLens], acc : Option[ItemAccessory] ) {

    def this( comp : GunComponents ) = this( Some( comp.body ),
        Some( comp.chamber ), Some( comp.battery ), comp.lens, comp.acc );
  }
}