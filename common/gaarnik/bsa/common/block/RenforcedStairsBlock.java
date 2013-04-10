package gaarnik.bsa.common.block;

import gaarnik.bsa.client.BSAClientProxy;
import gaarnik.bsa.common.BSAMod;
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
	private int textureOffset;

	// *******************************************************************
	protected RenforcedStairsBlock(int id, Block block, int textureOffset) {
		super(id, block, 0);

		this.textureOffset = textureOffset;
		
		this.setHardness(7.0f);
		this.setResistance(15.0f);
		this.setStepSound(Block.soundMetalFootstep);
		
		this.setRequiresSelfNotify();
		this.setLightOpacity(0);
		
		this.setCreativeTab(BSAMod.tabs);
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public static void registry() {
		int id = BSAMod.config.getBlock("RenforcedStairsBlock", BSABlocks.RENFORCED_STAIRS_ID).getInt();
		BSAMod.renforcedStairslock = new RenforcedStairsBlock(id, BSAMod.renforcedIronBlock, 0);
		BSAMod.renforcedStairslock.setBlockName("RenforcedStairsBlock");
		
		GameRegistry.registerBlock(BSAMod.renforcedStairslock, "renforcedStairslock");
		MinecraftForge.setBlockHarvestLevel(BSAMod.renforcedStairslock, "pickaxe", 2);
		
		ItemStack stack = new ItemStack(BSAMod.renforcedStairslock, 1);
		
		ItemStack ironStack = new ItemStack(Item.ingotIron, 1);
		ItemStack screwStack = new ItemStack(BSAMod.screwItem, 1);
		ItemStack cobbleStack = new ItemStack(Block.cobblestone, 1);

		GameRegistry.addRecipe(stack, new Object[] {
			"  X", " XX", "XXZ", 'X', ironStack, 'Z', screwStack
		});
		
		LanguageRegistry.addName(stack, "Renforced Stairs Block");
		
		//Dark version
		id = BSAMod.config.getBlock("RenforcedDarkStairsBlock", BSABlocks.RENFORCED_DARK_STAIRS_ID).getInt();
		BSAMod.renforcedDarkStairslock = new RenforcedStairsBlock(id, BSAMod.renforcedIronBlock, 1);
		BSAMod.renforcedDarkStairslock.setBlockName("renforcedDarkStairslock");
		
		GameRegistry.registerBlock(BSAMod.renforcedDarkStairslock, "renforcedDarkStairslock");
		MinecraftForge.setBlockHarvestLevel(BSAMod.renforcedDarkStairslock, "pickaxe", 2);
		
		stack = new ItemStack(BSAMod.renforcedDarkStairslock, 1);

		GameRegistry.addRecipe(stack, new Object[] {
			"  X", " CX", "XXZ", 'X', ironStack, 'C', cobbleStack, 'Z', screwStack
		});
		
		LanguageRegistry.addName(stack, "Renforced Dark Stairs Block");
	}

	// *******************************************************************
	@Override
	public String getTextureFile() { return BSAClientProxy.BLOCKS_TEXTURE; }
	
	@Override
	public int getBlockTextureFromSide(int side) {
		return this.textureOffset;
	}
	
	@Override
	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		return this.textureOffset;
	}

}
