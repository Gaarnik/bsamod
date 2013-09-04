package gaarnik.bsa.common.item;

import gaarnik.bsa.common.BSAMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EngCircuitItem extends Item {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public EngCircuitItem(int id) {
		super(id);
		
		this.setCreativeTab(BSAMod.tabs);
		this.setUnlocalizedName("EngCircuitItem");

		GameRegistry.registerItem(this, "EngCircuitItem");
		LanguageRegistry.addName(this, "Eng. Circuit");
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public static EngCircuitItem registry() {
		int id = BSAMod.config.getItem("EngCircuitItem", BSAItems.ENG_CIRCUIT_ID).getInt();
		
		return new EngCircuitItem(id);
	}

	// *******************************************************************
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		this.itemIcon = register.registerIcon("bsa:circuit");
	}

}
