package gaarnik.bsa.common.item;

public class BSAItems {
	// *******************************************************************
	public static final int SCREW_ID = 1800;
	public static final int ENG_CIRCUIT_ID = 1801;

	// *******************************************************************
	public static void registry() {
		ScrewItem.registry();
		EngCircuitItem.registry();
	}

}
