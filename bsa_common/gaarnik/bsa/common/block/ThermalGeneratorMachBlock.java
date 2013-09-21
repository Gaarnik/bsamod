package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAMod;
import gaarnik.bsa.common.tileentity.ThermalGeneratorTileEntity;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ThermalGeneratorMachBlock extends BSAMachineBlock {
	// *******************************************************************
	private static final String GAME_NAME 			= "Thermal Generator";

	private static final String TEXTURE_SIDE 		= "thermal_generator_side";
	private static final String TEXTURE_SIDE_ACTIVE = "thermal_generator_side_active";
	private static final String TEXTURE_OTHER 		= "thermal_generator_other";

	// *******************************************************************
	@SideOnly(Side.CLIENT)
	private Icon sideIcon;
	@SideOnly(Side.CLIENT)
	private Icon sideActiveIcon;
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

			//TODO change recipe
			ItemStack ironStack = new ItemStack(Item.stick, 1);
			ItemStack redstoneStack = new ItemStack(Item.redstone, 1);
			ItemStack goldStack = new ItemStack(Item.ingotGold, 1);

			GameRegistry.addRecipe(new ItemStack(this, 1), new Object[] {
				"XXX", "XYX", "XZX", 'X', ironStack, 'Y', redstoneStack, 'Z', goldStack
			});
		}
	}

	// *******************************************************************
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are) {
		if(world.isRemote == true)
			return false;
		
		TileEntity entity = world.getBlockTileEntity(x, y, z);
		
		if(entity == null)
			return false;
		
		if(entity instanceof ThermalGeneratorTileEntity == false)
			return false;
		
		ThermalGeneratorTileEntity generator = (ThermalGeneratorTileEntity) entity;
		
		player.addChatMessage("Sources count: " + generator.getSourcesCount());
		
		return true;
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
		this.sideIcon = register.registerIcon("bsa:" + TEXTURE_SIDE);
		this.sideActiveIcon = register.registerIcon("bsa:" + TEXTURE_SIDE_ACTIVE);
		this.otherIcon = register.registerIcon("bsa:" + TEXTURE_OTHER);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		switch(side) {
		
		case 0:
		case 1:
			return this.otherIcon;
			
		default:
			return this.active ? this.sideActiveIcon: this.sideIcon;
		
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new ThermalGeneratorTileEntity();
	}

}
