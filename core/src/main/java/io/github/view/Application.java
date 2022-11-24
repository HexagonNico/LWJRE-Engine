package io.github.view;

import io.github.view.core.Entity3D;
import io.github.view.core.TestComponent;
import io.github.view.core.TreeNode;
import io.github.view.resources.Resource;
import org.lwjgl.glfw.GLFW;

public final class Application {

	private static Application application;

	public static boolean isMainThread() {
		return Thread.currentThread().equals(application.mainThread);
	}

	public static boolean isRenderingThread() {
		return Thread.currentThread().equals(application.renderingThread);
	}

	public static Window window() {
		return application.window;
	}

	private Window window;

	private final Thread mainThread;
	private final Thread renderingThread;

	private final TreeNode root;

	private Application() {
		this.mainThread = Thread.currentThread();
		this.renderingThread = new Thread(this::renderingThread);
		this.mainThread.setName("Main-Thread");
		this.renderingThread.setName("Rendering-Thread");
		this.root = new TreeNode();
		Entity3D entity = new Entity3D();
		entity.addComponent(TestComponent::new);
		this.root.addChild(entity);
	}

	private void start() {
		if(GLFW.glfwInit()) {
			this.window = new Window("Hello", 900, 900);
			this.renderingThread.start();
			while(!this.window.shouldClose()) {
				this.root.process();
				GLFW.glfwPollEvents();
			}
			try {
				this.renderingThread.join();
			} catch (InterruptedException e) {
				// TODO: Terminate program
				throw new RuntimeException(e);
			}
			this.window.destroy();
		} else {
			System.err.println("Could not initialize GLFW");
		}
		GLFW.glfwTerminate();
	}

	private void renderingThread() {
		this.window.makeContextCurrent();
		while(!this.window.shouldClose()) {
			Rendering.renderingProcess();
			this.root.render();
			this.window.update();
		}
		Resource.cleanUp();
		this.window.makeContextNonCurrent();
	}

	public static void main(String[] args) {
		application = new Application();
		application.start();
	}
}
