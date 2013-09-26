package gaarnik.bsa.common.item;

import net.minecraft.item.Item;

public class BSAItems {
	// *******************************************************************
	public static final int SCREW_ID = 1800;
	public static final int ENG_CIRCUIT_ID = 1801;
	
	public static final int BLOCK_REPLACER_HEAD_ID = 1900;
	public static final int BLOCK_REPLACER_ID = 1901;

	// *******************************************************************
	public static Item screwItem;
	public static Item engCircuitItem;
	
	public static Item blockReplacerHeadItem;
	public static Item blockReplacerItem;
	
	// *******************************************************************
	public static void registry() {
		screwItem = ScrewItem.registry();
		engCircuitItem = EngCircuitItem.registry();
		
		blockReplacerHeadItem = BlockReplacerHeadItem.registry();
		blockReplacerItem = BlockReplacerItem.registry();
	}

}
