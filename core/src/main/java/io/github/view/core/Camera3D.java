package io.github.view.core;

import io.github.view.math.Matrix4;
import io.github.view.math.Vector3;

public class Camera3D extends TreeNode {

	public static Camera3D current() {
		return current;
	}

	public static Matrix4 currentProjection() {
		return current != null ? current.projectionMatrix() : Matrix4.IDENTITY;
	}

	public static Matrix4 currentView() {
		return current != null ? current.viewMatrix() : Matrix4.IDENTITY;
	}

	private static Camera3D current;

	public Vector3 position = Vector3.ZERO;
	public Vector3 rotation = Vector3.ZERO;

	public float fov = 70.0f;
	public float nearPlane = 0.1f;
	public float farPlane = 1000.0f;

	public void makeCurrent() {
		current = this;
	}

	public Matrix4 projectionMatrix() {
		float m00 = 1.0f / (float) Math.tan(Math.toRadians(fov / 2.0f));
		float m11 = m00 * (960.0f / 540.0f);
		float m22 = -(farPlane + nearPlane) / (farPlane - nearPlane);
		float m23 = -(2 * farPlane * nearPlane) / (farPlane - nearPlane);
		return new Matrix4(
				m00, 0.0f, 0.0f, 0.0f,
				0.0f, m11, 0.0f, 0.0f,
				0.0f, 0.0f, m22, m23,
				0.0f, 0.0f, -1.0f, 0.0f
		);
	}

	public Matrix4 viewMatrix() {
		return new Matrix4(
				1.0f, 0.0f, 0.0f, 0.0f,
				0.0f, (float) Math.cos(-this.rotation.x()), (float) -Math.sin(-this.rotation.x()), 0.0f,
				0.0f, (float) Math.sin(-this.rotation.x()), (float) Math.cos(-this.rotation.x()), 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f
		).multiply(new Matrix4(
				(float) Math.cos(-this.rotation.y()), 0.0f, (float) Math.sin(-this.rotation.y()), 0.0f,
				0.0f, 1.0f, 0.0f, 0.0f,
				(float) -Math.sin(-this.rotation.y()), 0.0f, (float) Math.cos(-this.rotation.y()), 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f
		)).multiply(new Matrix4(
				(float) Math.cos(-this.rotation.z()), (float) -Math.sin(-this.rotation.z()), 0.0f, 0.0f,
				(float) Math.sin(-this.rotation.z()), (float) Math.cos(-this.rotation.z()), 0.0f, 0.0f,
				0.0f, 0.0f, 1.0f, 0.0f,
				0.0f, 0.0f, 0.0f, 1.0f
		)).multiply(new Matrix4(
				1.0f, 0.0f, 0.0f, -this.position.x(),
				0.0f, 1.0f, 0.0f, -this.position.y(),
				0.0f, 0.0f, 1.0f, -this.position.z(),
				0.0f, 0.0f, 0.0f, 1.0f
		));
	}
}
