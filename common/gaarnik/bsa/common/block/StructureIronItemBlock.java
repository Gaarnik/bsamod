package gaarnik.bsa.common.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class StructureIronItemBlock extends ItemBlock {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public StructureIronItemBlock(int id) {
		super(id);
		
		this.setHasSubtypes(true);
		this.setItemName("StructureBlock");
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
		return StructureIronBlock.GAME_NAME[itemstack.getItemDamage()];
	}

}
