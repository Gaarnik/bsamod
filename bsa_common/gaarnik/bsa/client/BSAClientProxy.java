package gaarnik.bsa.client;

import gaarnik.bsa.common.BSACommonProxy;
import gaarnik.bsa.common.tileentity.EngElecMachTileEntity;
import ic2.api.energy.event.EnergyTileLoadEvent;
import net.minecraftforge.common.MinecraftForge;

public class BSAClientProxy extends BSACommonProxy {
	
	@Override
	public void addMachineToIc2Network(EngElecMachTileEntity machine) {
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
