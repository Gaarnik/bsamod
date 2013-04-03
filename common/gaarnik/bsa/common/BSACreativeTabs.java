package gaarnik.bsa.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class BSACreativeTabs extends CreativeTabs {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public BSACreativeTabs(String name) {
		super(name);
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(BSAMod.renforcedIronBlock, 1, 0);
	}

}
