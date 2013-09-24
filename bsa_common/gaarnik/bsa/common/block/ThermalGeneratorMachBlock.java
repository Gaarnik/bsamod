package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAGuiHandler;
import gaarnik.bsa.common.BSAMod;
import gaarnik.bsa.common.item.BSAItems;
import gaarnik.bsa.common.tileentity.ThermalGeneratorTileEntity;
import ic2.api.tile.IWrenchable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ThermalGeneratorMachBlock extends BSAMachineBlock {
	// *******************************************************************
	public static final String GAME_NAME 				= "Thermal Generator";

	private static final String TEXTURE_FRONT 			= "thermal_generator_front";
	private static final String TEXTURE_SIDE 			= "thermal_generator_side";
	private static final String TEXTURE_OTHER 			= "thermal_generator_other";

	// *******************************************************************
	@SideOnly(Side.CLIENT)
	private Icon frontIcon;
	@SideOnly(Side.CLIENT)
	private Icon sideIcon;
	@SideOnly(Side.CLIENT)
	private Icon otherIcon;

	// *******************************************************************
	public ThermalGeneratorMachBlock(int id, boolean active) {
		super(id, "ThermalGeneratorMachBlock");

		this.active = active;
	    
		//only register the inactive machine
		if(active == false) {
			GameRegistry.registerBlock(this, "thermalGeneratorMachBlock");
			LanguageRegistry.addName(this, GAME_NAME);

			ItemStack screwStack = new ItemStack(BSAItems.screwItem, 1);
			ItemStack obsidianStack = new ItemStack(Block.obsidian, 1);
			ItemStack circuitStack = new ItemStack(BSAItems.engCircuitItem, 1);

			GameRegistry.addRecipe(new ItemStack(this, 1), new Object[] {
				"XYX", "YZY", "XYX", 'X', screwStack, 'Y', obsidianStack, 'Z', circuitStack
			});
		}
	}

	// *******************************************************************
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (!world.isRemote) {
			int l = world.getBlockId(x, y, z - 1);
			int i1 = world.getBlockId(x, y, z + 1);
			int j1 = world.getBlockId(x - 1, y, z);
			int k1 = world.getBlockId(x + 1, y, z);
			byte b0 = 3;

			if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
				b0 = 3;

			if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
				b0 = 2;

			if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
				b0 = 5;

			if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
				b0 = 4;

			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}

	@Override
	public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis) {
		if (axis == ForgeDirection.UNKNOWN) return false;

		TileEntity tileEntity = worldObj.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof IWrenchable) {
			IWrenchable te = (IWrenchable)tileEntity;

			int newFacing = ForgeDirection.getOrientation(te.getFacing()).getRotation(axis).ordinal();

			if (te.wrenchCanSetFacing(null, newFacing)) {
				te.setFacing((short)newFacing);
			}
		}

		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		int metadata = BlockPistonBase.determineOrientation(world, x, y, z, entity);
		world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity == null)
			return false;
		
		if(player.isSneaking())
			return true;
		
		player.openGui(BSAMod.instance, BSAGuiHandler.GUI_THERMAL_GENERATOR, world, x, y, z);

		return true;
	}

	// *******************************************************************
	public static void registry() {
		int id = BSAMod.config.getBlock("ThermalGeneratorBlock", BSABlocks.THERMAL_GENERATOR_MACH_ID).getInt();
		BSABlocks.thermalGeneratorMachBlock = new ThermalGeneratorMachBlock(id, false);
	}

	// *******************************************************************
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		this.frontIcon = register.registerIcon("bsa:" + TEXTURE_FRONT);
		this.sideIcon = register.registerIcon("bsa:" + TEXTURE_SIDE);
		this.otherIcon = register.registerIcon("bsa:" + TEXTURE_OTHER);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		if(side == metadata)
			return this.frontIcon;
		else if(side == 0 || side == 1)
			return this.otherIcon;
		else
			return this.sideIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new ThermalGeneratorTileEntity();
	}

}
