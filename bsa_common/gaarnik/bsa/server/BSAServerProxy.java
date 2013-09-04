package gaarnik.bsa.server;

import gaarnik.bsa.common.BSACommonProxy;
import gaarnik.bsa.common.tileentity.EngElecMachTileEntity;

public class BSAServerProxy extends BSACommonProxy {
	
	@Override
	public void addMachineToIc2Network(EngElecMachTileEntity machine) {
		/*NetworkHelper.requestInitialData(machine);
		NetworkHelper.announceBlockUpdate(machine.worldObj, machine.xCoord, machine.yCoord, machine.zCoord);

		MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(machine));*/
	}

}
