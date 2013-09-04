package gaarnik.bsa.common;

import gaarnik.bsa.common.block.BSABlocks;
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
		return new ItemStack(BSABlocks.renforcedIronBlock, 1, 0);
	}

}
