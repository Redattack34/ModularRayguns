package com.castlebravostudios.rayguns.render

import scala.util.Random
import org.lwjgl.opengl.GL11
import com.castlebravostudios.rayguns.items.misc.RayGun
import cpw.mods.fml.common.ITickHandler
import net.minecraft.client.renderer.ItemRenderer
import net.minecraft.client.renderer.RenderBlocks
import net.minecraft.client.renderer.Tessellator
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraftforge.client.IItemRenderer
import net.minecraftforge.client.IItemRenderer.ItemRenderType
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper
import java.util.EnumSet
import cpw.mods.fml.common.TickType
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import net.minecraft.client.renderer.OpenGlHelper
import com.castlebravostudios.rayguns.utils.RaygunNbtUtils

object RaygunRender extends IItemRenderer with ITickHandler {

  private var partialTickTime : Float = 0.0f

  private val rand = new Random()

  def handleRenderType( item : ItemStack, renderType : ItemRenderType ) : Boolean = {
    renderType == ItemRenderType.EQUIPPED || renderType == ItemRenderType.EQUIPPED_FIRST_PERSON
  }

  def shouldUseRenderHelper( renderType : ItemRenderType, item : ItemStack, helper : ItemRendererHelper ) : Boolean = {
    false
  }

  def renderItem( renderType : ItemRenderType, item : ItemStack, data : Object* ): Unit = {
    val renderBlocks : RenderBlocks = data(0).asInstanceOf[RenderBlocks]
    val entity : EntityLivingBase = data(1).asInstanceOf[EntityLivingBase]

    offsetForRecoil(item)

    val chargePower = getChargePower( item, entity )
    offsetForChargeJitter( chargePower )

    renderItem( item, entity )
    renderCharge( item, chargePower )
  }

  private def offsetForRecoil(item: net.minecraft.item.ItemStack) : Unit = {
    val maxCooldown = Math.max( RayGun.getBaseCooldownTime( item ), 1 )
    val curCooldown = Math.min( RayGun.getCooldownTime( item ), maxCooldown )

    val coolPercent = Math.max( 0.0f, ( curCooldown.toFloat - partialTickTime ) / maxCooldown )

    GL11.glTranslatef( coolPercent * -0.3f, coolPercent * -0.2f, 0.0f)
  }

  private def offsetForChargeJitter( chargePercent: Double ) = {
    def randomJitter : Double = 0.02f * chargePercent * ( rand.nextDouble() - 0.5d )
    GL11.glTranslated( randomJitter, randomJitter, randomJitter )
  }

  private def getChargePower( item: ItemStack, entity: EntityLivingBase ) : Double = entity match {
    case pl : EntityPlayer => if ( pl.isUsingItem() ) RayGun.getChargePower( item, pl.getItemInUseCount() ) else 0.0d
    case _ => 0.0d
  }

  private def renderItem( item : ItemStack, entity : EntityLivingBase ) : Unit = {
    val tessellator = Tessellator.instance;
    val icon = entity.getItemIcon(item, 0);
    val minU = icon.getMinU();
    val maxU = icon.getMaxU();
    val minV = icon.getMinV();
    val maxV = icon.getMaxV();
    val width = 0.0625F
    ItemRenderer.renderItemIn2D( tessellator, maxU, minV, minU, maxV,
        icon.getIconWidth(), icon.getIconHeight(), width );
  }

  private def renderCharge( item : ItemStack, chargePower: Double ) : Unit= {
    val chamber = RaygunNbtUtils.getComponents(item).map( _.chamber )
    if ( chamber.isEmpty ) return

    Minecraft.getMinecraft().getTextureManager().bindTexture( chamber.get.chargeTexture )

    val tes = Tessellator.instance
    GL11.glTranslated( 1, 1, 0 )
    GL11.glScaled( 0.15 * chargePower, 0.15 * chargePower, 0.15 * chargePower )
    GL11.glRotated(25, 0, 0, 1)

    GL11.glDisable(GL11.GL_LIGHTING)
    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glDisable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)

    tes.startDrawingQuads()
    chargeVertices.foreach( ( tes.addVertexWithUV _ ).tupled )
    tes.draw()

    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glEnable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)
    GL11.glEnable(GL11.GL_LIGHTING)

  }

  def tickStart( tickType : EnumSet[TickType], tickData : Object* ) : Unit = {
    partialTickTime = tickData(0).asInstanceOf[Float]
  }

  def tickEnd( tickType : EnumSet[TickType], tickData : Object* ) : Unit = ()

  def ticks() = EnumSet.of( TickType.RENDER )

  def getLabel() = "RaygunRenderer"

  type Vertex = (Double, Double, Double, Double, Double)
  private def chargeVertices = Array[Vertex](
    (0.0D, -1.0D, 0.0D, 0, 0),
    (0.0D, 0.0D, 1.0D, 0, 1),
    (0.0D, 1.0D, 0.0D, 1, 1),
    (0.0D, 0.0D, -1.0D, 1, 0),

    (1.0D, 0.0D, 0.0D, 1, 0),
    (0.0D, 0.0D, -1.0D, 1, 1),
    (-0.25D, 0.0D, 0.0D, 0, 1),
    (0.0D, 0.0D, 1.0D, 0, 0),

    (1.0D, 0.0D, 0.0D, 0, 0),
    (0.0D, 1.0D, 0.0D, 0, 1),
    (-0.25D, 0.0D, 0.0D, 1, 1),
    (0.0D, -1.0D, 0.0D, 1, 0)
  )
}