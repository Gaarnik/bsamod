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
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return StructureIronBlock.GAME_NAME[stack.getItemDamage()];
	}
	
	@Override
	public int getMetadata(int metadata) {
		return metadata;
	}

}
