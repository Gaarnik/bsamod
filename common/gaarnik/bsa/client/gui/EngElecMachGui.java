package gaarnik.bsa.client.gui;

import gaarnik.bsa.client.BSAClientProxy;

import gaarnik.bsa.common.container.EngElecMachContainer;
import gaarnik.bsa.common.tileentity.EngElecMachTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import universalelectricity.core.electricity.ElectricInfo.ElectricUnit;
import universalelectricity.core.electricity.ElectricInfo.MeasurementUnit;

public class EngElecMachGui extends GuiContainer {
	// *******************************************************************

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
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString(this.tileEntity.getInvName(), 45, 6, 4210752);
		this.fontRenderer.drawString("Smelting:", 10, 28, 4210752);
		this.fontRenderer.drawString("Battery:", 10, 53, 4210752);
		String displayText = "";

		if (this.tileEntity.isDisabled())
			displayText = "Disabled!";
		else if (this.tileEntity.processTicks > 0)
			displayText = "Smelting";
		else
			displayText = "Idle";

		this.fontRenderer.drawString("Status: " + displayText, 82, 45, 4210752);
		this.fontRenderer.drawString(getDisplay(EngElecMachTileEntity.WATTS_PER_TICK * 20, ElectricUnit.WATT), 82, 56, 4210752);
		this.fontRenderer.drawString(getDisplay(this.tileEntity.getVoltage(), ElectricUnit.VOLTAGE), 82, 68, 4210752);
		this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		int texture = this.mc.renderEngine.getTexture(BSAClientProxy.ENGMACH_GUI);
		this.mc.renderEngine.bindTexture(texture);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		containerWidth = (this.width - this.xSize) / 2;
		containerHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);

		if (this.tileEntity.processTicks > 0) {
			int scale = (int) (((double) this.tileEntity.processTicks / (double) EngElecMachTileEntity.PROCESS_TIME_REQUIRED) * 23);
			this.drawTexturedModalRect(containerWidth + 77, containerHeight + 24, 176, 0, 23 - scale, 20);
		}
	}

	// *******************************************************************
	private String getDisplay(double value, ElectricUnit unit, int decimalPlaces, boolean isShort) {
		String unitName = unit.name;

		if (isShort)
			unitName = unit.symbol;
		else if (value > 1)
			unitName = unit.getPlural();

		if (value == 0)
			return value + " " + unitName;

		if (value <= MeasurementUnit.MILLI.value)
			return roundDecimals(MeasurementUnit.MICRO.process(value), decimalPlaces) + " " + MeasurementUnit.MICRO.getName(isShort) + unitName;

		if (value < 1)
			return roundDecimals(MeasurementUnit.MILLI.process(value), decimalPlaces) + " " + MeasurementUnit.MILLI.getName(isShort) + unitName;

		if (value > MeasurementUnit.MEGA.value)
			return roundDecimals(MeasurementUnit.MEGA.process(value), decimalPlaces) + " " + MeasurementUnit.MEGA.getName(isShort) + unitName;

		if (value > MeasurementUnit.KILO.value)
			return roundDecimals(MeasurementUnit.KILO.process(value), decimalPlaces) + " " + MeasurementUnit.KILO.getName(isShort) + unitName;

		return roundDecimals(value, decimalPlaces) + " " + unitName;
	}
	
	private String getDisplay(double value, ElectricUnit unit) {
		return getDisplay(value, unit, 2, false);
	}
	
	private double roundDecimals(double d, int decimalPlaces) {
		int j = (int) (d * Math.pow(10, decimalPlaces));
		return j / (double) Math.pow(10, decimalPlaces);
	}

	// *******************************************************************

	// *******************************************************************

}
