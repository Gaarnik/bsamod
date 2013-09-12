package gaarnik.bsa.common.tileentity;

import gaarnik.bsa.common.block.EngMachBlock;
import gaarnik.bsa.common.recipe.EngMachRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EngMachTileEntity extends TileEntity implements ISidedInventory {
	// *******************************************************************
	private static final int COOK_TIME = 200;

	// *******************************************************************
	private ItemStack[] engMachItemStacks = new ItemStack[6];

	public int engMachBurnTime = 0;
	public int engMachCookTime = 0;

	public int currentItemBurnTime = 0;

	// *******************************************************************
	public void updateEntity() {
		boolean var1 = this.engMachBurnTime > 0;
		boolean var2 = false;

		if (this.engMachBurnTime > 0)
			--this.engMachBurnTime;

		if (!this.worldObj.isRemote) {
			if (this.engMachBurnTime == 0 && this.canSmelt())
			{
				this.currentItemBurnTime = this.engMachBurnTime = getItemBurnTime(this.engMachItemStacks[1]);

				if (this.engMachBurnTime > 0) {
					var2 = true;

					if (this.engMachItemStacks[1] != null) {
						--this.engMachItemStacks[1].stackSize;

						if (this.engMachItemStacks[1].stackSize == 0)
							this.engMachItemStacks[1] = this.engMachItemStacks[1].getItem().getContainerItemStack(engMachItemStacks[1]);
					}
				}
			}

			if (this.isBurning() && this.canSmelt()) {
				++this.engMachCookTime;

				if (this.engMachCookTime == COOK_TIME) {
					this.engMachCookTime = 0;
					this.smeltItem();
					var2 = true;
				}
			}
			else
				this.engMachCookTime = 0;

			if (var1 != this.engMachBurnTime > 0)
			{
				var2 = true;
				EngMachBlock.updateBlockState(this.engMachBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}

		if (var2)
			this.onInventoryChanged();
	}

	public void smeltItem() {
		if (this.canSmelt()) {
			ItemStack stack = EngMachRecipe.smelting().getSmeltingResult(this.engMachItemStacks[0]);

			for(int slot=2;slot<=5;slot++) {
				if(this.isSlotFull(slot, stack) == true)
					continue;

				boolean smelted = false;

				if (this.engMachItemStacks[slot] == null) {
					this.engMachItemStacks[slot] = stack.copy();
					smelted = true;
				}
				else if (this.engMachItemStacks[slot].isItemEqual(stack)) {
					engMachItemStacks[slot].stackSize += stack.stackSize;
					smelted = true;
				}

				if(smelted == true) {
					--this.engMachItemStacks[0].stackSize;

					if (this.engMachItemStacks[0].stackSize <= 0)
						this.engMachItemStacks[0] = null;

					return;
				}
			}
		}
	}

	public static int getItemBurnTime(ItemStack stack) {
		if (stack == null)
			return 0;

		int var1 = stack.getItem().itemID;
		Item var2 = stack.getItem();

		if (stack.getItem() instanceof ItemBlock && Block.blocksList[var1] != null) {
			Block var3 = Block.blocksList[var1];

			if (var3 == Block.woodSingleSlab)
				return 150;

			if (var3.blockMaterial == Material.wood)
				return 300;
		}

		if (var2 instanceof ItemTool && ((ItemTool) var2).getToolMaterialName().equals("WOOD")) return 200;
		//if (var2 instanceof ItemSword && ((ItemSword) var2).func_77825_f().equals("WOOD")) return 200;
		//if (var2 instanceof ItemHoe && ((ItemHoe) var2).func_77842_f().equals("WOOD")) return 200;
		if (var1 == Item.stick.itemID) return 100;
		if (var1 == Item.coal.itemID) return 1600;
		if (var1 == Item.bucketLava.itemID) return 20000;
		if (var1 == Block.sapling.blockID) return 100;
		if (var1 == Item.blazeRod.itemID) return 2400;

		return GameRegistry.getFuelValue(stack);
	}

	public ItemStack decrStackSize(int position, int count) {
		if (this.engMachItemStacks[position] != null) {
			ItemStack stack;

			if (this.engMachItemStacks[position].stackSize <= count) {
				stack = this.engMachItemStacks[position];
				this.engMachItemStacks[position] = null;

				return stack;
			}
			else {
				stack = this.engMachItemStacks[position].splitStack(count);

				if (this.engMachItemStacks[position].stackSize == 0)
					this.engMachItemStacks[position] = null;

				return stack;
			}
		}
		else
			return null;
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1) {
		return this.engMachCookTime * par1 / COOK_TIME;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1) {
		if (this.currentItemBurnTime == 0)
			this.currentItemBurnTime = 200;

		return this.engMachBurnTime * par1 / this.currentItemBurnTime;
	}

	public void openChest() {}

	public void closeChest() {}

	// *******************************************************************
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);

		NBTTagList var2 = NBT.getTagList("Items1");
		this.engMachItemStacks = new ItemStack[this.getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
			byte var5 = var4.getByte("Slot1");

			if (var5 >= 0 && var5 < this.engMachItemStacks.length)
				this.engMachItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
		}

		this.engMachBurnTime = NBT.getShort("BurnTime1");
		this.engMachCookTime = NBT.getShort("CookTime1");
		this.currentItemBurnTime = getItemBurnTime(this.engMachItemStacks[1]);
	}

	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);

		NBT.setShort("BurnTime1", (short)this.engMachBurnTime);
		NBT.setShort("CookTime1", (short)this.engMachCookTime);
		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.engMachItemStacks.length; ++var3) {
			if (this.engMachItemStacks[var3] != null) {
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot1", (byte)var3);
				this.engMachItemStacks[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		NBT.setTag("Items1", var2);
	}

	// *******************************************************************
	private boolean canSmelt() {
		if (this.engMachItemStacks[0] == null)
			return false;

		ItemStack stack = EngMachRecipe.smelting().getSmeltingResult(this.engMachItemStacks[0]);

		if (stack == null) return false;

		for(int i=2;i<=5;i++)
			if(this.isSlotFull(i, stack) == false)
				return true;

		return false;
	}

	private boolean isSlotFull(int slot, ItemStack stack) {
		if (this.engMachItemStacks[slot] == null) return false;
		if (!this.engMachItemStacks[slot].isItemEqual(stack)) return false;

		int result = engMachItemStacks[slot].stackSize + stack.stackSize;
		return !(result <= getInventoryStackLimit() && result <= stack.getMaxStackSize());
	}

	// *******************************************************************
	public ItemStack getStackInSlotOnClosing(int position) {
		if (this.engMachItemStacks[position] != null) {
			ItemStack stack = this.engMachItemStacks[position];
			this.engMachItemStacks[position] = null;

			return stack;
		}
		else
			return null;
	}

	public void setInventorySlotContents(int position, ItemStack stack) {
		this.engMachItemStacks[position] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();
	}

	public int getSizeInventory() { return this.engMachItemStacks.length; }

	public ItemStack getStackInSlot(int position) { return this.engMachItemStacks[position]; }

	public String getInvName() { return "container.EngMach"; }

	public int getInventoryStackLimit() { return 64; }

	public boolean isBurning() { return this.engMachBurnTime > 0; }

	public static boolean isItemFuel(ItemStack par0ItemStack) { return getItemBurnTime(par0ItemStack) > 0; }

	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	public int getSizeInventorySide(ForgeDirection side) { return 1; }

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		int[] slots = new int[1];
		
		if (side == 1)
			slots[0] = 1;
		else
			slots[0] = 0;

		return slots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInvNameLocalized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}

}
