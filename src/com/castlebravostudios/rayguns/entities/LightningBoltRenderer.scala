package com.castlebravostudios.rayguns.entities

import org.lwjgl.opengl.GL11
import com.castlebravostudios.rayguns.entities.effects.BaseEffect
import net.minecraft.client.renderer.OpenGlHelper
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.entity.Render
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import com.castlebravostudios.rayguns.entities.effects.LightningBoltEntity
import com.castlebravostudios.rayguns.utils.Vector3
import com.castlebravostudios.rayguns.mod.Config

class LightningBoltRenderer extends Render {

  private val flash = Config.lightningFlash

  def doRender( e : Entity, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float) : Unit = {
    doRender( e.asInstanceOf[LightningBoltEntity], x, y, z, yaw, partialTickTime )
  }

  private def doRender( e : LightningBoltEntity, x : Double, y : Double, z : Double, yaw : Float, partialTickTime : Float) : Unit = {

    this.bindEntityTexture(e);
    GL11.glPushMatrix();

    GL11.glTranslated(x, y, z)
    GL11.glRotatef(e.rotationYaw, 0.0f, 1.0f, 0.0f)
    GL11.glRotatef(-e.rotationPitch, 1.0f, 0.0f, 0.0f)
    GL11.glScalef(1.0f, 1.0f, 1.0f)
    GL11.glDisable(GL11.GL_LIGHTING)
    GL11.glDisable(GL11.GL_CULL_FACE)
    GL11.glEnable(GL11.GL_BLEND);
    if ( flash && e.renderCount % 3 == 0 ) {
      GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
    else {
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
    }
    GL11.glDepthMask(false);
    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glDisable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)

    val tes = Tessellator.instance

    tes.startDrawingQuads();
    for { index <- 0 until e.pointsList.size -1 } {
      val start = e.pointsList( index )
      val end = e.pointsList( index + 1 )

      val offset = 0.125D
      tes.addVertexWithUV( start.x - offset, start.y, start.z - 2, 0, 0);
      tes.addVertexWithUV( end.x - offset, end.y, end.z - 2, 0, 1);
      tes.addVertexWithUV( end.x + offset, end.y, end.z - 2, 1, 1);
      tes.addVertexWithUV( start.x + offset, start.y, start.z - 2, 1, 0);
      tes.addVertexWithUV( start.x, start.y - offset, start.z - 2, 0, 0);
      tes.addVertexWithUV( end.x, end.y - offset, end.z - 2, 0, 1);
      tes.addVertexWithUV( end.x, end.y + offset, end.z - 2, 1, 1);
      tes.addVertexWithUV( start.x, start.y + offset, start.z - 2, 1, 0);
    }
    tes.draw();

    OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit)
    GL11.glEnable(GL11.GL_TEXTURE_2D)
    OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit)
    GL11.glColor4f( 1.0f, 1.0f, 1.0f, 1.0f )
    GL11.glEnable(GL11.GL_LIGHTING)
    GL11.glPopMatrix()
    e.renderCount += 1
  }

  def getEntityTexture( e : Entity ) : ResourceLocation =
    new ResourceLocation( "rayguns", "textures/beams/lightning_beam.png" )
}