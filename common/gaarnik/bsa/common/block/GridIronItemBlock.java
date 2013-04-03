package gaarnik.bsa.common.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class GridIronItemBlock extends ItemBlock {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public GridIronItemBlock(int id) {
		super(id);
		
		this.setHasSubtypes(true);
		this.setItemName("GridIronBlock");
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
		return GridIronBlock.GAME_NAME[itemstack.getItemDamage()];
	}

}
