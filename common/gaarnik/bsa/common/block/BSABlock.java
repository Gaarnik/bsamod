package gaarnik.bsa.common.block;

import gaarnik.bsa.client.BSAClientProxy;
import gaarnik.bsa.common.BSAMod;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BSABlock extends Block {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public BSABlock(int id, String name) {
		super(id, Material.iron);

		this.setBlockName(name);
		this.setHardness(7.0f);
		this.setResistance(15.0f);
		this.setStepSound(Block.soundMetalFootstep);

		this.setCreativeTab(BSAMod.tabs);
	}

	// *******************************************************************
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems) {
		for (int i=0;i<2;i++)
			subItems.add(new ItemStack(this, 1, i));
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	@Override
	public int quantityDropped(Random rand) { return 1; }
	
	@Override
	public String getTextureFile() { return BSAClientProxy.BLOCKS_TEXTURE; }

}
