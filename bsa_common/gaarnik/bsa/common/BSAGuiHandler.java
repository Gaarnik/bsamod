package gaarnik.bsa.common;

import gaarnik.bsa.client.gui.BlockReplacerGui;
import gaarnik.bsa.client.gui.ElevatorConGUI;
import gaarnik.bsa.client.gui.EngElecMachGui;
import gaarnik.bsa.client.gui.EngMachGui;
import gaarnik.bsa.client.gui.ThermalGeneratorGui;
import gaarnik.bsa.common.container.BlockReplacerContainer;
import gaarnik.bsa.common.container.ElevatorConContainer;
import gaarnik.bsa.common.container.EngElecMachContainer;
import gaarnik.bsa.common.container.EngMachContainer;
import gaarnik.bsa.common.container.ThermalGeneratorContainer;
import gaarnik.bsa.common.tileentity.BlockReplacerTileEntity;
import gaarnik.bsa.common.tileentity.ElevatorControllerTileEntity;
import gaarnik.bsa.common.tileentity.EngElecMachTileEntity;
import gaarnik.bsa.common.tileentity.EngMachTileEntity;
import gaarnik.bsa.common.tileentity.ThermalGeneratorTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class BSAGuiHandler implements IGuiHandler {
	// *******************************************************************
	public static final int GUI_ENG_MACH 			= 1;
	public static final int GUI_ELECTRICAL_ENG_MACH = 2;
	public static final int GUI_THERMAL_GENERATOR 	= 3;
	public static final int GUI_BLOCK_ELEVATOR 		= 4;
	public static final int GUI_BLOCK_REPLACER 		= 5;
	
	// *******************************************************************
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		switch(ID) {

		case GUI_ENG_MACH:
			if(tileEntity instanceof EngMachTileEntity)
				return new EngMachContainer(player.inventory, (EngMachTileEntity) tileEntity);

		case GUI_ELECTRICAL_ENG_MACH:
			if(tileEntity instanceof EngElecMachTileEntity)
				return new EngElecMachContainer(player.inventory, (EngElecMachTileEntity) tileEntity);

		case GUI_THERMAL_GENERATOR:
			if(tileEntity instanceof ThermalGeneratorTileEntity)
				return new ThermalGeneratorContainer(player.inventory, (ThermalGeneratorTileEntity) tileEntity);
			
		case GUI_BLOCK_ELEVATOR:
			if(tileEntity instanceof ElevatorControllerTileEntity)
				return new ElevatorConContainer(player.inventory, (ElevatorControllerTileEntity) tileEntity);

		case GUI_BLOCK_REPLACER:
			BlockReplacerTileEntity entity = new BlockReplacerTileEntity(player);
			return new BlockReplacerContainer(player, entity);
			
		}
		
		return null;
	}

	// *******************************************************************
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		switch(ID) {

		case GUI_ENG_MACH:
			if(tileEntity instanceof EngMachTileEntity)
				return new EngMachGui(player.inventory, (EngMachTileEntity) tileEntity);

		case GUI_ELECTRICAL_ENG_MACH:
			if(tileEntity instanceof EngElecMachTileEntity)
				return new EngElecMachGui(player.inventory, (EngElecMachTileEntity) tileEntity);

		case GUI_THERMAL_GENERATOR:
			if(tileEntity instanceof ThermalGeneratorTileEntity)
				return new ThermalGeneratorGui(player.inventory, (ThermalGeneratorTileEntity) tileEntity);
			
		case GUI_BLOCK_ELEVATOR:
			if(tileEntity instanceof ElevatorControllerTileEntity)
				return new ElevatorConGUI(player.inventory, (ElevatorControllerTileEntity) tileEntity);

		case GUI_BLOCK_REPLACER:
			BlockReplacerTileEntity entity = new BlockReplacerTileEntity(player);
			return new BlockReplacerGui(player, entity);

		}

		return null;
	}

}
