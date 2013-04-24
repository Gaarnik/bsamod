package gaarnik.bsa.common.item;

import gaarnik.bsa.client.BSAClientProxy;
import gaarnik.bsa.common.BSAMod;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class EngCircuitItem extends Item {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public EngCircuitItem(int id) {
		super(id);
		
		this.setCreativeTab(BSAMod.tabs);
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public static void registry() {
		int id = BSAMod.config.getItem("EngCircuitItem", BSAItems.ENG_CIRCUIT_ID).getInt();
		
		BSAMod.engCircuitItem = new EngCircuitItem(id)
		.setIconIndex(1)
		.setItemName("EngCircuitItem");

		GameRegistry.registerItem(BSAMod.engCircuitItem, "EngCircuitItem");
		LanguageRegistry.addName(BSAMod.engCircuitItem, "Eng. Circuit");		
	}

	// *******************************************************************
	@Override
	public String getTextureFile() { return BSAClientProxy.ITEMS_TEXTURE; }

}
