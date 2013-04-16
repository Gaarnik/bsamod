package gaarnik.bsa.common.item;

import gaarnik.bsa.client.BSAClientProxy;
import gaarnik.bsa.common.BSAGuiHandler;
import gaarnik.bsa.common.BSAMod;
import gaarnik.bsa.common.tileentity.BlockReplacerTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class BlockReplacerItem extends Item {
	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	public BlockReplacerItem(int id) {
		super(id);
		
		this.setIconIndex(3);
		this.setItemName("BlockReplacerItem");
		
		this.setCreativeTab(BSAMod.tabs);
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

	// *******************************************************************
	private void displayGUI(World world, EntityPlayer player) {
		if(player.isSneaking())
			player.openGui(BSAMod.instance, BSAGuiHandler.GUI_ENG_MACH, world, 
					(int) player.posX, (int) player.posY, (int) player.posZ);
	}
	
	private void replaceBlock(ItemStack stack, EntityPlayer player, World world, int x, int y, int z) {
		BlockReplacerTileEntity entity = new BlockReplacerTileEntity(player);
		ItemStack targetStack = entity.getStackInSlot(0);
		
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
		world.setBlockMetadata(x, y, z, targetStack.getItemDamage());
	}

	// *******************************************************************
	public static void registry() {
		int id = BSAMod.config.getItem("BlockReplacerItem", BSAItems.BLOCK_REPLACER_ID).getInt();

		BSAMod.blockReplacerItem = new BlockReplacerItem(id);
		
		//TODO replace iron by re-battery, if no item not create recipe

		ItemStack stack = new ItemStack(BSAMod.blockReplacerItem, 1);
		ItemStack headStack = new ItemStack(BSAMod.blockReplacerHeadItem, 1);
		ItemStack circuitStack = new ItemStack(BSAMod.engCircuitItem, 1);
		ItemStack ironStack = new ItemStack(Item.ingotIron, 1);
		ItemStack lapisStack = new ItemStack(Item.dyePowder, 1);
		lapisStack.setItemDamage(4);

		GameRegistry.addRecipe(stack, new Object[] {
			"  X", " Y ", "ZA ", 'X', headStack, 'Y', lapisStack, 'Z', ironStack, 'A', circuitStack
		});

		GameRegistry.registerItem(BSAMod.blockReplacerItem, "blockReplacerItem");
		LanguageRegistry.addName(BSAMod.blockReplacerItem, "Block Replacer");
	}

	// *******************************************************************
	@Override
	public String getTextureFile() { return BSAClientProxy.ITEMS_TEXTURE; }

}
