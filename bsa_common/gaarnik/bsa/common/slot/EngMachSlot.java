package gaarnik.bsa.common.slot;

import gaarnik.bsa.common.recipe.EngMachRecipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.MathHelper;

public class EngMachSlot extends Slot {
	// *******************************************************************

	// *******************************************************************
	private EntityPlayer thePlayer;
	private int field_75228_b;

	// *******************************************************************
	public EngMachSlot(EntityPlayer player, IInventory inv, int slotId, int par4, int par5) {
		super(inv, slotId, par4, par5);

		this.thePlayer = player;
	}

	// *******************************************************************
	protected void onCrafting(ItemStack par1ItemStack, int par2) {
		this.field_75228_b += par2;
		this.onCrafting(par1ItemStack);
	}

	protected void onCrafting(ItemStack stack) {
		stack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75228_b);

		if (!this.thePlayer.worldObj.isRemote) {
			int var2 = this.field_75228_b;
			float var3 = EngMachRecipe.smelting().getExperience(stack);
			int var4;

			if (var3 == 0.0F)
				var2 = 0;
			else if (var3 < 1.0F) {
				var4 = MathHelper.floor_float((float)var2 * var3);

				if (var4 < MathHelper.ceiling_float_int((float)var2 * var3) && (float)Math.random() < (float)var2 * var3 - (float)var4)
					++var4;

				var2 = var4;
			}

			while (var2 > 0) {
				var4 = EntityXPOrb.getXPSplit(var2);
				var2 -= var4;
				this.thePlayer.worldObj.spawnEntityInWorld(new EntityXPOrb(this.thePlayer.worldObj, this.thePlayer.posX, this.thePlayer.posY + 0.5D, this.thePlayer.posZ + 0.5D, var4));
			}
		}

		this.field_75228_b = 0;

		GameRegistry.onItemSmelted(thePlayer, stack);

		if (stack.itemID == Item.ingotIron.itemID)
			this.thePlayer.addStat(AchievementList.acquireIron, 1);

		if (stack.itemID == Item.fishCooked.itemID)
			this.thePlayer.addStat(AchievementList.cookFish, 1);
	}

	// *******************************************************************
	public ItemStack decrStackSize(int par1) {
		if (this.getHasStack())
			this.field_75228_b += Math.min(par1, this.getStack().stackSize);

		return super.decrStackSize(par1);
	}

	public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
		this.onCrafting(par2ItemStack);
		super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
	}

	// *******************************************************************

	// *******************************************************************
	public boolean isItemValid(ItemStack par1ItemStack) { return false; }

}
