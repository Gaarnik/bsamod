package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class GridIronBlock extends BSABlock {
	// *******************************************************************
	private static final int[] RECEIPE_MIDDLE_BLOCKS = new int[] {
		Block.fenceIron.blockID, Block.cobblestone.blockID
	};

	public static final String[] GAME_NAME = new String[] {
		"Grid Iron Block", "Grid Dark Iron Block"
	};

	// *******************************************************************

	// *******************************************************************
	public GridIronBlock(int id) {
		super(id, "GridIronBlock");
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	@SuppressWarnings("deprecation")
	public static void registry() {
		int id = BSAMod.config.getBlock("GridIronBlock", BSABlocks.GRID_IRON_ID).getInt();
		BSAMod.gridIronBlock = new GridIronBlock(id);

		GameRegistry.registerBlock(BSAMod.gridIronBlock, GridIronItemBlock.class);
		MinecraftForge.setBlockHarvestLevel(BSAMod.gridIronBlock, "pickaxe", 2);
		
		for(int i=0;i<2;i++) {
			ItemStack stack = new ItemStack(BSAMod.gridIronBlock, 1, i);
			
			ItemStack ironStack = new ItemStack(Item.ingotIron, 1);
			ItemStack screwStack = new ItemStack(BSAMod.screwItem, 1);
			ItemStack fenceStack = new ItemStack(Block.fenceIron, 1);
			ItemStack middleStack = new ItemStack(RECEIPE_MIDDLE_BLOCKS[i], 1, 0);

			GameRegistry.addRecipe(stack, new Object[] {
				"XYX", "ZAZ", "XYX", 'X', screwStack, 'Y', ironStack, 'Z', fenceStack, 'A', middleStack
			});
			
			LanguageRegistry.addName(stack, GAME_NAME[stack.getItemDamage()]);
		}
	}

	// *******************************************************************
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return 6 + metadata;
	}

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}

}
