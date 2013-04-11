package gaarnik.bsa.common.tileentity;

import gaarnik.bsa.common.recipe.EngMachRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.network.IPacketReceiver;
import universalelectricity.prefab.network.PacketManager;
import universalelectricity.prefab.tile.TileEntityElectricityRunnable;

import com.google.common.io.ByteArrayDataInput;

public class EngElecMachTileEntity extends TileEntityElectricityRunnable implements IInventory, ISidedInventory, IPacketReceiver{
	// *******************************************************************
	public static final double WATTS_PER_TICK = 500;
	public static final int PROCESS_TIME_REQUIRED = 130;

	// *******************************************************************
	private ItemStack[] containingItems = new ItemStack[3];

	public int processTicks = 0;
	private int playersUsing = 0;

	// *******************************************************************
	public EngElecMachTileEntity() {

	}

	// *******************************************************************
	@Override
	public void updateEntity() {
		super.updateEntity();

		//this.wattsReceived += ElectricItemHelper.dechargeItem(this.containingItems[0], WATTS_PER_TICK, this.getVoltage());

		if (!this.worldObj.isRemote) {
			if (this.canProcess()) {
				if (this.wattsReceived >= WATTS_PER_TICK)
					this.processTicks = PROCESS_TIME_REQUIRED;
				else if (this.processTicks > 0) {
					this.processTicks--;

					if (this.processTicks < 1) {
						this.smeltItem();
						this.processTicks = 0;
					}
				}
				else
					this.processTicks = 0;
			}
			else
				this.processTicks = 0;

			this.wattsReceived = Math.max(this.wattsReceived - WATTS_PER_TICK / 4, 0);
		}
		else
			this.processTicks = 0;

		if (this.ticks % 3 == 0 && this.playersUsing > 0)
			PacketManager.sendPacketToClients(getDescriptionPacket(), this.worldObj, new Vector3(this), 12);
	}
	
	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if (this.containingItems[par1] != null) {
			ItemStack var3;

			if (this.containingItems[par1].stackSize <= par2) {
				var3 = this.containingItems[par1];
				this.containingItems[par1] = null;
				return var3;
			}
			else {
				var3 = this.containingItems[par1].splitStack(par2);

				if (this.containingItems[par1].stackSize == 0)
					this.containingItems[par1] = null;

				return var3;
			}
		}
		else
			return null;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
		if (this.containingItems[par1] != null) {
			ItemStack var2 = this.containingItems[par1];
			this.containingItems[par1] = null;
			return var2;
		}
		else
			return null;
	}
	
	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		this.containingItems[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
			par2ItemStack.stackSize = this.getInventoryStackLimit();
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}
	
	@Override
	public void openChest() {}

	@Override
	public void closeChest() {}

	// *******************************************************************
	@Override
	public ElectricityPack getRequest() {
		if (this.canProcess())
			return new ElectricityPack(WATTS_PER_TICK / this.getVoltage(), this.getVoltage());
		else
			return new ElectricityPack();
	}

	@Override
	public Packet getDescriptionPacket() {
		return PacketManager.getPacket("BSAModChannel", this, this.processTicks, this.disabledTicks);
	}

	@Override
	public void handlePacketData(INetworkManager network, int packetType, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream) {
		try {
			this.processTicks = dataStream.readInt();
			this.disabledTicks = dataStream.readInt();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// *******************************************************************
	private boolean canProcess() {
		if (EngMachRecipe.smelting().getSmeltingResult(this.containingItems[1]) == null)
			return false;

		if (this.containingItems[1] == null)
			return false;

		if (this.containingItems[2] != null) {
			if (!this.containingItems[2].isItemEqual(EngMachRecipe.smelting().getSmeltingResult(this.containingItems[1])))
				return false;

			if (this.containingItems[2].stackSize + 1 > 64)
				return false;
		}

		return true;
	}
	
	private void smeltItem() {
		if (this.canProcess()) {
			ItemStack resultItemStack = EngMachRecipe.smelting().getSmeltingResult(this.containingItems[1]);

			if (this.containingItems[2] == null)
				this.containingItems[2] = resultItemStack.copy();
			else if (this.containingItems[2].isItemEqual(resultItemStack))
				this.containingItems[2].stackSize++;

			this.containingItems[1].stackSize--;

			if (this.containingItems[1].stackSize <= 0)
				this.containingItems[1] = null;
		}
	}

	// *******************************************************************
	@Override
	public void readFromNBT(NBTTagCompound NBT) {
		super.readFromNBT(NBT);
		
		this.processTicks = NBT.getInteger("smeltingTicks");
		NBTTagList var2 = NBT.getTagList("Items");
		this.containingItems = new ItemStack[this.getSizeInventory()];

		for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			byte var5 = var4.getByte("Slot");

			if (var5 >= 0 && var5 < this.containingItems.length)
			{
				this.containingItems[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound NBT) {
		super.writeToNBT(NBT);
		
		NBT.setInteger("smeltingTicks", this.processTicks);
		NBTTagList var2 = new NBTTagList();

		for (int var3 = 0; var3 < this.containingItems.length; ++var3)
		{
			if (this.containingItems[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				this.containingItems[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}

		NBT.setTag("Items", var2);
	}

	// *******************************************************************
	@Override
	public int getSizeInventory() { return this.containingItems.length; }
	
	@Override
	public ItemStack getStackInSlot(int par1) { return this.containingItems[par1]; }
	
	public String getInvName() { return "container.EngElecMach"; }
	
	@Override
	public int getInventoryStackLimit() { return 64; }

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		// TODO Auto-generated method stub
		return 0;
	}

}
