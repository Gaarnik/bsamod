package gaarnik.bsa.client.gui;

import org.lwjgl.opengl.GL11;

import gaarnik.bsa.common.container.ElevatorConContainer;
import gaarnik.bsa.common.tileentity.ElevatorControllerTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class ElevatorConGUI extends GuiContainer {
	// *******************************************************************
	private static final ResourceLocation background = new ResourceLocation("bsa", "textures/gui/elevator_gui.png");

	// *******************************************************************
	private ElevatorControllerTileEntity tileEntity;

	private int containerWidth;
	private int containerHeight;

	// *******************************************************************
	public ElevatorConGUI(InventoryPlayer player, ElevatorControllerTileEntity tileEntity) {
		super(new ElevatorConContainer(player, tileEntity));
		
		this.tileEntity = tileEntity;
	}

	// *******************************************************************
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(background);
		
		containerWidth = (this.width - this.xSize) / 2;
		containerHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		// TODO Auto-generated method stub
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************

}
