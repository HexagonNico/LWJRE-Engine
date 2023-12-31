package io.github.lwjre.demo;

import io.github.hexagonnico.vecmatlib.vector.Vec3f;
import io.github.lwjre.engine.annotations.EditorVariable;
import io.github.lwjre.engine.nodes.Node;
import io.github.lwjre.engine.nodes.RigidBody3D;
import io.github.lwjre.engine.resources.NodeResource;

import java.util.Random;

public class TestNode extends Node {

	@EditorVariable
	public int count = 1000;

	@Override
	protected void onEnter() {
		NodeResource resource = NodeResource.getOrLoad("scenes/dragon.yaml");
		Random random = new Random();
		for(int i = 0; i < this.count; i++) {
			if(resource.instantiate() instanceof RigidBody3D dragon) {
				dragon.applyForce(new Vec3f(0.0f, -9.81f, 0.0f).multipliedBy(dragon.mass));
				dragon.position = new Vec3f(
						random.nextFloat(-100.0f, 100.0f),
						random.nextFloat(-100.0f, 100.0f),
						random.nextFloat(-100.0f, 100.0f)
				);
				this.getParent().queueChild(dragon);
			}
		}
		super.onEnter();
	}
}
