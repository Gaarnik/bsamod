package gaarnik.bsa.client;

import gaarnik.bsa.common.BSACommonProxy;
import gaarnik.bsa.common.tileentity.EngElecMachTileEntity;
import ic2.api.energy.event.EnergyTileLoadEvent;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class BSAClientProxy extends BSACommonProxy {
	// *******************************************************************
	public static final String BLOCKS_TEXTURE = "/gaarnik/bsa/resources/block/blocks.png";

	// *******************************************************************
	public static final String ITEMS_TEXTURE = "/gaarnik/bsa/resources/item/items.png";

	// *******************************************************************
	public static final String ENGMACH_GUI = "/gaarnik/bsa/resources/gui/engmach_gui.png";
	
	public static final String ELECENGMACH_GUI = "/gaarnik/bsa/resources/gui/elecengmach_gui.png";

	public static final String BLOCKREPLACER_GUI = "/gaarnik/bsa/resources/gui/blockreplacer_gui.png";

	// *******************************************************************

	// *******************************************************************
	@Override
	public void registerTextures() {
		MinecraftForgeClient.preloadTexture(BLOCKS_TEXTURE);
		MinecraftForgeClient.preloadTexture(ITEMS_TEXTURE);

		MinecraftForgeClient.preloadTexture(ENGMACH_GUI);
		MinecraftForgeClient.preloadTexture(ELECENGMACH_GUI);
		MinecraftForgeClient.preloadTexture(BLOCKREPLACER_GUI);
	}

	// *******************************************************************
	@Override
	public void addMachineToIc2Network(EngElecMachTileEntity machine) {
		MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(machine));
	}

}
