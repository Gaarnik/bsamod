package gaarnik.bsa.common;

import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NBTTagCompound;
import gaarnik.bsa.common.tileentity.EngElecMachTileEntity;

public class BSACommonProxy {
	
	public void registerClientTickHandler() {
		
	}
	
	public void registerDrawBlockHighlightEvent() {
		
	}
	
	public void addMachineToIc2Network(EngElecMachTileEntity machine) {
		
	}

	// *******************************************************************
	public int getItemEnergyStored(ItemStack stack) {
		if(stack.hasTagCompound() == false) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("stored", 0);
			stack.setTagCompound(nbt);
		}
		
		NBTTagCompound tag = stack.getTagCompound();
		
		return tag.getInteger("stored");
	}
	
	public void setItemEnergyStored(ItemStack stack, int stored) {
		NBTTagCompound nbt = stack.getTagCompound();
		
		if(stack.hasTagCompound() == false) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		
		nbt.setInteger("stored", stored);
	}
	
}
