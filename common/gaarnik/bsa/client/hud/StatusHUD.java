package gaarnik.bsa.client.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiIngame;

public class StatusHUD {
	// *******************************************************************

	// *******************************************************************
	public static void draw(Minecraft mc, GuiIngame gui, EntityClientPlayerMP player) {
		gui.drawString(mc.fontRenderer, "Health: " + player.getHealth() + " / " + player.getMaxHealth(), 10, 10, 16777215);
	}

}
