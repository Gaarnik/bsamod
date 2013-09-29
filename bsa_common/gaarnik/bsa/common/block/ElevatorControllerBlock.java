package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAMod;
import gaarnik.bsa.common.tileentity.ElevatorControllerTileEntity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ElevatorControllerBlock extends BlockContainer {
	// *******************************************************************
	private static final String GAME_NAME = "Elevator Controller Block";

	// *******************************************************************
	
	// *******************************************************************
	public ElevatorControllerBlock(int id) {
		super(id, Material.iron);

		this.setUnlocalizedName("ElevatorControllerBlock");
		this.setHardness(7.0f);
		this.setResistance(100.0f);
		this.setStepSound(Block.soundMetalFootstep);

		this.setCreativeTab(BSAMod.tabs);
		
		MinecraftForge.setBlockHarvestLevel(this, "pickaxe", 2);
		
		GameRegistry.registerBlock(this, "elevatorControllerBlock");
		LanguageRegistry.addName(this, GAME_NAME);
	}

	// *******************************************************************
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int id) {
		boolean powered = world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z);

		if(powered)
			world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate(world));
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (world.isRemote)
			return;

		ElevatorControllerTileEntity controller = (ElevatorControllerTileEntity) world.getBlockTileEntity(x, y, z);

		if(controller != null)
			controller.callElevator(y);
		else
			System.out.println("Can't find controller !");
	}

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
	public int tickRate(World world) { return 4; }
	
	@Override
	public TileEntity createNewTileEntity(World world) { return new ElevatorControllerTileEntity(); }

}
