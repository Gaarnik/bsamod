package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAMod;
import gaarnik.bsa.common.tileentity.ElevatorControllerTileEntity;
import gaarnik.bsa.common.tileentity.ElevatorFloorTileEntity;

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

public class ElevatorFloorBlock extends BlockContainer {
	// *******************************************************************
	private static final String GAME_NAME = "Elevator Floor Block";

	// *******************************************************************

	// *******************************************************************
	public ElevatorFloorBlock(int id) {
		super(id, Material.iron);

		this.setUnlocalizedName("ElevatorFloorBlock");
		this.setHardness(7.0f);
		this.setResistance(100.0f);
		this.setStepSound(Block.soundMetalFootstep);

		this.setCreativeTab(BSAMod.tabs);

		MinecraftForge.setBlockHarvestLevel(this, "pickaxe", 2);

		GameRegistry.registerBlock(this, "elevatorFloorBlock");
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

		ElevatorFloorTileEntity entity = (ElevatorFloorTileEntity) world.getBlockTileEntity(x, y, z);

		if(entity == null)
			return;

		ElevatorControllerTileEntity controller = this.findElevatorController(world, x, y, z);

		if(controller != null)
			controller.callElevator(y);
		else
			System.out.println("Can't find controller !");
	}

	// *******************************************************************
	private ElevatorControllerTileEntity findElevatorController(World world, int x, int y, int z) {
		for(int i=0;i<world.getHeight();i++) {
			int id = world.getBlockId(x, i, z);

			if(id == BSABlocks.elevatorControllerBlock.blockID) {
				TileEntity entity = world.getBlockTileEntity(x, i, z);

				if(entity == null)
					return null;

				if(entity instanceof ElevatorControllerTileEntity == false)
					return null;

				return (ElevatorControllerTileEntity) entity;
			}
		}

		return null;
	}

	// *******************************************************************
	public static ElevatorFloorBlock registry() {
		int id = BSAMod.config.getBlock("ElevatorFloorBlock", BSABlocks.ELEVATOR_FLOOR_ID).getInt();
		return new ElevatorFloorBlock(id);
	}

	// *******************************************************************
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		this.blockIcon = register.registerIcon("bsa:elevator_floor");
	}

	@Override
	public int tickRate(World world) { return 4; }

	@Override
	public TileEntity createNewTileEntity(World world) { return new ElevatorFloorTileEntity(); }

}
