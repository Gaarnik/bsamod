package gaarnik.bsa.common.block;

import gaarnik.bsa.client.BSAClientProxy;
import gaarnik.bsa.common.BSAGuiHandler;
import gaarnik.bsa.common.BSAMod;
import gaarnik.bsa.common.tileentity.EngElecMachTileEntity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EngElecMachBlock extends BlockContainer {
	// *******************************************************************
	private static final String GAME_NAME = "Eng. Electrical Machine";

	private static final int TEXTURE_NUM = 32;

	// *******************************************************************
	private Random rand = new Random();

	private final boolean isActive;

	// *******************************************************************
	public EngElecMachBlock(int id, boolean isActive) {
		super(id, Material.iron);

		this.isActive = isActive;

		this.setBlockName("EngElecMachBlock");
		this.setStepSound(Block.soundAnvilFootstep);
		this.setCreativeTab(BSAMod.tabs);
	}

	// *******************************************************************
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, x);
		this.setDefaultDirection(world, x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity == null || player.isSneaking())
			return false;
		
		player.openGui(BSAMod.instance, BSAGuiHandler.GUI_ELECTRICAL_ENG_MACH, world, x, y, z);

		return true;
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity) {
		int var6 = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (var6 == 0)
			world.setBlockMetadataWithNotify(x, y, z, 2);

		if (var6 == 1)
			world.setBlockMetadataWithNotify(x, y, z, 5);

		if (var6 == 2)
			world.setBlockMetadataWithNotify(x, y, z, 3);

		if (var6 == 3)
			world.setBlockMetadataWithNotify(x, y, z, 4);
	}

	// *******************************************************************
	@SideOnly(Side.CLIENT)
	public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		if (par5 == 1)
			return TEXTURE_NUM + 3;
		else if (par5 == 0)
			return TEXTURE_NUM + 3;
		else {
			int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
			return par5 != var6 ? TEXTURE_NUM + 2 : (this.isActive ? TEXTURE_NUM + 1 : TEXTURE_NUM);
		}
	}

	public static void updateBlockState(boolean isActive, World world, int x, int y, int z) {
		int var5 = world.getBlockMetadata(x, y, z);
		TileEntity var6 = world.getBlockTileEntity(x, y, z);

		/*if (isActive)
			world.setBlockWithNotify(x, y, z, BSAMod.engMachActiveBlock.blockID);
		else
			world.setBlockWithNotify(x, y, z, BSAMod.engMachBlock.blockID);*/

		world.setBlockMetadataWithNotify(x, y, z, var5);

		if (var6 != null) {
			var6.validate();
			world.setBlockTileEntity(x, y, z, var6);
		}
	}

	public void breakBlock(World world, int par2, int par3, int par4, int par5, int par6) {
		//TDOD drop machine inventory
		/*EngElecMachTileEntity var7 = (EngElecMachTileEntity) world.getBlockTileEntity(par2, par3, par4);

		if (var7 != null) {
			for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
				ItemStack var9 = var7.getStackInSlot(var8);

				if (var9 != null) {
					float var10 = this.rand.nextFloat() * 0.8F + 0.1F;
					float var11 = this.rand.nextFloat() * 0.8F + 0.1F;
					float var12 = this.rand.nextFloat() * 0.8F + 0.1F;

					while (var9.stackSize > 0) {
						int var13 = this.rand.nextInt(21) + 10;

						if (var13 > var9.stackSize)
							var13 = var9.stackSize;

						var9.stackSize -= var13;
						EntityItem var14 = new EntityItem(world, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));

						//if (var9.hasTagCompound())
						//var14.func_92014_d().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());

						float var15 = 0.05F;
						var14.motionX = (double)((float)this.rand.nextGaussian() * var15);
						var14.motionY = (double)((float)this.rand.nextGaussian() * var15 + 0.2F);
						var14.motionZ = (double)((float)this.rand.nextGaussian() * var15);
						world.spawnEntityInWorld(var14);
					}
				}
			}
		}*/

		super.breakBlock(world, par2, par3, par4, par5, par6);
	}

	// *******************************************************************
	private void setDefaultDirection(World world, int x, int y, int z) {
		if (!world.isRemote) {
			int var5 = world.getBlockId(x, y, z - 1);
			int var6 = world.getBlockId(x, y, z + 1);
			int var7 = world.getBlockId(x - 1, y, z);
			int var8 = world.getBlockId(x + 1, y, z);
			byte var9 = 3;

			if (Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6])
				var9 = 3;

			if (Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5])
				var9 = 2;

			if (Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8])
				var9 = 5;

			if (Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7])
				var9 = 4;

			world.setBlockMetadataWithNotify(x, y, z, var9);
		}
	}

	// *******************************************************************
	public static void registry() {
		int id = BSAMod.config.getBlock("EngElecMachBlock", BSABlocks.ELEC_ENG_MACH_ID).getInt();

		BSAMod.engElecMachBlock = new EngElecMachBlock(id, false);

		GameRegistry.registerBlock(BSAMod.engElecMachBlock, "engElecMachBlock");
		LanguageRegistry.addName(BSAMod.engElecMachBlock, GAME_NAME);

		MinecraftForge.setBlockHarvestLevel(BSAMod.engElecMachBlock, "pickaxe", 2);
	}

	// *******************************************************************
	public int idDropped(int par1, Random par2Random, int par3) {
		return BSAMod.engMachBlock.blockID;
	}

	public int getBlockTextureFromSide(int side) {
		return side == 1 ? TEXTURE_NUM + 3 : (side == 0 ? TEXTURE_NUM + 3 : (side == 3 ? TEXTURE_NUM : TEXTURE_NUM + 2));
	}

	@Override
	public String getTextureFile() {
		return BSAClientProxy.BLOCKS_TEXTURE;
	}

	@Override
	public TileEntity createNewTileEntity(World world) { return new EngElecMachTileEntity(); }

}
