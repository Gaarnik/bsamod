package gaarnik.bsa.common.container;

import gaarnik.bsa.common.recipe.EngMachRecipe;
import gaarnik.bsa.common.slot.EngMachSlot;
import gaarnik.bsa.common.tileentity.EngMachTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EngMachContainer extends Container {
	// *******************************************************************

	// *******************************************************************
	private EngMachTileEntity engMachTileEntity;
	private int lastCookTime = 0;
	private int lastBurnTime = 0;
	private int lastItemBurnTime = 0;

	// *******************************************************************
	public EngMachContainer(InventoryPlayer player, EngMachTileEntity tileEntity) {
		this.engMachTileEntity = tileEntity;

		this.addSlotToContainer(new Slot(tileEntity, 0, 37, 17));
		this.addSlotToContainer(new Slot(tileEntity, 1, 37, 53));

		this.addSlotToContainer(new EngMachSlot(player.player, tileEntity, 2, 97, 20));
		this.addSlotToContainer(new EngMachSlot(player.player, tileEntity, 3, 126, 20));
		this.addSlotToContainer(new EngMachSlot(player.player, tileEntity, 4, 97, 49));
		this.addSlotToContainer(new EngMachSlot(player.player, tileEntity, 5, 126, 49));

		int var3;

		for (var3 = 0; var3 < 3; ++var3)
			for (int var4 = 0; var4 < 9; ++var4)
				this.addSlotToContainer(new Slot(player, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));

		for (var3 = 0; var3 < 9; ++var3)
			this.addSlotToContainer(new Slot(player, var3, 8 + var3 * 18, 142));
	}

	// *******************************************************************
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.engMachTileEntity.isUseableByPlayer(player);
	}

	// *******************************************************************
	public void addCraftingToCrafters(ICrafting craft) {
		super.addCraftingToCrafters(craft);

		craft.sendProgressBarUpdate(this, 0, this.engMachTileEntity.engMachCookTime);
		craft.sendProgressBarUpdate(this, 1, this.engMachTileEntity.engMachBurnTime);
		craft.sendProgressBarUpdate(this, 2, this.engMachTileEntity.currentItemBurnTime);
	}

	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int var1 = 0; var1 < this.crafters.size(); ++var1) {
			ICrafting var2 = (ICrafting)this.crafters.get(var1);

			if (this.lastCookTime != this.engMachTileEntity.engMachCookTime)
				var2.sendProgressBarUpdate(this, 0, this.engMachTileEntity.engMachCookTime);

			if (this.lastBurnTime != this.engMachTileEntity.engMachBurnTime)
				var2.sendProgressBarUpdate(this, 1, this.engMachTileEntity.engMachBurnTime);

			if (this.lastItemBurnTime != this.engMachTileEntity.currentItemBurnTime)
				var2.sendProgressBarUpdate(this, 2, this.engMachTileEntity.currentItemBurnTime);
		}

		this.lastCookTime = this.engMachTileEntity.engMachCookTime;
		this.lastBurnTime = this.engMachTileEntity.engMachBurnTime;
		this.lastItemBurnTime = this.engMachTileEntity.currentItemBurnTime;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0)
			this.engMachTileEntity.engMachCookTime = par2;

		if (par1 == 1)
			this.engMachTileEntity.engMachBurnTime = par2;

		if (par1 == 2)
			this.engMachTileEntity.currentItemBurnTime = par2;
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int position) {
		ItemStack stack = null;
		Slot sourceSlot = (Slot) this.inventorySlots.get(position);

		if (sourceSlot != null && sourceSlot.getHasStack()) {
			ItemStack sourceStack = sourceSlot.getStack();
			stack = sourceStack.copy();

			switch(position) {
			//from slots ton inventory
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				if (!this.mergeItemStack(sourceStack, 0, 37, true))
					return null;
				break;

			//from inventory to slots
			default:
				if(EngMachTileEntity.isItemFuel(sourceStack))
					if (!this.mergeItemStack(sourceStack, 1, 2, false))
						return null;
				if (EngMachRecipe.smelting().getSmeltingResult(sourceStack) != null)
					if (!this.mergeItemStack(sourceStack, 0, 1, false))
						return null;
				break;

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
