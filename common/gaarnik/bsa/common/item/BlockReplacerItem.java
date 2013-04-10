package gaarnik.bsa.common.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import gaarnik.bsa.common.BSAGuiHandler;
import gaarnik.bsa.common.BSAMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockReplacerItem extends Item {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public BlockReplacerItem(int id) {
		super(id);

		this.setCreativeTab(BSAMod.tabs);
	}

	// *******************************************************************
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		this.displayGUI(world, player);

		return stack;
	}

	// *******************************************************************
	private void displayGUI(World world, EntityPlayer player) {
		if(player.isSneaking())
			player.openGui(BSAMod.instance, BSAGuiHandler.GUI_ENG_MACH, world, 
					(int) player.posX, (int) player.posY, (int) player.posZ);
	}

	// *******************************************************************
	public static void registry() {
		int id = BSAMod.config.getItem("BlockReplacerItem", BSAItems.BLOCK_REPLACER_ID).getInt();

		BSAMod.blockReplacerItem = new BlockReplacerItem(id)
		.setIconIndex(0)
		.setItemName("BlockReplacerItem");

		//TODO add recipe

		GameRegistry.registerItem(BSAMod.blockReplacerItem, "blockReplacerItem");
		LanguageRegistry.addName(BSAMod.blockReplacerItem, "Block Replacer");
	}

	// *******************************************************************

}
