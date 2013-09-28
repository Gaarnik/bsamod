package gaarnik.bsa.common.tileentity;

import net.minecraft.tileentity.TileEntity;

public class ElevatorFloorTileEntity extends TileEntity {
	
	private boolean powered;
	
	public ElevatorFloorTileEntity() {
		this.powered = false;
	}
	
	public boolean isPowered() { return this.powered; }
	public void setPowered(boolean powered) { this.powered = powered; }

}
