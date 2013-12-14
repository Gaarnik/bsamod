package gaarnik.bsa.client.gui;

import gaarnik.bsa.common.block.ThermalGeneratorMachBlock;
import gaarnik.bsa.common.container.ThermalGeneratorContainer;
import gaarnik.bsa.common.tileentity.EngElecMachTileEntity;
import gaarnik.bsa.common.tileentity.ThermalGeneratorTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class ThermalGeneratorGui extends GuiContainer {
	// *******************************************************************
	private static final ResourceLocation background = new ResourceLocation("bsa", "textures/gui/thermal_generator_gui.png");

	// *******************************************************************
	private ThermalGeneratorTileEntity thermalGenTileEntity;

	// *******************************************************************
	public ThermalGeneratorGui(InventoryPlayer player, ThermalGeneratorTileEntity tileEntity) {
		super(new ThermalGeneratorContainer(player, tileEntity));
		
        this.thermalGenTileEntity = tileEntity;
    }

	// *******************************************************************
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRenderer.drawString(StatCollector.translateToLocal(ThermalGeneratorMachBlock.GAME_NAME), 40, 6, 4210752);

		String stored = this.thermalGenTileEntity.getEnergyStored() + " / " + ThermalGeneratorTileEntity.MAX_ENERGY + " EU";
		this.fontRenderer.drawString(StatCollector.translateToLocal(stored), 55, 40, 4210752);
		
		int sourcesCount = this.thermalGenTileEntity.getSourcesCount();
		String sources = "Lava source: " + sourcesCount + " ( " + (sourcesCount * ThermalGeneratorTileEntity.EU_PER_LAVA_SOURCE) + " EU/t)";
		this.fontRenderer.drawString(StatCollector.translateToLocal(sources), 20, 60, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        
        double ratio = (double) 112 / EngElecMachTileEntity.MAX_ENERGY;
        
        int stored = (int) (this.thermalGenTileEntity.getEnergyStored() * ratio);
		this.drawTexturedModalRect(x + 32, y + 24, 0, 166, stored, 7);
	}
	
	// *******************************************************************

	// *******************************************************************

}
