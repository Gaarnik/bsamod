package gaarnik.bsa.client.render;

import gaarnik.bsa.common.block.BSABlocks;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class ElevatorBlockEntityRender extends Render {
	// *******************************************************************
	private final RenderBlocks blocksRenderer;

	// *******************************************************************

	// *******************************************************************
	public ElevatorBlockEntityRender() {
		this.blocksRenderer =  new RenderBlocks();
		this.shadowSize = 0.5F;
	}

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.blocksRenderer.setRenderBoundsFromBlock(BSABlocks.elevatorBlock);
        
		this.blocksRenderer.renderBlockUsingTexture(BSABlocks.elevatorBlock, 
				MathHelper.floor_double(entity.posX), 
				MathHelper.floor_double(entity.posY),
				MathHelper.floor_double(entity.posZ),
				BSABlocks.elevatorBlock.getIcon(0, 0));
	}

	@Override
	protected ResourceLocation func_110775_a(Entity entity) {
		return new ResourceLocation("textures/atlas/blocks.png");
	}

}
