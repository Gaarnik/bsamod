package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BSAMachineBlock extends BlockContainer {
	// *******************************************************************

	// *******************************************************************
	private final boolean isActive;

	protected static boolean keepEngMachInventory;
	
	// *******************************************************************
	public BSAMachineBlock(int id, String name, boolean isActive) {
		super(id, Material.rock);

		keepEngMachInventory = false;
		
		this.isActive = isActive;
		
		this.setUnlocalizedName(name);
		this.setHardness(7.0f);
		this.setResistance(15.0f);
		this.setStepSound(Block.soundAnvilFootstep);
		
		this.setCreativeTab(BSAMod.tabs);
	}

	// *******************************************************************
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, x);
		this.setDefaultDirection(world, x, y, z);
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity) {
        int var6 = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (var6 == 0)
        	world.setBlockMetadataWithNotify(x, y, z, 2, 0);

        if (var6 == 1)
        	world.setBlockMetadataWithNotify(x, y, z, 5, 0);

        if (var6 == 2)
        	world.setBlockMetadataWithNotify(x, y, z, 3, 0);

        if (var6 == 3)
        	world.setBlockMetadataWithNotify(x, y, z, 4, 0);
    }
	
	public void breakBlock(World world, int par2, int par3, int par4, int par5, int par6) {
        if (!keepEngMachInventory) {
            IInventory var7 = (IInventory) world.getBlockTileEntity(par2, par3, par4);

            if (var7 != null) {
                for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
                    ItemStack var9 = var7.getStackInSlot(var8);

                    if (var9 != null) {
                        float var10 = BSAMod.rand.nextFloat() * 0.8F + 0.1F;
                        float var11 = BSAMod.rand.nextFloat() * 0.8F + 0.1F;
                        float var12 = BSAMod.rand.nextFloat() * 0.8F + 0.1F;

                        while (var9.stackSize > 0) {
                            int var13 = BSAMod.rand.nextInt(21) + 10;

                            if (var13 > var9.stackSize)
                                var13 = var9.stackSize;

                            var9.stackSize -= var13;
                            EntityItem var14 = new EntityItem(world, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));

                            //if (var9.hasTagCompound())
                                //var14.func_92014_d().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());

                            float var15 = 0.05F;
                            var14.motionX = (double)((float) BSAMod.rand.nextGaussian() * var15);
                            var14.motionY = (double)((float) BSAMod.rand.nextGaussian() * var15 + 0.2F);
                            var14.motionZ = (double)((float) BSAMod.rand.nextGaussian() * var15);
                            world.spawnEntityInWorld(var14);
                        }
                    }
                }
            }
        }

        super.breakBlock(world, par2, par3, par4, par5, par6);
    }

	// *******************************************************************
	
	// *******************************************************************
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		/*final int texture = this.blockIndexInTexture;
		
		if (par5 == 1)
			return texture + 3;
		else if (par5 == 0)
			return texture + 3;
		else {
			int var6 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
			return par5 != var6 ? texture + 2 : (this.isActive ? texture + 1 : texture);
		}*/
		return null;
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

			world.setBlockMetadataWithNotify(x, y, z, var9, 0);
		}
	}

	// *******************************************************************

}
