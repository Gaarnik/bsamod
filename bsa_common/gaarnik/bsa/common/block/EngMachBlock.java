package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAGuiHandler;
import gaarnik.bsa.common.BSAMod;
import gaarnik.bsa.common.tileentity.EngMachTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EngMachBlock extends BSAMachineBlock {
	// *******************************************************************
	private static final String GAME_NAME = 			"Engineering Machine";

	private static final String TEXTURE_FRONT = 		"eng_machine_front";
	private static final String TEXTURE_FRONT_ACTIVE = 	"eng_machine_front_active";
	private static final String TEXTURE_SIDE = 			"eng_machine_side";
	private static final String TEXTURE_OTHER = 		"eng_machine_other";

	// *******************************************************************
	@SideOnly(Side.CLIENT)
	private Icon frontIcon;
	@SideOnly(Side.CLIENT)
	private Icon activeFrontIcon;
	@SideOnly(Side.CLIENT)
	private Icon sideIcon;
	@SideOnly(Side.CLIENT)
	private Icon otherIcon;

	// *******************************************************************
	public EngMachBlock(int id, boolean active) {
		super(id, "EngMachBlock");

		this.active = active;

		this.setCreativeTab(BSAMod.tabs);

		this.setHardness(7.0f);
		this.setResistance(15.0f);
		this.setStepSound(Block.soundAnvilFootstep);

		//only register the inactive machine
		if(active == false) {
			GameRegistry.registerBlock(this, "engMachBlock");
			LanguageRegistry.addName(this, GAME_NAME);

			ItemStack ironStack = new ItemStack(Item.ingotIron, 1);
			ItemStack redstoneStack = new ItemStack(Item.redstone, 1);
			ItemStack goldStack = new ItemStack(Item.ingotGold, 1);

			GameRegistry.addRecipe(new ItemStack(this, 1), new Object[] {
				"XXX", "XYX", "XZX", 'X', ironStack, 'Y', redstoneStack, 'Z', goldStack
			});
		}
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
		int metadata = world.getBlockMetadata(x, y, z);
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		keepEngMachInventory = true;

		int id = isActive ? BSABlocks.engMachActiveBlock.blockID: BSABlocks.engMachBlock.blockID;
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
		int id = BSAMod.config.getBlock("EngMachBlock", BSABlocks.ENG_MACH_ID).getInt();
		BSABlocks.engMachBlock = new EngMachBlock(id, false);

		id = BSAMod.config.getBlock("EngMachActiveBlock", BSABlocks.ENG_MACH_ACTIVE_ID).getInt();
		BSABlocks.engMachActiveBlock = new EngMachBlock(id, true);
	}

	// *******************************************************************
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		this.frontIcon = register.registerIcon("bsa:" + TEXTURE_FRONT);
		this.activeFrontIcon = register.registerIcon("bsa:" + TEXTURE_FRONT_ACTIVE);
		this.sideIcon = register.registerIcon("bsa:" + TEXTURE_SIDE);
		this.otherIcon = register.registerIcon("bsa:" + TEXTURE_OTHER);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata) {
		switch(side) {

		//top & bottom
		case 0:
		case 1:
			return this.otherIcon;

		default:
			//if metadata == 0 (inventory rendering) && side == front
			if(metadata == 0 && side == 3)
				return this.frontIcon;

			return side != metadata ? this.sideIcon : (this.active ? this.activeFrontIcon: this.frontIcon);

		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new EngMachTileEntity();
	}

}
