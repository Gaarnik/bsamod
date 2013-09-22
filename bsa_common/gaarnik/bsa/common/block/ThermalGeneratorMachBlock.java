package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAMod;
import gaarnik.bsa.common.item.BSAItems;
import gaarnik.bsa.common.tileentity.ThermalGeneratorTileEntity;
import ic2.api.tile.IWrenchable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ThermalGeneratorMachBlock extends BSAMachineBlock {
	// *******************************************************************
	private static final String GAME_NAME 			= "Thermal Generator";

	private static final String TEXTURE_FRONT 			= "thermal_generator_front";
	private static final String TEXTURE_OTHER 			= "thermal_generator_other";
	private static final String TEXTURE_OTHER_ACTIVE 	= "thermal_generator_other_active";

	// *******************************************************************
	@SideOnly(Side.CLIENT)
	private Icon frontIcon;
	@SideOnly(Side.CLIENT)
	private Icon otherIcon;
	@SideOnly(Side.CLIENT)
	private Icon otherActiveIcon;

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
		int metadata = 0;
		
		if (MathHelper.abs((float) entity.posX - (float) x) < 2.0F && MathHelper.abs((float) entity.posZ - (float) z) < 2.0F) {
			double d0 = entity.posY + 1.82D - (double) entity.yOffset;

			if (d0 - (double) y > 2.0D)
				metadata = 1;

			if ((double) y - d0 > 0.0D)
				metadata = 0;
		}
		else {
			int orientation = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

			switch(orientation) {

			case 0:
				metadata = 2;
				break;

			case 1:
				metadata = 5;
				break;

			case 2:
				metadata = 3;
				break;

			case 3:
				metadata = 4;
				break;
			}
		}

		world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are) {
		return false;
	}

	// *******************************************************************
	public static void updateBlockState(boolean isActive, World world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		keepEngMachInventory = true;

		int id = isActive ? BSABlocks.thermalGeneratorActiveMachBlock.blockID: BSABlocks.thermalGeneratorMachBlock.blockID;
		world.setBlock(x, y, z, id);

		keepEngMachInventory = false;

		world.setBlockMetadataWithNotify(x, y, z, metadata, 2);

		if (tileEntity != null) {
			tileEntity.validate();
			world.setBlockTileEntity(x, y, z, tileEntity);
		}
	}

	// *******************************************************************
	public static void registry() {
		int id = BSAMod.config.getBlock("ThermalGeneratorBlock", BSABlocks.THERMAL_GENERATOR_MACH_ID).getInt();
		BSABlocks.thermalGeneratorMachBlock = new ThermalGeneratorMachBlock(id, false);

		id = BSAMod.config.getBlock("ThermalGeneratorActiveBlock", BSABlocks.THERMAL_GENERATOR_MACH_ACTIVE_ID).getInt();
		BSABlocks.thermalGeneratorActiveMachBlock = new ThermalGeneratorMachBlock(id, true);
	}

	// *******************************************************************
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		this.frontIcon = register.registerIcon("bsa:" + TEXTURE_FRONT);
		this.otherIcon = register.registerIcon("bsa:" + TEXTURE_OTHER);
		this.otherActiveIcon = register.registerIcon("bsa:" + TEXTURE_OTHER_ACTIVE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		if(side == metadata)
			return this.frontIcon;
		else
			return this.active ? this.otherActiveIcon: this.otherIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new ThermalGeneratorTileEntity();
	}

}
