package gaarnik.bsa.client.gui;

import gaarnik.bsa.client.BSAClientProxy;
import gaarnik.bsa.common.container.EngElecMachContainer2;
import gaarnik.bsa.common.tileentity.EngElecMachTileEntity2;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class EngElecMachGui2 extends GuiContainer {
	// *******************************************************************

	// *******************************************************************
	private EngElecMachTileEntity2 tileEntity;

	private int containerWidth;
	private int containerHeight;

	// *******************************************************************
	public EngElecMachGui2(InventoryPlayer inv, EngElecMachTileEntity2 tileEntity) {
		super(new EngElecMachContainer2(inv, tileEntity));
		
		this.tileEntity = tileEntity;
	}

	// *******************************************************************
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		int texture = this.mc.renderEngine.getTexture(BSAClientProxy.ELECENGMACH_GUI);
		this.mc.renderEngine.bindTexture(texture);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		containerWidth = (this.width - this.xSize) / 2;
		containerHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);

		//draw stored energy
		int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        
        double ratio = (double) 47 / EngElecMachTileEntity2.MAX_ENERGY;
        
        int stored = (int) (this.tileEntity.getEnergyStored() * ratio);
		this.drawTexturedModalRect(x + 21, y + 46, 176, 31, stored, 7);
		
		//draw process progress
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString(StatCollector.translateToLocal("Electrical Eng. Machine"), 30, 6, 4210752);
		
		String stored = this.tileEntity.getEnergyStored() + " / " + EngElecMachTileEntity2.MAX_ENERGY + " EU";
		
		this.fontRenderer.drawString(StatCollector.translateToLocal(stored), 15, 60, 4210752);
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************

}
