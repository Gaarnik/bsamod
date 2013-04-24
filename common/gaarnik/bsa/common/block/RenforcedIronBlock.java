package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class RenforcedIronBlock extends BSABlock {
	// *******************************************************************
	private static final int[] RECEIPE_MIDDLE_BLOCKS = new int[] {
		Item.ingotIron.itemID, Block.cobblestone.blockID
	};

	public static final String[] GAME_NAME = new String[] {
		"Renforced Iron Block", "Renforced Dark Iron Block"
	};

	// *******************************************************************

	// *******************************************************************
	public RenforcedIronBlock(int id) {
		super(id, "RenforcedIronBlock");
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	@SuppressWarnings("deprecation")
	public static void registry() {
		int id = BSAMod.config.getBlock("RenforcedIronBlock", BSABlocks.RENFORCED_IRON_ID).getInt();
		BSAMod.renforcedIronBlock = new RenforcedIronBlock(id);

		GameRegistry.registerBlock(BSAMod.renforcedIronBlock, RenforcedIronItemBlock.class);
		MinecraftForge.setBlockHarvestLevel(BSAMod.renforcedIronBlock, "pickaxe", 2);
		
		for(int i=0;i<2;i++) {
			ItemStack stack = new ItemStack(BSAMod.renforcedIronBlock, 4, i);
			
			ItemStack ironStack = new ItemStack(Item.ingotIron, 1);
			ItemStack screwStack = new ItemStack(BSAMod.screwItem, 1);
			ItemStack middleStack = new ItemStack(RECEIPE_MIDDLE_BLOCKS[i], 1, 0);

			GameRegistry.addRecipe(stack, new Object[] {
				"YXY", "XZX", "YXY", 'X', ironStack, 'Y', screwStack, 'Z', middleStack
			});
			
			LanguageRegistry.addName(stack, GAME_NAME[stack.getItemDamage()]);
		}
	}

	// *******************************************************************
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return metadata;
	}

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}

}
