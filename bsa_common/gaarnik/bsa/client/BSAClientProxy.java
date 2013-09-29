package gaarnik.bsa.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import gaarnik.bsa.client.render.ElevatorBlockEntityRender;
import gaarnik.bsa.common.BSACommonProxy;
import gaarnik.bsa.common.entity.ElevatorBlockEntity;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.tile.IEnergyTile;
import net.minecraftforge.common.MinecraftForge;

public class BSAClientProxy extends BSACommonProxy {
	
	@Override
	public void addMachineToIc2Network(IEnergyTile machine) {
		MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(machine));
	}
	
	@Override
	public void registerRenderes() {
		RenderingRegistry.registerEntityRenderingHandler(ElevatorBlockEntity.class, new ElevatorBlockEntityRender());
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
