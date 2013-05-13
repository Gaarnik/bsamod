package gaarnik.bsa.client.hud;

import ic2.api.energy.tile.IEnergySink;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

public class TargetHUD {
	// *******************************************************************
	public static DrawBlockHighlightEvent currentTarget;

	// *******************************************************************
	public static void draw(Minecraft mc, GuiIngame gui, EntityClientPlayerMP player) {
		if(currentTarget == null)
			return;

		if(currentTarget.target == null) {
			currentTarget = null;
			return;
		}

		MovingObjectPosition target = currentTarget.target;
		TileEntity tileEntity = mc.theWorld.getBlockTileEntity(target.blockX, target.blockY, target.blockZ);

		int y = 30;

		if(tileEntity != null) {
			gui.drawString(mc.fontRenderer, "Target Block: ", 10, y, 16777215);
			y += 10;

			if(tileEntity instanceof IInventory)
				y = drawInventory(mc, gui, (IInventory) tileEntity, y);

			if(tileEntity instanceof IEnergySink)
				y = drawEnergySync(mc, gui, (IEnergySink) tileEntity, y);
		}
		else if(target.entityHit != null) {
			gui.drawString(mc.fontRenderer, "Target Entity: ", 10, y, 16777215);
			y += 10;

			if(target.entityHit instanceof EntityLiving) {
				EntityLiving living = (EntityLiving) target.entityHit;

				gui.drawString(mc.fontRenderer, "Health: " + living.getHealth() + " / " + living.getMaxHealth(), 10, y, 16777215);
				y += 10;
			}
		}

		currentTarget = null; //consume target
	}

	// *******************************************************************
	private static int drawInventory(Minecraft mc, GuiIngame gui, IInventory inv, int y) {
		if(inv.getInvName() != "container.chest")
			return y;
		
		int total = inv.getSizeInventory();
		int full = 0;
		int max = inv.getInventoryStackLimit();
		
		for (int i=0;i<total;i++) {
			ItemStack stack = inv.getStackInSlotOnClosing(i);
			
			if(stack != null && stack.stackSize == max)
				full++;
		}
		
		String str = "Inventory: " + full + " / " + total;
		
		gui.drawString(mc.fontRenderer, str, 10, y, 16777215);
		y += 10;
		
		return y;
	}

	// *******************************************************************
	private static int drawEnergySync(Minecraft mc, GuiIngame gui, IEnergySink energySync, int y) {
		gui.drawString(mc.fontRenderer, "Electric Block: ", 10, y, 16777215);
		y += 10;
		
		return y;
	}

}
