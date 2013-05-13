package gaarnik.bsa.client.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.ForgeSubscribe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EngHelmetHUD {
	// *******************************************************************

	// *******************************************************************
	private static Minecraft mc;

	// *******************************************************************
	public static void init(Minecraft mc) {
		EngHelmetHUD.mc = mc;
	}

	// *******************************************************************
	@SideOnly(Side.CLIENT)
	public static void draw(GuiIngame gui) {
		EntityClientPlayerMP player = mc.thePlayer;
		
		StatusHUD.draw(mc, gui, player);
		TargetHUD.draw(mc, gui, player);
		EventHUD.draw(mc, gui, player);
	}

	// *******************************************************************
	@SideOnly(Side.CLIENT)
	@ForgeSubscribe
	public void onPlayerTargetBlock(DrawBlockHighlightEvent event) {
		TargetHUD.currentTarget = event;
	}

}
