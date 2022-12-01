package io.github.view.graphics;

import org.lwjgl.opengl.GL11;

public final class Graphics {

	// TODO: Make sure OpenGL context is current on the calling thread when GL* functions are called

	public static void clearColor(float red, float green, float blue, float alpha) {
		GL11.glClearColor(red, green, blue, alpha);
	}

	public static void clearFramebuffer() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
}
