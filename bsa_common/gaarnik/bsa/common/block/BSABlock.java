package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAMod;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BSABlock extends Block {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public BSABlock(int id, String name) {
		super(id, Material.iron);

		this.setUnlocalizedName(name);
		this.setHardness(7.0f);
		this.setResistance(100.0f);
		this.setStepSound(Block.soundMetalFootstep);

		this.setCreativeTab(BSAMod.tabs);
		
		MinecraftForge.setBlockHarvestLevel(this, "pickaxe", 2);
	}

	// *******************************************************************
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems) {
		if(this.hasSubBlock() == false) {
			super.getSubBlocks(par1, tab, subItems);
			return;
		}
		
		for (int i=0;i<2;i++)
			subItems.add(new ItemStack(this, 1, i));
	}

	// *******************************************************************
	public abstract boolean hasSubBlock();

	// *******************************************************************

	// *******************************************************************
	@Override
	public int quantityDropped(Random rand) { return 1; }

	@Override
	public int damageDropped(int metadata) { return metadata; }

}
