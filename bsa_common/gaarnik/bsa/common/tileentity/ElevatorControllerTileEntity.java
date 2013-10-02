package gaarnik.bsa.common.tileentity;

import gaarnik.bsa.common.block.BSABlocks;
import gaarnik.bsa.common.entity.ElevatorBlockEntity;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;

import org.lwjgl.util.vector.Vector3f;

public class ElevatorControllerTileEntity extends TileEntity {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public ElevatorControllerTileEntity() {
		// TODO Auto-generated constructor stub
	}

	// *******************************************************************
	public void callElevator(int y) {
		ArrayList<Vector3f> elevators = this.findElevator();

		if(elevators.size() != 0) {
			System.out.println("Elevator Called: " + elevators.size() + " blocks moved");
			
			for(int i=0;i<elevators.size();i++) {
			//for(int i=0;i<1;i++) {
				Vector3f coords = elevators.get(i);
				
				ElevatorBlockEntity entity = new ElevatorBlockEntity(this.worldObj, 
						(int) coords.x, 
						(int) coords.y, 
						(int) coords.z, 
						y);
				this.worldObj.spawnEntityInWorld(entity);

				this.worldObj.setBlockToAir((int) coords.x, (int) coords.y, (int) coords.z);
			}
		}
		else
			System.out.println("Elevator Not Called");
	}

	// *******************************************************************
	private ArrayList<Vector3f> findElevator() {
		ArrayList<Vector3f> elevators = new ArrayList<Vector3f>();

		do {
			if(this.findElevatorBlocksFromCoord(elevators, this.xCoord - 1, this.zCoord))
				break;

			if(this.findElevatorBlocksFromCoord(elevators, this.xCoord + 1, this.zCoord))
				break;

			if(this.findElevatorBlocksFromCoord(elevators, this.xCoord, this.zCoord - 1))
				break;

			if(this.findElevatorBlocksFromCoord(elevators, this.xCoord, this.zCoord + 1))
				break;
		}
		while(0 == 1);

		return elevators;
	}

	private boolean findElevatorBlocksFromCoord(ArrayList<Vector3f> elevators, int x, int z) {
		for(int y=0;y<this.worldObj.getHeight();y++) { //from bedrock to sky
			int id = this.worldObj.getBlockId(x, y, z);

			if(id == BSABlocks.elevatorBlock.blockID) {
				elevators.add(new Vector3f(x, y, z));
				this.findAdjacentElevatorBlock(elevators, x, y, z);
				return true;
			}
		}

		return false;
	}

	private void findAdjacentElevatorBlock(ArrayList<Vector3f> elevators, int x, int y, int z) {
		int id = this.worldObj.getBlockId(x + 1, y, z);
		if(id == BSABlocks.elevatorBlock.blockID) {
			Vector3f v = new Vector3f(x + 1, y, z);
			if(this.isAlreadyDetected(elevators, v) == false) {
				elevators.add(v);
				this.findAdjacentElevatorBlock(elevators, x + 1, y, z);
			}
		}
		
		id = this.worldObj.getBlockId(x - 1, y, z);
		if(id == BSABlocks.elevatorBlock.blockID) {
			Vector3f v = new Vector3f(x - 1, y, z);
			if(this.isAlreadyDetected(elevators, v) == false) {
				elevators.add(v);
				this.findAdjacentElevatorBlock(elevators, x - 1, y, z);
			}
		}
		
		id = this.worldObj.getBlockId(x, y, z + 1);
		if(id == BSABlocks.elevatorBlock.blockID) {
			Vector3f v = new Vector3f(x, y, z + 1);
			if(this.isAlreadyDetected(elevators, v) == false) {
				elevators.add(v);
				this.findAdjacentElevatorBlock(elevators, x, y, z + 1);
			}
		}
		
		id = this.worldObj.getBlockId(x, y, z - 1);
		if(id == BSABlocks.elevatorBlock.blockID) {
			Vector3f v = new Vector3f(x, y, z - 1);
			if(this.isAlreadyDetected(elevators, v) == false) {
				elevators.add(v);
				this.findAdjacentElevatorBlock(elevators, x + 1, y, z - 1);
			}
		}
	}
	
	private boolean isAlreadyDetected(ArrayList<Vector3f> elevators, Vector3f v) {
		int size = elevators.size();
		
		for(int i=0;i<size;i++) {
			Vector3f v2 = elevators.get(i);
			
			if(v.x == v2.x && v.y == v2.y && v.z == v2.z)
				return true;
		}
		
		return false;
	}

	// *******************************************************************

	// *******************************************************************

}
