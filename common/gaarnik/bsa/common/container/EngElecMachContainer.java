package gaarnik.bsa.common.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gaarnik.bsa.common.slot.EngMachSlot;
import gaarnik.bsa.common.tileentity.EngElecMachTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

public class EngElecMachContainer extends Container {
	// *******************************************************************
	private static final int UPDATE_STORED = 0;
	private static final int UPDATE_PROCESS = 1;

	// *******************************************************************
	private EngElecMachTileEntity tileEntity;
	
	private int energyStored;
	private int processTicks;

	// *******************************************************************
	public EngElecMachContainer(InventoryPlayer inv, EngElecMachTileEntity tileEntity) {
		this.tileEntity = tileEntity;
		
		this.addSlotToContainer(new Slot(tileEntity, 0, 24, 20));
		
		this.addSlotToContainer(new EngMachSlot(inv.player, tileEntity, 1, 97, 20));
		this.addSlotToContainer(new EngMachSlot(inv.player, tileEntity, 2, 126, 20));
		this.addSlotToContainer(new EngMachSlot(inv.player, tileEntity, 3, 97, 49));
		this.addSlotToContainer(new EngMachSlot(inv.player, tileEntity, 4, 126, 49));
		
		int var3;

		for (var3 = 0; var3 < 3; ++var3)
			for (int var4 = 0; var4 < 9; ++var4)
				this.addSlotToContainer(new Slot(inv, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));

		for (var3 = 0; var3 < 9; ++var3)
			this.addSlotToContainer(new Slot(inv, var3, 8 + var3 * 18, 142));
		
		this.energyStored = 0;
		this.processTicks = 0;
	}

	// *******************************************************************
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	// *******************************************************************
	@Override
	public void addCraftingToCrafters(ICrafting craft) {
		super.addCraftingToCrafters(craft);
		
		craft.sendProgressBarUpdate(this, UPDATE_STORED, this.tileEntity.getEnergyStored());
		craft.sendProgressBarUpdate(this, UPDATE_PROCESS, this.tileEntity.getProcessTicks());
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting crafter = (ICrafting) this.crafters.get(i);
            
            if (this.energyStored != this.tileEntity.getEnergyStored())
            	crafter.sendProgressBarUpdate(this, UPDATE_STORED, this.tileEntity.getEnergyStored());
            
            if (this.processTicks != this.tileEntity.getProcessTicks())
            	crafter.sendProgressBarUpdate(this, UPDATE_PROCESS, this.tileEntity.getProcessTicks());
        }

        this.energyStored = this.tileEntity.getEnergyStored();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int value) {
		if (par1 == UPDATE_STORED)
			this.tileEntity.setEnergyStored(value);
		else if (par1 == UPDATE_PROCESS)
			this.tileEntity.setProcessTicks(value);
	}
	
	// *******************************************************************

	// *******************************************************************

}
