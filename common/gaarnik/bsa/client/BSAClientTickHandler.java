package gaarnik.bsa.client;

import gaarnik.bsa.client.hud.EngHelmetHUD;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class BSAClientTickHandler implements ITickHandler {
	// *******************************************************************
	private Minecraft mc;

	// *******************************************************************
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if(mc == null) {
			mc = Minecraft.getMinecraft();
			EngHelmetHUD.init(mc);
		}
	}

	// *******************************************************************
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if (type.contains(TickType.RENDER) == false)
			return;
		
		if(this.mc.currentScreen != null)
			return;
		
		//TODO check if player has equiped the Eng. Helmet
		EngHelmetHUD.draw(this.mc.ingameGUI);
	}

	// *******************************************************************
	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.WORLD, TickType.WORLDLOAD, TickType.CLIENT, TickType.RENDER);
	}

	@Override
	public String getLabel() { return "BSAMod"; }

}
