package gaarnik.bsa.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ElevatorBlockEntity extends Entity {
	// *******************************************************************
	private static final float MOVE_SPEED = 0.1f;

	// *******************************************************************
	private int x;
	private int z;
	
	private float currentY;
	private int targetY;
	
	private boolean up;
	
	private int tempCount = 0;

	// *******************************************************************
	public ElevatorBlockEntity(World world) {
		super(world);
	}
	
	public ElevatorBlockEntity(World world, int x, int y, int z, int targetY) {
		super(world);
		
		this.preventEntitySpawning = true;
		//this.setSize(0.98F, 0.98F);
		//this.yOffset = this.height / 2.0F;
        this.setPosition(x, y, z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
		
		this.x = x;
		this.z = z;

		this.currentY = y;
		this.targetY = targetY;
		
		this.up = this.currentY < this.targetY ? true: false;
	}

	// *******************************************************************
	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onUpdate() {
		this.tempCount++;
		
		if(tempCount >= 50)
			this.setDead();
		/*if(this.up) {
			this.currentY += MOVE_SPEED;
			
			if(this.currentY >= this.targetY)
				this.setDead();
		}
		else {
			this.currentY -= MOVE_SPEED;
			
			if(this.currentY <= this.targetY)
				this.setDead();
		}*/
	}

	// *******************************************************************
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub
		
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
    public boolean canBeCollidedWith() { return !this.isDead; }

}
