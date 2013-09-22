package gaarnik.bsa.client.gui;

import gaarnik.bsa.common.container.EngElecMachContainer;
import gaarnik.bsa.common.tileentity.EngElecMachTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class EngElecMachGui extends GuiContainer {
	// *******************************************************************
	private static final ResourceLocation background = new ResourceLocation("bsa", "textures/gui/engelecmach_gui.png");

	// *******************************************************************
	private EngElecMachTileEntity tileEntity;

	private int containerWidth;
	private int containerHeight;

	// *******************************************************************
	public EngElecMachGui(InventoryPlayer inv, EngElecMachTileEntity tileEntity) {
		super(new EngElecMachContainer(inv, tileEntity));
		
		this.tileEntity = tileEntity;
	}

	// *******************************************************************
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.func_110434_K().func_110577_a(background);
		
		containerWidth = (this.width - this.xSize) / 2;
		containerHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);

		//draw stored energy
		int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        
        double ratio = (double) 47 / EngElecMachTileEntity.MAX_ENERGY;
        
        int stored = (int) (this.tileEntity.getEnergyStored() * ratio);
		this.drawTexturedModalRect(x + 21, y + 46, 176, 31, stored, 7);
		
		//draw process progress
        ratio = (double) 21 / EngElecMachTileEntity.MAX_PROCESS_TICKS;
        
        int process = (int) (this.tileEntity.getProcessTicks() * ratio);
		this.drawTexturedModalRect(x + 47, y + 19, 176, 14, process, 17);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString(StatCollector.translateToLocal("Electrical Eng. Machine"), 30, 6, 4210752);
		
		String stored = this.tileEntity.getEnergyStored() + " / " + EngElecMachTileEntity.MAX_ENERGY + " EU";
		this.fontRenderer.drawString(StatCollector.translateToLocal(stored), 15, 60, 4210752);
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************

}
