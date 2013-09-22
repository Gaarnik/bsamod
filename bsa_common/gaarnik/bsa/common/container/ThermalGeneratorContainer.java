package gaarnik.bsa.common.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gaarnik.bsa.common.tileentity.ThermalGeneratorTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

public class ThermalGeneratorContainer extends Container {
	// *******************************************************************
	private static final int UPDATE_STORED = 0;
	private static final int UPDATE_SOURCES = 1;

	// *******************************************************************
	private ThermalGeneratorTileEntity entity;

	private int energyStored;
	private int sourcesCount;

	// *******************************************************************
	public ThermalGeneratorContainer(InventoryPlayer inventory, ThermalGeneratorTileEntity tileEntity) {
		this.entity = tileEntity;

		int var3;

		for (var3 = 0; var3 < 3; ++var3)
			for (int var4 = 0; var4 < 9; ++var4)
				this.addSlotToContainer(new Slot(inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));

		for (var3 = 0; var3 < 9; ++var3)
			this.addSlotToContainer(new Slot(inventory, var3, 8 + var3 * 18, 142));
	}

	// *******************************************************************
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.entity.isUseableByPlayer(player);
	}

	// *******************************************************************
	@Override
	public void addCraftingToCrafters(ICrafting craft) {
		super.addCraftingToCrafters(craft);

		craft.sendProgressBarUpdate(this, UPDATE_STORED, this.entity.getEnergyStored());
		craft.sendProgressBarUpdate(this, UPDATE_SOURCES, this.entity.getSourcesCount());
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting crafter = (ICrafting) this.crafters.get(i);

			if (this.energyStored != this.entity.getEnergyStored())
				crafter.sendProgressBarUpdate(this, UPDATE_STORED, this.entity.getEnergyStored());

			if (this.sourcesCount != this.entity.getSourcesCount())
				crafter.sendProgressBarUpdate(this, UPDATE_SOURCES, this.entity.getSourcesCount());
		}

		this.energyStored = this.entity.getEnergyStored();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int value) {

		switch(id) {

		case UPDATE_STORED:
			this.entity.setEnergyStored(this.entity.getEnergyStored() & -65536 | value);
			break;

		case UPDATE_SOURCES:
			this.entity.setSourcesCount(value);
			break;

		}
	}

	// *******************************************************************

	// *******************************************************************

}
