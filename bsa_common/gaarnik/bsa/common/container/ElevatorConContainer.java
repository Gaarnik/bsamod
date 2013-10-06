package gaarnik.bsa.common.container;

import gaarnik.bsa.common.tileentity.ElevatorControllerTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ElevatorConContainer extends Container {
	// *******************************************************************

	// *******************************************************************
	private ElevatorControllerTileEntity tileEntity;

	// *******************************************************************
	public ElevatorConContainer(InventoryPlayer inventory, ElevatorControllerTileEntity tileEntity) {
		this.tileEntity = tileEntity;
	}

	// *******************************************************************
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************

}
