package gaarnik.bsa.common.container;

import gaarnik.bsa.common.tileentity.EngElecMachTileEntity2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class EngElecMachContainer2 extends Container {
	// *******************************************************************

	// *******************************************************************
	private EngElecMachTileEntity2 tileEntity;
	
	//private int energyStored;

	// *******************************************************************
	public EngElecMachContainer2(InventoryPlayer inv, EngElecMachTileEntity2 tileEntity) {
		this.tileEntity = tileEntity;
		this.energyStored = 0;
	}

	// *******************************************************************
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	// *******************************************************************
	/*@Override
	public void addCraftingToCrafters(ICrafting craft) {
		super.addCraftingToCrafters(craft);
		
		craft.sendProgressBarUpdate(this, 0, this.tileEntity.getEnergyStored());
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting crafter = (ICrafting)this.crafters.get(i);
            
            if (this.energyStored != this.tileEntity.getEnergyStored())
            	crafter.sendProgressBarUpdate(this, 0, this.tileEntity.getEnergyStored());
        }

        this.energyStored = this.tileEntity.getEnergyStored();
	}*/

	// *******************************************************************

	// *******************************************************************

}
