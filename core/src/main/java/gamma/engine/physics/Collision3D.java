package gamma.engine.physics;

import gamma.engine.tree.CollisionObject3D;
import vecmatlib.vector.Vec3f;

public record Collision3D(CollisionObject3D collider, Vec3f normal, float depth) {
}