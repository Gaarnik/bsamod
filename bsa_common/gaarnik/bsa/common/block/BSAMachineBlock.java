package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public abstract class BSAMachineBlock extends BlockContainer {
	// *******************************************************************

	// *******************************************************************
	protected boolean active;

	protected static boolean keepEngMachInventory;
	
	// *******************************************************************
	public BSAMachineBlock(int id, String name) {
		super(id, Material.rock);

		keepEngMachInventory = false;
		
		this.active = false;
		
		this.setUnlocalizedName(name);
		this.setHardness(7.0f);
		this.setResistance(15.0f);
		this.setStepSound(Block.soundAnvilFootstep);

		MinecraftForge.setBlockHarvestLevel(this, "pickaxe", 2);
		
		this.setCreativeTab(BSAMod.tabs);
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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		int orientation = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		switch(orientation) {

		case 0:
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
			break;

		case 1:
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
			break;

		case 2:
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
			break;

		case 3:
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
			break;

		}
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

	// *******************************************************************

}
