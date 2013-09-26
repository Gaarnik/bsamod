package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAMod;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ElevatorControllerBlock extends BSABlock {
	// *******************************************************************
	private static final String GAME_NAME = "Elevator Controller Block";

	// *******************************************************************
	
	// *******************************************************************
	public ElevatorControllerBlock(int id) {
		super(id, "ElevatorControllerBlock");
		
		GameRegistry.registerBlock(this, "elevatorControllerBlock");
		LanguageRegistry.addName(this, GAME_NAME);
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public static ElevatorControllerBlock registry() {
		int id = BSAMod.config.getBlock("ElevatorControllerBlock", BSABlocks.ELEVATOR_CONTROLLER_ID).getInt();
		return new ElevatorControllerBlock(id);
	}

	// *******************************************************************
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		this.blockIcon = register.registerIcon("bsa:elevator_controller");
	}
	
	@Override
	public boolean hasSubBlock() { return false; }

}
