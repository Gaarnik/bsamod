package gaarnik.bsa.common.tileentity;

import gaarnik.bsa.common.BSAMod;
import gaarnik.bsa.common.block.BSABlocks;
import ic2.api.Direction;
import ic2.api.IWrenchable;
import ic2.api.energy.event.EnergyTileSourceEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.network.NetworkHelper;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

public class ThermalGeneratorTileEntity extends TileEntity implements IEnergySource, IEnergySink, IWrenchable, INetworkDataProvider, INetworkTileEntityEventListener {
	// *******************************************************************
	public static final int MAX_ENERGY 				= 500;
	public static final float EU_PER_LAVA_SOURCE 	= 0.4f;

	// *******************************************************************
	private int sourcesCount;
	private float stored;

	private boolean addedToNetwork;
	private boolean active;
	private boolean prevActive;

	private static ArrayList<String> networkedFileds;

	// *******************************************************************
	public ThermalGeneratorTileEntity() {
		if(networkedFileds == null) {
			networkedFileds = new ArrayList<String>();
			networkedFileds.add("active");
		}

		this.addedToNetwork = false;
		this.active = this.prevActive = false;

		this.sourcesCount = 0;
		this.stored = 0;
	}

	// *******************************************************************
	@Override
	public void updateEntity() {
		super.updateEntity();

		if(this.addedToNetwork == false) {
			BSAMod.proxy.addMachineToIc2Network(this);
			this.addedToNetwork = true;
		}

		if(this.worldObj.isRemote)
			return;

		this.sourcesCount = 0;

		if(this.worldObj.getBlockId(this.xCoord, this.yCoord - 1, this.zCoord) == Block.lavaStill.blockID)
			this.sourcesCount++;

		if(this.worldObj.getBlockId(this.xCoord, this.yCoord + 1, this.zCoord) == Block.lavaStill.blockID)
			this.sourcesCount++;

		if(this.worldObj.getBlockId(this.xCoord - 1, this.yCoord, this.zCoord) == Block.lavaStill.blockID)
			this.sourcesCount++;

		if(this.worldObj.getBlockId(this.xCoord + 1, this.yCoord, this.zCoord) == Block.lavaStill.blockID)
			this.sourcesCount++;

		if(this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord - 1) == Block.lavaStill.blockID)
			this.sourcesCount++;

		if(this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord + 1) == Block.lavaStill.blockID)
			this.sourcesCount++;
		
		if(this.sourcesCount > 0) {
			this.stored += (float) (EU_PER_LAVA_SOURCE * this.sourcesCount);
			if (this.stored > MAX_ENERGY)
				this.stored = MAX_ENERGY;
		}
		
		int output = (int) Math.min(this.getMaxEnergyOutput(), this.stored);
		if (output > 0) {
			this.stored = ((float)(this.stored + (sendEnergy(output) - output)));
			//TODO fix when internal eu buffer implemented
			//ThermalGeneratorMachBlock.updateBlockState(true, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}
	}

	@Override
	public void invalidate() {
		if (this.addedToNetwork) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			this.addedToNetwork = false;
		}

		super.invalidate();
	}

	// *******************************************************************
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		this.sourcesCount = tag.getInteger("sourcesCount");
		this.stored = tag.getFloat("stored");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		tag.setInteger("sourcesCount", this.sourcesCount);
		tag.setFloat("stored", this.stored);
	}

	// *******************************************************************
	@Override
	public boolean emitsEnergyTo(TileEntity receiver, Direction direction) {
		int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);

		switch (metadata) {
		
		case 0:
			return direction == Direction.YN;
		
		case 1:
			return direction == Direction.YP;

		case 2:
			return direction == Direction.ZN;

		case 3:
			return direction == Direction.ZP;

		case 4:
			return direction == Direction.XN;

		case 5:
			return direction == Direction.XP;

		default:
			return false;

		}
	}

	@Override
	public int injectEnergy(Direction directionFrom, int amount) {
		if(amount > this.getMaxSafeInput()) {
			if (!BSAMod.explodeMachineAt(worldObj, xCoord, yCoord, zCoord)) {
				worldObj.createExplosion(null, xCoord, yCoord, zCoord, 2.0F, true);
				worldObj.setBlock(xCoord, yCoord, zCoord, 0);
			}

			invalidate();
			return 0;
		}

		this.stored += amount;
		int excess = 0;

		if (this.stored > MAX_ENERGY) {
			excess = (int) (this.stored - MAX_ENERGY);
			this.stored = MAX_ENERGY;
		}

		return excess;
	}

	// *******************************************************************
	@Override
	public void onNetworkEvent(int event) {}

	@Override
	public List<String> getNetworkedFields() {
		return networkedFileds;
	}

	// *******************************************************************
	@Override
	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) { return false; }

	@Override
	public short getFacing() { return 0; }

	@Override
	public void setFacing(short facing) {}

	@Override
	public boolean wrenchCanRemove(EntityPlayer player) {
		return player.isSneaking();
	}

	@Override
	public float getWrenchDropRate() { return 0.9f;	}

	@Override
	public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
		return new ItemStack(BSABlocks.thermalGeneratorMachBlock, 1);
	}

	// *******************************************************************
	private int sendEnergy(int send) {
		EnergyTileSourceEvent event = new EnergyTileSourceEvent(this, send);
		MinecraftForge.EVENT_BUS.post(event);

		return event.amount;
	}

	// *******************************************************************
	@Override
	public int getMaxEnergyOutput() { return 32; }

	@Override
	public int getMaxSafeInput() { return 32; }

	@Override
	public int demandsEnergy() { return (int) (MAX_ENERGY - this.stored); }

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter, Direction direction) { return true; }
	
	@Override
	public boolean isAddedToEnergyNet() { return this.addedToNetwork; }
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;

		if(this.active != this.prevActive)
			NetworkHelper.updateTileEntityField(this, "active");

		this.prevActive = active;
	}

	public int getSourcesCount() { return this.sourcesCount; }
	public void setSourcesCount(int count) { this.sourcesCount = count; }
	
	public int getEnergyStored() { return (int) this.stored; }
	public void setEnergyStored(int stored) { this.stored = stored; }

}
