package gaarnik.bsa.common.block;

import java.util.Random;

import gaarnik.bsa.common.BSAGuiHandler;
import gaarnik.bsa.common.BSAMod;
import gaarnik.bsa.common.tileentity.EngElecMachTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class EngElecMachBlock extends BSAMachineBlock {
	// *******************************************************************
	private static final String GAME_NAME = "Eng. Electrical Machine";

	// *******************************************************************

	// *******************************************************************
	public EngElecMachBlock(int id, boolean isActive) {
		super(id, "EngElecMachBlock", isActive);
		
		this.blockIndexInTexture = 32;
	}

	// *******************************************************************
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity == null || player.isSneaking())
			return false;
		
		player.openGui(BSAMod.instance, BSAGuiHandler.GUI_ELECTRICAL_ENG_MACH, world, x, y, z);

		return true;
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public static void registry() {
		int id = BSAMod.config.getBlock("EngElecMachBlock", BSABlocks.ELEC_ENG_MACH_ID).getInt();

		BSAMod.engElecMachBlock = new EngElecMachBlock(id, false);
		
		GameRegistry.registerBlock(BSAMod.engElecMachBlock, "engElecMachBlock");
		LanguageRegistry.addName(BSAMod.engElecMachBlock, GAME_NAME);

		ItemStack screwStack = new ItemStack(BSAMod.screwItem, 1);
		ItemStack ironStack = new ItemStack(Item.ingotIron, 1);
		ItemStack redstoneStack = new ItemStack(Item.redstone, 1);
		ItemStack circuitStack = new ItemStack(BSAMod.engCircuitItem, 1);

		GameRegistry.addRecipe(new ItemStack(BSAMod.engElecMachBlock, 1), new Object[] {
			"AXA", "XYX", "XZX", 'X', ironStack, 'A', screwStack, 'Y', redstoneStack, 'Z', circuitStack
		});

		MinecraftForge.setBlockHarvestLevel(BSAMod.engElecMachBlock, "pickaxe", 2);
	}

	// *******************************************************************
	@Override
	public TileEntity createNewTileEntity(World world) { return new EngElecMachTileEntity(); }
	
	@Override
	public int idDropped(int par1, Random rand, int par3) {
		int random = rand.nextInt(100);
		
		if(random >= 90)
			return BSAMod.engCircuitItem.itemID;
		else
			return this.blockID;
	}

}
