package gaarnik.bsa.common.item;

import gaarnik.bsa.client.BSAClientProxy;
import gaarnik.bsa.common.BSAMod;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ScrewItem extends Item {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public ScrewItem(int id) {
		super(id);
		
		this.setCreativeTab(BSAMod.tabs);
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public static void registry() {
		int id = BSAMod.config.getItem("ScrewItem", BSAItems.SCREW_ID).getInt();
		
		BSAMod.screwItem = new ScrewItem(id)
		.setIconIndex(0)
		.setItemName("ScrewItem");

		GameRegistry.registerItem(BSAMod.screwItem, "ScrewItem");
		LanguageRegistry.addName(BSAMod.screwItem, "Screw");
	}

	// *******************************************************************
	@Override
	public String getTextureFile() { return BSAClientProxy.ITEMS_TEXTURE; }

}
