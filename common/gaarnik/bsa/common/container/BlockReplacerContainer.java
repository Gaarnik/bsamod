package gaarnik.bsa.common.container;

import gaarnik.bsa.common.slot.BlockReplacerSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class BlockReplacerContainer extends Container {
	// *******************************************************************

	// *******************************************************************
	
	// *******************************************************************
	public BlockReplacerContainer(EntityPlayer player) {
		this.addSlotToContainer(new BlockReplacerSlot(player.inventory, 0, 79, 34));
		
		int var3;

		for (var3 = 0; var3 < 3; ++var3)
			for (int var4 = 0; var4 < 9; ++var4)
				this.addSlotToContainer(new Slot(player.inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));

		for (var3 = 0; var3 < 9; ++var3)
			this.addSlotToContainer(new Slot(player.inventory, var3, 8 + var3 * 18, 142));
	}

	// *******************************************************************
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	// *******************************************************************
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		// TODO Auto-generated method stub
		return super.transferStackInSlot(par1EntityPlayer, par2);
	}

	// *******************************************************************

	// *******************************************************************

}
