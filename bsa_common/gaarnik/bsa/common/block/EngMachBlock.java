package gaarnik.bsa.common.block;

import gaarnik.bsa.common.BSAMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EngMachBlock extends BlockContainer {
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

	private boolean active;

	// *******************************************************************
	public EngMachBlock(int id) {
		super(id, Material.iron);

		this.active = false;

		this.setCreativeTab(BSAMod.tabs);

		this.setHardness(7.0f);
		this.setResistance(15.0f);
		this.setStepSound(Block.soundAnvilFootstep);

		//TODO move to bsamachine
		this.setUnlocalizedName("EngMachBlock");

		GameRegistry.registerBlock(this, "engMachBlock");
		LanguageRegistry.addName(this, GAME_NAME);

		ItemStack ironStack = new ItemStack(Item.ingotIron, 1);
		ItemStack redstoneStack = new ItemStack(Item.redstone, 1);
		ItemStack goldStack = new ItemStack(Item.ingotGold, 1);

		GameRegistry.addRecipe(new ItemStack(this, 1), new Object[] {
			"XXX", "XYX", "XZX", 'X', ironStack, 'Y', redstoneStack, 'Z', goldStack
		});

		//TODO move to bsamachine
		MinecraftForge.setBlockHarvestLevel(this, "pickaxe", 2);
	}


	// *******************************************************************
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

	// *******************************************************************

	// *******************************************************************
	public static EngMachBlock registry() {
		int id = BSAMod.config.getBlock("EngMachBlock", BSABlocks.ENG_MACH_ID).getInt();

		return new EngMachBlock(id);
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

		case 0:
		case 1:
			return this.otherIcon;

		default:
			return side != metadata ? this.sideIcon : (this.active ? this.activeFrontIcon: this.frontIcon);

		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return null;
	}

}
