package gaarnik.bsa.common.block;

import gaarnik.bsa.client.BSAClientProxy;
import gaarnik.bsa.common.BSAMod;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class StructureIronBlock extends Block {
	// *******************************************************************
	private static final int[] RECEIPE_MIDDLE_BLOCKS = new int[] {
		Item.ingotIron.itemID, Block.cobblestone.blockID
	};

	public static final String[] GAME_NAME = new String[] {
		"Structure Iron Block", "Structure Dark Iron Block"
	};

	// *******************************************************************

	// *******************************************************************
	public StructureIronBlock(int id) {
		super(id, Material.iron);
		
		this.setBlockName("StructureIronBlock");
		this.setHardness(7.0f);
		this.setResistance(15.0f);
		this.setStepSound(Block.soundMetalFootstep);
		
		this.setCreativeTab(BSAMod.tabs);
	}

	// *******************************************************************

	// *******************************************************************
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems) {
		for (int i=0;i<2;i++)
			subItems.add(new ItemStack(this, 1, i));
	}

	// *******************************************************************
	@SuppressWarnings("deprecation")
	public static void registry() {
		int id = BSAMod.config.getBlock("StructureIronBlock", BSABlocks.STRUCTURE_IRON_ID).getInt();
		BSAMod.structureIronBlock = new StructureIronBlock(id);

		GameRegistry.registerBlock(BSAMod.structureIronBlock, StructureIronItemBlock.class);
		MinecraftForge.setBlockHarvestLevel(BSAMod.structureIronBlock, "pickaxe", 2);
		
		for(int i=0;i<2;i++) {
			ItemStack stack = new ItemStack(BSAMod.structureIronBlock, 1, i);
			
			ItemStack ironStack = new ItemStack(Item.ingotIron, 1);
			ItemStack screwStack = new ItemStack(BSAMod.screwItem, 1);
			ItemStack middleStack = new ItemStack(RECEIPE_MIDDLE_BLOCKS[i], 1, 0);

			GameRegistry.addRecipe(stack, new Object[] {
				"XYX", "YZY", "XYX", 'X', ironStack, 'Y', screwStack, 'Z', middleStack
			});
			
			LanguageRegistry.addName(stack, GAME_NAME[stack.getItemDamage()]);
		}
	}

	// *******************************************************************
	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return 4 + metadata;
	}

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}
	
	@Override
	public int idDropped(int par1, Random rand, int par3) { return this.blockID; }
	
	@Override
	public int quantityDropped(Random rand) { return 1; }
	
	@Override
	public String getTextureFile() { return BSAClientProxy.BLOCKS_TEXTURE; }

}
