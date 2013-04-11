package gaarnik.bsa.common;

import gaarnik.bsa.common.block.BSABlocks;
import gaarnik.bsa.common.item.BSAItems;
import gaarnik.bsa.common.tileentity.EngElecMachTileEntity;
import gaarnik.bsa.common.tileentity.EngElecMachTileEntity2;
import gaarnik.bsa.common.tileentity.EngMachTileEntity;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "BSAMod")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class BSAMod {
	// *******************************************************************
	@SidedProxy(clientSide = "gaarnik.bsa.client.BSAClientProxy", serverSide = "gaarnik.bsa.server.BSAServerProxy")
	public static BSACommonProxy proxy;
	
	@Instance("BSAMod")
	public static BSAMod instance = new BSAMod();
	
	public static Configuration config;
	
	public static CreativeTabs tabs = new BSACreativeTabs("bsaTab");
	
	private BSAGuiHandler engGuiHandler = new BSAGuiHandler();

	// *******************************************************************
	public static Block renforcedIronBlock;
	public static Block structureIronBlock;
	public static Block gridIronBlock;
	
	public static Block renforcedStairslock;
	public static Block renforcedDarkStairslock;
	
	public static Block engMachBlock;
	public static Block engMachActiveBlock;

	public static Block engElecMachBlock;

	// *******************************************************************
	public static Item screwItem;
	public static Item engCircuitItem;
	
	public static Item blockReplacerItem;

	// *******************************************************************
	@PreInit
	public void preload(FMLPreInitializationEvent event) {
		BSAMod.config = new Configuration(event.getSuggestedConfigurationFile());
		BSAMod.config.load();
	}

	@Init
	public void load(FMLInitializationEvent event) {
		proxy.registerTextures();
		
		LanguageRegistry.instance().addStringLocalization("itemGroup.bsaTab", "en_US", "BSA Mod");
		
		BSAItems.registry();
		BSABlocks.registry();
		
		GameRegistry.registerTileEntity(EngMachTileEntity.class, "EngMachBlock");
		GameRegistry.registerTileEntity(EngElecMachTileEntity.class, "EngElecMachBlock");
		GameRegistry.registerTileEntity(EngElecMachTileEntity2.class, "EngElecMachBlock2");
		
		NetworkRegistry.instance().registerGuiHandler(this, engGuiHandler);
	}

	@PostInit
	public void postload(FMLPostInitializationEvent event) {
		BSAMod.config.save();
	}

	@ServerStarting
	public void serverStarting(FMLServerStartingEvent event) {
		
	}
}
