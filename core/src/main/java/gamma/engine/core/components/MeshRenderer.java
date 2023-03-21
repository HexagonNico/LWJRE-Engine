package gamma.engine.core.components;

import gamma.engine.core.annotations.DefaultValueString;
import gamma.engine.core.annotations.EditorVariable;
import gamma.engine.core.rendering.RenderingSystem;
import gamma.engine.core.resources.Material;
import gamma.engine.core.scene.Component;
import gamma.engine.core.resources.Mesh;
import gamma.engine.core.resources.Shader;
import vecmatlib.color.Color;

import java.util.Objects;

/**
 * Component that can render a single mesh.
 *
 * @author Nico
 */
public class MeshRenderer extends Component {

	// TODO: Find a way to expose this for the editor
	public Mesh mesh;
	public Material material = new Material(Color.White(), Color.White(), Color.White(), 0.0f);
	@EditorVariable("Shader")
	@DefaultValueString("/gamma/engine/core/shaders/default_shader.glsl")
	private Shader shader;

	@Override
	protected void onStart() {
		super.onStart();
		RenderingSystem.addToBatch(this, this.mesh, () -> {
			this.getComponent(Transform3D.class)
					.map(Transform3D::globalTransformation)
					.ifPresent(matrix -> this.shader.setUniform("transformation_matrix", matrix));
			this.shader.setUniform("projection_matrix", Camera3D.getCurrent().projectionMatrix());
			this.shader.setUniform("view_matrix", Camera3D.getCurrent().viewMatrix());
			this.shader.setUniform("material.ambient", material.ambient);
			this.shader.setUniform("material.diffuse", material.diffuse);
			this.shader.setUniform("material.specular", material.specular);
			this.shader.setUniform("material.shininess", material.shininess);
			this.shader.start();
			this.mesh.drawElements();
		});
	}

	@Override
	protected void onExit() {
		super.onExit();
		RenderingSystem.removeFromBatch(this, this.mesh);
	}

	public void setShader(Shader shader) {
		this.shader = Objects.requireNonNull(shader);
	}

	public void setShader(String path) {
		this.shader = Shader.getOrLoad(path);
	}

	public Shader getShader() {
		return this.shader;
	}
}
