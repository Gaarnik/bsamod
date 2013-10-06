package gaarnik.bsa.common.entity;

import gaarnik.bsa.common.block.BSABlocks;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ElevatorBlockEntity extends Entity {
	// *******************************************************************
	private static final float MOVE_SPEED = 0.1f;

	// *******************************************************************
	private float currentY;
	
	private int targetX;
	private int targetY;
	private int targetZ;

	private boolean up;

	// *******************************************************************
	public ElevatorBlockEntity(World world) {
		super(world);
		
		this.preventEntitySpawning = true;
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
	}

	public ElevatorBlockEntity(World world, float x, float y, float z, int targetY) {
		super(world);

		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;

		this.targetX = (int) x;
		this.targetY = targetY;
		this.targetZ = (int) z;
		
		x += 0.5F;
		y -= 0.5F;
		z += 0.5F;

		this.setPosition(x, y, z);
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;

		this.currentY = y;

		this.up = this.currentY < this.targetY ? true: false;
	}

	// *******************************************************************
	@Override
	protected void entityInit() {
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
		if(worldObj.isRemote == false) {
			if(this.up)
				this.motionY = MOVE_SPEED;
			else
				this.motionY = - MOVE_SPEED;
		}
		
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
		
		if((this.posY >= ((float) this.targetY - 0.5f) && this.up) 
				|| (this.posY <= ((float) this.targetY) && this.up == false)) {
			
			this.motionY = 0;
			this.worldObj.setBlock(this.targetX, this.targetY, this.targetZ, BSABlocks.elevatorBlock.blockID);
			this.setDead();
		}
	}

	// *******************************************************************
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.currentY = nbt.getFloat("currentY");
		
		this.targetX = nbt.getInteger("targetX");
		this.targetY = nbt.getInteger("targetY");
		this.targetZ = nbt.getInteger("targetZ");
		
		this.up = nbt.getBoolean("uo");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setFloat("currentY", this.currentY);
		
		this.targetX = nbt.getInteger("targetX");
		this.targetY = nbt.getInteger("targetY");
		this.targetZ = nbt.getInteger("targetZ");
		
		nbt.setBoolean("up", this.up);
	}

	// *******************************************************************

	// *******************************************************************

	// *******************************************************************
	@Override
	public boolean canBeCollidedWith() { return !this.isDead; }

	@Override
	public AxisAlignedBB getBoundingBox() { return this.boundingBox; }

	@Override
	public AxisAlignedBB getCollisionBox(Entity entity) { return entity.boundingBox; }

}
