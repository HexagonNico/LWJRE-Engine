package io.github.lwjre.engine.nodes;

import io.github.hexagonnico.vecmatlib.vector.Vec3f;
import io.github.lwjre.engine.annotations.EditorVariable;
import io.github.lwjre.engine.servers.PhysicsServer;

/**
 * Node that represents an object that moves through kinematic equation.
 * Useful for colliders whose velocity is updated directly such as with user input.
 *
 * @see DynamicBody3D
 *
 * @author Nico
 */
public class KinematicBody3D extends CollisionObject3D {

	/**
	 * The body's current velocity.
	 */
	@EditorVariable(name = "Velocity")
	public Vec3f velocity = Vec3f.Zero();

	/**
	 * The body's current acceleration.
	 */
	@EditorVariable(name = "Acceleration")
	public Vec3f acceleration = new Vec3f(0.0f, -9.81f, 0.0f);

	@Override
	protected void onUpdate(float delta) {
		this.velocity = this.velocity.plus(this.acceleration.multipliedBy(delta));
		this.position = this.position.plus(this.velocity.multipliedBy(delta));
		if(this.velocity.lengthSquared() > 0.0f) {
			PhysicsServer.resolveCollision(this);
		}
		super.onUpdate(delta);
	}

	@Override
	protected void onCollision(CollisionObject3D collider, Vec3f normal, float depth) {
		this.position = this.position.minus(normal.multipliedBy(depth));
		this.velocity = this.velocity.slide(normal);
	}
}
