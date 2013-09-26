package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAMod;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ElevatorFloorBlock extends BSABlock {
	// *******************************************************************
	private static final String GAME_NAME = "Elevator Floor Block";

	// *******************************************************************
	
	// *******************************************************************
	public ElevatorFloorBlock(int id) {
		super(id, "ElevatorFloorBlock");
		
		GameRegistry.registerBlock(this, "elevatorFloorBlock");
		LanguageRegistry.addName(this, GAME_NAME);
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public static ElevatorFloorBlock registry() {
		int id = BSAMod.config.getBlock("ElevatorFloorBlock", BSABlocks.ELEVATOR_FLOOR_ID).getInt();
		return new ElevatorFloorBlock(id);
	}

	// *******************************************************************
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		this.blockIcon = register.registerIcon("bsa:elevator_floorr");
	}
	
	@Override
	public boolean hasSubBlock() { return false; }

}
