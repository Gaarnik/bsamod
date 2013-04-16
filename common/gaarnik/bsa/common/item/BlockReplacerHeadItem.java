package gaarnik.bsa.common.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import gaarnik.bsa.client.BSAClientProxy;
import gaarnik.bsa.common.BSAMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockReplacerHeadItem extends Item {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public BlockReplacerHeadItem(int id) {
		super(id);

		this.setIconIndex(2);
		this.setItemName("BlockReplacerHeadItem");
		
		this.setCreativeTab(BSAMod.tabs);
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public static void registry() {
		int id = BSAMod.config.getItem("BlockReplacerHeadItem", BSAItems.BLOCK_REPLACER_HEAD_ID).getInt();

		BSAMod.blockReplacerHeadItem = new BlockReplacerHeadItem(id);

		ItemStack stack = new ItemStack(BSAMod.blockReplacerHeadItem, 1);
		ItemStack ironStack = new ItemStack(Item.ingotIron, 1);
		ItemStack screwStack = new ItemStack(BSAMod.screwItem, 1);

		GameRegistry.addRecipe(stack, new Object[] {
			"X X", "XYX", " X ", 'X', ironStack, 'Y', screwStack
		});

		GameRegistry.registerItem(BSAMod.blockReplacerHeadItem, "BlockReplacerHeadItem");
		LanguageRegistry.addName(BSAMod.blockReplacerHeadItem, "Block Replacer Head");
	}

	// *******************************************************************
	@Override
	public String getTextureFile() { return BSAClientProxy.ITEMS_TEXTURE; }

}
