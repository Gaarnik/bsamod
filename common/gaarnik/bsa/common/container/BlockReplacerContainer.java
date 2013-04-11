package gaarnik.bsa.common.container;

import gaarnik.bsa.common.recipe.EngMachRecipe;
import gaarnik.bsa.common.slot.BlockReplacerSlot;
import gaarnik.bsa.common.tileentity.BlockReplacerTileEntity;
import gaarnik.bsa.common.tileentity.EngMachTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class BlockReplacerContainer extends Container {
	// *******************************************************************

	// *******************************************************************
	
	// *******************************************************************
	public BlockReplacerContainer(EntityPlayer player, BlockReplacerTileEntity entity) {
		this.addSlotToContainer(new BlockReplacerSlot(entity, 0, 80, 36));
		
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
	public ItemStack transferStackInSlot(EntityPlayer player, int par2) {
		ItemStack var3 = null;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if (var4 != null && var4.getHasStack()) {
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

			if (par2 == 2) {
				if (!this.mergeItemStack(var5, 3, 39, true))
					return null;

				var4.onSlotChange(var5, var3);
			}
			else if (par2 != 1 && par2 != 0) {
				if (EngMachRecipe.smelting().getSmeltingResult(var5) != null)
					if (!this.mergeItemStack(var5, 0, 1, false))
						return null;
				else if (EngMachTileEntity.isItemFuel(var5)) {
					if (!this.mergeItemStack(var5, 1, 2, false))
						return null;
				}
				else if (par2 >= 3 && par2 < 30) {
					if (!this.mergeItemStack(var5, 30, 39, false))
						return null;
				}
				else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(var5, 3, 30, false))
					return null;
			}
			else if (!this.mergeItemStack(var5, 3, 39, false))
				return null;

			if (var5.stackSize == 0)
				var4.putStack((ItemStack)null);
			else
				var4.onSlotChanged();

			if (var5.stackSize == var3.stackSize)
				return null;

			var4.onPickupFromSlot(player, var5);
		}

		return var3;
	}

	// *******************************************************************

	// *******************************************************************

}
