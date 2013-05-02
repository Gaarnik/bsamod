package gaarnik.bsa.client;

import ic2.api.energy.tile.IEnergySink;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EngHelmetHUD {
	// *******************************************************************

	// *******************************************************************
	private static Minecraft mc;
	
	private static DrawBlockHighlightEvent currentTarget;

	// *******************************************************************
	public static void init(Minecraft mc) {
		EngHelmetHUD.mc = mc;
	}

	// *******************************************************************
	@SideOnly(Side.CLIENT)
	public static void draw(GuiIngame gui) {
		EntityClientPlayerMP player = mc.thePlayer;
		
		gui.drawString(mc.fontRenderer, "Health: " + player.prevHealth + " / " + player.getMaxHealth(), 10, 10, 16777215);
		
		if(currentTarget != null && currentTarget.target != null) {
			MovingObjectPosition target = currentTarget.target;
			TileEntity tileEntity = mc.theWorld.getBlockTileEntity(target.blockX, target.blockY, target.blockZ);

			int y = 30;
			
			if(tileEntity != null) {
				gui.drawString(mc.fontRenderer, "Target Block: ", 10, y, 16777215);
				y += 10;
				
				if(tileEntity instanceof IInventory) {
					gui.drawString(mc.fontRenderer, "Inventory: ", 10, y, 16777215);
					y += 10;
				}
				
				if(tileEntity instanceof IEnergySink) {
					gui.drawString(mc.fontRenderer, "Electric Block: ", 10, y, 16777215);
					y += 10;
				}
			}
			else if(target.entityHit != null) {
				gui.drawString(mc.fontRenderer, "Target Entity: ", 10, y, 16777215);
				y += 10;
				
				if(target.entityHit instanceof EntityLiving) {
					EntityLiving living = (EntityLiving) target.entityHit;
					
					gui.drawString(mc.fontRenderer, "Health: " + living.prevHealth + " / " + living.getMaxHealth(), 10, y, 16777215);
					y += 10;
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@ForgeSubscribe
	public void onPlayerTargetBlock(DrawBlockHighlightEvent event) {
		currentTarget = event;
	}

}
