package gaarnik.bsa.common.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class RenforcedIronItemBlock extends ItemBlock {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public RenforcedIronItemBlock(int id) {
		super(id);
		
		this.setHasSubtypes(true);
		this.setItemName("RenforcedIronBlock");
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	@Override
	public int getMetadata(int damageValue) {
		return damageValue;
	}
	
	public String getItemNameIS(ItemStack itemstack) {
		return RenforcedIronBlock.GAME_NAME[itemstack.getItemDamage()];
	}

}
