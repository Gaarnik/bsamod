package gaarnik.bsa.client;

import net.minecraftforge.client.MinecraftForgeClient;
import gaarnik.bsa.common.BSACommonProxy;

public class BSAClientProxy extends BSACommonProxy {
	
	public static final String BLOCKS_TEXTURE = "/gaarnik/bsa/resources/block/blocks.png";

	public static final String ITEMS_TEXTURE = "/gaarnik/bsa/resources/item/items.png";
	
	public static final String ENGMACH_GUI = "/gaarnik/bsa/resources/gui/engmach_gui.png";
	
	public static final String ELECENGMACH_GUI = "/gaarnik/bsa/resources/gui/elecengmach_gui.png";
	
	@Override
	public void registerTextures() {
		MinecraftForgeClient.preloadTexture(BLOCKS_TEXTURE);
		MinecraftForgeClient.preloadTexture(ITEMS_TEXTURE);
	}

}
