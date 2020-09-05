package cold.fyre.API.Packets.Complete.util;

/**
 * Used for setting certain attributes to a player. Note that these will not apply to a player until
 * the PacketPlayOutAbilities is sent to the player. These settings can be adjusted, or can be gathered
 * from a player.
 * 
 * @author Armeriness
 * @author Sommod
 * @since 2.0
 *
 */
public class PlayerAbilities {
	
	private boolean isInvulnerable, isFlying, canFly, canInstantBuild;
	private boolean mayBuild = true;
	private float flySpeed = 0.05F;
	private float walkSpeed = 0.1F;
	
	/**
	 * Generic constructor that creates the base and common abilities a player will have.
	 */
	public PlayerAbilities() { }
	
	/**
	 * Loads this object with the variables found within another PlayerAbilities class.
	 * @param abilities - PlayerAbilities
	 */
	public PlayerAbilities(PlayerAbilities abilities) {
		isInvulnerable = abilities.isInvulnerable();
		isFlying = abilities.isFlying();
		canFly = abilities.canFly();
		canInstantBuild = abilities.canInstantBuild();
		mayBuild = abilities.mayBuild();
		flySpeed = abilities.flySpeed();
		walkSpeed = abilities.walkSpeed();
	}
	
	/**
	 * Stores all the data of what a player can / cannot do.
	 * @param isInvulnerable - Sets whether the player is invincible or not
	 * @param isFlying - sets whether the player is flying or not
	 * @param canFly - sets whether the player can fly
	 * @param canInstantBuild - Sets wether the player can instant build
	 * @param mayBuild - Sets whether the player is allowed to build
	 * @param flySpeed - Sets the players fly speed
	 * @param walkSpeed - Sets the players walk speed
	 */
	public PlayerAbilities(boolean isInvulnerable, boolean isFlying, boolean canFly, boolean canInstantBuild, boolean mayBuild, float flySpeed, float walkSpeed) {
		this.isInvulnerable = isInvulnerable;
		this.isFlying = isFlying;
		this.canFly = canFly;
		this.canInstantBuild = canInstantBuild;
		this.mayBuild = mayBuild;
		this.flySpeed = flySpeed;
		this.walkSpeed = walkSpeed;
	}
	
	/**
	 * Sets the object of whether the player is invincible or not.
	 * @param isInvulnerable
	 */
	public void setInvulnerable(boolean isInvulnerable) { this.isInvulnerable = false; }
	
	/**
	 * Sets whether the player is flying.
	 * @param isFlying
	 */
	public void setIsFlying(boolean isFlying) { this.isFlying = isFlying; }
	
	/**
	 * Sets whether the player can fly or not.
	 * @param canFly
	 */
	public void setCanFly(boolean canFly) { this.canFly = canFly; }
	
	/**
	 * Sets whether the player can instantly build.
	 * @param canInstantBuild
	 */
	public void setCanInstantBuild(boolean canInstantBuild) { this.canInstantBuild = canInstantBuild; }
	
	/**
	 * Sets the flying speed of the player.
	 * @param flySpeed
	 */
	public void setFlySpeed(float flySpeed) { this.flySpeed = flySpeed; }
	
	/**
	 * Sets the walk speed of a player.
	 * @param walkSpeed
	 */
	public void setWalkSpeed(float walkSpeed) { this.walkSpeed = walkSpeed; }
	
	/**
	 * Returns the value of if the player is Invinvincible or not.
	 * @return True - if player is invulnerable
	 */
	public boolean isInvulnerable() { return isInvulnerable; }
	
	/**
	 * Returns the value of if the player is flying.
	 * @return True - if player is flying
	 */
	public boolean isFlying() { return isFlying; }
	
	/**
	 * Returns the value of if the player can fly.
	 * @return True - player can fly
	 */
	public boolean canFly() { return canFly; }
	
	/**
	 * Returns the value of if the player can instant build.
	 * @return True - if player can instant build
	 */
	public boolean canInstantBuild() { return canInstantBuild; }
	
	/**
	 * Returns the value of if the player can build.
	 * @return True - if player can build
	 */
	public boolean mayBuild() { return mayBuild; }
	
	/**
	 * Returns the value of the fly speed of the player.
	 * @return fly speed of the player
	 */
	public float flySpeed() { return flySpeed; }
	
	/**
	 * Returns the value of the player's walk speed.
	 * @return walk speed of player
	 */
	public float walkSpeed() { return walkSpeed; }

}
