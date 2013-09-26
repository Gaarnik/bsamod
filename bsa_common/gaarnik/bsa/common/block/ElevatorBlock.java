package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAMod;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ElevatorBlock extends BSABlock {
	// *******************************************************************
	private static final String GAME_NAME = "Elevator Block";

	// *******************************************************************
	
	// *******************************************************************
	public ElevatorBlock(int id) {
		super(id, "ElevatorBlock");
		
		GameRegistry.registerBlock(this, "elevatorBlock");
		LanguageRegistry.addName(this, GAME_NAME);
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public static ElevatorBlock registry() {
		int id = BSAMod.config.getBlock("ElevatorBlock", BSABlocks.ELEVATOR_ID).getInt();
		return new ElevatorBlock(id);
	}

	// *******************************************************************
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		this.blockIcon = register.registerIcon("bsa:elevator");
	}
	
	@Override
	public boolean hasSubBlock() { return false; }

}
