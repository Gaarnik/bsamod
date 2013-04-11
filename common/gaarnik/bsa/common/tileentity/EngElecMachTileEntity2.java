package gaarnik.bsa.common.tileentity;

import ic2.api.Direction;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

public class EngElecMachTileEntity2 extends TileEntity implements IInventory, IEnergySink {
	// *******************************************************************
	public static final int MAX_ENERGY = 500;
	public static final int MAX_PROCESS_TICKS = 100;
	
	private static final int MAX_INPUT = 32;
	private static final int ENERGY_CONSUME = 2;
	
	// *******************************************************************
	private boolean addedToNetwork;
	
	//TODO back to private !
	public int energyStored;
	
	private int processTicks;
	
	// *******************************************************************
	public EngElecMachTileEntity2() {
		this.addedToNetwork = false;
		
		this.energyStored = 0;
	}

	// *******************************************************************
	@Override
	public void updateEntity() {
		super.updateEntity();

		if(this.addedToNetwork == false) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			this.addedToNetwork = true;
		}
		
		if(this.energyStored >= ENERGY_CONSUME && this.canProcess()) {
			
		}
		else
			this.processTicks = 0;
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		
		if (this.addedToNetwork) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            this.addedToNetwork = false;
        }
	}

	@Override
	public int injectEnergy(Direction directionFrom, int amount) {
		//TODO explode
		if(amount > MAX_INPUT) {
			return 0;
		}
		
		this.energyStored += amount;
        int excess = 0;
        
        if (this.energyStored > MAX_ENERGY) {
        	excess = this.energyStored - MAX_ENERGY;
            this.energyStored = MAX_ENERGY;
        }

        return excess;
	}

	// *******************************************************************
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		
		this.energyStored = tag.getInteger("energyStored");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		
		tag.setInteger("energyStored", this.energyStored);
	}

	// *******************************************************************
	private boolean canProcess() {
		return false;
	}

	// *******************************************************************
	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) {
		return true;
	}

	@Override
	public boolean isAddedToEnergyNet() { return this.addedToNetwork; }

	@Override
	public int demandsEnergy() { return MAX_ENERGY - this.energyStored; }

	@Override
	public int getMaxSafeInput() { return MAX_INPUT; }
	
	public int getEnergyStored() { return this.energyStored; }
	
	public int getProcessTicks() { return this.processTicks; }

	@Override
	public void openChest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInvName() { return "container.EngElecMach"; }

	@Override
	public int getInventoryStackLimit() { return 64; }

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) { return true; }


}
