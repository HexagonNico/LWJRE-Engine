package gamma.engine.graphics;

import gamma.engine.core.Module;
import gamma.engine.graphics.resources.DeletableResource;
import org.lwjgl.opengl.GL;

public final class GraphicsModule implements Module {

	@Override
	public void onStart() {
		GL.createCapabilities();
		Graphics.clearColor(0.0f, 0.5f, 1.0f, 1.0f);
	}

	@Override
	public void onUpdate() {
		Graphics.clearFramebuffer();
		RenderingSystem3D.renderingProcess();
	}

	@Override
	public void onTerminate() {
		DeletableResource.deleteAll();
	}
}
