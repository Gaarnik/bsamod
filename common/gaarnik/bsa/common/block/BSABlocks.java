package gaarnik.bsa.common.block;

public class BSABlocks {
	// *******************************************************************
	public static final int RENFORCED_IRON_ID = 1700;
	public static final int STRUCTURE_IRON_ID = 1701;
	public static final int GRID_IRON_ID = 1702;

	public static final int ENG_MACH_ID = 1750;
	public static final int ENG_MACH_ACTIVE_ID = 1751;

	public static final int ELEC_ENG_MACH_ID = 1752;
	public static final int ELEC_ENG_MACH_ACTIVE_ID = 1753;

	// *******************************************************************
	public static void registry() {
		RenforcedIronBlock.registry();
		StructureIronBlock.registry();
		GridIronBlock.registry();
		
		EngMachBlock.registry();
		
		EngElecMachBlock.registry();
	}

}
