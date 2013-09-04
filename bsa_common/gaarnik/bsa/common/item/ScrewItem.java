package gaarnik.bsa.common.item;

import gaarnik.bsa.common.BSAMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ScrewItem extends Item {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public ScrewItem(int id) {
		super(id);
		
		this.setCreativeTab(BSAMod.tabs);
		this.setUnlocalizedName("ScrewItem");

		GameRegistry.registerItem(this, "ScrewItem");
		LanguageRegistry.addName(this, "Screw");
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public static ScrewItem registry() {
		int id = BSAMod.config.getItem("ScrewItem", BSAItems.SCREW_ID).getInt();
		
		return new ScrewItem(id);
	}

	// *******************************************************************
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		this.itemIcon = register.registerIcon("bsa:screw");
	}

}
