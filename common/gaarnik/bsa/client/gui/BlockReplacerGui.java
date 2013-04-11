package gaarnik.bsa.client.gui;

import org.lwjgl.opengl.GL11;

import gaarnik.bsa.client.BSAClientProxy;
import gaarnik.bsa.common.container.BlockReplacerContainer;
import gaarnik.bsa.common.tileentity.BlockReplacerTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class BlockReplacerGui extends GuiContainer {
	// *******************************************************************

	// *******************************************************************
	private int containerWidth;
	private int containerHeight;

	// *******************************************************************
	public BlockReplacerGui(EntityPlayer player, BlockReplacerTileEntity entity) {
		super(new BlockReplacerContainer(player, entity));
	}

	// *******************************************************************
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		int texture = this.mc.renderEngine.getTexture(BSAClientProxy.BLOCKREPLACER_GUI);
		this.mc.renderEngine.bindTexture(texture);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		containerWidth = (this.width - this.xSize) / 2;
		containerHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString(StatCollector.translateToLocal("Block Replacer"), 51, 6, 4210752);
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************

}
