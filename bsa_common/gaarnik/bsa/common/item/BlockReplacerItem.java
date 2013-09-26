package gaarnik.bsa.common.item;

import java.util.List;

import gaarnik.bsa.common.BSAGuiHandler;
import gaarnik.bsa.common.BSAMod;
import gaarnik.bsa.common.tileentity.BlockReplacerTileEntity;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockReplacerItem extends Item implements IElectricItem {
	// *******************************************************************
	private static final int MAX_CHARGE 	= 10000;

	private static final int ENERGY_PER_USE = 100;

	// *******************************************************************

	// *******************************************************************
	public BlockReplacerItem(int id) {
		super(id);

		this.setMaxDamage(101);
		this.setCreativeTab(BSAMod.tabs);
		this.setUnlocalizedName("blockReplacerItem");

		ItemStack stack = new ItemStack(this, 1);
		ItemStack headStack = new ItemStack(BSAItems.blockReplacerHeadItem, 1);
		ItemStack circuitStack = new ItemStack(BSAItems.engCircuitItem, 1);
		ItemStack ironStack = new ItemStack(Item.ingotIron, 1);
		ItemStack lapisStack = new ItemStack(Item.dyePowder, 1);
		lapisStack.setItemDamage(4);

		GameRegistry.addRecipe(stack, new Object[] {
				"  X", " Y ", "ZA ", 'X', headStack, 'Y', lapisStack, 'Z', ironStack, 'A', circuitStack
		});

		GameRegistry.registerItem(this, "blockReplacerItem");
		LanguageRegistry.addName(this, "Block Replacer");
	}

	// *******************************************************************
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
		this.replaceBlock(stack, player, world, x, y, z);
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		this.displayGUI(world, player);
		return stack;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean par4) {
		String stored = ElectricItem.manager.getToolTip(stack);
		info.add(stored);
	}

	public void damage(ItemStack itemstack, int i, EntityPlayer entityplayer) {
		ElectricItem.manager.use(itemstack, ENERGY_PER_USE * i, entityplayer);
	}

	public boolean canTakeDamage(ItemStack itemstack, int i) {
		return true;
	}

	// *******************************************************************
	@Override
	public boolean canProvideEnergy(ItemStack itemStack) { return true; }

	@Override
	public int getChargedItemId(ItemStack itemStack) {
		return BSAItems.blockReplacerItem.itemID;
	}

	@Override
	public int getEmptyItemId(ItemStack itemStack) {
		return BSAItems.blockReplacerItem.itemID;
	}

	@Override
	public int getMaxCharge(ItemStack itemStack) { return MAX_CHARGE; }

	@Override
	public int getTier(ItemStack itemStack) { return 1; }

	@Override
	public int getTransferLimit(ItemStack itemStack) { return 32; }

	// *******************************************************************
	private void displayGUI(World world, EntityPlayer player) {
		if(player.isSneaking())
			player.openGui(BSAMod.instance, BSAGuiHandler.GUI_ENG_MACH, world, 
					(int) player.posX, (int) player.posY, (int) player.posZ);
	}

	private void replaceBlock(ItemStack stack, EntityPlayer player, World world, int x, int y, int z) {
		BlockReplacerTileEntity entity = new BlockReplacerTileEntity(player);
		ItemStack targetStack = entity.getStackInSlot(0);

		if(ElectricItem.manager.canUse(stack, ENERGY_PER_USE) == false)
			return;

		if(!world.isRemote)
			this.damage(stack, 1, player);

		if(targetStack == null)
			return;

		if(entity.decrStackSize(0, 1) == null)
			return;

		int blockId = world.getBlockId(x, y, z);
		int blockMetadata = world.getBlockMetadata(x, y, z);
		ItemStack replacedStack = new ItemStack(blockId, 1, blockMetadata);

		if(player.inventory.addItemStackToInventory(replacedStack) == false)
			if(world.isRemote == false)
				player.dropItem(blockId, 1);

		world.setBlock(x, y, z, targetStack.itemID);
		//world.setBlockMetadata(x, y, z, targetStack.getItemDamage());
		world.setBlockMetadataWithNotify(x, y, z, targetStack.getItemDamage(), 0);
	}

	// *******************************************************************
	public static BlockReplacerItem registry() {
		int id = BSAMod.config.getItem("BlockReplacerItem", BSAItems.BLOCK_REPLACER_ID).getInt();
		return new BlockReplacerItem(id);
	}

	// *******************************************************************
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		this.itemIcon = register.registerIcon("bsa:blockreplacer");
	}

	@Override
	public boolean isRepairable() { return false; }

}
