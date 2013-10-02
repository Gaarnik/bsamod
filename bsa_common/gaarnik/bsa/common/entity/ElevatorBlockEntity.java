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
	private int targetY;

	private boolean up;

	// *******************************************************************
	public ElevatorBlockEntity(World world) {
		super(world);
	}

	public ElevatorBlockEntity(World world, int x, int y, int z, int targetY) {
		super(world);

		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		//this.yOffset = this.height / 2.0F;
		this.setPosition(x, y, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;

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
		super.onUpdate();

		if(worldObj.isRemote == false) {
			if(this.up)
				this.motionY = MOVE_SPEED;
			else
				this.motionY = - MOVE_SPEED;
		}
		
		this.setPosition(this.posX, this.posY + this.motionY, this.posZ);
		
		if((this.posY >= this.targetY && this.up) || (this.posY <= this.targetY && this.up == false)) {
			this.motionY = 0;
			this.worldObj.setBlock((int) this.posX, (int) this.targetY, (int) this.posZ, BSABlocks.elevatorBlock.blockID);
			this.setDead();
		}
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
	@Override
	public boolean canBeCollidedWith() { return !this.isDead; }

	@Override
	public AxisAlignedBB getBoundingBox() { return this.boundingBox; }

	@Override
	public AxisAlignedBB getCollisionBox(Entity entity) { return entity.boundingBox; }

}
