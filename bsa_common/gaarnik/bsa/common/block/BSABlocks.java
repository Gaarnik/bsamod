package gaarnik.bsa.common.block;

import net.minecraft.block.Block;

public class BSABlocks {
	// *******************************************************************
	public static final int RENFORCED_IRON_ID = 1700;
	public static final int STRUCTURE_IRON_ID = 1701;
	public static final int GRID_IRON_ID = 1702;
	
	public static final int RENFORCED_STAIRS_ID = 1703;
	public static final int RENFORCED_DARK_STAIRS_ID = 1704;

	public static final int ENG_MACH_ID = 1750;
	public static final int ENG_MACH_ACTIVE_ID = 1751;

	public static final int ELEC_ENG_MACH_ID = 1752;
	public static final int ELEC_ENG_MACH_ACTIVE_ID = 1753;

	public static final int THERMAL_GENERATOR_MACH_ID = 1754;
	
	public static final int ELEVATOR_CONTROLLER_ID = 1755;
	public static final int ELEVATOR_FLOOR_ID = 1756;
	public static final int ELEVATOR_ID = 1757;

	// *******************************************************************
	public static Block renforcedIronBlock;
	public static Block gridIronBlock;
	public static Block structureIronBlock;

	public static Block renforcedStairslock;
	public static Block renforcedDarkStairslock;
	
	public static Block engMachBlock;
	public static Block engMachActiveBlock;
	
	public static Block engElecMachBlock;
	public static Block engElecActiveMachBlock;
	
	public static Block thermalGeneratorMachBlock;
	
	public static Block elevatorControllerBlock;
	public static Block elevatorFloorBlock;
	public static Block elevatorBlock;
	
	// *******************************************************************
	public static void registry() {
		renforcedIronBlock = RenforcedIronBlock.registry();
		gridIronBlock = GridIronBlock.registry();
		structureIronBlock = StructureIronBlock.registry();
		RenforcedStairsBlock.registry();
		
		EngMachBlock.registry();
		
		EngElecMachBlock.registry();
		
		ThermalGeneratorMachBlock.registry();
		/*
		elevatorControllerBlock = ElevatorControllerBlock.registry();
		elevatorFloorBlock = ElevatorFloorBlock.registry();
		elevatorBlock = ElevatorBlock.registry();*/
	}

}
