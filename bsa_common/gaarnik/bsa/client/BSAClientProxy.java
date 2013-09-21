package gaarnik.bsa.client;

import gaarnik.bsa.common.BSACommonProxy;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.tile.IEnergyTile;
import net.minecraftforge.common.MinecraftForge;

public class BSAClientProxy extends BSACommonProxy {
	
	@Override
	public void addMachineToIc2Network(IEnergyTile machine) {
		MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(machine));
	}
	
	@Override
	public void registerClientTickHandler() {
		//TickRegistry.registerTickHandler(new BSAClientTickHandler(), Side.CLIENT);
	}
	
	@Override
	public void registerDrawBlockHighlightEvent() {
		//MinecraftForge.EVENT_BUS.register(new EngHelmetHUD());
	}

}
