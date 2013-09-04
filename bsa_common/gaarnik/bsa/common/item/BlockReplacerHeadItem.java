package gaarnik.bsa.common.item;

import gaarnik.bsa.common.BSAMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockReplacerHeadItem extends Item {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public BlockReplacerHeadItem(int id) {
		super(id);

		this.setCreativeTab(BSAMod.tabs);
		this.setUnlocalizedName("BlockReplacerHeadItem");
		
		ItemStack stack = new ItemStack(this, 1);
		ItemStack ironStack = new ItemStack(Item.ingotIron, 1);
		ItemStack screwStack = new ItemStack(BSAItems.screwItem, 1);

		GameRegistry.addRecipe(stack, new Object[] {
			"X X", "XYX", " X ", 'X', ironStack, 'Y', screwStack
		});

		GameRegistry.registerItem(this, "BlockReplacerHeadItem");
		LanguageRegistry.addName(this, "Block Replacer Head");
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public static BlockReplacerHeadItem registry() {
		int id = BSAMod.config.getItem("BlockReplacerHeadItem", BSAItems.BLOCK_REPLACER_HEAD_ID).getInt();

		return new BlockReplacerHeadItem(id);
	}

	// *******************************************************************
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		this.itemIcon = register.registerIcon("bsa:blockreplacer_head");
	}

}
