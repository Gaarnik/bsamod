package gaarnik.bsa.common.recipe;

import gaarnik.bsa.common.BSAMod;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EngMachRecipe {
	// *******************************************************************

	// *******************************************************************
	private static final EngMachRecipe smeltingBase = new EngMachRecipe();
	
	private Map<Integer, ItemStack> smeltingList = new HashMap<Integer, ItemStack>();
    private Map<Integer, Float> experienceList = new HashMap<Integer, Float>();
    private HashMap<List<Integer>, ItemStack> metaSmeltingList = new HashMap<List<Integer>, ItemStack>();
    private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();

	// *******************************************************************
	public EngMachRecipe() {
		this.addSmelting(Item.ingotIron.itemID, new ItemStack(BSAMod.screwItem, 4), 0.7F);
		this.addSmelting(Item.ingotGold.itemID, new ItemStack(BSAMod.engCircuitItem, 4), 0.9F);
	}

	// *******************************************************************
	public void addSmelting(int par1, ItemStack par2ItemStack, float experience) {
        this.smeltingList.put(Integer.valueOf(par1), par2ItemStack);
        this.experienceList.put(Integer.valueOf(par2ItemStack.itemID), Float.valueOf(experience));
    }
	
	public void addSmelting(int itemID, int metadata, ItemStack itemstack, float experience) {
        metaSmeltingList.put(Arrays.asList(itemID, metadata), itemstack);
        metaExperience.put(Arrays.asList(itemID, metadata), experience);
    }

	// *******************************************************************
	@Deprecated
    public ItemStack getSmeltingResult(int par1) { return (ItemStack)this.smeltingList.get(Integer.valueOf(par1)); }

    public Map<Integer, ItemStack> getSmeltingList() { return this.smeltingList; }

    @Deprecated //In favor of ItemStack sensitive version
    public float getExperience(int par1) { 
        return this.experienceList.containsKey(Integer.valueOf(par1)) ? ((Float)this.experienceList.get(Integer.valueOf(par1))).floatValue() : 0.0F;
    }
    
    public ItemStack getSmeltingResult(ItemStack item) {
        if (item == null)
            return null;
        
        ItemStack ret = (ItemStack)metaSmeltingList.get(Arrays.asList(item.itemID, item.getItemDamage()));
        
        if (ret != null) 
            return ret;

        return (ItemStack)smeltingList.get(Integer.valueOf(item.itemID));
    }
    
    public float getExperience(ItemStack item) {
        if (item == null || item.getItem() == null)
            return 0;
        
        float ret = item.getItem().getSmeltingExperience(item);
        
        if (ret < 0 && metaExperience.containsKey(Arrays.asList(item.itemID, item.getItemDamage())))
            ret = metaExperience.get(Arrays.asList(item.itemID, item.getItemDamage()));
        
        if (ret < 0 && experienceList.containsKey(item.itemID))
            ret = ((Float)experienceList.get(item.itemID)).floatValue();
        
        return (ret < 0 ? 0 : ret);
    }

	// *******************************************************************
	public static final EngMachRecipe smelting() { return smeltingBase; }

	// *******************************************************************
	public Map<List<Integer>, ItemStack> getMetaSmeltingList() { return metaSmeltingList; }

}
