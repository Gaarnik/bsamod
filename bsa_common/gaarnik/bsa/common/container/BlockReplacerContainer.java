package gaarnik.bsa.common.container;

import gaarnik.bsa.common.slot.BlockReplacerSlot;
import gaarnik.bsa.common.tileentity.BlockReplacerTileEntity;
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
	public ItemStack transferStackInSlot(EntityPlayer player, int position) {
		ItemStack stack = null;
		Slot sourceSlot = (Slot) this.inventorySlots.get(position);

		if (sourceSlot != null && sourceSlot.getHasStack()) {
			ItemStack sourceStack = sourceSlot.getStack();
			stack = sourceStack.copy();

			if(position == 0) { //from slot to inventory
				if (!this.mergeItemStack(sourceStack, 0, 37, true))
					return null;
			}
			else { //from inventory to slot
				if (!this.mergeItemStack(sourceStack, 0, 1, false))
					return null;
			}

			if (sourceStack.stackSize == 0)
				sourceSlot.putStack((ItemStack)null);
			else
				sourceSlot.onSlotChanged();

			if (sourceStack.stackSize == stack.stackSize)
				return null;

			sourceSlot.onPickupFromSlot(player, sourceStack);
		}

		return stack;
	}

	// *******************************************************************

	// *******************************************************************

}
