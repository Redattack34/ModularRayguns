package com.castlebravostudios.rayguns.mod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import com.castlebravostudios.rayguns.gui.GuiBasic;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {

    public static String ITEMS_PNG = "/ModularRayguns/textures/items.png";
    public static String BLOCK_PNG = "/ModularRayguns/textures/block.png";

    public void registerRenderers() {
        //Do Nothing.
    }

    @Override
    public Object getClientGuiElement( int ID, EntityPlayer player, World world,
        int x, int y, int z ) {
        return null;
    }

    @Override
    public Object getServerGuiElement( int ID, EntityPlayer player, World world,
        int x, int y, int z ) {
        if ( ID == GuiBasic.GUI_ID( ) ) {
            return new GuiBasic();
        }
        return null;
    }
}
