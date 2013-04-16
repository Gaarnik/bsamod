package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAGuiHandler;
import gaarnik.bsa.common.BSAMod;
import gaarnik.bsa.common.tileentity.EngMachTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class EngMachBlock extends BSAMachineBlock {
	// *******************************************************************
	private static final String GAME_NAME = "Engineering Machine";
	
	// *******************************************************************

	// *******************************************************************
	protected EngMachBlock(int id, boolean isActive) {
		super(id, "EngMachBlock", isActive);

		this.blockIndexInTexture = 16;
	}

	// *******************************************************************
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		
		if (tileEntity == null || player.isSneaking())
			return false;
		
		player.openGui(BSAMod.instance, BSAGuiHandler.GUI_ENG_MACH, world, x, y, z);
		
		return true;
	}
	
	// *******************************************************************
	public static void updateBlockState(boolean isActive, World world, int x, int y, int z) {
        int var5 = world.getBlockMetadata(x, y, z);
        TileEntity var6 = world.getBlockTileEntity(x, y, z);
        keepEngMachInventory = true;

        if (isActive)
            world.setBlockWithNotify(x, y, z, BSAMod.engMachActiveBlock.blockID);
        else
        	world.setBlockWithNotify(x, y, z, BSAMod.engMachBlock.blockID);

        keepEngMachInventory = false;
        world.setBlockMetadataWithNotify(x, y, z, var5);

        if (var6 != null) {
            var6.validate();
            world.setBlockTileEntity(x, y, z, var6);
        }
    }

	// *******************************************************************

	// *******************************************************************
	public static void registry() {
		int id = BSAMod.config.getBlock("EngMachBlock", BSABlocks.ENG_MACH_ID).getInt();
		
		BSAMod.engMachBlock = new EngMachBlock(id, false);
		
		id = BSAMod.config.getBlock("EngMachActiveBlock", BSABlocks.ENG_MACH_ACTIVE_ID).getInt();

		BSAMod.engMachActiveBlock = new EngMachBlock(id, true);

		GameRegistry.registerBlock(BSAMod.engMachBlock, "engMachBlock");
		LanguageRegistry.addName(BSAMod.engMachBlock, GAME_NAME);

		ItemStack ironStack = new ItemStack(Item.ingotIron, 1);
		ItemStack redstoneStack = new ItemStack(Item.redstone, 1);
		ItemStack goldStack = new ItemStack(Item.ingotGold, 1);

		GameRegistry.addRecipe(new ItemStack(BSAMod.engMachBlock, 1), new Object[] {
			"XXX", "XYX", "XZX", 'X', ironStack, 'Y', redstoneStack, 'Z', goldStack
		});
		
		MinecraftForge.setBlockHarvestLevel(BSAMod.engMachBlock, "pickaxe", 2);
	}

	// *******************************************************************
	public TileEntity createNewTileEntity(World world) { return new EngMachTileEntity(); }

}
