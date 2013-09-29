package gaarnik.bsa.common.entity;

import gaarnik.bsa.common.BSAMod;
import cpw.mods.fml.common.registry.EntityRegistry;

public class BSAEntities {
	
	public static void registry() {
		EntityRegistry.registerModEntity(ElevatorBlockEntity.class, "ElevatorBlockEntity", 0, BSAMod.instance, 80, 3, true);
	}

}
