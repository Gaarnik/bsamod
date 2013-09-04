package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAMod;
import gaarnik.bsa.common.item.BSAItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RenforcedIronBlock extends BSABlock {
	// *******************************************************************
	private static final int[] RECEIPE_MIDDLE_BLOCKS = new int[] {
		Item.ingotIron.itemID, Block.cobblestone.blockID
	};

	public static final String[] TEXTURE_NAME = new String[] {
		"renforced", "renforced_dark"
	};

	public static final String[] GAME_NAME = new String[] {
		"Renforced Iron Block", "Renforced Dark Iron Block"
	};

	// *******************************************************************
	@SideOnly(Side.CLIENT)
	private Icon[] icons;

	// *******************************************************************
	public RenforcedIronBlock(int id) {
		super(id, "RenforcedIronBlock");

		this.setCreativeTab(BSAMod.tabs);

		this.setHardness(7.0f);
		this.setResistance(100.0f);
		this.setStepSound(Block.soundMetalFootstep);

		GameRegistry.registerBlock(this, RenforcedIronItemBlock.class, "RenforcedIronBlock");

		for(int i=0;i<RECEIPE_MIDDLE_BLOCKS.length;i++) {
			ItemStack stack = new ItemStack(this, 4, i);

			ItemStack ironStack = new ItemStack(Item.ingotIron, 1);
			ItemStack screwStack = new ItemStack(BSAItems.screwItem, 1);
			ItemStack middleStack = new ItemStack(RECEIPE_MIDDLE_BLOCKS[i], 1, 0);

			GameRegistry.addRecipe(stack, new Object[] {
					"YXY", "XZX", "YXY", 'X', ironStack, 'Y', screwStack, 'Z', middleStack
			});

			LanguageRegistry.addName(stack, GAME_NAME[stack.getItemDamage()]);
		}
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public static RenforcedIronBlock registry() {
		int id = BSAMod.config.getBlock("RenforcedIronBlock", BSABlocks.RENFORCED_IRON_ID).getInt();

		return new RenforcedIronBlock(id);
	}

	// *******************************************************************
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		this.icons = new Icon[TEXTURE_NAME.length];
		
		for(int i=0;i<TEXTURE_NAME.length;i++)
			this.icons[i] = register.registerIcon("bsa:" + TEXTURE_NAME[i]);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		return this.icons[metadata];
	}

}
