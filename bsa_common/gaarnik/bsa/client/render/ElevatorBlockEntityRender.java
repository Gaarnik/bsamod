package gaarnik.bsa.client.render;

import org.lwjgl.opengl.GL11;

import gaarnik.bsa.client.model.ElevatorModel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ElevatorBlockEntityRender extends Render {
	// *******************************************************************

	// *******************************************************************
	private ElevatorModel elevator;

	// *******************************************************************
	public ElevatorBlockEntityRender() {
		this.elevator = new ElevatorModel();
	}

	// *******************************************************************
	@Override
	public void doRender(Entity entity, double x, double y, double z, float f, float f1) {
        //this.elevator.render(entity, (float) d0, (float) d1, (float) d2, f, f, f1);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F * f1, (float) z + 0.5F);
		
		this.elevator.renderElevator();
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation func_110775_a(Entity entity) {
		return new ResourceLocation("bsa", "textures/entities/elevator.png");
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************

}
