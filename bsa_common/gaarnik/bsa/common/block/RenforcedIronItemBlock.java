package gaarnik.bsa.common.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class RenforcedIronItemBlock extends ItemBlock {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public RenforcedIronItemBlock(int par1) {
		super(par1);
		
		this.setHasSubtypes(true);
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return RenforcedIronBlock.GAME_NAME[stack.getItemDamage()];
	}
	
	@Override
	public int getMetadata(int metadata) {
		return metadata;
	}

}
