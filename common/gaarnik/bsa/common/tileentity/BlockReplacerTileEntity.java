package gaarnik.bsa.common.tileentity;

import gaarnik.bsa.common.BSAMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BlockReplacerTileEntity extends TileEntity implements IInventory {
	// *******************************************************************

	// *******************************************************************
	private EntityPlayer player;

	private ItemStack targetStack;

	// *******************************************************************
	public BlockReplacerTileEntity(EntityPlayer player) {
		this.player = player;

		this.targetStack = this.loadFromBlockReplacerItemStack(player);
	}

	// *******************************************************************

	// *******************************************************************
	@Override
	public ItemStack decrStackSize(int slot, int count) {
		if(slot != 0)
			return null;

		if (this.targetStack == null)
			return null;

		ItemStack stack;

		if (this.targetStack.stackSize <= count) {
			stack = this.targetStack;
			this.targetStack = null;
		}
		else {
			stack = this.targetStack.splitStack(count);

			if (this.targetStack.stackSize == 0)
				this.targetStack = null;
		}

		this.saveIntoBlockReplacerItemStach(this.player);
		
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if(slot != 0)
			return null;
		
		return this.targetStack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if(slot != 0)
			return;

		this.targetStack = stack;
		this.saveIntoBlockReplacerItemStach(this.player);
	}

	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	// *******************************************************************
	private ItemStack loadFromBlockReplacerItemStack(EntityPlayer player) {
		ItemStack currentStack = player.inventory.getCurrentItem();

		if(currentStack.itemID != BSAMod.blockReplacerItem.itemID)
			return null;

		if(currentStack.hasTagCompound() == false)
			return null;

		NBTTagCompound tag = currentStack.getTagCompound();
		NBTTagCompound targetTag = (NBTTagCompound) tag.getTag("targetStack");

		if(targetTag == null)
			return null;

		return ItemStack.loadItemStackFromNBT(targetTag);
	}

	private void saveIntoBlockReplacerItemStach(EntityPlayer player) {
		ItemStack currentStack = player.inventory.getCurrentItem();
		
		if(currentStack == null)
			return;
		
		if(currentStack.itemID != BSAMod.blockReplacerItem.itemID)
			return;
		
		if(this.targetStack == null) {
			if(currentStack.hasTagCompound() == false)
				return;
			
			NBTTagCompound tag = currentStack.getTagCompound();
			if(tag.hasKey("targetStack"))
				tag.removeTag("targetStack");
		}
		else {
			if(currentStack.hasTagCompound() == false)
				currentStack.setTagCompound(new NBTTagCompound());

			NBTTagCompound tag = currentStack.getTagCompound();

			NBTTagCompound targetTag = new NBTTagCompound();
			this.targetStack.writeToNBT(targetTag);
			tag.setTag("targetStack", targetTag);
		}
	}

	// *******************************************************************
	@Override
	public int getSizeInventory() { return 1; }

	@Override
	public ItemStack getStackInSlot(int position) {
		if(position != 0)
			return null;

		return this.targetStack;
	}

	@Override
	public String getInvName() { return "container.BlockReplacer"; }

	@Override
	public int getInventoryStackLimit() { return 64; }

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) { return true; }
	
}
