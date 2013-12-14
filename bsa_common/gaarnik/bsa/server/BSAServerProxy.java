package gaarnik.bsa.server;

import gaarnik.bsa.common.BSACommonProxy;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.tile.IEnergyTile;
import net.minecraftforge.common.MinecraftForge;

public class BSAServerProxy extends BSACommonProxy {
	
	@Override
	public void addMachineToIc2Network(IEnergyTile machine) {
		MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(machine));
	}

}
