package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAMod;
import gaarnik.bsa.common.item.BSAItems;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class RenforcedStairsBlock extends BlockStairs {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	protected RenforcedStairsBlock(int id, Block block, int metadata) {
		super(id, block, metadata);
		
		this.setHardness(7.0f);
		this.setResistance(15.0f);
		this.setStepSound(Block.soundMetalFootstep);
		
		this.setLightOpacity(0);
		
		this.setCreativeTab(BSAMod.tabs);
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public static void registry() {
		int id = BSAMod.config.getBlock("RenforcedStairsBlock", BSABlocks.RENFORCED_STAIRS_ID).getInt();
		BSABlocks.renforcedStairslock = new RenforcedStairsBlock(id, BSABlocks.renforcedIronBlock, 0);
		BSABlocks.renforcedStairslock.setUnlocalizedName("RenforcedStairsBlock_0");
		
		GameRegistry.registerBlock(BSABlocks.renforcedStairslock, "renforcedStairslock");
		MinecraftForge.setBlockHarvestLevel(BSABlocks.renforcedStairslock, "pickaxe", 2);
		
		ItemStack stack = new ItemStack(BSABlocks.renforcedStairslock, 4);
		
		ItemStack ironStack = new ItemStack(Item.ingotIron, 1);
		ItemStack screwStack = new ItemStack(BSAItems.screwItem, 1);
		ItemStack cobbleStack = new ItemStack(Block.cobblestone, 1);

		GameRegistry.addRecipe(stack, new Object[] {
			"  X", " XX", "XXZ", 'X', ironStack, 'Z', screwStack
		});
		
		LanguageRegistry.addName(stack, "Renforced Stairs Block");
		
		//Dark version
		id = BSAMod.config.getBlock("RenforcedDarkStairsBlock", BSABlocks.RENFORCED_DARK_STAIRS_ID).getInt();
		BSABlocks.renforcedDarkStairslock = new RenforcedStairsBlock(id, BSABlocks.renforcedIronBlock, 1);
		BSABlocks.renforcedDarkStairslock.setUnlocalizedName("RenforcedStairsBlock_1");
		
		GameRegistry.registerBlock(BSABlocks.renforcedDarkStairslock, "renforcedDarkStairslock");
		MinecraftForge.setBlockHarvestLevel(BSABlocks.renforcedDarkStairslock, "pickaxe", 2);
		
		stack = new ItemStack(BSABlocks.renforcedDarkStairslock, 4);

		GameRegistry.addRecipe(stack, new Object[] {
			"  X", " CX", "XXZ", 'X', ironStack, 'C', cobbleStack, 'Z', screwStack
		});
		
		LanguageRegistry.addName(stack, "Renforced Dark Stairs Block");
	}

	// *******************************************************************
	@Override
	public int quantityDropped(Random rand) { return 4; }

}
