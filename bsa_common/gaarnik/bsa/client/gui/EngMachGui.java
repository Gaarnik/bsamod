package gaarnik.bsa.client.gui;

import gaarnik.bsa.common.container.EngMachContainer;
import gaarnik.bsa.common.tileentity.EngMachTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EngMachGui extends GuiContainer {
	// *******************************************************************
	private static final ResourceLocation background = new ResourceLocation("bsa", "textures/gui/engmach_gui.png");

	// *******************************************************************
	private EngMachTileEntity engMachTileEntity;

	// *******************************************************************
	public EngMachGui(InventoryPlayer player, EngMachTileEntity tileEntity) {
        super(new EngMachContainer(player, tileEntity));
        this.engMachTileEntity = tileEntity;
    }
	// *******************************************************************
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRenderer.drawString(StatCollector.translateToLocal("Engineering Machine"), 40, 6, 4210752);
    }
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        int var7;
        
        if (this.engMachTileEntity.isBurning()) {
            var7 = this.engMachTileEntity.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(var5 + 38, var6 + 36 + 12 - var7, 176, 12 - var7, 14, var7 + 2);
        }

        var7 = this.engMachTileEntity.getCookProgressScaled(24);
        this.drawTexturedModalRect(var5 + 61, var6 + 34, 176, 14, var7 + 1, 16);
    }

	// *******************************************************************

	// *******************************************************************

}
